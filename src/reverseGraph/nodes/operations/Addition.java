package reverseGraph.nodes.operations;

import reverseGraph.DifferentSizeException;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public final class Addition extends Operation {
	private final Node addend1;
	private final Node addend2;

	public Addition(Node addend1, Node addend2) {
		super(addend1.values.length);

		if (addend1.values.length != addend2.values.length) {
			throw new DifferentSizeException(addend1.values.length, addend2.values.length);
		}

		this.addend1 = addend1;
		this.addend2 = addend2;
	}

	@Override
	public void compute() {
		for (int i = 0; i < values.length; i++) {
			values[i] = addend1.values[i] + addend2.values[i];
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

			for (int i = 0; i < derivable.derivatives.length; i++) {
				derivable.derivatives[i] += derivatives[i];
			}
		}

		if (addend1 instanceof Derivable) {
			Derivable derivable = ((Derivable) addend2);

			for (int i = 0; i < derivable.derivatives.length; i++) {
				derivable.derivatives[i] += derivatives[i];
			}
		}
	}
}
