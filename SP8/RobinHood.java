package vjs180000;
public class RobinHood<T> {
	int size; // The size of the hash_table.
	Object[] hash_table; // The hash table.
	int maximum_Displacement; // maximum  displacement any element can have.
	float loadfactor=(float) 0.5;
	
	// Initializes new hash hash_table using Robin Hood.
	public RobinHood() {
		hash_table = new Object[1024];
		maximum_Displacement = 0;
		size = 0;
	}
	/**
	 * 
	 * @param x input element
	 * @return true if present, false otherwise
	 */
	public boolean contains(T x) {
		int loc = hash(x);
		for (int d = 0; d <= maximum_Displacement; d++) {
			int index = (loc + d) % hash_table.length;
			if (x.equals(hash_table[index])) {
				return true;
			}
		}
		return false;
		}
		/**
		 * Removes the specified element from this set if it is present.
		 * @param x element removed
		 * @return true if element is removed success otherwise  false otherwise
		 */

		public boolean remove(T x) {
		int loc = hash(x);
		
		for (int d = 0; d <= maximum_Displacement; d++) {
			
			int index = (loc + d) % hash_table.length;
			
			if (x.equals(hash_table[index])) {
				hash_table[index] = null;
				size--;
				return true;
			}
		}
		return false;
	}

	/**
	 * It adds the element to this set if it is not already present.
	 * 
	 * @param x the element to be added
	 * @return true if successful insertion, false otherwise
	 */
	public boolean add(T x) {
		// we change the size of the table as per the load factor 
		// we can change the load factor as per the need
		
		if (size >= hash_table.length *loadfactor) {
			resize();
		}
		if (contains(x)) {
			return false;
		}
		int loc = hash(x);
		int d = 0;
		
		while (true) {
			if (hash_table[loc] == null) {
				hash_table[loc] = x;
				size++;
				return true;
			} 
			else if (displacement((T) hash_table[loc], loc) >= d) {
				d++;
				loc = (loc + 1) % hash_table.length;
				maximum_Displacement = Math.max(d, maximum_Displacement);
			} 
			else {
				T temp = x;
				x = (T) hash_table[loc];
				hash_table[loc] = temp;
				loc = (loc + 1) % hash_table.length;
				d = displacement(x, loc);
				maximum_Displacement = Math.max(d, maximum_Displacement);
			}
		}
	}
	

	// Returns the number of elements in the hash_table.
	public int size() {
		return size;
	}

	// Resizes the hash_table to double the size.
	private void resize() {
		Object[] oldTable = hash_table;
		hash_table = new Object[hash_table.length * 2];
		size = 0;
		maximum_Displacement = 0;
		
		for (Object x : oldTable) {
			if (x != null) {
				add((T) x);
			}
		}
	}
	/**
	 * Returns the displacement of element x at location 
	 * @param x - calculate displacement of
	 * @param location  table position of x
	 * @return the displacement of element x at location
	 */
	private int displacement(T x, int location) {
		int h = hash(x);
		return (location >= h) ? (location - h) : (hash_table.length + location - h);
	}

	/**
	 * @param x the element to hash
	 * @return hash of x
	 */
	private int hash(T x) {
		int h = x.hashCode();
		if (h < 0) {
			return (x.hashCode() + 1) % hash_table.length + hash_table.length - 1;
		}
		return x.hashCode() % hash_table.length;
	}
	public static<T> int distinctElements(T[] arr){
		RobinHood<T> dist = new RobinHood<>();
		
		for (T e : arr) { dist.add(e); }
		return dist.size();
	}
}