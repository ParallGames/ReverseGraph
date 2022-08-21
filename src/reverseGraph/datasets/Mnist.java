package reverseGraph.datasets;

import reverseGraph.data.Tensor;

import java.io.DataInputStream;

public class Mnist {
    public static final int TRAINING_SET_SIZE = 60000;
    public static final int TEST_SET_SIZE = 10000;

    public static final int IMAGE_SIZE = 28;

    private static byte[][] prepareRawTrainingImages() {
        try (DataInputStream data = new DataInputStream(Mnist.class.getResourceAsStream("/reverseGraph/resources/mnist-train-images"))) {
            data.readInt(); // magic number
            data.readInt(); // number of images
            data.readInt(); // image height
            data.readInt(); // image width

            byte[][] rawImages = new byte[TRAINING_SET_SIZE][];
            for (int i = 0; i < TRAINING_SET_SIZE; i++) {
                rawImages[i] = new byte[28 * 28];
                data.readFully(rawImages[i]);
            }
            return rawImages;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[][] prepareRawTestImages() {
        try (DataInputStream data = new DataInputStream(Mnist.class.getResourceAsStream("/reverseGraph/resources/mnist-test-images"))) {
            data.readInt(); // magic number
            data.readInt(); // number of images
            data.readInt(); // image height
            data.readInt(); // image width

            byte[][] rawImages = new byte[TEST_SET_SIZE][];
            for (int i = 0; i < TEST_SET_SIZE; i++) {
                rawImages[i] = new byte[28 * 28];
                data.readFully(rawImages[i]);
            }
            return rawImages;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] prepareRawTrainingLabels() {
        try (DataInputStream data = new DataInputStream(Mnist.class.getResourceAsStream("/reverseGraph/resources/mnist-train-labels"))) {
            data.readInt(); // magic number
            data.readInt(); // number of images
            byte[] rawLabels = new byte[TRAINING_SET_SIZE];
            data.readFully(rawLabels);
            return rawLabels;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] prepareRawTestLabels() {
        try (DataInputStream data = new DataInputStream(Mnist.class.getResourceAsStream("/reverseGraph/resources/mnist-test-labels"))) {
            data.readInt(); // magic number
            data.readInt(); // number of images
            byte[] rawLabels = new byte[TEST_SET_SIZE];
            data.readFully(rawLabels);
            return rawLabels;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Flatten raw data from training images, values are between -128 and 127.
     */
    public static final byte[][] rawTrainingImages = prepareRawTrainingImages();

    /**
     * Flatten raw data from test images, values are between -128 and 127.
     */
    public static final byte[][] rawTestImages = prepareRawTestImages();

    /**
     * raw data from training labels, values are between 0 and 9.
     */
    public static final byte[] rawTrainingLabels = prepareRawTrainingLabels();

    /**
     * raw data from test labels, values are between 0 and 9.
     */
    public static final byte[] rawTestLabels = prepareRawTestLabels();


    public static Tensor[] prepareTrainingImages() {
        Tensor[] trainingImages = new Tensor[TRAINING_SET_SIZE];
        for (int i = 0; i < TRAINING_SET_SIZE; i++) {
            trainingImages[i] = new Tensor(IMAGE_SIZE, IMAGE_SIZE);
            for (int j = 0; j < IMAGE_SIZE * IMAGE_SIZE; j++) {
                trainingImages[i].flat[j] = (rawTrainingImages[i][j] & 0x000000ff) / 255.0; // raw values are between -128 and 127, we want values between 0 and 1
            }
        }
        return trainingImages;
    }

    public static Tensor[] prepareTestImages() {
        Tensor[] testImages = new Tensor[TEST_SET_SIZE];
        for (int i = 0; i < TEST_SET_SIZE; i++) {
            testImages[i] = new Tensor(IMAGE_SIZE, IMAGE_SIZE);
            for (int j = 0; j < IMAGE_SIZE * IMAGE_SIZE; j++) {
                testImages[i].flat[j] = (rawTestImages[i][j] & 0x000000ff) / 255.0; // raw values are between -128 and 127, we want values between 0 and 1
            }
        }
        return testImages;
    }

    public static Tensor[] prepareTrainingLabels() {
        Tensor[] trainingLabels = new Tensor[TRAINING_SET_SIZE];
        for (int i = 0; i < TRAINING_SET_SIZE; i++) {
            trainingLabels[i] = new Tensor(10);
            trainingLabels[i].flat[rawTrainingLabels[i]] = 1.0;
        }
        return trainingLabels;
    }

    public static Tensor[] prepareTestLabels() {
        Tensor[] testLabels = new Tensor[TEST_SET_SIZE];
        for (int i = 0; i < TEST_SET_SIZE; i++) {
            testLabels[i] = new Tensor(10);
            testLabels[i].flat[rawTestLabels[i]] = 1.0;
        }
        return testLabels;
    }

    /**
     * 28 x 28 tensor of training images, pixel values are between 0 and 1.
     */
    public static final Tensor[] trainingImages = prepareTrainingImages();

    /**
     * 28 x 28 tensor of test images, pixel values are between 0 and 1.
     */
    public static final Tensor[] testImages = prepareTestImages();

    /**
     * One hot encoded tensor of training labels
     */
    public static final Tensor[] trainingLabels = prepareTrainingLabels();

    /**
     * One hot encoded tensor of test labels
     */
    public static final Tensor[] testLabels = prepareTestLabels();
}
