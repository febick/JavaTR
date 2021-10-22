package telran.race.controller;

import java.io.*;
import java.util.*;
import telran.race.Racer;

public class RaceController {

	public static int distance;
	public static int winner;
	private static int countOfRacers;
	private static List<Racer> racers;
	
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
		finish();
	}
	
	private static void prepareRacers() {
		for (int i = 1; i <= countOfRacers; i++) {
			racers.add(new Racer(i));
		}
	}
	
	private static void run()  {
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
	
	private static void finish() {
		System.out.printf("\nCockroach number %d won!", winner);
	}
	
}
