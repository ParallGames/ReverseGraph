package reverseGraph.nodes.operations.loss;

import reverseGraph.data.Dimensions;
import reverseGraph.exceptions.Assert;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public class SigmoidSquaredError extends Operation {
	private final Node output;
	private final Node desiredOutput;

	public SigmoidSquaredError(Node output, Node desiredOutput) {
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

			double diff = 1D / (1D + Math.exp(-x)) - y;

			sum += diff * diff;
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

				double expx = Math.exp(x);

				double expxinc = expx + 1;

				((Derivable) output).derivatives.flat[i] -= (2D * expx * ((y - 1D) * expx + y))
						/ (expxinc * expxinc * expxinc) * d;
			}
		}

		if (desiredOutput instanceof Derivable) {
			double d = derivatives.flat[0];

			for (int i = 0; i < desiredOutput.values.flat.length; i++) {
				double x = output.values.flat[i];
				double y = desiredOutput.values.flat[i];

				((Derivable) desiredOutput).derivatives.flat[i] += 2 * (y - 1D / (Math.exp(-x) + 1)) * d;
			}
		}
	}
}