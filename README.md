# MNIST Digit Recognition Neural Network

## 🎯 Project Overview
A pure Java implementation of a neural network for recognizing handwritten digits from the MNIST dataset. This project was built from scratch without relying on any machine learning libraries to deepen understanding of neural network fundamentals.

## 🧠 Neural Network Architecture
The network consists of three layers:
- **Input Layer (784 neurons)**: Processes flattened 28×28 pixel images
- **Hidden Layer (128 neurons)**: Fully connected layer with ReLU activation
- **Output Layer (10 neurons)**: Produces predictions using softmax activation

## 🔑 Key Features
- **Pure Java Implementation**: Built without external ML libraries
- **MNIST Dataset Support**: Handles the standard MNIST dataset format
- **Xavier/Glorot Initialization**: Smart weight initialization for better training
- **Backpropagation**: Implements complete gradient descent learning
- **Real-time Training Metrics**: Monitors accuracy during training
- **Validation**: Tests network performance every 5 epochs

## 📊 Technical Details
- **Activation Functions**:
    - Hidden Layer: ReLU (Rectified Linear Unit)
    - Output Layer: Softmax
- **Learning Parameters**:
    - Learning Rate: 0.01
    - Epochs: 10
    - Batch Size: 1 (Stochastic Gradient Descent)

## 🚀 Getting Started

### Prerequisites
- Java JDK 21
- MNIST dataset files:
    - `train-images.idx3-ubyte`
    - `train-labels.idx1-ubyte`
    - `t10k-images.idx3-ubyte`
    - `t10k-labels.idx1-ubyte`

### Dataset Structure
Place the MNIST dataset files in the `dataset/` directory: 


## Running the Project

Run these two commands:
``` 
javac src/.java
```

```
java src/Main
```

## 📈 Performance
The network typically achieves:
- Training Accuracy: ~90-95%
- Test Accuracy: ~90%

#### Screenshot
  ![image](https://github.com/user-attachments/assets/0984e1a0-531f-4bce-a8f5-b2e4f7d7d470)