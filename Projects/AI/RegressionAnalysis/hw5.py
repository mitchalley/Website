#!/usr/bin/env python
# coding: utf-8

# In[147]:


import pandas as pd
import csv
import matplotlib.pyplot as plt
import numpy as np
import bs4 as bs
import urllib.request
import sys
from matplotlib.ticker import FormatStrFormatter

if __name__ == '__main__':
    
    sys.argv[1] # Gets the input/file
    df = pd.read_csv(sys.argv[1]) # Reading the file
    for i in range(len(df)):
        df['days'][i] = int(df['days'][i]) # Setting the values based on the file and creating a graph
    fig, ax = plt.subplots()
    ax.plot(df['year'], df['days'])
    ax.set_xlabel('Year')
    ax.set_ylabel('Number of frozen days')
    plt.yticks(np.arange(30, 180, 30))
    plt.savefig('plot.png')
    
    #Part 3
    X = []
    for i in df['year']:
        X.append([1, i])
    X = np.array(X) # Values in the file
    Y = np.array(df['days']) # Vector of corresponding Yi values

    Z = np.matmul(np.transpose(X), X) # Matrix product of Z=X^TX
    I = np.linalg.inv(Z) # Inverse of Z=X^TX
    PI = np.matmul(I, np.transpose(X)) # Psudo-inverse (PI=(X^(T)*X)^(-1)X^T)
    hat_beta = np.matmul(PI, Y) # Computing Beta for the regression ((PI=(X^(T)*X)^(-1)X^(T)Y)
    print('Q3a:')
    print(X)
    print('Q3b:')
    print(Y)
    print('Q3c:')
    print(Z)
    print('Q3d:')
    print(I)
    print('Q3e:')
    print(PI)
    print('Q3f:')
    print(hat_beta)

    #Part 4
    beta_zero = hat_beta[0]
    beta_one = hat_beta[1]
    x_test = 2021
    y_test = beta_zero + beta_one * x_test
    print('Q4: ' + str(y_test)) # Printing out the regression as a function y_test = B0 + B1xtest

    #Part 5
    if beta_one > 0: # Indicating the sign of Beta
        print('Q5a: >')
    elif beta_one < 0:
        print('Q5a: <')
    else:
        print('Q5a: =')

    print('Q5b: This means that on average, as the year increases, the amount of days that the ice is frozen decreases by', beta_one)

    #Part 6 - justification
    neg_beta_zero = 0 - beta_zero
    xstar = neg_beta_zero / beta_one
    print('Q6a:', xstar)
    print('Q6a: This is a compelling prediction because the beta_one value is very small, therefore, it will take an extreme amount of time (roughly 400 years) before lake mendota does not freeze over')



# In[ ]:




