package reverseGraph.nodes.operations;

import reverseGraph.DifferentSizeException;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public class Multiplication extends Operation {
	private final Node factor1;
	private final Node factor2;

	public Multiplication(Node factor1, Node factor2) {
		super(factor1.getSize());

		if (factor1.getSize() != factor2.getSize()) {
			throw new DifferentSizeException(factor1.getSize(), factor2.getSize());
		}

		this.factor1 = factor1;
		this.factor2 = factor2;
	}

	@Override
	public void compute() {
		final int size = getSize();
		for (int i = 0; i < size; i++) {
			outputs[i] = factor1.getValues()[i] * factor2.getValues()[i];
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { factor1, factor2 };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (factor1 instanceof Derivable) {
			final int size = getSize();
			for (int i = 0; i < size; i++) {
				((Derivable) factor1).getDerivatives()[i] += this.getDerivatives()[i] * factor2.getValues()[i];
			}
		}

		if (factor2 instanceof Derivable) {
			final int size = getSize();
			for (int i = 0; i < size; i++) {
				((Derivable) factor2).getDerivatives()[i] += this.getDerivatives()[i] * factor1.getValues()[i];
			}
		}
	}
}
