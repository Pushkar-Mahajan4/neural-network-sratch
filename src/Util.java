import java.util.ArrayList;
import java.util.List;

public class Util {
    public static int getIntFromBytes(List<Byte> byteList){
        return (byteList.get(0) & 0xFF) << 24 |
                (byteList.get(1) & 0xFF) << 16 |
                (byteList.get(2) & 0xFF) << 8 |
                (byteList.get(3) & 0xFF);
    }

    public static void assertRowsAndColumns(int rows, int columns) throws AssertionError {
        assert rows == columns && rows == 28;
    }

    public static void assertNumberOfTrainImages(int imageCount) throws AssertionError {
        assert imageCount == 60000;
    }

    public static int[][] extractImagePixels(List<Byte> input, int offset) {
        int[][] result = new int[28][28];
        for(int i = 0; i < 28; i++) {
            for(int j = 0; j < 28; j++) {
                result[i][j] = input.get(offset) & 0xFF;
                offset++;
            }
        }

        return result;
    }

    public static List<Integer> extractLabels(List<Byte> input, int offset) {
        List<Integer> labelsList = new ArrayList<>();
        for(int i = offset; i < input.size(); i++) {
            labelsList.add(input.get(i) & 0xFF);
        }
        return labelsList;
    }

    public static List<int[][]> getEachImage(List<Byte> input) {
        List<int[][]> result = new ArrayList<>();
        int imageStartIndex = 16;
        while(imageStartIndex + 784 <= input.size()) {
            // 28 x 28 = 784 pixels, imageIndex to keep track of number of images
            result.add(extractImagePixels(input, imageStartIndex));
            imageStartIndex += 784;
        }

        return result;
    }

    public static List<int[]> encodeLabels(List<Integer> labelData) {
        /* One hot encode label data for Softmax activation compatibility
        *  In neural networks, we often use a softmax function in the last layer for classification, which outputs a probability distribution.
        *  The one-hot encoding helps compare the model's predicted probabilities with the true class in a clear way */
        List<int[]> resultEncoding = new ArrayList<>();
        for(Integer label : labelData) {
            // To hold numbers 0 - 9
            // Label `3` → One-hot encoded as `[0, 0, 0, 1, 0, 0, 0, 0, 0, 0]
            int[] oneHotArray = new int[10];
            oneHotArray[label] = 1;
            resultEncoding.add(oneHotArray);

        }

        return resultEncoding;
    }

}
