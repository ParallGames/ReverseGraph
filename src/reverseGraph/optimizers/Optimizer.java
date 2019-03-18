package reverseGraph.optimizers;

public abstract class Optimizer {
	protected final int size;

	public Optimizer(int size) {
		this.size = size;
	}

	public abstract double computeUpdate(int index, double gradient);

	public abstract Optimizer copy(int size);
}
