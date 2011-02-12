<<<<<<< HEAD
package processor;
import java.util.*;

/**
 * Represents all of the Links from a Sequence to other Sequences.  
 */
public class LinkList
{
    private Vector links = new Vector();  //stores each of the Links
    private int totalLinksOut = 0;    //(counting duplicates)

    
    /**
     * Either creates a link to insert, or reinfoces the existing link
     * @param nextSequenceKey the key of the sequence we are linking to
     */
    public void insertLink(String nextSequenceKey)
    {
	Link currentLink = new Link(nextSequenceKey);
	if (this.getEquivLink(currentLink) != null) {
	    this.getEquivLink(currentLink).incrementOccurances();
	}
	else {
	    links.add(currentLink);
	}
	totalLinksOut += 1;
    }

    /**
     * Used by SummaryBuilder to add links
     * @param link the link to add
     */
    public void addLink(Link link)
    {
	links.add(link);
    }

    /**
     * Finds in the hashTable the link we wish to find an equivalent for.  
     * Helpful in testing equality with some previously entered link.
     * @param link the Link we wish to find an equivalent to
     * @return the link in this LinkList that is equivalent to Link
     */
    public Link getEquivLink(Link link)
    {
	for (int i = 0; i < links.size(); i++) {
	    if (link.equals((Link)links.elementAt(i))) {
		return (Link)links.elementAt(i);
	    }
	}
	return null;
    }

    /**
     * Applies updateLinks to every Link in the LinkList.
     * @param sequences the hashtable of sequences
     */
    public void updateLinks(Hashtable sequences)
    {
	for (int i = 0; i < links.size(); i++) {
	    ((Link)links.elementAt(i)).updateLink(sequences);
	}
    }
    
    /**
     * Outputs these each of the links into our propietary XML format.
     * @return an XML representation of this LinkList
     */
    public String outputXML()
    {
	String linksText = "";
	for (int i = 0; i < links.size(); i ++) {
	    linksText = linksText + ((Link)links.elementAt(i)).outputXML();
	}
	return linksText;
    }

    /**
     * Applies setProbability to every link in the LinkList.
     */
    public void setProbabilities()
    {
	for (int i = 0; i < links.size(); i ++) {
	    ((Link)links.elementAt(i)).setProbability(totalLinksOut);
	}
    }

    /**
     * Returns the String representation of each of the links.  Used for debugging.
     * @return the LinkList's string representation.
     */
    public String toString(){
	String output = "";
	for (int i = 0; i < links.size();i++){
	    output = output + "     " +links.elementAt(i);
	}
	return output;
    }

    /**
     * Picks the next sequence to go to, and return's its key.  The sequence picked
     * will be one of the sequences connected to the current sequence, and the chance that
     * it will be picked depends upon the probability of its edge.  (how strong its link is).
     * @return the key of the "next" sequence, randomly picked with
     */
    public String getNextSequenceKey(){
	double pick, progress;
	pick = Math.random();
	progress = 0.0;
	for (int i = 0; i < links.size(); i++){
	    progress = progress + ((Link)links.get(i)).getProbability();
	    if (pick < progress) {
		return ((Link)links.get(i)).getKey();
	    }
	}
	System.out.println("Error in getting next sequences key, returning to start sequence...");
	return null;
    }
}
=======
package processor;import java.util.*;/** * Represents all of the Links from a Sequence to other Sequences.   */public class LinkList{    private Vector links = new Vector();  //stores each of the Links    private int totalLinksOut = 0;    //(counting duplicates)        /**     * Either creates a link to insert, or reinfoces the existing link     * @param nextSequenceKey the key of the sequence we are linking to     */    public void insertLink(String nextSequenceKey)    {	Link currentLink = new Link(nextSequenceKey);	if (this.getEquivLink(currentLink) != null) {	    this.getEquivLink(currentLink).incrementOccurances();	}	else {	    links.add(currentLink);	}	totalLinksOut += 1;    }    /**     * Used by SummaryBuilder to add links     * @param link the link to add     */    public void addLink(Link link)    {	links.add(link);    }    /**     * Finds in the hashTable the link we wish to find an equivalent for.       * Helpful in testing equality with some previously entered link.     * @param link the Link we wish to find an equivalent to     * @return the link in this LinkList that is equivalent to Link     */    public Link getEquivLink(Link link)    {	for (int i = 0; i < links.size(); i++) {	    if (link.equals((Link)links.elementAt(i))) {		return (Link)links.elementAt(i);	    }	}	return null;    }    /**     * Applies updateLinks to every Link in the LinkList.     * @param sequences the hashtable of sequences     */    public void updateLinks(Hashtable sequences)    {	for (int i = 0; i < links.size(); i++) {	    ((Link)links.elementAt(i)).updateLink(sequences);	}    }        /**     * Outputs these each of the links into our propietary XML format.     * @return an XML representation of this LinkList     */    public String outputXML()    {	String linksText = "";	for (int i = 0; i < links.size(); i ++) {	    linksText = linksText + ((Link)links.elementAt(i)).outputXML();	}	return linksText;    }    /**     * Applies setProbability to every link in the LinkList.     */    public void setProbabilities()    {	for (int i = 0; i < links.size(); i ++) {	    ((Link)links.elementAt(i)).setProbability(totalLinksOut);	}    }    /**     * Returns the String representation of each of the links.  Used for debugging.     * @return the LinkList's string representation.     */    public String toString(){	String output = "";	for (int i = 0; i < links.size();i++){	    output = output + "     " +links.elementAt(i);	}	return output;    }    /**     * Picks the next sequence to go to, and return's its key.  The sequence picked     * will be one of the sequences connected to the current sequence, and the chance that     * it will be picked depends upon the probability of its edge.  (how strong its link is).     * @return the key of the "next" sequence, randomly picked with     */    public String getNextSequenceKey(){	double pick, progress;	pick = Math.random();	progress = 0.0;	for (int i = 0; i < links.size(); i++){	    progress = progress + ((Link)links.get(i)).getProbability();	    if (pick < progress) {		return ((Link)links.get(i)).getKey();	    }	}	System.out.println("Error in getting next sequences key, returning to start sequence...");	return null;    }}
>>>>>>> 35a35230355cd1cd9deb61925155eef54330b5eb
