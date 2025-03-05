import java.util.List;

public class AssertionUtils {

    public static void validateTrainImages(List<Byte> trainImages) throws AssertionError{

        // Assert not null
        assert trainImages != null;

        // Verify 60000 train images
        int numberOfTrainImages = Util.getIntFromBytes(trainImages.subList(4, 8));
        Util.assertNumberOfTrainImages(numberOfTrainImages);

        // Verify that rows & column are 28
        int numberOfRows = Util.getIntFromBytes(trainImages.subList(8, 12));
        int numberOfColumns = Util.getIntFromBytes(trainImages.subList(12, 16));
        Util.assertRowsAndColumns(numberOfRows, numberOfColumns);
    }

    public static void validateTestImages(List<Byte> testImages) throws AssertionError {
        assert testImages != null;

        int numberOfTestImages = Util.getIntFromBytes(testImages.subList(4, 8));
        assert numberOfTestImages == 10000;

        int numberOfRows = Util.getIntFromBytes(testImages.subList(8, 12));
        int numberOfColumns = Util.getIntFromBytes(testImages.subList(12, 16));
        Util.assertRowsAndColumns(numberOfRows, numberOfColumns);
    }


    public static void validateTrainLabels(List<Byte> trainLabels) throws AssertionError{
        // Verify Label Data  : 60000 bytes
        assert trainLabels.size() == 60000;
    }

    public static void validateTestLabels(List<Byte> testLabels) throws AssertionError{
        // Verify Label Data  : 60000 bytes
        assert testLabels.size() == 60000;
    }


}
