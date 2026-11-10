package org.opendcs.decodes;

import java.util.Collections;
import java.util.List;
import java.util.SequencedMap;

import org.opendcs.decodes.operations.DecodesOperation;

public final class CompiledScript
{
    private final SequencedMap<String, List<DecodesOperation>> statements;

    public CompiledScript(SequencedMap<String, List<DecodesOperation>> statements)
    {
        this.statements = Collections.unmodifiableSequencedMap(statements);
    }

    public String getFirstLabel()
    {
        return statements.sequencedEntrySet().getFirst().getKey();
    }

    public List<DecodesOperation> getFirst()
    {
        return statements.sequencedEntrySet().getFirst().getValue();
    }

    public List<DecodesOperation> getOperationsFor(String label)
    {
        return statements.getOrDefault(label, List.of());
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
