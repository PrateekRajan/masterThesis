package MasterPaper.Code;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;

import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;

public class gettingNouns {
	private static Logger LOGGER = Logger.getLogger(gettingNouns.class);

	static String[] temp = new String[100];
	static String[] nouns = new String[10];
	static String[] adjective = new String[10];

	public static List<TypedDependency> read() {
		List<TypedDependency> tdl = null;
		try {
			int j = 0, l = 0;

			FileInputStream fi = new FileInputStream(
					"C:\\Users\\Prateek\\workspace\\masterThesis\\data\\test\\1.txt");// You
																						// can
																						// change
																						// the
																						// path
																						// according
																						// to
																						// your
																						// location
																						// of
																						// the
																						// 1.
																						// txt
																						// file
			DataInputStream ds = new DataInputStream(fi);
			BufferedReader br = new BufferedReader(new InputStreamReader(ds));
			String str;

			while ((str = br.readLine()) != null) {
				str = str.replace("(", "");
				str = str.replace(")", "");
				temp = str.split(" ");

				for (int i = 0; i <= temp.length - 1; i++) {
					if (temp[i].contains("NN")) {
						nouns[j] = temp[i + 1];
						j++;
					}
					if (temp[i].contains("JJ")) {
						adjective[l] = temp[i + 1];
						l++;
					}

				}
			}
			System.out.println("The Nouns in the sentence are :");
			for (int k = 0; k < nouns.length - 1; k++) {
				if (nouns[k] != null) {
					System.out.println(nouns[k]);// Displaying nouns
				}
			}
			System.out.println("The Adjectives in the sentence are :");
			for (l = 0; l < adjective.length - 1; l++) {
				if (adjective[l] != null) {
					System.out.println(adjective[l]);// Displaying adjectives.
				}
			}
			br.close();
		} catch (Throwable ex) {
			LOGGER.error("Something went wrong while extracting nouns and adjectives "
					+ ex.toString() + new java.util.Date());
		}
		LOGGER.info("Nouns and adjectives extracted successfully "
				+ new java.util.Date());
		tdl = prune(nouns, adjective);
		return tdl;
	}

	private static List<TypedDependency> prune(String[] nouns,
			String[] adjective) {
		String temp;
		List<TypedDependency> tdl = null;
		String[] firstSplit = new String[50];
		String[] secondSplit = new String[50];
		int countnouns = 0;
		int countadjective = 0;
		int counttotal;
		LexicalizedParser lp = LexicalizedParser
				.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");// Creating
		// an
		// instance
		// of
		// the
		// parser
		// model
		// to
		// be
		// used
		// .

		try {
			FileInputStream fi = new FileInputStream(
					"C:\\Users\\Prateek\\workspace\\masterThesis\\data\\test\\test.txt");// You
																							// can
																							// change
																							// the
																							// path
																							// according
																							// to
																							// your
																							// location
																							// of
																							// the
																							// test
																							// .
																							// txt
																							// file
			DataInputStream di = new DataInputStream(fi);
			BufferedReader br = new BufferedReader(new InputStreamReader(di));
			while ((temp = br.readLine()) != null) {
				firstSplit = temp.split("\\.");// Splitting the paragraph into
				// sentences and storing them
				// into an array named
				// firstSplit.

			}
			br.close();

			for (int i = 0; i < firstSplit.length; i++) {

				if (firstSplit[i] != null) {

				}

				secondSplit = firstSplit[i].trim().split(" ");// Splitting the
				// individual
				// sentences
				// down to
				// words.

				for (int j = 0; j < secondSplit.length; j++) {
					for (int k = 0; k < nouns.length; k++) {
						if (nouns[k] != null
								&& nouns[k].trim().equalsIgnoreCase(
										secondSplit[j].trim())) {
							countnouns = 1;
							break;
						}
					}
					for (int l = 0; l < adjective.length; l++) {
						if (adjective[l] != null
								&& adjective[l].trim().equalsIgnoreCase(
										secondSplit[j].trim())) {
							countadjective = 1;
							break;
						}
					}
				}
				counttotal = countnouns + countadjective;
				if (counttotal == 2) {
					System.out.println(Arrays.deepToString(secondSplit));
					if (secondSplit.length > 0) {
						String sent2 = Arrays.deepToString(secondSplit)
								.replaceAll(",", "");
						edu.stanford.nlp.process.TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer
								.factory(new CoreLabelTokenFactory(), "");
						List<CoreLabel> rawWords2 = tokenizerFactory
								.getTokenizer(new StringReader(sent2))
								.tokenize();
						Tree parse = lp.apply(rawWords2);

						TreebankLanguagePack tlp = new PennTreebankLanguagePack();
						GrammaticalStructureFactory gsf = tlp
								.grammaticalStructureFactory();
						GrammaticalStructure gs = gsf
								.newGrammaticalStructure(parse);
						// List<TypedDependency> tdl = gs
						// .typedDependenciesCCprocessed();
						tdl = (List<TypedDependency>) gs
								.typedDependenciesCollapsed();
					}
				}
			}
		} catch (Throwable ex) {
			LOGGER.error("Something went wrong while pruning the sentences -->"
					+ ex.toString() + new java.util.Date());
		}
		LOGGER.info("Sentence pruning done successfully -->"
				+ new java.util.Date());

		return tdl;
	}

}
