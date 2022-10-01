import numpy as np
import torch
import torch.nn as nn
import torch.nn.functional as F
import torch.optim as optim
from torchvision import datasets, transforms

# Feel free to import other packages, if needed.
# As long as they are supported by CSL machines.

#INPUT: An optional boolean argument (default value is True for training dataset)
#RETURNS: Dataloader for the training set (if training = True) or the test set (if training = False)
def get_data_loader(training = True):
    custom_transform= transforms.Compose([transforms.ToTensor(),transforms.Normalize((0.1307,), (0.3081,))])
    
    # Customizing the data loader
    train_set=datasets.FashionMNIST('./data',train=True,download=True,transform=custom_transform)

    test_set=datasets.FashionMNIST('./data', train=False,
            transform=custom_transform)

    # If training is false, do not shuffle the images, otherwise, shuffle
    if training == True:
        return torch.utils.data.DataLoader(train_set ,batch_size = 64, shuffle = True)
    elif training == False:
        return torch.utils.data.DataLoader(test_set, batch_size = 64, shuffle = False)
    else:
        return torch.utils.data.DataLoader(train_set ,batch_size = 64, shuffle = True)

    transform=transforms.Compose([
        transforms.ToTensor(),
        transforms.Normalize((0.1307,), (0.3081,))
        ])

#    RETURNS: An untrained neural network model
def build_model():
    
    model = nn.Sequential(nn.Flatten(), nn.Linear(784, 128), nn.ReLU(), nn.Linear(128, 64), nn.ReLU(), nn.Linear(64, 10))
    return model

#     INPUT: 
#        model - the model produced by the previous function
#        train_loader  - the train DataLoader produced by the first function
#        criterion   - cross-entropy 
#        T - number of epochs for training
#     RETURNS:
#        None
#
def train_model(model, train_loader, criterion, T):
    
    criterion = nn.CrossEntropyLoss()
    optimizer = optim.SGD(model.parameters(), lr=0.001, momentum=0.9)
    
    # Training the loader on each image in the training set
    trainloader = get_data_loader(True)
    model.train()
    for epoch in range(T):
        running_loss = 0.0 # Records the total incorrect
        correct = 0 # Records the total correct
        total = 0 # Records the total attempts
        for i, data in enumerate(trainloader, 0):

            inputs, labels = data
            optimizer.zero_grad()

            outputs = model(inputs)
            _, predicted = torch.max(outputs.data, 1)
            loss = criterion(outputs, labels)
            loss.backward()
            optimizer.step()

            running_loss += loss.item() * 64
            total += labels.size(0)
            correct += (predicted == labels).sum().item()
        accperc = correct / total # Getting the percentage of correct
        accpercstr = '({:.2f}%)'.format(accperc * 100)
        accracystr = str(correct) + '/' + str(total) # Prints the result
        print('Train Epoch:', epoch, 'Accuracy:', accracystr, accpercstr, 'Loss: {:.3f}'.format(running_loss / len(trainloader.dataset)))
        

    
#     INPUT: 
#         model - the the trained model produced by the previous function
#         test_loader    - the test DataLoader
#         criterion   - cropy-entropy 

#     RETURNS:
#         None

def evaluate_model(model, test_loader, criterion, show_loss = True):
    
    model.eval()
    correct = 0
    total = 0

    with torch.no_grad():
        running_loss = 0.0
        for data in test_loader:
            images, labels = data
            outputs = model(images)

            _, predicted = torch.max(outputs.data, 1)
            loss = criterion(outputs, labels)
            #loss.backward()
            running_loss += loss.item() * 64
            total += labels.size(0)
            correct += (predicted == labels).sum().item()
        if show_loss == True:
            print('Average loss: {:.4f}'.format(running_loss / len(test_loader.dataset)))
            print('Accuracy: {:.2f}%'.format((correct/total)*100))
        else:
            print('Accuracy: {:.2f}%'.format((correct/total)*100))
    
    
#     INPUT: 
#         model - the trained model
#         test_images   -  test image set of shape Nx1x28x28
#         index   -  specific index  i of the image to be tested: 0 <= i <= N - 1


#     RETURNS:
#         None

def predict_label(model, test_images, index):
    class_names = ['T-shirt/top','Trouser','Pullover','Dress','Coat','Sandal','Shirt','Sneaker','Bag','Ankle Boot']
    logits = model(test_images[index])
    prob = F.softmax(logits, dim=index)
    probabilities, indicies = torch.topk(prob, 3)
    print(class_names[indicies[0][0]] + ': {:.2f}%'.format(probabilities[0][0] * 100))
    print(class_names[indicies[0][1]] + ': {:.2f}%'.format(probabilities[0][1] * 100))
    print(class_names[indicies[0][2]] + ': {:.2f}%'.format(probabilities[0][2] * 100))


    #prob_list = prob.tolist()
    #print(prob_list)




if __name__ == '__main__':
# Write test code
    
