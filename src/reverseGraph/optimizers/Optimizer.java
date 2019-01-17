package reverseGraph.optimizers;

public abstract class Optimizer {
	public abstract double computeUpdate(double gradient);

	public abstract Optimizer copy();
}
