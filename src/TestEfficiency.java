import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import junit.framework.TestCase;
import edu.uwm.cs351.TravestyMap;


public class TestEfficiency extends TestCase {
	private static final String PART2 = "bottles of pop";
	private static final int POWER = 19;
	private static final int MAX = 1<<POWER;
	private static final int TESTS = 100_000;
	private TravestyMap tm;
	
	private String makeNumericString(int i) {
		return String.format("%d", i);
	}

	protected void setUp() throws Exception {
		super.setUp();
		try {
			assert tm.size() == TESTS : "cannot run test with assertions enabled";
		} catch (NullPointerException ex) {
			throw new IllegalStateException("Cannot run test with assertions enabled");
		}
		tm = new TravestyMap();
		String[] key = new String[2];
		key[1] = PART2;
		for (int i=0; i < MAX; ++i) {
			key[0] = makeNumericString(i);
			tm.get(key).add("on the wall");
		}
	}

	public void testSize() {
		for (int i=0; i < TESTS; ++i) {
			assertEquals(MAX,tm.size());
		}
	}
	
	public void testFind() {
		Random r = new Random();
		String[] key = new String[2];
		key[1] = PART2;
		for (int i=0; i < TESTS; ++i) {
			key[0] = makeNumericString(r.nextInt(MAX));
			List<String> l = tm.find(key);
			assertEquals(1,l.size());
		}
	}
	
	public void testRemove() {
		Random r = new Random();
		String[] key = new String[2];
		key[1] = PART2;
		Set<Integer> removed = new TreeSet<Integer>();
		for (int i=0; i < TESTS; ++i) {
			int h = r.nextInt(MAX);
			key[0] = makeNumericString(h);
			List<String> l = tm.remove(key);
			if (removed.add(h)) {
				assertEquals(1,l.size());
			} else {
				assertNull(l);
			}
		}
	}
}
