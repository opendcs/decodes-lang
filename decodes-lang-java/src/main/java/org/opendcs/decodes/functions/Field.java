package org.opendcs.decodes.functions;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.opendcs.decodes.exec.DataBuilder.Position;
import org.opendcs.decodes.exec.DecodesExecutionContext;
import org.opendcs.decodes.spi.DecodesFunction;

//@AutoServive
public class Field implements DecodesFunction
{

    @Override
    public void execute(DecodesExecutionContext context, List<String> args)
    {
        int chars = Integer.parseInt(args.get(2).substring(0, 1));
        var f = context.getMessageData().slice(context.currentPosition(), chars);
        System.out.println(f);        
        //var value = Double.parseDouble(f.asCharBuffer().toString());
        
        context.emitData(Integer.parseInt(args.get(3)), null, StandardCharsets.US_ASCII.decode(f).toString(), new Position(context.currentPosition(), chars));
        context.advancePosition(chars);
    }
    
}
