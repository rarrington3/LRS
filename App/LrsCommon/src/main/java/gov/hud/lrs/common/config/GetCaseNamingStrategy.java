package gov.hud.lrs.common.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.PropertyNamingStrategyBase;

@SuppressWarnings("serial")
public class GetCaseNamingStrategy extends PropertyNamingStrategyBase {

	@Override
	public String translate(String propertyName) {
		if (propertyName == null) {
			return propertyName; // garbage in, garbage out
		}
		int length = propertyName.length();
		StringBuilder result = new StringBuilder(length * 2);
		int resultLength = 0;
		boolean wasPrevUnderscore = false;
		for (int i = 0; i < length; i++) {
			char c = propertyName.charAt(i);
			if (c == '_') {
				wasPrevUnderscore = true;
			} else {
				if (wasPrevUnderscore) {
					result.append(Character.toUpperCase(c));
					resultLength++;
					wasPrevUnderscore = false;
				} else {
					result.append(c);
					resultLength++;
				}
			}
		}
		return resultLength > 0 ? result.toString() : propertyName;
	}

}
