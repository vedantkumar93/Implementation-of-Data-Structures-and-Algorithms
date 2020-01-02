/* Starter code for LP3 */

// Change this to netid of any member of team
package vjs180000;

import java.util.Iterator;
import java.util.Random;
import java.util.*;
// Skeleton for skip list implementation.

/**
 * Skip List: an efficient linked list implementation of an ordered map
 * @param <T>
 */
public class SkipList<T extends Comparable<? super T>> {
    static final int PossibleLevels = 33;

    Entry head, tail;    // first and last element of top level
    int size, maxlevel;                   // number of entries in skip list
    Entry[] last;
    Random random;           // coin toss
    int currentHeight;      // height starting from 1
    int [] position;

    static class Entry<E extends Comparable> {
        E element;
        Entry[] next;
        Entry prev;
        int[] span;
        int level;

        public Entry(E x, int lev) {
            element = x;
            next = new Entry[lev];
            this.level = lev;
            this.span = new int[lev];
            // add more code if needed
        }

        public E getElement() {
            return element;
        }

        public int compareTo(Entry y){
            return (this.element).compareTo(y.element);
        }
    }

    static class rebuildElement<E>{
        E element;
        public rebuildElement(E element){
            this.element = element;
        }
        public E getElement(){ return element;}
    }


    // Constructor
    public SkipList() {
        head = new Entry(Long.MIN_VALUE,PossibleLevels);  	//first element of the top level
        tail = new Entry(Long.MAX_VALUE,0);					//last element of the top level
        size=0;
        this.maxlevel = 1;
        currentHeight =1;					//height
        this.position = new int[33];
        this.last = new Entry[33];
        random = new Random();				// coin toss
        for(int i=0; i<PossibleLevels-1; i++) {
            head.next[i] = this.tail;
            tail.prev = head;
            this.last[i] = this.head;
            this.position[i] = 0;
        }
    }

    // Add x to list. If x already exists, reject it. Returns true if new node is added to list
    public boolean add(T x) {
        Entry p = findEntry(x);
        // check if the element is same
        if(p.element.compareTo(x)==0){
            p.element=x;
            return false;
        }
        // Coin Toss Experiment to give random height
        // initial height is 1 so that element get added on lowest level
        int ht=1;
        while(random.nextDouble()<0.5 && currentHeight <=PossibleLevels)
            ht++;
        Entry q = new Entry(x,ht);
        for(int i=0; i<ht; i++) {
            q.next[i]=p.next[i];
            // prev is stored only for bottom most level
            if(i==0) {
                q.prev = p;
                p.next[i].prev = q;
            }
            p.next[i]=q;
            while(p.next.length<=i+1)
                p=p.prev;
        }
        size++;
        currentHeight = currentHeight>ht?currentHeight:ht;
        return true;
    }

   
   //findEntry(k): find the largest key x <= k
     //    on the LOWEST level of the Skip List
    private Entry<T> findEntry(T x){
      //Start at "head"
	    Entry<T> p = head;
        int level= currentHeight-1; //level start from currentHeight-1 to 0
        while(true){
         //Search RIGHT until you find a LARGER entry
	         while(p.next[level]!=tail && p.next[level].element.compareTo(x)<=0){
                p=p.next[level];
            }
        // Go down one level if you can...
	   
            if(level==0)
                break;
            level--;
        }
        return p;
    }

    // Find smallest element that is greater or equal to x
    public T ceiling(T x) {
        if(!isEmpty()) {
            Entry p = findEntry(x);
            if (p.element.compareTo(x) == 0)
                return (T) p.element;
            return (T) p.next[0].element;
        }
        return null;
    }

    // Does list contain x?
    public boolean contains(T x) {
        if(!isEmpty()){
            Entry p = findEntry(x);
            if (p.element.compareTo(x) == 0)
                return true;
        }
        return false;
    }

    // Return first element of list
    public T first() {
        if(!isEmpty()){
            return (T) head.next[0].element;
        }
        return null;
    }

    // Find largest element that is less than or equal to x
    public T floor(T x) {
        if(!isEmpty()) {
            Entry p = findEntry(x);
            return (T) p.element;
        }
        return null;
    }

    // Return element at index n of list.  First element is at index 0.
    public T get(int n) {
        return getLinear(n);
    }

    // O(n) algorithm for get(n)
    public T getLinear(int n) {
        Entry<T> cur = head;
        if (n > (size) || n < 0) {
            return null;
        }
        for (int i = 0; i < n; i++) {
            cur = cur.next[0];
        }
        return (T) cur.next[0].element;
    }

    // Optional operation: Eligible for EC.
    // O(log n) expected time for get(n).
    public T getLog(int n) {
        if(this.isEmpty()){
            // System.out.println("Null from get log");
            return null;
        }
        else{
            Entry<T> cursor = this.head;
            int position = -1;
            for(int i= this.maxlevel-1; i>=0 ; i--){
                while((position + cursor.span[i]) <= n && cursor.next[i] != this.tail ){
                    position+=cursor.span[i];
                    cursor = cursor.next[i];
                }
            }
            return cursor.getElement();
        }
    }

