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

    public static int[] extractLabels(List<Byte> input, int offset) {
        int[] result = new int[28];
//        for(int i = 0; i < 28; i++) {
//            for(int j = 0; j < 28; j++) {
//                result[i][j] = input.get(offset) & 0xFF;
//                offset++;
//            }
//        }

        return result;
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

    public static List<int[]> getEachLabel(List<Byte> input) {
        List<int[]> result = new ArrayList<>();
        int labelStartIndex = 8;
        while(labelStartIndex < input.size()) {
//            result.add(extractImagePixels(input, labelStartIndex));
            // 28 x 28 = 784 pixels + 1 to start with first pixel of next image
            labelStartIndex++;
        }

        return result;
    }

}
