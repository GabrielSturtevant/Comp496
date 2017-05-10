/* Project3 TestCases
   Run Project3Testcase.java file with
   the input files, inputA.txt and inputB.txt.
   These text files are posted on Moodle.
   Include the output of Project3TestCase.java
   with the material handed in for Project 3.
   If your program does not work on some
   of the tested methods below, comment them out but PRINT A
   MESSAGE that your program does not work for these methods.

   Make no other changes in the test program.

   Highly recommended that you had check your results.
*/



import java.util.*;

public class Project3TestCase
{

    public static void main (String[] args)
    {


        //------------TestCase A ------------------------
        System.out.println ("Project 3 TestCase A:" );
        Graph g1 = new Graph("inputA.txt");
        System.out.println("Print Graph");
        g1.printGraph();
        System.out.println("\nNumber of Vertices = "  +
                g1.get_nVertices() +
                "\tNumber of Edges = " + g1.get_nEdges() +
                "\tTotal Edge Weight = " + g1.get_TotalWeightOfEdges() );


        //-----------------------------------------------

        int v = 0;
        System.out.println("\nDFS Traversal starting at " + v);
        Graph tree = g1.dfsTraversal(v);
        if( tree != null)
        {
            System.out.println("\nDFS Spanning Tree ");
            tree.printGraph();
        }



        //-----------------------------------------------
        System.out.println("\nDijkstra SP");
        int start = 3;
        g1.dijkstraShortestPaths(start);


        //-----------------------------------------------------
        System.out.println("\nKruskal's MST ");
        Graph treeKruskal = g1.kruskalMST();
        if(treeKruskal != null)
        {
            System.out.println("\nMST Tree");
            treeKruskal.printGraph();
        }



        //------------TestCase B ------------------------
        System.out.println("\n\nProject 3 TestCase B:");
        Graph g2 = new Graph("inputB.txt");
        System.out.println("Print Graph");
        g2.printGraph();
        //-----------------------------------------------

        int v2 = 3;
        System.out.println("\nDFS Traversal starting at " + v2);
        Graph tree2 = g2.dfsTraversal(v2);
        if( tree2 != null)
        {
            System.out.println("\nDFS Spanning Tree ");
            tree2.printGraph();
        }



        //-----------------------------------------------
        System.out.println("\nDijkstra SP");
        int start2 = 2;
        g2.dijkstraShortestPaths(start2);


    }
}
