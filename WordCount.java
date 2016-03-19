import java.io.IOException;

/**
 * An executable that counts the words in a files and prints out the counts in
 * descending order. You will need to modify this file.
 */
public class WordCount {

	private static void countWords(String backEndStructure, String analyze, String file) {

		DataCounter<String> counter;
		if(backEndStructure.equals("h")) {
			counter = new HashTable();
		}
		else if(backEndStructure.equals("b")) {
			counter = new BinarySearchTree();
		}
		else {
			counter = new AVLTree();
		}

		try {
			FileWordReader reader = new FileWordReader(file);
			String word = reader.nextWord();

			while (word != null) {
				counter.incCount(word);
				word = reader.nextWord();
			}
		} catch (IOException e) {
			System.err.println("Error processing " + file + e);
			System.exit(1);
		}

		DataCount<String>[] counts = counter.getCounts();
		boolean hashTable = false;
		if(backEndStructure.endsWith("h")){
			hashTable = true;	
		}
		sortByDescendingCount(counts, hashTable); 	//sorting algorithm for hashTable and AVL Tree are different.

		if(analyze.equals("frequency")) {
			for (DataCount<String> c : counts) {
				System.out.println(c.count + " \t" + c.data);
			} 
		}
		else {
			System.out.println("Total unique words: " +counts.length);
		}
	}

	/**
	 * TODO Replace this comment with your own.
	 * 
	 * Sort the count array in descending order of count. If two elements have
	 * the same count, they should be in alphabetical order (for Strings, that
	 * is. In general, use the compareTo method for the DataCount.data field).
	 * 
	 * This code uses insertion sort. You should modify it to use a different
	 * sorting algorithm. NOTE: the current code assumes the array starts in
	 * alphabetical order! You'll need to make your code deal with unsorted
	 * arrays.
	 * 
	 * The generic parameter syntax here is new, but it just defines E as a
	 * generic parameter for this method, and constrains E to be Comparable. You
	 * shouldn't have to change it.
	 * 
	 * @param counts array to be sorted.
	 */
	private static <E extends Comparable<? super E>> void sortByDescendingCount(
			DataCount<E>[] counts, boolean hashTable) {
		//CAN I MODIFY THE ARGUMENT???
		if(hashTable) {
			DataCount<E>[] result = new DataCount[counts.length];
			for(int i = 1; i < result.length; i++) {
				int max = findMax(counts,i); //find max
				swap(counts, i-1, max); //swap previous position with max starting position
			}
		}
		else {
			for (int i = 1; i < counts.length; i++) {
				DataCount<E> x = counts[i];
				int j;
				for (j = i - 1; j >= 0; j--) {
					if (counts[j].count >= x.count) {
						break;
					}
					counts[j + 1] = counts[j];
				}
				counts[j + 1] = x;
			}
		}
	}

	private static <E extends Comparable<? super E>> int findMax(DataCount<E>[] counts, int from) {
		int max = from;
		for(int i = from; i < counts.length; i++) {
			if(counts[i] != null) {
				if(counts[i].count > counts[max].count) {
					max = i;
				}
				else if(counts[i].count == counts[max].count && counts[i].data.compareTo(counts[max].data) < 0) {
					max = i;
				}
			}
		}
		return max;
	}

	private static <E extends Comparable<? super E>> void swap (DataCount<E>[] counts, int a, int b) {
		DataCount<E> temp = counts[a];
		counts[a] = counts[b];
		counts[b] = temp;
	}


	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("Usage: filename of document to analyze");
			System.exit(1);
		}
		if(!args[0].equals("b") && !args[0].equals("a") && !args[0].equals("h")) {
			System.err.println("Usage: incorrect back end structure");
			System.exit(1);
		}
		if(!args[1].equals("frequency") && !args[1].equals("num_unique")) {
			System.err.println("Usage: invalid analysis method");
			System.exit(1);
		}
		countWords(args[0], args[1], args[2]);
	}
}
