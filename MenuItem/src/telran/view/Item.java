package telran.view;

import java.util.function.Consumer;

public interface Item {

	String displayName();
	void perform(InputOutput io);
	Boolean isExit();
	
	static Item of(String name, Consumer<InputOutput> action, boolean flExit) {
		return new Item() {
			@Override
			public void perform(InputOutput io) {
				action.accept(io);
			}
			@Override
			public Boolean isExit() {
				return flExit;
			}
			@Override
			public String displayName() {
				return name;
			}
		};
	}
	
	static Item of(String name, Consumer<InputOutput> action) { 
		return of(name, action, false);
	} 
	
	static Item exit() {
		return of("Exit", io -> {}, true);
	} 
	
	static Item back() {
		return of("Back to previous", io -> {}, true);
	} 
	
}
