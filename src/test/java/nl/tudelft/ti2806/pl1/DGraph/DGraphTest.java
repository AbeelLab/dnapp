package nl.tudelft.ti2806.pl1.DGraph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

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

		when(node1.getId()).thenReturn(1);
		when(node1.getAllEdges()).thenReturn(Arrays.asList(edge1));
		when(node1.getInEdges()).thenReturn(new ArrayList<DEdge>());

		when(node2.getId()).thenReturn(2);
		when(node2.getAllEdges()).thenReturn(Arrays.asList(edge1, edge2));
		when(node2.getInEdges()).thenReturn(new ArrayList<DEdge>());

		when(node3.getId()).thenReturn(3);
		when(node3.getAllEdges()).thenReturn(Arrays.asList(edge2));

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

}
