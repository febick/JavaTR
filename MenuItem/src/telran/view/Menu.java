package telran.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Menu implements Item {

	private String name;
	private ArrayList<Item> items;

	public Menu(String name, ArrayList<Item> items) {
		this.name = name;
		this.items = items;
	}
	
	public Menu(String name, Item ...items) {
		this(name, new ArrayList<Item>(Arrays.asList(items)));
	}
	
	@Override
	public String displayName() {
		return name;
	}

	@Override
	public void perform(InputOutput io) {
		displayTitle(io);
		while (true) {
			displayItems(io);
			int itemNumber = io.readInt("Select action item.\n", 1, items.size());
			Item item = items.get(itemNumber - 1);
			try {
				item.perform(io);
				if (item.isExit()) { break; }
			} catch (Throwable e) {
				io.writeObjectLine(e.getLocalizedMessage());
			}
		}
	}

	private void displayTitle(InputOutput io) {
		io.writeObjectLine(name);
		io.writeObjectLine("_".repeat(20));
	}
	
	private void displayItems(InputOutput io) {
		IntStream.range(0, items.size())
			.forEach(i -> io.writeObjectLine(String.format("%d. %s", i + 1, items.get(i).displayName())));
	}

	@Override
	public Boolean isExit() {
		return false;
	}

}
