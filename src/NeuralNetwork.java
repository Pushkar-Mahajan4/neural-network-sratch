import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NeuralNetwork {

    public NeuralNetwork(List<float[][]> trainImages, List<int[]> encodedLabels) {
        /*
            Input Layer (784 neurons) → Each image is 28 × 28 = 784 pixels (flattened into a single vector).
            Hidden Layer (128 neurons) → A fully connected layer with 128 neurons.
            Output Layer (10 neurons) → Predicts a digit (0-9) using softmax activation.

            Where Do Weights and Biases Come In?
            Every time data moves from one layer to the next,
            it passes through weights and biases before reaching the activation function. The flow looks like this:
        */
        List<float[]> flattenedImages = flattenImages(trainImages);
        float[][] weightsOne = initializeWeightsOne();
        float[] biasOne = new float[128];
        float[][] weightsTwo = initializeWeightsTwo();
        float[] biasTwo = new float[10];

        System.out.println("Initiating Network training");
        trainNetwork(flattenedImages, encodedLabels, weightsOne, biasOne, weightsTwo, biasTwo);


    }

    public void trainNetwork(List<float[]> flattenedImages,
                                         List<int[]> encodedLabels,
                                         float[][] weightsOne,
                                         float[] biasOne,
                                         float[][] weightsTwo,
                                         float[] biasTwo) {
        /* Formula to calculate value for each neuron in hidden layer
        * Hj = {1 to 784}Summation(Input(i) x Weight(i, j) + Bias(j)) */
        float[] hiddenLayerNeurons = new float[128];

        // For each train image
        int counter = 0;
        for(float[] image : flattenedImages) {
            System.out.println("Counter : " + counter);
            hiddenLayerNeurons = matrixMultiplication(image, weightsOne, biasOne); // Calculate hidden node values

            // This array[128] should now be used to predict a value from [0-9]
            float[] propagatedOutput = calculateOutput(hiddenLayerNeurons, weightsTwo, biasTwo);

            // Calculate loss
            float loss = NeuralNWUtils.calculateLoss(propagatedOutput, encodedLabels.get(counter));

            // Backpropagation for weight adjustment
            backPropagation(
                    image,
                    hiddenLayerNeurons,
                    propagatedOutput,
                    encodedLabels.get(counter),
                    weightsOne,
                    biasOne,
                    weightsTwo,
                    biasTwo);

            counter++;
        }

    }

    public static void backPropagation(float[] input,               // Flattened image (1x784)
                                       float[] hiddenOutput,        // Output of hidden layer (1x128)
                                       float[] finalOutput,         // Softmax output (1x10)
                                       int[] actualLabel,         // One-hot encoded label (1x10)
                                       float[][] weightsOne,        // Weights (784x128)
                                       float[] biasesOne,           // Biases (128)
                                       float[][] weightsTwo,        // Weights (128x10)
                                       float[] biasesTwo) {           // Biases (10)
        // Step 1: Compute Output Layer Error (delta_output)
        System.out.println("Calculating Delta");
        float[] deltaOutput = new float[10];
        for (int k = 0; k < 10; k++) {
            deltaOutput[k] = finalOutput[k] - actualLabel[k]; // Softmax Derivative
        }

        // Step 2: Compute Hidden Layer Error (delta_hidden)
        float[] deltaHidden = new float[128];
        for (int j = 0; j < 128; j++) {
            float errorSum = 0;
            for (int k = 0; k < 10; k++) {
                errorSum += weightsTwo[j][k] * deltaOutput[k];
            }
            // ReLU derivative: 1 if hiddenOutput[j] > 0, else 0
            deltaHidden[j] = (hiddenOutput[j] > 0) ? errorSum : 0;
        }

        // Step 3: Update Weights & Biases (Output Layer)
        System.out.println("Adjusting weights and biases");
        float learningRate = 0.01f;
        for (int j = 0; j < 128; j++) {
            for (int k = 0; k < 10; k++) {
                weightsTwo[j][k] -= learningRate * hiddenOutput[j] * deltaOutput[k]; // Gradient Descent
            }
        }
        for (int k = 0; k < 10; k++) {
            biasesTwo[k] -= learningRate * deltaOutput[k]; // Update biases for output layer
        }

        // Step 4: Update Weights & Biases (Hidden Layer)
        for (int i = 0; i < 784; i++) {
            for (int j = 0; j < 128; j++) {
                weightsOne[i][j] -= learningRate * input[i] * deltaHidden[j]; // Gradient Descent
            }
        }
        for (int j = 0; j < 128; j++) {
            biasesOne[j] -= learningRate * deltaHidden[j]; // Update biases for hidden layer
        }
    }

    public static float[] calculateOutput(float[] hiddenLayer, float[][] weightsTwo, float[] biasTwo) {
        float[] preActivationLayer = new float[10];
        for(int i = 0; i < 10; i++) {
            float sum = 0.0f;
            for(int j = 0; j < 128; j++) {
                sum += hiddenLayer[j] * weightsTwo[j][i];
            }
            sum += biasTwo[i];
            preActivationLayer[i] = sum;
        }

        return NeuralNWUtils.calculateSoftMax(preActivationLayer);
    }

    public static float[] matrixMultiplication(float[] image, float[][] weights, float[] bias) {
        float[] result = new float[128];
        for(int i = 0; i < 128; i++) {
            float mul = 0.0f;
            for(int j = 0; j < 784; j++) {
                // Each pixel of single image x weight per neuron
                mul = image[j] * weights[j][i];
            }
            result[i] = NeuralNWUtils.calculateReLu(mul + bias[i]);
        }

        return result;
    }

    public List<float[]> flattenImages(List<float[][]> trainImages) {

        /*
          Think of a dense layer as a spreadsheet where each pixel is a separate column.
          A 1D vector (flattened) is like having 784 columns, each storing one pixel value.
          A 2D matrix (28 × 28) is like a table with rows and columns, which dense layers can't process directly.
          A CNN can handle spatial data aka (2D - Matrix). But is overkill for MNIST.
        */
        List<float[]> resultImages = new ArrayList<>();

        for(float[][] image : trainImages) {
            float[] flatImage = new float[image.length * image[0].length];
            System.out.println("single image size : " + flatImage.length);
            int counter = 0;
            for(int i = 0; i < image.length; i++) {
                for(int j = 0; j < image[0].length; j ++) {
                    flatImage[counter] = image[i][j];
                }
            }
            resultImages.add(flatImage);
        }

        return resultImages;
    }

    public float[][] initializeWeightsOne() {
        /* Weights Dimensions : Size of previous layer x current layer
        *  Since these weights sit between input layer(784 pixels) and hidden layer(128) */
        float[][] weightsOne = new float[784][128];
        float stdDev = (float) Math.sqrt(2.0 / 784); // He  initialization suitable for ReLu
        Random random = new Random();
        for(int i = 0; i < 784; i++) {
            for(int j = 0; j < 128; j++) {
                weightsOne[i][j] = (float) (random.nextGaussian() * stdDev);
            }
        }

        return weightsOne;

    }

    public float[][] initializeWeightsTwo() {
        float[][] weightsTwo = new float[128][10];
        float stdDev = (float) Math.sqrt(1.0 / 128); // Xavier initialization for Sigmoid
        Random random = new Random();

        for(int i = 0; i < 128; i++) {
            for(int j = 0; j < 10; j++) {
                weightsTwo[i][j] = (float) (random.nextGaussian() * stdDev);
            }
        }

        return weightsTwo;
    }

}
