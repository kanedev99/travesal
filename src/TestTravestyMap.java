import java.util.Arrays;
import java.util.List;
import java.util.Random;

import edu.uwm.cs.junit.LockedTestCase;
import edu.uwm.cs351.TravestyMap;


public class TestTravestyMap extends LockedTestCase {
    protected static void assertException(Class<? extends Throwable> c, Runnable r) {
    	try {
    		r.run();
    		assertFalse("Exception should have been thrown",true);
        } catch (RuntimeException ex) {
        	assertTrue("should throw exception of " + c + ", not of " + ex.getClass(), c.isInstance(ex));
        }	
    }	

	private TravestyMap tm;
	private Random random;
	
	private String[] a0 = {};
	private String[] a1 = {""};
	private String[] a2 = {"2"};
	private String[] a3 = {"hello","world"};
	private String[] a4 = {"CS","351","is","fun!"};
	private String[] a5 = {"5"};
	private String[] a6 = {"vi"};
	private String[] a7 = {"7"};
	private String[] a8 = {"88"};
	private String[] a9 = {"nine"};
	
	protected void setUp() {
		try {
			assert tm.size() == 42;
			assertTrue("Assertions not enabled.  Add -ea to VM Args Pane in Arguments tab of Run Configuration",false);
		} catch (NullPointerException ex) {
			assertTrue(true);
		}
		tm = new TravestyMap();
		random = new Random();
	}
	
	
	/// test(): locked tests
	public void test() {
		// tm is empty, a0 is the empty array (not null!) 
		List<String> l = tm.find(a0);
		assertEquals(Tb(793871904), l == null);
		l = tm.get(a2); // a2 = ["2"]
		assertEquals(Tb(502148067), l == null);
		assertEquals(Ti(2114493775),l.size());
		assertEquals(Ti(466828416),tm.size());
		l.add("hello");
		l = tm.find(a2);
		assertEquals(Tb(1434850236), l == null);
		assertEquals(Ti(1672324113),l.size());
		l = tm.find(a0);
		assertEquals(Tb(162655893), l == null);
		testRemove(false);
	}
	
	private void testRemove(boolean ignored) {
		tm = new TravestyMap();
		List<String> l = tm.get(a3); // a3 = ["hello","world"]
		l.add("Bert");
		l = tm.remove(a3.clone());
		assertEquals(Ts(558579339),l.get(0));
		assertEquals(Ti(502020279),tm.size());

	}
	
	
	public void test00() {
		assertEquals(0,tm.size());
	}
	
	public void test01() {
		assertNull(tm.find(a2));
	}
	
	public void test02() {
		List<String> l = tm.get(a2);
		assertTrue(l != null);
	}
	
	public void test03() {
		List<String> l = tm.get(a3.clone());
		assertSame("Must return the identical list",l,tm.find(a3));
	}
	
	public void test04() {
		assertEquals(0,tm.get(a4).size());
	}
	
	public void test05() {
		List<String> l = tm.get(a5.clone());
		assertSame("Must return the identical list",l,tm.get(a5));
	}
	
	public void test06() {
		tm.get(a6).add("humpty");
		assertEquals(1,tm.get(a6).size());
	}
	
	public void test07() {
		tm.get(a7).add("cabbage");
		tm.find(a7.clone()).add("beets");
		tm.get(a7).add("carrots");
		assertEquals(3,tm.find(a7).size());
	}
	
	public void test08() {
		List<String> l = tm.get(a8);
		l.add("celery");
		l.add("bread");
		l.add("tea");
		l.add("fish");
		assertEquals("tea",tm.find(a8.clone()).get(2));
	}
	
	public void test09() {
		List<String> l = tm.get(a0);
		l.add("beans");
		l.add("radish");
		tm.find(a0.clone()).clear();
		assertEquals(0,l.size());
	}
	
	public void test10() {
		tm.get(a2).add("beets");
		assertEquals("[,,,,2,,]",tm.picture());
	}
	
