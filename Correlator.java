import java.io.IOException;

/**
 * An executable that counts the words in a files and prints out the counts in
 * descending order. You will need to modify this file.
 */
public class Correlator {

	private static void countWords(String backEndStructure, String file, String file2) {
		DataCounter<String> counter1;
		DataCounter<String> counter2;

		if (backEndStructure.equals("b")) {
			counter1 = new BinarySearchTree<String>();
			counter2 = new BinarySearchTree<String>();
		} else if (backEndStructure.equals("a")) {
			counter1 = new AVLTree<String>();
			counter2 = new AVLTree<String>();
		} else {
			counter1 = new HashTable();
			counter2 = new HashTable();
		}

		try {
			FileWordReader reader = new FileWordReader(file);
			String word = reader.nextWord();
			while (word != null) {
				counter1.incCount(word);
				word = reader.nextWord();
			}
		} catch (IOException e) {
			System.err.println("Error processing " + file + e);
			System.exit(1);
		}
		try {
			FileWordReader reader = new FileWordReader(file2);
			String word = reader.nextWord();
			while (word != null) {
				counter2.incCount(word);
				word = reader.nextWord();
			}
		} catch (IOException e) {
			System.err.println("Error processing " + file2 + e);
			System.exit(1);
		}

		int sumDifferences = 0;
		DataCount<String>[] counts1 = counter1.getCounts();
		sortByDescendingCount(counts1);
		removeOutliers(counts1); //removes the insignificant words
		//prints difference of the two texts
		int sumDiff = sumDiff(counts1, counter2, backEndStructure);
		System.out.println("The sum difference is: " + sumDiff); //prints the sum diff
	}

	private static void removeOutliers(DataCount<String>[] counts) {
		//removes the outliers 
		int total = 0;
		for(DataCount<String> d: counts) {
			if(d != null) {
				total += d.count;
			}
		}

		double upperBound = total * 0.01; //higher than 1%
		double lowerBound = total * 0.0001; //lower than 0.01%

		for(int i = 0; i < counts.length; i++) {
			if(counts[i] != null) {
				if(counts[i].count > upperBound || counts[i].count < lowerBound) {
					counts[i] = null;
				}
			}
		}

	}

	private static int sumDiff(DataCount<String>[] a, DataCounter<String> b, String backEndStructure) {
		int sumDiff = 0;
		for(DataCount<String> x: a) {
			if(x != null) {
				String aWord = x.data;
				int aWordCount = x.count;
				DataCount<String> y = b.search(aWord);
				if(y != null) {
					int bWordCount = y.count;
					int diff = Math.abs(aWordCount - bWordCount);
					sumDiff += diff;
				}
			}
		}
		return sumDiff;
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
			DataCount<E>[] counts) {
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

	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("Usage: java Correlator [b | a | h] <filename1> <filename2>");
			System.exit(1);
		}
		if(!args[0].equals("b") && !args[0].equals("a") && !args[0].equals("h")) {
			System.err.println("Usage: incorrect back end structure");
			System.exit(1);
		}
		long start = 0;
		long stop = 0;
		
		start = System.currentTimeMillis();
		countWords(args[0], args[1], args[2]);
		stop = System.currentTimeMillis();
		
		System.out.println("Total time duration to complete correlator with AVLTree for " + args[1] + " and " + args[2] + " : " + (stop - start) + " Millis");
	}
}
