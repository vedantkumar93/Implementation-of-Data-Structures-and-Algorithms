 /** Starter code for SP4
 *  @author VISHAL SHAH
 *  @author VEDANT KUMAR
 *  Depth-first search (DFS)
 * Finding topological order of DAG using DFS
 */


package vjs180000;

import vjs180000.Graph.Vertex;
import vjs180000.Graph.Edge;
import vjs180000.Graph.GraphAlgorithm;
import vjs180000.Graph.Factory;
import vjs180000.Graph.Timer;

import java.io.File;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;

public class DFS extends GraphAlgorithm<DFS.DFSVertex> {
	private LinkedList<Vertex> OutputList;
	static boolean isCycle;
	public int numberOfConnectedComponents; // The number of connected components of the graph as 0;
	private int TopNumber; // 	Used for dfs()
	enum Color 
	{ 
	    WHITE, BLACK, GREY; 
	}
    public static class DFSVertex implements Factory {
    	int top;
    	Color color;
    	int ComponentNumber;//component number
    	//boolean visited;
    	Vertex parent;
	
	public DFSVertex(Vertex u) {
		this.color=Color.WHITE;
		//this.visited=false;
		this.top=0;
		this.ComponentNumber=0;
		this.parent=null;
		
		
	}
	public DFSVertex make(Vertex u) {
		return new DFSVertex(u);
		}
    }

    public DFS(Graph g) {
    	super(g, new DFSVertex(null));
    	}
    /**
     * Recursive algorithm to visit the nodes of a graph
     * @param Graph g
     * @return DFS
     */

    public static DFS depthFirstSearch(Graph g) {
    	DFS temparary=new DFS(g);
    	temparary.TopNumber=g.size();
    	for (Vertex v:g) {
    		temparary.get(v).parent=null;
    		temparary.get(v).color=Color.WHITE;
    		temparary.get(v).top=0;
    	}
    	temparary.OutputList = new LinkedList<>();
    	for (Vertex v:g) {
    		if(temparary.get(v).color==Color.WHITE) {
    			temparary.visit_DFS(v);
    		}
    	}
    	return temparary;
    }
    /**
     * Utility method for depthFirstSearch()
     * @param Vertex u
     */
    private void visit_DFS(Vertex u) {
    	get(u).color=Color.GREY;
    	for (Edge e:g.incident(u)) {
    		Vertex v=e.otherEnd(u);
    		if(get(v).color==Color.WHITE) {
    			get(v).parent=u;
    			visit_DFS(v);
    		}
    		else {
    			if(get(v).color==Color.GREY) {
    				isCycle=true;
    			}
    		}
    	}
    	get(u).color=Color.BLACK;
    	get(u).top=TopNumber--;
    	OutputList.addFirst(u);
    	
    }
    /**
	 * Member function to find topological order
	 * @return List of vertices in topological order if graph is DAG, 
	 * 			null otherwise
	 */

    public List<Vertex> topologicalOrder1() {
    	DFS d1 = depthFirstSearch(g);
    	if(d1.isCycle) {
    		return null;
    	}
    	else {
    		return d1.OutputList;
    	}
    }

    // Find the number of connected components of the graph g by running dfs.
    // Enter the component number of each vertex u in u.cno.
    // Note that the graph g is available as a class field via GraphAlgorithm.
    public int connectedComponents() {
	return 0;
    }

    // After running the onnected components algorithm, the component no of each vertex can be queried.
    public int cno(Vertex u) {
	return get(u).ComponentNumber;
    }
    
    // Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder1(Graph g) {
	DFS d = new DFS(g);
	return d.topologicalOrder1();
    }

    // Find topological oder of a DAG using the second algorithm. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder2(Graph g) {
	return null;
    }

    public static void main(String[] args) throws Exception {
	//String string = "7 8   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 1 7   6 7 1   7 6 1 0";
	String string = "7 6   1 2 2   1 3 3   2 4 5   3 4 4   5 1 7   7 6 1 0";
	//String string="3 2  1 2 2   2 3 4";
	Scanner in;
	// If there is a command line argument, use it as file from which
	// input is read, otherwise use input from string.
	in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);
	
	// Read graph from input
        Graph g = Graph.readDirectedGraph(in);
	g.printGraph(false);
	DFS d=new DFS(g);
	//System.out.println(d.OutputList);
	
	List<Vertex> finishList = d.topologicalOrder1();
	
	System.out.println("Topological order of DAG using DFS");
	if(finishList == null) {
		System.out.println("--Cycles detected, Not a DAG--");
	}
	else {
		for(Vertex u: finishList) {
		    System.out.print(u + " ");
		}
	}
	System.out.println();
	
	
    }
}