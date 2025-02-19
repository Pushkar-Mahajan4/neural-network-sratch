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

    public static int[][] getImagePixels(List<Byte> input, int offset) {
        int[][] result = new int[28][28];
        for(int i = 0; i < 28; i++) {
            for(int j = 0; j < 28; j++) {
                result[i][j] = input.get(offset) & 0xFF;
                offset++;
            }
        }

        return result;
    }

    public static List<int[][]> getEachImage(List<Byte> input) {
        List<int[][]> result = new ArrayList<>();
        int imageStartIndex = 16;
        while(imageStartIndex < input.size()) {
            result.add(getImagePixels(input, imageStartIndex));
            // 28 x 28 = 784 pixels + 1 to start with first pixel of next image
            imageStartIndex += 785;
        }

        return result;
    }

}
