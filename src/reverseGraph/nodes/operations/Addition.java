package reverseGraph.nodes.operations;

import reverseGraph.exceptions.Assert;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public final class Addition extends Operation {
	private final Node addend1;
	private final Node addend2;

	public Addition(Node addend1, Node addend2) {
		super(addend1.values.dimensions);

		Assert.sameDimensions(addend1.values.dimensions, addend2.values.dimensions);

		this.addend1 = addend1;
		this.addend2 = addend2;
	}

	@Override
	public void compute() {
		for (int i = 0; i < values.dimensions.valuesCount; i++) {
			values.flat[i] = addend1.values.flat[i] + addend2.values.flat[i];
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { addend1, addend2 };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (addend1 instanceof Derivable) {
			Derivable derivable = ((Derivable) addend1);

			for (int i = 0; i < derivatives.flat.length; i++) {
				derivable.derivatives.flat[i] += derivatives.flat[i];
			}
		}

		if (addend2 instanceof Derivable) {
			Derivable derivable = ((Derivable) addend2);

			for (int i = 0; i < derivatives.flat.length; i++) {
				derivable.derivatives.flat[i] += derivatives.flat[i];
			}
		}
	}
}
