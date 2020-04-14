package reverseGraph.nodes.operations;

import reverseGraph.exceptions.Assert;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public final class Multiplication extends Operation {
	private final Node factor1;
	private final Node factor2;

	public Multiplication(Node factor1, Node factor2) {
		super(factor1.values.dimensions);

		Assert.sameDimensions(factor1.values.dimensions, factor2.values.dimensions);

		this.factor1 = factor1;
		this.factor2 = factor2;
	}

	@Override
	public void compute() {
		for (int i = 0; i < values.flat.length; i++) {
			values.flat[i] = factor1.values.flat[i] * factor2.values.flat[i];
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { factor1, factor2 };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (factor1 instanceof Derivable) {
			for (int i = 0; i < derivatives.flat.length; i++) {
				((Derivable) factor1).derivatives.flat[i] += derivatives.flat[i] * factor2.values.flat[i];
			}
		}

		if (factor2 instanceof Derivable) {
			for (int i = 0; i < derivatives.flat.length; i++) {
				((Derivable) factor2).derivatives.flat[i] += derivatives.flat[i] * factor1.values.flat[i];
			}
		}
	}
}
