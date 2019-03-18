package reverseGraph.optimizers;

public final class SGD extends Optimizer {
	private static final double DEFAULT_LEARNING_RATE = 0.001;

	private final double learningRate;

	private SGD(int size, double learningRate) {
		super(size);
		this.learningRate = learningRate;
	}

	public SGD(double learningRate) {
		this(0, learningRate);
	}

	public SGD() {
		this(DEFAULT_LEARNING_RATE);
	}

	@Override
	public double computeUpdate(int index, double gradient) {
		return gradient * learningRate;
	}

	@Override
	public SGD copy(int size) {
		return new SGD(size, learningRate);
	}
}
