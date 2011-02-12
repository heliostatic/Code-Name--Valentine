<<<<<<< HEAD
package parser;
import XMLparse.*;
import processor.*;
public class GlyphLinkVisitor implements GAlgo
{

    /* Singleton Pattern */
    public final static GlyphLinkVisitor Singleton = new GlyphLinkVisitor();
    private GlyphLinkVisitor()
    {
    }
    
    /*
     * @param host the Word host Glyph
     * @param input the Link that we are modifying
     * @return null
     * if input is empty, returns the host text, otherwise concatenates
     * the host text onto input.
     */
    public Object forWord(Word host,Object input)
    {
	throw new IllegalArgumentException("untagged word in link");
    }

    /**
     * @param host the TaggedItem host Glyph
     * @param input the Tag we're modifying
     * @return null
     * @exception IllegalArgumentException
     * Modifies the Tag's based on <node> or <prob> input
     * throws an error if a different tag is seen
     */
    public Object forTaggedItem(TaggedItem host,Object input)
    {
	Link link = (Link)input;
	if (host.getOpen().getTag().compareToIgnoreCase("<node>") == 0)
	    {
		String key = (String)host.getText().execute(TextVisitor.Singleton,"");
		link.setKey(key);
	    }
	else if (host.getOpen().getTag().compareToIgnoreCase("<prob>") == 0)
	    {
		String sprob = (String)host.getText().execute(TextVisitor.Singleton,"");
		double prob = Double.parseDouble(sprob);
		link.setProb(prob);
    }
	else
	    {
		throw new IllegalArgumentException("invalid inner-link tag: " + host.getOpen().getTag());
	    }
	return null;
    }
}




=======
package parser;import XMLparse.*;import processor.*;public class GlyphLinkVisitor implements GAlgo{    /* Singleton Pattern */    public final static GlyphLinkVisitor Singleton = new GlyphLinkVisitor();    private GlyphLinkVisitor()    {    }        /*     * @param host the Word host Glyph     * @param input the Link that we are modifying     * @return null     * if input is empty, returns the host text, otherwise concatenates     * the host text onto input.     */    public Object forWord(Word host,Object input)    {	throw new IllegalArgumentException("untagged word in link");    }    /**     * @param host the TaggedItem host Glyph     * @param input the Tag we're modifying     * @return null     * @exception IllegalArgumentException     * Modifies the Tag's based on <node> or <prob> input     * throws an error if a different tag is seen     */    public Object forTaggedItem(TaggedItem host,Object input)    {	Link link = (Link)input;	if (host.getOpen().getTag().compareToIgnoreCase("<node>") == 0)	    {		String key = (String)host.getText().execute(TextVisitor.Singleton,"");		link.setKey(key);	    }	else if (host.getOpen().getTag().compareToIgnoreCase("<prob>") == 0)	    {		String sprob = (String)host.getText().execute(TextVisitor.Singleton,"");		double prob = Double.parseDouble(sprob);		link.setProb(prob);    }	else	    {		throw new IllegalArgumentException("invalid inner-link tag: " + host.getOpen().getTag());	    }	return null;    }}
>>>>>>> 35a35230355cd1cd9deb61925155eef54330b5eb
