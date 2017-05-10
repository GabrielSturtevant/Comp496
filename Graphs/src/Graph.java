import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

public class Graph {
	private ArrayList<EdgeNode>[] adjList;
	private PriorityQueue<EdgeNode> EdgeQueue = new PriorityQueue<>();
	private int nVertices;
	private int nEdges;
	private int totalWeight;
	private boolean isConnected = true;
	private boolean Print = true;

	public Graph (String inputFileName) {
		File file = new File(inputFileName);
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));
			String text;

			boolean firstLine = true;
			String[] edgeString;
			while ((text = reader.readLine()) != null) {
				text = text.replaceAll("( )+", " ");
				if (firstLine) {
					firstLine = !firstLine;
					int n = Integer.parseInt(text);
					this.addGraph(n);

					// Handles the empty line following the number of nodes in the graph
					text = reader.readLine();
				} else{
					edgeString = text.split(" ");
					int a, b, c;
					a = Integer.parseInt(edgeString[0]);
					b = Integer.parseInt(edgeString[1]);
					c = Integer.parseInt(edgeString[2]);
					this.addEdge(a, b, c);
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
			ArrayList<EdgeNode> edges = this.adjList[i];
			for (int j = 0; j < edges.size(); j++) {
				EdgeNode node = edges.get(j);
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
			if(this.Print)
				System.out.println("Graph is not connected");
			for (int i = 0; i < visited.length; i++) {
				if (!visited[i]) {
					toReturn = this.dfsTraversal(i, -1, visited, toReturn);
					break;
				}
			}
		}
		if (!this.isConnected){
			return null;
		}else {
			return toReturn;
		}
	}

	private boolean isNeighbor (int next, int last) {
		return last != -1 && this.adjList[last].stream().anyMatch(node -> node.vertex2 == next);
	}

	private Graph dfsTraversal (int start, int last, boolean[] visited, Graph toReturn) {
		visited[start] = true;
		if(this.Print)
			System.out.println(MessageFormat.format("Visited: {0}", start));

		for (EdgeNode node : this.adjList[start]) {
			int next = node.vertex2;

			if (visited[next]) {
				if ((next != last)) {
					if (this.isNeighbor(next, last)) {
						if (this.Print) {
							System.out.println(MessageFormat.format("Cycle: {0}->{1}->{2}", last, start, next));
						}
					}
				}
			}

			if (!visited[next]) {
				toReturn.addEdge(node.vertex1, node.vertex2, node.weight);
				toReturn = dfsTraversal(next, start, visited, toReturn);
				if (this.Print) {
					System.out.println(MessageFormat.format("Return to: {0}", start));
				}
			}
		}

		return toReturn;
	}

	private boolean missing_nodes (boolean[] check) {
		return IntStream.range(0, check.length).allMatch(i -> check[i]);
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
		int i = 0;
		while (i < this.nVertices) {
			PQNode temp = new PQNode(i, distances[i]);
			Nodes.add(temp);
			nodeArray[i] = temp;
			i++;
		}
		while (Nodes.peek().distance != Integer.MAX_VALUE) {
			PQNode current = Nodes.poll();
			ArrayList<EdgeNode> edgeNodes = this.adjList[current.vertex];
			for (int j = 0, edgeNodesSize = edgeNodes.size(); j < edgeNodesSize; j++) {
				EdgeNode node = edgeNodes.get(j);
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
		String toPrint;
		int current;
		//TODO print paths
		i = 0;
		while (i < previous.length) {
			current = i;
			toPrint = ((previous[current] == -1)? "Start": ((current == -2)? "Unreachable" : "->"+current));
			if ((previous[current] != -1) && (previous[current] != -2)) {
				do {
					current = previous[current];
					toPrint = ((previous[current] == -1) ? "Start" : "->" + current) + toPrint;
				} while ((previous[current] != -1) && (previous[current] != -2));
			}
			if (previous[current] == -2) {
				System.out.println(i+": Unreachable from start");
			} else {
				System.out.println(i+": "+toPrint);
			}
			i++;
		}
	}

	public Graph kruskalMST () {
        /* Implement Kruskal algorithm from text.
        Use clusters.
        If graph is connected
                Print the edges of the MST found and its total weight
                 Returns the minimum spanning tree as a Graph
         else
        Print a message and return null
        */

        //The following two lines run dfTraversal in order to determine whether or not the graph is connected
		this.Print = false;
		this.dfsTraversal(0);
		this.Print = true;

		if (this.isConnected) {

			Graph toReturn = new Graph(this.get_nVertices());

			if (!this.EdgeQueue.isEmpty()) {
				do {
					EdgeNode temp = this.EdgeQueue.poll();
					if (!(!toReturn.adjList[temp.vertex1].isEmpty() && !toReturn.adjList[temp.vertex2].isEmpty())) {
						toReturn.addEdge(temp);
						System.out.println("Adding edge: " + temp.toString());
					}
				} while (!this.EdgeQueue.isEmpty());
			}

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