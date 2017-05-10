import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

public class Graph {
	private ArrayList<EdgeNode>[] adjList;
	private int nVertices;
	private int nEdges;
	private int totWgt;
	private boolean connected = true;
	private boolean output = true;
	private boolean cycles = false;
	private PriorityQueue<EdgeNode> edgeQ = new PriorityQueue<>();

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
		this.totWgt = 0;
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
		this.adjList[j].add(new EdgeNode(j, i, weight));
		this.edgeQ.add(new EdgeNode(i, j, weight));
		this.totWgt += weight;
		this.nEdges++;
	} //adds an edge to the graph

	public void printGraph () {
		//prints nVertices, nEdges, and adjacency lists and total edge weight
		String out = "Graph: nVertices = " + this.nVertices + " nEdges = " + this.nEdges;
		out += " totWgt = " + this.totWgt+ "\nAdjacency Lists";
		System.out.println(out);
		for (int i = 0; i < this.adjList.length; i++) {
			out = "v= " + i + " ";
			System.out.print(out);
			out = "";
			ArrayList<EdgeNode> edges = this.adjList[i];
			for (int j = 0; j < edges.size(); j++) {
				EdgeNode node = edges.get(j);
				out += node.toString() + ", ";
			}
			if (out != "") {
				out = "[" + out;
				out = out.substring(0, out.length() - 2);
				out += "]";
			} else
				out = "Singleton";
			System.out.println(out);
		}
	}

	public int get_nVertices () {
		return this.nVertices;
	}

	public int get_nEdges () {
		return this.nEdges;
	}

	public int get_TotalWeightOfEdges () {
		return this.totWgt;
	}


	public Graph dfsTraversal (int start) {
	    /* Use recursion by calling a recursive dfs method;
	        Visit all nodes
            If graph is not connected you will need to call dfs more than once to visit all
            node and to print out the information below.
            output the following information gleaned from the dfs traversal
                output nodes in order visited
                Connected?    ____
                NumberOfComponents?   _____
                Has a cycle?   _______
            If the graph is connected, return the spanning tree in the dfs traversal.
            Otherwise, return null.

        */
		boolean[] visited = new boolean[this.nVertices];
		Arrays.fill(visited, false);
		Graph toReturn = new Graph(this.get_nVertices());

		toReturn = this.dfsT(start, -1, visited, toReturn);

		while (!unexplored(visited)) {
			this.connected = false;
			if(this.output)
				System.out.println("----------------------\nGraph is not connected");
			for (int i = 0; i < visited.length; i++) {
				if (!visited[i]) {
					toReturn = this.dfsT(i, -1, visited, toReturn);
					break;
				}
			}
		}
		if (!this.connected){
			return null;
		}else {
			if(this.cycles){
				System.out.println("Graph has cycles");
			}
			return toReturn;
		}
	}

	private Graph dfsT(int current, int previous, boolean[] explored, Graph toReturn) {
		explored[current] = true;
		if(this.output)
			System.out.println(MessageFormat.format("Visited: {0}", current));

		for (EdgeNode vertex : this.adjList[current]) {
			int next = vertex.vertex2;

			if (explored[next]) {
				if ((next != previous)) {
					if (this.adj(next, previous)) {
						if (this.output) {
							this.cycles = true;
						}
					}
				}
			}

			if (!explored[next]) {
				toReturn.addEdge(vertex.vertex1, vertex.vertex2, vertex.weight);
				toReturn = dfsT(next, current, explored, toReturn);
			}
		}

		return toReturn;
	}

	private boolean adj(int next, int previous) {
		return previous != -1 && this.adjList[previous].stream().anyMatch(node -> node.vertex2 == next);
	}

	private boolean unexplored(boolean[] nodes) {
		return IntStream.range(0, nodes.length).allMatch(i -> nodes[i]);
	}

	public void dijkstraShortestPaths (int start) {
	    /* Implement Dijkstra algorithm from text as discussed in class;
	    Use the Java PriorityQueue<EdgeNode>   class. Use EdgeNode class below.
        This class has no updateKey method
        To simulate an updateKey method in priority queue, see Problem C-14.3 from text.
        Prints shortest paths from vertex start to all other vertices reachable from start
        */
		PQNode[] A = new PQNode[this.get_nVertices()];
		int[] D = new int[this.get_nVertices()];
		int[] PI = new int[this.get_nVertices()];
		Arrays.fill(D, Integer.MAX_VALUE);
		Arrays.fill(PI, -2);
		D[start] = 0;
		PI[start] = -1;
		PriorityQueue<PQNode> PQ = new PriorityQueue<>();
		int i = 0;
		while (i < this.nVertices) {
			PQNode tmp = new PQNode(i, D[i]);
			PQ.add(tmp);
			A[i] = tmp;
			i++;
		}
		while (PQ.peek().distance != Integer.MAX_VALUE) {
			PQNode current = PQ.poll();
			ArrayList<EdgeNode> edgeNodes = this.adjList[current.vertex];
			for (int j = 0, edgeNodesSize = edgeNodes.size(); j < edgeNodesSize; j++) {
				EdgeNode vertex = edgeNodes.get(j);
				if (PQ.contains(A[vertex.vertex2])) {
					int currentWeight = D[current.vertex] + vertex.weight;
					if (currentWeight < D[vertex.vertex2]) {
						D[vertex.vertex2] = currentWeight;
						PI[vertex.vertex2] = current.vertex;
						PQNode tmpVertex = new PQNode(vertex.vertex2, currentWeight);
						PQ.add(tmpVertex);
						A[vertex.vertex2] = tmpVertex;
					}
				}
			}
		}
		String toPrint;
		int now;
		i = 0;
		while (i < PI.length) {
			now = i;
			if (PI[now] == -1){
				toPrint = ""+now;
			}else if (PI[now] == -2){
				toPrint = "Cannot reach";
			} else {
				toPrint = "->" + now;
			}
			if ((PI[now] != -1) && (PI[now] != -2)) {
				do {
					now = PI[now];
					if (PI[now] == -1) {
						toPrint = "" + now + toPrint;
					} else {
						toPrint = "->" + now + toPrint;
					}
				} while ((PI[now] != -1) && (PI[now] != -2));
			}
			if (PI[now] == -2) {
				System.out.println(i+": " +toPrint);
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
                output the edges of the MST found and its total weight
                 Returns the minimum spanning tree as a Graph
         else
        output a message and return null
        */

        //The following two lines run dfTraversal in order to determine whether or not the graph is connected
		this.output = false;
		this.dfsTraversal(0);
		this.output = true;

		if (this.connected) {

			Graph toReturn = new Graph(this.get_nVertices());

			if (!this.edgeQ.isEmpty()) {
				do {
					EdgeNode tmp = this.edgeQ.poll();
					if (!(!toReturn.adjList[tmp.vertex1].isEmpty() && !toReturn.adjList[tmp.vertex2].isEmpty())) {
						toReturn.addEdge(tmp.vertex1, tmp.vertex2, tmp.weight);
						System.out.println("Adding edge: " + tmp.toString());
					}
				} while (!this.edgeQ.isEmpty());
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