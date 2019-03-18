package reverseGraph.optimizers;

public final class Adam extends Optimizer {
	private final double learningRate;
	private final double firstMomentDecay;
	private final double secondMomentDecay;

	private final double[] firstMoment;
	private final double[] secondMoment;

	private Adam(int size, double learningRate, double firstMomentDecay, double secondMomentDecay) {
		super(size);
		this.learningRate = learningRate;
		this.firstMomentDecay = firstMomentDecay;
		this.secondMomentDecay = secondMomentDecay;
		firstMoment = new double[size];
		secondMoment = new double[size];
	}

	public Adam(double learningRate, double firstMomentDecay, double secondMomentDecay) {
		this(0, learningRate, firstMomentDecay, secondMomentDecay);
	}

	@Override
	public double computeUpdate(int index, double gradient) {
		firstMoment[index] *= firstMomentDecay;
		firstMoment[index] += (1 - firstMomentDecay) * gradient;

		secondMoment[index] *= secondMomentDecay;
		secondMoment[index] += (1 - secondMomentDecay) * gradient * gradient;

		return learningRate * firstMoment[index] / (Math.sqrt(secondMoment[index]) + 1e-8);
	}

	@Override
	public Adam copy(int size) {
		return new Adam(size, learningRate, firstMomentDecay, secondMomentDecay);
	}
}
