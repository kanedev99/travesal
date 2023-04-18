import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;

import edu.uwm.cs351.TravestyMap;
import edu.uwm.cs351.TravestyMap.Spy;
import junit.framework.TestCase;


public class TestInternals extends TestCase {
	TravestyMap.Spy spy;
	protected int reports;

	protected void assertReporting(boolean expected, Supplier<Boolean> test) {
		reports = 0;
		Consumer<String> savedReporter = spy.getReporter();
		try {
			spy.setReporter((String message) -> {
				++reports;
				if (message == null || message.trim().isEmpty()) {
					assertFalse("Uninformative report is not acceptable", true);
				}
				if (expected) {
					assertFalse("Reported error incorrectly: " + message, true);
				}
			});
			assertEquals(expected, test.get().booleanValue());
			if (!expected) {
				assertEquals("Expected exactly one invariant error to be reported;", 1, reports);
			}
			spy.setReporter(null);
		} finally {
			spy.setReporter(savedReporter);
		}
	}
	
	TravestyMap self;

	private static String[][] keys = new String[][] {
			null,
			new String[] {"the"},
			new String[] {"the","quick"},
			new String[] {"the","quick","brown"},
			new String[] {"the","quick","brown","fox"},
			new String[] {"the","quick","brown","fox","jumped"},
			new String[] {"the","quick","brown","fox","jumped","over"},
			new String[] {"the","quick","brown","fox","jumped","over","lazy"},
			new String[] {"the","quick","brown","fox","jumped","over","lazy","dog"},
			new String[] {"pack my box with five dozen liquor jugs"}
	};
	
	private Spy.TestEntry[] e;

	@Override
	public void setUp() {
		spy = new TravestyMap.Spy();
		self = spy.makeMap(null, 0);
		e = new Spy.TestEntry[keys.length];
		for (int i=0; i < keys.length; ++i) {
			e[i] = new Spy.TestEntry(keys[i]);
		}
	}
	
	private void assertWellFormed(boolean expected) {
		assertReporting(expected, () -> spy.wellFormed(self));
	}
	
	protected Spy.TestEntry[] tea; // array for the hash table
	
	
	/// testAx: an emoty table
	
	public void testA0() {
		self = spy.makeMap(null, 0);
		assertWellFormed(false);
	}
	
