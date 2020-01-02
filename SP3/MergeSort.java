package vxk180003;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MergeSort {

	private static Timer timer = new Timer();
	public static Random random = new Random();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Merge Sort Testing");
		System.out.println("1 for take 1 algo");
		System.out.println("3 for take 3 algo");
		System.out.println("4 for take 4 algo");
		System.out.println("5 for take 5 algo");
		System.out.print("Input:");
		int input = sc.nextInt();
		switch (input) {
		case 1:
			System.out.println("Started take 1...");
			mergeTake1();
			break;
		case 3:
			System.out.println("Started take 3...");
			mergeTake3();
			break;
		case 4:
			System.out.println("Started take 4...");
			mergeTake4();
			break;
		case 5:
			System.out.println("Started take 5...");
			mergeTake5();
			break;
		}
		sc.close();
	}

//--------------------------------- TAKE 5 -----------------------------------------------------------------------

	private static void mergeTake5() {
		int n = 8;
		n *= 1024 * 1024;
		int[] A;
		int[] B;
		while (true) {
			A = new int[n];
			for (int x = 0; x < n; x++)
				A[x] = (int) Math.round(Math.random() * 10000);
			timer.start();
			for (int i = 0; i < 100; i++) {
				Shuffle.shuffle(A);
				B = Arrays.copyOf(A, A.length);
				A = mergeSort5(A, B, 0, A.length - 1);
			}
			timer.end();
			timer.scale(100);
			System.out.println("Records: "+n+" "+timer);
//			for(int st: A) 
//				System.out.print(st+" ");
//			System.out.println();
			n *= 2;
		}

	}

	private static int[] mergeSort5(int[] A, int[] B, int p, int r) {
		int n = A.length;
		for (int i = 1; i < n; i = 2 * i) {
			for (int j = 0; j < n; j = j + 2 * i)
				A = merge3(A, B, j, j + i - 1, j + 2 * i - 1);
			B = Arrays.copyOf(A, A.length);
		}
		return A;
	}

//--------------------------------- TAKE 4 -----------------------------------------------------------------------

	private static void mergeTake4() {
		int n = 8;
		n *= 1000000;
		int[] A;
		int[] B;
		while (true) {
			A = new int[n];
			for (int x = 0; x < n; x++)
				A[x] = (int) Math.round(Math.random() * 10000);
			timer.start();
			for (int i = 0; i < 100; i++) {
				Shuffle.shuffle(A);
				B = Arrays.copyOf(A, A.length);
				A = mergeSort4(A, B, 0, A.length - 1);
			}
			timer.end();
			timer.scale(100);
			System.out.println("Records: "+n+" "+timer);
//			for(int st: A) 
//				System.out.print(st+" ");
//			System.out.println();
			n *= 2;
		}
	}

	private static int[] mergeSort4(int[] A, int[] B, int p, int r) {
		if (r - p < 32) {
			A = insertionSort(A, p, r);
		} else {
			int q = (p + r) / 2;
			mergeSort4(B, A, p, q);
			mergeSort4(B, A, q + 1, r);
			A = merge3(A, B, p, q, r);
		}
		return A;
	}

	private static int[] insertionSort(int[] A, int p, int r) {
		int j;
		for (int i = p + 1; i <= r; i++) {
			j = i - 1;
			int key = A[i];
			while (j >= p && A[j] > key) {
				A[j + 1] = A[j];
				j--;
			}
			A[j + 1] = key;
		}
		return A;
	}

//--------------------------------- TAKE 3 -----------------------------------------------------------------------

	private static void mergeTake3() {
		int n = 8;
		n *= 1000000;
		int[] A;
		int[] B;
		while (true) {
			A = new int[n];
			for (int x = 0; x < n; x++)
				A[x] = (int) Math.round(Math.random() * 10000);
			timer.start();
			for (int i = 0; i < 100; i++) {
				Shuffle.shuffle(A);
				B = Arrays.copyOf(A, A.length);
				A = mergeSort3(A, B, 0, A.length - 1);
			}
			timer.end();
			timer.scale(100);
			System.out.println("Records: "+n+" "+timer);
//			for(int st: A) 
//				System.out.print(st+" ");
//			System.out.println();
			n *= 2;
		}

	}

	private static int[] mergeSort3(int[] A, int[] B, int p, int r) {
		if (p < r) {
			int q = (p + r) / 2;
			mergeSort3(B, A, p, q);
			mergeSort3(B, A, q + 1, r);
			A = merge3(A, B, p, q, r);
		}
		return A;
	}

	private static int[] merge3(int[] A, int[] B, int p, int q, int r) {
		int i = p, j = q + 1, k = p;
		while (i <= q && j <= r) {
			if (B[i] <= B[j])
				A[k++] = B[i++];
			else
				A[k++] = B[j++];
		}
		while (i <= q)
			A[k++] = B[i++];
		while (j <= r)
			A[k++] = B[j++];
		return A;
	}

