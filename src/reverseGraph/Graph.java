package reverseGraph;

import java.util.ArrayList;
import java.util.HashSet;

import reverseGraph.nodes.Node;
import reverseGraph.nodes.Param;
import reverseGraph.nodes.operations.Operation;
import reverseGraph.optimizers.Optimizer;

public final class Graph {
	private final Param[] params;
	private final Operation[] operations;

	private final ArrayList<Param> paramsList = new ArrayList<>();
	private final HashSet<Param> paramsSet = new HashSet<>();

	private final ArrayList<Operation> operationsList = new ArrayList<>();
	private final HashSet<Operation> operationsSet = new HashSet<>();

	private final double[] derivatives;
	private final Optimizer[] optimizers;

	private int exemplesCount = 0;

	private double l1 = 0;

	/**
	 * Constructs a graph to compute an operation and its dependencies
	 * 
	 * @param operation the operation to compute
	 * @param optimizer an optimizer that updates the parameters
	 */
	public Graph(Operation operation, Optimizer optimizer) {
		findDependentNodes(operation);
		paramsSet.clear();
		operationsSet.clear();

		sortOperations();

		params = paramsList.toArray(new Param[0]);
		paramsList.clear();

		operations = operationsList.toArray(new Operation[0]);
		operationsList.clear();

		int paramsCount = 0;

		for (Param p : params) {
			paramsCount += p.getSize();
		}

		derivatives = new double[paramsCount];

		optimizers = new Optimizer[paramsCount];

		for (int i = 0; i < optimizers.length; i++) {
			optimizers[i] = optimizer.copy();
		}
	}

	private void addParam(Param param) {
		if (paramsSet.add(param)) {
			paramsList.add(param);
		}
	}

	private void addOperation(Operation operation) {
		if (operationsSet.add(operation)) {
			operationsList.add(operation);
		}
	}

	/**
	 * Compute all operations of the graph
	 */
	public void compute() {
		for (int i = 0; i < operations.length; i++) {
			operations[i].compute();
		}
	}

	/**
	 * Compute derivatives for the last computed values
	 */
	public void computeDerivatives() {
		for (Operation op : operations) {
			op.resetDerivatives();
		}

		for (Param p : params) {
			p.resetDerivatives();
		}

		operations[operations.length - 1].derivatives[0] = 1;

		for (int i = operations.length - 1; i >= 0; i--) {
			operations[i].computeDependenciesDerivatives();
		}

		int index = 0;
		for (int i = 0; i < params.length; i++) {
			final int size = params[i].getSize();
			for (int a = 0; a < size; a++) {
				derivatives[index] += params[i].derivatives[a];
				index++;
			}
		}

		exemplesCount++;
	}

	/**
	 * Minimizes the graph output by updating its parameters
	 */
	public void minimize() {
		if (exemplesCount == 0) {
			return;
		}

		int index = 0;

		for (int i = 0; i < params.length; i++) {
			Param p = params[i];

			final int size = p.getSize();

			for (int a = 0; a < size; a++) {
				double gradient = derivatives[index] / exemplesCount + l1 * p.values[a];

				p.values[a] -= optimizers[index].computeUpdate(gradient);

				derivatives[index] = 0;

				index++;
			}
		}

		exemplesCount = 0;
	}

	private void findDependentNodes(Operation operation) {
		addOperation(operation);

		for (int i = 0; i < operationsList.size(); i++) {
			Operation op = operationsList.get(i);

			for (Node dep : op.getDependencies()) {
				if (dep instanceof Operation) {
					addOperation((Operation) dep);
				} else if (dep instanceof Param) {
					addParam((Param) dep);
				}
			}
		}
	}

	private void sortOperations() {
		final ArrayList<Operation> sortedOperations = new ArrayList<>();
		final HashSet<Operation> sortedOperationsSet = new HashSet<>();

		while (!operationsList.isEmpty()) {
			operationLoop: for (int i = operationsList.size() - 1; i >= 0; i--) {
				Operation op = operationsList.get(i);

				for (Node n : op.getDependencies()) {
					if (n instanceof Operation && !sortedOperationsSet.contains(n)) {
						continue operationLoop;
					}
				}

				operationsList.remove(i);

				sortedOperations.add(op);
				sortedOperationsSet.add(op);

			}
		}

		operationsList.clear();
		operationsList.addAll(sortedOperations);
	}

	public int operationsCount() {
		return operations.length;
	}

	public int paramsCount() {
		return params.length;
	}

	public void setL1Regularization(double l1) {
		this.l1 = l1;
	}
}
