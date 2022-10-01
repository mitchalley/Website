import sys
import math
import string
import numpy

probabilityEnglish = 0.6
probabilitySpanish = 0.4
def get_parameter_vectors():
    '''
    This function parses e.txt and s.txt to get the  26-dimensional multinomial
    parameter vector (characters probabilities of English and Spanish) as
    descibed in section 1.2 of the writeup

    Returns: tuple of vectors e and s
    '''
    #Implementing vectors e,s as lists (arrays) of length 26
    #with p[0] being the probability of 'A' and so on
    e=[0]*26
    s=[0]*26
    
    with open('e.txt',encoding='utf-8') as f:
        for line in f:
            #strip: removes the newline character
            #split: split the string on space character
            char,prob=line.strip().split(" ")
            #ord('E') gives the ASCII (integer) value of character 'E'
            #we then subtract it from 'A' to give array index
            #This way 'A' gets index 0 and 'Z' gets index 25.
            e[ord(char)-ord('A')]=float(prob)
    f.close()

    with open('s.txt',encoding='utf-8') as f:
        for line in f:
            char,prob=line.strip().split(" ")
            s[ord(char)-ord('A')]=float(prob)
    f.close()

    return (e,s)


def shred(filename):
    #Using a dictionary here. You may change this to any data structure of
    #your choice such as lists (X=[]) etc. for the assignment
    
    d = {}
    for char in string.ascii_uppercase:
        d[char] = 0
    with open(filename, encoding='utf-8') as f:
        for line in f:
            for char in line:
                char = char.upper()
                if char in string.ascii_uppercase:
                    if char in d:
                        d[char.upper()] += 1
                    else:
                        d[char.upper()] = 1
        
    f.close()
    return d
# TODO: add your code here for the assignment
# You are free to implement it as you wish!
# Happy Coding!

def printQ1(filename):
    print('Q1')
    ASCIIChars = string.ascii_uppercase
    numbers = shred(filename)
    for letter in numbers:
        letterFrequency = numbers.get(letter)
        print(letter, letterFrequency)


def printQ2(filename):
    numbers = shred(filename)
    englSpanProbs = get_parameter_vectors()
    Q2e = "{:.4f}".format(numbers.get('A') * numpy.log(englSpanProbs[0][0]))
    Q2s = "{:.4f}".format(numbers.get('A') * numpy.log(englSpanProbs[1][0]))
    print('Q2')
    print(Q2e)
    print(Q2s)


def printQ3(filename):
    fEnglish = "{:.4f}".format(computeFEnglish(filename))
    fSpanish = "{:.4f}".format(computeFSpanish(filename))
    print('Q3')
    print(fEnglish)
    print(fSpanish)


def printQ4(filename):
    final = "{:.4f}".format(finalComputation(filename))
    print('Q4')
    print(final)


def printAll(filename):
    printQ1(filename)
    printQ2(filename)
    printQ3(filename)
    printQ4(filename)


def computeFEnglish(filename):
    fEnglish = numpy.log(probabilityEnglish)
    ASCIIChars = string.ascii_uppercase
    numbers = shred(filename)
    englSpanProbs = get_parameter_vectors()

    index = 0
    summation = 0
    for letter in numbers:
        letterFrequency = numbers.get(letter)
        letterProbability = englSpanProbs[0][index]
        index += 1
        summation += letterFrequency * numpy.log(letterProbability)
    returnVal = fEnglish + summation
    return returnVal


def computeFSpanish(filename):
    fSpanish = numpy.log(probabilitySpanish)
    ASCIIChars = string.ascii_uppercase
    numbers = shred(filename)
    englSpanProbs = get_parameter_vectors()

    index = 0
    summation = 0
    for letter in numbers:
        letterFrequency = numbers.get(letter)
        letterProbability = englSpanProbs[1][index]
        index += 1
        summation += letterFrequency * numpy.log(letterProbability)
    returnVal = fSpanish + summation
    return returnVal


def finalComputation(filename):
    determinant = computeFSpanish(filename) - computeFEnglish(filename)
    if determinant <= -100:
        return 1
    elif determinant >= 100:
        return 0
    else:
        return 1/(1 + (math.e ** (determinant)))


if __name__ == "__main__":
    printAll('letter.txt')