package nl.tudelft.ti2806.pl1.mutation;

import java.util.ArrayList;
import java.util.Collection;

import nl.tudelft.ti2806.pl1.DGraph.DGraph;
import nl.tudelft.ti2806.pl1.DGraph.DNode;

/**
 * 
 * @author Justin, Maarten
 * @since 10-6-2015
 */
public final class MutationFinder {

	/**
	 * Reference genome name.
	 */
	private static final String REFERENCE_GENOME = "TKK_REF";

	/**
	 */
	private MutationFinder() {
	}

	/**
	 * Finds the simple Insertion mutations of a DGraph.
	 * 
	 * @param graph
	 *            The DGraph.
	 * @return A collection of the Insertion mutations.
	 */
	public static Collection<InsertionMutation> findInsertionMutations(
			final DGraph graph) {
		ArrayList<InsertionMutation> ins = new ArrayList<InsertionMutation>();
		Collection<DNode> nodes = graph.getReference(REFERENCE_GENOME);
		for (DNode node : nodes) {
			for (DNode next : node.getNextNodes()) {
				if (!(next.getSources().contains(REFERENCE_GENOME))) {
					Collection<DNode> nextnodes = next.getNextNodes();
					if (nextnodes.size() == 1) {
						DNode endnode = nextnodes.iterator().next();
						ins.add(new InsertionMutation(node.getId(), next
								.getId(), endnode.getId()));
					}
				}
			}
		}

		return ins;
	}

	/**
	 * Finds the simple Deletion mutations of a DGraph.
	 * 
	 * @param graph
	 *            The DGraph.
	 * @return A collection of the Deletion mutations.
	 */
	public static Collection<DeletionMutation> findDeletionMutations(
			final DGraph graph) {
		ArrayList<DeletionMutation> dels = new ArrayList<DeletionMutation>();
		Collection<DNode> nodes = graph.getReference(REFERENCE_GENOME);
		for (DNode node : nodes) {
			boolean isDeletion = false;
			int countRefNodes = 0;
			int maxdepth = 0;
			DNode endnode = null;
			for (DNode next : node.getNextNodes()) {
				if (next.getDepth() > maxdepth) {
					maxdepth = next.getDepth();
					endnode = next;
				}
				if (countRefNodes > 1) {
					isDeletion = true;
				}
				if (next.getSources().contains(REFERENCE_GENOME)) {
					countRefNodes++;
				}
			}
			if (isDeletion) {
				dels.add(new DeletionMutation(node.getId(), endnode.getId()));
			}
		}
		return dels;
	}

}