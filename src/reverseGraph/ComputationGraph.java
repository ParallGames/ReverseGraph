package reverseGraph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public final class ComputationGraph {
	private final Operation[] operations;

	/**
	 * Constructs a graph to compute an operation and its dependencies
	 *
	 * @param output the operation to compute
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
		operationsList.sort(new Comparator<Operation>() {
			@Override
			public int compare(Operation o1, Operation o2) {
				return o1.index - o2.index;
			}
		});

		return operationsList.toArray(new Operation[0]);
	}
}
