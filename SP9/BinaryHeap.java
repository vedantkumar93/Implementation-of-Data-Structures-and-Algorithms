// Starter code for SP5

// Change to your netid
package vxk180003;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BinaryHeap<T extends Comparable<? super T>> {
	Comparable[] pq;
	int size;

	// Constructor for building an empty priority queue using natural ordering of T
	public BinaryHeap(int maxCapacity) {
		pq = new Comparable[maxCapacity];
		size = 0;
	}

	// add method: resize pq if needed
	/**
	 * adds new element along with resize in case PQ is full
	 * 
	 * @author vxk180003
	 * @param x
	 * @return
	 */
	public boolean add(T x) {
		if (size == pq.length) {
			resize();
			System.out.println("PQ Resized to: " + pq.length);
		}
		pq[size++] = x;
		percolateUp(size - 1);
		return true;
	}

	/**
	 * adds new element to existing PQ if space is available
	 * 
	 * @author vxk180003
	 * @param x
	 * @return
	 */
	public boolean offer(T x) {
		// can resize() PQ here:
		if (size == pq.length) {
			return false;
		}
		// Adding to the leaf
		move(size, x); // pq[size] = x;
		// Moving to the appropriate place
		percolateUp(size);
		size++;
		return true;
	}

	// throw exception if pq is empty
	/**
	 * Removes the element at the top of PQ and throws exception if no more element
	 * is left
	 * 
	 * @author vxk180003
	 * @return
	 * @throws NoSuchElementException
	 */
	public T remove() throws NoSuchElementException {
		T result = poll();
		if (result == null) {
			throw new NoSuchElementException("Priority queue is empty");
		} else {
			return result;
		}
	}

	// return null if pq is empty
	/**
	 * removes the element from PQ if any present
	 * 
	 * @author vxk180003
	 * @return
	 */
	public T poll() {
		if (size == 0) {
			return null;
		}
		// The first element which is to be removed
		Comparable<? super T> min = min();
		move(0, pq[--size]); // pq[0] = pq[--size];

		// Moving newly added element to appropriate place
		percolateDown(0);

		return (T) min;
	}

	public T min() {
		return peek();
	}

	// return null if pq is empty
	/**
	 * returns the element at the top of PQ without removing it
	 * 
	 * @author vxk180003
	 * @return
	 */
	public T peek() {
		if (isEmpty())
			return null;
		return (T) pq[0];

	}

	/**
	 * parent node index
	 * 
	 * @param i
	 * @return
	 */
	int parent(int i) {
		return (i - 1) / 2;
	}

	/**
	 * left child node index
	 * 
	 * @param i
	 * @return
	 */
	int leftChild(int i) {
		return 2 * i + 1;
	}

	/**
	 * right child node index
	 * 
	 * @param i
	 * @return
	 */
	int rightChild(int i) {
		return 2 * i + 2;
	}

	/** pq[index] may violate heap order with parent */
	/**
	 * used in insertion process
	 */
	@SuppressWarnings("unchecked")
	void percolateUp(int index) {
		Comparable<? super T> x = pq[index];

		// pq[index] may violate heap order with parent***
		while (0 < index && (compare(x, pq[parent(index)]) < 0)) {
			move(index, pq[parent(index)]); // pq[index] = pq[parent(index)];
			index = parent(index);
		}
		move(index, x); // pq[index] = x;
	}

	/** pq[index] may violate heap order with children */
	/**
	 * used in deletion process
	 * 
	 * @param index
	 */
	void percolateDown(int index) {
		Comparable<? super T> x = pq[index];
		int c = leftChild(index); // (2 * index) + 1;

		// pq[i] may violate heap order with children***
		while (c <= size - 1) {
			// right child has higher priority
			if (c < (size - 1) && (compare(pq[c], pq[c + 1]) > 0)) {
				c++;
			}

			if (compare(x, pq[c]) <= 0) {
				break;
			}

			move(index, pq[c]); // pq[index] = pq[c];
			index = c;
			c = leftChild(index); // 2 * index + 1;
		}
		move(index, x); // pq[index] = x;
	}

	/**
	 * Assigns x to pq[dest]. Indexed Heap will override this method.
	 * 
	 * @param dest the index where x is to be assigned.
	 * @param x    the element to be assigned.
	 */
	void move(int dest, Comparable x) {
		pq[dest] = x;
	}

	@SuppressWarnings("unchecked")
	int compare(Comparable a, Comparable b) {
		return ((T) a).compareTo((T) b);
	}

	/** Create a heap. Precondition: none. */
	void buildHeap() {
		for (int i = parent(size - 1); i >= 0; i--) {
			percolateDown(i);
		}
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public int size() {
		return size;
	}

	// Resize array to double the current size
	void resize() {
		pq = Arrays.copyOf(pq, pq.length * 2);
	}

	private void printBinaryHeap() {
		for (int i = 0; i < size; i++) {
			System.out.print(pq[i] + " ");
		}
		System.out.println();
	}

	public interface Index {
		public void putIndex(int index);

		public int getIndex();
	}

	public static class IndexedHeap<T extends Index & Comparable<? super T>> extends BinaryHeap<T> {

		public IndexedHeap(int maxCapacity) {
			super(maxCapacity);
		}

		// restore heap order property after the priority of x has decreased
		void decreaseKey(T x) {
			int thisIndex = x.getIndex();
			percolateUp(thisIndex);
		}

		@Override
		void move(int i, Comparable x) {
			super.move(i, x);

			// updating the index after moving
			T xImage = (T) x;
			xImage.putIndex(i);
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println("**********Min HEAP***************");
		System.out.println("1 for offer");
		System.out.println("2 for add");
		System.out.println("3 for poll");
		System.out.println("4 for remove");
		System.out.println("5 for peek");
		int n = 10;
		if (args.length > 0) {
			n = Integer.parseInt(args[0]);
		}
		Integer[] arr = { 0, 9, 7, 5, 3, 1, 8, 6, 4, 2 };
		BinaryHeap<Integer> bh = new BinaryHeap<Integer>(n);
		for (int i = 0; i < n; i++) {
			bh.offer(arr[i]);
		}
		bh.printBinaryHeap();

		Scanner in = new Scanner(System.in);
		whileloop: while (in.hasNext()) {
			int com = in.nextInt();
			switch (com) {
			case 1: // Add a new element x to the priority queue
					// return false if priority queue is full
				int elemAdd = in.nextInt();
				System.out.println(bh.offer(elemAdd));
				bh.printBinaryHeap();
				break;
			case 2: // Add a new element x to the priority queue, with resizing
				int elemAddOne = in.nextInt();
				bh.add(elemAddOne);
				bh.printBinaryHeap();
				break;
			case 3: // Remove and return the element with max priority (max value)
				System.out.println(bh.poll());
				bh.printBinaryHeap();
				break;
			case 4: // Remove and return the element with max priority (max value),
					// throw exception if priority queue is empty
				System.out.println(bh.remove());
				bh.printBinaryHeap();
				break;
			case 5: // Return the element with max priority, without removing it
				System.out.println(bh.peek());
				bh.printBinaryHeap();
				break;
			default: // Exit loop
				break whileloop;
			}
		}
		bh.printBinaryHeap();
	}

}
