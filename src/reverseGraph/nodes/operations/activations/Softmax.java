package reverseGraph.nodes.operations.activations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public class Softmax extends Operation {
	private final Node inputs;

	public Softmax(Node inputs) {
		super(inputs.values.dimensions);
		this.inputs = inputs;
	}

	@Override
	public void compute() {
		double max = Double.NEGATIVE_INFINITY;

		for (int i = 0; i < inputs.values.flat.length; i++) {
			if (inputs.values.flat[i] > max) {
				max = inputs.values.flat[i];
			}
		}
		
		double sum = 0;
		
		for (int i = 0; i < inputs.values.flat.length; i++) {
			values.flat[i] = Math.exp(inputs.values.flat[i] - max);
			sum += values.flat[i];
		}

		for (int i = 0; i < values.flat.length; i++) {
			values.flat[i] /= sum;
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { inputs };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (this.inputs instanceof Derivable) {
			double max = Double.NEGATIVE_INFINITY;

			for (int i = 0; i < inputs.values.flat.length; i++) {
				if (inputs.values.flat[i] > max) {
					max = inputs.values.flat[i];
				}
			}

			double allexp = 0;
			double weights = 0;

			for (int i = 0; i < inputs.values.flat.length; i++) {
				double exp = Math.exp(inputs.values.flat[i] - max);
				allexp += exp;
				weights += exp * derivatives.flat[i];
			}
			
			double allexp2 = allexp * allexp;
			
			for (int i = 0; i < derivatives.flat.length; i++) {
				double exp = Math.exp(inputs.values.flat[i]);
				
				((Derivable) inputs).derivatives.flat[i] += (derivatives.flat[i] * (allexp - exp) - weights + exp * derivatives.flat[i]) * exp / allexp2;
			}
			
		}
	}
}
