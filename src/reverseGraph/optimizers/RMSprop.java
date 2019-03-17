package reverseGraph.optimizers;

public final class RMSprop extends Optimizer {
	private static final double GRADIENT_RMS_DECAY = 0.9;
	private static final double DEFAULT_LEARNING_RATE = 0.001;

	private final double learningRate;
	private final double decay;

	private double gradientRMS = 0;

	public RMSprop(double learningRate, double decay) {
		this.learningRate = learningRate;
		this.decay = decay;
	}

	public RMSprop(double learningRate) {
		this(learningRate, GRADIENT_RMS_DECAY);
	}

	public RMSprop() {
		this(DEFAULT_LEARNING_RATE, GRADIENT_RMS_DECAY);
	}

	@Override
	public double computeUpdate(double gradient) {
		gradientRMS *= decay;
		gradientRMS += gradient * gradient * (1 - decay);

		return learningRate * gradient / Math.sqrt(gradientRMS + 1e-8);
	}

	@Override
	public RMSprop copy() {
		return new RMSprop(learningRate, decay);
	}
}
