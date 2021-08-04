package telran.messure;

public class Lenght implements Comparable<Lenght>{
	float number;
	LenghtUnit unit;
	
	Lenght(float number, LenghtUnit unit) {
		this.number = number;
		this.unit = unit;
	}

	@Override
	public int compareTo(Lenght o) {
		var o1 = convert(LenghtUnit.MM);
		var o2 = o.convert(LenghtUnit.MM);
		if (o1.getLenght() < o2.getLenght()) { return -1; }
		if (o1.getLenght() > o2.getLenght()) { return 1; }
		return 0;
	}
	
	public float getLenght() {
		return number;
	}
	
	public Lenght convert(LenghtUnit unit) {
		return new Lenght(number * this.unit.getValue() / unit.getValue(), unit);
	}
}
