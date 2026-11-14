package org.opendcs.decodes.exec;

import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import org.opendcs.decodes.CompiledScript;
import org.opendcs.decodes.operations.DecodesOperation;

public final class ScriptExecutor
{
    

    public static <T> DataBuilder<T> execute(CompiledScript script, ByteBuffer dataIn, DataBuilder<T> emitter)
    {
        var context = new DecodesExecutionContext(dataIn.asReadOnlyBuffer(), emitter);
        String currentLabel = script.getFirstLabel();
        var opStack = toStack(script.getOperationsFor(currentLabel));
        while (!opStack.isEmpty() && context.moreData())
        {
            var op = opStack.pop();
            System.out.println("running(" +currentLabel + "):" + op);
            op.execute(context);
            var label = context.currentLabel();
            if (label != null && !currentLabel.equals(label))
            {
                opStack.clear();
                System.out.println("Changing label");
                currentLabel=label;
                opStack.addAll(toStack(script.getOperationsFor(label)));
            }
        }
        return emitter;
    }

    private static Deque<DecodesOperation> toStack(List<DecodesOperation> ops)
    {
        return new ArrayDeque<>(ops);
    }
}
