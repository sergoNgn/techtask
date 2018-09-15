package gnode;

import java.util.List;

public interface GNodeService {
    List<GNode> walkGraph(GNode root);
    List<List<GNode>> paths(GNode root);
}
