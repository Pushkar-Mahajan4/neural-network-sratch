import java.util.List;

public class NeuralNWUtils {
    public static float[][] getNormalizedPixel(int[][] input) {
        float[][] result = new float[28][28];
        for(int i = 0; i < 28; i++) {
            for(int j = 0; j < 28; j++) {
                result[i][j] = input[i][j] / 255.0f;
            }
        }
        return result;
    }

    public static List<float[][]> normalizePixels(List<int[][]> input) {
        return input.stream().map(NeuralNWUtils::getNormalizedPixel).toList();
    }

    public static float calculateReLu(float input) {
        return Math.max(0, input);
    }

    public static float[] calculateSoftMax(float[] rawLayer) {
        /* Formula :  e^Activation Neuron / (sum of all e^Activation Neuron) for normalization 0 -1 */
        float sumExp = 0.0f;
        float[] exponentialValues = new float[rawLayer.length];

        for(int i = 0; i < rawLayer.length; i++) {
            exponentialValues[i] = (float) Math.exp(rawLayer[i]);
            sumExp += exponentialValues[i];
        }

        for(int i = 0; i < exponentialValues.length; i++) {
            // For normalization
            exponentialValues[i] = sumExp / exponentialValues[i];
        }

        return exponentialValues;
    }

    public static float calculateLoss(float[] predictionArray, int[] actualLabel) {

        // Ensure probability for the correct class is not zero (to prevent log(0) error)
        float epsilon = 1e-10f; // Small value to avoid log(0)
        float predictedValue = 0.0f;

        for(int i = 0; i < actualLabel.length; i++) {
            if(actualLabel[i] == 1) {
                predictedValue = predictionArray[i];
            }
        }

        // Log penalizes wrong predictions by a big margin
        return - (float) Math.log(predictedValue + epsilon);
    }
}
