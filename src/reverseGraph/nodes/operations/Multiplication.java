package reverseGraph.nodes.operations;

import reverseGraph.DifferentSizeException;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public final class Multiplication extends Operation {
	private final Node factor1;
	private final Node factor2;

	public Multiplication(Node factor1, Node factor2) {
		super(factor1.values.length);

		if (factor1.values.length != factor2.values.length) {
			throw new DifferentSizeException(factor1.values.length, factor2.values.length);
		}

		this.factor1 = factor1;
		this.factor2 = factor2;
	}

	@Override
	public void compute() {
		for (int i = 0; i < values.length; i++) {
			values[i] = factor1.values[i] * factor2.values[i];
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { factor1, factor2 };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (factor1 instanceof Derivable) {
			for (int i = 0; i < derivatives.length; i++) {
				((Derivable) factor1).derivatives[i] += derivatives[i] * factor2.values[i];
			}
		}

		if (factor2 instanceof Derivable) {
			for (int i = 0; i < derivatives.length; i++) {
				((Derivable) factor2).derivatives[i] += derivatives[i] * factor1.values[i];
			}
		}
	}
}
