package reverseGraph.optimizers;

public class Adam extends Optimizer {

	private final double learningRate;
	private final double firstMomentDecay;
	private final double secondMomentDecay;

	private double firstMoment;
	private double secondMoment;

	public Adam(double learningRate, double firstMomentDecay, double secondMomentDecay) {
		this.learningRate = learningRate;
		this.firstMomentDecay = firstMomentDecay;
		this.secondMomentDecay = secondMomentDecay;
	}

	@Override
	public double computeUpdate(double gradient) {
		firstMoment *= firstMomentDecay;
		firstMoment += (1 - firstMomentDecay) * gradient;

		secondMoment *= secondMomentDecay;
		secondMoment += (1 - secondMomentDecay) * gradient * gradient;

		return learningRate * firstMoment / (Math.sqrt(secondMoment) + 1e-8);
	}

	@Override
	public Optimizer copy() {
		return new Adam(learningRate, firstMomentDecay, secondMomentDecay);
	}

}
