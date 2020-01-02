/*

 * Author: VISHAL SHAH

 * Author:VEDANT KUMAR

 * 

 */

package vxk180003;



import java.util.Scanner;




@SuppressWarnings("unchecked")
public class BoundedQueue<T>{

	private int front;

	private int rear;

	private static int QueueCapacity;

	private int CurrentQueueSize;

	Object[] BoundQueue	;

		

	public BoundedQueue(int bqsize) {

		BoundQueue =new Object[bqsize];

		front=0;

		rear=-1;

		CurrentQueueSize=0;

		QueueCapacity=bqsize;

		// TODO Auto-generated constructor stub

	}

	int size() {

		return CurrentQueueSize;

	}

	void QueuePrinting() {

		System.out.print("Elements of Queue: ");

		if (front<=rear) {

		for(int i=front;i<=rear;i++) {

			System.out.print(" "+BoundQueue[i]+" ");

			}

			System.out.println("");

		}

		else{

			for(int i=front;i<QueueCapacity;i++) {

				System.out.print(" "+BoundQueue[i]+" ");

				

			}

			for(int i=0;i<=rear;i++) {

				System.out.print(" "+BoundQueue[i]+" ");

			}

			System.out.println("");	

		}

	}

	boolean offer(T x) {

		if(size()==QueueCapacity) {

			return false;

		}

		rear=(rear+1)%QueueCapacity;

		BoundQueue[rear]=x;

		CurrentQueueSize=CurrentQueueSize+1;

		return true;

	}

	

	T poll() {

		if (CurrentQueueSize==0) {return null;}

		Object ElementRemoved=BoundQueue[front];

		front=(front+1)%QueueCapacity;

		CurrentQueueSize=CurrentQueueSize-1;

		return (T)ElementRemoved;

	}

	T peek() {

		

		return CurrentQueueSize==0?null:(T)BoundQueue[front];

	}

	

	boolean isEmpty() {

		return CurrentQueueSize==0?true:false;

	}

	void clear() {

		for(int i=0;i<QueueCapacity;i++) 

		{

			BoundQueue[i]=null;

		}

		rear=-1;

		front=0;

		CurrentQueueSize=0;

		

	}

	void toArray(T[] a) {

		int ArrayIndexValue=0;

		if(front<=rear) {	

			for (int i=front;i<=rear;i++) {

				a[ArrayIndexValue]=(T)BoundQueue[i];

				ArrayIndexValue=ArrayIndexValue+1;

			}

		}

			else {

				for(int i=front;i<QueueCapacity;i++) {

					a[ArrayIndexValue]=(T)BoundQueue[i];

					ArrayIndexValue=ArrayIndexValue+1;

				

				}

				for(int i=0;i<=rear;i++) {

					a[ArrayIndexValue]=(T)BoundQueue[i];

					ArrayIndexValue=ArrayIndexValue+1;

				}

			}

		System.out.println("Elements of Array in Queue order : " );

		for(int m=0; m<a.length; m++) {

			if(a[m]!=null)

			

    		System.out.print(a[m] +" ");

    	}

		

	}

	

	public static void main(String[] args) {

		int qcapacity=20;

		int n = 10;

        if(args.length > 0) {

            n = Integer.parseInt(args[0]);

        }

        BoundedQueue<Integer>  QueueName=new BoundedQueue<Integer>(qcapacity);

        for(int i=1; i<=n; i++) {

            QueueName.offer(Integer.valueOf(i));

        }

        QueueName.QueuePrinting();

        Scanner in =new Scanner(System.in);

        whileloop:

        	while(in.hasNext()) {

        	    int com = in.nextInt();

        	    System.out.println("");

        	    switch(com) {

        	    case 1:  // Clearing the Queue

        	    	QueueName.clear();

        	    	QueueName.QueuePrinting();

    				break;

        		

        	    case 2:  // Checking if the Queue is empty or not

        	    	System.out.println(QueueName.isEmpty());

        	    	break;

        	    	

        	    case 3://Returning Front element without removing it

        	    	System.out.println(QueueName.peek());

    				break;

    				

        	    case 4:// Remove and return the element at the front of the queue

        	    	System.out.println(QueueName.poll());

        	    	QueueName.QueuePrinting();

        	    	break;

        	    	

        	    case 5:// Returning the number of elements in the queue 

        	    	System.out.println(QueueName.size());

    				QueueName.QueuePrinting();

        	    	break;

        	    	

        	    case 6:// Adding  a new element at the rear of the queue

    				int NewElement = in.nextInt();

    				System.out.println(QueueName.offer(NewElement));

    				QueueName.QueuePrinting();

        	    	break;

        	    	

        	    case 7://fill user supplied array with the elements of the queue, in queue order

        	    	Integer[] a = new Integer[QueueCapacity];

        	    	QueueName.toArray(a);

        	    	break;

        	    default:  // Exit loop

        		 break whileloop;

        	    }

        	}

		

	}



}