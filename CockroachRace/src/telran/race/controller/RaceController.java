package telran.race.controller;

import java.io.*;
import java.time.Instant;
import java.util.*;

import telran.race.Racer;

public class RaceController {

	public static int distance;
	private static int countOfRacers;
	private static List<Racer> racers;
	private static final List<Racer> results = new LinkedList<>();
	public static Instant startTime;

	
	public static void main(String[] args) {
		loadProperties(args);
		startRace();
	}
	
	private static void loadProperties(String[] args) {
		var props = new Properties();
		var filePath = args.length > 0 ? args[0] : "race.properties";
		try {
			props.load(new FileInputStream(filePath));
			distance = Integer.parseInt(props.getProperty("distance"));
			countOfRacers = Integer.parseInt(props.getProperty("racers"));
			racers = new LinkedList<>();
		} catch (FileNotFoundException e) {
			System.out.println("Race can't start: Properties file not found");
		} catch (IOException e) {
			System.out.println("Race can't start: " + e.getLocalizedMessage());
		}
	}
	
	private static void startRace() {
		prepareRacers();
		run();
		showWinner();
	}
	
	private static void prepareRacers() {
		for (int i = 1; i <= countOfRacers; i++) {
			racers.add(new Racer(i));
		}
	}
	
	private static void run()  {
		startTime = Instant.now();
		racers.forEach(racer -> {
			racer.start();
		});
		racers.forEach(racer -> {
			try {
				racer.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});	
	}
	
	public static void finish(Racer racer) {
		synchronized (results) {
			results.add(racer);
		}
	}
		
	private static void showWinner() {
		System.out.println("Place\tRacer\tTime");
		for (int i = 1; i <= countOfRacers; i++) {
			System.out.printf("  %d\t%s", i, results.get(i - 1).showTime());
		}
	}	
}
