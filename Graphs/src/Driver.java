/**
 * Created by gabriel on 5/7/17.
 */
public class Driver {
	public static void main (String[] args) {
		Graph foo = new Graph("graph.txt");
		/*foo.printGraph();
		System.out.println("\nTraversal:");
		System.out.println("----------------------------");
		Graph traverse = foo.dfsTraversal(0);
		System.out.println("\nOutcome:");
		System.out.println("----------------------------");
		traverse.printGraph();
		foo.dijkstraShortestPaths(0);*/
		foo.kruskalMST();
	}
}
