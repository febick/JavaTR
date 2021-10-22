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
		var lastRound = RaceController.distance - 1;
		for (int i = 0; i < RaceController.distance; i++) {
			if (RaceController.isRaceActive) {
				System.out.println("🪳 " + number + " (round " + i + ")");
				if (i == lastRound) { setLeader(); }
				setDelay();
			} else {
				break;
			}
		}
	}
	
	private void setLeader() {
		RaceController.winner = number;
		RaceController.finish();
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
