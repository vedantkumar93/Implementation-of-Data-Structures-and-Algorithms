package vxk180003;

//starter code for Tries

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Trie<V> {
	
	private class Entry {
		V value; //id for each word
		char c;	// character stored at each node
		HashMap<Character, Entry> child;	// list of successive nodes
		boolean isLeaf;	// is Leaf Node
		
		Entry() {
			this.value = null;
			child = new HashMap<>();
		}
		
		Entry(char c){
			this();
			this.c = c;
		}
	}

	private Entry root;
	private int size;

	public Trie() {
		root = new Entry();
		size = 0;
	}

//	private V put(Iterator<Character> iter, V value) {
//		return null;
//	}
//
//	private V get(Iterator<Character> iter) {
//		return null;
//	}
//
//	private V remove(Iterator<Character> iter) {
//		return null;
//	}

	// public methods

	/**
	 * Insert word to trie data structure
	 * @param s -	input word
	 * @param value - id for input word
	 * @return	- return id of new word
	 */
	public V put(String s, V value) {
        HashMap<Character, Entry> children = root.child;
        
        for(int i=0; i<s.length(); i++){
            char c = s.charAt(i);
 
            Entry t;
            
            //check if it already exists
            if(children.containsKey(c)){
                    t = children.get(c);
            }else{
            	// create new node
                t = new Entry(c);
                children.put(c, t);
            }
 
            children = t.child;
 
            //set leaf node
            if(i==s.length()-1) {
                t.value = value;
                t.isLeaf = true;
            }
        }
        size++;
		return value;
	}

	/**
	 * Method used to check if the word exists in the trie
	 * @param s - input word
	 * @return - id of input word on valid input otherwise null 
	 */
	public V get(String s) {
		Map<Character, Entry> children = root.child; 
        Entry t = null;
        for(int i=0; i<s.length(); i++){
            char c = s.charAt(i);
            if(children.containsKey(c)){
                t = children.get(c);
                children = t.child;
            }else{
                return null;
            }
        }
        if(t != null && t.isLeaf) 
            return t.value;
        else
            return null;
    }
	
	/**
	 * remove the word if present 
	 * @param s	- input word
	 * @return id of word if present otherwise null
	 */
	public V remove(String s) {
		Map<Character, Entry> children = root.child;
		Entry t = null;
		for(int i=0; i<s.length(); i++){
            char c = s.charAt(i);
            if(children.containsKey(c)) { 
            	t = children.get(c);
            	children = t.child;
            }
            else
            	return null;
		}
		// setting leaf to false and value to null, nodes will remain present
		if(t != null && t.isLeaf) {
			V value = t.value;
			t.value=null;
			t.isLeaf=false;
			size--;
			return value;
		}
		return null;
	}

	// How many words in the dictionary start with this prefix?
	/**
	 * return count of word for the prefix
	 * @param s - input prefix word
	 * @return count of words otherwise 0
	 */
	public int prefixCount(String s) {
		Map<Character, Entry> children = root.child;
		Entry t = null;
		for(int i=0; i<s.length(); i++){
            char c = s.charAt(i);
            if(children.containsKey(c)) { 
            	t = children.get(c);
            	children = t.child;
            }
            else
            	return 0;
		}
		int count=0;
		if(t.isLeaf)
			count++;
		count += func(t);
		
		return count;
	}
	
	/**
	 * helper function for prefixCount
	 * @param t
	 * @return
	 */
	private int func(Trie<V>.Entry t) {
		int count = 0;
		for(java.util.Map.Entry<Character, Trie<V>.Entry> entrySet: t.child.entrySet()){
			if(entrySet.getValue().isLeaf)
				count = 1+func(entrySet.getValue());
			else
				count += func(entrySet.getValue());
		}
		return count;
	}

	public int size() {
		return size;
	}

	public static class StringIterator implements Iterator<Character> {
		char[] arr;
		int index;

		public StringIterator(String s) {
			arr = s.toCharArray();
			index = 0;
		}

		public boolean hasNext() {
			return index < arr.length;
		}

		public Character next() {
			return arr[index++];
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}
	}

//	Sample Output:-----------------
//	Anthole
//	Anteena
//	Ant
//	Antwerp
//	Antique
//	Anthouse
//	End
//	Size: 6
//	Antique
//	Antique	 Position:5
//	Anteena
//	Anteena	 Position:2
//	Anxxx
//	Anxxx	 Position:null
//	End
//	Ant
//	Ant	 Count: 6
//	Anth
//	Anth	 Count: 2
//	Antwer
//	Antwer	 Count: 1
//	Anxddd
//	Anxddd	 Count: 0
//	End
//	Antwerp
//	Antwerp	 Removed: 4
//	Ant
//	Ant	 Removed: 3
//	End
//	Size: 4
//	Ant
//	Ant	 Count: 4
//	Antw
//	Antw	 Count: 0
//	Anth
//	Anth	 Count: 2
//	End

	public static void main(String[] args) {
		Trie<Integer> trie = new Trie<>();
		int wordno = 0;
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			String s = in.next();
			if (s.equals("End")) {
				break;
			}
			wordno++;
			trie.put(s, wordno);
		}
		
		System.out.println("Size: "+trie.size);
		
		while (in.hasNext()) {
			String s = in.next();
			if (s.equals("End")) {
				break;
			}
			Integer val = trie.get(s);
			System.out.println(s + "\t Position:" + val);
		}
		
		while (in.hasNext()) {
			String s = in.next();
			if (s.equals("End")) {
				break;
			}
			Integer val = trie.prefixCount(s);
			System.out.println(s + "\t Count: " + val);
		}
		
		while (in.hasNext()) {
			String s = in.next();
			if (s.equals("End")) {
				break;
			}
			Integer val = trie.remove(s);
			System.out.println(s + "\t Removed: " + val);
		}
		
		System.out.println("Size: "+trie.size);
		
		while (in.hasNext()) {
			String s = in.next();
			if (s.equals("End")) {
				break;
			}
			Integer val = trie.prefixCount(s);
			System.out.println(s + "\t Count: " + val);
		}
		
		
	}
}
