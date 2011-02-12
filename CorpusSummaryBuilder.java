<<<<<<< HEAD
import java.io.*;
import processor.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class reads in one or more input files, and generates a Hidden Markov Model
 * representing the structure of the words in the files.  The Markov Model is then
 * processed so that it will be ready to output to XML.  Each edge in the model is
 * given a probability, and each node is given a new, unique number so that it does
 * not take up as much space on output.  The markov model is then written to a file
 * @author Joe Montgomery, Justin Brickell
 */
public class CorpusSummaryBuilder
{
    private static final int DEFAULT_SEQUENCE_LENGTH = 2;  // Default number of tokens in a sequence
    private static final String DEFAULT_OUTPUT_FILE = "summary.xml";  // Default file to write XML to
    private MarkovChain summary = new MarkovChain();  // The hidden Markov Model
    private AsciiParse parser;
    private StatusBar statusBar;
    private Wc wordCounter = new Wc();
    private int totalBadFileNames = 0;
    private static final int ILLEGAL_INPUT_FILE = 0, ILLEGAL_K_FORMAT = 1, ILLEGAL_K_VALUE = 2, NO_K_VALUE = 3,
	NO_OUTPUT_VALUE = 4, NO_INPUT = 5, NO_CORRECT_FILES = 6, ILLEGAL_INPUT_FILE_INVIS = 7;

    /**
     * Reads the filenames from the String array, starting at the given position in the array.  Each
     * file is read using the word counter from HW0, which returns the number of tokens in the file.
     * The total number of tokens in all of the files is then returned.  The method also keeps track
     * of any invalid file names in the string array.  An error is printed out for each invalid name,
     * and a count is kept of the total number of bad files.
     * @return int the number of tokens in the input files
     * @exception throws IOException
     */
    public int countTokens(String[] args, int startingPosition) throws IOException
    {
	String input;
	int totalTokens = 0;
	for (int i = startingPosition; i < args.length; i++) {
	    input = args[i];
	    try {
		totalTokens= totalTokens + wordCounter.wordCount(input);
	    }
	    catch (IOException e) {
		errorMessages(ILLEGAL_INPUT_FILE, input);
		totalBadFileNames += 1;
	    }
	}
	return totalTokens;
    }

    /**
     * Builds up the Markov Model by calling addToSummary on each of the input files.  Errors are 
     * reported for any files that can not be opened.  Also closes down the status bar once the
     * Markov Model is finished being built.
     * @exception throws IOException
     */
    public void buildSummary(String[] args, int startingPosition, int sequenceLength) throws IOException
    {
	String input;
	summary.insertSequence(new Sequence(MarkovChain.start,""),MarkovChain.start);
	for (int i = startingPosition; i < args.length; i++) {
	    input = args[i];
	    try {
		parser  = new AsciiParse(input);
		addToSummary(sequenceLength); 
	    }
	    catch (IOException e) {
		errorMessages(ILLEGAL_INPUT_FILE_INVIS, input);
	    }
	}
	statusBar.closeStatusBar();
    }

    /**
     * Adds the file which the parser is currently processing to the Hidden Markov Model.
     * Each token from the parser is added to the Markov Model, updating the strength of links
     * and generating new nodes as necessary.  The method does this by keeping track of three
     * values; the K-1 tokens that identify the previous node in the model, the K-1 tokens that
     * identify the node that is being added, and the token that is the token to actually add
     * to the model.  Each iteration of the while loop adds one token in this manner, and then
     * moves each value forward by one so that the next token can be added.  Each time this method
     * is called it will also update the statusBar.
     * @exception throws IOException
     */
    public void addToSummary(int sequenceLength) throws IOException 
    {
	String token = parser.nextToken();         // current token, value of the current node
	String firstToken;
	boolean isStart = true;
	String currentString = "";
	String priorString;
	int tokenCount = 0;
	
	while (token != null) {
	    if (isStart) {
		summary.insertSequence(MarkovChain.start,token,"");
		isStart = false;
		currentString = token;
		token = parser.nextToken();
		tokenCount++;
	    }
	    else {
		priorString = currentString;
		currentString = currentString + " " + token;
		tokenCount++;
		if (tokenCount < sequenceLength) {
		    firstToken = "";
		}
		else if (tokenCount == sequenceLength) {
		    firstToken = currentString.substring(0,currentString.indexOf(" "));
		}
		else {
		    currentString = currentString.substring(currentString.indexOf(" ")+1);
		    firstToken = currentString.substring(0,currentString.indexOf(" "));
		}
		summary.insertSequence(priorString, currentString, firstToken);
		token = parser.nextToken();
		statusBar.updateStatusBar();
	    }
	}

	while (currentString != MarkovChain.finish) {
	    priorString = currentString;
	    int spaceindex = currentString.indexOf(" ");
	    if (spaceindex == -1) {
		firstToken = currentString;
		currentString = MarkovChain.finish;
	    }
	    else {
		firstToken = currentString.substring(0,spaceindex);
		currentString = currentString.substring(spaceindex +1);

	    }
	    summary.insertSequence(priorString,currentString,firstToken);
			
	}
	summary.insertSequence(MarkovChain.finish,MarkovChain.start,"");
    }

