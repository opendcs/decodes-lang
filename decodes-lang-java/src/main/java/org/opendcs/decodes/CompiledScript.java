package org.opendcs.decodes;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SequencedMap;

import org.opendcs.decodes.exec.DecodesExecutionContext;
import org.opendcs.decodes.operations.DecodesOperation;
import org.opendcs.decodes.operations.RedirectDecodesOperation;

public final class CompiledScript
{
    private final SequencedMap<String, List<DecodesOperation>> statements;

    public CompiledScript(SequencedMap<String, List<DecodesOperation>> statements)
    {
        this.statements = Collections.unmodifiableSequencedMap(statements);
    }

    public void execute(DecodesExecutionContext<?> context)
    {
        Map.Entry<String,List<DecodesOperation>> currentSet = statements.sequencedEntrySet().getFirst();
        
        String label = currentSet.getKey();
        while (statements.get(label) != null && (label = execute(label, context)) != null )
        {
            label = execute(label, context);
        }
    }    

    private String execute(String currentLabel, DecodesExecutionContext<?> context)
    {
        List<DecodesOperation> currentOperations = statements.get(currentLabel);
        String ret = null;
        for (var op: currentOperations)
        {
            if (op instanceof RedirectDecodesOperation redirectOp)
            {
                ret = redirectOp.getTargetLabel();
                break;
            }
            else
            {
                op.execute(context);
            }
        }
        return ret;
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
