import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

class Main {

    public static List<Byte> loadDataset(String fileLocation) throws Exception {

        try {
            // Use FileInputStream to read raw bytes, FileReader reads
            FileInputStream fileReader = new FileInputStream (fileLocation);
            int read;

            List<Byte> byteList = new ArrayList<>();
            while ((read = fileReader.read()) != -1) {
                byteList.add((byte) read);
            }

            fileReader.close();
            /* Data is stored in Big endian format (Most significant bit - stored first) */
            int magicNumber = Util.getIntFromBytes(byteList.subList(0, 4));
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
        System.out.println("trainImages size : " +trainImages.size());

        List<Byte> trainLabels = loadDataset(trainLabelsLocation);
        List<Byte> testImages = loadDataset(testImagesLocation);
        List<Byte> testLabels = loadDataset(testLabelsLocation);

        if (trainImages == null || trainLabels == null || testImages == null || testLabels == null) {
            System.out.println("Problem occurred during loading of datasets");
        }

        // Verify 60000 train images
        int numberOfTrainImages = Util.getIntFromBytes(trainImages.subList(4, 8));
        Util.assertNumberOfTrainImages(numberOfTrainImages);

        // Verify that rows & column are 28
        int numberOfRows = Util.getIntFromBytes(trainImages.subList(8, 12));
        int numberOfColumns = Util.getIntFromBytes(trainImages.subList(12, 16));
        Util.assertRowsAndColumns(numberOfRows, numberOfColumns);

//        /* Load each image */
        List<int[][]> eachImage = Util.getEachImage(trainImages);

        /* Load each label */
//        List<int[]> eachLabel = Util.getEachLabel(trainLabels);

        /* Normalizing pixel values to 0 - 1, check docs to see why float */
        List<float[][]> normalizedPixels = NeuralNWUtils.normalizePixels(eachImage);

        /* Uncomment to view the first image */
//        displayImage(eachImage.get(90));

        /* TODO : One hot encode labels
        * TODO : Initialize weights using min bias
        * TODO : Setup Activation function
        * TODO : Calculate loss */

    }

    public static void displayImage(int[][] input) {
        /* Java's BufferedImage, RGB values are stored as a single 32-bit integer */
        BufferedImage image = new BufferedImage(28, 28, BufferedImage.TYPE_BYTE_GRAY);
        for(int y = 0; y < 28; y++) {
            for(int x = 0; x < 28; x++) {
                int pixelValue = input[y][x];
                System.out.println("PixelValue : " + pixelValue);
                image.getRaster().setSample(x, y, 0, pixelValue);
            }
        }

        // Display the image
        JFrame frame = new JFrame("MNIST Image #" + 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new JLabel(new ImageIcon(image)));
        frame.pack();
        frame.setVisible(true);
    }
}