	public void test11() {
		// tm starts empty
		List<String> l = tm.get(a3); // a3 = ["hello","world"]
		l.add("rhombus");
		String[] a3c = a3.clone();
		l = tm.find(a3c);
		assertEquals(Tb(2104394962),l == null);
		assertEquals(1,l.size());
		assertEquals("rhombus",l.get(0));
		assertEquals("[hello world,,,,,,]",tm.picture());
	}
	
	public void test12() {
		// tm starts empty
		String[] a4c = a4.clone(); // a4 = ["CS","351","is","fun!"]
		List<String> l = tm.get(a4c); 
		l.add("Yeah!");
		assertEquals("[,,,,CS 351 is fun!,,]",tm.picture());
		a4c[2] = "isn't"; // Mutating a4c (but not the original a4)
		l = tm.find(a4c); // using mutated array
		assertTrue(l == null);
		assertEquals("[,,,,CS 351 is fun!,,]",tm.picture());
		l = tm.find(a4); // using original array
		assertEquals(1,l.size());
		assertEquals("Yeah!",l.get(0));
	}
	
	public void test20() {
		tm.get(a0);
		tm.get(a1);
		assertEquals(2,tm.size());
	}
	
	public void test21() {
		List<String> l1 = tm.get(a2);
		List<String> l2 = tm.get(a3);
		assertTrue(l1 != l2);
	}
	
	public void test22() {
		List<String> l1 = tm.get(a4);
		List<String> l2 = tm.get(a5);
		assertSame(l1,tm.find(a4));
		assertSame(l2,tm.find(a5));
	}
	
	public void test23() {
		List<String> l1 = tm.get(a3);
		tm.get(a4).add("melon");
		l1.add("lemon");
		l1.add("lmnop");
		assertEquals("lemon",tm.get(a3.clone()).get(0));
	}
	
	public void test24() {
		tm.get(a4);
		tm.get(a5);
		assertEquals("[5,,,,CS 351 is fun!,,]",tm.picture());
	}
	
	public void test25() {
		// tm starts empty
		List<String> l = tm.get(a5); // a5 = ["5"]
		l.add("lemons");
		l = tm.find(a3); // a3 = ["hello","world"] 
		assertEquals(true, l == null);
		l = tm.get(a3); 
		assertEquals(0,l.size());
		assertEquals(2,tm.size());
		assertEquals("[hello world->5,,,,,,]",tm.picture());
	}
	
	public void test26() {
		tm.get(a3);
		tm.get(a5);
		tm.get(a6);
		assertEquals("[vi->5->hello world,,,,,,]",tm.picture());
	}
	
	public void test27() {
		tm.get(a3).add("cabbage");
		tm.get(a5).add("leeks");
		tm.get(a6).add("turnips");
		assertEquals("cabbage",tm.find(a3).get(0));
		assertEquals("leeks",tm.get(a5).get(0));
		assertEquals(1,tm.find(a6).size());
	}
	
	public void test30() {
		List<String> l = tm.get(a3); // a3 = ["hello","world"]
		l.add("pineapple");
		l.add("persimmon");
		tm.get(a4).add("yeah!");
		tm.get(a5).addAll(Arrays.asList(a3));
		assertEquals(3,tm.size());
	}
	
	public void test32() {
		List<String> l = tm.get(a0);
		l.add("nothing");
		assertSame(l,tm.get(a0));
		l = tm.get(a1);
		l.add("nada");
		assertEquals("[,,,,,,]",tm.picture());
		assertEquals(2,tm.size());
	}
	
	public void test38() {
		List<String> l = tm.get(a2);
		l.add("peas");
		l = tm.get(a3);
		l.add("Ms.");
		l.add("Piggie");
		l = tm.get(a4);
		l.add("always");
		assertEquals("[hello world,,,,CS 351 is fun!->2,,]",tm.picture());
		l = tm.get(a5);
		l.add("oranges");
		assertEquals(4,tm.size());
		assertEquals("[5->hello world,,,,CS 351 is fun!->2,,]",tm.picture());
		assertEquals("oranges",tm.get(a5).get(0));
		assertEquals("Piggie",tm.get(a3).get(1));
		assertEquals("always",tm.get(a4).get(0));
		assertEquals("peas",tm.get(a2).get(0));
	}

	
	/// test4X: testing removal
	
