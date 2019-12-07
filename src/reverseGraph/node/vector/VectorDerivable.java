package reverseGraph.node.vector;

import reverseGraph.node.Derivable;

public abstract class VectorDerivable extends VectorNode implements Derivable {

	public VectorDerivable(int width) {
		super(width);
	}

	public abstract double getDerivative(int index);

	public abstract void addDerivative(int index, double value);

	@Override
	public final double getFlatDerivative(int index) {
		return getDerivative(index);
	}

	@Override
	public final void addFlatDerivative(int index, double value) {
		addDerivative(index, value);
	}

}
