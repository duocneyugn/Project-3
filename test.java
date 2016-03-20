import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

/**
 * More benchmarking test 
 * Data Structure and Algorithm Analysis
 * Duoc Nguyen and Patrick Leung 
 *
 */
public class test {
	public static void main(String[] arg) throws FileNotFoundException, IOException {
		HashTable table = new HashTable();
		AVLTree aTree = new AVLTree();
		BinarySearchTree BST = new BinarySearchTree();
		long start = 0;
		long stop = 0;

		String[] books = {"A-Garden-Diary.txt", "Der-Golem.txt", "Der-Tabak.txt", "Dumbwaiter.txt", "East-In-The-Morning.txt", "hamlet.txt", 
				"Narrative-and-Critical-History-of-America.txt", "Self-Control-Its-Kingship-and-Majesty.txt", "The-Donkey-the-Elephant-and-the-Goat.txt", 
				"The-East-India-Vade-Mecum.txt", "The-Love-of-Monsieur.txt", "the-new-atlantis.txt", "King-James-Bible.txt"};
		for(int k = 0; k < books.length; k++) {
			FileWordReader read1 = new FileWordReader(books[k]);
			FileWordReader read2 = new FileWordReader(books[k]);
			FileWordReader read3 = new FileWordReader(books[k]);
			System.out.println("USING: " +books[k]);
			start = System.currentTimeMillis();
			String word1 = read1.nextWord();
			while(word1 != null) {
				BST.incCount(word1);
				word1 = read1.nextWord();
			}
			stop = System.currentTimeMillis();
			System.out.println("insert all words from " + books[k] + " in BST: " + (stop - start) + " Millis");

			start = System.currentTimeMillis();
			String word2 = read2.nextWord();
			while(word2 != null) {
				aTree.incCount(word2);
				word2 = read2.nextWord();
			}
			stop = System.currentTimeMillis();
			System.out.println("insert all words from " + books[k] + " in AVLTree: " + (stop - start) + " Millis");

			start = System.currentTimeMillis();
			String word3 = read3.nextWord();
			while(word3 != null) {
				table.incCount(word3);
				word3 = read3.nextWord();
			}
			stop = System.currentTimeMillis();
			System.out.println("insert all words in HashTable: " + (stop - start) + " Millis");

			start = System.currentTimeMillis();
			DataCount<String>[] counts1 = BST.getCounts();
			stop = System.currentTimeMillis();
			System.out.println("get all word counts in BST: " + (stop - start) + " Millis");

			start = System.currentTimeMillis();
			DataCount<String>[] counts2 = aTree.getCounts();
			stop = System.currentTimeMillis();
			System.out.println("get all word counts in AVLTree: " + (stop - start) + " Millis");

			start = System.currentTimeMillis();
			DataCount<String>[] counts3 = table.getCounts();
			stop = System.currentTimeMillis();
			System.out.println("get all word counts in HashTable: " + (stop - start) + " Millis");

			start = System.currentTimeMillis();
			for(int i = 0; i < 1000000; i++) {
				BST.search("asdfasf");
			}
			stop = System.currentTimeMillis();
			System.out.println("search a random phrase 1000000 times in BST: " + (stop - start) + " Millis");

			start = System.currentTimeMillis();
			for(int i = 0; i < 1000000; i++) {
				aTree.search("asdfasf");
			}
			stop = System.currentTimeMillis();
			System.out.println("search a random phrase 1000000 times in AVLTree: " + (stop - start) + " Millis");

			start = System.currentTimeMillis();
			for(int i = 0; i < 1000000; i++) {
				table.search("asdfasf");
			}
			stop = System.currentTimeMillis();
			System.out.println("search a random phrase 1000000 times in HashTable: " + (stop - start) + " Millis");

			start = System.currentTimeMillis();
			for(DataCount<String> a : counts1) {

				String s = a.data;
				table.search(s);
			}
			stop = System.currentTimeMillis();
			System.out.println("search all the existing words in the HashTable " + (stop - start) + " Millis");

			start = System.currentTimeMillis();
			for(DataCount<String> a : counts2) {
				String s = a.data;
				BST.search(s);
			}
			stop = System.currentTimeMillis();
			System.out.println("search all the existing words in the BST " + (stop - start) + " Millis");

			start = System.currentTimeMillis();
			for(DataCount<String> a : counts3) {
				String s = a.data;
				aTree.search(s);
			}
			stop = System.currentTimeMillis();
			System.out.println("search all the existing words in the AVLTree " + (stop - start) + " Millis");

		}
	}
}
