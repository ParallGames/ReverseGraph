package reverseGraph.optimizers;

public final class AdaDelta extends Optimizer {
	private static final double DEFAULT_DECAY = 0.9;

	private final double decay;

	private double meanGradient = 0;
	private double meanUpdate = 0;

	public AdaDelta(double decay) {
		this.decay = decay;
	}

	public AdaDelta() {
		this(DEFAULT_DECAY);
	}

	@Override
	public double computeUpdate(double gradient) {
		meanGradient *= decay;
		meanGradient += (1 - decay) * gradient * gradient;

		double update = gradient * Math.sqrt(meanUpdate + 1e-8) / Math.sqrt(meanGradient + 1e-8);

		meanUpdate *= decay;
		meanUpdate += (1 - decay) * update * update;

		return update;
	}

	@Override
	public AdaDelta copy() {
		return new AdaDelta(decay);
	}
}
