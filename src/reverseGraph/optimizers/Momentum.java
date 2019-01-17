package reverseGraph.optimizers;

public class Momentum extends Optimizer {
	private static final double DEFAULT_RESISTANCE = 0.9;
	private static final double DEFAULT_LEARNING_RATE = 0.001;

	private final double learningRate;
	private final double resistance;

	private double momentum = 0;

	public Momentum(double learningRate, double resistance) {
		this.learningRate = learningRate;
		this.resistance = resistance;
	}

	public Momentum(double learningRate) {
		this(learningRate, DEFAULT_RESISTANCE);
	}

	public Momentum() {
		this(DEFAULT_LEARNING_RATE, DEFAULT_RESISTANCE);
	}

	@Override
	public double computeUpdate(double gradient) {
		gradient *= learningRate;

		momentum *= resistance;

		momentum += (1 - resistance) * gradient;

		return momentum;
	}

	@Override
	public Optimizer copy() {
		return new Momentum(learningRate, resistance);
	}
}
