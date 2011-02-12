<<<<<<< HEAD
package XMLparse;
/**
 *The base class for the entire XML hierarchy.
 */
public abstract class XMLText {
    /**
     *The next token can be either a glyphList or empty.
     *Empty can be either NULL or a close tag.
     *The read method should then call the appropriate subclass.read
     */
    public static XMLText read(TokenList tl) throws java.io.IOException{
	String nextString = tl.pop();
	tl.push(nextString);
	//if tl string is now empty.
	if ((nextString == null) || (nextString.startsWith("</"))){
	    return Empty.read(tl);
	}
	//if tl string is not empty continue parsing.
	return GlyphList.read(tl);
	
    }

    /**
     * @param algo the Algorithm we wish to execute
     * @param input the input for the algorithm
     * @return the result of execution
     * Provides a hook for the visitor pattern
     */
    public abstract Object execute(XAlgo algo, Object input);
    
}
   
=======
package XMLparse;/** *The base class for the entire XML hierarchy. */public abstract class XMLText {    /**     *The next token can be either a glyphList or empty.     *Empty can be either NULL or a close tag.     *The read method should then call the appropriate subclass.read     */    public static XMLText read(TokenList tl) throws java.io.IOException{	String nextString = tl.pop();	tl.push(nextString);	//if tl string is now empty.	if ((nextString == null) || (nextString.startsWith("</"))){	    return Empty.read(tl);	}	//if tl string is not empty continue parsing.	return GlyphList.read(tl);	    }    /**     * @param algo the Algorithm we wish to execute     * @param input the input for the algorithm     * @return the result of execution     * Provides a hook for the visitor pattern     */    public abstract Object execute(XAlgo algo, Object input);    }   
>>>>>>> 35a35230355cd1cd9deb61925155eef54330b5eb
