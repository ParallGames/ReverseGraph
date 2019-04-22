package reverseGraph.nodes.operations;

import reverseGraph.DifferentSizeException;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public final class Substraction extends Operation {
	private final Node minuend;
	private final Node substraend;

	public Substraction(Node minuend, Node substraend) {
		super(minuend.values.length);

		if (minuend.values.length != substraend.values.length) {
			throw new DifferentSizeException(minuend.values.length, substraend.values.length);
		}

		this.minuend = minuend;
		this.substraend = substraend;
	}

	@Override
	public void compute() {
		for (int i = 0; i < values.length; i++) {
			values[i] = minuend.values[i] - substraend.values[i];
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { minuend, substraend };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (minuend instanceof Derivable) {
			for (int i = 0; i < values.length; i++) {
				((Derivable) minuend).derivatives[i] += derivatives[i];
			}
		}

		if (substraend instanceof Derivable) {
			for (int i = 0; i < values.length; i++) {
				((Derivable) substraend).derivatives[i] -= derivatives[i];
			}
		}
	}
}
