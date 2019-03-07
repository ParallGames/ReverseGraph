package reverseGraph.nodes.operations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public class Addition extends Operation {
	private final Node addend1;
	private final Node addend2;

	public Addition(Node addend1, Node addend2) {
		super(addend1.getSize());

		this.addend1 = addend1;
		this.addend2 = addend2;
	}

	@Override
	public void compute() {
		for (int i = 0; i < getSize(); i++) {
			outputs[i] = addend1.getValues()[i] + addend2.getValues()[i];
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { addend1, addend2};
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (addend1 instanceof Derivable) {
			((Derivable) addend1).addToDerivatives(getDerivatives());
		}

		if (addend1 instanceof Derivable) {
			((Derivable) addend2).addToDerivatives(getDerivatives());
		}
	}
}
