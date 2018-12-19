package rail.shortPath;

import java.util.Stack;

public interface ShortPath<Vertex> {

    Stack<Vertex> pathTo(String start, String end);
}