    /**
     * Puts the finishing touches on the Markov Model.  Each edge is given a probability, and
     * sequence keys are shortened to long ints.
     * @exception throws IOException
     */
    public void finalizeSummary() throws IOException
    {
	summary.setProbabilities();
	summary.implementIDs();
    }
    
    /**
     * Writes out the XML summary of a corpus of text to the specified file.
     * @param output the name of the output file to write to
     * @exception throws IOException, FileNotFoundException
     */
    public void outputXML(String output) throws IOException, FileNotFoundException
    {
	System.out.println("Writing to File (may take a few seconds).");
	Writer out = new BufferedWriter(new OutputStreamWriter
	    (new FileOutputStream(output)));
	out.write("<corpus>\n\n");
	out.write(summary.outputXML());
	out.write("</corpus>");
	out.close();
    }

    /**
     * Prints out any error messages from the SummaryBuilder to Standard Error.  Error messages deal
     * exclusively with invalid or ill-formated command line arguments, or with files that were specified
     * by the user and can not be found.
     * @param i the type of error message to throw
     * @param type an optional string to be used in the error message
     */
    private void errorMessages(int i, String type) {
	String str;
	
	switch (i) {
	case ILLEGAL_INPUT_FILE:
	    str = "Could not find file: " + type + ".";
	    break;
	case ILLEGAL_K_FORMAT:
	    str = "Error: Invalid K format entered. K must be a positive, non-zero integer. " + 
		"Using the default value of K, which is K = " + DEFAULT_SEQUENCE_LENGTH + ".";
	    break;
	case ILLEGAL_K_VALUE:
	    str = "Error: Invalid K value entered. K must be a positive, non-zero integer. " + 
		"Using the default value of K, which is K = " + DEFAULT_SEQUENCE_LENGTH + ".";
	    break;
	case NO_K_VALUE:
	    str = "Error: No -k value entered.  The K value should be a positive, non-zero integer. " +
		"Using the default value of K, which is K = " + DEFAULT_SEQUENCE_LENGTH + ".";
	    break;
	case NO_OUTPUT_VALUE:
	    str = "Error: No -o value entered.  The -o value should be the file to output to. " +
		"Using the default output file, " + DEFAULT_OUTPUT_FILE + ".";
	    break;
	case NO_INPUT:
	    str = "Error: No input files specified.";
	    break;
	case NO_CORRECT_FILES:
	    str = "Error: None of the input files could be found.";
	    break; 
	case ILLEGAL_INPUT_FILE_INVIS:
	    str = "\b";
	    break;
	default:  
	    str = "Unknown Error in CorpusSummaryBuilder.  Please call the programmer at 713-348-7140 for" +
		" technical assistance.";
	}
	System.err.println("\n" + str);
    }
    
