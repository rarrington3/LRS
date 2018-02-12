package gov.hud.lrs.common.util;

public class JpqlBuilder<FirstType, SecondType> {

	public FirstType first;
	public SecondType second;

	public static <F, S> JpqlBuilder<F, S> create(F first, S second) {
		return new JpqlBuilder<F, S>(first, second);
	}

	public JpqlBuilder(FirstType first, SecondType second) {
		this.first = first;
		this.second = second;
	}

}
