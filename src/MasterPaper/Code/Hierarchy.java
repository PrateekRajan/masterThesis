package MasterPaper.Code;

import java.util.List;

import edu.stanford.nlp.trees.TypedDependency;

public interface Hierarchy {
	void createHierarchy(String structure, List<TypedDependency> tdl);
}
