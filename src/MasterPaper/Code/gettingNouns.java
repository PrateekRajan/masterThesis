package MasterPaper.Code;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;

import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;

public class gettingNouns {
	private static Logger LOGGER = Logger.getLogger(gettingNouns.class);

	static String[] temp = new String[100];
	static ArrayList<String> nouns = new ArrayList<String>();
	static ArrayList<String> adjective = new ArrayList<String>();

	static String sentence = "";
	static List<List<TypedDependency>> gdependency = new LinkedList<List<TypedDependency>>();

	public static List<List<TypedDependency>> read() {
		List<TypedDependency> tdl = null;
		int index = -1;
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
						// nouns.add(j, temp[i + 1]);
						nouns.add(temp[i + 1]);

						j++;
					}
					if (temp[i].contains("JJ")) {
						// adjective.add(l, temp[i + 1]);
						adjective.add(temp[i + 1]);
						l++;
					}

				}
			}
			System.out.println("The Nouns in the sentence are :");
			for (int k = 0; k < nouns.size(); k++) {
				if (nouns.get(k) != null) {
					System.out.println(nouns.get(k));// Displaying nouns
				}
			}
			System.out.println("The Adjectives in the sentence are :");
			for (l = 0; l < adjective.size(); l++) {
				if (adjective.get(l) != null) {
					System.out.println(adjective.get(l));// Displaying
															// adjectives.
				}
			}
			br.close();
		} catch (Throwable ex) {
			LOGGER.error("Something went wrong while extracting nouns and adjectives "
					+ ex.toString() + new java.util.Date());
		}
		LOGGER.info("Nouns and adjectives extracted successfully "
				+ new java.util.Date());
		gdependency = prune(nouns, adjective);
		return gdependency;
	}

	private static List<List<TypedDependency>> prune(ArrayList<String> nouns,
			ArrayList<String> adjective) {
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
				sentence = sentence + temp;
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
					for (int k = 0; k < nouns.size(); k++) {
						if (nouns.get(k) != null
								&& nouns.get(k)
										.trim()
										.equalsIgnoreCase(secondSplit[j].trim())) {
							countnouns = 1;
							break;
						}
					}
					for (int l = 0; l < adjective.size(); l++) {
						if (adjective.get(l) != null
								&& adjective
										.get(l)
										.trim()
										.equalsIgnoreCase(secondSplit[j].trim())) {
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
						TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer
								.factory(new CoreLabelTokenFactory(), "");
						List<CoreLabel> rawWords2 = tokenizerFactory
								.getTokenizer(new StringReader(sent2))
								.tokenize();
						// System.out.println("Raw words " + rawWords2);
						Tree parse = lp.apply(rawWords2.subList(1,
								rawWords2.size() - 1));
						TreebankLanguagePack tlp = new PennTreebankLanguagePack();
						GrammaticalStructureFactory gsf = tlp
								.grammaticalStructureFactory();
						GrammaticalStructure gs = gsf
								.newGrammaticalStructure(parse);
						tdl = (List<TypedDependency>) gs
								.typedDependenciesCCprocessed();
						System.out.println("I am from getting nouns " + tdl);
						// IN here add TDL to a list and traverse over the list
						// to get the individual sentence structures and
						gdependency.add(tdl);
					}
				}
			}
		} catch (Throwable ex) {
			LOGGER.error("Something went wrong while pruning the sentences -->"
					+ ex.toString() + new java.util.Date());
		}
		LOGGER.info("Sentence pruning done successfully -->"
				+ new java.util.Date());

		return gdependency;
	}
}
