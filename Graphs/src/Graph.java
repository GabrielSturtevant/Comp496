import java.util.ArrayList;

public class Graph {
    private ArrayList<EdgeNode>[] adjList;
    private int nVertices;
    private int nEdges;


    public Graph(String inputFileName) {
    }//creates Graph from data in file

    public Graph(int n) {
    }   //Creates a  Graph with n vertices and 0 edges

    public void addEdge(int i, int j, int weight) {
    } //adds an edge to the graph

    public void printGraph() {
        //prints nVertices, nEdges, and adjacency lists and total edge weight
    }

    public int get_nVertices() {
        return 0;
    }

    public int get_nEdges() {
        return 0;
    }

    public int get_TotalWeightOfEdges() {
        return 0;
    }


    public Graph dfsTraversal(int start) {
        /* Use recursion by calling a recursive dfs method;
            Visit all nodes
            If graph is not connected you will need to call dfs more than once
to visit all
            node and to print out the information below.
            Print the following information gleaned from the dfs traversal
                Print nodes in order visited
                Connected?    ____
                NumberOfComponents?   _____
                Has a cycle?   _______
            If the graph is connected, return the spanning tree in the dfs
traversal.
            Otherwise, return null.

        */
        return this;
    }


    public Graph bfsTraversal(int start) {
        /*
        Print the level lists found from the start vertex, one line at a time,
labeled
        If the graph is connected, return the spanning tree discovered.
        Otherwise return null.
        */
        return this;
    }

    public ArrayList<Integer> getShortestEdgePath(int x, int y) {
        /*
         Return the shortest edge path from x to y .
         If no such list, return null
         Use breadth first search
        */
        ArrayList<Integer> foo = new ArrayList();
        return foo;
    }

    public void dijkstraShortestPaths(int start) {
        /* Implement Dijkstra algorithm from text as discussed in class;
        Use the Java PriorityQueue<EdgeNode>   class. Use EdgeNode class below.
        This class has no updateKey method
        To simulate an updateKey method in priority queue, see Problem C-14.3
from text.
        Prints shortest paths from vertex start to all other vertices reachable
from start
        */
    }

    public int[] bellmanFordShortestPaths(int start) {
        /* Implement Bellman Ford Shortest Path algorithm from text
        Prints shortest paths from vertex start to all other vertices reachable
from start */
        int[] foo = new int[]{1};
        return foo;
    }

    public Graph KruskalMST() {
        /* Implement Kruskal algorithm from text.
        Use clusters.
        If graph is connected
                Print the edges of the MST found and its total weight
                 Returns the minimum spanning tree as a Graph
         else
        Print a message and return null
        */
        return this;
    }


}
//4. Specification for the EdgeNode class  (Put in the same file as the Graph
class)

class EdgeNode implements Comparable<EdgeNode> {
    int vertex1;
    int vertex2;
    int weight;

    public EdgeNode(int v1, int v2, int w) {
        vertex1 = v1;
        vertex2 = v2;
        weight = w;
    }

    public int compareTo(EdgeNode node) {
        return weight - node.weight;
    }

    public String toString() {

        String s = "(";
        s = s + vertex1 + "," + vertex2 + "," + weight + ")";
        return s;
    }
}
