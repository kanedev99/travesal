package edu.uwm.cs351;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import edu.uwm.cs.util.Primes;

public class TravestyMap {

	private static class Entry extends ArrayList<String> {
		/**
		 * Keep Eclipse Happy
		 */
		private static final long serialVersionUID = 1L;
		Entry next;

		String[] key;
		Entry(String... key) {
			super();
			if (key != null) {
				this.key = key.clone(); // keep own copy
			}
		}

		@Override
		public String toString() {
			return "Entry(" + Arrays.toString(key) + ")";
		}
	}
	
	public static final int INITIAL_CAPACITY = 7;
	private static final double CROWDED = 0.75;

	private Entry[] table;
	private int numEntries; // number of entries
	// Don't add any more fields!

	private static Consumer<String> reporter = (s) -> System.out.println("Invariant error: "+ s);
	
	/**
	 * Used to report an error found when checking the invariant.
	 * By providing a string, this will help debugging the class if the invariant should fail.
	 * @param error string to print to report the exact error found
	 * @return false always
	 */
	private static boolean report(String error) {
		reporter.accept(error);
		return false;
	}

	private boolean wellFormed() {
		// DESIGN: we have non-null table with a prime length.
		// in which each Entry is in an endogenous non-cyclic list starting at table[i]
		// where i is the modulus of the entry's key hash with respects to the length of the table.
		// We rehash the table if the number of numEntries /length >= CROWDED,
		// No two entries in the table can have the same key.		
		// Entries with null keys are not present in the table.
		// The "numEntries" field represents the number of entries.
		// There are no other fields.
		// TODO: Implement object invariant described in DESIGN above.
		// Our solution uses the "tortoise and hare" check from HW #4 to avoid cycles.
		// You can also use the technique from countNodes in HW #8
		// (count the nodes, and if you have more than alleged, maybe there's a cycle).
		return true;
	}

	/**
	 * Hash an array to a table index using modulus arithmetic.
	 * @param u array to hash, must not be null and must not contain null
	 * @return modulus hash.
	 */
	private int hash(String[] u) {
		return -1; // TODO
	}

	/**
	 * Create a new array that holds all the existing
	 * entries, added in the order they appear in the current table.
	 * This method should not create any new entry objects.
	 * Since add puts things at the front of the chain, you
	 * must use a different algorithm that preserves the order. 
	 * The size of the new array is a prime size larger than 
	 * twice the current array's size
	 */
	private void rehash() {
		// TODO rehash the table
		// Don't assert the invariant before the rehash (the array is crowded).		
		// Take care to handle entry 'next' field when moving to new table
		assert wellFormed() : "after rehash, invariant broken";
	}

	/// Public methods
	public TravestyMap() {
		// TODO:initialize the hashtable
		assert wellFormed() : "invariant false at end of constructor";
	}
	
	private TravestyMap(boolean ignored) {} // do not change

	/**
	 * Return the number of entries in this table,
	 * mappings from arrays of strings to lists of strings.
	 * @return
	 */
	public int size() {
		assert wellFormed() : "invariant broken at start of size()";
		return 0; // TODO: easy
	}

	/**
	 * Return the list with the associated key.  If there
	 * is no entry for the key, return null.
	 * @param key array of strings used as a key, must not be null or include null
	 * @return array list of strings associated with the key, may be null
	 */
	public List<String> find(String[] key) {
		assert wellFormed(): "invariant broken at start of find(" + Arrays.toString(key) + ")";
		// TODO
		return null;
	}

	/** 
	 * Return the array list with the associate key.  If there is none,
	 * create a new entry in the table with an empty list and return that new empty list.
	 * @param key array of strings used as a key, must not be null, or include null
	 * @return array list of strings associated with the key, never null.
	 */
	public List<String> get(String[] key) {
		assert wellFormed(): "invariant broken at start of get(" + Arrays.toString(key) + ")";
		// TODO (don't duplicate code)
		assert wellFormed() : "invariant broken after get(" + Arrays.toString(key) + ")";
		return null; // TODO: return result computed in code above
	}

