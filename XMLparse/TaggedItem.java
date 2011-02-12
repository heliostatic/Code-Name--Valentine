package XMLparse;
/**
 *Tagged item is a Glyph of the format <tag>XMLText</tag>
 */
public class TaggedItem extends Glyph
{
    private OpenTag ot;
    private CloseTag ct;
    private XMLText xt;
    
    /**
     * @param t1 
     * @SBGen Constructor 
     */
    public TaggedItem(TokenList tl)
    {
    }

    /**
     * @return the open tag
     */
    public OpenTag getOpen()
    {
	return ot;
    }

    /**
     * @return the close tag
     */
    public CloseTag getClose()
    {
	return ct;
    }

    /**
     * @return the contained XMLText
     */
    public XMLText getText()
    {
	return xt;
    }
    
    /**
     * Reads in the next item and puts it in OpenTag.
     * calls XMLText.read on the next.
     * calls CloseTag.read
     * If any of these parts are not present an error is thrown.
     * close tag should match the type of open tag, if not error is
     * thrown.
     * @param t1 
     */
    static public Glyph read(TokenList tl) throws java.io.IOException
    {
	TaggedItem theItem = new TaggedItem(tl);
	theItem.ot = OpenTag.read(tl);
	theItem.xt = XMLText.read(tl);
	theItem.ct = CloseTag.read(tl);
	//every open tag must have an identical close tag.
	if (theItem.ct == null){
	    throw new java.lang.IllegalArgumentException("No close tag");
	}
	if (!theItem.ot.equals(theItem.ct)){
	    throw new java.lang.IllegalArgumentException("Mismatched Open/Close Tags");
	}
	return theItem;
    }
    
    /**
     * @param algo the Algorithm we wish to execute
     * @param input the input for the algorithm
     * @return the result of execution
     * Provides a hook for the visitor pattern
     */
    public Object execute(GAlgo algo,Object input)
    {
	return algo.forTaggedItem(this,input);
    }
}

