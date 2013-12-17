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
		try {
			String compositeFeature = HelperMethods.getComposite(tdl);
			String dependencyString = tdl.toString().replace(",", "")
					.replace("(", " ").replace(")", " ").replace("-", " ");
			String[] arr = dependencyString.split(" ");
			for (int first_element = 0; first_element < arr.length; first_element++) {
				if (arr[first_element].equals("nsubj")) {
					for (int second_element = 0; second_element < arr.length; second_element++) {
						if (arr[second_element].equals("prep_of")
								&& arr[second_element + 1]
										.equals(arr[first_element + 3])
								|| (arr[second_element].equals("poss") && arr[second_element + 1]
										.equals(arr[first_element + 3]))) {
							SharedLists.parents.add(arr[second_element + 3]);
							if (!compositeFeature.isEmpty()) {
								SharedLists.features.add(compositeFeature);
							} else {
								SharedLists.features
										.add(arr[first_element + 3]);
							}
							if (HelperMethods.isNegative(tdl).trim()
									.equals(arr[first_element + 1])) {
								SharedLists.opinions.add("not "
										+ arr[first_element + 1]);
							} else {
								SharedLists.opinions
										.add(arr[first_element + 1]);
							}
							SharedLists.opinions.add(arr[first_element + 1]);
						}
					}
				}

				LOGGER.info("Simple sentence Hierarchy generated successfully");

			}
		} catch (Throwable ex) {
			LOGGER.error("Something went wrong while creating hierarchy for simple sentence "
					+ ex.toString());
		}
	}
}
