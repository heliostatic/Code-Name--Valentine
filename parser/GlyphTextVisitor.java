package parser;
import XMLparse.*;
import processor.*;
public class GlyphTextVisitor implements GAlgo
{

    /* Singleton Pattern */
    public final static GlyphTextVisitor Singleton = new GlyphTextVisitor();
    private GlyphTextVisitor()
    {
    }
    
    /*
     * @param host the Word host Glyph
     * @param input the string being built up
     * @return null
     * if input is empty, returns the host text, otherwise concatenates
     * the host text onto input.
     */
    public Object forWord(Word host,Object input)
    {
	if (((String)input).compareToIgnoreCase("") == 0)
	    {
		return host.getText();
	    }
	else
	    {
		return input + " " + host.getText();
	    }
    }

    /**
     * @param host the TaggedItem host Glyph
     * @param input the string being built up
     * @return null
     * @exception IllegalArgumentException
     * there shouldn't be any tags within a tagged item at this point
     */
    public Object forTaggedItem(TaggedItem host,Object input)
    {
	throw new IllegalArgumentException("tag where it shouldn't be");
    }
}
