package XMLparse;

/**
 *Stores a string representing a close tag.
 */
public class CloseTag
{
    private String tag;
    
    /**
     * @param tl
     * @SBGen Constructor 
     */
    public CloseTag(TokenList tl)
    {
    }

    /**
     * @return tag
     */
    public String getTag()
    {
	return tag;
    }
    
    /**
     * Puts t1.pop in tag.
     * @param tl 
     */
    public static CloseTag read(TokenList tl) throws java.io.IOException
    {
	CloseTag closeTag = new CloseTag(tl);
	
	closeTag.tag = tl.pop();
	return closeTag;
    }


        
}

