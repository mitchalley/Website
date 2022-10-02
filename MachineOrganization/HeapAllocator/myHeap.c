///////////////////////////////////////////////////////////////////////////////
//
// Copyright 2019-2020 Jim Skrentny
// Posting or sharing this file is prohibited, including any changes/additions.
// Used by permission Fall 2020, CS354-deppeler
//
///////////////////////////////////////////////////////////////////////////////
 
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/mman.h>
#include <stdio.h>
#include <string.h>
#include "myHeap.h"
 
/*
 * This structure serves as the header for each allocated and free block.
 * It also serves as the footer for each free block but only containing size.
 */
typedef struct blockHeader {           
    int size_status;
    /*
    * Size of the block is always a multiple of 8.
    * Size is stored in all block headers and free block footers.
    *
    * Status is stored only in headers using the two least significant bits.
    *   Bit0 => least significant bit, last bit
    *   Bit0 == 0 => free block
    *   Bit0 == 1 => allocated block
    *
    *   Bit1 => second last bit 
    *   Bit1 == 0 => previous block is free
    *   Bit1 == 1 => previous block is allocated
    * 
    * End Mark: 
    *  The end of the available memory is indicated using a size_status of 1.
    * 
    * Examples:
    * 
    * 1. Allocated block of size 24 bytes:
    *    Header:
    *      If the previous block is allocated, size_status should be 27
    *      If the previous block is free, size_status should be 25
    * 
    * 2. Free block of size 24 bytes:
    *    Header:
    *      If the previous block is allocated, size_status should be 26
    *      If the previous block is free, size_status should be 24
    *    Footer:
    *      size_status should be 24
    */
} blockHeader;         

/* Global variable - DO NOT CHANGE. It should always point to the first block,
 * i.e., the block at the lowest address.
 */
blockHeader *heapStart = NULL;     

#define IS_CURRENT_ALLOC(x) ((x) & (int) 0x1)
#define IS_PREV_ALLOC(x) (((x) & (int) 0x2) >> 1)
#define SET_CURRENT_ALLOC(x) ((x) |= (int) 0x1)
#define SET_PREV_ALLOC(x) ((x) |= (int) 0x2)
#define SET_CURRENT_FREE(x) ((x) &= ~((int) 0x1))
#define SET_PREV_FREE(x) ((x) &= ~((int) 0x2))
#define GET_SIZE(x) ((x) & ~((int) 0x3))


/* Size of heap allocation padded to round to nearest page size.
 */
int allocsize;
void *lastAllocated = NULL;
void *resumeHere = NULL;

/*
 * Additional global variables may be added as needed below
 */

 
/* 
 * Function for allocating 'size' bytes of heap memory.
 * Argument size: requested size for the payload
 * Returns address of allocated block on success.
 * Returns NULL on failure.
 * This function should:
 * - Check size - Return NULL if not positive or if larger than heap space.
 * - Determine block size rounding up to a multiple of 8 and possibly adding padding as a result.
 * - Use NEXT-FIT PLACEMENT POLICY to chose a free block
 * - Use SPLITTING to divide the chosen free block into two if it is too large.
 * - Update header(s) and footer as needed.
 * Tips: Be careful with pointer arithmetic and scale factors.
 */
void* myAlloc(int size) {     
    //TODO: Your code goes in here.
        // return NULL if size parameter is not positive or larger than heap space.
    if (size <= 0 || size > allocsize) {
      return NULL;
    }

    // size including header size
    int sizePlusHeader = size + 4;

    // round size up to a multiple of 8 if necessary
    if ((sizePlusHeader % 8) != 0) {
      sizePlusHeader += (8 - (sizePlusHeader % 8));
    }
  
    void *ptr = NULL;
    if (resumeHere == NULL) {
        ptr = (void *) heapStart;
    }
    else {
        ptr = resumeHere;
    }

    //while (ptr < (void*) heapStart + allocsize ) {
    // loop through memory until an available block is found
    while (lastAllocated == NULL || ptr != lastAllocated) {

        // get reference to current location's header
	    blockHeader *currentBlock = (blockHeader*) ptr;
	    int current_size_status = currentBlock->size_status;

        // get size of current location, (no p&a-bits)
	    int currentSize = GET_SIZE(current_size_status);

        // incremement and continue to loop if size doesn't fit in current location
	    if (IS_CURRENT_ALLOC(current_size_status) || size > currentSize) {

            if (IS_CURRENT_ALLOC(current_size_status)){
            }
            else {
            }

            // increment pointer, wrap around if necessary
		    ptr += currentSize;
            if (ptr >= (void*) heapStart + allocsize) {
                ptr = (void*) heapStart;
            }
		    continue;
	    }
        // size fits here, proceed with allocation
        else {
            // split if necessary
            if (currentSize - sizePlusHeader >= 8){
                // TODO? update size of newly allocated block
                SET_CURRENT_ALLOC(currentBlock->size_status);

                // create a block to follow the newly allocated block
                blockHeader* newBlock = (blockHeader*) ((char*)currentBlock + sizePlusHeader);
                //newBlock->size_status = currentSize - sizePlusHeader + 2;
                newBlock->size_status = currentSize - sizePlusHeader;
                SET_PREV_ALLOC(newBlock->size_status);
                SET_CURRENT_FREE(newBlock->size_status);


                // update the footer for the newly created following free block
                blockHeader* newFooter = (blockHeader*) ((char*)newBlock + newBlock->size_status - 6);
                newFooter->size_status = currentSize - sizePlusHeader;
            }
            // else perfect size, update next block's p-bit
            else {
                blockHeader* nextBlock = (blockHeader*)((char*)currentBlock + currentSize);
                //nextBlock->size_status += 2;
                SET_PREV_ALLOC(nextBlock->size_status);

            }
            lastAllocated = ptr;
            //ptr += currentSize;
            resumeHere = ptr + sizePlusHeader;
            if (resumeHere >= (void*) heapStart + allocsize) {
                resumeHere = (void*) heapStart;
            }
            
   	        return ptr + 4;
        }
   }
    return NULL;
} 
 
