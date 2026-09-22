package org.opendcs.decodes.operations;

import org.opendcs.decodes.exec.DecodesExecutionContext;

public final class RedirectDecodesOperation implements DecodesOperation
{
    public static final String OPERATION_NAME = "REDIRECT";


    private final String targetLabel;

    public RedirectDecodesOperation(String targetLabel)
    {
        this.targetLabel = targetLabel;
    }

    @Override
    public String operationName()
    {
        return OPERATION_NAME;
    }

    public String getTargetLabel()
    {
        return targetLabel;
    }

    @Override
    public int repeat()
    {
        return 1; // doesn't repeat.
    }

    @Override
    public String toString() 
    {
        return String.format("N/A -> Redirect:%s", targetLabel);
    }

    @Override
    public void execute(DecodesExecutionContext<?> context) 
    {
        // This *should* be the last command on the line, so it's fairly easy to just change what set we're using
        // However, this is not the only 'redirect', the whole Scan thing, or perhaps custom things need to
        // also be able to "jump" to something else. The consider is nested elements. It would just be
        // good to do so *without* throwing.
        System.out.println("Would redirect... somehow, maybe something with the execution context?");
    }
    
}
