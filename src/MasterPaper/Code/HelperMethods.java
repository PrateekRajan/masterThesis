package MasterPaper.Code;

public class HelperMethods {

	static boolean isPresentAdjective(String input) {
		for (String i : gettingNouns.adjective) {
			if (i.equals(input.trim())) {
				return true;
			}
		}
		return false;
	}

	static boolean isPresentNouns(String input) {
		for (String i : gettingNouns.nouns) {
			if (i.equals(input.trim())) {
				return true;
			}
		}
		return false;
	}
}
