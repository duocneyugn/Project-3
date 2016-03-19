import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

public class test {
	public static void main(String[] arg) throws FileNotFoundException, IOException {
		HashTable table = new HashTable();
		AVLTree aTree = new AVLTree();
		BinarySearchTree BST = new BinarySearchTree();
		long start = 0;
		long stop = 0;
		FileWordReader read1 = new FileWordReader("hamlet.txt");
		FileWordReader read2 = new FileWordReader("hamlet.txt");
		FileWordReader read3 = new FileWordReader("hamlet.txt");
		
		start = System.currentTimeMillis();
		String word1 = read1.nextWord();
		while(word1 != null) {
			BST.incCount(word1);
			word1 = read1.nextWord();
		}
		stop = System.currentTimeMillis();
		System.out.println("Total time duration to insert all words from Hamlet in BST: " + (stop - start) + " Millis");
		
		start = System.currentTimeMillis();
		String word2 = read2.nextWord();
		while(word2 != null) {
			aTree.incCount(word2);
			word2 = read2.nextWord();
		}
		stop = System.currentTimeMillis();
		System.out.println("Total time duration to insert all words from Hamlet in AVLTree: " + (stop - start) + " Millis");
		
		start = System.currentTimeMillis();
		String word3 = read3.nextWord();
		while(word3 != null) {
			table.incCount(word3);
			word3 = read3.nextWord();
		}
		stop = System.currentTimeMillis();
		System.out.println("Total time duration to insert all words from Hamlet in HashTable: " + (stop - start) + " Millis");
		
		start = System.currentTimeMillis();
		DataCount<String>[] counts1 = BST.getCounts();
		stop = System.currentTimeMillis();
		System.out.println("Total time duration to get all word counts from Hamlet in BST: " + (stop - start) + " Millis");
		
		start = System.currentTimeMillis();
		DataCount<String>[] counts2 = aTree.getCounts();
		stop = System.currentTimeMillis();
		System.out.println("Total time duration to get all word counts from Hamlet in AVLTree: " + (stop - start) + " Millis");
		
		start = System.currentTimeMillis();
		DataCount<String>[] counts3 = table.getCounts();
		stop = System.currentTimeMillis();
		System.out.println("Total time duration to get all word counts from Hamlet in HashTable: " + (stop - start) + " Millis");
		
		start = System.currentTimeMillis();
		for(int i = 0; i < 1000000; i++) {
			BST.search("asdfasf");
		}
		stop = System.currentTimeMillis();
		System.out.println("Total time duration to search a random phrase 1000000 times in BST: " + (stop - start) + " Millis");
		
		start = System.currentTimeMillis();
		for(int i = 0; i < 1000000; i++) {
			aTree.search("asdfasf");
		}
		stop = System.currentTimeMillis();
		System.out.println("Total time duration to search a random phrase 1000000 times in AVLTree: " + (stop - start) + " Millis");
		
		start = System.currentTimeMillis();
		for(int i = 0; i < 1000000; i++) {
			table.search("asdfasf");
		}
		stop = System.currentTimeMillis();
		System.out.println("Total time duration to search a random phrase 1000000 times in HashTable: " + (stop - start) + " Millis");
		
		start = System.currentTimeMillis();
		for(DataCount<String> a : counts1) {
			
			String s = a.data;
			table.search(s);
		}
		stop = System.currentTimeMillis();
		System.out.println("Total time duration to search all the existing words in the HashTable " + (stop - start) + " Millis");
		
		start = System.currentTimeMillis();
		for(DataCount<String> a : counts2) {
			String s = a.data;
			BST.search(s);
		}
		stop = System.currentTimeMillis();
		System.out.println("Total time duration to search all the existing words in the BST " + (stop - start) + " Millis");

		start = System.currentTimeMillis();
		for(DataCount<String> a : counts3) {
			String s = a.data;
			aTree.search(s);
		}
		stop = System.currentTimeMillis();
		System.out.println("Total time duration to search all the existing words in the AVLTree " + (stop - start) + " Millis");

		
		//Correlator test
		
		
	}
}
