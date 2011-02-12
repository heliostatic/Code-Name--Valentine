package parser;
import XMLparse.*;
import processor.*;

public class CorpusParseVisitor implements XAlgo{


    /* Singleton Pattern */
    public final static CorpusParseVisitor Singleton = new CorpusParseVisitor();
    private CorpusParseVisitor()
    {
    }

    
    /**
     * @param host the Empty host XMLText, somewhere inside a <corpus> tag
     * @param input the MarkovChain we are building
     * @return null
     * Does Nothing
     */
    public Object forEmpty(Empty host,Object input){
	return null;
    }

    /**
     * @param host the GlyphList host XMLText, somewhere inside a <corpus> tag
     * @param input the MarkovChain we are building
     * @return null
     * Executes GlyphCorpusParseVisitor on the Item,
     * and CorpusParseVisitor on the rest of the XMLText
     */
    public Object forGlyphList(GlyphList host,Object input){
	host.getItem().execute(GlyphCorpusParseVisitor.Singleton,input);
	host.getXmltext().execute(CorpusParseVisitor.Singleton,input);
	return null;
    }
}
