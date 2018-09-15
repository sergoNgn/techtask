package gnode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

public class GNodeServiceImpl implements GNodeService {

    @Override
    public List<GNode> walkGraph(GNode root) {
        if (Objects.isNull(root)) {
            return Collections.emptyList();
        }

        Queue<GNode> visitNodeQueue = new LinkedList<>();
        Set<GNode> visitedNodes = new HashSet<>();
        visitNodeQueue.add(root);

        while (!visitNodeQueue.isEmpty()) {
            GNode currentNode = visitNodeQueue.poll();
            if (visitedNodes.add(currentNode)) {
                for (GNode node : currentNode.getChildren()) {
                    visitNodeQueue.add(node);
                }
            }
        }
        return new ArrayList<>(visitedNodes);
    }

    @Override
    public List<List<GNode>> paths(GNode root) {
        if(Objects.isNull(root)) {
            return Collections.emptyList();
        }
        List<List<GNode>> resultPaths = new ArrayList<>();
        getNodePath(root, new HashSet<>(), resultPaths);
        return resultPaths;
    }

    private void getNodePath(GNode node, Set<GNode> nodePath, List<List<GNode>> resultPaths) {
        nodePath.add(node);
        if (node.getChildren().length == 0) {
            resultPaths.add(new ArrayList<>(nodePath));
        } else {
            Queue<GNode> visitNodeQueue = getQueueForNodes(node.getChildren());
            while (!visitNodeQueue.isEmpty()) {
                GNode currentNode = visitNodeQueue.poll();

                //check for cycle in the graph
                if (nodePath.contains(currentNode)) {
                    resultPaths.add(new ArrayList<>(nodePath));
                    return;
                }
                getNodePath(currentNode, nodePath, resultPaths);

                //removing leaf node from the checked graph path
                nodePath.remove(currentNode);
            }
        }
    }

    private Queue<GNode> getQueueForNodes(GNode[] nodes) {
        return new LinkedList<>(Arrays.asList(nodes));
    }
}