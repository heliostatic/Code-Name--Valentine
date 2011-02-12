<<<<<<< HEAD
package processor;
import java.util.*;
import java.io.*;

/**
 * Tokenizes an ASCII file.  A paragraph break, represented as seeing
 * more than 1 consecutive \n, is returned as the token "&pb"
 */
public class AsciiParse
{

    private static final int LB = 10; //ascii code for a line break
    public static final String PARAGRAPH_BREAK = "&pb";
    private StreamTokenizer tokenizer;
    private boolean isEnd;

    /**
     * @param theFile the file that we wish to Tokenize
     */
    public AsciiParse(String theFile) throws IOException
    {
	isEnd = false;
	FileInputStream stream = new FileInputStream(theFile);
	Reader r = new BufferedReader(new InputStreamReader(stream));
	tokenizer = new StreamTokenizer(r);
    }

    /**
     * @return the next token in the text file
     */
    public String nextToken() throws java.io.IOException
    {
	if (isEnd) {
	    return null;
	}
	else {
	    tokenizer.resetSyntax();
	    tokenizer.whitespaceChars(0,32);
	    tokenizer.wordChars(33,127);
	    tokenizer.ordinaryChar(LB);    // line break 
	    tokenizer.ordinaryChars(33,47);// punctuation
	    tokenizer.ordinaryChars(58,64);// punctuation
	    tokenizer.ordinaryChars(91,96);// punctuation
	    tokenizer.ordinaryChars(123,127);// punctuation
	    int tokenType = tokenizer.nextToken();
	    if (tokenType == StreamTokenizer.TT_EOF)
		{
		    isEnd = true;
		    return PARAGRAPH_BREAK;
		}
	    if (tokenType == StreamTokenizer.TT_WORD)
		{
		    return tokenizer.sval;
		}
	    if (tokenizer.ttype == LB){
		int lbtype = tokenizer.nextToken();
		if (lbtype != LB)
		    {
			tokenizer.pushBack();
			return nextToken();
		    }
		else {
		    while (lbtype == LB)
			{
			    lbtype = tokenizer.nextToken();
			}
		    tokenizer.pushBack();
		    return AsciiParse.PARAGRAPH_BREAK;
		}
	    }
	    else
		{
		    return new String(String.valueOf((char)tokenizer.ttype));
		}
	}
    }
}
=======
package processor;import java.util.*;import java.io.*;/** * Tokenizes an ASCII file.  A paragraph break, represented as seeing * more than 1 consecutive \n, is returned as the token "&pb" */public class AsciiParse{    private static final int LB = 10; //ascii code for a line break    public static final String PARAGRAPH_BREAK = "&pb";    private StreamTokenizer tokenizer;    private boolean isEnd;    /**     * @param theFile the file that we wish to Tokenize     */    public AsciiParse(String theFile) throws IOException    {	isEnd = false;	FileInputStream stream = new FileInputStream(theFile);	Reader r = new BufferedReader(new InputStreamReader(stream));	tokenizer = new StreamTokenizer(r);    }    /**     * @return the next token in the text file     */    public String nextToken() throws java.io.IOException    {	if (isEnd) {	    return null;	}	else {	    tokenizer.resetSyntax();	    tokenizer.whitespaceChars(0,32);	    tokenizer.wordChars(33,127);	    tokenizer.ordinaryChar(LB);    // line break 	    tokenizer.ordinaryChars(33,47);// punctuation	    tokenizer.ordinaryChars(58,64);// punctuation	    tokenizer.ordinaryChars(91,96);// punctuation	    tokenizer.ordinaryChars(123,127);// punctuation	    int tokenType = tokenizer.nextToken();	    if (tokenType == StreamTokenizer.TT_EOF)		{		    isEnd = true;		    return PARAGRAPH_BREAK;		}	    if (tokenType == StreamTokenizer.TT_WORD)		{		    return tokenizer.sval;		}	    if (tokenizer.ttype == LB){		int lbtype = tokenizer.nextToken();		if (lbtype != LB)		    {			tokenizer.pushBack();			return nextToken();		    }		else {		    while (lbtype == LB)			{			    lbtype = tokenizer.nextToken();			}		    tokenizer.pushBack();		    return AsciiParse.PARAGRAPH_BREAK;		}	    }	    else		{		    return new String(String.valueOf((char)tokenizer.ttype));		}	}    }}
>>>>>>> 35a35230355cd1cd9deb61925155eef54330b5eb
