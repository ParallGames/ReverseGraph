package reverseGraph.optimizers;

public final class Momentum extends Optimizer {
	private static final double DEFAULT_RESISTANCE = 0.9;
	private static final double DEFAULT_LEARNING_RATE = 0.001;

	private final double learningRate;
	private final double resistance;

	private final double[] momentum;

	private Momentum(int size, double learningRate, double resistance) {
		super(size);
		this.learningRate = learningRate;
		this.resistance = resistance;
		this.momentum = new double[size];
	}

	public Momentum(double learningRate, double resistance) {
		this(0, learningRate, resistance);
	}

	public Momentum(double learningRate) {
		this(learningRate, DEFAULT_RESISTANCE);
	}

	public Momentum() {
		this(DEFAULT_LEARNING_RATE, DEFAULT_RESISTANCE);
	}

	@Override
	public double computeUpdate(int index, double gradient) {
		momentum[index] *= resistance;

		momentum[index] += (1 - resistance) * gradient;

		return momentum[index] * learningRate;
	}

	@Override
	public Momentum copy(int size) {
		return new Momentum(size, learningRate, resistance);
	}
}