//--------------------------------- TAKE 1 -----------------------------------------------------------------------	

	private static void mergeTake1() {
		int n = 8;
		n *= 1000000;
		int[] A;
		while (true) {
			A = new int[n];
			for (int x = 0; x < n; x++)
				A[x] = (int) Math.round(Math.random() * 10000);
			timer.start();
			for (int i = 0; i < 100; i++) {
				Shuffle.shuffle(A);
				A = mergeSort1(A, 0, A.length - 1);
			}
			timer.end();
			timer.scale(100);
			System.out.println("Records: "+n+" "+timer);
//			for(int st: A) 
//				System.out.print(st+" ");
//			System.out.println();
			n *= 2;
		}

	}

	private static int[] mergeSort1(int[] A, int p, int r) {
		if (p < r) {
			int q = (p + r) / 2;
			mergeSort1(A, p, q);
			mergeSort1(A, q + 1, r);
			A = merge1(A, p, q, r);
		}
		return A;
	}

	private static int[] merge1(int[] A, int p, int q, int r) {
		int[] B = Arrays.copyOfRange(A, p, r + 1);
		int i = 0, j = q - p + 1, k = p;
		while (i <= q - p && j <= r - p) {
			if (B[i] <= B[j])
				A[k++] = B[i++];
			else
				A[k++] = B[j++];
		}
		while (i <= q - p)
			A[k++] = B[i++];
		while (j <= r - p)
			A[k++] = B[j++];
		return A;
	}

//--------------------------------------------Timer Class----------------------------

	/**
	 * Timer class for roughly calculating running time of programs
	 * 
	 * @author rbk Usage: Timer timer = new Timer(); timer.start(); timer.end();
	 *         System.out.println(timer); // output statistics
	 */

	public static class Timer {
		long startTime, endTime, elapsedTime, memAvailable, memUsed;
		boolean ready;

		public Timer() {
			startTime = System.currentTimeMillis();
			ready = false;
		}

		public void start() {
			startTime = System.currentTimeMillis();
			ready = false;
		}

		public Timer end() {
			endTime = System.currentTimeMillis();
			elapsedTime = endTime - startTime;
			memAvailable = Runtime.getRuntime().totalMemory();
			memUsed = memAvailable - Runtime.getRuntime().freeMemory();
			ready = true;
			return this;
		}

		public long duration() {
			if (!ready) {
				end();
			}
			return elapsedTime;
		}

		public long memory() {
			if (!ready) {
				end();
			}
			return memUsed;
		}

		public void scale(int num) {
			elapsedTime /= num;
		}

		public String toString() {
			if (!ready) {
				end();
			}
			return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed / 1048576) + " MB / "
					+ (memAvailable / 1048576) + " MB.";
		}
	}

	/**
	 * @author rbk : based on algorithm described in a book
	 */

	/* Shuffle the elements of an array arr[from..to] randomly */
	public static class Shuffle {

		public static void shuffle(int[] arr) {
			shuffle(arr, 0, arr.length - 1);
		}

		public static <T> void shuffle(T[] arr) {
			shuffle(arr, 0, arr.length - 1);
		}

		public static void shuffle(int[] arr, int from, int to) {
			int n = to - from + 1;
			for (int i = 1; i < n; i++) {
				int j = random.nextInt(i);
				swap(arr, i + from, j + from);
			}
		}

		public static <T> void shuffle(T[] arr, int from, int to) {
			int n = to - from + 1;
			Random random = new Random();
			for (int i = 1; i < n; i++) {
				int j = random.nextInt(i);
				swap(arr, i + from, j + from);
			}
		}

		static void swap(int[] arr, int x, int y) {
			int tmp = arr[x];
			arr[x] = arr[y];
			arr[y] = tmp;
		}

		static <T> void swap(T[] arr, int x, int y) {
			T tmp = arr[x];
			arr[x] = arr[y];
			arr[y] = tmp;
		}

		public static <T> void printArray(T[] arr, String message) {
			printArray(arr, 0, arr.length - 1, message);
		}

		public static <T> void printArray(T[] arr, int from, int to, String message) {
			System.out.print(message);
			for (int i = from; i <= to; i++) {
				System.out.print(" " + arr[i]);
			}
			System.out.println();
		}
	}

}
