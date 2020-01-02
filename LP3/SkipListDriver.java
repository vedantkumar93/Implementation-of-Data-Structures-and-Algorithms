package vjs180000;



import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

//Driver program for skip list implementation.

public class SkipListDriver {
    public static void main(String[] args) throws FileNotFoundException {
	Scanner sc;
	if (args.length > 0) {
		// file path to be changed as per the system 
	    File file = new File("/Users/vishal/eclipse/vjs180000/src/vjs180000/sk-t11.txt");
	    sc = new Scanner(file);
	} else {
	    sc = new Scanner(System.in);
	}
	String operation = "";
	long operand = 0;
	int modValue = 999983;
	long result = 0;
	Long returnValue = null;
	SkipList<Long> skipList = new SkipList<>();
	// Initialize the timer
	Timer timer = new Timer();

	while (!((operation = sc.next()).equals("End"))) {
	    switch (operation) {
	    case "Add": {
	    	// Add a new element x to the list. If x already exists in the
	    	 //skip list, replace it and return false. Otherwise, insert x into the
	    	 //skip list and return true.
		operand = sc.nextLong();
		if(skipList.add(operand)) {
		    result = (result + 1) % modValue;
		}
		break;
	    }
	    case "Ceiling": {
	    	//Find smallest element that is greater or equal to x.
		operand = sc.nextLong();
		returnValue = skipList.ceiling(operand);
		if (returnValue != null) {
		    result = (result + returnValue) % modValue;
		}
		break;
	    }
	    case "First": {
	    	 //Return first element of list.
		returnValue = skipList.first();
		if (returnValue != null) {
		    result = (result + returnValue) % modValue;
		}
		break;
	    }
	    case "Get": {
	    	 //Return element at index n of list. First element is at index
	    	 //0. Call either getLinear or getLog.
		int intOperand = sc.nextInt();
		returnValue = skipList.get(intOperand);
		if (returnValue != null) {
		    result = (result + returnValue) % modValue;
		}
		break;
	    }
	    case "Last": {
	    	// Return last element of list.
		returnValue = skipList.last();
		if (returnValue != null) {
		    result = (result + returnValue) % modValue;
		}
		break;
	    }
	    case "Floor": {
	    	// Find largest element that is less than or equal to x.
		operand = sc.nextLong();
		returnValue = skipList.floor(operand);
		if (returnValue != null) {
		    result = (result + returnValue) % modValue;
		}
		break;
	    }
	    case "Remove": {
	    	//: Remove x from the list. If successful, removed element is
	    	//returned. Otherwise, return null.
		operand = sc.nextLong();
		if (skipList.remove(operand) != null) {
		    result = (result + 1) % modValue;
		}
		break;
	    }
	    case "Contains":{
	    	// Does list contain x?
		operand = sc.nextLong();
		if (skipList.contains(operand)) {
		    result = (result + 1) % modValue;
		}
		break;
	    }
		
	    }
	}

	// End Time
	timer.end();

	System.out.println(result);
	System.out.println(timer);
    }
}
