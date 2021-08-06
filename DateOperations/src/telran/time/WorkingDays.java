package telran.time;

import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class WorkingDays implements TemporalAdjuster {

	int amountOfWorkingDays;
	int[] daysOffValues;
	
	public WorkingDays(int amountOfWorkingDays, int[] daysOffValues) {
		this.amountOfWorkingDays = amountOfWorkingDays;
		this.daysOffValues = daysOffValues;
	}
	
	@Override
	public Temporal adjustInto(Temporal temporal) {
		int count = 0;
		while (count != amountOfWorkingDays) {
			temporal = temporal.plus(1, ChronoUnit.DAYS);
			if (!isWeekend(temporal, daysOffValues)) { count++; }
		}
		return temporal;
	}
	
	private boolean isWeekend(Temporal temporal, int[] daysOffValues) {
		int current = temporal.get(ChronoField.DAY_OF_WEEK);
		for (int i : daysOffValues) {
			if (current == i) { return true; }
		}
		return false;
	}

}
