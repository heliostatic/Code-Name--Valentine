package XMLparse;
/**
*A glyph is either a Word or a TaggedItem class.
*/
public abstract class Glyph
{
    /**
     * Checks the next input and calls word.read or
     * TaggedItem.read onit accordingly.
     * @param t1 
     */
    
    public  static Glyph read(TokenList tl) throws java.io.IOException
    {
	String nextString = tl.pop();
	tl.push(nextString);
	
	if (nextString.startsWith("<")){
	    return TaggedItem.read(tl);
	}
	else{
	    return Word.read(tl);
	}
    }
    
    /**
     * @param algo the Algorithm we wish to execute
     * @param input the input for the algorithm
     * @return the result of execution
     * Provides a hook for the visitor pattern
     */
    public abstract  Object execute(GAlgo algo,Object input);    
    
}

