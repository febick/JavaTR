import java.io.*;

public class LineOrientedCopyAppl {

	private static final String CONSOLE = "console";

	public static void main(String[] args) {
		
		if (args.length < 2) {
			System.out.println("Too few argumetns.");
			return;
		}

		if (args[0].equals(CONSOLE)) {
			getConsoleInput(args[1]);
		} else {
			try {
				getFileInput(args[0], args[1]);
			} catch (FileNotFoundException e) {
				System.out.println(e.getLocalizedMessage());
			}
		}
		
	}
	
	private static void getConsoleInput(String output) {
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		getOutput(console, output);
	}
	
	private static void getFileInput(String input, String output) throws FileNotFoundException {
		BufferedReader fileReader = new BufferedReader(new FileReader(input));
		getOutput(fileReader, output);
	}
	
	private static void getOutput(BufferedReader bufferReader, String output) {
		try (PrintWriter writer = new PrintWriter(output)) {
			while (true) {
				String line = bufferReader.readLine();
				if (line == null) { break;}
				if (output.equals(CONSOLE)) {
					System.out.println(line);
				} else {
					writer.println(line);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
	

}
