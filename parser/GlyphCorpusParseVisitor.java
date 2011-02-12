<<<<<<< HEAD
package parser;
import XMLparse.*;
import processor.*;
public class GlyphCorpusParseVisitor implements GAlgo{


    /* Singleton Pattern */
    public final static GlyphCorpusParseVisitor Singleton = new GlyphCorpusParseVisitor();
    private GlyphCorpusParseVisitor()
    {
    }

    /*
     * @param host the Word host Glyph, somewhere inside a <corpus> tag
     * @param input the MarkovChain we are building
     * @return null
     * @exception IllegalArgumentException
     * throws an exception, because execution implies an untagged word
     * within an Article tag.
     */
    public Object forWord(Word host,Object input)
    {
	throw new IllegalArgumentException("Untagged word");
    }

    /**
     * @param host the TaggedItem host Glyph, somewhere inside a <corpus> tag
     * @param input the MarkovChain we are building
     * @return null
     * Makes sure that the tag is <node> and calls NodeParseVisitor
     */
    public Object forTaggedItem(TaggedItem host,Object input)
    { 
	
	if (host.getOpen().getTag().compareToIgnoreCase("<node>") == 0)
	    {
		Sequence temp = new Sequence();
		MarkovChain chain = (MarkovChain)input;
		Sequence returned = (Sequence)host.getText().execute(NodeParseVisitor.Singleton,temp);
		if (returned.getKey() != null)
		    {
			chain.insertSequence(returned,returned.getKey());
		    }
	    }
	else {
	    throw new IllegalArgumentException("Invalid corpus-level tag");
	}
	return null;
    }
}

=======
package parser;import XMLparse.*;import processor.*;public class GlyphCorpusParseVisitor implements GAlgo{    /* Singleton Pattern */    public final static GlyphCorpusParseVisitor Singleton = new GlyphCorpusParseVisitor();    private GlyphCorpusParseVisitor()    {    }    /*     * @param host the Word host Glyph, somewhere inside a <corpus> tag     * @param input the MarkovChain we are building     * @return null     * @exception IllegalArgumentException     * throws an exception, because execution implies an untagged word     * within an Article tag.     */    public Object forWord(Word host,Object input)    {	throw new IllegalArgumentException("Untagged word");    }    /**     * @param host the TaggedItem host Glyph, somewhere inside a <corpus> tag     * @param input the MarkovChain we are building     * @return null     * Makes sure that the tag is <node> and calls NodeParseVisitor     */    public Object forTaggedItem(TaggedItem host,Object input)    { 		if (host.getOpen().getTag().compareToIgnoreCase("<node>") == 0)	    {		Sequence temp = new Sequence();		MarkovChain chain = (MarkovChain)input;		Sequence returned = (Sequence)host.getText().execute(NodeParseVisitor.Singleton,temp);		if (returned.getKey() != null)		    {			chain.insertSequence(returned,returned.getKey());		    }	    }	else {	    throw new IllegalArgumentException("Invalid corpus-level tag");	}	return null;    }}
>>>>>>> 35a35230355cd1cd9deb61925155eef54330b5eb
