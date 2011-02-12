<<<<<<< HEAD
package processor;
import java.io.*;

/**
 * Implements a subset of Unix's wc program.  Counts the number of words in
 * from the standard input, and reports the number of words when the end of
 * file (Ctrl-D) is reached.  A word is any "content" that falls
 * between whitespace (whitespace considered to the same as for C'
 * iswspace(), i.e. 9-13, and 32.
 * @author Joe Montgomery
 */
public class Wc
{   
    public int wordCount(String input) throws IOException
    {
	int words = 0;
	FileInputStream stream = new FileInputStream(input);
	Reader r = new BufferedReader(new InputStreamReader(stream));
	StreamTokenizer st = new StreamTokenizer(r);

	st.resetSyntax();
	st.whitespaceChars(0,32);
	st.wordChars(33,127);
	st.ordinaryChar(10);
	st.ordinaryChars(33,47);
	st.ordinaryChars(58,64);
	st.ordinaryChars(91,96);
	st.ordinaryChars(123,127);
	
	// Counts the words
	try
	    {
		st.nextToken();
		while (st.ttype != st.TT_EOF)
		    {
			st.nextToken();
			words = words + 1;
		    }
	    }
	catch(IOException e) 
	    { 
		System.err.println("Something has gone horribly, horribly wrong in the word counter"); 
	    }
	return words;
    }
}
=======
package processor;import java.io.*;/** * Implements a subset of Unix's wc program.  Counts the number of words in * from the standard input, and reports the number of words when the end of * file (Ctrl-D) is reached.  A word is any "content" that falls * between whitespace (whitespace considered to the same as for C' * iswspace(), i.e. 9-13, and 32. * @author Joe Montgomery */public class Wc{       public int wordCount(String input) throws IOException    {	int words = 0;	FileInputStream stream = new FileInputStream(input);	Reader r = new BufferedReader(new InputStreamReader(stream));	StreamTokenizer st = new StreamTokenizer(r);	st.resetSyntax();	st.whitespaceChars(0,32);	st.wordChars(33,127);	st.ordinaryChar(10);	st.ordinaryChars(33,47);	st.ordinaryChars(58,64);	st.ordinaryChars(91,96);	st.ordinaryChars(123,127);		// Counts the words	try	    {		st.nextToken();		while (st.ttype != st.TT_EOF)		    {			st.nextToken();			words = words + 1;		    }	    }	catch(IOException e) 	    { 		System.err.println("Something has gone horribly, horribly wrong in the word counter"); 	    }	return words;    }}
>>>>>>> 35a35230355cd1cd9deb61925155eef54330b5eb