    /**
     * The main method for the first part of the disassociative text generator.
     * It first reads in input parameters, such as the length of the identifying
     * sequences and the input files to read (the corpus).  It then checks the
     * number of tokens in the corpus, so that the progress in processing the corpus
     * can be measured and printed out.  After that the corpus is actually read in and
     * processed, generating a Hidden Markov Model (or at least the skeleton of one, it does not
     * have probabilities on the edges at this point).  This model is then modified to make
     * it ready for output.  Based on the statistics gathered while generating the Model,
     * each edge between the nodes is given a probability.  Also, each node in Model is given
     * a new unique identifier, a number from 0 to the number of unique number of K-1 sequences.
     * This is so that the full K-1 length identifier of each node does not have to written out.
     * Once this processing is done, the Markov Model is written out to XML in the format
     * specified by the README and the spec.
     * @param String the command line arguments
     * @exception throws IOException, FileNotFoundException
     */
    public static void main(String[] args) throws IOException, FileNotFoundException
    {
	CorpusSummaryBuilder csb= new CorpusSummaryBuilder();
	String output  = DEFAULT_OUTPUT_FILE;
	int sequenceLength = DEFAULT_SEQUENCE_LENGTH;     // Number of tokens in a sequence
	int positionInArgs = 0; // current position in the args
	boolean crash = false;
	
	/* If there is a new length k for sequences, read it in. */
	if ((args.length > positionInArgs) && (args[positionInArgs].equals("-k"))) {
	    if (args.length > positionInArgs+1) {
		try {
		    sequenceLength = Integer.parseInt(args[(positionInArgs + 1)]);
		}
		catch (NumberFormatException e) {
		    csb.errorMessages(ILLEGAL_K_FORMAT, "");
		    sequenceLength = DEFAULT_SEQUENCE_LENGTH;
		}
		positionInArgs = positionInArgs + 2;
		if (sequenceLength <= 0) {
		    csb.errorMessages(ILLEGAL_K_VALUE, "");
		    sequenceLength = DEFAULT_SEQUENCE_LENGTH;
		}
	    }
	    else {
		csb.errorMessages(NO_K_VALUE, "");
	    }
	}

	/* If there is a output file name, read it in. */
	if ((args.length > positionInArgs) && (args[positionInArgs].equals("-o"))) {
	    if (args.length > positionInArgs+1) {
		output = args[(positionInArgs + 1)];
		positionInArgs = positionInArgs + 2;
	    }
	    else {
		csb.errorMessages(NO_OUTPUT_VALUE, "");
	    }
	}
	
	if (positionInArgs >= args.length) {
	    csb.errorMessages(NO_INPUT, "");
	    crash = true;
	}

	if (!crash) {
	    /* Count the total number of tokens in the corpus, and start the statusBar */
	    csb.statusBar = new StatusBar(csb.countTokens(args, positionInArgs));

	    if (csb.totalBadFileNames < (args.length - positionInArgs)) {
	    
		/* Build up the skeleton of the Hidden Markov Model from the corpus. */
		csb.buildSummary(args, positionInArgs, sequenceLength);
		
		/* Flesh out the Hidden Markov Model by adding probabilites and shorter ID's. */
		csb.finalizeSummary();
		
		/* Write out the XML file */
		csb.outputXML(output);
	    }
	    else {
		csb.errorMessages(NO_CORRECT_FILES, "");
	    }
	}
    }
}
=======
import java.io.*;import processor.*;import javax.swing.*;import java.awt.*;import java.awt.event.*;/** * This class reads in one or more input files, and generates a Hidden Markov Model * representing the structure of the words in the files.  The Markov Model is then * processed so that it will be ready to output to XML.  Each edge in the model is * given a probability, and each node is given a new, unique number so that it does * not take up as much space on output.  The markov model is then written to a file * @author Joe Montgomery, Justin Brickell */public class CorpusSummaryBuilder{    private static final int DEFAULT_SEQUENCE_LENGTH = 2;  // Default number of tokens in a sequence    private static final String DEFAULT_OUTPUT_FILE = "summary.xml";  // Default file to write XML to    private MarkovChain summary = new MarkovChain();  // The hidden Markov Model    private AsciiParse parser;    private StatusBar statusBar;    private Wc wordCounter = new Wc();    private int totalBadFileNames = 0;    private static final int ILLEGAL_INPUT_FILE = 0, ILLEGAL_K_FORMAT = 1, ILLEGAL_K_VALUE = 2, NO_K_VALUE = 3,	NO_OUTPUT_VALUE = 4, NO_INPUT = 5, NO_CORRECT_FILES = 6, ILLEGAL_INPUT_FILE_INVIS = 7;    /**     * Reads the filenames from the String array, starting at the given position in the array.  Each     * file is read using the word counter from HW0, which returns the number of tokens in the file.     * The total number of tokens in all of the files is then returned.  The method also keeps track     * of any invalid file names in the string array.  An error is printed out for each invalid name,     * and a count is kept of the total number of bad files.     * @return int the number of tokens in the input files     * @exception throws IOException     */    public int countTokens(String[] args, int startingPosition) throws IOException    {	String input;	int totalTokens = 0;	for (int i = startingPosition; i < args.length; i++) {	    input = args[i];	    try {		totalTokens= totalTokens + wordCounter.wordCount(input);	    }	    catch (IOException e) {		errorMessages(ILLEGAL_INPUT_FILE, input);		totalBadFileNames += 1;	    }	}	return totalTokens;    }    /**     * Builds up the Markov Model by calling addToSummary on each of the input files.  Errors are      * reported for any files that can not be opened.  Also closes down the status bar once the     * Markov Model is finished being built.     * @exception throws IOException     */    public void buildSummary(String[] args, int startingPosition, int sequenceLength) throws IOException    {	String input;	summary.insertSequence(new Sequence(MarkovChain.start,""),MarkovChain.start);	for (int i = startingPosition; i < args.length; i++) {	    input = args[i];	    try {		parser  = new AsciiParse(input);		addToSummary(sequenceLength); 	    }	    catch (IOException e) {		errorMessages(ILLEGAL_INPUT_FILE_INVIS, input);	    }	}	statusBar.closeStatusBar();    }    /**     * Adds the file which the parser is currently processing to the Hidden Markov Model.     * Each token from the parser is added to the Markov Model, updating the strength of links     * and generating new nodes as necessary.  The method does this by keeping track of three     * values; the K-1 tokens that identify the previous node in the model, the K-1 tokens that     * identify the node that is being added, and the token that is the token to actually add     * to the model.  Each iteration of the while loop adds one token in this manner, and then     * moves each value forward by one so that the next token can be added.  Each time this method     * is called it will also update the statusBar.     * @exception throws IOException     */    public void addToSummary(int sequenceLength) throws IOException     {	String token = parser.nextToken();         // current token, value of the current node	String firstToken;	boolean isStart = true;	String currentString = "";	String priorString;	int tokenCount = 0;		while (token != null) {	    if (isStart) {		summary.insertSequence(MarkovChain.start,token,"");		isStart = false;		currentString = token;		token = parser.nextToken();		tokenCount++;	    }	    else {		priorString = currentString;		currentString = currentString + " " + token;		tokenCount++;		if (tokenCount < sequenceLength) {		    firstToken = "";		}		else if (tokenCount == sequenceLength) {		    firstToken = currentString.substring(0,currentString.indexOf(" "));		}		else {		    currentString = currentString.substring(currentString.indexOf(" ")+1);		    firstToken = currentString.substring(0,currentString.indexOf(" "));		}		summary.insertSequence(priorString, currentString, firstToken);		token = parser.nextToken();		statusBar.updateStatusBar();	    }	}	while (currentString != MarkovChain.finish) {	    priorString = currentString;	    int spaceindex = currentString.indexOf(" ");	    if (spaceindex == -1) {		firstToken = currentString;		currentString = MarkovChain.finish;	    }	    else {		firstToken = currentString.substring(0,spaceindex);		currentString = currentString.substring(spaceindex +1);	    }	    summary.insertSequence(priorString,currentString,firstToken);				}	summary.insertSequence(MarkovChain.finish,MarkovChain.start,"");    }    /**     * Puts the finishing touches on the Markov Model.  Each edge is given a probability, and     * sequence keys are shortened to long ints.     * @exception throws IOException     */    public void finalizeSummary() throws IOException    {	summary.setProbabilities();	summary.implementIDs();    }        /**     * Writes out the XML summary of a corpus of text to the specified file.     * @param output the name of the output file to write to     * @exception throws IOException, FileNotFoundException     */    public void outputXML(String output) throws IOException, FileNotFoundException    {	System.out.println("Writing to File (may take a few seconds).");	Writer out = new BufferedWriter(new OutputStreamWriter	    (new FileOutputStream(output)));	out.write("<corpus>\n\n");	out.write(summary.outputXML());	out.write("</corpus>");	out.close();    }    /**     * Prints out any error messages from the SummaryBuilder to Standard Error.  Error messages deal     * exclusively with invalid or ill-formated command line arguments, or with files that were specified     * by the user and can not be found.     * @param i the type of error message to throw     * @param type an optional string to be used in the error message     */    private void errorMessages(int i, String type) {	String str;		switch (i) {	case ILLEGAL_INPUT_FILE:	    str = "Could not find file: " + type + ".";	    break;	case ILLEGAL_K_FORMAT:	    str = "Error: Invalid K format entered. K must be a positive, non-zero integer. " + 		"Using the default value of K, which is K = " + DEFAULT_SEQUENCE_LENGTH + ".";	    break;	case ILLEGAL_K_VALUE:	    str = "Error: Invalid K value entered. K must be a positive, non-zero integer. " + 		"Using the default value of K, which is K = " + DEFAULT_SEQUENCE_LENGTH + ".";	    break;	case NO_K_VALUE:	    str = "Error: No -k value entered.  The K value should be a positive, non-zero integer. " +		"Using the default value of K, which is K = " + DEFAULT_SEQUENCE_LENGTH + ".";	    break;	case NO_OUTPUT_VALUE:	    str = "Error: No -o value entered.  The -o value should be the file to output to. " +		"Using the default output file, " + DEFAULT_OUTPUT_FILE + ".";	    break;	case NO_INPUT:	    str = "Error: No input files specified.";	    break;	case NO_CORRECT_FILES:	    str = "Error: None of the input files could be found.";	    break; 	case ILLEGAL_INPUT_FILE_INVIS:	    str = "\b";	    break;	default:  	    str = "Unknown Error in CorpusSummaryBuilder.  Please call the programmer at 713-348-7140 for" +		" technical assistance.";	}	System.err.println("\n" + str);    }        /**     * The main method for the first part of the disassociative text generator.     * It first reads in input parameters, such as the length of the identifying     * sequences and the input files to read (the corpus).  It then checks the     * number of tokens in the corpus, so that the progress in processing the corpus     * can be measured and printed out.  After that the corpus is actually read in and     * processed, generating a Hidden Markov Model (or at least the skeleton of one, it does not     * have probabilities on the edges at this point).  This model is then modified to make     * it ready for output.  Based on the statistics gathered while generating the Model,     * each edge between the nodes is given a probability.  Also, each node in Model is given     * a new unique identifier, a number from 0 to the number of unique number of K-1 sequences.     * This is so that the full K-1 length identifier of each node does not have to written out.     * Once this processing is done, the Markov Model is written out to XML in the format     * specified by the README and the spec.     * @param String the command line arguments     * @exception throws IOException, FileNotFoundException     */    public static void main(String[] args) throws IOException, FileNotFoundException    {	CorpusSummaryBuilder csb= new CorpusSummaryBuilder();	String output  = DEFAULT_OUTPUT_FILE;	int sequenceLength = DEFAULT_SEQUENCE_LENGTH;     // Number of tokens in a sequence	int positionInArgs = 0; // current position in the args	boolean crash = false;		/* If there is a new length k for sequences, read it in. */	if ((args.length > positionInArgs) && (args[positionInArgs].equals("-k"))) {	    if (args.length > positionInArgs+1) {		try {		    sequenceLength = Integer.parseInt(args[(positionInArgs + 1)]);		}		catch (NumberFormatException e) {		    csb.errorMessages(ILLEGAL_K_FORMAT, "");		    sequenceLength = DEFAULT_SEQUENCE_LENGTH;		}		positionInArgs = positionInArgs + 2;		if (sequenceLength <= 0) {		    csb.errorMessages(ILLEGAL_K_VALUE, "");		    sequenceLength = DEFAULT_SEQUENCE_LENGTH;		}	    }	    else {		csb.errorMessages(NO_K_VALUE, "");	    }	}	/* If there is a output file name, read it in. */	if ((args.length > positionInArgs) && (args[positionInArgs].equals("-o"))) {	    if (args.length > positionInArgs+1) {		output = args[(positionInArgs + 1)];		positionInArgs = positionInArgs + 2;	    }	    else {		csb.errorMessages(NO_OUTPUT_VALUE, "");	    }	}		if (positionInArgs >= args.length) {	    csb.errorMessages(NO_INPUT, "");	    crash = true;	}	if (!crash) {	    /* Count the total number of tokens in the corpus, and start the statusBar */	    csb.statusBar = new StatusBar(csb.countTokens(args, positionInArgs));	    if (csb.totalBadFileNames < (args.length - positionInArgs)) {	    		/* Build up the skeleton of the Hidden Markov Model from the corpus. */		csb.buildSummary(args, positionInArgs, sequenceLength);				/* Flesh out the Hidden Markov Model by adding probabilites and shorter ID's. */		csb.finalizeSummary();				/* Write out the XML file */		csb.outputXML(output);	    }	    else {		csb.errorMessages(NO_CORRECT_FILES, "");	    }	}    }}
>>>>>>> 35a35230355cd1cd9deb61925155eef54330b5eb
