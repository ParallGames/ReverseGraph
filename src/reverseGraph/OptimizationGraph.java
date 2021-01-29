package reverseGraph;

import java.util.ArrayList;
import java.util.Comparator;
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
			paramsCount += p.values.flat.length;
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
		for (int i = 0; i < operations.length - 1; i++) {
			for (int a = 0; a < operations[i].derivatives.flat.length; a++) {
				operations[i].derivatives.flat[a] = 0;
			}
		}

		for (int i = 0; i < operations[operations.length - 1].derivatives.flat.length; i++) {
			operations[operations.length - 1].derivatives.flat[i] = 1;
		}

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
			for (int a = 0; a < params[i].values.flat.length; a++) {
				double gradient = params[i].derivatives.flat[a] + l1 * params[i].values.flat[a];

				params[i].values.flat[a] -= optimizer.computeUpdate(index, gradient);

				index++;
			}

			for (int a = 0; a < params[i].derivatives.flat.length; a++) {
				params[i].derivatives.flat[a] = 0;
			}
		}
	}

	/**
	 * Maximizes the graph output by updating its parameters
	 */
	public void maximize() {
		int index = 0;

		for (int i = 0; i < params.length; i++) {
			for (int a = 0; a < params[i].values.flat.length; a++) {
				double gradient = params[i].derivatives.flat[a] + l1 * params[i].values.flat[a];

				params[i].values.flat[a] += optimizer.computeUpdate(index, gradient);

				index++;
			}

			for (int a = 0; a < params[i].derivatives.flat.length; a++) {
				params[i].derivatives.flat[a] = 0;
			}
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
		operationsList.sort(new Comparator<Operation>() {
			@Override
			public int compare(Operation o1, Operation o2) {
				return o1.index - o2.index;
			}
		});

		return operationsList.toArray(new Operation[0]);
	}

	public void setL1Regularization(double l1) {
		this.l1 = l1;
	}
}
