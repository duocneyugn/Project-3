
public class Correlator {
	public static void main(String[] arg) {
		if(arg[0] == "-b") {
			//Use an Unbalanced BST in the backend
		}
		else if(arg[0] == "-a") {
			//Use an AVL Tree in the backend
		}
		else if(arg[0] == "-h") {
			//Use a Hashtable in the backend
			
		}
		else {
			//flag data
		}
		
		String a1 = arg[1];
		String a2 = arg[2];
		
		String a1End = a1.substring(a1.length()-4, a1.length());
		String a2End = a2.substring(a2.length()-4, a2.length());
		if(!a1End.equals(".txt") && !a2End.equals(".txt")) {
			//flag. 
		}
	}

}
