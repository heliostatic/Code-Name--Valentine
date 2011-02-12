package parser;
import XMLparse.*;
import processor.*;
public class GlyphParseVisitor implements GAlgo{

    /* Singleton Pattern */
    public final static GlyphParseVisitor Singleton = new GlyphParseVisitor();
    private GlyphParseVisitor()
    {
    }

    /*
     * @param host the Word host Glyph
     * @param input the Article we wish to process
     * @return null
     * @exception IllegalArgumentException
     * Throws an exception, because execution of this function implies
     * that there is an untagged word at the top level of the XMLText
     */
    public Object forWord(Word host,Object input)
    {
	throw new IllegalArgumentException("Untagged word");
    }

    /*
     * @param host the TaggedItem host Glyph
     * @param input the Article we wish to process
     * @return null
     * @exception IllegalArgumentException
     * Checks that the first tag seen in the XMLText is an <article> tag
     * and calls ArticleParseVisitor on the text within the tag
     */
    public Object forTaggedItem(TaggedItem host,Object input)
    {
	
	if (host.getOpen().getTag().compareToIgnoreCase("<corpus>") == 0)
	    {
		host.getText().execute(CorpusParseVisitor.Singleton, input);
	    }
	else {
	    throw new IllegalArgumentException("Invalid top-level tag");
	}
	return null;
    }
}

