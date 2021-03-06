package nl.tudelft.ti2806.pl1.zoomlevels;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import nl.tudelft.ti2806.pl1.graph.ConvertDGraph;
import nl.tudelft.ti2806.pl1.graph.DEdge;
import nl.tudelft.ti2806.pl1.graph.DGraph;
import nl.tudelft.ti2806.pl1.graph.DNode;
import nl.tudelft.ti2806.pl1.mutation.InsertionMutation;
import nl.tudelft.ti2806.pl1.mutation.MutationFinder;

import org.graphstream.graph.Graph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InDelCollapserInsertionsTest {

	DGraph graph;
	DNode start, end, insertion;
	DEdge si, ie, se;
	HashSet<String> sources;
	Graph gsg;
	HashSet<String> insertSrc;

	@Before
	public void setup() {
		sources = new HashSet<String>(Arrays.asList("TKK_REF", "REF1", "REF2"));
		insertSrc = new HashSet<String>(Arrays.asList("REF2"));

		start = new DNode(1, sources, 0, 0, "");
		start.setDepth(1);
		insertion = new DNode(2, insertSrc, 0, 0, "");
		insertion.setDepth(2);
		end = new DNode(4, sources, 0, 0, "");
		end.setDepth(3);
		si = new DEdge(start, insertion);
		ie = new DEdge(insertion, end);
		se = new DEdge(start, end);
		graph = new DGraph();
		graph.addDNode(start);
		graph.addDNode(insertion);
		graph.addDNode(end);
		graph.addDEdge(si);
		graph.addDEdge(se);
		graph.addDEdge(ie);
		graph.setStart(start);
	}

	@After
	public void teardown() {
		graph = null;

		start = null;
		end = null;
		insertion = null;

		si = null;
		ie = null;
		se = null;
	}

	@Test
	public void belowThreshHoldTest() {
		Collection<InsertionMutation> muts = MutationFinder
				.findInsertionMutations(graph, null);
		gsg = ConvertDGraph.convert(graph);
		assertTrue(gsg.getNode("1") != null);
		assertTrue(gsg.getNode("2") != null);
		assertTrue(gsg.getNode("4") != null);
		InDelCollapser.collapseInsertions(muts, gsg, 30);
		assertTrue(gsg.getNode("1") != null);
		assertTrue(gsg.getNode("2") == null);
		assertTrue(gsg.getNode("4") != null);
	}

	@Test
	public void aboveThreshHoldTest() {
		Collection<InsertionMutation> muts = MutationFinder
				.findInsertionMutations(graph, null);
		gsg = ConvertDGraph.convert(graph);
		assertTrue(gsg.getNode("1") != null);
		assertTrue(gsg.getNode("2") != null);
		assertTrue(gsg.getNode("4") != null);
		InDelCollapser.collapseInsertions(muts, gsg, 0);
		assertTrue(gsg.getNode("1") != null);
		assertTrue(gsg.getNode("2") != null);
		assertTrue(gsg.getNode("4") != null);
	}

	@Test
	public void simpleInsertionCaseTest() {
		Collection<InsertionMutation> muts = MutationFinder
				.findInsertionMutations(graph, null);
		gsg = ConvertDGraph.convert(graph);
		assertTrue(gsg.getNode("1") != null);
		assertTrue(gsg.getNode("2") != null);
		assertTrue(gsg.getNode("4") != null);
		for (InsertionMutation mut : muts) {
			InDelCollapser.collapseInsertion(mut, gsg);
		}
		assertTrue(gsg.getNode("1") != null);
		assertTrue(gsg.getNode("2") == null);
		assertTrue(gsg.getNode("4") != null);
	}

	@Test
	public void doubleInsertionTest() {
		HashSet<String> insertSrc2 = new HashSet<String>(Arrays.asList("REF1"));
		DNode insertion2 = new DNode(3, insertSrc2, 0, 0, "");
		graph.addDNode(insertion2);
		DEdge sy = new DEdge(start, insertion2);
		DEdge ye = new DEdge(insertion2, end);
		graph.addDEdge(sy);
		graph.addDEdge(ye);
		Collection<InsertionMutation> muts = MutationFinder
				.findInsertionMutations(graph, null);
		gsg = ConvertDGraph.convert(graph);
		assertTrue(gsg.getNode("1") != null);
		assertTrue(gsg.getNode("2") != null);
		assertTrue(gsg.getNode("3") != null);
		assertTrue(gsg.getNode("4") != null);
		for (InsertionMutation mut : muts) {
			InDelCollapser.collapseInsertion(mut, gsg);
		}
		assertTrue(gsg.getNode("1") != null);
		assertTrue(gsg.getNode("2") == null);
		assertTrue(gsg.getNode("3") == null);
		assertTrue(gsg.getNode("4") != null);
	}

	@Test
	public void multipleInsertionsTest() {
		HashSet<String> insertSrc2 = new HashSet<String>(Arrays.asList("REF1"));
		DNode insertion2 = new DNode(3, insertSrc2, 0, 0, "");
		DNode end2 = new DNode(5, sources, 0, 0, "");
		graph.addDNode(insertion2);
		graph.addDNode(end2);
		DEdge sy = new DEdge(end, insertion2);
		DEdge ye = new DEdge(insertion2, end2);
		DEdge ee = new DEdge(end, end2);
		graph.addDEdge(sy);
		graph.addDEdge(ye);
		graph.addDEdge(ee);
		Collection<InsertionMutation> muts = MutationFinder
				.findInsertionMutations(graph, null);
		gsg = ConvertDGraph.convert(graph);
		assertTrue(gsg.getNode("1") != null);
		assertTrue(gsg.getNode("2") != null);
		assertTrue(gsg.getNode("3") != null);
		assertTrue(gsg.getNode("4") != null);
		assertTrue(gsg.getNode("5") != null);
		for (InsertionMutation mut : muts) {
			InDelCollapser.collapseInsertion(mut, gsg);
		}
		assertTrue(gsg.getNode("1") != null);
		assertTrue(gsg.getNode("2") == null);
		assertTrue(gsg.getNode("3") == null);
		assertTrue(gsg.getNode("4") != null);
		assertTrue(gsg.getNode("5") != null);
	}

	@Test
	public void InsertionWithSNPTest() {
		HashSet<String> insertSrc2 = new HashSet<String>(Arrays.asList("REF1"));
		DNode SNP = new DNode(3, insertSrc2, 0, 0, "");
		DNode SNPREF = new DNode(5, sources, 0, 0, "");
		DNode end2 = new DNode(6, sources, 0, 0, "");
		DNode insertion2 = new DNode(7, insertSrc2, 0, 0, "");
		DNode end3 = new DNode(8, sources, 0, 0, "");

		graph.addDNode(SNPREF);
		graph.addDNode(SNP);
		graph.addDNode(end2);
		graph.addDNode(insertion2);
		graph.addDNode(end3);

		graph.addDEdge(new DEdge(end, SNP));
		graph.addDEdge(new DEdge(end, SNPREF));
		graph.addDEdge(new DEdge(SNP, end2));
		graph.addDEdge(new DEdge(SNPREF, end2));
		graph.addDEdge(new DEdge(end2, insertion2));
		graph.addDEdge(new DEdge(end2, end3));
		graph.addDEdge(new DEdge(insertion2, end3));

		Collection<InsertionMutation> muts = MutationFinder
				.findInsertionMutations(graph, null);
		gsg = ConvertDGraph.convert(graph);
		assertTrue(gsg.getNode("1") != null);
		assertTrue(gsg.getNode("2") != null);
		assertTrue(gsg.getNode("3") != null);
		assertTrue(gsg.getNode("4") != null);
		assertTrue(gsg.getNode("5") != null);
		assertTrue(gsg.getNode("6") != null);
		assertTrue(gsg.getNode("7") != null);
		assertTrue(gsg.getNode("8") != null);
		for (InsertionMutation mut : muts) {
			InDelCollapser.collapseInsertion(mut, gsg);
		}
		assertTrue(gsg.getNode("1") != null);
		assertTrue(gsg.getNode("2") == null);
		assertTrue(gsg.getNode("3") != null);
		assertTrue(gsg.getNode("4") != null);
		assertTrue(gsg.getNode("5") != null);
		assertTrue(gsg.getNode("6") != null);
		assertTrue(gsg.getNode("7") == null);
		assertTrue(gsg.getNode("8") != null);
	}

	@Test
	public void InsertionWithDeletionTest() {
		HashSet<String> insertSrc2 = new HashSet<String>(Arrays.asList("REF1"));
		DNode deletion = new DNode(5, sources, 0, 0, "");
		DNode end2 = new DNode(6, sources, 0, 0, "");
		DNode insertion2 = new DNode(7, insertSrc2, 0, 0, "");
		DNode end3 = new DNode(8, sources, 0, 0, "");

		graph.addDNode(deletion);
		graph.addDNode(end2);
		graph.addDNode(insertion2);
		graph.addDNode(end3);

		graph.addDEdge(new DEdge(end, end2));
		graph.addDEdge(new DEdge(end, deletion));
		graph.addDEdge(new DEdge(deletion, end2));
		graph.addDEdge(new DEdge(end2, insertion2));
		graph.addDEdge(new DEdge(end2, end3));
		graph.addDEdge(new DEdge(insertion2, end3));

		Collection<InsertionMutation> muts = MutationFinder
				.findInsertionMutations(graph, null);
		gsg = ConvertDGraph.convert(graph);
		assertTrue(gsg.getNode("1") != null);
		assertTrue(gsg.getNode("2") != null);
		assertTrue(gsg.getNode("4") != null);
		assertTrue(gsg.getNode("5") != null);
		assertTrue(gsg.getNode("6") != null);
		assertTrue(gsg.getNode("7") != null);
		assertTrue(gsg.getNode("8") != null);
		for (InsertionMutation mut : muts) {
			InDelCollapser.collapseInsertion(mut, gsg);
		}
		assertTrue(gsg.getNode("1") != null);
		assertTrue(gsg.getNode("2") == null);
		assertTrue(gsg.getNode("4") != null);
		assertTrue(gsg.getNode("5") != null);
		assertTrue(gsg.getNode("6") != null);
		assertTrue(gsg.getNode("7") == null);
		assertTrue(gsg.getNode("8") != null);
	}
}
