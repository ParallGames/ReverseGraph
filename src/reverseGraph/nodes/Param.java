package reverseGraph.nodes;

import reverseGraph.WrongSizeException;

public final class Param extends Derivable {
	public Param(int outSize) {
		super(outSize);
	}

	public Param(double[] initialValues) {
		super(initialValues);
	}

	public void setValues(double[] values) {
		if (values.length != this.values.length) {
			throw new WrongSizeException(values.length, this.values.length);
		}

		for (int i = 0; i < values.length; i++) {
			this.values[i] = values[i];
		}
	}
}
