# Dataset README

### Introduction

The dataset for this project is MNIST dataset. It is a dataset that contains handwritten digits [0 - 9]. 

The dataset contains two main files:
1. Images File(train-images-idx3-ubyte / t10k-images-idx3-ubyte)
2. Labels File(train-labels-idx1-ubyte / t10k-labels-idx1-ubyte)


### IDX File Structure
Each IDX file follows a simple binary structure:
- First few bytes contain metadata (headers) describing the data format.
- Remaining bytes store the actual image or label data.

#### Images File Structure (IDX3)
Each image is a 28x28 grayscale image, stored as unsigned 8-bit integers (0-255).
The format:

| Offset (Bytes) | 	Description                |
|----------------|--------------------------------|
| 0 - 3          | Magic Number (2051 for images) |
| 4 - 7          | Number of images               |
| 8 - 11         | Number of rows (28)            |
| 12 - 15        | Number of columns (28)         |
| 16 - ...       | Pixel values (0-255)           |
Each pixel is stored in row-major order, meaning the first 28 pixels belong to row 1, the next 28 belong to row 2, and so on.

#### Labels File Structure (IDX1)
Each label is a single integer (0-9), stored as unsigned 8-bit integers.
The format:

| Offset (Bytes)    | Description |
| -------- | ------- |
| 0 - 3  | Magic Number (2049 for labels)   |
| 4 - 7 | Number of labels     |
| 8 - ...    | Labels (0-9)    |