	public void testA1() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 0);
		assertWellFormed(true);
	}
	
	public void testA2() {
		tea = new Spy.TestEntry[17];
		self = spy.makeMap(tea, 0);
		assertWellFormed(false);
	}
	
	public void testA3() {
		tea = new Spy.TestEntry[3];
		self = spy.makeMap(tea, 0);
		assertWellFormed(false);
	}
	
	public void testA4() {
		tea = new Spy.TestEntry[0];
		self = spy.makeMap(tea, 0);
		assertWellFormed(false);
	}
	
	public void testA5() {
		tea = new Spy.TestEntry[8];
		self = spy.makeMap(tea, 0);
		assertWellFormed(false);
	}
	
	public void testA6() {
		tea = new Spy.TestEntry[11];
		self = spy.makeMap(tea, 0);
		assertWellFormed(false);
	}
	
	
	/// testBX: hashing keys when table size is 7
	
	public void testB1() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 0);
		assertEquals(4, spy.hash(self, keys[1]));
	}
	
	public void testB2() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 0);
		assertEquals(2, spy.hash(self, keys[2]));
	}
	
	public void testB3() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 0);
		assertEquals(3, spy.hash(self, keys[3]));
	}
	
	public void testB4() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 0);
		assertEquals(0, spy.hash(self, keys[4]));
	}
	
	public void testB5() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 0);
		assertEquals(6, spy.hash(self, keys[5]));
	}
	
	public void testB6() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 0);
		assertEquals(0, spy.hash(self, keys[6]));
	}
	
	public void testB7() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 0);
		assertEquals(6, spy.hash(self, keys[7]));
	}
	
	public void testB8() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 0);
		assertEquals(6, spy.hash(self, keys[8]));
	}
	
	public void testB9() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 0);
		assertEquals(6, spy.hash(self, keys[9]));
	}
	
	
	/// testCx: A single element
	
	public void testC0() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		assertWellFormed(false);
	}
	
	public void testC1() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 0);
		tea[4] = e[1];
		assertWellFormed(false);
	}
	
	public void testC2() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[2] = e[2];
		assertWellFormed(false);
	}
	
	public void testC3() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[3] = e[3];
		assertWellFormed(true);
	}
	
	public void testC4() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[0] = e[4];
		assertWellFormed(true);
	}
	
	public void testC5() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[6] = e[5];
		assertWellFormed(true);
	}
	
	public void testC6() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[0] = e[6];
		assertWellFormed(true);
	}
	
	public void testC7() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[6] = e[7];
		assertWellFormed(true);
	}
	
	public void testC8() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[6] = e[8];
		assertWellFormed(true);
	}
	
	public void testC9() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 0);
		tea[6] = e[9];
		assertWellFormed(false);
	}
	
	
	/// testDx: Placing e[1] correctly
	
	public void testD0() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[0] = e[1];
		assertWellFormed(false);
	}
	
	public void testD1() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[1] = e[1];
		assertWellFormed(false);
	}
	
	public void testD2() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[2] = e[1];
		assertWellFormed(false);
	}
	
	public void testD3() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[3] = e[1];
		assertWellFormed(false);
	}
	
	public void testD4() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[4] = e[1];
		assertWellFormed(true);
	}
	
	public void testD5() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[5] = e[1];
		assertWellFormed(false);
	}
	
	public void testD6() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[6] = e[1];
		assertWellFormed(false);
	}
	
	public void testD7() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[6] = e[1];
		tea[1] = e[1];
		assertWellFormed(false);
	}
	
	public void testD8() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[6] = e[1];
		tea[1] = e[1];
		assertWellFormed(false);
	}
	
	
	/// testEx: Placing e[3] correctly
	
	public void testE0() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[0] = e[3];
		assertWellFormed(false);
	}
	
	public void testE1() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[1] = e[3];
		assertWellFormed(false);
	}
	
	public void testE2() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[2] = e[3];
		assertWellFormed(false);
	}
	
	public void testE3() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[3] = e[3];
		assertWellFormed(true);
	}
	
	public void testE4() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[4] = e[3];
		assertWellFormed(false);
	}
	
	public void testE5() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[5] = e[3];
		assertWellFormed(false);
	}
	
	public void testE6() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[6] = e[3];
		assertWellFormed(false);
	}

	
	/// testFx: null is not a good key anywhere
	
	public void testF0() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[0] = e[0];
		assertWellFormed(false);
	}
	
	public void testF1() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[1] = e[0];
		assertWellFormed(false);
	}
	
	public void testF2() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[2] = e[0];
		assertWellFormed(false);
	}
	
	public void testF3() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[3] = e[0];
		assertWellFormed(false);
	}
	
	public void testF4() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[4] = e[0];
		assertWellFormed(false);
	}
	
	public void testF5() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[5] = e[0];
		assertWellFormed(false);
	}
	
	public void testF6() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[6] = e[0];
		assertWellFormed(false);
	}

	
	public void testG2() {
		tea = new Spy.TestEntry[9];
		self = spy.makeMap(tea, 1);
		tea[2] = e[2];
		assertWellFormed(false);
	}
	
	
	/// testHx: placing test4 and test8
	
	public void testH0() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[0] = e[4];
		tea[6] = e[8];
		assertWellFormed(true);
	}
	
	public void testH1() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[1] = e[4];
		tea[6] = e[8];
		assertWellFormed(false);
	}
	
	public void testH2() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[2] = e[4];
		tea[6] = e[8];
		assertWellFormed(false);
	}
	
	public void testH3() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[3] = e[4];
		tea[6] = e[8];
		assertWellFormed(false);
	}
	
	public void testH4() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[4] = e[4];
		tea[6] = e[8];
		assertWellFormed(false);
	}
	
	public void testH5() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[5] = e[4];
		tea[6] = e[8];
		assertWellFormed(false);
	}
	
	public void testH6() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[0] = e[4];
		tea[6] = e[8];
		assertWellFormed(false);
	}
	
	public void testH7() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[0] = e[4];
		tea[1] = e[8];
		assertWellFormed(false);
	}
	
	public void testH8() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[0] = e[4];
		tea[2] = e[8];
		assertWellFormed(false);
	}
	
	public void testH9() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[0] = e[4];
		tea[4] = e[8];
		assertWellFormed(false);
	}
	
	
	
	public void testJ0() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[4] = e[1];
		tea[6] = e[5];
		assertWellFormed(true);
	}
	
	public void testJ1() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 3);
		tea[4] = e[1];
		tea[6] = e[5];
		assertWellFormed(false);
	}
	
	
	public void testK0() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[4] = e[1];
		tea[0] = e[0];
		assertWellFormed(false);
	}
	
	public void testK1() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[4] = e[1];
		tea[6] = e[0];
		assertWellFormed(false);
	}

	
	/// testKx cannot place e[4] and e[6] (for now)
	
	public void testL0() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[0] = e[4];
		tea[1] = e[6];
		assertWellFormed(false);
	}
	
	public void testL1() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[0] = e[4];
		tea[2] = e[6];
		assertWellFormed(false);
	}
	
	public void testL2() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[0] = e[4];
		tea[3] = e[6];
		assertWellFormed(false);
	}
	
	public void testL3() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[0] = e[4];
		tea[4] = e[6];
		assertWellFormed(false);
	}
	
	public void testL4() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[0] = e[4];
		tea[5] = e[6];
		assertWellFormed(false);
	}
	
	public void testL5() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[0] = e[4];
		tea[6] = e[6];
		assertWellFormed(false);
	}
	
	public void testL6() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[0] = e[6];
		tea[1] = e[4];
		assertWellFormed(false);
	}
	
	public void testL7() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[0] = e[6];
		tea[2] = e[4];
		assertWellFormed(false);
	}
	
	public void testL8() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[0] = e[6];
		tea[4] = e[4];
		assertWellFormed(false);
	}
	
	public void testL9() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[0] = e[6];
		tea[6] = e[4];
		assertWellFormed(false);
	}
	
	
	/// testMx: correctly handle e[4] and e[6]
	
	public void testM0() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[0] = e[4];
		spy.link(e[4],e[6]);
		assertWellFormed(true);
	}
	
	public void testM1() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[0] = e[4];
		spy.link(e[4],e[6]);
		assertWellFormed(false);
	}
	
	public void testM2() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[0] = e[6];
		spy.link(e[6],e[4]);
		assertWellFormed(true);
	}
	
	public void testM3() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[0] = e[4];
		tea[6] = e[6];
		spy.link(e[4],e[6]);
		assertWellFormed(false);
	}
	
	public void testM4() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 3);
		tea[0] = e[4];
		tea[6] = e[6];
		spy.link(e[4],e[6]);
		assertWellFormed(false);
	}
	
	public void testM5() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[0] = e[4];
		tea[6] = e[8];
		spy.link(e[4],e[8]);
		assertWellFormed(false);
	}
	
	public void testM6() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 3);
		tea[0] = e[4];
		tea[6] = e[8];
		spy.link(e[4],e[8]);
		assertWellFormed(false);
	}
	
	public void testM7() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[0] = e[4];
		spy.link(e[4],e[8]);
		assertWellFormed(false);
	}
	
	public void testM8() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[0] = e[4];
		spy.link(e[4],e[0]);
		assertWellFormed(false);
	}
	
	public void testM9() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[0] = e[4];
		spy.link(e[4],e[0]);
		assertWellFormed(false);
	}

	
	public void testN0() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[6] = e[7];
		spy.link(e[7],e[8]);
		assertWellFormed(true);
	}
	
	public void testN1() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[6] = e[7];
		tea[1] = e[8];
		spy.link(e[7],e[8]);
		assertWellFormed(false);
	}
	
	public void testN2() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 3);
		tea[6] = e[7];
		tea[1] = e[8];
		spy.link(e[7],e[8]);
		assertWellFormed(false);
	}
	
	public void testN3() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[6] = e[7];
		tea[1] = e[8];
		assertWellFormed(false);
	}
	
	public void testN4() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[6] = e[8];
		spy.link(e[8],e[7]);
		assertWellFormed(true);
	}
	
	
	// testPx: simple cycles
	
	public void testP0() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[0] = e[4];
		spy.link(e[4], e[4]);
		assertWellFormed(false);
	}
	
	public void testP1() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[0] = e[4];
		spy.link(e[4], e[4]);
		assertWellFormed(false);
	}
	
	public void testP5() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[6] = e[8];
		spy.link(e[8],e[7],e[8]);
		assertWellFormed(false);
	}
	
	public void testP6() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[6] = e[8];
		spy.link(e[8],e[7],e[7]);
		assertWellFormed(false);
	}
	
	
	/// testQx: simple duplicates
	
	public void testQ0() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[0] = e[4];
		Spy.TestEntry im4 = new Spy.TestEntry(keys[4]);
		spy.link(e[4], im4);
		assertWellFormed(false);
	}
	
	public void testQ1() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 1);
		tea[0] = e[4];
		Spy.TestEntry im4 = new Spy.TestEntry(keys[4]);
		spy.link(e[4], im4);
		assertWellFormed(false);
	}
	
	public void testQ5() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 2);
		tea[6] = e[8];
		Spy.TestEntry im7 = new Spy.TestEntry(keys[7]);
		spy.link(e[8],e[7],im7);
		assertWellFormed(false);
	}
	
	public void testQ6() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 3);
		tea[6] = e[8];
		Spy.TestEntry im7 = new Spy.TestEntry(keys[7]);
		spy.link(e[8],e[7],im7);
		assertWellFormed(false);
	}
	
	public void testQ2() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 3);
		tea[6] = e[7];
		Spy.TestEntry im7 = new Spy.TestEntry(keys[7]);
		spy.link(e[7],e[8],im7);
		assertWellFormed(false);
	}

	
	/// testRx: longer chains
	
	public void testR0() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 4);
		tea[6] = e[7];
		spy.link(e[7], e[5], e[9], e[8]);
		assertWellFormed(true);
	}
	
	public void testR1() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 4);
		tea[6] = e[7];
		spy.link(e[7], e[8], e[9], e[5]);
		assertWellFormed(true);
	}
	
	public void testR2() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 4);
		tea[6] = e[7];
		spy.link(e[7], e[8], e[1], e[5]);
		assertWellFormed(false);
	}
	
	public void testR3() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 4);
		tea[6] = e[7];
		spy.link(e[7], e[0], e[9], e[5]);
		assertWellFormed(false);
	}
	
	public void testR4() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 4);
		tea[6] = e[7];
		tea[5] = e[9];
		spy.link(e[7], e[8], e[9], e[5]);
		assertWellFormed(false);
	}
	
	public void testR5() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 3);
		tea[0] = e[4];
		spy.link(e[4], e[0], e[6]);
		assertWellFormed(false);
	}
	
	public void testR6() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 4);
		tea[0] = e[4];
		tea[6] = e[7];
		spy.link(e[7], e[8], e[9], e[4]);
		assertWellFormed(false);
	}
	
	public void testR7() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 5);
		tea[0] = e[4];
		tea[6] = e[7];
		spy.link(e[7], e[8], e[9], e[4]);
		assertWellFormed(false);
	}
	
	public void testR8() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 4);
		tea[0] = e[4];
		tea[6] = e[7];
		spy.link(e[7], e[6]);
		spy.link(e[4], e[8]);
		assertWellFormed(false);
	}


	/// testSx: longer cycles
	
	public void testS0() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 4);
		tea[6] = e[7];
		spy.link(e[7], e[5], e[9], e[8], e[7]);
		assertWellFormed(false);
	}

	public void testS1() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 4);
		tea[6] = e[7];
		spy.link(e[7], e[5], e[9], e[8], e[5]);
		assertWellFormed(false);
	}

	public void testS2() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 4);
		tea[6] = e[7];
		spy.link(e[7], e[5], e[9], e[8], e[9]);
		assertWellFormed(false);
	}

	public void testS3() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 4);
		tea[6] = e[7];
		spy.link(e[7], e[5], e[9], e[8], e[8]);
		assertWellFormed(false);
	}

	public void testS4() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 4);
		tea[6] = e[7];
		spy.link(e[7], e[5], e[9], e[8], e[0]);
		assertWellFormed(false);
	}

	public void testS5() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 4);
		tea[6] = e[7];
		spy.link(e[7], e[5], e[9], e[0]);
		assertWellFormed(false);
	}

	public void testS6() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 5);
		tea[6] = e[7];
		spy.link(e[7], e[5], e[9], e[8], e[0]);
		assertWellFormed(false);
	}
	
	
	/// testTx: duplicates
	
	public void testT0() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 5);
		tea[6] = e[7];
		Spy.TestEntry imm = new Spy.TestEntry(keys[7]);
		spy.link(e[7], e[5], e[9], e[8], imm);
		assertWellFormed(false);
	}
	
	public void testT1() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 5);
		tea[6] = e[7];
		Spy.TestEntry imm = new Spy.TestEntry(keys[5]);
		spy.link(e[7], e[5], e[9], e[8], imm);
		assertWellFormed(false);
	}
	
	public void testT2() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 5);
		tea[6] = e[7];
		Spy.TestEntry imm = new Spy.TestEntry(keys[9]);
		spy.link(e[7], e[5], e[9], e[8], imm);
		assertWellFormed(false);
	}
	
	public void testT3() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 5);
		tea[6] = e[7];
		Spy.TestEntry imm = new Spy.TestEntry(keys[8]);
		spy.link(e[7], e[5], e[9], e[8], imm);
		assertWellFormed(false);
	}
	
	public void testT4() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 5);
		tea[6] = e[7];
		Spy.TestEntry imm = new Spy.TestEntry(keys[5]);
		spy.link(e[7], e[5], e[9], imm, e[8]);
		assertWellFormed(false);
	}

	
	/// testUx: even more elements (how many can we have?)
	
	public void testU0() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 5);
		tea[0] = e[4];
		tea[6] = e[7];
		spy.link(e[4], e[6]);
		spy.link(e[7], e[5], e[9]);
		assertWellFormed(true);
	}
	
	public void testU1() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 5);
		tea[0] = e[4];
		tea[2] = e[2];
		tea[3] = e[3];
		tea[4] = e[1];
		tea[6] = e[7];
		assertWellFormed(true);
	}

	public void testU2() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 5);
		tea[6] = e[7];
		spy.link(e[7], e[5], e[9], e[8]);
		tea[0] = e[6];
		assertWellFormed(true);
	}

	public void testU3() {
		// What is the comment about testUx ?
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 6);
		tea[6] = e[7];
		spy.link(e[7], e[5], e[9], e[8]);
		tea[0] = e[6];
		spy.link(e[6], e[4]);
		assertWellFormed(false);
	}
	
	public void testU4() {
		tea = new Spy.TestEntry[7];
		self = spy.makeMap(tea, 6);
		tea[0] = e[4];
		tea[2] = e[2];
		tea[3] = e[3];
		tea[4] = e[1];
		tea[6] = e[7];
		spy.link(e[7], e[8]);
		assertWellFormed(false);
	}
	
	
	public void testV1() {
		tea = new Spy.TestEntry[17];
		self = spy.makeMap(tea, 0);
		assertEquals(14, spy.hash(self, keys[1]));
	}
	
	public void testV2() {
		tea = new Spy.TestEntry[17];
		self = spy.makeMap(tea, 0);
		assertEquals(9, spy.hash(self, keys[2]));
	}
	
	public void testV3() {
		tea = new Spy.TestEntry[17];
		self = spy.makeMap(tea, 0);
		assertEquals(8, spy.hash(self, keys[3]));
	}
	
	public void testV4() {
		tea = new Spy.TestEntry[17];
		self = spy.makeMap(tea, 0);
		assertEquals(6, spy.hash(self, keys[4]));
	}
	
	public void testV5() {
		tea = new Spy.TestEntry[17];
		self = spy.makeMap(tea, 0);
		assertEquals(2, spy.hash(self, keys[5]));
	}
	
	public void testV6() {
		tea = new Spy.TestEntry[17];
		self = spy.makeMap(tea, 0);
		assertEquals(5, spy.hash(self, keys[6]));
	}
	
	public void testV7() {
		tea = new Spy.TestEntry[17];
		self = spy.makeMap(tea, 0);
		assertEquals(6, spy.hash(self, keys[7]));
	}
	
	public void testV8() {
		tea = new Spy.TestEntry[17];
		self = spy.makeMap(tea, 0);
		assertEquals(8, spy.hash(self, keys[8]));
	}
	
	public void testV9() {
		tea = new Spy.TestEntry[17];
		self = spy.makeMap(tea, 0);
		assertEquals(11, spy.hash(self, keys[9]));
	}
	
	
	public void testW0() {
		tea = new Spy.TestEntry[17];
		self = spy.makeMap(tea, 6);
		tea[0] = e[4];
		tea[2] = e[2];
		tea[3] = e[3];
		tea[4] = e[1];
		tea[6] = e[7];
		spy.link(e[7], e[8]);
		assertWellFormed(false);
	}
	
	public void testW1() {
		tea = new Spy.TestEntry[17];
		self = spy.makeMap(tea, 7);
		tea[6] = e[4];
		tea[8] = e[3];
		tea[14] = e[1];
		tea[9] = e[2];
		tea[2] = e[5];
		spy.link(e[4], e[7]);
		spy.link(e[3], e[8]);
		assertWellFormed(true);
	}
	
	public void testW2() {
		tea = new Spy.TestEntry[17];
		self = spy.makeMap(tea, 9);
		tea[6] = e[7];
		tea[8] = e[8];
		tea[14] = e[1];
		tea[9] = e[2];
		tea[2] = e[5];
		tea[5] = e[6];
		tea[11] = e[9];
		spy.link(e[7], e[4]);
		spy.link(e[8], e[3]);
		assertWellFormed(true);
	}
	
	public void testW3() {
		tea = new Spy.TestEntry[17];
		self = spy.makeMap(tea, 9);
		tea[6] = e[7];
		tea[8] = e[8];
		tea[14] = e[1];
		tea[9] = e[2];
		tea[2] = e[5];
		tea[5] = e[6];
		tea[11] = e[9];
		spy.link(e[7], e[3]);
		spy.link(e[8], e[4]);
		assertWellFormed(false);
	}

	public void testW4() {
		tea = new Spy.TestEntry[17];
		self = spy.makeMap(tea, 9);
		tea[6] = e[7];
		tea[8] = e[8];
		tea[14] = e[1];
		tea[9] = e[2];
		tea[2] = e[5];
		tea[5] = e[6];
		tea[11] = e[9];
		spy.link(e[7], e[4], e[8], e[3]);
		assertWellFormed(false);
	}

	public void testW5() {
		tea = new Spy.TestEntry[17];
		self = spy.makeMap(tea, 11);
		tea[6] = e[7];
		tea[8] = e[8];
		tea[14] = e[1];
		tea[9] = e[2];
		tea[2] = e[5];
		tea[5] = e[6];
		tea[11] = e[9];
		spy.link(e[7], e[4], e[8], e[3]);
		assertWellFormed(false);
	}

	public void testW6() {
		tea = new Spy.TestEntry[17];
		self = spy.makeMap(tea, 7);
		tea[6] = e[4];
		tea[8] = e[3];
		tea[14] = e[1];
		tea[9] = e[2];
		tea[2] = e[5];
		spy.link(e[4], e[7]);
		spy.link(e[3], e[8]);
		spy.link(e[5], e[2]);
		assertWellFormed(false);
	}

	public void testW7() {
		tea = new Spy.TestEntry[17];
		self = spy.makeMap(tea, 8);
		tea[6] = e[4];
		tea[8] = e[3];
		tea[14] = e[1];
		tea[9] = e[2];
		tea[2] = e[5];
		spy.link(e[4], e[7]);
		spy.link(e[3], e[8]);
		spy.link(e[5], e[2]);
		assertWellFormed(false);
	}
	
	public void testW8() {
		tea = new Spy.TestEntry[17];
		self = spy.makeMap(tea, 7);
		tea[6] = e[4];
		tea[8] = e[3];
		tea[14] = e[1];
		tea[9] = e[2];
		tea[2] = e[5];
		spy.link(e[4], e[7]);
		spy.link(e[3], e[8]);
		tea[5] = e[9];
		assertWellFormed(false);
	}

	public void testW9() {
		tea = new Spy.TestEntry[17];
		self = spy.makeMap(tea, 5);
		tea[6] = e[4];
		tea[8] = e[3];
		tea[14] = e[1];
		tea[9] = e[2];
		tea[2] = e[5];
		spy.link(e[4], e[7]);
		spy.link(e[3], e[8]);
		assertWellFormed(false);
	}

	
	public void testX1() {
		tea = new Spy.TestEntry[37];
		self = spy.makeMap(tea, 0);
		assertEquals(21, spy.hash(self, keys[1]));
	}
	
	public void testX2() {
		tea = new Spy.TestEntry[37];
		self = spy.makeMap(tea, 0);
		assertEquals(23, spy.hash(self, keys[2]));
	}
	
	public void testX3() {
		tea = new Spy.TestEntry[37];
		self = spy.makeMap(tea, 0);
		assertEquals(33, spy.hash(self, keys[3]));
	}
	
	public void testX4() {
		tea = new Spy.TestEntry[37];
		self = spy.makeMap(tea, 0);
		assertEquals(3, spy.hash(self, keys[4]));
	}
	
	public void testX5() {
		tea = new Spy.TestEntry[37];
		self = spy.makeMap(tea, 0);
		assertEquals(20, spy.hash(self, keys[5]));
	}
	
	public void testX6() {
		tea = new Spy.TestEntry[37];
		self = spy.makeMap(tea, 0);
		assertEquals(35, spy.hash(self, keys[6]));
	}
	
	public void testX7() {
		tea = new Spy.TestEntry[37];
		self = spy.makeMap(tea, 0);
		assertEquals(13, spy.hash(self, keys[7]));
	}
	
	public void testX8() {
		tea = new Spy.TestEntry[37];
		self = spy.makeMap(tea, 0);
		assertEquals(5, spy.hash(self, keys[8]));
	}
	
	public void testX9() {
		tea = new Spy.TestEntry[37];
		self = spy.makeMap(tea, 0);
		assertEquals(9, spy.hash(self, keys[9]));
	}

	
	public void testY1() {
		tea = new Spy.TestEntry[37];
		self = spy.makeMap(tea, 1);
		tea[35] = e[6];
		assertWellFormed(true);
	}
	
	public void testY2() {
		tea = new Spy.TestEntry[37];
		self = spy.makeMap(tea, 2);
		tea[21] = e[1];
		tea[23] = e[2];
		assertWellFormed(true);
	}
	
	public void testY3() {
		tea = new Spy.TestEntry[37];
		self = spy.makeMap(tea, 3);
		tea[33] = e[3];
		tea[3] = e[4];
		tea[20] = e[5];
		assertWellFormed(true);
	}
	
	public void testY4() {
		tea = new Spy.TestEntry[37];
		self = spy.makeMap(tea, 4);
		tea[35] = e[6];
		tea[13] = e[7];
		tea[5] = e[8];
		tea[9] = e[9];
		assertWellFormed(true);
	}
	
	public void testY5() {
		tea = new Spy.TestEntry[37];
		self = spy.makeMap(tea, 4);
		tea[35] = e[6];
		tea[13] = e[9];
		tea[5] = e[8];
		tea[9] = e[7];
		assertWellFormed(false);
	}
			
	// This can be run for information.  
	// It is not part of the assignment.
	public static void main(String[] args) {
		System.out.println("i\te[i]%7\te[i]%9\te[i]%17\te[i]%37\n");
		for (int i = 0; i < keys.length; ++i) {
			int h = Arrays.hashCode(keys[i]);
			int i1 = h % 7;
			int i15 = h % 9;
			int i2 = h % 17;
			int i3 = h % 37;
			System.out.println(i + "\t" + i1 + "\t" + i15 + "\t" + i2 + "\t" + i3);
		}
	}
}
