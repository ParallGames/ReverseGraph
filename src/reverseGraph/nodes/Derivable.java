package reverseGraph.nodes;

import reverseGraph.WrongSizeException;

public abstract class Derivable extends Node {
	public final double[] derivatives;

	public Derivable(int outSize) {
		super(outSize);
		derivatives = new double[outSize];
	}

	public Derivable(double[] initialValues) {
		super(initialValues);
		derivatives = new double[initialValues.length];
	}

	public void resetDerivatives() {
		for (int i = 0; i < this.values.length; i++) {
			derivatives[i] = 0;
		}
	}

	public void addToDerivatives(double[] values) {
		if (values.length != this.values.length) {
			throw new WrongSizeException(values.length, this.values.length);
		}

		for (int i = 0; i < values.length; i++) {
			derivatives[i] += values[i];
		}
	}
}
