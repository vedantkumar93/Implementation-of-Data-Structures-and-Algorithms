package vjs180000;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;

public class RobinHoodDriver {
	
	
	static RobinHood rh = new RobinHood();
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc;
		File file = new File("/Users/vishal/eclipse/vjs180000/src/vjs180000/sk-t13.txt");
		sc = new Scanner(file);
		String operation;
		int operand;
		long modValue = 1000000009;
		long result = 0;
		
		System.out.println("RobinHood Hashing Performance:");
		
		
		Timer timer = new Timer();
		
		while (!((operation = sc.next()).equals("End"))) {
			
			switch (operation) {
				case "Add": {
					operand = sc.nextInt();
					
					if (rh.add(operand))
	                {
	                    result = (result + 1) % modValue;
	                }
					break;
				}
				case "Remove":{
	                operand = sc.nextInt();
	                if (rh.remove(operand))
	                {
	                    result = (result + 1) % modValue;
	                }
	                break;
				}
				case "Contains":{
					operand = sc.nextInt();
						if(rh.contains(operand))
							{
						result = (result + 1) % modValue;
					}
					break;
				}
				default:
				break;
			}
		}
		System.out.println("Robin Hood result: " + result);
		System.out.println("Robin Hood size: " + rh.size());
			
		
		System.out.println(timer.end());
		File file1 = new File("/Users/vishal/eclipse/vjs180000/src/vjs180000/sk-t13.txt");
		sc = new Scanner(file1);
        operation = "";
        operand = 0;
        result = 0;
        modValue = 999983;
        float loadfactor=(float) 0.5;
        	
        System.out.println("Java Hashset Performance:");
        timer = new Timer();
        HashSet<Long> hs = new HashSet<Long>(10000000,loadfactor);
        while (!((operation = sc.next()).equals("End")))
        {
            switch (operation)
            {
            case "Add":
                operand = sc.nextInt();
                if (hs.add((long) operand))
                {
                    result = (result + 1) % modValue;
                }
                break;
            case "Remove":
                operand = sc.nextInt();
                if (hs.remove(operand))
                {
                    result = (result + 1) % modValue;
                }
                break;
            case "Contains":
                operand = sc.nextInt();
                if (hs.contains(operand))
                {
                    result = (result + 1) % modValue;
                }
                break;
            }
        }

        // End Time
        System.out.println("Hash Set result: " + result);
		System.out.println("Hash Set size: " +  hs.size());
			
		
		System.out.println(timer.end());
		 
	}

}