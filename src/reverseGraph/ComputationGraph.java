package reverseGraph;

import java.util.ArrayList;
import java.util.HashSet;

import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public class ComputationGraph {
	private final Operation[] operations;

	/**
	 * Constructs a graph to compute an operation and its dependencies
	 * 
	 * @param output    the operation to compute
	 * @param optimizer an optimizer that updates the parameters
	 */
	public ComputationGraph(Operation output) {
		operations = sortOperations(findDependentOperations(output));
	}

	/**
	 * Compute all operations of the graph
	 */
	public void compute() {
		for (int i = 0; i < operations.length; i++) {
			operations[i].compute();
		}
	}

	private static ArrayList<Operation> findDependentOperations(Operation operation) {
		final HashSet<Operation> operationsSet = new HashSet<>();
		final ArrayList<Operation> operationsList = new ArrayList<>();

		operationsList.add(operation);
		operationsSet.add(operation);

		for (int i = 0; i < operationsList.size(); i++) {
			Operation op = operationsList.get(i);

			for (Node dep : op.getDependencies()) {
				if (dep instanceof Operation) {
					if (operationsSet.add((Operation) dep)) {
						operationsList.add((Operation) dep);
					}
				}
			}
		}

		return operationsList;
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
}
