package graph.search_path;

import graph.Graph;

public class Test {

    public static void main(String[] args){
        Graph g = Graph.getGraph();
        Test t = new Test(g);
        t.searchTest(new DepthFirstSearch(g, 0));
        t.searchTest(new BreadthFirstSearch(g, 0));
        t.pathTest(new BreadthFirstPath(g, 7));
        t.pathTest(new DepthFirstPath(g, 0));
    }

    private Graph g;

    public Test(Graph g){
        this.g = g;
    }

    public void searchTest(Search search){
        System.out.println("------------SearchTest-----------");
        for (int i = 0; i < g.V(); i++)
            System.out.printf("节点%s：%b\n", i, search.isMarked(i));
        System.out.printf("图的总节点数为：%d \n搜索到的节点数为：%d\n", g.V(), search.count());
        System.out.println();
    }

    public void pathTest(Path path){
        System.out.println("-------------PathTest------------");
        for (int i = 0; i < g.V(); i++){
            Iterable<Integer> p = path.pathTo(i);
            System.out.printf("节点%d到起点的路径为：", i);
            if (p == null) System.out.println("null");
            else{
                for (int v : p)
                    System.out.print(v + "-");
                System.out.println();
            }
        }
        System.out.println();
    }
}
