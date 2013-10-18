package MasterPaper.Code;

import java.util.List;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import edu.stanford.nlp.trees.TypedDependency;

public class SimpleSentences extends gettingNouns {

	private static Logger LOGGER = Logger.getLogger(SimpleSentences.class);

	/**
	 * @brief Based on the structure of the sentence corresponding
	 *        createHierarchy method would be called
	 */
	public void createHierarchy() {
		// TO-DO: Check the sentence structure using the 1.txt file contents
		List<TypedDependency> tdl = gettingNouns.read();
		System.out.println(tdl);
		try {
			FileInputStream fstream = new FileInputStream(
					"C:\\Users\\Prateek\\workspace\\masterThesis\\data\\test\\1.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strline = null;
			Vector<String> v = new Vector<String>(10, 5);
			while ((strline = br.readLine()) != null) {
				String temp = strline.replace("(", "");
				temp = temp.replace(")", "");
				// temp = temp.replace(" ", "");
				temp = temp.trim();
				String[] arr = temp.split(" ");

				System.out.print(temp);
			}
			in.close();
		} catch (Throwable ex) {
			LOGGER.error("Error reading file " + ex.toString()
					+ new java.util.Date());
		}

	}

	public static void main(String args[]) {
		SimpleSentences obj = new SimpleSentences();
		obj.createHierarchy();
	}
}
