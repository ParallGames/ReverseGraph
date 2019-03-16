package reverseGraph.nodes.operations;

import reverseGraph.DifferentSizeException;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public class Substraction extends Operation {
	private final Node minuend;
	private final Node substraend;

	public Substraction(Node minuend, Node substraend) {
		super(minuend.getSize());

		if (minuend.getSize() != substraend.getSize()) {
			throw new DifferentSizeException(minuend.getSize(), substraend.getSize());
		}

		this.minuend = minuend;
		this.substraend = substraend;
	}

	@Override
	public void compute() {
		final int size = getSize();
		for (int i = 0; i < size; i++) {
			outputs[i] = minuend.getValues()[i] - substraend.getValues()[i];
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { minuend, substraend };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (minuend instanceof Derivable) {
			final int size = getSize();
			for (int i = 0; i < size; i++) {
				((Derivable) minuend).getDerivatives()[i] += this.getDerivatives()[i];
			}
		}

		if (substraend instanceof Derivable) {
			final int size = getSize();
			for (int i = 0; i < size; i++) {
				((Derivable) minuend).getDerivatives()[i] -= this.getDerivatives()[i];
			}
		}
	}
}
