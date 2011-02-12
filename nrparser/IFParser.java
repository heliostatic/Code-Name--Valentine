package nrparser;
import processor.*;

public class IFParser {

    private TokenList tokenlist;

    public IFParser(String theFile) throws java.io.IOException{
	tokenlist = new TokenList(theFile);
	String next = tokenlist.pop();
	if (!(next.equalsIgnoreCase("<corpus>"))){
	    throw new IllegalArgumentException("bad corpus(1)");
	}
    }

    /**
     * @param original the original String
     * @return the converted string
     * Converts &gt to > and &lt to <
     */
    public String convertspecial(String original)
    {
	if (original.equals("&gt")){
	    return ">";
	}
	if (original.equals("&lt")){
	    return "<";
	}
	return original;
    }

    /**
     * @return the next sequence
     * Parses the XML file, and returns the next sequence.  Does
     * error-checking to some extent, but is not guaranteed to work with bad
     * inputs.
     */
    public Sequence nextSequence() throws java.io.IOException{
	String next = tokenlist.pop();
	if (next.equalsIgnoreCase("</corpus>")){
	    return null;
	}
	else if (!(next.equalsIgnoreCase("<node>"))){
	    throw new IllegalArgumentException("bad corpus(2)");
	}
	else {
	    Sequence temp = new Sequence();
	    String word;
	    String code;
	    Link link;
	    next = tokenlist.pop();
	    while (!(next.equalsIgnoreCase("</node>"))){
		if (next.equalsIgnoreCase("<word>")) {
		    word = "";
		    next = tokenlist.pop();
		    while (!(next.equalsIgnoreCase("</word>"))){
			word = word + next;
			next = tokenlist.pop();
		    }
		    word = convertspecial(word);
		    temp.setWord(word);
		}
		else if (next.equalsIgnoreCase("<code>")){
		    code = "";
		    next = tokenlist.pop();
		    while (!(next.equalsIgnoreCase("</code>"))){
			code = code + next;
			next = tokenlist.pop();
		    }
		    temp.setKey(code);
		}
		else if (next.equalsIgnoreCase("<edge>")){
		    link = new Link();
		    String prob;
		    String nextnode;
		    next = tokenlist.pop();
		    while (!(next.equalsIgnoreCase("</edge>"))){
			if (next.equalsIgnoreCase("<prob>")) {
			    prob = "";
			    next = tokenlist.pop();
			    while (!(next.equalsIgnoreCase("</prob>"))){
				prob = prob + next;
				next = tokenlist.pop();
			    }
			    link.setProb(Double.parseDouble(prob));
			}
			else if (next.equalsIgnoreCase("<node>")){
			    nextnode = "";
			    next = tokenlist.pop();
			    while (!(next.equalsIgnoreCase("</node>"))){
				nextnode = nextnode + next;
				next = tokenlist.pop();
			    }
			    link.setKey(nextnode);
			}
			else {
			    throw new IllegalArgumentException("bad corpus(3)");
			}
			next = tokenlist.pop();
		    }
		    temp.addLink(link);
		}
		else {
		    throw new IllegalArgumentException("bad corpus(4)");
		}
		next = tokenlist.pop();
	    }
	    return temp;
	}
    }
}
