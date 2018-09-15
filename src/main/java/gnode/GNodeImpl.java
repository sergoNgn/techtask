package gnode;

import java.util.Objects;

public class GNodeImpl implements GNode {

    private String name;
    private GNode[] children;

    public GNodeImpl(String name, GNode... children) {
        this.name = name;
        this.children = children;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public GNode[] getChildren() {
        if(Objects.isNull(children)) {
            return new GNode[0];
        }
        return children;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GNodeImpl gNode = (GNodeImpl) o;
        return Objects.equals(name, gNode.name);
    }
}
