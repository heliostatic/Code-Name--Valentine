package XMLparse;

/**
 *Stores a string. Represents the data between two tags.
 */
public class Word extends Glyph
{
    private String text;
    
    /**
     * Constructs a new Word.
     * @param t1 
     * @SBGen Constructor 
     */
    private Word(TokenList tl)
    {	   
    }
    
    public String getText()
    {
	return text;
    }
    
    /**
     * Creates a new Glyph from TokenList input
     * @param t1 the TokenList being read from
     */
    public static Glyph read(TokenList tl) throws java.io.IOException
    {
	Word theWord = new Word(tl);
	theWord.text = tl.pop();
	return theWord;
    }

    /**
     * @param algo the Algorithm we wish to execute
     * @param input the input for the algorithm
     * @return the result of execution
     * Provides a hook for the visitor pattern
     */
    public Object execute(GAlgo algo,Object input)
    {
	return algo.forWord(this,input);
    }
}

