package telran.race;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import telran.race.controller.RaceController;

public class Racer extends Thread {
	private static final int MIN = 1;
	private static final int MAX = 4;
	private int range = MAX - MIN + 1;
	private Random randomGenerator = new Random();
	private int number;
	private Instant finishTime;
	
	public Racer(int number) {
		super();
		this.number = number;
	}
	
	@Override
	public void run() {
		var lastRound = RaceController.distance;
		for (int i = 0; i <= lastRound; i++) {
				setDelay();
				System.out.println("🪳 " + number + " (round " + i + ")");
				if (i == lastRound) { finishLine(); }
		}
	}
	
	private void finishLine() {
		finishTime = Instant.now();
		RaceController.finish(this);
	}
	
	private void setDelay() {
		int randomDelay = randomGenerator.nextInt(range) + MIN;
		try {
			sleep(randomDelay);
		} catch (InterruptedException e) {
			throw new IllegalStateException();
		}
	}
	
	public String showTime() {
		var time = ChronoUnit.MILLIS.between(RaceController.startTime, finishTime);
		return String.format("🪳%d\t%d mls.\n", number, time);
	}

}
