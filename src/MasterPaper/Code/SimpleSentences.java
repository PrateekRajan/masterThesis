package MasterPaper.Code;

import java.util.List;
import org.apache.log4j.Logger;
import edu.stanford.nlp.trees.TypedDependency;

public class SimpleSentences extends gettingNouns implements Hierarchy {

	private static Logger LOGGER = Logger.getLogger(SimpleSentences.class);

	/**
	 * @brief This method creates the hierarchy for sentences having a simple NN
	 *        POS NN Structure
	 * @param structure
	 * @param tdl
	 */
	@Override
	public void createHierarchy(String structure, List<TypedDependency> tdl) {
		Boolean isComposite = false;
		String compositeFeature = null;

		String dependencyString = tdl.toString().replace(",", "")
				.replace("(", " ").replace(")", " ").replace("-", " ");
		String[] arr = dependencyString.split(" ");
		for (int i = 0; i < arr.length; i++) {
			// First Check for composite features and handle them
			if (arr[i].equals("nn")) {
				for (int j = 0; j < arr.length; j++) {
					if ((arr[j].equals("prep_of") && arr[i + 1]
							.equals(arr[j + 1]))
							|| (arr[j].equals("poss") && arr[i + 1]
									.equals(arr[j + 1]))) {
						compositeFeature = arr[i + 3] + " " + arr[i + 1];
						SharedLists.features.add(compositeFeature);
						SharedLists.parents.add(arr[j + 3]);
						isComposite = true;
					}
				}
			}
		}
		if (isComposite) {
			for (int i = 0; i < arr.length; i++) {
				if (arr[i].equals("nn")) {
					for (int j = 0; j < arr.length; j++) {
						if (arr[j].equals("nsubj")
								&& arr[j + 3].equals(arr[i + 1])) {
							SharedLists.opinions.add(arr[j + 1]);
						}
					}
				}
			}
		} else {
			for (int first_element = 0; first_element < arr.length; first_element++) {
				if (arr[first_element].equals("nsubj")) {
					for (int second_element = 0; second_element < arr.length; second_element++) {
						if (arr[second_element].equals("prep_of")
								&& arr[second_element + 1]
										.equals(arr[first_element + 3])
								|| (arr[second_element].equals("poss") && arr[second_element + 1]
										.equals(arr[first_element + 3]))) {
							SharedLists.parents.add(arr[second_element + 3]);
							SharedLists.features.add(arr[first_element + 3]);
							SharedLists.opinions.add(arr[first_element + 1]);
						}
					}
				}

			}

		}

	}
}