/* 
 * Function for freeing up a previously allocated block.
 * Argument ptr: address of the block to be freed up.
 * Returns 0 on success.
 * Returns -1 on failure.
 * This function should:
 * - Return -1 if ptr is NULL.
 * - Return -1 if ptr is not a multiple of 8.
 * - Return -1 if ptr is outside of the heap space.
 * - Return -1 if ptr block is already freed.
 * - USE IMMEDIATE COALESCING if one or both of the adjacent neighbors are free.
 * - Update header(s) and footer as needed.
 */                    
int myFree(void *ptr) {    

    // Return -1 if ptr is NULL.
    if (ptr == NULL) {
        return -1;
    }

    // Return -1 if ptr is not a multiple of 8.
    if ((int)ptr % 8 != 0) {
        return -1;
    }

    // Return -1 if ptr is outside of the heap space.
    if (ptr >= (void*) heapStart + allocsize) {
        return -1;
    }

    // Return -1 if ptr block is already freed.
    blockHeader* headerToFree = (blockHeader*) ((void*)ptr - 4);
    if (IS_CURRENT_ALLOC(headerToFree->size_status) == 0){
        return -1;
    }

    // USE IMMEDIATE COALESCING if one or both of the adjacent neighbors are free.
    
    // get current block size
    SET_CURRENT_FREE(headerToFree->size_status);
    int currBlockSize = GET_SIZE(headerToFree->size_status);

    // clear p bit of next block
    blockHeader* nextBlock = (blockHeader*)((void*)headerToFree + currBlockSize);
    SET_PREV_FREE(nextBlock->size_status);
    
    // create footer and return if next block is allocated
    if (IS_CURRENT_ALLOC(nextBlock->size_status)) {
        blockHeader* newFooter = (blockHeader*)((void*)nextBlock - 4);
        newFooter->size_status = currBlockSize;

    } 
    // coalesce if next block is free
    else {
        headerToFree->size_status += nextBlock->size_status;
        blockHeader* newFooter = 
            (blockHeader*) ((void*)nextBlock + GET_SIZE(nextBlock->size_status) - 4);
        newFooter->size_status = currBlockSize + GET_SIZE(nextBlock->size_status);

    }

    // check next block if previous is allocated
    if (IS_PREV_ALLOC(headerToFree->size_status)) {
    } 
    // block is free, check next block
    else { 

        blockHeader* prevFooter = (blockHeader*) ((void*)headerToFree - 4);
        blockHeader* prevHeader = 
            (blockHeader*) ((void*)headerToFree - prevFooter->size_status);
        prevHeader->size_status += GET_SIZE(headerToFree->size_status);
        
        blockHeader* finalFooter = (blockHeader*) ((void*)prevFooter + GET_SIZE(prevFooter->size_status) - 4);
        finalFooter->size_status = GET_SIZE(prevHeader->size_status);
        // int prevBlockSize = GET_SIZE(prevHeader->size_status);
        
        // // coalesce previous if next is allocated
        // if ((nextBlock->size_status & 3) == 1) {

        //     // update previous block's size
        //     prevHeader->size_status += currBlockSize;

        //     // make new footer
        //     blockHeader* newFooter = (blockHeader*)((void*)nextBlock - 4);
        //     newFooter->size_status = prevBlockSize + currBlockSize;
            
        // } 
        // // coalesce everything
        // else {

        //     // update previous block's size
        //     prevHeader->size_status += currBlockSize + nextBlock->size_status;

        //     // make new footer
        //     blockHeader* newFooter = 
        //         (blockHeader*) ((void*)nextBlock + nextBlock->size_status - 4);
        //     newFooter->size_status = prevBlockSize + 
        //         currBlockSize + nextBlock->size_status;
        // }
    }

	// return 0 for success
    return 0;
} 
 
