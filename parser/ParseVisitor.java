<<<<<<< HEAD
package parser;
import XMLparse.*;
import processor.*;
public class ParseVisitor implements XAlgo{


    /* Singleton Pattern */
    public final static ParseVisitor Singleton = new ParseVisitor();
    private ParseVisitor()
    {
    }

    
    /*
     * @param host the Empty host XMLText
     * @param input the MarkovChain we are building
     * @return null
     * Does nothing
     */
    public Object forEmpty(Empty host,Object input){
	return null;
    }

    /**
     * @param host the GlyphList host XMLText
     * @param input the MarkovChain we are building
     * @return null
     * Executes GlyphParseVisitor on the Item,
     * and ParseVisitor on the remaining XMLText
     */
    public Object forGlyphList(GlyphList host,Object input){
	host.getItem().execute(GlyphParseVisitor.Singleton,input);
	host.getXmltext().execute(ParseVisitor.Singleton,input);
	return null;
    }
}
=======
package parser;import XMLparse.*;import processor.*;public class ParseVisitor implements XAlgo{    /* Singleton Pattern */    public final static ParseVisitor Singleton = new ParseVisitor();    private ParseVisitor()    {    }        /*     * @param host the Empty host XMLText     * @param input the MarkovChain we are building     * @return null     * Does nothing     */    public Object forEmpty(Empty host,Object input){	return null;    }    /**     * @param host the GlyphList host XMLText     * @param input the MarkovChain we are building     * @return null     * Executes GlyphParseVisitor on the Item,     * and ParseVisitor on the remaining XMLText     */    public Object forGlyphList(GlyphList host,Object input){	host.getItem().execute(GlyphParseVisitor.Singleton,input);	host.getXmltext().execute(ParseVisitor.Singleton,input);	return null;    }}
>>>>>>> 35a35230355cd1cd9deb61925155eef54330b5eb
