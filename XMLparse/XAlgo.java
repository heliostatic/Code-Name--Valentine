package XMLparse;

/**
 * Interface for an algorithm to execute on an XMLText object
 */
public interface XAlgo {

    /**
     * @param host the Empty being operated on
     * @param input the input to the algorithm
     * Function to execute on an Empty object
     */
    public abstract Object forEmpty(Empty host, Object input);

    /**
     * @param host the GlyphList being operated on
     * @param input the input to the algorithm
     * Function to execute on a GlyphList
     */
    public abstract Object forGlyphList(GlyphList host, Object input);

}
