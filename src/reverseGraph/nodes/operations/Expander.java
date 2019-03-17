package reverseGraph.nodes.operations;

import reverseGraph.WrongSizeException;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public final class Expander extends Operation {
	private final Node input;

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
			values[i] = input.values[0];
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
				sum += derivatives[i];
			}

			((Derivable) input).derivatives[0] += sum;
		}
	}
}
