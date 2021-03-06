package nl.tudelft.ti2806.pl1.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import nl.tudelft.ti2806.pl1.exceptions.InvalidGenomeIdException;
import nl.tudelft.ti2806.pl1.graph.DEdge;
import nl.tudelft.ti2806.pl1.graph.DGraph;
import nl.tudelft.ti2806.pl1.graph.DNode;
import nl.tudelft.ti2806.pl1.gui.contentpane.ViewArea;
import nl.tudelft.ti2806.pl1.mutation.ComplexMutation;
import nl.tudelft.ti2806.pl1.mutation.DeletionMutation;
import nl.tudelft.ti2806.pl1.mutation.InsertionMutation;
import nl.tudelft.ti2806.pl1.mutation.PointMutation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DGraphTest {

	DGraph graph;

	DNode node1;
	DNode node2;
	DNode node3;

	DEdge edge1;
	DEdge edge2;

	@Before
	public void setup() {
		graph = new DGraph();

		node1 = mock(DNode.class);
		node2 = mock(DNode.class);
		node3 = mock(DNode.class);

		edge1 = mock(DEdge.class);
		edge2 = mock(DEdge.class);

		HashSet<String> reference1 = new HashSet<String>();
		reference1.add("tkk-1");

		HashSet<String> reference2 = new HashSet<String>();
		reference2.add("tkk-1");
		reference2.add("tkk-2");

		HashSet<String> reference3 = new HashSet<String>();
		reference3.add("tkk-3");

		when(node1.getId()).thenReturn(1);
		when(node1.getAllEdges()).thenReturn(Arrays.asList(edge1));
		when(node1.getInEdges()).thenReturn(new ArrayList<DEdge>());
		when(node1.getSources()).thenReturn(reference1);
		when(node1.getContent()).thenReturn("content");

		when(node2.getId()).thenReturn(2);
		when(node2.getAllEdges()).thenReturn(Arrays.asList(edge1, edge2));
		when(node2.getInEdges()).thenReturn(new ArrayList<DEdge>());
		when(node2.getSources()).thenReturn(reference2);

		when(node3.getId()).thenReturn(3);
		when(node3.getAllEdges()).thenReturn(Arrays.asList(edge2));
		when(node3.getSources()).thenReturn(reference3);

		when(edge1.getStartNode()).thenReturn(node1);
		when(edge1.getEndNode()).thenReturn(node2);

		when(edge2.getStartNode()).thenReturn(node2);
		when(edge2.getEndNode()).thenReturn(node3);

	}

	@After
	public void teardown() {
		node1 = null;
		graph = null;
	}

	@Test
	public void addNewNodeTest() {
		assertTrue(graph.addDNode(node1));
	}

	@Test
	public void addExistingTest() {
		graph.addDNode(node1);
		assertFalse(graph.addDNode(node1));
	}

	@Test
	public void addedNodeIncreasesSizeTest() {
		graph.addDNode(node1);
		assertEquals(graph.getNodeCount(), 1);
	}

	@Test
	public void deleteNonExistingNodeTest() {
		assertFalse(graph.removeDNode(node1));
	}

	@Test
	public void deleteExistingNodeTest() {
		graph.addDNode(node1);
		when(node1.getAllEdges()).thenReturn(new ArrayList<DEdge>());
		assertTrue(graph.removeDNode(node1));
	}

	@Test
	public void deleteNonExistingNodeIDTest() {
		assertFalse(graph.removeDNode(1));
	}

	@Test
	public void deleteExistingNodeIDTest() {
		graph.addDNode(node1);
		when(node1.getAllEdges()).thenReturn(new ArrayList<DEdge>());
		assertTrue(graph.removeDNode(1));
	}

	@Test
	public void deleteNodeDeletesEdges() {
		graph.addDNode(node1);
		graph.addDNode(node2);
		graph.addDNode(node3);
		graph.removeDNode(2);
		verify(node1).deleteEdge(edge1);
		verify(node3).deleteEdge(edge2);
	}

	@Test
	public void addEdgeWithoutStartEndNodeInGraph() {
		DEdge edge = mock(DEdge.class);
		DNode node = mock(DNode.class);
		when(edge.getStartNode()).thenReturn(node);
		assertFalse(graph.addDEdge(edge));
	}

	@Test
	public void addEdgeTest() {
		graph.addDNode(node1);
		graph.addDNode(node2);
		assertTrue(graph.addDEdge(edge1));
	}

	@Test
	public void addExistingEdge() {
		graph.addDNode(node1);
		graph.addDNode(node2);
		graph.addDEdge(edge1);
		assertFalse(graph.addDEdge(edge1));
	}

	@Test
	public void removeExistingEdgeTest() {
		graph.addDNode(node1);
		graph.addDNode(node2);
		graph.addDEdge(edge1);
		assertTrue(graph.removeDEdge(edge1));
		verify(node1).deleteEdge(edge1);
		verify(node2).deleteEdge(edge1);
	}

	@Test
	public void removeNonExistingEdge() {
		graph.addDNode(node1);
		graph.addDNode(node2);
		assertFalse(graph.removeDEdge(edge1));
	}

	@Test
	public void removeEdgeNonExistentStartEndNode() {
		assertFalse(graph.removeDEdge(edge1));
	}

	@Test
	public void addNodeIncreasesNodeCountTest() {
		int n = graph.getNodeCount();
		graph.addDNode(node1);
		assertEquals(graph.getNodeCount(), n + 1);
	}

	@Test
	public void removeNodesDecreasesNodeCount() {
		graph.addDNode(node1);
		int n = graph.getNodeCount();
		graph.removeDNode(node1);
		assertEquals(graph.getNodeCount(), n - 1);
	}

	@Test
	public void addEdgeIncreasesEdgeSize() {
		int n = graph.getEdges().size();
		graph.addDNode(node1);
		graph.addDNode(node2);
		graph.addDEdge(edge1);
		assertEquals(graph.getEdges().size(), n + 1);
	}

	@Test
	public void deleteEdgeDecreasesEdgeSize() {
		graph.addDNode(node1);
		graph.addDNode(node2);
		graph.addDEdge(edge1);
		int n = graph.getEdges().size();
		graph.removeDEdge(edge1);
		assertEquals(graph.getEdges().size(), n - 1);
	}

	@Test
	public void referencesAreAddedTest1() {
		graph.addDNode(node1);
		assertTrue(graph.getReferences().get("tkk-1").contains(node1));
	}

	@Test
	public void referencesAreAddedTest2() {
		graph.addDNode(node1);
		assertEquals(graph.getReferences().size(), 1);
	}

	@Test
	public void referencesAreAddedTest3() {
		graph.addDNode(node1);
		graph.addDNode(node2);
		assertEquals(graph.getReferences().get("tkk-1").size(), 2);
	}

	@Test
	public void removeReferenceNodeTest() {
		graph.addDNode(node1);
		graph.removeDNode(node1);
		assertEquals(graph.getReferences().size(), 0);
	}

	@Test
	public void getReferenceTest1() {
		graph.addDNode(node1);
		assertTrue(graph.getReference("tkk-1").contains(node1));
	}

	@Test
	public void getReferenceTest2() {
		assertTrue(graph.getReference("hs").isEmpty());
	}

	@Test
	public void gettersTest() {
		assertEquals(graph.getReferencesSet(), new HashSet<String>());
		assertEquals(String.valueOf(Integer.MIN_VALUE), graph.getSelected());
		assertEquals(0, graph.getReferenceLength());
		assertEquals("TKK_REF", graph.getRefGenomeName());
		assertEquals(new ArrayList<DNode>(), graph.getRefGenome());
		assertEquals(null, graph.getDNode(1));
		assertEquals(null, graph.getStart());
	}

	@Test(expected = InvalidGenomeIdException.class)
	public void setInvalidRefGenomeTest() {
		graph.setRefGenomeName("INVALID");
	}

	@Test
	public void setGenomeTest() {
		HashMap<String, Collection<DNode>> mp = new HashMap<String, Collection<DNode>>();
		Collection<DNode> c = new HashSet<DNode>();
		c.add(node1);
		mp.put("tkk-1", c);
		graph.setReferences(mp);
		graph.setRefGenomeName("tkk-1");
	}

	@Test
	public void testGetDNodes() {
		ViewArea va = mock(ViewArea.class);
		graph.addDNode(node1);
		graph.addDNode(node2);
		when(va.isContained(node1)).thenReturn(true);
		ArrayList<DNode> ret = new ArrayList<DNode>();
		ret.add(node1);
		assertEquals(graph.getDNodes(va), ret);
	}

	@Test
	public void testToString() {
		graph.addDNode(node1);
		graph.addDNode(node2);
		graph.addDEdge(edge1);
		Map<Integer, DNode> mp = new HashMap<Integer, DNode>();
		mp.put(node1.getId(), node1);
		mp.put(node2.getId(), node2);
		Collection<DEdge> edges = new HashSet<DEdge>();
		edges.add(edge1);
		assertEquals(graph.toString(), mp + " " + edges);
	}

	@Test
	public void testAddInvalidEdge() {
		graph.addDNode(node1);
		assertFalse(graph.addDEdge(edge1));
	}

	@Test
	public void testRemoveInvalidEdge() {
		graph.addDNode(node1);
		assertFalse(graph.removeDEdge(edge1));
	}

	@Test
	public void testIsRefGeneSet() {
		assertTrue(graph.isRefGenSet());
	}

	@Test
	public void testSetters() {
		String sel = "2";
		graph.setSelected(sel);
		assertEquals(sel, graph.getSelected());

		Collection<PointMutation> pm = new HashSet<PointMutation>();
		pm.add(mock(PointMutation.class));
		graph.setPointMutations(pm);
		assertEquals(pm, graph.getPointMutations());

		Collection<ComplexMutation> cm = new HashSet<ComplexMutation>();
		cm.add(mock(ComplexMutation.class));
		graph.setComplexMutations(cm);
		assertEquals(cm, graph.getComplexMutations());

		Collection<DeletionMutation> dm = new HashSet<DeletionMutation>();
		dm.add(mock(DeletionMutation.class));
		graph.setDeletionMutations(dm);
		assertEquals(dm, graph.getDelMutations());

		Collection<InsertionMutation> im = new HashSet<InsertionMutation>();
		im.add(mock(InsertionMutation.class));
		graph.setInsertionMutations(im);
		assertEquals(im, graph.getInsMutations());

		graph.setStart(node2);
		assertEquals(node2, graph.getStart());

		Collection<DEdge> edges = new HashSet<DEdge>();
		edges.add(edge2);
		graph.setEdges(edges);
		assertEquals(edges, graph.getEdges());
	}
}
