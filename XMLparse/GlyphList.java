package XMLparse;
/**
 * Represents a Non-empty GlyphList.  Has a Glyph and a pointer to
 * the rest of the GlyphList
 */

public class GlyphList extends XMLText
{
    private Glyph item;
    private XMLText xmltext;
    /** 
    * Constructs a new GlyphList
    *@SBGen Constructor   
    */
    public GlyphList(){	
    }

    public Glyph getItem(){
	return item;
    }

    public XMLText getXmltext(){
	return xmltext;
    }
    
    /**
     * Puts next into item using Glyph.read
     * then recursively calls XMLText.read on
     * the rest of tokenList and puts it in xmltext.
     * @param t1 
     */
    public static XMLText read(TokenList tl) throws java.io.IOException{
	GlyphList temp = new GlyphList();
	temp.item = Glyph.read(tl);
	temp.xmltext = XMLText.read(tl);
	return temp;
    }

    public Object execute(XAlgo algo, Object input)
    {
	return algo.forGlyphList(this,input);
    }
}

