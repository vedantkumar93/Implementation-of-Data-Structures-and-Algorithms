package vxk180003;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import vxk180003.SinglyLinkedList; 

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DoublyLinkedList<T> extends SinglyLinkedList<T> {

	public DoublyLinkedList() {
		head = new DoubleEntry<>(null, null, null);
		tail = head;
	}

	static class DoubleEntry<E> extends Entry {
		Entry<E> prev;

		DoubleEntry(E x, Entry<E> nxt, Entry<E> prv) {
			super(x, nxt);
			prev = prv;
		}
	}

	public Iterator<T> iterator() {
		return new DLLIterator();
	}

	class DLLIterator extends SLLIterator {
		
		public boolean hasPrev() {
			return ((DoubleEntry<T>) cursor).prev != null;
		}

		public T prev() {
			prev = ((DoubleEntry<T>) prev).prev;
			cursor = ((DoubleEntry<T>) cursor).prev;
			return cursor.element;
		}

		public void add(T x) {
			if(cursor.element==null) {
				next();
			}
			DoubleEntry<T> etNew = new DoubleEntry<T>(x, null, null);
			etNew.next = cursor.next;
			etNew.prev = cursor;
			((DoubleEntry<T>) cursor.next).prev = etNew;
			cursor.next = etNew;
			size++;
		}
		
		public void remove() {
			super.remove();
			if(cursor.next.element!=null) {
				((DoubleEntry<T>) cursor.next).prev = prev;
			}
		}
	}

	// Add new elements to the end of the list
	public void add(T x) {
		add(new DoubleEntry<>(x, null, null));
	}

	public void add(DoubleEntry<T> ent) {
		ent.prev = tail;
		tail.next = ent;
		tail = tail.next;
		size++;
	}

	public static void main(String[] args) throws NoSuchElementException {
		int n = 10;
		if (args.length > 0) {
			n = Integer.parseInt(args[0]);
		}

		DoublyLinkedList<Integer> lst = new DoublyLinkedList<>();
		for (int i = 1; i <= n; i++) {
			lst.add(Integer.valueOf(i));
		}
		lst.printList();

		Iterator<Integer> it = lst.iterator();
		Scanner in = new Scanner(System.in);
		whileloop: while (in.hasNext()) {
			int com = in.nextInt();
			switch (com) {
			case 1: // Move to next element and print it
				if (it.hasNext()) {
					System.out.println(it.next());
				} else {
					break whileloop;
				}
				break;
			case 2: // Remove element
				it.remove();
				lst.printList();
				break;
			case 3: // Move to prev element and print it
				if (((DoublyLinkedList<Integer>.DLLIterator) it).hasPrev()) {
					System.out.println(((DoublyLinkedList<Integer>.DLLIterator) it).prev());
				} else {
					break whileloop;
				}
				break;
			case 4:
				((DoublyLinkedList<Integer>.DLLIterator) it).add((int)(Math.random()*10));
				lst.printList();
				break;
			default: // Exit loop
				break whileloop;
			}
		}
		lst.printList();
		lst.unzip();
		lst.printList();
	}
}
