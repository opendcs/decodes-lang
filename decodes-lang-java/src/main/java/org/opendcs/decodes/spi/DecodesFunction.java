package org.opendcs.decodes.spi;

import java.util.List;

import org.opendcs.decodes.exec.DecodesExecutionContext;

public interface DecodesFunction
{
    void execute(DecodesExecutionContext context, List<String> args);
}