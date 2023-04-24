package edu.uwm.cs351;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;


public class Travesty {
	// TODO: pick fields that will enable you to create a travesty
	private final int contextSize;
    private final Random rand;
    private final Map<List<String>, List<String>> map;
    private List<String> context;
	
	/**
	 * Create travesty generator that handles context of a given size.
	 * For a context of size zero, the result are just random words from the
	 * source document without context.
	 * @param n number of words of context, must be non-negative.
	 */
	public Travesty(int n) {
		if (n < 0) {
            throw new IllegalArgumentException("n must be non-negative");
        }
        contextSize = n;
        rand = new Random();
        map = new HashMap<>();
        context = new ArrayList<>();
	}
	
	private static void shift(String[] array, String w) {
		if (array.length == 0) return;
		for (int i=0; i < array.length-1; ++i) {
			array[i] = array[i+1];
		}
		array[array.length-1]= w;
	}
	
	/**
	 * Train the travesty generator with another String.
	 * @param x next string from input, must not be null
	 */
	public void add(String x) {
		if (x == null) {
            throw new IllegalArgumentException("input string cannot be null");
        }
        String[] words = x.split("\\s+");
        for (String w : words) {
            if (context.size() < contextSize) {
                context.add(w);
            } else {
                List<String> key = new ArrayList<>(context);
                List<String> value = map.getOrDefault(key, new ArrayList<>());
                value.add(w);
                map.put(key, value);
                context.remove(0);
                context.add(w);
            }
        }
	}
	
	/**
	 * Start a new text to train the same travesty.
	 * The previous data is remembered, but we don't correct the
	 * last words of the previous text with the first words of this text.
	 */
	public void restart() {
		map.clear();
        context.clear();
	}
	
	/**
	 * Generate a travesty from the data read in so far.
	 * The travesty will continue indefinitely,
	 * but will end if the travesty cannot continue.
	 * @param c consumer to send strings to.
	 */
	public void generate(Consumer<String> c) {
		List<String> key = new ArrayList<>(contextSize);
        for (int i = 0; i < contextSize; i++) {
            key.add("");
        }
        while (true) {
            List<String> value = map.get(key);
            if (value == null || value.isEmpty()) {
                break;
            }
            String next = value.get(rand.nextInt(value.size()));
            c.accept(next + " ");
            shift(key.toArray(new String[0]), next);
        }
	}
	
	private static final int MAX_WORDS = 1000;
	private static final int LINE_WIDTH = 80;
	
	private static void usage() {
		System.err.println("usage: java edu.uwm.cs351.Travesty <n> <filename>");
		System.err.println("  <n> -- number of words of context (2 is good).");
		System.err.println("  <filename> -- text file of text to travesty");
		System.exit(1);
	}
	
	public static void main(String... args) {
		if (args.length != 2) {
			usage();
		}
		int n = 0;
		try {
			n = Integer.parseInt(args[0]);
		} catch (RuntimeException ex) {
			usage();
		}
		if (n < 0) usage();
		Travesty t = new Travesty(n);
		try {
			BufferedReader br = new BufferedReader(new FileReader(args[1]));
			String line;
			while ((line = br.readLine())!= null) {
				String[] words = line.split(" ");
				for (String w : words) {
					t.add(w);
				}
			}
			br.close();
		} catch (FileNotFoundException ex) {
			System.err.println("Cannot open " + args[1] + ": " + ex.getLocalizedMessage());
		} catch (IOException ex) {
			System.err.println("Problem reading " + args[1] + ": " + ex.getLocalizedMessage());
		}
		t.generate(new Consumer<String>() {
			int i = 0;
			int w = 0;
			public void accept(String s) {
				if (i > MAX_WORDS) System.exit(0);
				if (w > 0) {
					if (w + s.length() + 1 >= LINE_WIDTH) {
						System.out.println();
						w = 0;
					} else {
						System.out.print(" ");
						++w;
					}
				}	
				System.out.print(s);
				w += s.length();
			}
		});
	}
}
