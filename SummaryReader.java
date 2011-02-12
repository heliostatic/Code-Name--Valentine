<<<<<<< HEAD
import java.util.*;
import java.io.*;
import processor.*;
import XMLparse.*;
import parser.*;
import nrparser.*;

/**
 * This class reads in the XML representation of a Hidden Markov Model, and
 * creates a Hidden Markov Model from it.  The requested number of paragraphs are
 * then written out by taking a random walk through the Markov Model.  The command line
 * structure for this class's main method is
 * "java SummaryReader -l -p <number of paragraphs to generate> -i <input XML file> -o <output ASCII file>"
 * Each of these arguments are optional.  The -l argument will tell SummaryReader to use the non-recursive
 * parser, which is needed when processing extremely large inpout XML files.  The disadvantage to the
 * non-recursive parser is that it is not quite as robust as the default, recursive parser.
 */
public class SummaryReader
{
    private MarkovChain summary = new MarkovChain();
    private static final int DEFAULT_PARAGRAPH_LENGTH = 3;
    private static final String DEFAULT_INPUT_FILE = "summary.xml";
    private static final String DEFAULT_OUTPUT_FILE = "distext.txt";
    private static final int NO_P_VALUE = 0, NO_I_VALUE = 1, NO_O_VALUE = 2,
	ILLEGAL_P_FORMAT = 3, UNNEEDED_ARGS = 4, INVALID_INPUT_FILE = 5;

    /**
     * Reads in an XML summary of a Hidden Markov Model.
     * @param inputFile the filename of the XML file to read
     * @param nrParser whether to use the non-recursive parser to build the Model
     * @exception IOException
     */
    public void readSummary(String inputFile, boolean nrParser) throws IOException 
    {
	if (!nrParser) {
	    XMLText xmlText;
	    XMLparse.TokenList temp = new XMLparse.TokenList(inputFile);
	    xmlText = XMLText.read(temp);
	    xmlText.execute(ParseVisitor.Singleton,summary);
	}
	else {
	    IFParser parser = new IFParser(inputFile);
	    Sequence nextSequence = parser.nextSequence();
	    while (nextSequence != null) {
		summary.insertSequence(nextSequence, nextSequence.getKey());
		nextSequence = parser.nextSequence();
	    }
	}
    }
    
    /**
     * Outputs dis-associative ASCII text from the generated Hidden Markov Model to the
     * given output file.  The amount of output will depend upon the number of paragraphs requested.
     * @param outputFile the name of the file to output the generate text to.
     * @param paragraphs the number of paragraphs of dis-associative text to generate.
     * @exception throws IOException, FileNotFoundException
     */
    public void outputASCII(String outputFile, int paragraphs) throws IOException, FileNotFoundException
    {
	Writer out = new BufferedWriter(new OutputStreamWriter
	    (new FileOutputStream(outputFile)));
	String outputText;
	outputText = summary.outputASCII(paragraphs);
	out.write(outputText);
	out.close();
    }

    /**
     * Prints out any error messages from the SummaryReader to Standard Error.  Error messages deal
     * exclusively with invalid or ill-formated command line arguments, or with files that were specified
     * by the user and can not be found.
     * @param i the type of error message to throw
     * @param type an optional string to be used in the error message
     */
    private void errorMessages(int i, String type) {
	String str;
	
	switch (i) {
	case NO_P_VALUE:
	    str = "Error: No -p value entered.  The -p value is the number of paragraphs to print out" +
		" and should be a positive, non-zero int.  Using the default -p value of " +
		DEFAULT_PARAGRAPH_LENGTH + ".";
	    break;
	case NO_I_VALUE:
	    str = "Error: No -i value entered.  The -i value is the input file to read from." +
		" Using the default -i value of " + DEFAULT_INPUT_FILE + ".";
		
	    break;
	case NO_O_VALUE:
	    str = "Error: No -o value entered.  The -o value is the output file to read from." +
		" Using the default -o value of " + DEFAULT_OUTPUT_FILE + ".";
	    break;
	case ILLEGAL_P_FORMAT:
	    str = "Error: Incorrect format for -p value entered. The -p value is the number of paragraphs to print out" +
		" and should be a positive, non-zero int.  Using the default -p value of " +
		DEFAULT_PARAGRAPH_LENGTH + ".";
	    break;
	case UNNEEDED_ARGS:
	    str = "Error: Too many command line arguements entered.  The last " + type + " arguments were ignored.";
	    break;
	case INVALID_INPUT_FILE:
	    str = "Error: The input XML file " + type + " could not be opened.";
	    break;
	default:  
	    str = "Unknown Error in SummaryReader.  Please call the programmer at 713-348-7140 for" +
		" technical assistance.";
	}
	System.err.println("\n" + str);
    }
    
