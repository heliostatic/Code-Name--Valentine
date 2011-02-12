<<<<<<< HEAD
package processor;
import java.util.*;

/**
 * Representation of a link from one sequence to another.  Has an occurances field
 * to keep track of how many times the link has been seen in text, a nextKey field which
 * stores the n-1 word key to the sequence this Link links to, and a probability, which is
 * the probability that this link will be chosen when making a random walk through the link's Sequence.
 */
public class Link
{
    private int occurances;
    private String nextKey;    // the sequence we are linking to
    private double probability;// the probability of traversing this link

    /**
     * Constructor used by CorpusSummaryBuilder. Establishes this Link as a Link to nextKey
     * @param nextKey the key of the Sequence to link to.
     */
    public Link(String nextKey) {
	occurances = 1;
	this.nextKey = nextKey;
    }

    /**
     * Constructor used by CorpusSummaryBuilder. Returns an empty Link
     */
    public Link()
    {
	nextKey = null;
	probability = 0.0;
    }
    
    /**
     * Increments occurances for the current Link.
     */
    public void incrementOccurances()
    {
	occurances += 1;
    }

    /**
     * Replaces the key of the next sequence with the new ID of the next sequence.
     * This is used when preparing a Markov Model for output to XML.
     * @param sequences the hashtable of sequences
     */
    public void updateLink(Hashtable sequences)
    {
	nextKey = ((Sequence)sequences.get(nextKey)).getID();
    }
    
    /**
     * Outputs the link to XML when it is traversed.  The n-1 word key
     * to the next Sequence is not written, since the spec so commands it.
     * @return the XML representation of this Link
     */
    public String outputXML()
    {
	String linkText = "";
	linkText = linkText + "<edge>\n";
	linkText = linkText + "<node>" + nextKey + "</node>\n";
	linkText = linkText + "<prob>" + probability + "</prob>\n";
	linkText = linkText + "</edge>\n";
	return linkText;
    }

    /**
     * Establishes the probability this link will be chosen based upon the links occurances
     * and the given total_links_out from the sequence.
     * @param total_links_out the number of links out from the parent sequence
     */
    public void setProbability(int total_links_out)
    {
	this.probability = (double)occurances/(double)total_links_out;
    }

    /**
     * Returns whether two links are equal.
     * @param the Link to compare the current Link to
     * @return the truth value of this equality
     */
    public boolean equals(Link l1) {
	return l1.getKey().equals(this.nextKey);
    }

    /**
     * Sets the current Link's key to the given key.
     * @param key the key to change to
     */
    public void setKey(String key) {
	this.nextKey = key;
    }

    /**
     * Sets the current Link's probability to the given probability.
     * @param key the prob to change to
     */
    public void setProb(double prob) {
	this.probability = prob;
    }

    /**
     * Returns this Link's n-1 word key.
     * @return this Link's key
     */
    public String getKey() {
	return this.nextKey;
    }

    /**
     * Returns this Link's probability of being chosen by its Sequence.
     * @return this Link's probability
     */
    public double getProbability() {
	return this.probability;
    }

    /**
     * Returns this Link's String representation.  This is used for debugging.
     * @return String representation of this Link.
     */
    public String toString() {
	return (nextKey + " - " + occurances + " - " + probability);
    }

    
}
=======
package processor;import java.util.*;/** * Representation of a link from one sequence to another.  Has an occurances field * to keep track of how many times the link has been seen in text, a nextKey field which * stores the n-1 word key to the sequence this Link links to, and a probability, which is * the probability that this link will be chosen when making a random walk through the link's Sequence. */public class Link{    private int occurances;    private String nextKey;    // the sequence we are linking to    private double probability;// the probability of traversing this link    /**     * Constructor used by CorpusSummaryBuilder. Establishes this Link as a Link to nextKey     * @param nextKey the key of the Sequence to link to.     */    public Link(String nextKey) {	occurances = 1;	this.nextKey = nextKey;    }    /**     * Constructor used by CorpusSummaryBuilder. Returns an empty Link     */    public Link()    {	nextKey = null;	probability = 0.0;    }        /**     * Increments occurances for the current Link.     */    public void incrementOccurances()    {	occurances += 1;    }    /**     * Replaces the key of the next sequence with the new ID of the next sequence.     * This is used when preparing a Markov Model for output to XML.     * @param sequences the hashtable of sequences     */    public void updateLink(Hashtable sequences)    {	nextKey = ((Sequence)sequences.get(nextKey)).getID();    }        /**     * Outputs the link to XML when it is traversed.  The n-1 word key     * to the next Sequence is not written, since the spec so commands it.     * @return the XML representation of this Link     */    public String outputXML()    {	String linkText = "";	linkText = linkText + "<edge>\n";	linkText = linkText + "<node>" + nextKey + "</node>\n";	linkText = linkText + "<prob>" + probability + "</prob>\n";	linkText = linkText + "</edge>\n";	return linkText;    }    /**     * Establishes the probability this link will be chosen based upon the links occurances     * and the given total_links_out from the sequence.     * @param total_links_out the number of links out from the parent sequence     */    public void setProbability(int total_links_out)    {	this.probability = (double)occurances/(double)total_links_out;    }    /**     * Returns whether two links are equal.     * @param the Link to compare the current Link to     * @return the truth value of this equality     */    public boolean equals(Link l1) {	return l1.getKey().equals(this.nextKey);    }    /**     * Sets the current Link's key to the given key.     * @param key the key to change to     */    public void setKey(String key) {	this.nextKey = key;    }    /**     * Sets the current Link's probability to the given probability.     * @param key the prob to change to     */    public void setProb(double prob) {	this.probability = prob;    }    /**     * Returns this Link's n-1 word key.     * @return this Link's key     */    public String getKey() {	return this.nextKey;    }    /**     * Returns this Link's probability of being chosen by its Sequence.     * @return this Link's probability     */    public double getProbability() {	return this.probability;    }    /**     * Returns this Link's String representation.  This is used for debugging.     * @return String representation of this Link.     */    public String toString() {	return (nextKey + " - " + occurances + " - " + probability);    }    }
>>>>>>> 35a35230355cd1cd9deb61925155eef54330b5eb
