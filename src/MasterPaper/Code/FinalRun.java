package MasterPaper.Code;

import java.util.List;

import edu.stanford.nlp.trees.TypedDependency;

/**
 * 
 * @author Prateek
 * @brief This class accepts the sentences and based on the structure of the
 *        sentence calls the appropriate algorithm to generate hierarchy
 * 
 */
public class FinalRun {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean hierarchy_generated = false;
		Hierarchy obj = null;
		List<List<TypedDependency>> tdl = HelperMethods.getDependency();
		String structure = HelperMethods.getStructure();
		structure = structure.replaceAll("\\++", "\\+").trim();
		String[] structure_arr = structure.split("\\+");
		System.out.println("Structure is " + structure.toString());

		for (int i = 0; i <= structure_arr.length - 1; i++) {
			if (structure_arr[i].contains("NN NN CC NN")
					&& hierarchy_generated == false) {
				obj = new CommaSeperatedSentences();
				obj.createHierarchy(structure_arr[i], tdl.get(i));
				hierarchy_generated = true;
			}
			if ((structure_arr[i].trim().contains("NN VBZ JJ") || structure_arr[i]
					.trim().contains("NN VBZ RB JJ"))
					&& hierarchy_generated == false) {
				obj = new SimpleSentences();
				obj.createHierarchy(structure_arr[i], tdl.get(i));
				hierarchy_generated = true;
			}
		}

		System.out.println("Features " + SharedLists.features.toString());
		System.out.println("Opinions " + SharedLists.opinions.toString());
		System.out.println("Parents " + SharedLists.parents.toString());

		System.out.println("Below is the Hierarchy for input review");
		for (int i = 0; i < SharedLists.parents.size(); i++) {
			System.out.println("For sentence " + (i + 1)
					+ " the hierarchy is :");
			System.out.println("Parent ---> " + SharedLists.parents.get(i));
			if (!HelperMethods.isComponent(SharedLists.features.get(i))) {
				System.out.println("Feature ---> "
						+ SharedLists.features.get(i));
			} else {
				System.out.println("Component ---> "
						+ SharedLists.features.get(i));
			}
			System.out.println("Opinions ---> " + SharedLists.opinions.get(i));
		}
	}
}
