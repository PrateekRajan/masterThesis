package MasterPaper.Code;

import java.util.List;

import edu.stanford.nlp.trees.TypedDependency;

public class CommaSeperatedSentences implements Hierarchy {

	@Override
	public void createHierarchy(String structure, List<TypedDependency> tdl) {
		// get all the nouns preceding ',' in the original
		String compositeFeature = HelperMethods.getComposite1(tdl);
		System.out.println("The composite Feature from comma seperated " + compositeFeature);

	}

}
