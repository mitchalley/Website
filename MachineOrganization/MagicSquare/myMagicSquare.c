///////////////////////////////////////////////////////////////////////////////
//
// Copyright 2020 Jim Skrentny
// Posting or sharing this file is prohibited, including any changes/additions.
// Used by permission, CS 354 Fall 2020, Deb Deppeler
//
////////////////////////////////////////////////////////////////////////////////
// Main File:        (myMagicSquare.c)
// This File:        (myMagicSquare.c)
// Other Files:      (no other files)
// Semester:         CS 354 Fall 2020
//
// Author:           (Mitchell Alley)
// Email:            (mgalley@wisc.edu)
// CS Login:         (alley)
//
/////////////////////////// OTHER SOURCES OF HELP //////////////////////////////
//                   Fully acknowledge and credit all sources of help,
//                   other than Instructors and TAs.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   Avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of
//                   of any information you find.
////////////////////////////////////////////////////////////////////////////////
   
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Structure that represents a magic square
typedef struct {
    int size;      // dimension of the square
    int **magic_square; // pointer to heap allocated magic square
} MagicSquare;

/* TODO:
 * Prompts the user for the magic square's size, reads it,
 * checks if it's an odd number >= 3 (if not display the required
 * error message and exit), and returns the valid number.
 */
int getSize() {
	int n;
	printf("Enter magic square's size (odd integer >=3)\n");
	//checks for user input
	scanf("%i", &n);
	//makes sure that n is greater than 3 and odd
        if (n % 2 == 0){
                printf("Size must be odd.\n");
                exit(1);
        }
	else if (n < 3){
                printf("Size must be >= 3.\n");
                exit(1);
        }
    	return n;   
} 
   
/* TODO:
 * Makes a magic square of size n using the alternate 
 * Siamese magic square algorithm from assignment and 
 * returns a pointer to the completed MagicSquare struct.
 *
 * n the number of rows and columns
 */
MagicSquare *generateMagicSquare(int n) {
    	MagicSquare *m = NULL;
	//allocates memory for the 2D array
        m = malloc(sizeof(MagicSquare));
	m->magic_square = malloc(sizeof(int*) * n);	
	m->size = n;
	//makes sure malloc worked
	if (m == NULL || m->magic_square == NULL){
		printf("malloc did not allocate memory");
		exit(1);
	}	
	
	
	//allocates memory for the 2D array and
	//makes sure malloc worked
	for (int i = 0; i < n; i++){
		*(m->magic_square + i) = malloc(sizeof(int*) * n);
		if (*(m->magic_square + i) == NULL){
			printf("malloc did not allocate memory");
			exit(1);
		}
	}
	//variables to keep track of position in algorithm
	int total = n * n;
	int jlocation = n / 2;
	int ilocation = 0;
	int counter = 1;
	int oldi = ilocation;
	int oldj = jlocation;
	*(*(m->magic_square + ilocation) + jlocation) = counter;
	counter++;
	//will iterate until n*n is in the magic_square
	while (counter != total + 1){
		ilocation--;
		if (ilocation < 0){
			ilocation = m->size - 1;
		}
		jlocation++;
		if (jlocation >= m->size){
			jlocation = 0;
		}
		//spot is filled, move down
		if (*(*(m->magic_square + ilocation) + jlocation) > 0){
			if (oldi == m->size - 1){
				oldi = -1;	
			}
			ilocation = oldi + 1;
			jlocation = oldj;
			
		       *(*(m->magic_square + ilocation) + jlocation) = counter;
		} else {
			*(*(m->magic_square + ilocation) + jlocation) = counter;
		}
		oldi = ilocation;
		oldj = jlocation;
		counter++;

	}
	return m;    
} 

/* TODO:  
 * Opens a new file (or overwrites the existing file)
 * and writes the square in the specified format.
 *
 * magic_square the magic square to write to a file
 * filename the name of the output file
 */
void fileOutputMagicSquare(MagicSquare *magic_square, char *filename) {
	FILE *fp = fopen(filename, "w");
	if (fp == NULL) {
        	printf("Can't open file for reading.\n");
        	exit(1);
    }//prints magic_square in filename
	fprintf(fp, "%i %s", magic_square->size, "\n");
	for (int i = 0; i < magic_square->size; i++){
		for ( int j = 0; j < magic_square->size; j++){
			fprintf(fp, "%i", *(*(magic_square->magic_square + i) + j));
			if (j != magic_square->size - 1){
				fprintf(fp, ",");
			}
		}fprintf(fp, "\n");
	}
	//frees heap memory
	for (int i = 0; i < magic_square->size; i++){
		free(*(magic_square->magic_square + i));
		*(magic_square->magic_square + i) = NULL;

	}
	//frees heap memory
	free(magic_square->magic_square);
	magic_square->magic_square = NULL;
	free(magic_square);
	magic_square = NULL;
	//makes sure file closees properly
	if (fclose(fp) != 0) {
        printf("Error while closing the file.\n");
        exit(1);
    }
}

/* TODO:
 * Generates a magic square of the user specified size and
 * output the quare to the output filename
 */
int main(int argc, char *argv[]) {
    // TODO: Check input arguments to get output filename
	if (argc != 2){
		printf("Usage: ./myMagicSquare <output_filename>\n");
		exit(1);
	}
	char *file = argv[1];
    // TODO: Get magin square's size from user
	int size = getSize();
	//printf("got here");
    // TODO: Generate the magic square
	//MagicSquare final = generateMagicSquare(size);
    // TODO: Output the magic square
        
	fileOutputMagicSquare(generateMagicSquare(size), file);
    return 0;
} 


                                                         
//				myMagicSquare.c      

