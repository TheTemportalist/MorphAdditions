package com.countrygamer.morphactions.common.lib;

public class JsonHelper {

	public static String toReadableString(String jsonString) {
		String ret = "";

		char[] characters = jsonString.toCharArray();

		boolean isInString = false;

		int tabSet = 0;
		for (char c : characters) {
			if (c == '}' || c == ']') {
				tabSet--;
				ret += '\n';
				for (int tab = 1; tab <= tabSet; tab++) {
					ret += "  ";
				}
			}

			ret += c;

			if (c == '{' || c == '[') {
				tabSet++;
				ret += '\n';

				for (int tab = 1; tab <= tabSet; tab++) {
					ret += "  ";
				}
			}

			if (c == ':') {
				ret += " ";
			}

			if (c == '"') {
				isInString = !isInString;
			}

			if (c == ',' && !isInString) {
				ret += '\n';

				for (int tab = 1; tab <= tabSet; tab++) {
					ret += "  ";
				}
			}
		}

		return ret;
	}

}
