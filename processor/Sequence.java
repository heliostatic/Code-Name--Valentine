<<<<<<< HEAD
package processor;
import java.util.*;

/**
 * Representation of a node in the Hidden Markov Model.  A sequence has a n-1 word identifying key, as well
 * as an ID which is generally a long int (but is kept in String form to make Link's job easier).  A
 * Sequence also has the word that the node contains, and a list of links to other Sequences.
 */
public class Sequence
{
    private String key;      
    private String word;
    private LinkList links;
    private String ID;
    private Vector specialChars = new Vector();
    
    /**
     * Constructor for a basic sequence.
     */
    public Sequence()
    {
	this.word = null;
	this.key = null;
	links = new LinkList();
    }

    /**
     * Constructor for a sequence that will have the given identifying key, and will store
     * the given word.
     * @param key the new sequence's key
     * @param word the new sequence's word
     */
    public Sequence(String key, String word)
    {
	this.key = key;
	this.word = word;
	links = new LinkList();
    }  

    /**
     * Sets this Sequence's word to the given value.
     * @param word the Sequence's new word value
     */
    public void setWord(String word)
    {
	this.word = word;
    }

    /**
     * Sets this Sequence's key to the given value.
     * @param word the Sequence's new key value
     */
    public void setKey(String key)
    {
	this.key = key;
    }
    
    /**
     * Returns this Sequence's key.
     * @return the key for this Sequence.
     */
    public String getKey()
    {
	return key;
    }

    /**
     * Adds a new Link to this sequence.
     * @param link the Link to add to this Sequence.
     */
    public void addLink(Link link)
    {
	links.addLink(link);
    }    

    /**
     * Increments total_links_out, and either creates a new link to insert based
     * upon the given sequence key, or reinforces the existing link to the given sequence
     * key.
     * @param nextSequenceKey the key of the Sequence to link to
     */
    public void insertLink(String nextSequenceKey)
    {
	links.insertLink(nextSequenceKey);
    }

    /**
     * Outputs a representation of this Sequence-node in our proprietary XML format.
     * @return the XML format of this Sequence
     */
    public String outputXML()
    {
	String sequenceText = "";
	String xmlWord = word;
	if (xmlWord.equals("<")) {
	    xmlWord =  "&lt";
	}
	if (xmlWord.equals(">")) {
	    xmlWord = "&gt";
	}
	sequenceText = sequenceText + "<node>\n";
	sequenceText = sequenceText + "<code>" + ID + "</code>\n";
	sequenceText = sequenceText + "<word>" + xmlWord + "</word>\n";
	sequenceText = sequenceText + links.outputXML();
	sequenceText = sequenceText + "</node>\n";
	return sequenceText;
    }

    
    /**
     * Creates the numerical ID for this Sequence.  The starting and ending nodes get
     * their own special ID's.
     * @param tokenNum the ID for this Sequence.  
     */
    public void makeIDs(long tokenNum)
    {
	ID = (new Long(tokenNum)).toString();
	if (key.equals(MarkovChain.start)) {
	    ID = MarkovChain.start;
	}
	if (key.equals(MarkovChain.finish)) {
	    ID = MarkovChain.finish;
	}
    }

    /**
     * Updates the links going out of this sequence, so that they link to sequences ID's, and
     * not to their n-1 word keys.  This is needed to prepare the sequences to be output to XML.
     * @param sequences the Hashtable to get ID's from
     */
    public void updateLinks(Hashtable sequences)
    {
	links.updateLinks(sequences);
    }

    /**
     * Returns a ASCII representaiton of this Sequence.
     * @return the ASCII  representaiton of this Sequence.
     */
    public String outputASCII()
    {
	return word.toString();
    }

    /**
     * Returns a String representaiton of this Sequence.  Used for debugging.
     * @return the String  representaiton of this Sequence.
     */ 
    public String toString()
    {
	return ("\nkey: " + key + "\nword: " + word + "\nlinks: " + links + "\n\n");
    }

    /**
     * Returns the ID of this Sequence.
     * @return this sequence's ID
     */
    public String getID()
    {
	return ID;
    }

    /**
     * Sets the probabilities of each of this Sequence's links.  This should be performed
     * after the Corpus has been completely read and the Markov Model for it built up.
     */
    public void setProbabilities()
    {
	links.setProbabilities();
    }

