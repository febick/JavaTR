package telran.view;

import java.io.*;

public class ConsoleInputOutput implements InputOutput {

	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	@Override
	public String readString(String prompt) {
		writeObject(prompt);
		String result = null;
		try {
			result = reader.readLine();
		} catch (IOException e) { }
		return result;
	}

	@Override
	public void writeObject(Object object) {
		System.out.print(object);
	}

}
