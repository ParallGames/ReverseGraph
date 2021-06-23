package reverseGraph.nodes.operations;

import reverseGraph.data.Dimensions;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public class Concat extends Operation {
	private final Node[] elements;

	private static int totalSize(Node... elements) {
		int sum = 0;

		for (int i = 0; i < elements.length; i++) {
			sum += elements[i].values.flat.length;
		}

		return sum;
	}

	public Concat(Node... elements) {
		super(new Dimensions(totalSize(elements)));
		this.elements = elements;
	}

	@Override
	public void compute() {
		int index = 0;

		for (int a = 0; a < elements.length; a++) {
			for (int b = 0; b < elements[a].values.flat.length; b++) {
				values.flat[index++] = elements[a].values.flat[b];
			}
		}
	}

	@Override
	public Node[] getDependencies() {
		return elements;
	}

	@Override
	public void computeDependenciesDerivatives() {
		int index = 0;

		for (int a = 0; a < elements.length; a++) {
			if (elements[a] instanceof Derivable) {
				Derivable derivable = (Derivable) elements[a];
				for (int b = 0; b < elements[a].values.flat.length; b++) {
					derivable.derivatives.flat[b] = derivatives.flat[index++];
				}
			} else {
				index += elements[a].values.flat.length;
			}
		}
	}
}
