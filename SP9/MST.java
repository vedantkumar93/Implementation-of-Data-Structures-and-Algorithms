// Starter code for SP9

package vxk180003;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

import vxk180003.BinaryHeap.Index;
import vxk180003.BinaryHeap.IndexedHeap;
import vxk180003.Graph.Edge;
import vxk180003.Graph.Factory;
import vxk180003.Graph.GraphAlgorithm;
import vxk180003.Graph.Timer;
import vxk180003.Graph.Vertex;

public class MST extends GraphAlgorithm<MST.MSTVertex> {
	String algorithm;
	public long wmst; // Weight of MST
	List<Edge> mst; // List of edges included in MST
	int count; // MST has n-1 edges
	int N; // No of vertices in graph

	MST(Graph g) {
		super(g, new MSTVertex((Vertex) null));
		N = g.size();
	}

	public static class MSTVertex implements Index, Comparable<MSTVertex>, Factory {
		boolean visited; // visited flag
		Vertex parent; // parent of vertex
		Integer d; // distance
		Vertex vertex; // vertex object
		int index;
		Edge incidentEdge; // Edge reaching out to this MSTVertex

		MSTVertex(Vertex u) {
			vertex = u;
			d = Integer.MAX_VALUE;
		}

		MSTVertex(MSTVertex u) { // for prim2
		}

		public MSTVertex make(Vertex u) {
			return new MSTVertex(u);
		}

		public void putIndex(int index) {
			this.index = index;
		}

		public int getIndex() {
			return index;
		}

		/**
		 * Ordering MSTVertices on the distance attribute.
		 */
		public int compareTo(MSTVertex otherVertex) {
			if (otherVertex == null || this.d > otherVertex.d) {
				return 1;
			} else if (this.d == otherVertex.d) {
				return 0;
			} else {
				return -1;
			}
		}
	}

	public long kruskal() {
		algorithm = "Kruskal";
		Edge[] edgeArray = g.getEdgeArray();
		mst = new LinkedList<>();
		wmst = 0;
		return wmst;
	}

	public long prim3(Vertex s) {
		algorithm = "Prim3";
		mst = new LinkedList<>();
		wmst = 0;
		IndexedHeap<MSTVertex> q = new IndexedHeap<>(g.size());

		// initialization
		for (Vertex u : g) {
			get(u).visited = false;
			get(u).parent = null;
			get(u).d = Integer.MAX_VALUE;
			get(u).putIndex(u.getIndex());
		}
		get(s).d = 0;

		// adding all the vertex to the indexed min heap
		for (Vertex u : g) {
			q.add(get(u));
		}

		while (!q.isEmpty()) {
			if (count == (N - 1)) 
				break;
			
			MSTVertex u = q.remove(); // MSTVertex
			Vertex uOld = u.vertex; // normal Vertex

			u.visited = true;
			wmst += u.d;

			// Adding edge to the MST, incrementing the count
			if (u.parent != null) {
				mst.add(u.incidentEdge);
				count++;
			}

			for (Edge e : g.incident(uOld)) {
				Vertex v = e.otherEnd(uOld);

				if (!get(v).visited && (e.getWeight() < get(v).d)) {
					get(v).d = e.getWeight();
					get(v).parent = uOld;
					get(v).incidentEdge = e; 
					q.decreaseKey(get(v)); 
				}
			}
		}
		return wmst;
	}

	public long prim2(Vertex s) {
		algorithm = "PriorityQueue<Vertex>";
		mst = new LinkedList<>();
		wmst = 0;
		PriorityQueue<MSTVertex> q = new PriorityQueue<>();
		return wmst;
	}

	public long prim1(Vertex s) {
		algorithm = "PriorityQueue<Edge>";
		mst = new LinkedList<>();
		wmst = 0;
		PriorityQueue<Edge> q = new PriorityQueue<>();
		return wmst;
	}

	public static MST mst(Graph g, Vertex s, int choice) {
		MST m = new MST(g);
		switch (choice) {
		case 0:
			m.kruskal();
			break;
		case 1:
			m.prim1(s);
			break;
		case 2:
			m.prim2(s);
			break;
		case 3:
			m.prim3(s);
			break;
		default:
			// Boruvka to be implemented next
			break;
		}
		return m;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in;
		String string = "5 6 1 2 6 2 4 7 4 5 3 5 2 2 2 3 5 3 1 1";
		int choice = 3; // prim3
		if (args.length == 0 || args[0].equals("-")) {
			in = new Scanner(System.in);
		} else {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		}

		if (args.length > 1) {
			choice = Integer.parseInt(args[1]);
		}

		Graph g = Graph.readGraph(in);
		Vertex s = g.getVertex(1);

		Timer timer = new Timer();
		MST m = mst(g, s, choice);
		System.out.println(m.algorithm + "\n" + m.wmst);
		System.out.println(timer.end());
	}
}
