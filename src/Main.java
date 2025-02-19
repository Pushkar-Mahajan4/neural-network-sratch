import javax.swing.*;
import java.awt.image.BufferedImage;
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
            int magicNumber = Util.getIntFromBytes(byteList.subList(0, 5));
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

        if (trainImages == null || trainLabels == null || testImages == null || testLabels == null) {
            System.out.println("Problem occurred during loading of datasets");
        }
g
        int numberOfTrainImages = Util.getIntFromBytes(trainImages.subList(4, 8));

        // Verify that rows & column are 28
        int numberOfRows = Util.getIntFromBytes(trainImages.subList(8, 12));
        int numberOfColumns = Util.getIntFromBytes(trainImages.subList(12, 16));
        try {
            Util.assertRowsAndColumns(numberOfRows, numberOfColumns);
        } catch (AssertionError e) {
            System.out.println("Expected 28 row x 28 columns image");
            System.exit(0);
        }

        System.out.println("Size : " +trainImages.size());
        System.out.println("numberOfTrainImages : " +numberOfTrainImages);

        /* Load each image */
        List<int[][]> eachImage = Util.getEachImage(trainImages);

        /* Uncomment to view the first image */
//        displayImage(eachImage.getFirst());
//        eachImage.add(Util.getImagePixels(trainImages, 16));




    }

    public static void displayImage(int[][] input) {
        BufferedImage image = new BufferedImage(28, 28, BufferedImage.TYPE_BYTE_GRAY);
        for(int y = 0; y < 28; y++) {
            for(int x = 0; x < 28; x++) {
                int pixelValue = input[y][x];
                int rgb = (pixelValue << 16) | (pixelValue << 8) | pixelValue;
                image.setRGB(x, y, rgb);
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