package reverseGraph.nodes.operations;

import reverseGraph.exceptions.Assert;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public final class Min extends Operation {
	private final Node a;
	private final Node b;

	public Min(Node a, Node b) {
		super(a.values.dimensions);

		Assert.sameDimensions(a.values.dimensions, b.values.dimensions);

		this.a = a;
		this.b = b;
	}

	@Override
	public void compute() {
		for (int i = 0; i < values.dimensions.valuesCount; i++) {
			values.flat[i] = Math.min(a.values.flat[i], b.values.flat[i]);
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { a, b };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (a instanceof Derivable) {
			Derivable derivable = ((Derivable) a);

			for (int i = 0; i < derivatives.flat.length; i++) {
				if (a.values.flat[i] <= b.values.flat[i]) {
					derivable.derivatives.flat[i] += derivatives.flat[i];
				}
			}
		}

		if (b instanceof Derivable) {
			Derivable derivable = ((Derivable) b);

			for (int i = 0; i < derivatives.flat.length; i++) {
				if (b.values.flat[i] <= a.values.flat[i]) {
					derivable.derivatives.flat[i] += derivatives.flat[i];
				}
			}
		}
	}
}
