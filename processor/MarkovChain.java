<<<<<<< HEAD
package processor;
import java.util.*;

public class MarkovChain
{
    private static final int LETTERS_PER_LINE = 80;   // Number of letters per line for ASCII
    public static final String start = "&start";
    public static final String finish = "&finish";
    private Hashtable sequences = new Hashtable();

    /**
     * Inserts the given sequence into the hash table using the given string.
     * @param sequence the sequence we wish to insert into the hashtable
     * @param key the key we will use to store said sequence
     */
    public void insertSequence(Sequence sequence, String key)
    {
	sequences.put(key,sequence);
    }

    /**
     * Inserts the sequence with key currentKey into the hashtable,
     * and additionally inserts a link to this sequence from the sequence
     * with key priorSequence.
     * @param priorKey the key of the sequence seen just before the current
     * @param currentKey the key of the current sequence
     * @param word the word that the current sequence will output
     */
    public void insertSequence(String priorKey, String currentKey, String word)
    {
	Sequence priorSequence;
	if (!(sequences.containsKey(currentKey))) {
	    sequences.put(currentKey, new Sequence(currentKey, word));
	}
	priorSequence = (Sequence)sequences.get(priorKey);	
	priorSequence.insertLink(currentKey);
    }
    
    /**
     * Concatenates calls to Sequence.outputXML for each member of sequences.
     * @return an XML representation of the entire MarkovChain
     */
    public String outputXML()
    {
	Enumeration elements = sequences.elements();
	Sequence element;
	StringBuffer xmlText = new StringBuffer();
	while (elements.hasMoreElements()) {
	     xmlText = xmlText.append(((Sequence)elements.nextElement()).outputXML() + " ");
	}
	return xmlText.toString();
    }

    /**
     * Returns the the requested number of paragraphs of ASCII text generated from the
     * Markov Model.  The text is generated by taking a random walk through the model, using the
     * probability of each edge to decide where to go next.  Output is limited to 80 chars per line,
     * and paragraph breaks are indicated by a double link break.  Punctutation is treated specially,
     * since even though it is considered a token, it should not generally have spaces around it.
     * @param p the number of paragraphs of dissocated text to output
     * @return a dissociated text with p paragraphs
     */
    public String outputASCII(int p)
    {
	StringBuffer returnText = new StringBuffer();
	String text;
	String next;
	int paragraphs = 0;
	int letters = 0;
	boolean insertSpace = false;

	/* If the Markov Chain does not have a start node, write an error and return null */
	if (!(sequences.containsKey(MarkovChain.start))){
	    System.out.println("No Start Sequence\n");
	    return null;
	}

	/* Otherwise begin outputting */
	Sequence currentSequence = (Sequence)sequences.get(MarkovChain.start);
	while (paragraphs < p) {
	    text = currentSequence.outputASCII();
	    next = currentSequence.getNextSequenceKey();
	    /* If the text will go over LETTERS_PER_LINE chars, add a newline */
	    if (((letters + 1 + text.length()) > LETTERS_PER_LINE)) {
		    returnText = returnText.append("\n");
		    letters = 0;
		    insertSpace = false;
	    }
	    if (text.equals("")){
		returnText = returnText;
	    }
	    /* If the text is a paragraph break, return two line breaks */
	    else if (text.equals(AsciiParse.PARAGRAPH_BREAK)){
		returnText = returnText.append("\n\n");
		paragraphs += 1;
		letters = 0;
		insertSpace = false;
	    }
	    /* If the text is punctuation, deal with appropriately */
	    else if (text.equals(".") || text.equals(",") || text.equals("!") || text.equals("?")){
		returnText = returnText.append(text);
		letters += 1;
	    }
	    else if (text.equals("'") || text.equals("\"")) {
		returnText = returnText.append(text);
		insertSpace = false;
		letters += 1;
	    }
	    else {
		if (insertSpace){
		    returnText = returnText.append(" " + text);
		    letters = letters + 1 + text.length();
		}
		else {
		    returnText = returnText.append(text);
		    insertSpace = true;
		    letters = letters + text.length();
		}
	    }
	    /* If by some change you hit a node with no links, return to start node*/
	    if (next == null) {
		currentSequence = (Sequence)sequences.get(MarkovChain.start);
		System.out.println("Hit a terminal node");
		returnText = returnText.append("\n\n");
		paragraphs += 1;
		letters = 0;
	    }
	    
	    else if (next != null) {
		currentSequence = (Sequence)sequences.get(next);
	    }
	}
	return returnText.toString();
    }
    
    /**
     * Fills in the links between sequences so that each link will also
     * store the probability that the given link will be chosen.  This
     * function updates the links of every sequence in
     * the Markov Chain.
     */
    public void setProbabilities()
    {
	Enumeration elements = sequences.elements();
	Sequence element;
	while (elements.hasMoreElements()) {
	    element = (Sequence)elements.nextElement();
	    element.setProbabilities();
	}
    }

    /**
     * This function prepares the Markov Chain to be written out to
     * human-readable XML.
     */ 
     public void implementIDs()
     
    {
	Enumeration elements = sequences.elements();
	Sequence element;
	long uniqueTokenCount = 1;
	while (elements.hasMoreElements()) {
	    element = (Sequence)elements.nextElement();
	    element.makeIDs(uniqueTokenCount);
	    uniqueTokenCount += 1;
	}
	elements = sequences.elements();
	while (elements.hasMoreElements()) {
	    element = (Sequence)elements.nextElement();
	    element.updateLinks(sequences);
	}
    }