	public void test40() {
		tm.get(a4).add("apples");
		List<String> l = tm.remove(a4);
		assertEquals("apples",l.get(0));
	}
	
	public void test41() {
		List<String> l = tm.get(a8);
		l.add("2");
		l = tm.remove(a2);
		assertTrue(l == null);
		assertEquals(1,tm.size());
	}
	
	public void test42() {
		List<String> l = tm.get(a3); // a3 = ["hello","world"]
		l.add("Bert");
		assertEquals("[hello world,,,,,,]",tm.picture());
		l = tm.remove(a3);
		assertEquals(1,l.size());
		l = tm.find(a3);
		assertEquals(true, l == null);
		assertEquals("[,,,,,,]",tm.picture());
		l = tm.get(a5); // a5 = ["5"]
		l.add("lemons");
		assertEquals("[5,,,,,,]",tm.picture());
	}
	
	public void test43() {
		tm.get(a2);
		List<String> l = tm.get(a3);
		l.add("Ms.");
		l.add("Piggie");
		l = tm.get(a4);
		l.add("always");
		assertEquals("[hello world,,,,CS 351 is fun!->2,,]",tm.picture());
		tm.remove(a2);
		assertEquals("[hello world,,,,CS 351 is fun!,,]",tm.picture());
		l = tm.get(a5);
		l.add("oranges");
		assertEquals("[5->hello world,,,,CS 351 is fun!,,]",tm.picture());
		assertEquals("oranges",tm.get(a5).get(0));
		assertEquals("Piggie",tm.get(a3).get(1));
		assertEquals("always",tm.get(a4).get(0));
	}
	
	public void test44() {
		List<String> l = tm.get(a8);
		l.add("lucky");
		l = tm.get(a5);
		l.add("apples");
		l = tm.get(a3);
		l.add("Big");
		l.add("Bird");
		tm.remove(a5);
		assertEquals("[hello world,,,88,,,]",tm.picture());
		l = tm.get(a2);
		l.add("wheels");
		//assertEquals("wheels",tm.find(a2).get(0));
		assertEquals("Bird",tm.get(a3).get(1));
		assertEquals("lucky",tm.get(a8).get(0));
		assertEquals("[hello world,,,88,2,,]",tm.picture());
	}
	
	public void test45() {
		tm.get(a2);
		tm.get(a4);
		tm.get(a8);
		assertEquals("[,,,88,CS 351 is fun!->2,,]",tm.picture());
		tm.remove(a8);
		tm.remove(a2);
		assertEquals(1,tm.size());
		tm.get(a6);
		assertEquals("[vi,,,,CS 351 is fun!,,]",tm.picture());
	}
	
	public void test46() {
		tm.get(a4);
		tm.get(a5);
		tm.get(a9);
		assertEquals("[5,,nine,,CS 351 is fun!,,]",tm.picture());
		tm.remove(a9);
		assertEquals(2,tm.size());
		tm.get(a6);
		assertEquals("[vi->5,,,,CS 351 is fun!,,]",tm.picture());
	}
	
	public void test47() {
		List<String> l = tm.get(a6); // a6 = ["vi"]
		l.add("mproved");
		l = tm.get(a5); // a5 = ["5"]
		l.add("pears");
		tm.get(a3); // a3 = ["hello","world"]
		assertEquals("[hello world->5->vi,,,,,,]",tm.picture());
		tm.remove(a6);
		l = tm.get(a5);
		assertEquals("[hello world->5,,,,,,]",tm.picture());
		assertEquals("pears",l.get(0));
		tm.remove(a3);
		tm.remove(a5);
		assertEquals("[,,,,,,]",tm.picture());
		l = tm.get(a9);
		assertEquals("[,,nine,,,,]",tm.picture());
	}
	
