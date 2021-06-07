package reverseGraph.optimizers;

public final class RMSprop extends Optimizer {
	private static final double GRADIENT_RMS_DECAY = 0.9;
	private static final double DEFAULT_LEARNING_RATE = 0.001;

	private final double learningRate;
	private final double decay;

	private final double[] gradientRMS;

	private RMSprop(int size, double learningRate, double decay) {
		super(size);
		this.learningRate = learningRate;
		this.decay = decay;
		gradientRMS = new double[size];
	}

	public RMSprop(double learningRate, double decay) {
		this(0, learningRate, decay);
	}

	public RMSprop(double learningRate) {
		this(learningRate, GRADIENT_RMS_DECAY);
	}

	public RMSprop() {
		this(DEFAULT_LEARNING_RATE, GRADIENT_RMS_DECAY);
	}

	@Override
	public double computeUpdate(int index, double gradient) {
		gradientRMS[index] *= decay;
		gradientRMS[index] += gradient * gradient * (1 - decay);

		return learningRate * gradient / Math.sqrt(gradientRMS[index] + 0.001);
	}

	@Override
	public RMSprop copy(int size) {
		return new RMSprop(size, learningRate, decay);
	}
}
