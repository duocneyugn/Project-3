/**
 * TODO Replace this comment with your own.
 * 
 * Stub code for an implementation of a DataCounter that uses a hash table as
 * its backing data structure. We included this stub so that it's very clear
 * that HashTable works only with Strings, whereas the DataCounter interface is
 * generic.  You need the String contents to write your hashcode code.
 */
public class HashTable implements DataCounter<String> {
	
	private int size; //unique words
	private DataCount<String>[] table;
	
	public HashTable() {
		this.size = 0;
		this.table = new DataCount[2];
	}
	

    /** {@inheritDoc} */
    public DataCount<String>[] getCounts() {
        DataCount<String>[] result = new DataCount[size];
        int k = 0; 
        for(int i = 0; i < table.length; i++) {
        	DataCount<String> currentPos = table[i];
        	while(currentPos != null) {
        		result[k] = currentPos;
        		currentPos = currentPos.next;
        		k++;
        	}
        }
        return result;
    }

    /** {@inheritDoc} */
    public int getSize() {
        return size;
    }

    /** {@inheritDoc} */
    public void incCount(String data) {
    	
        int hash = hash(data);
        DataCount<String> currentBucket = table[hash];
        if(currentBucket == null) {
        	//handles element with empty bucket
        	currentBucket = new DataCount<String>(data,1);
        	size++;
        }
        else {
        	//handles element with non-empty bucket
        	DataCount<String> currentPos = currentBucket;
        	boolean found = false;
        	while(!found && currentPos != null) {
        		if(currentPos.data.equals(data)) {
        			currentPos.count++;
        			found = true;
        		}
        		else {
        			currentPos = currentPos.next;
        		}
        	}
        	if(!found) {
        		//collision
        		DataCount<String> newData = new DataCount(data, 1);
        		newData.next = currentBucket;
        		currentBucket = newData;
        		size++;
        	}
        }
        table[hash] = currentBucket;
        
        if(size >= table.length / 2) {
        	rehash();
        }
    }

    public void incCount(DataCount<String> dataCount) {
    	int hash = hash(dataCount.data);
    	DataCount<String> newData = new DataCount(dataCount.data, dataCount.count);
    	
    	DataCount<String> bucket = table[hash];
    	if(bucket == null) {
    		bucket = newData;
    	}
    	else {
    		newData.next = bucket;
    		bucket = newData;
    	}
    	table[hash] = bucket;
    	size++;
    }
    
    private void rehash() {
    	DataCount<String>[] oldTable = table;
    	table = new DataCount[nextPrime(oldTable.length * 2)];
    	size = 0;
    	for(int i = 0; i < oldTable.length; i++) {
    		DataCount<String> currentPos = oldTable[i];
    		while(currentPos != null) {
    			incCount(currentPos);
    			currentPos = currentPos.next;
    		}
    	}
    }
    
    public int hash(String key)
    {
        int hashVal = 0;

        for( int i = 0; i < key.length( ); i++ )
            hashVal = 37 * hashVal + key.charAt( i );

        hashVal %= table.length;
        if( hashVal < 0 )
            hashVal += table.length;

        return hashVal;
    }
    
	private static int nextPrime(int n) {
		int k = n; //k is the next prime number
		if(k % 2 == 0) //checks if it is an even number
			k++; //adds 1 to make it even
		while(!isPrime(k)) {
			k+=2; //check for the next prime number less than n. 
		}
		return k; //this returns the next prime number 
	}

	private static boolean isPrime(int n) {
		if(n == 2 || n == 3)
			return true; 
		if (n == 1 || n % 2 == 0)
			return false;
		for(int i = 3; i * i <= n; i += 2)
			if(n % i == 0) //checks if smaller odds number is a factor of n.
				return false;
		return true; //if n is prime.
	}

	public DataCount<String> search(String data) {
		int hash = hash(data);
		DataCount<String> currentBucket = table[hash];
		if(currentBucket == null) {
			return null;
		}
		else {
			DataCount<String> currentPos = currentBucket;
			boolean found = false;
			while(!found && currentPos != null) {
				if(currentPos.data.equals(data)) {
					found = true;
				}
				else {
					currentPos = currentPos.next;
				}
			}
			return currentPos;
		}
	}
}
