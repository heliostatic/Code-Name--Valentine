package XMLparse;
/**
 *Stores a string representing an open tag.
 */
public class OpenTag
{
    private String tag;
    
    /**
     * @param t1 
     * @SBGen Constructor 
     */
    private OpenTag(TokenList t1)
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
     * puts t1.pop in tag.
     * @param t1 
     */
    public static OpenTag read(TokenList tl) throws java.io.IOException
    {
	OpenTag openTag = new OpenTag(tl);
	openTag.tag = tl.pop();
	return openTag;
    }
    /**
     * Compares this OpenTag to the closeTag passed into obj.
     * @param obj the close tag that should match this open tag.
     */
    public boolean equals(Object obj){
	String closeTag = ((CloseTag)obj).getTag();
	//knock off "<" for open tag.
	String tempOpenTag = tag.substring(1,tag.length()-1);
	//knock off "</" for close tag
	closeTag = closeTag.substring(2,closeTag.length() - 1);
	//compare the two strings.
	return (closeTag.compareToIgnoreCase(tempOpenTag) == 0);
    }    
}

