package nl.tudelft.ti2806.pl1.mutation;

import nl.tudelft.ti2806.pl1.geneAnnotation.ReferenceGeneStorage;

/**
 * @author Maarten, Justin
 * @since 2-6-2015
 */
public class InsertionMutation extends Mutation {

	/** The node(s) inserted in the graph. */
	private int inNode;

	/**
	 * @param pre
	 *            The ID of the node before the mutation.
	 * @param post
	 *            The ID of the node after the mutation.
	 * @param insertednode
	 *            The node inserted in the graph.
	 * @param rgs
	 *            The storage containing all the interesting gene information.
	 * @param startpos
	 *            The start position on the reference genome.
	 * 
	 * @param endpos
	 *            The end position on the reference genome.
	 */
	public InsertionMutation(final int pre, final int post, final int startpos,
			final int endpos, final ReferenceGeneStorage rgs,
			final int insertednode) {
		super(pre, post, startpos, endpos, rgs);
		this.inNode = insertednode;
	}

	/**
	 * @return The node(s) inserted in the graph.
	 */
	public int getInNode() {
		return inNode;
	}

	@Override
	public String toString() {
		return "<InsMut[pre:" + getPreNode() + ",post:" + getPostNode()
				+ ",ins:" + getInNode() + "]>";
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof InsertionMutation) {
			InsertionMutation that = (InsertionMutation) other;
			return super.equals(that) && inNode == that.inNode;
		}
		return false;
	}

	@Override
	public final int hashCode() {
		return getPreNode();
	}

	@Override
	public double getScore() {
		return super.getScore()
				* ScoreMultiplier.getMult(MutationMultipliers.INDEL.name());
	}
}
