package vnl;

import java.util.Random;

import reverseGraph.nodes.Node;
import reverseGraph.nodes.Param;
import reverseGraph.nodes.operations.Multiplication;
import reverseGraph.nodes.operations.Operation;
import reverseGraph.nodes.operations.Sum;
import reverseGraph.nodes.operations.activations.LeakyRelu;
import reverseGraph.nodes.operations.activations.Sigmoid;
import reverseGraph.nodes.operations.activations.SoftPlus;
import reverseGraph.nodes.operations.activations.Tanh;
import vnl.model.LayerModel;

public class Network {
	private static final Random rand = new Random();

	private static Operation createNeuron(Node[] inputs, Activation act, int outputSize) {
		Node[] muls = new Node[inputs.length + 1];

		for (int i = 0; i < inputs.length; i++) {
			muls[i] = new Multiplication(new Param(rand.nextGaussian() * 2 / (inputs.length + outputSize)), inputs[i]);
		}

		muls[inputs.length] = new Param();

		Sum sum = new Sum(muls);

		switch (act) {
		case IDENTITY:
			return sum;
		case LEAKYRELU:
			return new LeakyRelu(sum);
		case SIGMOID:
			return new Sigmoid(sum);
		case TANH:
			return new Tanh(sum);
		case SOFTPLUS:
			return new SoftPlus(sum);
		}

		throw new RuntimeException("Unknown activation");
	}

	private static Operation[] createLayer(Node[] inputs, int outputSize, Activation activation) {
		Operation[] outputs = new Operation[outputSize];

		for (int i = 0; i < outputSize; i++) {
			outputs[i] = createNeuron(inputs, activation, outputSize);
		}

		return outputs;
	}

	public static Operation[] createNetwork(Node[] inputs, LayerModel... layers) {
		Operation[] outputs = createLayer(inputs, layers[0].outputSize, layers[0].activation);

		for (int l = 1; l < layers.length; l++) {
			outputs = createLayer(outputs, layers[l].outputSize, layers[l].activation);
		}

		return outputs;
	}
}
