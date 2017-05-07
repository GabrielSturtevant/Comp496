import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Graph {
	private ArrayList<EdgeNode>[] adjList;
	private int nVertices;
	private int nEdges;
	private int totalWeight;

	public static void main (String[] args) {
		Graph foo = new Graph("graph.txt");
		foo.printGraph();
		System.out.println("\nTraversal:");
		System.out.println("----------------------------");
		Graph traverse = foo.dfsTraversal(0);
		System.out.println("\nOutcome:");
		System.out.println("----------------------------");
		traverse.printGraph();
	}

	public Graph (String inputFileName) {
		File file = new File(inputFileName);
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));
			String text;

			boolean first = true;
			String[] parts;
			while ((text = reader.readLine()) != null) {
				if (first) {
					first = !first;
					int n = Integer.parseInt(text);
					this.addGraph(n);
				} else {
					parts = text.split(" ");
					this.addEdge(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
				}
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
			}
		}
	}//creates Graph from data in file

	private void addGraph (int n) {
		this.nVertices = n;
		this.totalWeight = 0;
		this.nEdges = 0;
		this.adjList = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			this.adjList[i] = new ArrayList<>();
		}
	}   //Creates a  Graph with n vertices and 0 edges

	public Graph (int n) {
		this.addGraph(n);
	}   //Creates a  Graph with n vertices and 0 edges

	public void addEdge (int i, int j, int weight) {
		this.adjList[i].add(new EdgeNode(i, j, weight));
		if (i != j) {
			this.adjList[j].add(new EdgeNode(j, i, weight));
		}
		this.totalWeight += weight;
		this.nEdges++;
	} //adds an edge to the graph

	public void printGraph () {
		//prints nVertices, nEdges, and adjacency lists and total edge weight
		String toPrint = "Graph: nVertices = " + this.get_nVertices()+ " nEdges = " + this.get_nEdges();
		toPrint += " totalWeight = " + this.get_TotalWeightOfEdges()+ "\nAdjacency Lists";
		System.out.println(toPrint);
		for (int i = 0; i < this.adjList.length; i++) {
			toPrint = "v= " + i + " ";
			System.out.print(toPrint);
			toPrint = "";
			for (EdgeNode node : this.adjList[i]) {
				toPrint += node.toString() + ", ";
			}
			if(toPrint != "") {
				toPrint = "[" + toPrint;
				toPrint = toPrint.substring(0, toPrint.length() - 2);
				toPrint += "]";
			}
			else
				toPrint = "Unconnected node";
			System.out.println(toPrint);
		}
	}

	public int get_nVertices () {
		return this.nVertices;
	}

	public int get_nEdges () {
		return this.nEdges;
	}

	public int get_TotalWeightOfEdges () {
		return this.totalWeight;
	}


	public Graph dfsTraversal (int start) {
	    /* Use recursion by calling a recursive dfs method;
            Visit all nodes
            If graph is not connected you will need to call dfs more than once to visit all
            node and to print out the information below.
            Print the following information gleaned from the dfs traversal
                Print nodes in order visited
                Connected?    ____
                NumberOfComponents?   _____
                Has a cycle?   _______
            If the graph is connected, return the spanning tree in the dfs traversal.
            Otherwise, return null.

        */
		boolean[] visited = new boolean[this.nVertices];
		Arrays.fill(visited, false);
		Graph toReturn = new Graph(this.get_nVertices());

		toReturn = this.dfsTraversal(start, -1 , visited, toReturn);

		while (!missing_nodes(visited)) {
			System.out.println("Graph is not connected");
			for (int i = 0; i < visited.length; i++) {
				if (!visited[i]) {
					toReturn = this.dfsTraversal(i, -1, visited, toReturn);
					break;
				}
			}
		}
		return toReturn;
	}

	private boolean isNeighbor(int next, int last) {
		if(last == -1) return false;
		for(EdgeNode node : this.adjList[last]) {
			if(node.vertex2 == next)
				return true;
		}
		return false;
	}

	private Graph dfsTraversal (int start, int last, boolean[] visited, Graph toReturn) {
		visited[start] = true;
		System.out.println("Visited: " + start);

		for (EdgeNode node : this.adjList[start]) {
			int next = node.vertex2;

			if(visited[next] && (next != last) && this.isNeighbor(next, last)){
				System.out.println("Cycle: "+last+"->"+start+"->"+next);
			}

			if (!visited[next]) {
				toReturn.addEdge(node.vertex1, node.vertex2, node.weight);
				toReturn = dfsTraversal(next, start, visited, toReturn);
				System.out.println("Return to: " + start);
			}
		}

		return toReturn;
	}

	/**
	 * Returns false if any members of the passed array are false,
	 * otherwise returns true
	 *
	 * @param check : boolean array
	 * @return boolean
	 */
	private boolean missing_nodes (boolean[] check) {
		for (int i = 0; i < check.length; i++) {
			if (!check[i]) {
				return false;
			}
		}
		return true;
	}

	public void dijkstraShortestPaths (int start) {
        /* Implement Dijkstra algorithm from text as discussed in class;
        Use the Java PriorityQueue<EdgeNode>   class. Use EdgeNode class below.
        This class has no updateKey method
        To simulate an updateKey method in priority queue, see Problem C-14.3 from text.
        Prints shortest paths from vertex start to all other vertices reachable from start
        */
	}

	public Graph KruskalMST () {
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

class EdgeNode implements Comparable<EdgeNode> {
	int vertex1;
	int vertex2;
	int weight;

	public EdgeNode (int v1, int v2, int w) {
		vertex1 = v1;
		vertex2 = v2;
		weight = w;
	}

	public int compareTo (EdgeNode node) {
		return weight - node.weight;
	}

	public String toString () {
		String s = "(";
		s = s + vertex1 + "," + vertex2 + "," + weight + ")";
		return s;
	}
}

class PQNode implements Comparable<PQNode> {
	int vertex;
	int distance;

	public PQNode (int v, int z) {
		vertex = v;
		distance = z;
	}

	public int compareTo (PQNode node) {
		return (distance - node.distance);
	}

	public String toString () {
		return "(" + vertex + "," + distance;
	}
}