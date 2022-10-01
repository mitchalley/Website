#!/usr/bin/env python
# coding: utf-8

# In[51]:


import csv
import numpy as np
from matplotlib import pyplot as plt
import math as m
from scipy.cluster.hierarchy import dendrogram, linkage

# Takes in a string with a path to a CSV file, and returns the data points as a list of dicts
def load_data(filepath):
    input_file = csv.DictReader(open(filepath))
    file = []
    for n in input_file:
        file.append(n)
    return file

# Takes in one row dict from the data loaded from the previous function then calculates the corresponding 
# feature vector for that Pokeon as specified above, and returns it as a numpy array of shape (6)
def calc_features(row):
    ret = []
    ret.append(row["Attack"])
    ret.append(row["Sp. Atk"])
    ret.append(row["Speed"])
    ret.append(row["Defense"])
    ret.append(row["Sp. Def"])
    ret.append(row["HP"])
    arr = np.array(ret, dtype=np.int64)
    return arr


def updateDistance(oneR, distMatrix,  twoR):
    sume = len(distMatrix)
    sumrows = distMatrix.size - 2
    i = 0
    current = []
    while sumrows > 0 and i < sume:
        if i == oneR or i == twoR: 
            if i == oneR:
                current.append(0) 
            i += 1
            continue
        firstone = distMatrix[oneR][i]
        secondone = distMatrix[twoR][i]
        filling = max(firstone, secondone)
        current.append(filling) 
        i += 1
        sumrows -= 1
    return current

# Performs complete linkage hierarchial agglomerate clustering on the Pokemon with the (x1...x6) feature representation,
# and returns a numpy array representing the clustering
def hac(features):
    
    trackclusters = []
    totalnumberofpokes = len(features)
    numberofpokes = []
    for industry in range(totalnumberofpokes): # Tracks the industry and number of Pokemon
        trackclusters.append(industry)
        numberofpokes.append(1)
    listofpokes = [] 
    for somei in features: # Saves the features to the pokes
        listofpokes.append(somei) 
    
    listofallpokesin = np.zeros((totalnumberofpokes-1, 4))
    listofpokes = np.array(listofpokes) # Assigning the variables to get ready for the clustering algorithm
    clusterofallpokes = totalnumberofpokes
    indexofzed = 0
    
    matrixofdistances = np.zeros((totalnumberofpokes, totalnumberofpokes)) # Gets the matrix of distances for each Pokemon
    
    for indi in range(totalnumberofpokes): # 
        for indj in range(totalnumberofpokes): # Retaining the features of indj and indi
            currentindxofj = features[indj]
            currentindxofi = features[indi]
            
            distance = np.linalg.norm(currentindxofi-currentindxofj) # Retrieving the distance between the two locations
            matrixofdistances[indi][indj] = distance
    while indexofzed < totalnumberofpokes-1: # Iterates while there are less indices than the total amount of pokes
        powerthrust1 = [] 
        indexofi = 0
        powerthrust2 = []
        indexofj = 0 
        findeliteindexi = 0
        sizeofpoke = 0
        findeliteindexj = 1
        mindistance = matrixofdistances[0][1]
        zeta = len(trackclusters)
        for i in range(zeta):
            sizeofpoke+=1
            indexofj = 0
            
            
            
            for j in range(zeta):
                sizeofpoke+=1
                # If the matrix is less than the minimum distance, add the index and the distance
                if matrixofdistances[i][j] < mindistance and matrixofdistances[i][j] != 0: 
                    powerthrust1 = listofpokes[indexofi] 
                    mindistance = matrixofdistances[i][j] 
                     
                    findeliteindexi = i # Preparing to organize the visual
                    powerthrust2 = listofpokes[indexofj]
                    findeliteindexj = j 
                if j == (zeta-1) and i == (zeta-1): 
                    
                    if matrixofdistances.size-2 == 2:
                        findeliteindexi = 0
                        findeliteindexj = 1
                        #Updates the distance in the matrix if the size is 4
                        matrixofdistances = updateDistance(findeliteindexi, matrixofdistances, findeliteindexj)
                    else:
                        toAdd = updateDistance(findeliteindexi, matrixofdistances,  findeliteindexj) 
                        matrixofdistances = np.delete(matrixofdistances, findeliteindexj, 1) 
                        matrixofdistances = np.delete(matrixofdistances, findeliteindexj, 0)
                        matrixofdistances[findeliteindexi] = toAdd
                        matrixofdistances[:, findeliteindexi] = toAdd
                    if trackclusters[findeliteindexi] > trackclusters[findeliteindexj]:
                        listofallpokesin[indexofzed][1] = trackclusters[findeliteindexi]
                        listofallpokesin[indexofzed][0] = trackclusters[findeliteindexj]
                        
                    else:
                        listofallpokesin[indexofzed][0] = trackclusters[findeliteindexi]
                        listofallpokesin[indexofzed][1] = trackclusters[findeliteindexj] 
                    currentnumberin2 = trackclusters[findeliteindexj] 
                    currentnumberin = trackclusters[findeliteindexi] 
                    listofallpokesin[indexofzed][2] = mindistance
                    somenumbernamed1 = numberofpokes[currentnumberin] + numberofpokes[currentnumberin2] 
                    numberofpokes.append(somenumbernamed1) 
                    listofallpokesin[indexofzed][3] = somenumbernamed1
                    trackclusters[findeliteindexi] = clusterofallpokes
                    clusterofallpokes += 1
                    deletethisnumber = trackclusters[findeliteindexj]
                    trackclusters.remove(deletethisnumber)
                    indexofzed+=1
                    break
            indexofi += 1
            indexofj += 1
    return listofallpokesin


def imshow_hac(Z):
    dendrogram(Z,leaf_font_size=8.,leaf_rotation=90.,)
    plt.show()


