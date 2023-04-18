import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import edu.uwm.cs.junit.LockedTestCase;
import edu.uwm.cs351.Travesty;


public class TestTravesty extends LockedTestCase {
	protected void assertIncluded(int lo, int hi, int value) {
		assertTrue("value below range: " + value + " not in [" + lo + ", " + hi + "]", value >= lo);
		assertTrue("value above range: " + value + " not in [" + lo + ", " + hi + "]", value >= lo);
	}
	
	private Travesty tr;

	/**
	 * Generate a travesty text up to the maximum length and then stop the process.
	 * @param tr The travesty to use to generate
	 * @param max the maximum number of times to accept words
	 * @param c the consumer to give the words
	 * @return the number of times words were generated.
	 * It will be less than max if the generator ended on its own.
	 * @throws RuntimeException if the consumer throws an exception
	 */
	protected int generateUpTo(Travesty tr, int max, Consumer<String> c) {
		class LimitedConsumer implements Consumer<String> {
			int count = 0;
			RuntimeException reason;
			public void accept(String s) {
				++count;
				c.accept(s);
				if (count >= max) {
					reason = new RuntimeException("Stop!");
					throw reason;
				}
			}
		}
		if (max <= 0) return 0;
		LimitedConsumer lc = new LimitedConsumer();
		try {
			tr.generate(lc);
		} catch (RuntimeException e) {
			if (e != lc.reason) {
				throw e;
			}
		}
		return lc.count;
	}
	
	public void test0() {
		tr = new Travesty(0); // no context: random words
		tr.add("rhubarb");
		StringBuilder sb = new StringBuilder();
		assertEquals(3, generateUpTo(tr, 3, (w) -> sb.append(w) ));
		// We generated three words and smushed them altogether.  What is the result?
		assertEquals(Ts(430226970),sb.toString());
	}

	public void test1() {
		tr = new Travesty(1); // one word context
		tr.add("a");
		tr.add("b");
		tr.add("c");
		tr.add("d");
		tr.add("e");
		StringBuilder sb = new StringBuilder();
		tr.generate((s) -> sb.append(s));
		switch(sb.charAt(0)) {
		// if it starts with "a", what must be the whole result?
		case 'a': assertEquals(Ts(379187060),sb.toString()); break;
		case 'b': assertEquals(Ts(664060887),sb.toString()); break;
		case 'c': assertEquals(Ts(1624933210),sb.toString()); break;
		case 'd': assertEquals(Ts(290644142),sb.toString()); break;
		default:
			assertFalse("bad result from generate",true);
		}
	}
	
	public void test2() {
		tr = new Travesty(2); // two word context
		tr.add("a");
		tr.add("b");
		tr.add("a");
		tr.add("c");
		tr.add("b");
		tr.add("c");
		tr.add("d");
		StringBuilder sb = new StringBuilder();
		tr.generate((s) -> sb.append(s));
		switch(sb.toString().substring(0,2)) {
		case "ab": assertEquals(Ts(1106234790),sb.toString()); break;
		case "ba": assertEquals(Ts(750301624),sb.toString()); break;
		case "ac": assertEquals("acbcd",sb.toString()); break;
		case "cb": assertEquals("cbcd",sb.toString()); break;
		case "bc": assertEquals("bcd",sb.toString()); break;
		default:
			assertFalse("bad result from generate",true);
		}
	}
	
	public void test3() {
		tr = new Travesty(3);
		tr.add("a");
		tr.add("b");
		tr.add("c");
		tr.add("d");
		tr.restart();
		tr.add("e");
		tr.add("f");
		tr.add("g");
		tr.add("h");
		StringBuilder sb = new StringBuilder();
		boolean gotFirst = false, gotSecond = false;
		for (int i=0; i < 50; ++i) {
			sb.setLength(0);
			tr.generate((s) -> sb.append(s));
			switch (sb.toString()) {
			case "abcd":
				gotFirst = true;
				break;
			case "efgh":
				gotSecond = true;
				break;
			default:
				if (sb.toString().contains("de")) {
					assertFalse("restart didn't disconnect before and after",true);
				} else {
					assertFalse("generated nonsense: " + sb,true);
				}
			}
		}
		assertTrue("restart incorrectly tossed old state.",gotFirst);
		assertTrue("restart prevented new changes?",gotSecond);
	}
	
	public void test4() {
		tr = new Travesty(0);
		tr.add("bananas");
		tr.add("melons");
		tr.restart(); // no effect;
		tr.add("grapes");
		final Map<String,Integer> m = new HashMap<>();
		assertEquals(1000, generateUpTo(tr, 1000, (w) -> {
			m.put(w,m.getOrDefault(w, 0) + 1);
		}));
		assertIncluded(300, 375, m.get("bananas"));
		assertIncluded(300, 375, m.get("melons"));
		assertIncluded(300, 375, m.get("grapes"));
	}
	
	public void test5() {
		tr = new Travesty(1);
		tr.add("start");
		tr.add("apples");
		tr.add("start");
		tr.add("oranges");
		tr.add("start");
		tr.add("bananas");
		tr.add("start");
		tr.add("grapes");
		tr.add("start");
		final Map<String,Integer> m = new HashMap<>();
		assertEquals(2000, generateUpTo(tr, 2000, (w) -> {
			m.put(w,m.getOrDefault(w, 0) + 1);
		}));
		assertEquals(1000,m.get("start").intValue());
		assertIncluded(220, 280, m.get("apples"));
		assertIncluded(220, 280, m.get("oranges"));
		assertIncluded(220, 280, m.get("bananas"));
		assertIncluded(220, 280, m.get("grapes"));
	}
}
