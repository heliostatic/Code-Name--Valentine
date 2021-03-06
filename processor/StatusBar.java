package processor;

public class StatusBar
{
    private static final int UPDATE_RATE = 3000;  // Number of tokens to process before updating the status bar
    private static final int NUMBER_OF_PERIODS = 20;  // Number of periods in the status bar
    private static final int MAX_DIGITS = 15;  // Maximum number of ETA digits to print
    public final static int ONE_SECOND = 1000;   // How many milliseconds in a second
    private int totalTokens = 0;          // Total number of tokens in input files
    private int processedTokens = 0;      // Tokens processed to date
    private int processedPeriods = 0;     // Periods printed to date
    private int nextMilestone = 0;
    private int milestoneSize;
    private long startTime;

    public StatusBar(int tokens)
    {
	totalTokens = tokens;
	if (totalTokens <= NUMBER_OF_PERIODS) {
	    milestoneSize = 1;
	}
	else {
	    milestoneSize = totalTokens/NUMBER_OF_PERIODS;
	}
	startTime = System.currentTimeMillis();	
    }

    /**
     * Updates the status bar seen by the user.  The status bar displays one "." for each 5%
     * of the processing completed and next to that the estimated time to finish the creation
     * of the Hidden Markov Model.  This estimate does not include the time needed to modify
     * the Markov Model so that it is ready for output, or the time needed to write the Markov
     * Model to file.  The estimate is performed by keep track of the number of tokens processed,
     * and the total amount of time that has passed.
     */
    public void updateStatusBar()
    {
	if (processedTokens == 0) {
	    System.out.print("Status:   .");
	    processedPeriods += 1;
	    for (int i = 0; i < (MAX_DIGITS + (NUMBER_OF_PERIODS - processedPeriods)); i++) {
		System.out.print(" ");
	    }
	}
	
	processedTokens += 1;
	if ((processedTokens % UPDATE_RATE) == 0) {
	    removeSpaces();
	    while (processedTokens > nextMilestone) {
		nextMilestone = nextMilestone + milestoneSize;
		System.out.print(".");
		processedPeriods += 1;
	    }
	    updateETA();
	}
    }

    public void removeSpaces()
    {
	for (int i = 0; i < (MAX_DIGITS + (NUMBER_OF_PERIODS - processedPeriods)); i++) {
	    System.out.print("\b");
	}
    }
    
    public void updateETA()
    {
	double elapsedTime, eta, rate;
	String etaString;
	int digitsPrinted;
	elapsedTime = ((double)System.currentTimeMillis() - startTime);
	elapsedTime = elapsedTime/(double)ONE_SECOND;
	rate = processedTokens/elapsedTime;
	eta = ((double)(totalTokens-processedTokens))/rate;
	
	etaString = (new Double(eta)).toString();
	etaString = etaString.substring(0, etaString.indexOf("."));
	etaString = " " + etaString + "s";
	digitsPrinted = etaString.length();
	while (digitsPrinted < (MAX_DIGITS + (NUMBER_OF_PERIODS - processedPeriods))) {
	    System.out.print(" ");
	    digitsPrinted += 1;
	}
	System.out.print(etaString);
    }

    public void closeStatusBar()
    {
	removeSpaces();
	while (processedPeriods < NUMBER_OF_PERIODS) {
	    System.out.print(".");
	    processedPeriods += 1;
	}
	System.out.println("  Done!");
    }
	
	
}
