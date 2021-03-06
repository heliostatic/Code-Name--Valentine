package parser;
import XMLparse.*;
import processor.*;
public class LinkVisitor implements XAlgo{


    /* Singleton Pattern */
    public final static LinkVisitor Singleton = new LinkVisitor();
    private LinkVisitor()
    {
    }

    
    
    /**
     * @param host the Empty host XMLText
     * @param input the Link we are processing
     * @return the Link
     */
    public Object forEmpty(Empty host,Object input){
	return input;
    }

    /**
     * @param host the GlyphList host XMLText
     * @param input the Link we are processing
     * @return the Link, modified by GlyphLinkVisitor
     * Executes GlyphLinkVisitor on the Item,
     * and returns LinkVisitor on the rest of the XMLText
     */
    public Object forGlyphList(GlyphList host,Object input){
	host.getItem().execute(GlyphLinkVisitor.Singleton,input);
	return host.getXmltext().execute(LinkVisitor.Singleton,input);
    }
}