    /**
     * Returns the string representing the Markov Chain.  This
     * is really only used for debugging.  
     * @return String the Markov Chain's string representation
     */
    public String toString()
    {    
	return sequences.toString();
    }
}
=======
package processor;import java.util.*;public class MarkovChain{    private static final int LETTERS_PER_LINE = 80;   // Number of letters per line for ASCII    public static final String start = "&start";    public static final String finish = "&finish";    private Hashtable sequences = new Hashtable();    /**     * Inserts the given sequence into the hash table using the given string.     * @param sequence the sequence we wish to insert into the hashtable     * @param key the key we will use to store said sequence     */    public void insertSequence(Sequence sequence, String key)    {	sequences.put(key,sequence);    }    /**     * Inserts the sequence with key currentKey into the hashtable,     * and additionally inserts a link to this sequence from the sequence     * with key priorSequence.     * @param priorKey the key of the sequence seen just before the current     * @param currentKey the key of the current sequence     * @param word the word that the current sequence will output     */    public void insertSequence(String priorKey, String currentKey, String word)    {	Sequence priorSequence;	if (!(sequences.containsKey(currentKey))) {	    sequences.put(currentKey, new Sequence(currentKey, word));	}	priorSequence = (Sequence)sequences.get(priorKey);		priorSequence.insertLink(currentKey);    }        /**     * Concatenates calls to Sequence.outputXML for each member of sequences.     * @return an XML representation of the entire MarkovChain     */    public String outputXML()    {	Enumeration elements = sequences.elements();	Sequence element;	StringBuffer xmlText = new StringBuffer();	while (elements.hasMoreElements()) {	     xmlText = xmlText.append(((Sequence)elements.nextElement()).outputXML() + " ");	}	return xmlText.toString();    }    /**     * Returns the the requested number of paragraphs of ASCII text generated from the     * Markov Model.  The text is generated by taking a random walk through the model, using the     * probability of each edge to decide where to go next.  Output is limited to 80 chars per line,     * and paragraph breaks are indicated by a double link break.  Punctutation is treated specially,     * since even though it is considered a token, it should not generally have spaces around it.     * @param p the number of paragraphs of dissocated text to output     * @return a dissociated text with p paragraphs     */    public String outputASCII(int p)    {	StringBuffer returnText = new StringBuffer();	String text;	String next;	int paragraphs = 0;	int letters = 0;	boolean insertSpace = false;	/* If the Markov Chain does not have a start node, write an error and return null */	if (!(sequences.containsKey(MarkovChain.start))){	    System.out.println("No Start Sequence\n");	    return null;	}	/* Otherwise begin outputting */	Sequence currentSequence = (Sequence)sequences.get(MarkovChain.start);	while (paragraphs < p) {	    text = currentSequence.outputASCII();	    next = currentSequence.getNextSequenceKey();	    /* If the text will go over LETTERS_PER_LINE chars, add a newline */	    if (((letters + 1 + text.length()) > LETTERS_PER_LINE)) {		    returnText = returnText.append("\n");		    letters = 0;		    insertSpace = false;	    }	    if (text.equals("")){		returnText = returnText;	    }	    /* If the text is a paragraph break, return two line breaks */	    else if (text.equals(AsciiParse.PARAGRAPH_BREAK)){		returnText = returnText.append("\n\n");		paragraphs += 1;		letters = 0;		insertSpace = false;	    }	    /* If the text is punctuation, deal with appropriately */	    else if (text.equals(".") || text.equals(",") || text.equals("!") || text.equals("?")){		returnText = returnText.append(text);		letters += 1;	    }	    else if (text.equals("'") || text.equals("\"")) {		returnText = returnText.append(text);		insertSpace = false;		letters += 1;	    }	    else {		if (insertSpace){		    returnText = returnText.append(" " + text);		    letters = letters + 1 + text.length();		}		else {		    returnText = returnText.append(text);		    insertSpace = true;		    letters = letters + text.length();		}	    }	    /* If by some change you hit a node with no links, return to start node*/	    if (next == null) {		currentSequence = (Sequence)sequences.get(MarkovChain.start);		System.out.println("Hit a terminal node");		returnText = returnText.append("\n\n");		paragraphs += 1;		letters = 0;	    }	    	    else if (next != null) {		currentSequence = (Sequence)sequences.get(next);	    }	}	return returnText.toString();    }        /**     * Fills in the links between sequences so that each link will also     * store the probability that the given link will be chosen.  This     * function updates the links of every sequence in     * the Markov Chain.     */    public void setProbabilities()    {	Enumeration elements = sequences.elements();	Sequence element;	while (elements.hasMoreElements()) {	    element = (Sequence)elements.nextElement();	    element.setProbabilities();	}    }    /**     * This function prepares the Markov Chain to be written out to     * human-readable XML.     */      public void implementIDs()         {	Enumeration elements = sequences.elements();	Sequence element;	long uniqueTokenCount = 1;	while (elements.hasMoreElements()) {	    element = (Sequence)elements.nextElement();	    element.makeIDs(uniqueTokenCount);	    uniqueTokenCount += 1;	}	elements = sequences.elements();	while (elements.hasMoreElements()) {	    element = (Sequence)elements.nextElement();	    element.updateLinks(sequences);	}    }    /**     * Returns the string representing the Markov Chain.  This     * is really only used for debugging.       * @return String the Markov Chain's string representation     */    public String toString()    {    	return sequences.toString();    }}
>>>>>>> 35a35230355cd1cd9deb61925155eef54330b5eb
