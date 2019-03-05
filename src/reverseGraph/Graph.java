package reverseGraph;

import java.util.ArrayList;
import java.util.HashSet;

import reverseGraph.nodes.Node;
import reverseGraph.nodes.Param;
import reverseGraph.nodes.operations.Operation;
import reverseGraph.optimizers.Optimizer;

public class Graph {
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

		sortOperations();

		params = paramsList.toArray(new Param[0]);
		paramsList.clear();
		paramsSet.clear();

		operations = operationsList.toArray(new Operation[0]);
		operationsList.clear();
		operationsSet.clear();

		derivatives = new double[params.length];

		optimizers = new Optimizer[params.length];

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
			op.resetDerivative();
		}

		for (Param p : params) {
			p.resetDerivative();
		}

		operations[operations.length - 1].addToDerivative(1);

		for (int i = operations.length - 1; i >= 0; i--) {
			operations[i].computeDependenciesDerivative();
		}

		for (int i = 0; i < params.length; i++) {
			derivatives[i] += params[i].getDerivative();
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

		for (int i = 0; i < params.length; i++) {
			Param p = params[i];

			double gradient = derivatives[i] / exemplesCount + l1 * p.getValue();

			p.update(-optimizers[i].computeUpdate(gradient));

			derivatives[i] = 0;
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
			for (int i = operationsList.size() - 1; i >= 0; i--) {
				Operation op = operationsList.get(i);

				boolean canCompute = true;

				for (Node n : op.getDependencies()) {
					if (n instanceof Operation && !sortedOperationsSet.contains(n)) {
						canCompute = false;
						break;
					}
				}

				if (canCompute) {
					operationsList.remove(i);

					sortedOperations.add(op);
					sortedOperationsSet.add(op);
				}
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
