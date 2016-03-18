import java.io.FileNotFoundException;
import java.io.IOException;

public class test {
	public static void main(String[] arg) throws FileNotFoundException, IOException {
		HashTable table = new HashTable();
		FileWordReader read = new FileWordReader("hamlet.txt");
		
		int i = 0;
		int total = 0; //total of word input
		String word = read.nextWord();
		while(word != null) {
			if(word.equalsIgnoreCase("the")) {
				i++;
			}
			table.incCount(word);
			word = read.nextWord();
			total++;
		}
		int sum = 0; //total of word in table
		int the = 0; //the freq of 'the' in table
		DataCount<String>[] counts = table.getCounts();
		for(DataCount<String> a: counts) {
			sum += a.count;
			if(a.data.equals("the")) {
				the += a.count;
			}
		}
		
		System.out.println("Total word input: " + total);
		System.out.println("Total word in table: " + sum);
		
		System.out.println("Total 'the' input: " + i);
		System.out.println("Total 'the' in table: " + the);
	}
}
