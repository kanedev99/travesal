import java.io.File;

import edu.uwm.cs351.Travesty;
public class Main {
	public static void main(String[] args) {
		String arg;
		if (args.length > 0) arg = args[0];
		else arg = "lib" + File.separator + "biden.txt";
		Travesty.main("2",arg);
	}
}
