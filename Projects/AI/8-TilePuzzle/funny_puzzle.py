#!/usr/bin/env python
# coding: utf-8

# In[3]:


import heapq



#    INPUT: Two states (if second state is omitted then it is assumed that it is the goal state)
#    RETURNS: A scalar that is the sum of Manhattan distances for all tiles.
def get_manhattan_distance(from_state, to_state=[1, 2, 3, 4, 5, 6, 7, 0, 0]):

    distance = []
    for i in range(len(to_state)): # Iterates through the goal state
        if from_state[i] != to_state[i] and from_state[i] != 0: # If the current state at i is not the goal, enter if
            rr = (from_state[i] - 1) // 3 # Floor division
            rc = (from_state[i] - 1) % 3
            cr = i // 3
            cc = i % 3
            distance.append(abs(rr - cr) + abs(rc - cc)) # Moving that piece to the idea state
            
    
    return sum(distance)


#    INPUT: A state (list of length 9)
#    WHAT IT DOES: Prints the list of all the valid successors in the puzzle. 
def print_succ(state):

    succ_states = get_succ(state)

    for succ_state in succ_states: # Gets all possible successor states
        print(succ_state, "h={}".format(get_manhattan_distance(succ_state)))


#    INPUT: A state (list of length 9)
#    RETURNS: A list of all the valid successors in the puzzle (don't forget to sort the result as done below). 

def get_succ(state):
    
    # Determining whether all possible locations on board are ideal
    succ_states = []
    zzb = True
    zob = True
    ztb = True
    ozb = True
    oob = True
    otb = True
    tzb = True
    tob = True
    ttb = True
    succ_states = []
    for i in range(len(state)):
                
            # Assigns all possible states to their respective position before the algorithm
        if state[i] == 0:
            tmp = []
            zz = state[0]
            zo = state[1]
            zt = state[2]
            oz = state[3]
            oo = state[4]
            ot = state[5]
            tz = state[6]
            to = state[7]
            tt = state[8]
            if state[i] == zz and zzb: # If the correct number is not in the correct position, move it, add to list of successors
                zzb = False
                if state[1] != 0:
                    tmp.append([state[1], state[0],state[2],state[3],state[4],state[5],state[6],state[7],state[8]])
                if state[3] != 0:
                    tmp.append([state[3], state[1],state[2],state[0],state[4],state[5],state[6],state[7],state[8]])
                
            elif state[i] == zo and zob:
                zob = False
                if state[0] != 0:
                    tmp.append([state[1], state[0],state[2],state[3],state[4],state[5],state[6],state[7],state[8]])
                if state[4] != 0:
                    tmp.append([state[0], state[4],state[2],state[3],state[1],state[5],state[6],state[7],state[8]])
                if state[2] != 0:
                    tmp.append([state[0], state[2],state[1],state[3],state[4],state[5],state[6],state[7],state[8]])
            
            elif state[i] == zt and ztb:
                ztb = False
                if state[1] != 0:
                    tmp.append([state[0], state[2],state[1],state[3],state[4],state[5],state[6],state[7],state[8]])
                if state[5] != 0:
                    tmp.append([state[0], state[1],state[5],state[3],state[4],state[2],state[6],state[7],state[8]])
            
            elif state[i] == oz and ozb:
                ozb = False
                if state[0] != 0:
                    tmp.append([state[3], state[1],state[2],state[0],state[4],state[5],state[6],state[7],state[8]])
                if state[4] != 0:
                    tmp.append([state[0], state[1],state[2],state[4],state[3],state[5],state[6],state[7],state[8]])
                if state[6] != 0:
                    tmp.append([state[0], state[1],state[2],state[6],state[4],state[5],state[3],state[7],state[8]])
                
            elif state[i] == oo and oob:
                oob = False
                if state[1] != 0:
                    tmp.append([state[0], state[4],state[2],state[3],state[1],state[5],state[6],state[7],state[8]])
                if state[5] != 0:
                    tmp.append([state[0], state[1],state[2],state[3],state[5],state[4],state[6],state[7],state[8]])
                if state[7] != 0:
                    tmp.append([state[0], state[1],state[2],state[3],state[7],state[5],state[6],state[4],state[8]])
                if state[3] != 0:
                    tmp.append([state[0], state[1],state[2],state[4],state[3],state[5],state[6],state[7],state[8]])
                
            elif state[i] == ot and otb:
                otb = False
                if state[2] != 0:
                    tmp.append([state[0], state[1],state[5],state[3],state[4],state[2],state[6],state[7],state[8]])
                if state[4] != 0:
                    tmp.append([state[0], state[1],state[2],state[3],state[5],state[4],state[6],state[7],state[8]])
                if state[8] != 0:
                    tmp.append([state[0], state[1],state[2],state[3],state[4],state[8],state[6],state[7],state[5]])
                
            elif state[i] == tz and tzb:
                tzb = False
                if state[3] != 0:
                    tmp.append([state[0], state[1],state[2],state[6],state[4],state[5],state[3],state[7],state[8]])
                if state[7] != 0:
                    tmp.append([state[0], state[1],state[2],state[3],state[4],state[5],state[7],state[6],state[8]])
                
            elif state[i] == to and tob:
                tob = False
                if state[6] != 0:
                    tmp.append([state[0], state[1],state[2],state[3],state[4],state[5],state[7],state[6],state[8]])
                if state[4] != 0:
                    tmp.append([state[0], state[1],state[2],state[3],state[7],state[5],state[6],state[4],state[8]])
                if state[8] != 0:
                    tmp.append([state[0], state[1],state[2],state[3],state[4],state[5],state[6],state[8],state[7]])
                
            elif state[i] == tt and ttb:
                ttb = False
                if state[5] != 0:
                    tmp.append([state[0], state[1],state[2],state[3],state[4],state[8],state[6],state[7],state[5]])
                if state[7] != 0:
                    tmp.append([state[0], state[1],state[2],state[3],state[4],state[5],state[6],state[8],state[7]])
                
            for states in tmp: # Adding the list of succesors so that they can be printed and we can determine the best option
                succ_states.append(states)           
   
    return sorted(succ_states)

