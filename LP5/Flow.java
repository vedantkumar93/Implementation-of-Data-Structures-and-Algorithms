// Starter code for max flow
package vxk180003;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import vxk180003.Graph.Edge;
import vxk180003.Graph.Vertex;

public class Flow extends GraphAlgorithm<Flow.FlowVertex>{
	FlowVertex source;
	FlowVertex sink;
	HashMap<Edge, FlowEdge> map;
	Queue<FlowVertex> queue;
	/**
	 * height: relabel push front height
	 * parent: store vertex that was visited right before this vertex
	 * excess: extra flow in vertex
	 * visited: whether this vertex is visited in bfs
	 * name: name of vertex
	 * adj, revAdj: similar to adj, revAdj in Graph.java
	 * self: reference of vertex
	 */
	static class FlowVertex{
		int name;
		int excess;
		int height;
		Vertex self;
		int level;
		boolean visited;
		List<FlowEdge> parent;
		List<FlowEdge> adj;
		List<FlowEdge> revAdj;
		public FlowVertex(Vertex self) {
			this.name = self.name;
			this.height = 0;
			this.self = self;
			this.excess = 0;
			adj = new LinkedList<>();
			revAdj = new LinkedList<>();
		}
		@Override
		public String toString() {
			return "Vertex [name=" + (name + 1) + ", excess=" + excess + ", height=" + height + "]";
		}
	}
	
	/**
	 * capacity: capacity of this edge
	 * flow:flow on this edge
	 * self: reference of self edge
	 */
	static class FlowEdge{
		int capacity;
		int flow;
		Edge self;
		public FlowEdge(int capacity, int flow, Edge self) {
			this.capacity = capacity;
			this.flow = flow;
			this.self = self;
		}
		@Override
		public String toString() {
			return "Edge [capacity=" + capacity + ", flow=" + flow + ", from" + self.from +":to"+ self.to + "]";
		}
		
		
		@Override
		public boolean equals(Object obj) {
			if(obj == null) return false;
			if(obj == this) return true;
			if(!(obj instanceof FlowEdge)) return false;
			FlowEdge other = (FlowEdge) obj;
			return this.self.equals(other.self);
		}
		@Override
		public int hashCode() {
			return this.self.hashCode();
		}
		
		public FlowVertex otherEnd(FlowVertex other, FlowVertex[] nodes) {
			int ee = this.self.otherEnd(other.self).name;
			FlowVertex end = nodes[ee];
			return end;
		}
	}
	
	public Flow(Graph g, Vertex s, Vertex t, HashMap<Edge, Integer> capacity) {
		super(g);
		queue = new LinkedList<FlowVertex>();
		node = new FlowVertex[g.size()];
		map = new HashMap<>();
		for(Vertex v : g) {
			node[v.name] = new FlowVertex(v);
			List<FlowEdge> out = new LinkedList<>();
			for(Edge e : v.adj) {
				FlowEdge edge = null;
				if(!map.containsKey(e)) { 
					edge = new FlowEdge(capacity.get(e), 0, e);
					map.put(e, edge);
				}
				else { edge = map.get(e); }
				out.add(edge);
			}
			
			List<FlowEdge> in = new LinkedList<>();
			for(Edge e : v.revAdj) {
				FlowEdge edge = null;
				if(!map.containsKey(e)) { 
					edge = new FlowEdge(capacity.get(e), 0, e);
					map.put(e, edge);
				}
				else { edge = map.get(e); }
				in.add(edge);
			}
			
			node[v.name].adj = out;
			node[v.name].revAdj = in;
		}
		source = node[s.name];
		sink = node[t.name];
	}

