package reverseGraph.node.vector.operation;

import reverseGraph.node.Node;
import reverseGraph.node.vector.VectorDerivable;
import reverseGraph.node.vector.VectorNode;
import reverseGraph.node.vector.VectorOperation;

public class ReLU extends VectorOperation {
	private final double[] values;
	private final double[] derivatives;

	private final VectorNode input;

	public ReLU(VectorNode input) {
		super(input.width);

		this.input = input;

		values = new double[width];
		derivatives = new double[width];
	}

	@Override
	public void compute() {
		for (int i = 0; i < width; i++) {
			values[i] = input.get(i) > 0 ? input.get(i) : input.get(i) * 0.01;
		}
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (input instanceof VectorDerivable) {
			VectorDerivable derivable = (VectorDerivable) input;

			for (int i = 0; i < width; i++) {
				derivable.addDerivative(i, (input.get(i) > 0 ? 1 : 0.01) * derivatives[i]);
			}
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { input };
	}

	@Override
	public void resetDerivatives() {
		for (int i = 0; i < width; i++) {
			derivatives[i] = 0;
		}
	}

	@Override
	public double getDerivative(int index) {
		return derivatives[index];
	}

	@Override
	public void addDerivative(int index, double value) {
		derivatives[index] += value;
	}

	@Override
	public double get(int index) {
		return values[index];
	}
}
