import java.io.IOException;

/**
 * An executable that counts the words in a files and prints out the counts in
 * descending order. You will need to modify this file.
 */
public class WordCount {

	private static void countWords(String file) {
		DataCounter<String> counter = new HashTable();
		//sdfadf
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
		//System.out.println(counter.getSize());
		//System.out.println(counts.length);
		sortByDescendingCount(counts); //CAN I MODIFY THIS???

		int total = 0;
		for (DataCount<String> c : counts) {
			System.out.println(c.count + " \t" + c.data);
			total += c.count;
		}
		System.out.println("total word: " + total);
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
		//CAN I MODIFY THE ARGUMENT???
		DataCount<E>[] result = new DataCount[counts.length];
		for(int i = 1; i < result.length; i++) {
			int max = findMax(counts,i); //find max
			swap(counts, i-1, max); //swap previous position with max starting position
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
		if (args.length != 1) {
			System.err.println("Usage: filename of document to analyze");
			System.exit(1);
		}
		countWords(args[0]);
	}
}
