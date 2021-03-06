package nl.tudelft.ti2806.pl1.gui.optionpane;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collection;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nl.tudelft.ti2806.pl1.graph.DNode;
import nl.tudelft.ti2806.pl1.gui.contentpane.NodeSelectionObserver;

import org.graphstream.graph.Node;

/**
 * @author Maarten
 * @since 18-5-2015
 */
public class SelectedNodeGroup extends JPanel implements NodeSelectionObserver {

	/** The serial version UID. */
	private static final long serialVersionUID = -4851724739205792429L;

	/** Vertical grid coordinate of ID field. */
	private static final int Y_ID = 0;

	/** Vertical grid coordinate of length field. */
	private static final int Y_LENGTH = 1;

	/** Vertical grid coordinate of the label of the content bar. */
	private static final int Y_CONTENT_BAR_LABEL = 2;

	/** Vertical grid coordinate of the content bar. */
	private static final int Y_CONTENT_BAR = 3;

	/** Vertical grid coordinate of the label of the sources field. */
	private static final int Y_SOURCES_LABEL = 4;

	/** Vertical grid coordinate of the sources. */
	private static final int Y_SOURCES = 5;

	/** Horizontal weight of info labels containing the values. */
	private static final int XW_VAL_LABEL = 10;

	/** The default visible title. */
	private static final String DEFAULT_TITLE = "<html><b>Selected node";

	/** The insets. */
	private static final Insets INSETS = new Insets(2, 5, 2, 5);

	/** The size of the group. */
	private static final Dimension SIZE = new Dimension(
			OptionsPane.MAX_CHILD_WIDTH, 120);

	/** The width of the node content bar. */
	private static final int NCB_WIDTH = 15;

	/** The length of the node content bar. */
	private static final int NCB_LENGTH = 160;

	/** The grid bag constraints for the layout manager. */
	private GridBagConstraints gbc = new GridBagConstraints();

	/** The labels containing the node statistics. */
	private JLabel lblIDL = mkLabel("ID:", RIGHT_ALIGNMENT),
			lblContentLengthL = mkLabel("Length:", RIGHT_ALIGNMENT),
			lblSourcesL = mkLabel("Sources:", LEFT_ALIGNMENT);

	/** The labels containing the node statistics. */
	private JLabel lblID = mkLabel("", LEFT_ALIGNMENT),
			lblContentLength = mkLabel("", LEFT_ALIGNMENT),
			lblSources = mkLabel("", LEFT_ALIGNMENT);

	/** The simple bar chart showing the distribution of nucleotides. */
	private NodeContentBar nodeChart;

	/** Whether this group should be visible or not. */
	private boolean show = false;

	/** Initialize the group layout panel. */
	public SelectedNodeGroup() {
		super();
		setLayout(new GridBagLayout());
		setMaximumSize(SIZE);
		setupGBC();
		setAlignmentY(TOP_ALIGNMENT);
		setBorder(BorderFactory.createTitledBorder("<html><b>" + DEFAULT_TITLE));

		placeID();
		placeLength();
		placeDistr();
		placeSources();
	}

	/** Place the node id elements. */
	private void placeID() {
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridy = SelectedNodeGroup.Y_ID;
		gbc.gridx = 0;
		gbc.weightx = 1;
		add(lblIDL, gbc);
		gbc.gridx = 1;
		gbc.weightx = XW_VAL_LABEL;
		add(lblID, gbc);
	}

	/** Place the content length elements. */
	private void placeLength() {
		gbc.gridy = Y_LENGTH;
		gbc.gridx = 0;
		gbc.weightx = 1;
		add(lblContentLengthL, gbc);
		gbc.gridx = 1;
		gbc.weightx = XW_VAL_LABEL;
		add(lblContentLength, gbc);
	}

	/** Place the content distribution elements. */
	private void placeDistr() {
		gbc.gridy = Y_CONTENT_BAR_LABEL;
		gbc.gridx = 0;
		gbc.weightx = 1;
		gbc.gridwidth = 2;
		add(new JLabel("Nucleotide type distribution:"), gbc);

		nodeChart = new NodeContentBar(NCB_WIDTH, NCB_LENGTH);
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridy = Y_CONTENT_BAR;
		gbc.gridwidth = 2;
		add(nodeChart, gbc);
	}

	/** Place the sources elements. */
	private void placeSources() {
		gbc.gridy = Y_SOURCES_LABEL;
		gbc.gridx = 0;
		gbc.weightx = 1;
		add(lblSourcesL, gbc);
		gbc.gridy = Y_SOURCES;
		gbc.gridwidth = 2;
		gbc.weightx = 2.0;
		add(lblSources, gbc);
	}

	/**
	 * Makes a label with the input text.
	 * 
	 * @param text
	 *            Input text.
	 * @param align
	 *            Align
	 * @return JLabel object with the input text.
	 */
	private JLabel mkLabel(final String text, final float align) {
		JLabel ret = new JLabel(text);
		ret.setAlignmentX(align);
		return ret;
	}

	/** Sets up the grid bag constraints. */
	private void setupGBC() {
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.insets = INSETS;
		gbc.weightx = 1;
		gbc.weighty = 1;
	}

	/**
	 * Sets the visible title for the grouping.
	 * 
	 * @param newTitle
	 *            The new title.
	 */
	public final void setTitle(final String newTitle) {
		setBorder(BorderFactory.createTitledBorder(newTitle));
	}

	@Override
	public final void update(final Node node, final Set<DNode> innerNodes) {
		show = innerNodes.size() == 1;
		if (show) {
			DNode selectedNode = innerNodes.iterator().next();
			lblID.setText(String.valueOf(selectedNode.getId()));
			lblContentLength.setText(String.valueOf(selectedNode.getContent()
					.length()));
			lblSources.setText(collectionToString(selectedNode.getSources()));
			nodeChart.analyseString(selectedNode.getContent());
		}
		revalidate();
	}

	/**
	 * @param col
	 *            The collection to stringify.
	 * @param <A>
	 *            The type of the collection items.
	 * @return The basic string representation of <code>col</code>.
	 */
	private <A> String collectionToString(final Collection<A> col) {
		StringBuilder sb = new StringBuilder("<html>");
		for (A item : col) {
			sb.append(item.toString());
			sb.append("<br>");
		}
		return sb.append("</html>").toString();
	}

	@Override
	public boolean isVisible() {
		return super.isVisible() && show;
	}

	@Override
	public final String toString() {
		return this.getClass().toString();
	}
}
