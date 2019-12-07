package reverseGraph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

import reverseGraph.node.Computable;
import reverseGraph.node.Node;
import reverseGraph.node.Optimizable;
import reverseGraph.node.scalar.ScalarOperation;
import reverseGraph.optimizers.Optimizer;

public final class Graph {
	private final Optimizable[] params;
	private final Computable[] operations;

	private final Optimizer optimizer;

	/**
	 * Constructs a graph to compute an operation and its dependencies
	 *
	 * @param output    the operation to compute
	 * @param optimizer an optimizer that updates the parameters
	 */
	public Graph(ScalarOperation output, Optimizer optimizer) {
		ArrayList<Computable> operationsList = new ArrayList<>();

		params = findDependentNodes(output, operationsList);

		operations = sortOperations(operationsList);

		int paramsCount = 0;

		for (Optimizable p : params) {
			paramsCount += ((Node) p).size;
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
		for (Computable c : operations) {
			c.resetDerivatives();
		}

		operations[operations.length - 1].addFlatDerivative(0, 1);

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
			for (int a = 0; a < ((Node) params[i]).size; a++) {
				params[i].update(a, -optimizer.computeUpdate(index++, params[i].getFlatDerivative(a)));
			}

			params[i].resetDerivatives();
		}
	}

	private static Optimizable[] findDependentNodes(Computable operation, ArrayList<Computable> operationsList) {
		final HashSet<Computable> operationsSet = new HashSet<>();
		final HashSet<Optimizable> paramsSet = new HashSet<>();

		operationsList.add(operation);
		operationsSet.add(operation);

		for (int i = 0; i < operationsList.size(); i++) {
			Computable op = operationsList.get(i);

			for (Node dep : op.getDependencies()) {
				if (dep instanceof Computable) {
					if (operationsSet.add((Computable) dep)) {
						operationsList.add((Computable) dep);
					}
				} else if (dep instanceof Optimizable) {
					paramsSet.add((Optimizable) dep);
				}
			}
		}

		return paramsSet.toArray(new Optimizable[0]);
	}

	private static Computable[] sortOperations(ArrayList<Computable> operationsList) {
		operationsList.sort(new Comparator<Computable>() {
			@Override
			public int compare(Computable o1, Computable o2) {
				return ((Node) o1).index - ((Node) o2).index;
			}
		});

		return operationsList.toArray(new Computable[0]);
	}
}
