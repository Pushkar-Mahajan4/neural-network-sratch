import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NeuralNetwork {

    public NeuralNetwork(List<float[][]> trainImages, List<Integer> trainLabels) {
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

    }

    public float[] forwardPropagateLayerOne(List<float[]> flattenedImages,
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
            hiddenLayerNeurons = matrixMultiplication(image, weightsOne, biasOne); // Calculate hidden node values

            // This array[128] should now be used to predict a value from [0-9]
            float[] propagatedOutput = calculateOutput(hiddenLayerNeurons, weightsTwo, biasTwo);

            // Calculate loss
            float loss = NeuralNWUtils.calculateLoss(propagatedOutput, encodedLabels.get(counter));
            counter++;
        }


        return hiddenLayerNeurons;
    }

    public static float[] calculateOutput(float[] hiddenLayer, float[][] weightsTwo, float[] biasTwo) {
        float[] preActivationLayer = new float[10];
        for(int i = 0; i < 128; i++) {
            float sum = 0.0f;
            for(int j = 0; j < 10; j++) {
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
            int counter = 0;
            for(int i = 0; i < image.length; i++) {
                for(int j = 0; j < image[0].length; j ++) {
                    flatImage[counter] = image[i][j];
                }
            }
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