    // Return max flow found. Using FIFO. 
    public int preflowPush() {

    	// initialize all vertex and edges for relabelToFront algo
		for(FlowVertex v : node) {
			v.parent = new LinkedList<>();
			v.level = -1;
			v.visited = false;
			v.excess = 0;
			v.height = 0;
			for(FlowEdge edge : v.adj) { edge.flow = 0; }
			for(FlowEdge edge : v.revAdj) { edge.flow = 0; }
		}
		source.height = node.length;
		
		for(FlowEdge e : source.adj) {
			FlowVertex u = e.otherEnd(source, node);
			e.flow = e.capacity;
			source.excess = source.excess - e.capacity;
			u.excess = u.excess + e.capacity;
		}
	
		// Return max flow found by relabelToFront algorithm
 		List<FlowVertex> L = new LinkedList<>();
 		for(FlowVertex v : node) {
 			if(!v.equals(source) && !v.equals(sink)) {
 				L.add(v);
 			}
 		}
 		boolean done = false;
 		while(!done) {
 			Iterator<FlowVertex> it = L.iterator();
 			done = true;
 			FlowVertex u = null;
 			while(it.hasNext()) {
 				u = it.next();
 				if(u.excess == 0) continue;
 				int oldHeight = u.height;
 				discharge(u);
 				if(oldHeight != u.height) {
 					done = false;
 					break;
 				}
 			}
 			
 			if(!done) {
 				it.remove();
 				L.add(0, u);
 			}
 		}
 		return sink.excess;
 	
    }
	
	/**
	 * relabel or push a vertex
	 * @param u vertex that involves with relabel or push
	*/
	private void discharge(FlowVertex u) {
		while(u.excess > 0) {
			for(FlowEdge e : u.adj) {
				FlowVertex v = e.otherEnd(u, node);
				if(inGf(u, e) && u.height == v.height + 1) {
					push(u, v, e);
					if(u.excess == 0) return;
				}
			}
			
			for(FlowEdge e : u.revAdj) {
				FlowVertex v = e.otherEnd(u, node);
				if(inGf(u, e) && u.height == v.height + 1) {
					push(u, v, e);
					if(u.excess == 0) return;
				}
			}
			relabel(u);
			
		}
	}
	
	/**
	 * push escess of u to v by following e
	 * @param u
	 * @param v
	 * @param e
	 */
	private void push(FlowVertex u, FlowVertex v, FlowEdge e) {
		int delta;
		if(e.self.from.equals(u.self)) {
			delta = e.capacity - e.flow;
		}else{
			delta = e.flow;
		}
		delta = Math.min(delta, u.excess);
		if(e.self.from.equals(u.self)) {
			e.flow += delta;
		}else {
			e.flow -= delta;
		}
		u.excess -= delta;
		v.excess += delta;
	}
	
	/**
	 * relabel vertex u
	 * @param u
	 */
	private void relabel(FlowVertex u) {
		int height = Integer.MAX_VALUE;
		for(FlowEdge e : u.adj) {
			if(e.capacity > e.flow) {
				FlowVertex v = e.otherEnd(u, node);
				height = Math.min(height, v.height);
			}
		}
		
		for(FlowEdge e : u.revAdj) {
			if(0 < e.flow) {
				FlowVertex v = e.otherEnd(u, node);
				height = Math.min(height, v.height);
			}
		}
		u.height = height + 1;
	}
	
	/**
	 * @param u vertex that points to another vertex in Gf
	 * @param e edge that u follows to point to another point
	 * @return whether an edge in Gf point to another vertex by following e
	 */
	private boolean inGf(FlowVertex u, FlowEdge e) {
		return u.self.equals(e.self.from) ? e.capacity > e.flow : e.flow > 0;
	}
	
    // flow going through edge e
    public int flow(Edge e) {
    	return map.get(e).flow;
    }

    // capacity of edge e
    public int capacity(Edge e) {
	return 0;
    }

    /* After maxflow has been computed, this method can be called to
       get the "S"-side of the min-cut found by the algorithm
    */
    public Set<Vertex> minCutS() {
	return null;
    }

    /* After maxflow has been computed, this method can be called to
       get the "T"-side of the min-cut found by the algorithm
    */
    public Set<Vertex> minCutT() {
	return null;
    }
}