	public void test48() {
		tm.get(a3);
		tm.get(a5);
		tm.get(a6);
		tm.remove(a3);
		tm.remove(a5);
		tm.remove(a6);
		assertEquals(0,tm.size());
		assertEquals("[,,,,,,]",tm.picture());
		tm.get(a2);
		assertEquals("[,,,,2,,]",tm.picture());
	}
	
	public void test49() {
		List<String> l = tm.get(a7); // a7 = ["7"]
		l.add("spades");
		l = tm.get(a5); // a5 = ["5"]
		l.add("limes");
		l = tm.get(a3); // a3 = ["hello","world"]
		l.add("Kermit");
		assertEquals("[hello world->5,,7,,,,]",tm.picture());
		tm.remove(a7);
		assertEquals(2,tm.size());
		assertEquals("[hello world->5,,,,,,]",tm.picture());
		l = tm.get(a3);
		assertEquals("Kermit",l.get(0));
		l = tm.get(a9); // a9 = ["nine"]
		assertEquals(0,l.size());
		assertEquals(3,tm.size());
		assertEquals("[hello world->5,,nine,,,,]",tm.picture());
		tm.remove(a5);
		assertEquals("[hello world,,nine,,,,]",tm.picture());
		l = tm.get(a3);
		assertEquals("Kermit",l.get(0));
	}
	
	public void test50() {
		List<String> l = tm.get(a3); // a3 = ["hello","world"]
		l.add("Ernie");
		l = tm.get(a5); // a5 = ["5"]
		l.add("melons");
		assertEquals("[5->hello world,,,,,,]",tm.picture());
		l = tm.remove(a3);
		assertEquals("Ernie",l.get(0));
		assertEquals("[5,,,,,,]",tm.picture());
		l = tm.find(a3);
		assertEquals(true, l == null);
		l = tm.find(a5);
		assertEquals(false, l == null);
		assertEquals("melons",l.get(0));
	}
	
	public void test56() {
		List<String> l = tm.get(a2);
		tm.get(a3);
		tm.get(a4);
		tm.remove(a4);
		assertEquals("[hello world,,,,2,,]",tm.picture());
		tm.get(a8);
		assertEquals("[hello world,,,88,2,,]",tm.picture());
		tm.get(a7);
		tm.get(a4);
		tm.get(a6);
		assertEquals("[,hello world->7,,vi,88,,,,,,,,CS 351 is fun!,2,,,]",tm.picture());
		assertSame(l,tm.remove(a2));
		tm.remove(a3);
		tm.remove(a4);
		tm.remove(a6);
		tm.remove(a7);
		tm.remove(a8);
		assertEquals("[,,,,,,]",tm.picture()); // last removed!
		tm.get(a9);
		assertEquals("[,,nine,,,,]",tm.picture());
	}
	
	public void test63() {
		tm.get(a0);
		tm.get(a1);
		tm.get(a2);
		tm.remove(a2);
		tm.get(a3);
		// NB: a0 and a1 are invisible
		assertEquals("[hello world,,,,,,]",tm.picture());
		tm.get(a4);
		assertEquals("[hello world,,,,CS 351 is fun!,,]",tm.picture());
		tm.get(a5);
		assertEquals("[5->hello world,,,,CS 351 is fun!,,]",tm.picture());
		tm.get(a6);
		assertEquals("[,hello world->,,vi,,,,,,,,,CS 351 is fun!,,,,5]",tm.picture());
		tm.remove(a0);
		tm.remove(a1);
		assertEquals("[,hello world,,vi,,,,,,,,,CS 351 is fun!,,,,5]",tm.picture());
		tm.get(a7);
		assertEquals("[,7->hello world,,vi,,,,,,,,,CS 351 is fun!,,,,5]",tm.picture());
	}
	
