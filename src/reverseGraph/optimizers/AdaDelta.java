package reverseGraph.optimizers;

public final class AdaDelta extends Optimizer {
	private static final double DEFAULT_DECAY = 0.9;

	private final double decay;

	private final double[] meanGradient;
	private final double[] meanUpdate;

	private AdaDelta(int size, double decay) {
		super(size);
		this.decay = decay;
		meanGradient = new double[size];
		meanUpdate = new double[size];
	}

	public AdaDelta(double decay) {
		this(0, decay);
	}

	public AdaDelta() {
		this(DEFAULT_DECAY);
	}

	@Override
	public double computeUpdate(int index, double gradient) {
		meanGradient[index] *= decay;
		meanGradient[index] += (1 - decay) * gradient * gradient;

		double update = gradient * Math.sqrt(meanUpdate[index] + 1e-8) / Math.sqrt(meanGradient[index] + 1e-8);

		meanUpdate[index] *= decay;
		meanUpdate[index] += (1 - decay) * update * update;

		return update;
	}

	@Override
	public AdaDelta copy(int size) {
		return new AdaDelta(size, decay);
	}
}
