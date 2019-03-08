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
		if (values.length != getSize()) {
			throw new WrongSizeException(values.length, getSize());
		}

		for (int i = 0; i < getSize(); i++) {
			outputs[i] = values[i];
		}
	}

	public void update(double[] updates) {
		if (updates.length != getSize()) {
			throw new WrongSizeException(updates.length, getSize());
		}

		for (int i = 0; i < getSize(); i++) {
			outputs[i] += updates[i];
		}
	}
}