	/**
	 * Remove the entry for the given key, and return the old list, if any,
	 * associated with it.
	 * @param key array of strings used as a key, must not be null, or include null
	 * @return array list of strings formerly associated with the key, may be null.
	 */
	public List<String> remove(String[] key) {
		assert wellFormed(): "invariant broken at start of remove(" + Arrays.toString(key) + ")";
		// TODO (don't duplicate code)
		assert wellFormed() : "invariant broken after remove(" + Arrays.toString(key) + ")";
		return null; // TODO: return result computed in code above
	}

	/**
	 * Return a key chosen uniformly random from the table.
	 * This operation is not efficient: it takes time linear in the
	 * number of entries.
	 * @param r source of randomness, must not be null
	 * @throw IllegalStateException if the table has no keys (is empty)
	 * @return array of strings as a random key from table (this array
	 * will be fresh -- the recipient may modify it without harming this table).
	 */
	public String[] randomKey(Random r) throws IllegalStateException {
		assert wellFormed() : "invariant broken at start of randomKey";
		return null; // TODO
	}

	@Override
	public String toString() {
		return picture();
	}

	/**
	 * Return a string that summarizes the state of the internal hashtable.
	 * The array is show in the form [e0,e2->e3,...,en]
	 * where e<i>i</i> is determined by the entry there:
	 * <ul>
	 * <li> nothing if the entry is null
	 * <li> A -> separating the elements within a bucket
	 * <li> the words of the key separated by spaces.
	 * </ul>
	 * Don't change this code.
	 * @return string summary of the internal array.
	 */
	public String picture() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		boolean first = true;
		for (Entry e : table) {
			if (first) first = false;
			else sb.append(",");

			if (e == null) continue;
			else if (e.key == null) sb.append('?'); // don't crash, show problem
			else {//we have an element
				//print whole bucket
				Entry b = e;
				boolean firstInBucket = true;
				while(b!= null) {
					if (firstInBucket) firstInBucket = false;
					else sb.append("->");
					boolean firstWord=true;
					for (String w : b.key) {
						if (firstWord) firstWord=false;
						else sb.append(' ');
						sb.append(w);
					}

					b = b.next;
				}

			}
		}
		sb.append("]");
		return sb.toString();
	}

	// Do not change anything in this class (used for internal testing)
	public static class Spy {
		/**
		 * Return the sink for invariant error messages
		 * @return current reporter
		 */
		public Consumer<String> getReporter() {
			return reporter;
		}

		/**
		 * Change the sink for invariant error messages.
		 * @param r where to send invariant error messages.
		 */
		public void setReporter(Consumer<String> r) {
			reporter = r;
		}
		
		/**
		 * A test version of the entry class that exposes the keys
		 * for testing purposes.
		 */
		public static class TestEntry extends Entry implements List<String> {
			private static final long serialVersionUID = 1L; // KEH

			public TestEntry(String[] k) {
				super(k);
			}
			
			public String[] key() { return key; }
			
			public void setNext(TestEntry n) {
				this.next = n;
			}
		}
		
		/**
		 * Connect the entries in a linked list (forwards).
		 * @param entries entries to connect, if fewer than two, this method does nothing.
		 */
		public void link(TestEntry... entries) {
			TestEntry e = null;
			for (TestEntry n : entries) {
				if (e != null) e.setNext(n);
				e = n;
			}
		}
		
		/**
		 * Create a travesty map with the given data structure
		 * for testing purposes.
		 * @param t table to use
		 * @param n claimed number of entries
		 * @return newly creates test travesty map
		 */
		public TravestyMap makeMap(TestEntry[] t, int n) {
			TravestyMap result = new TravestyMap(false);
			result.table = t;
			result.numEntries = n;
			return result;
		}
		
		/**
		 * Return whether this map's data structure is well formed
		 * @param tm travesty map to check, must not be null
		 * @return whether the invariant checker thinks the data structure is OK
		 */
		public boolean wellFormed(TravestyMap tm) {
			return tm.wellFormed();
		}
		
		/**
		 * Test the hashing of arrays for the given travesty map
		 * @param tm travesty map to use
		 * @param key string array to hash
		 * @return
		 */
		public int hash(TravestyMap tm, String[] key) {
			return tm.hash(key);
		}
	}
}
