package reverseGraph.nodes.operations.loss;

import reverseGraph.data.Dimensions;
import reverseGraph.exceptions.Assert;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public class SigmoidCrossEntropy extends Operation {
	private final Node output;
	private final Node desiredOutput;

	public SigmoidCrossEntropy(Node output, Node desiredOutput) {
		super(Dimensions.SCALAR);

		Assert.sameDimensions(output.values.dimensions, desiredOutput.values.dimensions);

		this.output = output;
		this.desiredOutput = desiredOutput;
	}

	@Override
	public void compute() {
		double sum = 0;

		for (int i = 0; i < output.values.flat.length; i++) {
			double x = output.values.flat[i];
			double y = desiredOutput.values.flat[i];

			double sigm = 1D / (1D + Math.exp(-x));

			sum -= y * Math.log(sigm);
			sum -= (1D - y) * Math.log(1D - sigm);
		}

		values.flat[0] = sum;
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { output, desiredOutput };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (output instanceof Derivable) {
			double d = derivatives.flat[0];

			for (int i = 0; i < output.values.flat.length; i++) {
				double x = output.values.flat[i];
				double y = desiredOutput.values.flat[i];

				((Derivable) output).derivatives.flat[i] += (1D / (Math.exp(-x) + 1D) - y) * d;
			}
		}

		if (desiredOutput instanceof Derivable) {
			double d = derivatives.flat[0];

			for (int i = 0; i < desiredOutput.values.flat.length; i++) {
				double x = output.values.flat[i];

				((Derivable) desiredOutput).derivatives.flat[i] -= x * d;
			}
		}
	}
}