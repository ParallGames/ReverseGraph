package reverseGraph;

import java.util.ArrayList;
import java.util.HashSet;

import reverseGraph.nodes.Node;
import reverseGraph.nodes.Param;
import reverseGraph.nodes.operations.Operation;
import reverseGraph.optimizers.Optimizer;

public final class OptimizationGraph {
	private final Param[] params;
	private final Operation[] operations;

	private final Optimizer optimizer;

	private double l1 = 0;

	/**
	 * Constructs a graph to compute an operation and its dependencies
	 *
	 * @param output    the operation to compute
	 * @param optimizer an optimizer that updates the parameters
	 */
	public OptimizationGraph(Operation output, Optimizer optimizer) {
		ArrayList<Operation> operationsList = new ArrayList<>();

		params = findDependentNodes(output, operationsList);

		operations = sortOperations(operationsList);

		int paramsCount = 0;

		for (Param p : params) {
			paramsCount += p.values.length;
		}

		this.optimizer = optimizer.copy(paramsCount);
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

		operations[operations.length - 1].derivatives[0] = 1;

		for (int i = operations.length - 1; i >= 0; i--) {
			operations[i].computeDependenciesDerivatives();
		}
	}

	/**
	 * Minimizes the graph output by updating its parameters
	 */
	public void minimize() {
		int index = 0;

		for (int i = 0; i < params.length; i++) {
			for (int a = 0; a < params[i].values.length; a++) {
				double gradient = params[i].derivatives[a] + l1 * params[i].values[a];

				params[i].values[a] -= optimizer.computeUpdate(index, gradient);

				index++;
			}
			params[i].resetDerivatives();
		}
	}

	private static Param[] findDependentNodes(Operation operation, ArrayList<Operation> operationsList) {
		final HashSet<Operation> operationsSet = new HashSet<>();
		final HashSet<Param> paramsSet = new HashSet<>();

		operationsList.add(operation);
		operationsSet.add(operation);

		for (int i = 0; i < operationsList.size(); i++) {
			Operation op = operationsList.get(i);

			for (Node dep : op.getDependencies()) {
				if (dep instanceof Operation) {
					if (operationsSet.add((Operation) dep)) {
						operationsList.add((Operation) dep);
					}
				} else if (dep instanceof Param) {
					paramsSet.add((Param) dep);
				}
			}
		}

		return paramsSet.toArray(new Param[0]);
	}

	private static Operation[] sortOperations(ArrayList<Operation> operationsList) {
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

		return sortedOperations.toArray(new Operation[0]);
	}

	public void setL1Regularization(double l1) {
		this.l1 = l1;
	}
}
