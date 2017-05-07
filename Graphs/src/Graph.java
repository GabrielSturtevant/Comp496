import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Graph {
	private ArrayList<EdgeNode>[] adjList;
	private PriorityQueue<EdgeNode> EdgeQueue = new PriorityQueue<>();
	private int nVertices;
	private int nEdges;
	private int totalWeight;
	private boolean isConnected = true;
	private boolean shouldPrint = true;

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

	private void addEdge (EdgeNode a) {
		addEdge(a.vertex1, a.vertex2, a.weight);
	}

	public void addEdge (int i, int j, int weight) {
		this.adjList[i].add(new EdgeNode(i, j, weight));
		this.adjList[j].add(new EdgeNode(j, i, weight));
		this.EdgeQueue.add(new EdgeNode(i, j, weight));
		this.totalWeight += weight;
		this.nEdges++;
	} //adds an edge to the graph

	public void printGraph () {
		//prints nVertices, nEdges, and adjacency lists and total edge weight
		String toPrint = "Graph: nVertices = " + this.get_nVertices() + " nEdges = " + this.get_nEdges();
		toPrint += " totalWeight = " + this.get_TotalWeightOfEdges() + "\nAdjacency Lists";
		System.out.println(toPrint);
		for (int i = 0; i < this.adjList.length; i++) {
			toPrint = "v= " + i + " ";
			System.out.print(toPrint);
			toPrint = "";
			for (EdgeNode node : this.adjList[i]) {
				toPrint += node.toString() + ", ";
			}
			if (toPrint != "") {
				toPrint = "[" + toPrint;
				toPrint = toPrint.substring(0, toPrint.length() - 2);
				toPrint += "]";
			} else
				toPrint = "Singleton";
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

		toReturn = this.dfsTraversal(start, -1, visited, toReturn);

		while (!missing_nodes(visited)) {
			this.isConnected = false;
			if(this.shouldPrint)
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

	private boolean isNeighbor (int next, int last) {
		if (last == -1)
			return false;
		for (EdgeNode node : this.adjList[last]) {
			if (node.vertex2 == next)
				return true;
		}
		return false;
	}

	private Graph dfsTraversal (int start, int last, boolean[] visited, Graph toReturn) {
		visited[start] = true;
		if(this.shouldPrint)
			System.out.println("Visited: " + start);

		for (EdgeNode node : this.adjList[start]) {
			int next = node.vertex2;

			if (visited[next] && (next != last) && this.isNeighbor(next, last)) {
				if(this.shouldPrint)
					System.out.println("Cycle: " + last + "->" + start + "->" + next);
			}

			if (!visited[next]) {
				toReturn.addEdge(node.vertex1, node.vertex2, node.weight);
				toReturn = dfsTraversal(next, start, visited, toReturn);
				if (this.shouldPrint)
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
		PQNode[] nodeArray = new PQNode[this.get_nVertices()];
		int[] distances = new int[this.get_nVertices()];
		int[] previous = new int[this.get_nVertices()];
		Arrays.fill(distances, Integer.MAX_VALUE);
		Arrays.fill(previous, -2);
		distances[start] = 0;
		previous[start] = -1;
		PriorityQueue<PQNode> Nodes = new PriorityQueue<>();
		for (int i = 0; i < this.nVertices; i++) {
			PQNode temp = new PQNode(i, distances[i]);
			Nodes.add(temp);
			nodeArray[i] = temp;
		}
		while (Nodes.peek().distance != Integer.MAX_VALUE) {
			PQNode current = Nodes.poll();
			for (EdgeNode node : this.adjList[current.vertex]) {
				if (Nodes.contains(nodeArray[node.vertex2])) {
					int currentWeight = distances[current.vertex] + node.weight;
					if (currentWeight < distances[node.vertex2]) {
						distances[node.vertex2] = currentWeight;
						previous[node.vertex2] = current.vertex;
						PQNode tempNode = new PQNode(node.vertex2, currentWeight);
						Nodes.add(tempNode);
						nodeArray[node.vertex2] = tempNode;
					}
				}
			}
		}
		System.out.println("Hi");
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

		Graph toReturn = new Graph(this.get_nVertices());

		while (!this.EdgeQueue.isEmpty()) {
			EdgeNode temp = this.EdgeQueue.poll();
			if (!(!toReturn.adjList[temp.vertex1].isEmpty() && !toReturn.adjList[temp.vertex2].isEmpty())) {
				toReturn.addEdge(temp);
			}
		}

		//The following two lines run dfTraversal in order to determine whether or not the graph is connected
		toReturn.shouldPrint = false;
		toReturn.dfsTraversal(0);

		if(toReturn.isConnected) {
			toReturn.printGraph();
			return toReturn;
		} else {
			System.out.println("The graph is not connected");
			return null;
		}
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