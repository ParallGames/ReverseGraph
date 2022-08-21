package examples;

import reverseGraph.OptimizationGraph;
import reverseGraph.data.Dimensions;
import reverseGraph.data.Tensor;
import reverseGraph.datasets.Mnist;
import reverseGraph.model.Activation;
import reverseGraph.model.LayerModel;
import reverseGraph.model.NeuralNetwork;
import reverseGraph.nodes.Input;
import reverseGraph.nodes.operations.Operation;
import reverseGraph.optimizers.AdaDelta;
import reverseGraph.optimizers.Adam;
import reverseGraph.optimizers.SGD;
import reverseGraph.util.Util;

public class MnistExample {
    public static void main(String[] args) {
        // Net inputs
        Input inputs = new Input(new Dimensions(28 * 28));

        // Create net model
        LayerModel[] layers = new LayerModel[2];
        layers[0] = new LayerModel(64, Activation.TANH);
        layers[1] = new LayerModel(10, Activation.SOFTMAX);

        NeuralNetwork net = new NeuralNetwork(inputs, layers);
        // Create net
        Operation output = net.getOutput();

        // Net label (the desired output)
        Input label = new Input(new Dimensions(10));

        // Add operation to compute how far the output is from the label
        Operation error = Util.createNegativeLogLikelihood(output, label);

        // Create a graph to compute all operations
        OptimizationGraph graph = new OptimizationGraph(error, new Adam(0.001, 0.9, 0.999));


        for (int epoch = 0; epoch < 10; epoch++) {
            double trainError = 0;
            for (int i = 0; i < 60000; i++) {
                // Set input and label in the graph
                inputs.setValues(Mnist.trainingImages[i]);
                label.setValues(Mnist.trainingLabels[i]);

                // Compute error
                graph.compute();
                trainError += error.values.get();

                // Minimize the error
                graph.computeDerivatives();
            }

            graph.minimize();

            double testError = 0;
            for (int i = 0; i < 10000; i++) {
                // Set input and label in the graph
                inputs.setValues(Mnist.testImages[i]);
                label.setValues(Mnist.testLabels[i]);

                // Compute error
                graph.compute();
                testError += error.values.get();
            }

            System.out.println("Epoch " + epoch);
            System.out.println("Train error: " + trainError);
            System.out.println("Test error: " + testError);
            System.out.println();
        }
    }

}
