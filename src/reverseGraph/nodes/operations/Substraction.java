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
		for (int i = 0; i < getSize(); i++) {
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
			double[] derivatives = new double[getSize()];

			for (int i = 0; i < getSize(); i++) {
				derivatives[i] = this.getDerivatives()[i];
			}

			((Derivable) minuend).addToDerivatives(derivatives);
		}

		if (substraend instanceof Derivable) {
			double[] derivatives = new double[getSize()];

			for (int i = 0; i < getSize(); i++) {
				derivatives[i] = -this.getDerivatives()[i];
			}

			((Derivable) minuend).addToDerivatives(derivatives);
		}
	}
}
