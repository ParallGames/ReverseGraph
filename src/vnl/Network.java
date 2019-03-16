package vnl;

import java.util.Random;

import reverseGraph.nodes.Node;
import reverseGraph.nodes.Param;
import reverseGraph.nodes.operations.Division;
import reverseGraph.nodes.operations.Expander;
import reverseGraph.nodes.operations.Layer;
import reverseGraph.nodes.operations.Operation;
import reverseGraph.nodes.operations.Sum;
import reverseGraph.nodes.operations.activations.LeakyRelu;
import reverseGraph.nodes.operations.activations.Sigmoid;
import reverseGraph.nodes.operations.activations.Tanh;
import vnl.model.LayerModel;

public class Network {
	private static final Random rand = new Random();

	private static Operation createLayer(Node inputs, int outputSize, Activation activation) {
		double[] initialWeights = new double[inputs.getSize() * outputSize];

		for (int i = 0; i < initialWeights.length; i++) {
			initialWeights[i] = rand.nextGaussian() * 2 / (inputs.getSize() + outputSize);
		}

		Operation output = new Layer(inputs, new Param(initialWeights), new Param(outputSize));

		// Apply an activation
		switch (activation) {
		case IDENTITY:
			return output;
		case LEAKYRELU:
			return new LeakyRelu(output);
		case SIGMOID:
			return new Sigmoid(output);
		case TANH:
			return new Tanh(output);
		case SOFTMAX:
			output = new Sigmoid(output);

			return new Division(output, new Expander(new Sum(output), outputSize));
		default:
			throw new RuntimeException("Unknown activation");
		}
	}

	public static Operation createNetwork(Node inputs, LayerModel... layers) {
		Operation outputs = createLayer(inputs, layers[0].outputSize, layers[0].activation);

		for (int l = 1; l < layers.length; l++) {
			outputs = createLayer(outputs, layers[l].outputSize, layers[l].activation);
		}

		return outputs;
	}
}