    /**
     * Returns the key for the next Sequence from this sequence when taking a random walk through the
     * Markov Model.
     * @return The next Sequence's key.
     */
    public String getNextSequenceKey()
    {
	return links.getNextSequenceKey();
    }
}

=======
package processor;import java.util.*;/** * Representation of a node in the Hidden Markov Model.  A sequence has a n-1 word identifying key, as well * as an ID which is generally a long int (but is kept in String form to make Link's job easier).  A * Sequence also has the word that the node contains, and a list of links to other Sequences. */public class Sequence{    private String key;          private String word;    private LinkList links;    private String ID;    private Vector specialChars = new Vector();        /**     * Constructor for a basic sequence.     */    public Sequence()    {	this.word = null;	this.key = null;	links = new LinkList();    }    /**     * Constructor for a sequence that will have the given identifying key, and will store     * the given word.     * @param key the new sequence's key     * @param word the new sequence's word     */    public Sequence(String key, String word)    {	this.key = key;	this.word = word;	links = new LinkList();    }      /**     * Sets this Sequence's word to the given value.     * @param word the Sequence's new word value     */    public void setWord(String word)    {	this.word = word;    }    /**     * Sets this Sequence's key to the given value.     * @param word the Sequence's new key value     */    public void setKey(String key)    {	this.key = key;    }        /**     * Returns this Sequence's key.     * @return the key for this Sequence.     */    public String getKey()    {	return key;    }    /**     * Adds a new Link to this sequence.     * @param link the Link to add to this Sequence.     */    public void addLink(Link link)    {	links.addLink(link);    }        /**     * Increments total_links_out, and either creates a new link to insert based     * upon the given sequence key, or reinforces the existing link to the given sequence     * key.     * @param nextSequenceKey the key of the Sequence to link to     */    public void insertLink(String nextSequenceKey)    {	links.insertLink(nextSequenceKey);    }    /**     * Outputs a representation of this Sequence-node in our proprietary XML format.     * @return the XML format of this Sequence     */    public String outputXML()    {	String sequenceText = "";	String xmlWord = word;	if (xmlWord.equals("<")) {	    xmlWord =  "&lt";	}	if (xmlWord.equals(">")) {	    xmlWord = "&gt";	}	sequenceText = sequenceText + "<node>\n";	sequenceText = sequenceText + "<code>" + ID + "</code>\n";	sequenceText = sequenceText + "<word>" + xmlWord + "</word>\n";	sequenceText = sequenceText + links.outputXML();	sequenceText = sequenceText + "</node>\n";	return sequenceText;    }        /**     * Creates the numerical ID for this Sequence.  The starting and ending nodes get     * their own special ID's.     * @param tokenNum the ID for this Sequence.       */    public void makeIDs(long tokenNum)    {	ID = (new Long(tokenNum)).toString();	if (key.equals(MarkovChain.start)) {	    ID = MarkovChain.start;	}	if (key.equals(MarkovChain.finish)) {	    ID = MarkovChain.finish;	}    }    /**     * Updates the links going out of this sequence, so that they link to sequences ID's, and     * not to their n-1 word keys.  This is needed to prepare the sequences to be output to XML.     * @param sequences the Hashtable to get ID's from     */    public void updateLinks(Hashtable sequences)    {	links.updateLinks(sequences);    }    /**     * Returns a ASCII representaiton of this Sequence.     * @return the ASCII  representaiton of this Sequence.     */    public String outputASCII()    {	return word.toString();    }    /**     * Returns a String representaiton of this Sequence.  Used for debugging.     * @return the String  representaiton of this Sequence.     */     public String toString()    {	return ("\nkey: " + key + "\nword: " + word + "\nlinks: " + links + "\n\n");    }    /**     * Returns the ID of this Sequence.     * @return this sequence's ID     */    public String getID()    {	return ID;    }    /**     * Sets the probabilities of each of this Sequence's links.  This should be performed     * after the Corpus has been completely read and the Markov Model for it built up.     */    public void setProbabilities()    {	links.setProbabilities();    }    /**     * Returns the key for the next Sequence from this sequence when taking a random walk through the     * Markov Model.     * @return The next Sequence's key.     */    public String getNextSequenceKey()    {	return links.getNextSequenceKey();    }}
>>>>>>> 35a35230355cd1cd9deb61925155eef54330b5eb
