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
	 * @author vxk180003
	 * @param x
	 * @return
	 */
	public boolean add(T x) {
		if (size == pq.length) {
			resize();
			System.out.println("PQ Resized to: "+pq.length);
		}
		pq[size++] = x;
		percolateUp(size - 1);
		return true;
	}
	
	
	/**
	 * adds new element to existing PQ if space is available
	 * @author vxk180003
	 * @param x
	 * @return
	 */
	public boolean offer(T x) {
		if (size == pq.length)
			return false;
		return add(x);
	}

	// throw exception if pq is empty
	/**
	 * Removes the element at the top of PQ and throws exception if no more element is left
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
	 * @author vxk180003
	 * @return
	 */
	public T poll() {
		if (isEmpty())
			return null;

		T element = (T) pq[0];
		pq[0] = pq[size - 1];
		pq[--size] = 0;
		percolateDown(0);
		return element;
	}

	public T min() {
		return peek();
	}

	// return null if pq is empty
	/**
	 * returns the element at the top of PQ without removing it
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
	 * @param i
	 * @return
	 */
	int parent(int i) {
		return (i - 1) / 2;
	}

	/**
	 * left child node index 
	 * @param i
	 * @return
	 */
	int leftChild(int i) {
		return 2 * i + 1;
	}

	/**
	 * right child node index 
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
	void percolateUp(int index) {
		Comparable temp;

		// maximum index can reach upto root node
		while (index >= 0) {

			// child > parent
			if (parent(index) >= 0 && compare(pq[index], pq[parent(index)]) > 0) {
				temp = pq[index];
				pq[index] = pq[parent(index)];
				pq[parent(index)] = temp;
				index = parent(index);
			} else {
				break;
			}
		}
	}

	/** pq[index] may violate heap order with children */
	/**
	 * used in deletion process
	 * @param index
	 */
	void percolateDown(int index) {
		Comparable temp;

		while (index <= parent(size - 1)) {

			// parent is less than left and right child, parent < (left and right)
			if (size > leftChild(index) && size > rightChild(index) && compare(pq[index], pq[leftChild(index)]) < 0
					&& compare(pq[index], pq[rightChild(index)]) < 0) {

				// left child is greater than right child, left > right > parent
				if (compare(pq[leftChild(index)], pq[rightChild(index)]) > 0) {
					// swap parent and left
					temp = pq[index];
					pq[index] = pq[leftChild(index)];
					pq[leftChild(index)] = temp;
					index = leftChild(index);
				}

				// right child is greater than left child, right > left > parent
				else {
					// swap parent and right
					temp = pq[index];
					pq[index] = pq[rightChild(index)];
					pq[rightChild(index)] = temp;
					index = rightChild(index);
				}

			}

			// left child > parent > right
			else if (size > leftChild(index) && compare(pq[index], pq[leftChild(index)]) < 0) {
				// swap parent and left
				temp = pq[index];
				pq[index] = pq[leftChild(index)];
				pq[leftChild(index)] = temp;
				index = leftChild(index);
				index = leftChild(index);
			}

			// right > parent > left
			else if (size > rightChild(index) && compare(pq[index], pq[rightChild(index)]) < 0) {
				// swap parent and right
				temp = pq[index];
				pq[index] = pq[rightChild(index)];
				pq[rightChild(index)] = temp;
				index = rightChild(index);
			}

			else {
				break;
			}
		}
	}

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
		for(int i=0; i<size; i++) {
			System.out.print(pq[i]+ " ");
		}
		System.out.println();
	}

	public static void main(String[] args) throws Exception {
		System.out.println("**********Max HEAP***************");
		System.out.println("1 for offer");
		System.out.println("2 for add");
		System.out.println("3 for poll");
		System.out.println("4 for remove");
		System.out.println("5 for peek");
		int n = 10;
		if (args.length > 0) {
			n = Integer.parseInt(args[0]);
		}
		Integer[] arr = {0,9,7,5,3,1,8,6,4,2};
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
