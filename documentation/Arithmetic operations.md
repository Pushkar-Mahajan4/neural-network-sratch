
#### 1. Why float to hold normalized pixel values?

- **Precision for Calculations**:
    - Neural networks rely heavily on operations like matrix multiplications, dot products, and gradient updates, which often produce fractional values.
    - Storing the input as `float` ensures that the precision required for these operations is preserved.

- **Normalization Needs Fractional Values**:
    - When you normalize pixel values to a range of 0–10–10–1, the result is typically a fractional number (e.g., 127/255=0.498127 / 255 = 0.498127/255=0.498).
    - If you store this as an integer, you lose the fractional part, which defeats the purpose of normalization.

- **Compatibility with Frameworks**:
    - Most neural network libraries (e.g., TensorFlow, PyTorch, Keras) expect input data in `float32` or `float64` format.
    - Using integers would require additional conversion during training, which can reduce performance and increase memory overhead.

- **Gradient-Based Learning**:
    - Neural networks learn using gradient-based optimization methods (e.g., SGD, Adam). These methods require precise calculations involving small differences in weights and inputs.
    - Using integers would limit the precision of these calculations, potentially affecting the learning process.

- **Generalization Across Layers**:
    - Activation functions like ReLU, sigmoid, or tanh inherently operate on floating-point values, as do weight matrices in fully connected or convolutional layers.