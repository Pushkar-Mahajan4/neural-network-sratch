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
}
