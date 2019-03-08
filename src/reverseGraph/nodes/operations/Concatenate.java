package reverseGraph.nodes.operations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public class Concatenate extends Operation {
	private static int totalSize(Node[] nodes) {
		int size = 0;

		for (Node node : nodes) {
			size += node.getSize();
		}

		return size;
	}

	Node[] inputs;

	public Concatenate(Node... inputs) {
		super(totalSize(inputs));

		this.inputs = inputs;
	}

	@Override
	public void compute() {
		int index = 0;

		for (int i = 0; i < inputs.length; i++) {
			for (int a = 0; a < inputs[i].getSize(); a++) {
				outputs[index] = inputs[i].getValues()[a];
				index++;
			}
		}
	}

	@Override
	public Node[] getDependencies() {
		return inputs;
	}

	@Override
	public void computeDependenciesDerivatives() {

		int index = 0;
		for (int i = 0; i < inputs.length; i++) {
			if (inputs[i] instanceof Derivable) {
				double[] derivatives = new double[inputs[i].getSize()];

				for (int a = 0; a < inputs[i].getSize(); a++) {
					derivatives[a] = getDerivatives()[index];
					index++;
				}

				((Derivable) inputs[i]).addToDerivatives(derivatives);
			} else {
				index += inputs[i].getSize();
			}

		}
	}
}
