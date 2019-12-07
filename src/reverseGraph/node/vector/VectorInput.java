package reverseGraph.node.vector;

import reverseGraph.node.Setable;

public class VectorInput extends VectorNode implements Setable {
	private final double[] values;

	public VectorInput(int width) {
		super(width);
		values = new double[width];

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
