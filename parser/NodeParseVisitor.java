package parser;
import XMLparse.*;
import processor.*;
public class NodeParseVisitor implements XAlgo
{


    /* Singleton Pattern */
    public final static NodeParseVisitor Singleton = new NodeParseVisitor();
    private NodeParseVisitor()
    {
    }

    /**
     * @param host the Empty host XMLText, somewhere inside a <node> tag
     * @param input the Node we are building
     * @return input
     * returns the input Node
     */
    public Object forEmpty(Empty host, Object input)
    {
	return input;
    }

    /**
     * @param host the GlyphList host XMLText, somewhere inside a <node> tag
     * @param input the Node we are building
     * @return the Node, after modifications
     * Executes GlyphNodeParseVisitor on the Node
     * and returns the result of NodeParseVisitor on the rest of the XMLText
     */
    public Object forGlyphList(GlyphList host, Object input)
    {
	host.getItem().execute(GlyphNodeParseVisitor.Singleton,input);
	return host.getXmltext().execute(NodeParseVisitor.Singleton,input);
    }
}
