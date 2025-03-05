
### One hot encoding Labels

One-hot encoding is a way of representing categorical labels as numerical arrays. In the **MNIST dataset**, where the labels (digits) range from 0 to 9, we use one-hot encoding to convert each digit into a vector with **10 elements**, where only **one** element is **1** (hot), and the rest are **0** (cold).

#### Why do we need one-hot encoding for labels?

1. **Machine learning models work better with numbers** – Many models, especially neural networks, perform better when labels are represented as numerical arrays rather than raw numbers.
    
2. **Avoiding misleading relationships** – If we keep the labels as just numbers (0-9), the model might assume that **9 is greater than 2**, which is not true in classification problems.
    
3. **Softmax activation compatibility** – In neural networks, we often use a softmax function in the last layer for classification, which outputs a probability distribution. The one-hot encoding helps compare the model's predicted probabilities with the true class in a clear way.
    

#### Example:

- Label `3` → One-hot encoded as `[0, 0, 0, 1, 0, 0, 0, 0, 0, 0]`
- Label `7` → One-hot encoded as `[0, 0, 0, 0, 0, 0, 0, 1, 0, 0]`

This helps the model learn which category a digit belongs to without assuming any numerical relationship between labels.

### Weights and Biases

##### Why weights?

Weights determine the strength of connection b/w two neurons
- If a weight is **large**, it gives more importance to a feature.
- If a weight is **small**, it reduces the importance of that feature.

##### Why Initialize Weights with a Gaussian Distribution Centered Around 0?

When initializing weights, we **don't** want them to be too large or too small. A **Gaussian (normal) distribution centered around 0** helps with:

1. **Avoiding Symmetry** – If all weights are the same, all neurons in a layer will learn the same thing, making learning inefficient. Random initialization breaks this symmetry.
    
2. **Stable Gradients** – If weights are too large, activations can explode, and gradients become unstable (vanishing or exploding gradient problem). A normal distribution with small values keeps gradients in a useful range.
    
3. **Faster Convergence** – Proper initialization allows the model to learn faster by ensuring a good starting point.

##### Why biases?

These are additional parameters that allow the model to shift the activation function, helping it learn better patterns.

- Even if all input values are zero, bias ensures that neurons can still activate.
- It prevents the model from being too dependent on the input alone.

Setting biases to 0 prevents the model from being biased towards certain digits. It's literally called bias for a reason. We want to give the neural network a neutral start.

