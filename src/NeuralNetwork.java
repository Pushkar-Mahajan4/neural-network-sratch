import java.util.ArrayList;
import java.util.List;

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
}
