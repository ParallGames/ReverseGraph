package reverseGraph.nodes.operations;

import reverseGraph.DifferentSizeException;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public class WeightedSum extends Operation {

	private final Node addends;
	private final Node weights;

	public WeightedSum(Node addends, Node weights) {
		super(1);

		if (addends.getSize() != weights.getSize()) {
			throw new DifferentSizeException(addends.getSize(), weights.getSize());
		}

		this.addends = addends;
		this.weights = weights;
	}

	@Override
	public void compute() {
		double output = 0;

		for (int i = 0; i < addends.getValues().length; i++) {
			output += addends.getValues()[i] * weights.getValues()[i];
		}

		outputs[0] = output;
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { addends, weights };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (addends instanceof Derivable) {
			double[] derivatives = new double[addends.getSize()];

			for (int i = 0; i < addends.getSize(); i++) {
				derivatives[i] = this.getDerivatives()[0] * weights.getValues()[i];
			}

			((Derivable) addends).addToDerivatives(derivatives);
		}

		if (weights instanceof Derivable) {
			double[] derivatives = new double[weights.getSize()];

			for (int i = 0; i < weights.getSize(); i++) {
				derivatives[i] = this.getDerivatives()[0] * addends.getValues()[i];
			}

			((Derivable) weights).addToDerivatives(derivatives);
		}
	}

}
