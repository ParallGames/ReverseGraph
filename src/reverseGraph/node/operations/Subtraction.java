package reverseGraph.node.operations;

import reverseGraph.data.Tensor;
import reverseGraph.node.Operation;
import reverseGraph.node.vector.VectorNode;
import reverseGraph.util.Assert;

public class Subtraction extends Operation {
	public Subtraction(VectorNode minuend, VectorNode subtrahend) {
		super(minuend.dimensions, minuend, subtrahend);
		
		Assert.equals(minuend.dimensions, subtrahend.dimensions);
	}

	@Override
	public void compute(Tensor output, Tensor[] inputs) {
		for(int i = 0; i < output.values.length; i++) {
			output.values[i] = inputs[0].values[i] - inputs[1].values[i];
		}
	}

	@Override
	public void computeDerivatives(Tensor output, Tensor[] inputs, Tensor outputGradient, Tensor[] inputsGradient) {
		for(int i = 0; i < outputGradient.values.length; i++) {
			inputsGradient[0].values[i] = outputGradient.values[i];
			inputsGradient[1].values[i] = -outputGradient.values[i];
		}
	}
}