/*
 * Function used to initialize the memory allocator.
 * Intended to be called ONLY once by a program.
 * Argument sizeOfRegion: the size of the heap space to be allocated.
 * Returns 0 on success.
 * Returns -1 on failure.
 */                    
int myInit(int sizeOfRegion) {    
 
    static int allocated_once = 0; //prevent multiple myInit calls
 
    int pagesize;  // page size
    int padsize;   // size of padding when heap size not a multiple of page size
    void* mmap_ptr; // pointer to memory mapped area
    int fd;

    blockHeader* endMark;
  
    if (0 != allocated_once) {
        fprintf(stderr, 
        "Error:mem.c: InitHeap has allocated space during a previous call\n");
        return -1;
    }
    if (sizeOfRegion <= 0) {
        fprintf(stderr, "Error:mem.c: Requested block size is not positive\n");
        return -1;
    }

    // Get the pagesize
    pagesize = getpagesize();

    // Calculate padsize as the padding required to round up sizeOfRegion 
    // to a multiple of pagesize
    padsize = sizeOfRegion % pagesize;
    padsize = (pagesize - padsize) % pagesize;

    allocsize = sizeOfRegion + padsize;

    // Using mmap to allocate memory
    fd = open("/dev/zero", O_RDWR);
    if (-1 == fd) {
        fprintf(stderr, "Error:mem.c: Cannot open /dev/zero\n");
        return -1;
    }
    mmap_ptr = mmap(NULL, allocsize, PROT_READ | PROT_WRITE, MAP_PRIVATE, fd, 0);
    if (MAP_FAILED == mmap_ptr) {
        fprintf(stderr, "Error:mem.c: mmap cannot allocate space\n");
        allocated_once = 0;
        return -1;
    }
  
    allocated_once = 1;

    // for double word alignment and end mark
    allocsize -= 8;

    // Initially there is only one big free block in the heap.
    // Skip first 4 bytes for double word alignment requirement.
    heapStart = (blockHeader*) mmap_ptr + 1;

    // Set the end mark
    endMark = (blockHeader*)((void*)heapStart + allocsize);
    endMark->size_status = 1;

    // Set size in header
    heapStart->size_status = allocsize;

    // Set p-bit as allocated in header
    // note a-bit left at 0 for free
    heapStart->size_status += 2;

    // Set the footer
    blockHeader *footer = (blockHeader*) ((void*)heapStart + allocsize - 4);
    footer->size_status = allocsize;
  
    return 0;
} 
                  
/* 
 * Function to be used for DEBUGGING to help you visualize your heap structure.
 * Prints out a list of all the blocks including this information:
 * No.      : serial number of the block 
 * Status   : free/used (allocated)
 * Prev     : status of previous block free/used (allocated)
 * t_Begin  : address of the first byte in the block (where the header starts) 
 * t_End    : address of the last byte in the block 
 * t_Size   : size of the block as stored in the block header
 */                     
void dispMem() {     
 
    int counter;
    char status[5];
    char p_status[5];
    char *t_begin = NULL;
    char *t_end   = NULL;
    int t_size;

    blockHeader *current = heapStart;
    counter = 1;

    int used_size = 0;
    int free_size = 0;
    int is_used   = -1;

    fprintf(stdout, "************************************Block list***\
                    ********************************\n");
    fprintf(stdout, "No.\tStatus\tPrev\tt_Begin\t\tt_End\t\tt_Size\n");
    fprintf(stdout, "-------------------------------------------------\
                    --------------------------------\n");
  
    while (current->size_status != 1) {
        t_begin = (char*)current;
        t_size = current->size_status;
    
        if (t_size & 1) {
            // LSB = 1 => used block
            strcpy(status, "used");
            is_used = 1;
            t_size = t_size - 1;
        } else {
            strcpy(status, "Free");
            is_used = 0;
        }

        if (t_size & 2) {
            strcpy(p_status, "used");
            t_size = t_size - 2;
        } else {
            strcpy(p_status, "Free");
        }

        if (is_used) 
            used_size += t_size;
        else 
            free_size += t_size;

        t_end = t_begin + t_size - 1;
    
        fprintf(stdout, "%d\t%s\t%s\t0x%08lx\t0x%08lx\t%d\n", counter, status, 
        p_status, (unsigned long int)t_begin, (unsigned long int)t_end, t_size);
    
        current = (blockHeader*)((char*)current + t_size);
        counter = counter + 1;
    }

    fprintf(stdout, "---------------------------------------------------\
                    ------------------------------\n");
    fprintf(stdout, "***************************************************\
                    ******************************\n");
    fprintf(stdout, "Total used size = %d\n", used_size);
    fprintf(stdout, "Total free size = %d\n", free_size);
    fprintf(stdout, "Total size = %d\n", used_size + free_size);
    fprintf(stdout, "***************************************************\
                    ******************************\n");
    fflush(stdout);

    return;  
} 


// end of myHeap.c (fall 2020)

