import java.util.List;

public class AssertionUtils {

    public static void validateTrainImages(List<Byte> trainImages) throws AssertionError{
        // Verify 60000 train images
        int numberOfTrainImages = Util.getIntFromBytes(trainImages.subList(4, 8));
        Util.assertNumberOfTrainImages(numberOfTrainImages);

        // Verify that rows & column are 28
        int numberOfRows = Util.getIntFromBytes(trainImages.subList(8, 12));
        int numberOfColumns = Util.getIntFromBytes(trainImages.subList(12, 16));
        Util.assertRowsAndColumns(numberOfRows, numberOfColumns);
    }
}