    // Is the list empty?
    public boolean isEmpty() {
        return size==0?true:false;
    }

    // Iterate through the elements of list in sorted order
    public Iterator<T> iterator() {
        return new SkipListIterator(this);
    }
    protected class SkipListIterator implements Iterator<T>{
        SkipList<T> skipList;
        Entry<T> cursor, tail;
        // ready flag is used to make sure the element is ready to be remvoved
        boolean ready;

        SkipListIterator(SkipList<T> skipList){
            this.cursor = skipList.head;
            this.tail = skipList.tail;
            this.ready = false;
        }

        @Override
        public boolean hasNext() {
            return cursor.next[0] != this.tail;
        }

        @Override
        public T next() {
            this.cursor = this.cursor.next[0];
            this.ready = true;
            return this.cursor.getElement();
        }

        // Removes the element (cursor) most recent next()
        // Remove can be used only after next has been called
        @Override
        public void remove() {
            if(!this.ready){
                throw new NoSuchElementException();
            }
            this.cursor = this.cursor.prev;
            skipList.remove((T) this.cursor.next[0].getElement());
            this.ready = false;

        }
    }

    // Return last element of list
    public T last() {
        if(!isEmpty()){
            return (T) tail.prev.element;
        }
        return null;
    }

    private boolean add(T element, int level) {
        Entry<T> newNode = new Entry<T>(element, level);
        /**
         * When the list is empty and there are no nodes apart from just the head and tail, 
         * the span of head is one and the span of new element that would be inserted between head 
         * and tail will be zero since that node wont be going over any nodes, since the next is the tail.
         */
        if(this.isEmpty()){
            for(int i=0; i < this.maxlevel; i++){
                head.span[i] = 1;
            }
            for(int i=0; i< level; i++){
                newNode.span[i] = 0;
            }
        }else{
            int start = this.getTravelledDistance();
            int previous = -1;

            // Computing Spans
            for(int i=this.maxlevel-1; i >= 0; i--){
                previous+=position[i];
                if(i<level){
                    newNode.span[i] = this.last[i].span[i] + 1 - (start - previous);
                    this.last[i].span[i] = start - previous;
                }else{
                    this.last[i].span[i]+=1;
                }
            }
        }
        for(int j = 0; j <= level-1; j++){
            newNode.next[j] = this.last[j].next[j];
            this.last[j].next[j] = newNode;

        }
        newNode.next[0].prev = newNode;
        newNode.prev = this.last[0];
        this.size+=1;
        return true;
    }

    public int getTravelledDistance(){
        int start = 0;
        for(int i = this.maxlevel -1; i >= 0; i--){
            start+=this.position[i];
        }
        return start;
    }
    // Optional operation: Reorganize the elements of the list into a perfect skip list
    // Not a standard operation in skip lists. Eligible for EC.
    public void rebuild() {
        rebuildElement[] elements = new rebuildElement[size()];
        int[] perfectLevels = new int[this.size()];
        int index = 0;
        int n = this.size();
        int count = n;
        for(int i = 0; i<n; i++){
            T slElement = this.remove(getLog(0));
            elements[i] = new rebuildElement(slElement);
        }
        this.maxlevel = (int)(Math.ceil(Math.log10(n)/Math.log10(2)));

        // At this point we have all the elements of the skiplist in an array(elements) and the original one is empty, so now we have to set everything to default
        for(int i = 0; i< this.maxlevel; i++) {
            this.head.next[i] = this.tail;
            this.last[i] = this.head;
            this.head.span[i] = 0;
        }
        calculatePerfectLevels(0, n-1, perfectLevels);
        for(int i = n-1; i >= 0; i--){
            this.add((T) elements[i].getElement(), perfectLevels[i]);
        }

    }

    private void calculatePerfectLevels(int first, int last, int[] perfectLevels){
        if(last<first){
            return;
        }
        else if(first==last){
            perfectLevels[first] = 1;
            return;
        }else{
            int mid = (last-first+1)/2;
            int temp = last - first + 1;
            perfectLevels[first+mid] = (int) Math.ceil(Math.log10(temp) / Math.log10(2));
            calculatePerfectLevels(first, first+mid-1, perfectLevels);
            calculatePerfectLevels(first+mid+1, last, perfectLevels);
        }
    }
    // Remove x from list.  Removed element is returned. Return null if x not in list
    public T remove(T x) {
        if(!isEmpty()){
            {
                Entry p = findEntry(x);
                // check if the element is same
                if(p.element.compareTo(x)==0) {
                    x = (T) p.element;
                    int ht = p.next.length;
                    p = p.prev;
                    for (int i = 0; i < ht; i++) {
                        p.next[i] = p.next[i].next[i];
                        // prev is stored only for bottom most level
                        if(i==0)
                            p.next[i].prev = p;
                        while(p.next.length<=i+1)
                            p=p.prev;
                    }
                    size--;
                }
                else
                    x = null;
            }
        }
        return x;
    }

    // Return the number of elements in the list
    public int size() {
        return size;
    }
    }
