import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

class Main {

    public static List<Byte> loadDataset(String fileLocation) throws Exception {

        try {
            FileReader fileReader = new FileReader(fileLocation);
            byte read;

            List<Byte> byteList = new ArrayList<>();
            while ((read = (byte) fileReader.read()) != -1) {
                byteList.add(read);
            }

            /* Data is stored in Big endian format (Most significant bit - stored first) */
            int magicNumber = (
                    (byteList.get(0) & 0xFF) << 24 |
                    (byteList.get(1) & 0xFF) << 16 |
                    (byteList.get(2) & 0xFF) << 8 |
                    (byteList.get(3) & 0xFF));

            System.out.println(magicNumber);
            if(magicNumber == 2051) {
                System.out.println("Reading image file..");
            } else if(magicNumber == 2049) {
                System.out.println("Reading label file..");
            } else {
                throw new IllegalArgumentException("Invalid File format received");
            }

            return byteList;
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        /* Train Dataset */
        String trainImagesLocation = "dataset/train-images.idx3-ubyte";
        String trainLabelsLocation = "dataset/train-labels.idx1-ubyte";

        /* Test Dataset */
        String testImagesLocation = "dataset/t10k-images.idx3-ubyte";
        String testLabelsLocation = "dataset/t10k-labels.idx1-ubyte";

        /* Load the dataset */
        List<Byte> trainImages = loadDataset(trainImagesLocation);
        List<Byte> trainLabels = loadDataset(trainLabelsLocation);
        List<Byte> testImages = loadDataset(testImagesLocation);
        List<Byte> testLabels = loadDataset(testLabelsLocation);

    }
}