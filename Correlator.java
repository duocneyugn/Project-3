import java.io.IOException;

/**
 * Data Structure and Algorithm Analysis
 * Duoc Nguyen and Patrick Leung 
 * An executable that counts the words in a files and prints out the counts in
 * descending order. You will need to modify this file.
 */
public class Correlator {

	/**
	 * Calculate the sum difference in frequency of word use in 2 files.
	 * @param backEndStructure BST, AVLTree, or HashTable
	 * @param file1	first file to analyze
	 * @param file2 second file to analyze
	 */
	private static void wordCorrelator(String backEndStructure, String file1, String file2) {
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
			FileWordReader reader = new FileWordReader(file1);
			String word = reader.nextWord();
			while (word != null) {
				counter1.incCount(word);
				word = reader.nextWord();
			}
		} catch (IOException e) {
			System.err.println("Error processing " + file1 + e);
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
		//sortByDescendingCount(counts1);
		removeOutliers(counts1); //removes the insignificant words
		//prints difference of the two texts
		int sumDiff = sumDiff(counts1, counter2, backEndStructure);
		//add "//" in front of the below System.out.println() command for benchmarks
		System.out.println("The sum difference is: " + sumDiff); //prints the sum diff
	}

	/**
	 * Remove words with normalized frequencies above 0.01 (1%) and below 0.0001 (0.01%)
	 * This also known as removing outliers that insignificant to our analysis.
	 * @param counts the array to remove outliers
	 */
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

	/**
	 * Gets the sum difference of frequency words that are in both files
	 * This algorithm's runtime depends on the data structure used to contain words in file 2
	 * If data structure is a hash table then algorithm is O(n) because search is O(1)
	 * If data structure is a tree then algorithm is O(nlog n) because search is O(log n)
	 * @param a	Array of DataCount containing word and count in file 1
	 * @param b	The tree or table that is used to contain words in file 2
	 * @param backEndStructure	The data structure used
	 * @return the sum difference
	 */
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

	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("Usage: java Correlator [b | a | h] <filename1> <filename2>");
			System.exit(1);
		}
		if(!args[0].equals("b") && !args[0].equals("a") && !args[0].equals("h")) {
			System.err.println("Usage: incorrect back end structure");
			System.exit(1);
		}
		if(!args[2].substring(args[2].length()-4, args[2].length()).equals(".txt") && 
				!args[1].substring(args[1].length()-4, args[1].length()).equals(".txt")) {
			System.err.println("Usage: invalid text file");
			System.exit(1);
		}
		wordCorrelator(args[0], args[1], args[2]);	//add "//" for at the beginning of line to benchmark
		
		/**
		 * Below lines of codes were used for benchmarking
		long start = 0;
		long stop = 0;

		String[] books = {"A-Garden-Diary.txt", "Der-Golem.txt", "Der-Tabak.txt", "Dumbwaiter.txt", "East-In-The-Morning.txt", "hamlet.txt", 
				"Narrative-and-Critical-History-of-America.txt", "Self-Control-Its-Kingship-and-Majesty.txt", "The-Donkey-the-Elephant-and-the-Goat.txt", 
				"The-East-India-Vade-Mecum.txt", "The-Love-of-Monsieur.txt", "the-new-atlantis.txt", "King-James-Bible.txt"};

		for(int i = 1; i < books.length - 1 ; i += 2) {
			start = System.currentTimeMillis();
			wordCorrelator(args[0], books[i-1], books[i]);
			stop = System.currentTimeMillis();

			System.out.println(books[i-1] + " and " + books[i] + " : " + (stop - start) + " Millis");
		}
		*/
	}
}