	public void test67() {
		tm.get(a0);
		tm.get(a1);
		tm.get(a2);
		tm.get(a3);
		tm.get(a4);
		tm.get(a5);
		tm.get(a6);
		tm.get(a7);
		tm.get(a8);
		tm.remove(a0);
		tm.remove(a1);
		assertEquals("[,7->hello world,,vi,88,,,,,,,,CS 351 is fun!,2,,,5]",tm.picture());
		tm.get(a9);
		assertEquals("[,7->hello world,,vi,nine->88,,,,,,,,CS 351 is fun!,2,,,5]",tm.picture());
	}
	
	public void test69() {
		tm.get(a0);
		tm.get(a1);
		tm.get(a2).add("carrots");
		tm.get(a3);
		tm.get(a4);
		tm.get(a5);
		tm.get(a6);
		tm.get(a7);
		tm.get(a8).add("grapes");
		assertEquals(Arrays.asList("carrots"),tm.find(a2));
		assertEquals(Arrays.asList("grapes"),tm.find(a8));
	}
	
	
	/// test7X: randomKey
	
	public void test70() {
		assertException(IllegalStateException.class, () -> tm.randomKey(random));
	}
	
	public void test71() {
		tm.get(a3).add("kale");
		tm.get(a3.clone()).add("lemons");
		tm.get(a3.clone()).add("graoes");
		String[] key = tm.randomKey(random);
		assertTrue("Expected " + a3 + ", not " + key, Arrays.equals(key,a3));
	}
	
	public void test72() {
		tm.get(a0);
		for (int i=0; i < 1000; ++i) {
			tm.get(a1).add("String #" + i);
		}
		int foundEmpty = 0;
		for (int i=0; i < 1000; ++i) {
			if (tm.randomKey(random).length == 0) ++foundEmpty;
		}
		assertFalse("found a0 too little: " + foundEmpty, foundEmpty < 400);
		assertFalse("found a0 too often: " + foundEmpty, foundEmpty > 600);
	}
	
	public void test73() {
		tm.get(a3); // hello world
		tm.get(a5); // 5
		tm.get(a4); // CS 351 is fun!
		int foundCS = 0;
		for (int i=0; i < 3000; ++i) {
			if (tm.randomKey(random)[0].equals("CS")) ++foundCS;
		}
		assertFalse("found CS too little: " + foundCS, foundCS < 900);
		assertFalse("found CS too often: " + foundCS, foundCS > 1100);
		
	}
	
	public void test74() {
		tm.get(a3);
		String[] key = tm.randomKey(random);
		key[0] = "goodbye";
		key = tm.randomKey(random);
		assertEquals("hello",key[0]);
	}
	
	public void test75() {
		tm.get(a3);
		tm.get(a4);
		tm.get(a5);
		String[] key = tm.randomKey(random);
		switch (key[0]) {
		case "hello":
		case "CS":
		case "5":
			assertTrue("all OK",true);
			break;
		default:
			assertFalse("Bad key returned " + Arrays.toString(key),true);
		}
		tm.remove(a3);
		tm.remove(a4);
		for (int i=0; i<10; ++i) {
			key = tm.randomKey(random);
			assertEquals("5",key[0]);
		}
	}
	
	public void test76() {
		tm.get(a6);
		tm.get(a7);
		tm.get(a9);
		tm.remove(a7);
		tm.remove(a6);
		tm.remove(a9);
		assertException(IllegalStateException.class, () -> tm.randomKey(random));
	}
	
	public void test77() {
		List<String> l = tm.get(a4); // a4 = ["CS","351","is","fun!"]
		l.add("and");
		l.add("hard");
		String[] key = tm.randomKey(random);
		assertEquals(4,key.length);
		assertEquals("is",key[2]);
		key[2] = "isn't";
		assertEquals("is",a4[2]);
		l = tm.get(a4);
		assertEquals(2,l.size());
		assertEquals(1,tm.size());
	}
}
