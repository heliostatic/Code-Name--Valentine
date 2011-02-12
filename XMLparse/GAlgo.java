package XMLparse;

/**
 * Interface for an algorithm to execute on a Glyph object
 */
public interface GAlgo {
    
    /**
     * @param host the Word being operated on
     * @param input the input to the algorithm
     * Function to execute on a Word
     */
    public abstract Object forWord(Word host, Object input);

    /**
     * @param host the TaggedItem being operated on
     * @param input the input to the algorithm
     * Function to execute on a TaggedItem
     */
    public abstract Object forTaggedItem(TaggedItem host, Object input);

}
