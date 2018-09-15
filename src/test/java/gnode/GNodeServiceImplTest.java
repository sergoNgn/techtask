package gnode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class GNodeServiceImplTest {

    private GNodeService gNodeServiceImpl = new GNodeServiceImpl();

    @Test
    public void walkGraph() {
        GNode e = new GNodeImpl("E");
        GNode f = new GNodeImpl("F");
        GNode b = new GNodeImpl("B", e, f);

        GNode g = new GNodeImpl("G");
        GNode h = new GNodeImpl("H");
        GNode i = new GNodeImpl("I");
        GNode c = new GNodeImpl("C", g, h, i);

        GNode d = new GNodeImpl("D");
        GNode a = new GNodeImpl("A", b, c, d);

        assertGraphs(Arrays.asList(a, b, c, d, e, f, g, h, i), gNodeServiceImpl.walkGraph(a));
    }

    @Test
    public void walkCyclicGraph() {
        GNode e = new GNodeImpl("E");
        GNode f = new GNodeImpl("F");
        GNode b = new GNodeImpl("B", e, f);

        GNode g = new GNodeImpl("G");
        GNode h = new GNodeImpl("H");
        GNode i = new GNodeImpl("I");
        GNode c = new GNodeImpl("C", g, h, i);

        GNode d = new GNodeImpl("D");
        GNode a = new GNodeImpl("A", b, c, d);

        b.getChildren()[0] = new GNodeImpl("E", a);
        assertGraphs(Arrays.asList(a, b, c, d, e, f, g, h, i), gNodeServiceImpl.walkGraph(a));
    }

    @Test
    public void walkEmptyGraph() {
        assertGraphs(new ArrayList<>(), gNodeServiceImpl.walkGraph(null));
    }

    @Test
    public void paths() {
        GNode e = new GNodeImpl("E");
        GNode f = new GNodeImpl("F");
        GNode b = new GNodeImpl("B", e, f);

        GNode g = new GNodeImpl("G");
        GNode h = new GNodeImpl("H");
        GNode i = new GNodeImpl("I");
        GNode c = new GNodeImpl("C", g, h, i);

        GNode d = new GNodeImpl("D");
        GNode a = new GNodeImpl("A", b, c, d);

        List<List<GNode>> expected = new ArrayList<>();
        addNodesToList(expected, a, b, e);
        addNodesToList(expected, a, b, f);
        addNodesToList(expected, a, c, g);
        addNodesToList(expected, a, c, h);
        addNodesToList(expected, a, c, i);
        addNodesToList(expected, a, d);

        List<List<GNode>> nodePaths = gNodeServiceImpl.paths(a);

        assertEquals(expected.size(), nodePaths.size());
        Iterator<List<GNode>> expectedListIterator = expected.iterator();
        Iterator<List<GNode>> resultListIterator = nodePaths.iterator();

        while (expectedListIterator.hasNext()) {
            assertGraphs(expectedListIterator.next(), resultListIterator.next());
        }
    }

    @Test
    public void pathsEmptyGraph() {
        assertNotNull(gNodeServiceImpl.paths(null));
    }

    @Test
    public void pathsCyclicGraph() {
        GNode e = new GNodeImpl("E");
        GNode f = new GNodeImpl("F");
        GNode b = new GNodeImpl("B", e, f);

        GNode g = new GNodeImpl("G");
        GNode h = new GNodeImpl("H");
        GNode i = new GNodeImpl("I");
        GNode c = new GNodeImpl("C", g, h, i);

        GNode d = new GNodeImpl("D");
        GNode a = new GNodeImpl("A", b, c, d);

        b.getChildren()[0] = new GNodeImpl("E", a);

        List<List<GNode>> expected = new ArrayList<>();
        addNodesToList(expected, a, b, e);
        addNodesToList(expected, a, b, f);
        addNodesToList(expected, a, c, g);
        addNodesToList(expected, a, c, h);
        addNodesToList(expected, a, c, i);
        addNodesToList(expected, a, d);

        List<List<GNode>> nodePaths = gNodeServiceImpl.paths(a);

        assertEquals(expected.size(), nodePaths.size());

        Iterator<List<GNode>> expectedListIterator = expected.iterator();
        Iterator<List<GNode>> resultListIterator = nodePaths.iterator();

        while (expectedListIterator.hasNext()) {
            assertGraphs(expectedListIterator.next(), resultListIterator.next());
        }
    }

    private void addNodesToList(List<List<GNode>> expected, GNode... nodes) {
        expected.add(Arrays.asList(nodes));
    }

    private void assertGraphs(List<GNode> expected, List<GNode> graph) {
        assertEquals(expected.size(), graph.size());
        expected
                .forEach(node -> assertTrue(graph.contains(node)));
    }
}