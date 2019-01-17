package vnl.model;

import vnl.Activation;

public class LayerModel {
	public final int outputSize;
	public final Activation activation;

	public LayerModel(int outputSize, Activation activation) {
		this.outputSize = outputSize;
		this.activation = activation;
	}
}
