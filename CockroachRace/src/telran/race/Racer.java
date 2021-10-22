package telran.race;

import java.util.Random;

import telran.race.controller.RaceController;

public class Racer extends Thread {
	private static final int MIN = 1;
	private static final int MAX = 4;
	private int range = MAX - MIN + 1;
	private Random randomGenerator = new Random();
	private int number;
	
	public Racer(int number) {
		super();
		this.number = number;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < RaceController.distance; i++) {
				setLeader();
				setDelay();
		}
	}
	
	private void setLeader() {
		System.out.println("🪳 - " + number);
		RaceController.winner = number;
	}
	
	private void setDelay() {
		int randomDelay = randomGenerator.nextInt(range) + MIN;
		try {
			sleep(randomDelay);
		} catch (InterruptedException e) {
			System.out.println("Interrupted");
		}
	}

}
