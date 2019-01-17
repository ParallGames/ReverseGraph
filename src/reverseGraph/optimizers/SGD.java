package reverseGraph.optimizers;

public class SGD extends Optimizer {
	private static final double DEFAULT_LEARNING_RATE = 0.001;

	private final double learningRate;

	public SGD(double learningRate) {
		this.learningRate = learningRate;
	}

	public SGD() {
		this(DEFAULT_LEARNING_RATE);
	}

	@Override
	public double computeUpdate(double gradient) {
		return gradient * learningRate;
	}

	@Override
	public SGD copy() {
		return new SGD(learningRate);
	}
}
