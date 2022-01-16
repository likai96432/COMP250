import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class MyHashTable<K, V> implements Iterable<HashPair<K, V>> {
	// num of entries to the table
	private int numEntries;
	// num of buckets
	private int numBuckets;
	// load factor needed to check for rehashing
	private static final double MAX_LOAD_FACTOR = 0.75;
	// ArrayList of buckets. Each bucket is a LinkedList of HashPair
	private ArrayList<LinkedList<HashPair<K, V>>> buckets;

	// constructor
	public MyHashTable(int initialCapacity) {
		this.numEntries = 0;
		this.numBuckets = initialCapacity;
		buckets = new ArrayList<LinkedList<HashPair<K, V>>>(numBuckets);
		for (int i = 0; i < initialCapacity; i++) {
			buckets.add(new LinkedList<HashPair<K, V>>());
		}
		if (initialCapacity == 0) {
			buckets.add(new LinkedList<HashPair<K, V>>());
		}
	}

	public int size() {
		return this.numEntries;
	}

	public boolean isEmpty() {
		return this.numEntries == 0;
	}

	public int numBuckets() {
		return this.numBuckets;
	}

	/**
	 * Returns the buckets variable. Useful for testing purposes.
	 */
	public ArrayList<LinkedList<HashPair<K, V>>> getBuckets() {
		return this.buckets;
	}

	/**
	 * Given a key, return the bucket position for the key.
	 */
	public int hashFunction(K key) {
		int hashValue = Math.abs(key.hashCode()) % this.numBuckets;
		return hashValue;
	}

	/**
	 * Takes a key and a value as input and adds the corresponding HashPair to this
	 * HashTable. Expected average run time O(1)
	 */
	public V put(K key, V value) {
		int hashcode = 0;
		if (numBuckets != 0) {
			hashcode = hashFunction(key);
		}
		

		//V result = null;


		LinkedList<HashPair<K, V>> List = buckets.get(hashcode);
		for (int i = 0; i < List.size(); i++) {
			if (List.get(i).getKey().equals(key)) {

				V result = List.get(i).getValue();
				List.get(i).setValue(value);
				return result;

			}

			

		}
		List.add(new HashPair<K, V>(key, value));
		this.numEntries = this.numEntries + 1;
		double loadfactor = 0;
		if (numBuckets != 0)
			loadfactor = (double) numEntries / numBuckets;
		if (loadfactor > MAX_LOAD_FACTOR) {
			this.rehash();

		}

		return null;

		// ADD YOUR CODE ABOVE HERE
	}

	/**
	 * Get the value corresponding to key. Expected average runtime O(1)
	 */

	public V get(K key) {
		int hashcode =0;
		if (numBuckets != 0) {
			hashcode = hashFunction(key);
		}
		
		
		LinkedList<HashPair<K, V>> List = buckets.get(hashcode);
		for (int i = 0; i < List.size(); i++) {
			if (List.get(i).getKey().equals(key)) {

				return List.get(i).getValue();

			}

		}

		return null;

	}

	/**
	 * Remove the HashPair corresponding to key . Expected average runtime O(1)
	 */
	public V remove(K key) {
		V result=null;
		if (key == null)
			return null;
		int hashcode = hashFunction(key);
		LinkedList<HashPair<K, V>> List = buckets.get(hashcode);
		for (int i = 0; i < List.size(); i++) {
			if (List.get(i).getKey().equals(key)) {
				result=List.get(i).getValue();
				List.remove(i);
				numEntries-=1;
				return result;

			}

		}

		return null;

	}

	/**
	 * Method to double the size of the hashtable if load factor increases beyond
	 * MAX_LOAD_FACTOR. Made public for ease of testing. Expected average runtime is
	 * O(m), where m is the number of buckets
	 */
	public void rehash() {
		numBuckets = numBuckets * 2;
		MyHashTable<K, V> newtable = new MyHashTable<K, V>(numBuckets);
		for (int i = 0; i < this.buckets.size(); i++) {
			for (int j = 0; j < this.buckets.get(i).size(); j++) {
				newtable.put(this.buckets.get(i).get(j).getKey(), this.buckets.get(i).get(j).getValue());

			}
		}
		this.buckets = newtable.buckets;
	}

	/**
	 * Return a list of all the keys present in this hashtable. Expected average
	 * runtime is O(m), where m is the number of buckets
	 */

	public ArrayList<K> keys() {
		// ADD YOUR CODE BELOW HERE
		ArrayList<K> result = new ArrayList<K>(numEntries);
		for (int i = 0; i < this.buckets.size(); i++) {
			for (int j = 0; j < this.buckets.get(i).size(); j++) {
				result.add(this.buckets.get(i).get(j).getKey());

			}
		}

		return result;

		// ADD YOUR CODE ABOVE HERE
	}

	/**
	 * Returns an ArrayList of unique values present in this hashtable. Expected
	 * average runtime is O(m) where m is the number of buckets
	 */
	public ArrayList<V> values() {
		 MyHashTable <V,V> valuetable=new MyHashTable<V,V>(numBuckets);
		 
		 
		ArrayList<V> result = new ArrayList<V>();
		
		for(int i=0;i<this.buckets.size();i++) {
			for(int j=0;j<this.buckets.get(i).size();j++) {
				valuetable.put(this.buckets.get(i).get(j).getValue(),this.buckets.get(i).get(j).getValue());
			}
		}
		for(int i=0;i<valuetable.numBuckets;i++) {
			for(int j=0;j<valuetable.buckets.get(i).size();j++) {
				result.add(valuetable.buckets.get(i).get(j).getKey());
			}
		}
		
		
		

		return result;

		
	}
	
	public static <K, V extends Comparable<V>> ArrayList<K> slowSort(MyHashTable<K, V> results) {
		ArrayList<K> sortedResults = new ArrayList<>();
		for (HashPair<K, V> entry : results) {
			V element = entry.getValue();
			K toAdd = entry.getKey();
			int i = sortedResults.size() - 1;
			V toCompare = null;
			while (i >= 0) {
				toCompare = results.get(sortedResults.get(i));
				if (element.compareTo(toCompare) <= 0)
					break;
				i--;
			}
			sortedResults.add(i + 1, toAdd);
		}
		return sortedResults;
	}

	/**
	 * This method takes as input an object of type MyHashTable with values that are
	 * Comparable. It returns an ArrayList containing all the keys from the map,
	 * ordered in descending order based on the values they mapped to.
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number of
	 * pairs in the map.
	 */

  
    public static <K, V extends Comparable<V>> ArrayList<K> fastSort(MyHashTable<K, V> results) {
    	ArrayList<K> fastsortresult = new ArrayList<>();
    	 fastsortresult=results.keys();
    	   if( fastsortresult.size()==0) {
    		   return fastsortresult;
    	   }
    	  results.QuickSort(fastsortresult, 0, fastsortresult.size() - 1,results);
    	  return fastsortresult;
    	 
    	  
		
        
    }
private <K, V extends Comparable<V>>  void QuickSort(ArrayList<K> keyarray, int start, int end,MyHashTable<K, V> results) {
	
	int idx = sort(keyarray, start, end,results); 
	if (start < idx - 1) { QuickSort(keyarray, start, idx - 1,results); 
	} 
	if (end > idx) { 
		QuickSort(keyarray, idx, end,results);
		} 
		
	}

private <K, V extends Comparable<V>> int sort(ArrayList<K> array,int left, int right,MyHashTable<K, V> results) {
    	V pivot = results.get(array.get((int) (left+(right-left)*0.5)));
    	while (left <= right) {
    		while (results.get(array.get(left)).compareTo(pivot)>0) { left++; }  
    		while (results.get(array.get(right)).compareTo(pivot)<0) { right--; }  
    		if (left <= right) { 
    		K tmp = array.get(left); 
    		array.set(left, array.get(right)); 
    		array.set(right, tmp);
    		left++; right--; }
    		} 
    	return left;
	
}

	@Override
	public MyHashIterator iterator() {
		return new MyHashIterator();
	}

	private class MyHashIterator implements Iterator<HashPair<K, V>> {
		ArrayList<HashPair<K,V>> result=new ArrayList<>();
        int current=0;
		private MyHashIterator() {
			for (int i = 0; i < buckets.size(); i++) {
				for (int j = 0; j < buckets.get(i).size(); j++) {
					result.add(buckets.get(i).get(j));
					
				}
			}

		}

		@Override
		/**
		 * Expected average runtime is O(1)
		 */
		public boolean hasNext() {
			
            if(current>=result.size()) {
			return false;}
            else return true;

			// ADD YOUR CODE ABOVE HERE
		}

		@Override
		/**
		 * Expected average runtime is O(1)
		 */
		public HashPair<K, V> next() {
			HashPair<K,V> konodioda=result.get(current);
		
			 konodioda=result.get(current);
		current++;
			

			return konodioda;

			
		

	}}
}
