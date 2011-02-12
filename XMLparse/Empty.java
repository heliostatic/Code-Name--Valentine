package XMLparse;

/**
* Represents the termination of a GlyphList
*/
public class Empty extends XMLText
{
    /**
     * @param tl 
     * @SBGen Constructor 
     */
    public Empty(TokenList tl)
    {
    }
    
    /**
     * @param tl 
     */
    public static XMLText read(TokenList tl)
    {
	return new Empty(tl);
    }
    

    /**
     * @param algo the Algorithm we wish to execute
     * @param input the input for the algorithm
     * @return the result of execution
     * Provides a hook for the visitor pattern
     */
    public Object execute(XAlgo algo, Object input)
    {
	return algo.forEmpty(this,input);
    }
}

