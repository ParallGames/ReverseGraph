package vnl;

import java.util.Random;

import reverseGraph.nodes.Node;
import reverseGraph.nodes.Param;
import reverseGraph.nodes.operations.Addition;
import reverseGraph.nodes.operations.Aggregator;
import reverseGraph.nodes.operations.Division;
import reverseGraph.nodes.operations.Multiplication;
import reverseGraph.nodes.operations.Operation;
import reverseGraph.nodes.operations.Sum;
import reverseGraph.nodes.operations.activations.LeakyRelu;
import reverseGraph.nodes.operations.activations.Sigmoid;
import reverseGraph.nodes.operations.activations.Tanh;
import vnl.model.LayerModel;

public class Network {
	private static final Random rand = new Random();

	private static Operation createNeuron(Node inputs, int outputSize) {
		double[] initialValues = new double[inputs.getSize()];
		
		for (int i = 0; i < inputs.getSize(); i++) {
			initialValues[i] = rand.nextGaussian() * 2 / (inputs.getSize() + outputSize);
		}
		
		Param weights = new Param(initialValues);
		
		Node muls = new Multiplication(inputs, weights);

		return new Sum(muls);
	}

	private static Operation createLayer(Node inputs, int outputSize, Activation activation) {
		Operation[] outputs = new Operation[outputSize];

		for (int i = 0; i < outputSize; i++) {
			outputs[i] = createNeuron(inputs, outputSize);
		}
		
		Operation output = new Addition(new Aggregator(outputs), new Param(outputSize));

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

			return new Division(output, new Sum(output));
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
