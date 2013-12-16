masterThesis
============

Steps to regenerate:

1. Download Stanford parser from http://nlp.stanford.edu/software/lex-parser.shtml#Download
2. To generate the POS tagged structure and gramatical dependency structure used by this system run stanford-parser.jar
3. Input your review and load the englishFactored.ser.gz model from the stanford-parser-x.x.x-model.jar (x.x.x is your version of stanford parser).
4. Run the parser and save the output (POS tagged structure) as 1.txt in the masterThesis/data/test folder.
5. Save the raw review as test.txt in masterThesis/data/test folder.
6. Run the FinalRun.java.