#    INPUT: An initial state (list of length 9)
# WHAT IT SHOULD DO: Prints a path of configurations from initial state to goal state along  h values, number of moves, and max queue number in the format specified in the pdf.
# A* Algorithm
def solve(state, goal_state=[1, 2, 3, 4, 5, 6, 7, 0, 0]):

    pq = []
    closed = []
    order = {}
    heapq.heappush(pq, (get_manhattan_distance(state) + 0, state, (0, get_manhattan_distance(state), -1)))
    counter = 0
    tmp = 0
    maxqueue = 1
    while pq != []: # While the queue is not empty
        tmp = heapq.heappop(pq) # Get the next item in the queue
        closed.append(tmp[1])
        order[counter] = {'tmp1':tmp[1],'tmp2':tmp[2]} # Create a dictionary storing the values so they are accessible
        if tmp[1] == goal_state: # Ensures that we are not already in the goal state
            break
        sucs = get_succ(tmp[1])
        for s in sucs: # Iterates through the successors
            if s not in closed:
                heapq.heappush(pq,(get_manhattan_distance(s) + tmp[2][0] + 1, s, (tmp[2][0] + 1, get_manhattan_distance(state), counter)))
            else:
                continue
        if len(pq) > maxqueue: # If the length of the queue is greater than maxqueue, assign the length of pq to maxqueue
            maxqueue = len(pq)
        counter = counter + 1
        
    # Collects the data from the algorithm above and populates the new board
    ind = len(order) - 1
    boards = []
    while ind != 0:
        boards.append(order[ind]['tmp1'])
        ind = order[ind]['tmp2'][2]
    boards.append(state)
    
    moves = 0
    for i in range(len(boards)): # Computes the number of moves and prints the output
        gmd = get_manhattan_distance(boards[len(boards)-i-1])
        print(boards[len(boards) - i - 1], 'h={}'.format(gmd), 'moves: {}'.format(moves))
        moves = moves + 1
    print('Max queue length: {}'.format(maxqueue))
        
if __name__ == "__main__":
    """
    Feel free to write your own test code here to exaime the correctness of your functions. 
    Note that this part will not be graded.
    """
    #print_succ([2,5,1,4,0,6,7,0,3])
    #print()

    #print(get_manhattan_distance([2,5,1,4,0,6,7,0,3], [1, 2, 3, 4, 5, 6, 7, 0, 0]))
    #print()
    
    solve([2,5,1,4,0,6,7,0,3])
    #print()

