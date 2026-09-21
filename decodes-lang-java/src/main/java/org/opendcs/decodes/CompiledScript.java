package org.opendcs.decodes;

import java.util.Collections;
import java.util.List;
import java.util.SequencedMap;

import org.opendcs.decodes.exec.DecodesExecutionContext;
import org.opendcs.decodes.operations.DecodesOperation;

public final class CompiledScript
{
    private final SequencedMap<String, List<DecodesOperation>> statements;

    public CompiledScript(SequencedMap<String, List<DecodesOperation>> statements)
    {
        this.statements = Collections.unmodifiableSequencedMap(statements);
    }

    public void execute(DecodesExecutionContext<?> context)
    {
        var currentSet = statements.sequencedEntrySet().getFirst();

        // This should probably execute individually *and* manually expand out groups 
        // so that label redirect determinations can be made.
        currentSet.getValue().forEach(op -> op.execute(context));
    }



    @Override
    public String toString()
    {
        var sb = new StringBuilder();
        statements.forEach((k,v) ->
        {
            sb.append(k);
            
            v.forEach(op ->
            {
                sb.append("\t").append(op.toString()).append(System.lineSeparator());
            });
        });
        return sb.toString();
    }
}
