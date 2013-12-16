package MasterPaper.Code;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import org.apache.log4j.Logger;
import edu.stanford.nlp.trees.TypedDependency;

public class HelperMethods {
	private static Logger LOGGER = Logger.getLogger(HelperMethods.class);

	static String getStructure() {
		StringBuilder structure = new StringBuilder("");
		try {
			FileInputStream fstream = new FileInputStream(
					"C:\\Users\\Prateek\\workspace\\masterThesis\\data\\test\\1.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strline = null;

			while ((strline = br.readLine()) != null) {
				String temp = strline.replace("(", "");
				temp = temp.replace(")", "");
				temp = temp.trim();

				if (HelperMethods.isPresentAdjective(temp.trim())
						&& HelperMethods.isPresentNouns(temp.trim())) {
					String[] arr = temp.split(" ");
					for (int i = 0; i < arr.length; i++) {
						if (gettingNouns.sentence.contains(arr[i])
								&& !arr[i].equals(",")) {
							if (!arr[i].equals(".")) {
								structure.append(arr[i - 1]).append(" ")
										.trimToSize();
							} else {
								structure.append("+");
							}
						}
					}
				}
			}
			in.close();
		} catch (Throwable ex) {
			LOGGER.error("Error reading file " + ex.toString()
					+ new java.util.Date());
		}
		LOGGER.info("getStructure method ran successfully on "
				+ new java.util.Date());
		return structure.toString();
	}

	static boolean isPresentAdjective(String input) {
		try {
			for (String i : gettingNouns.adjective) {
				if (input.contains(i)) {
					return true;
				}
			}
		} catch (Exception ex) {
			LOGGER.error("Something went wrong in isPresentAdjective"
					+ ex.toString());
		}
		LOGGER.info("isPresentAdjective method executed successfully");
		return false;
	}

	static boolean isPresentNouns(String input) {
		try {
			for (String i : gettingNouns.nouns) {
				if (input.contains(i)) {
					return true;
				}
			}
		} catch (Exception ex) {
			LOGGER.error("Something went wrong in isPresentNouns"
					+ ex.toString());
		}
		LOGGER.info("isPresentNouns method executed successfully");
		return false;
	}

	static List<List<TypedDependency>> getDependency() {
		List<List<TypedDependency>> tdl = gettingNouns.read();
		return tdl;
	}

	static boolean isComponent(String input) {
		boolean isComponent = false;
		try {

			FileInputStream fstream = new FileInputStream(
					"C:\\Users\\Prateek\\workspace\\masterThesis\\data\\test\\ComponentDatabase.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strline = null;
			while ((strline = br.readLine()) != null) {
				if (strline.equals(input)) {
					isComponent = true;
					break;
				}
			}
			br.close();
		} catch (Throwable ex) {
			LOGGER.error("Something went wrong while reading component database");
		}
		return isComponent;
	}

	static String isNegative(List<TypedDependency> tdl) {
		StringBuffer output = new StringBuffer();
		String dependencyString = tdl.toString().replace(",", "")
				.replace("(", " ").replace(")", " ").replace("-", " ");
		String[] arr = dependencyString.split(" ");
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals("neg")) {
				output.append(arr[i + 1]);// Check if we need to add governor or
											// dependent here
			}
		}
		return output.toString();
	}
}
