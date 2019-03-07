package reverseGraph.nodes;

public abstract class Derivable extends Node {
	private final double[] derivatives;

	public Derivable(int outSize) {
		super(outSize);
		derivatives = new double[outSize];
	}

	public Derivable(double[] initialValues) {
		super(initialValues);
		derivatives = new double[initialValues.length];
	}

	public void resetDerivatives() {
		for (int i = 0; i < getSize(); i++) {
			derivatives[i] = 0;
		}
	}

	public void addToDerivatives(double[] values) {
		for (int i = 0; i < getSize(); i++) {
			derivatives[i] += values[i];
		}
	}

	public double[] getDerivatives() {
		return derivatives;
	}
}
