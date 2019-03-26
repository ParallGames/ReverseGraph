package reverseGraph.optimizers;

public class AMSGrad extends Optimizer {
	private final double learningRate;
	private final double firstMomentDecay;
	private final double secondMomentDecay;

	private final double[] firstMoment;
	private final double[] secondMoment;
	private final double[] maxSecondMoment;

	private AMSGrad(int size, double learningRate, double firstMomentDecay, double secondMomentDecay) {
		super(size);
		this.learningRate = learningRate;
		this.firstMomentDecay = firstMomentDecay;
		this.secondMomentDecay = secondMomentDecay;
		firstMoment = new double[size];
		secondMoment = new double[size];
		maxSecondMoment = new double[size];
	}

	public AMSGrad(double learningRate, double firstMomentDecay, double secondMomentDecay) {
		this(0, learningRate, firstMomentDecay, secondMomentDecay);
	}

	@Override
	public double computeUpdate(int index, double gradient) {
		firstMoment[index] *= firstMomentDecay;
		firstMoment[index] += (1 - firstMomentDecay) * gradient;

		secondMoment[index] *= secondMomentDecay;
		secondMoment[index] += (1 - secondMomentDecay) * gradient * gradient;

		maxSecondMoment[index] = maxSecondMoment[index] > secondMoment[index] ? maxSecondMoment[index]
				: secondMoment[index];

		return learningRate * firstMoment[index] / (Math.sqrt(maxSecondMoment[index]) + 1e-8);
	}

	@Override
	public AMSGrad copy(int size) {
		return new AMSGrad(size, learningRate, firstMomentDecay, secondMomentDecay);
	}
}
