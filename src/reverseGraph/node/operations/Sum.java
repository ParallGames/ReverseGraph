package reverseGraph.node.operations;

import reverseGraph.data.Tensor;
import reverseGraph.node.Node;
import reverseGraph.node.Operation;
import reverseGraph.util.DimensionsUtil;

public class Sum extends Operation {
	public Sum(Node... inputs) {
		super(DimensionsUtil.SCALAR, inputs);
	}

	@Override
	public void compute(Tensor output, Tensor[] inputs) {
		double sum = 0;
		
		for(Tensor in : inputs) {
			for(int i = 0; i < in.dimensions.valuesCount; i++) {
				sum += in.values[i];
			}
		}
		
		output.values[0] = sum;
	}

	@Override
	public void computeDerivatives(Tensor output, Tensor[] inputs, Tensor outputGradient, Tensor[] inputsGradient) {
		double gradient = outputGradient.get();
		
		for(Tensor in : inputsGradient) {
			for(int i = 0; i < in.dimensions.valuesCount; i++) {
				in.values[i] += gradient;
			}
		}
	}
}
