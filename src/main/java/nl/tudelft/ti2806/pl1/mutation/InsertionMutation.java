package nl.tudelft.ti2806.pl1.mutation;

/**
 * @author Maarten, Justin
 * @since 2-6-2015
 */
public class InsertionMutation extends Mutation {

	/**
	 * The nodes inserted in the graph.
	 */
	private int inNode;

	/**
	 * @param pre
	 *            The ID of the node before the mutation.
	 * @param post
	 *            The ID of the node after the mutation.
	 * @param insertednode
	 *            The node inserted in the graph.
	 */
	public InsertionMutation(final int pre, final int post,
			final int insertednode) {
		super(pre, post);
		this.inNode = insertednode;
	}

	/**
	 * @return The nodes inserted in the graph.
	 */
	public int getNode() {
		return inNode;
	}

	@Override
	public String toString() {
		return "prenode: " + getPreNode() + " postnode: " + getPostNode()
				+ " insertednode: " + this.inNode;
	}

}
