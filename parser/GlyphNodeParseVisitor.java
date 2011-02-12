<<<<<<< HEAD
package parser;
import XMLparse.*;
import processor.*;
public class GlyphNodeParseVisitor implements GAlgo
{

    /* Singleton Pattern */
    public final static GlyphNodeParseVisitor Singleton = new GlyphNodeParseVisitor();
    private GlyphNodeParseVisitor()
    {
    }


    /**
     * @param host the Word host Glyph, somewhere inside a <node> tag
     * @param input the Node we are building
     * @return null
     * @throw IllegalArgumentException
     * throws an illegal argument exception, because execution implies
     * that there is an untagged word in the header
     */
    public Object forWord(Word host, Object input)
    {
	throw new IllegalArgumentException("untagged word in node");
    }

    /**
     * @param host the TaggedItem host Glyph, somewhere inside a <node> tag
     * @param input the Node we are building
     * @return null
     * @exception IllegalArgumentException
     * Modifies the input Node in response to new information
     */
    public Object forTaggedItem(TaggedItem host, Object input)
    {
	Sequence sequence = (Sequence)input;
	if (host.getOpen().getTag().compareToIgnoreCase("<code>") == 0)
	    {
		String key = (String)host.getText().execute(TextVisitor.Singleton,"");
		sequence.setKey(key);
	    }
	
	else if (host.getOpen().getTag().compareToIgnoreCase("<word>") == 0)
	    {
		String word = (String)host.getText().execute(TextVisitor.Singleton,"");
		sequence.setWord(preProcess(word));
	    }
	
	else if (host.getOpen().getTag().compareToIgnoreCase("<edge>") == 0)
	    {
		Link link = (Link)host.getText().execute(LinkVisitor.Singleton,new Link());
		sequence.addLink(link);
	    }
	return null;
    }

    /**
     * Replaces reserved "&gt" and "&lt" tokens with their actual meanings
     */
    private String preProcess(String word)
    {
	if (word.equals("&gt"))
	    {
		return ">";
	    }
	if (word.equals("&lt"))
	    {
		return "<";
	    }
	return word;
    }
}
	
	
		
=======
package parser;import XMLparse.*;import processor.*;public class GlyphNodeParseVisitor implements GAlgo{    /* Singleton Pattern */    public final static GlyphNodeParseVisitor Singleton = new GlyphNodeParseVisitor();    private GlyphNodeParseVisitor()    {    }    /**     * @param host the Word host Glyph, somewhere inside a <node> tag     * @param input the Node we are building     * @return null     * @throw IllegalArgumentException     * throws an illegal argument exception, because execution implies     * that there is an untagged word in the header     */    public Object forWord(Word host, Object input)    {	throw new IllegalArgumentException("untagged word in node");    }    /**     * @param host the TaggedItem host Glyph, somewhere inside a <node> tag     * @param input the Node we are building     * @return null     * @exception IllegalArgumentException     * Modifies the input Node in response to new information     */    public Object forTaggedItem(TaggedItem host, Object input)    {	Sequence sequence = (Sequence)input;	if (host.getOpen().getTag().compareToIgnoreCase("<code>") == 0)	    {		String key = (String)host.getText().execute(TextVisitor.Singleton,"");		sequence.setKey(key);	    }		else if (host.getOpen().getTag().compareToIgnoreCase("<word>") == 0)	    {		String word = (String)host.getText().execute(TextVisitor.Singleton,"");		sequence.setWord(preProcess(word));	    }		else if (host.getOpen().getTag().compareToIgnoreCase("<edge>") == 0)	    {		Link link = (Link)host.getText().execute(LinkVisitor.Singleton,new Link());		sequence.addLink(link);	    }	return null;    }    /**     * Replaces reserved "&gt" and "&lt" tokens with their actual meanings     */    private String preProcess(String word)    {	if (word.equals("&gt"))	    {		return ">";	    }	if (word.equals("&lt"))	    {		return "<";	    }	return word;    }}				
>>>>>>> 35a35230355cd1cd9deb61925155eef54330b5eb
