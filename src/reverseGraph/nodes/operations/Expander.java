package reverseGraph.nodes.operations;

import reverseGraph.WrongSizeException;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public class Expander extends Operation {
	private Node input;

	public Expander(Node input, int outSize) {
		super(outSize);

		if (input.getSize() != 1) {
			throw new WrongSizeException(input.getSize(), 1);
		}

		this.input = input;
	}

	@Override
	public void compute() {
		final int size = getSize();
		for (int i = 0; i < size; i++) {
			outputs[i] = input.getValues()[0];
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { input };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (input instanceof Derivable) {
			double sum = 0;

			final int size = getSize();
			for (int i = 0; i < size; i++) {
				sum += getDerivatives()[i];
			}

			((Derivable) input).getDerivatives()[0] += sum;
		}
	}
}
