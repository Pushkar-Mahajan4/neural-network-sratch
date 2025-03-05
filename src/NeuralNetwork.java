import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NeuralNetwork {
    /*
            Input Layer (784 neurons) → Each image is 28 × 28 = 784 pixels (flattened into a single vector).
            Hidden Layer (128 neurons) → A fully connected layer with 128 neurons.
            Output Layer (10 neurons) → Predicts a digit (0-9) using softmax activation.

            Where Do Weights and Biases Come In?
            Every time data moves from one layer to the next,
            it passes through weights and biases before reaching the activation function. The flow looks like this:
        */

    private float[][] weightsInputHidden;
    private float[][] weightsHiddenOutput;
    private float[] biasHidden;
    private float[] biasOutput;
    private static final int HIDDEN_NODES = 128;
    private static final int OUTPUT_NODES = 10;
    private static final float LEARNING_RATE = 0.01f;
    private static final int EPOCHS = 10;

    public NeuralNetwork(List<float[][]> trainImages, List<int[]> trainLabels,
            List<float[][]> testImages, List<int[]> testLabels) {
        int inputNodes = 784; // 28x28 pixels

        // Initialize weights with Xavier/Glorot initialization
        Random random = new Random();
        double weightScale = Math.sqrt(2.0 / (inputNodes + HIDDEN_NODES));

        weightsInputHidden = new float[inputNodes][HIDDEN_NODES];
        weightsHiddenOutput = new float[HIDDEN_NODES][OUTPUT_NODES];
        biasHidden = new float[HIDDEN_NODES];
        biasOutput = new float[OUTPUT_NODES];

        // Initialize weights and biases
        for (int i = 0; i < inputNodes; i++) {
            for (int j = 0; j < HIDDEN_NODES; j++) {
                weightsInputHidden[i][j] = (float) (random.nextGaussian() * weightScale);
            }
        }

        weightScale = Math.sqrt(2.0 / (HIDDEN_NODES + OUTPUT_NODES));
        for (int i = 0; i < HIDDEN_NODES; i++) {
            for (int j = 0; j < OUTPUT_NODES; j++) {
                weightsHiddenOutput[i][j] = (float) (random.nextGaussian() * weightScale);
            }
        }

        // Training loop
        for (int epoch = 0; epoch < EPOCHS; epoch++) {
            int correct = 0;
            for (int i = 0; i < trainImages.size(); i++) {
                float[] flattenedInput = flattenImage(trainImages.get(i));
                float[] targetOutput = NeuralNWUtils.convertToFloat(trainLabels.get(i));

                // Forward pass
                float[] hiddenLayer = forwardHidden(flattenedInput);
                float[] outputLayer = forwardOutput(hiddenLayer);

                // Backward pass
                backPropagate(flattenedInput, hiddenLayer, outputLayer, targetOutput);

                // Calculate accuracy
                if (NeuralNWUtils.getMaxIndex(outputLayer) == NeuralNWUtils.getMaxIndex(targetOutput)) {
                    correct++;
                }
            }

            float accuracy = (float) correct / trainImages.size() * 100;
            System.out.printf("Epoch %d: Training Accuracy = %.2f%%\n", epoch + 1, accuracy);

            // Evaluate on test set on every 5th epoch
            if ((epoch + 1) % 5 == 0) {
                evaluateTestSet(testImages, testLabels);
            }
        }
    }

    private float[] flattenImage(float[][] image) {
        float[] flattened = new float[784];
        int index = 0;
        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 28; j++) {
                flattened[index++] = image[i][j];
            }
        }
        return flattened;
    }

    private float[] forwardHidden(float[] input) {
        float[] hidden = new float[HIDDEN_NODES];
        for (int i = 0; i < HIDDEN_NODES; i++) {
            float sum = biasHidden[i];
            for (int j = 0; j < input.length; j++) {
                sum += input[j] * weightsInputHidden[j][i];
            }
            hidden[i] = relu(sum);
        }
        return hidden;
    }

    private float[] forwardOutput(float[] hidden) {
        float[] output = new float[OUTPUT_NODES];
        for (int i = 0; i < OUTPUT_NODES; i++) {
            float sum = biasOutput[i];
            for (int j = 0; j < HIDDEN_NODES; j++) {
                sum += hidden[j] * weightsHiddenOutput[j][i];
            }
            output[i] = sum;
        }
        return softmax(output);
    }

    private void backPropagate(float[] input, float[] hidden, float[] output, float[] target) {
        // Output layer error
        float[] outputError = new float[OUTPUT_NODES];
        for (int i = 0; i < OUTPUT_NODES; i++) {
            outputError[i] = output[i] - target[i];
        }

        // Hidden layer error
        float[] hiddenError = new float[HIDDEN_NODES];
        for (int i = 0; i < HIDDEN_NODES; i++) {
            float error = 0;
            for (int j = 0; j < OUTPUT_NODES; j++) {
                error += outputError[j] * weightsHiddenOutput[i][j];
            }
            hiddenError[i] = error * reluDerivative(hidden[i]);
        }

        // Update weights and biases
        updateWeights(input, hidden, outputError, hiddenError);
    }

    private void updateWeights(float[] input, float[] hidden, float[] outputError, float[] hiddenError) {
        // Update hidden-output weights
        for (int i = 0; i < HIDDEN_NODES; i++) {
            for (int j = 0; j < OUTPUT_NODES; j++) {
                weightsHiddenOutput[i][j] -= LEARNING_RATE * hidden[i] * outputError[j];
            }
        }

        // Update input-hidden weights
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < HIDDEN_NODES; j++) {
                weightsInputHidden[i][j] -= LEARNING_RATE * input[i] * hiddenError[j];
            }
        }

        // Update biases
        for (int i = 0; i < OUTPUT_NODES; i++) {
            biasOutput[i] -= LEARNING_RATE * outputError[i];
        }
        for (int i = 0; i < HIDDEN_NODES; i++) {
            biasHidden[i] -= LEARNING_RATE * hiddenError[i];
        }
    }

    private float relu(float x) {
        return Math.max(0, x);
    }

    private float reluDerivative(float x) {
        return x > 0 ? 1 : 0;
    }

    private float[] softmax(float[] input) {
        float[] output = new float[input.length];
        float max = Float.NEGATIVE_INFINITY;
        for (float value : input) {
            max = Math.max(max, value);
        }

        float sum = 0;
        for (int i = 0; i < input.length; i++) {
            output[i] = (float) Math.exp(input[i] - max);
            sum += output[i];
        }

        for (int i = 0; i < output.length; i++) {
            output[i] /= sum;
        }
        return output;
    }



    private void evaluateTestSet(List<float[][]> testImages, List<int[]> testLabels) {
        int correct = 0;
        for (int i = 0; i < testImages.size(); i++) {
            float[] input = flattenImage(testImages.get(i));
            float[] hidden = forwardHidden(input);
            float[] output = forwardOutput(hidden);
            float[] target = NeuralNWUtils.convertToFloat(testLabels.get(i));

            if (NeuralNWUtils.getMaxIndex(output) == NeuralNWUtils.getMaxIndex(target)) {
                correct++;
            }
        }
        float accuracy = (float) correct / testImages.size() * 100;
        System.out.printf("Test Accuracy: %.2f%%\n", accuracy);
    }
}
