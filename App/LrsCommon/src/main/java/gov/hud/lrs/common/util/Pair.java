package gov.hud.lrs.common.util;

public class Pair<FirstType, SecondType> {

	public FirstType first;
	public SecondType second;

	public static <F, S> Pair<F, S> create(F first, S second) {
		return new Pair<F, S>(first, second);
	}

	public Pair(FirstType first, SecondType second) {
		this.first = first;
		this.second = second;
	}

}
