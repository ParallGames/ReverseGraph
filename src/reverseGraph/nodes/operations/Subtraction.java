package reverseGraph.nodes.operations;

import reverseGraph.exceptions.Assert;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public final class Subtraction extends Operation {
	private final Node minuend;
	private final Node subtraend;

	public Subtraction(Node minuend, Node subtraend) {
		super(minuend.values.dimensions);

		Assert.sameDimensions(minuend.values.dimensions, subtraend.values.dimensions);

		this.minuend = minuend;
		this.subtraend = subtraend;
	}

	@Override
	public void compute() {
		for (int i = 0; i < values.flat.length; i++) {
			values.flat[i] = minuend.values.flat[i] - subtraend.values.flat[i];
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { minuend, subtraend };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (minuend instanceof Derivable) {
			for (int i = 0; i < values.flat.length; i++) {
				((Derivable) minuend).derivatives.flat[i] += derivatives.flat[i];
			}
		}

		if (subtraend instanceof Derivable) {
			for (int i = 0; i < values.flat.length; i++) {
				((Derivable) subtraend).derivatives.flat[i] -= derivatives.flat[i];
			}
		}
	}
}
