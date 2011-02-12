package parser;
import XMLparse.*;
import processor.*;
public class TextVisitor implements XAlgo
{


    /* Singleton Pattern */
    public final static TextVisitor Singleton = new TextVisitor();
    private TextVisitor()
    {
    }

    
    /**
     * @param host the Empty host XMLText, somewhere in a tag
     * @param input the text we've seen within that tag so far
     * @return input
     */
    public Object forEmpty(Empty host, Object input)
    {
	return input;
    }

    /**
     * @param host the GlyphList host XMLText, somewhere in a tag
     * @param input the text we've seen within that tag so far
     * @return the string from this point on within the tag
     * calls GlyphTextVisitor on the Item and
     * TextVisitor on the GlyphList, concatenating their results
     */    
    public Object forGlyphList(GlyphList host, Object input)
    {
	String temp = (String)host.getItem().execute(GlyphTextVisitor.Singleton,input);
	return host.getXmltext().execute(TextVisitor.Singleton,temp);
    }
}
