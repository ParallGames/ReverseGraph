package reverseGraph.node.vector;

import reverseGraph.node.Optimizable;
import reverseGraph.node.Setable;

public class VectorParam extends VectorDerivable implements Optimizable, Setable {
	private final double[] values;
	private final double[] derivatives;

	public VectorParam(int width) {
		super(width);
		values = new double[width];
		derivatives = new double[width];
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
	public void resetDerivatives() {
		for (int i = 0; i < width; i++) {
			derivatives[i] = 0;
		}
	}

	@Override
	public void update(int index, double update) {
		values[index] += update;
	}

	@Override
	public double get(int index) {
		return values[index];
	}

	public void set(int index, double value) {
		values[index] = value;
	}

	@Override
	public void setFlat(int index, double value) {
		values[index] = value;
	}
}
