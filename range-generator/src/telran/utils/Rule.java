package telran.utils;

public interface Rule {
	void check(int  number, int min, int max) throws NoRuleMatchException;
}