    /**
     * Parses an input summary file, and returns writes dis-associative text to an output file.
     * @param String the command line arguments
     * @exception throws IOException, FileNotFoundException
     */
    public static void main(String[] args) throws IOException, FileNotFoundException
    {
	SummaryReader sr= new SummaryReader();
	int positionInArgs = 0; // current position in the args
        int paragraphs = DEFAULT_PARAGRAPH_LENGTH;
	String output = DEFAULT_OUTPUT_FILE;
	String input = DEFAULT_INPUT_FILE;
	boolean nrParser = false;

	/* Check whether the user wants to use the non-recursive parser*/
	if ((args.length > positionInArgs) && (args[positionInArgs].equals("-l"))) {
	    nrParser = true;
	    positionInArgs = positionInArgs + 1;
	}
	
	/* If there is a new length k for sequences, read it in */
	if ((args.length > positionInArgs) && (args[positionInArgs].equals("-p"))) {
	    if (args.length > positionInArgs+1) {
		try {
		    paragraphs = new Integer(args[(positionInArgs + 1)]).intValue();
		}
		catch (NumberFormatException e) {
		    sr.errorMessages(ILLEGAL_P_FORMAT, "");
		}
		positionInArgs = positionInArgs + 2;
	    }
	    else {
		sr.errorMessages(NO_P_VALUE, "");
	    }
	}

	/* If there is a input file name, read it in */
	if ((args.length > positionInArgs) && (args[positionInArgs].equals("-i"))) {
	    if (args.length > positionInArgs+1) {
		input = args[(positionInArgs + 1)];
		positionInArgs = positionInArgs + 2;
	    }
	    else {
		sr.errorMessages(NO_I_VALUE, "");
	    }
	}
	
	/* If there is a output file name, read it in */
	if ((args.length > positionInArgs) && (args[positionInArgs].equals("-o"))) {
	    if (args.length > positionInArgs+1) {
		output = args[(positionInArgs + 1)];
		positionInArgs = positionInArgs + 2;
	    }
	    else {
		sr.errorMessages(NO_O_VALUE, "");
	    }
	}

	if (args.length != positionInArgs) {
	    sr.errorMessages(UNNEEDED_ARGS, (new Integer(args.length-positionInArgs)).toString());
	}
	
	/* Read the input file and make a Markov Chain of it*/
	try {
	    sr.readSummary(input, nrParser);
	    sr.outputASCII(output, paragraphs);
	}
	catch (FileNotFoundException e) {
	    sr.errorMessages(INVALID_INPUT_FILE, "");
	}
    }
}
=======
import java.util.*;import java.io.*;import processor.*;import XMLparse.*;import parser.*;import nrparser.*;/** * This class reads in the XML representation of a Hidden Markov Model, and * creates a Hidden Markov Model from it.  The requested number of paragraphs are * then written out by taking a random walk through the Markov Model.  The command line * structure for this class's main method is * "java SummaryReader -l -p <number of paragraphs to generate> -i <input XML file> -o <output ASCII file>" * Each of these arguments are optional.  The -l argument will tell SummaryReader to use the non-recursive * parser, which is needed when processing extremely large inpout XML files.  The disadvantage to the * non-recursive parser is that it is not quite as robust as the default, recursive parser. */public class SummaryReader{    private MarkovChain summary = new MarkovChain();    private static final int DEFAULT_PARAGRAPH_LENGTH = 3;    private static final String DEFAULT_INPUT_FILE = "summary.xml";    private static final String DEFAULT_OUTPUT_FILE = "distext.txt";    private static final int NO_P_VALUE = 0, NO_I_VALUE = 1, NO_O_VALUE = 2,	ILLEGAL_P_FORMAT = 3, UNNEEDED_ARGS = 4, INVALID_INPUT_FILE = 5;    /**     * Reads in an XML summary of a Hidden Markov Model.     * @param inputFile the filename of the XML file to read     * @param nrParser whether to use the non-recursive parser to build the Model     * @exception IOException     */    public void readSummary(String inputFile, boolean nrParser) throws IOException     {	if (!nrParser) {	    XMLText xmlText;	    XMLparse.TokenList temp = new XMLparse.TokenList(inputFile);	    xmlText = XMLText.read(temp);	    xmlText.execute(ParseVisitor.Singleton,summary);	}	else {	    IFParser parser = new IFParser(inputFile);	    Sequence nextSequence = parser.nextSequence();	    while (nextSequence != null) {		summary.insertSequence(nextSequence, nextSequence.getKey());		nextSequence = parser.nextSequence();	    }	}    }        /**     * Outputs dis-associative ASCII text from the generated Hidden Markov Model to the     * given output file.  The amount of output will depend upon the number of paragraphs requested.     * @param outputFile the name of the file to output the generate text to.     * @param paragraphs the number of paragraphs of dis-associative text to generate.     * @exception throws IOException, FileNotFoundException     */    public void outputASCII(String outputFile, int paragraphs) throws IOException, FileNotFoundException    {	Writer out = new BufferedWriter(new OutputStreamWriter	    (new FileOutputStream(outputFile)));	String outputText;	outputText = summary.outputASCII(paragraphs);	out.write(outputText);	out.close();    }    /**     * Prints out any error messages from the SummaryReader to Standard Error.  Error messages deal     * exclusively with invalid or ill-formated command line arguments, or with files that were specified     * by the user and can not be found.     * @param i the type of error message to throw     * @param type an optional string to be used in the error message     */    private void errorMessages(int i, String type) {	String str;		switch (i) {	case NO_P_VALUE:	    str = "Error: No -p value entered.  The -p value is the number of paragraphs to print out" +		" and should be a positive, non-zero int.  Using the default -p value of " +		DEFAULT_PARAGRAPH_LENGTH + ".";	    break;	case NO_I_VALUE:	    str = "Error: No -i value entered.  The -i value is the input file to read from." +		" Using the default -i value of " + DEFAULT_INPUT_FILE + ".";			    break;	case NO_O_VALUE:	    str = "Error: No -o value entered.  The -o value is the output file to read from." +		" Using the default -o value of " + DEFAULT_OUTPUT_FILE + ".";	    break;	case ILLEGAL_P_FORMAT:	    str = "Error: Incorrect format for -p value entered. The -p value is the number of paragraphs to print out" +		" and should be a positive, non-zero int.  Using the default -p value of " +		DEFAULT_PARAGRAPH_LENGTH + ".";	    break;	case UNNEEDED_ARGS:	    str = "Error: Too many command line arguements entered.  The last " + type + " arguments were ignored.";	    break;	case INVALID_INPUT_FILE:	    str = "Error: The input XML file " + type + " could not be opened.";	    break;	default:  	    str = "Unknown Error in SummaryReader.  Please call the programmer at 713-348-7140 for" +		" technical assistance.";	}	System.err.println("\n" + str);    }        /**     * Parses an input summary file, and returns writes dis-associative text to an output file.     * @param String the command line arguments     * @exception throws IOException, FileNotFoundException     */    public static void main(String[] args) throws IOException, FileNotFoundException    {	SummaryReader sr= new SummaryReader();	int positionInArgs = 0; // current position in the args        int paragraphs = DEFAULT_PARAGRAPH_LENGTH;	String output = DEFAULT_OUTPUT_FILE;	String input = DEFAULT_INPUT_FILE;	boolean nrParser = false;	/* Check whether the user wants to use the non-recursive parser*/	if ((args.length > positionInArgs) && (args[positionInArgs].equals("-l"))) {	    nrParser = true;	    positionInArgs = positionInArgs + 1;	}		/* If there is a new length k for sequences, read it in */	if ((args.length > positionInArgs) && (args[positionInArgs].equals("-p"))) {	    if (args.length > positionInArgs+1) {		try {		    paragraphs = new Integer(args[(positionInArgs + 1)]).intValue();		}		catch (NumberFormatException e) {		    sr.errorMessages(ILLEGAL_P_FORMAT, "");		}		positionInArgs = positionInArgs + 2;	    }	    else {		sr.errorMessages(NO_P_VALUE, "");	    }	}	/* If there is a input file name, read it in */	if ((args.length > positionInArgs) && (args[positionInArgs].equals("-i"))) {	    if (args.length > positionInArgs+1) {		input = args[(positionInArgs + 1)];		positionInArgs = positionInArgs + 2;	    }	    else {		sr.errorMessages(NO_I_VALUE, "");	    }	}		/* If there is a output file name, read it in */	if ((args.length > positionInArgs) && (args[positionInArgs].equals("-o"))) {	    if (args.length > positionInArgs+1) {		output = args[(positionInArgs + 1)];		positionInArgs = positionInArgs + 2;	    }	    else {		sr.errorMessages(NO_O_VALUE, "");	    }	}	if (args.length != positionInArgs) {	    sr.errorMessages(UNNEEDED_ARGS, (new Integer(args.length-positionInArgs)).toString());	}		/* Read the input file and make a Markov Chain of it*/	try {	    sr.readSummary(input, nrParser);	    sr.outputASCII(output, paragraphs);	}	catch (FileNotFoundException e) {	    sr.errorMessages(INVALID_INPUT_FILE, "");	}    }}
>>>>>>> 35a35230355cd1cd9deb61925155eef54330b5eb
