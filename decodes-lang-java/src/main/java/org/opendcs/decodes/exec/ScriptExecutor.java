package org.opendcs.decodes.exec;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import org.opendcs.decodes.CompiledScript;
import org.opendcs.decodes.operations.DecodesOperation;

public final class ScriptExecutor
{
    

    public static void execute(CompiledScript script, DecodesExecutionContext<?> context)
    {
        String currentLabel = script.getFirstLabel();
        var opStack = toStack(script.getOperationsFor(currentLabel));
        while (!opStack.isEmpty())
        {
            var op = opStack.pop();
            op.execute(context);
            var label = context.currentLabel();
            if (label != null && !currentLabel.equals(label))
            {
                opStack.clear();
                opStack.addAll(toStack(script.getOperationsFor(label)));
            }
        }
        
    }

    private static Deque<DecodesOperation> toStack(List<DecodesOperation> ops)
    {
        return new ArrayDeque<>(ops.reversed());
    }
}
