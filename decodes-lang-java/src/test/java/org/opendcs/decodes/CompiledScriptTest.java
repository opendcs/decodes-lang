package org.opendcs.decodes;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.opendcs.decodes.exec.DataBuilder;
import org.opendcs.decodes.exec.DecodesExecutionContext;
import org.opendcs.decodes.exec.ScriptExecutor;
import org.opendcs.decodes.exec.DataBuilder.Position;

class CompiledScriptTest
{
    @Test
    void test_parsing() throws Exception
    {
        final String scriptIn = """
            HG: 2x, f(s, a,5D' ', 1), 2f(s,a,5D' ',2) > TA
            TA: 2(2x, f(s,a,6D' ', 3))
              """.trim();
        var  scriptCompiled = ScriptCompiler.compile(scriptIn);

        assertNotNull(scriptCompiled);
        System.out.println(scriptCompiled.toString());
    }


    @Test
    void test_executation() throws Exception
    {
        final String scriptIn = """
            HG: 2x, f(s, a,5D' ', 1), 2f(s,a,5D' ',2) > TA
            TA: 2(2x, f(s,a,6, 3))
              """.trim();
        var scriptCompiled = ScriptCompiler.compile(scriptIn);

        assertNotNull(scriptCompiled);

        var data = """
xx5.1 1.223 12.5 xx123456xx654321
    """.stripTrailing();

        System.out.println(scriptCompiled.toString());
        var dataOut = ScriptExecutor.execute(scriptCompiled, ByteBuffer.wrap(data.getBytes(StandardCharsets.US_ASCII)), new Data())
                                    .build();

        assertFalse(dataOut.data.isEmpty());
        dataOut.data.forEach((k,v) ->
        {
            System.out.println(k);
            v.forEach(tv ->
            {
                System.out.println("\t" + tv);
            });
        });
    }

    public static class Data implements DataBuilder<Data>
    {
        public final Map<Integer, ArrayList<TimeValue>> data = new HashMap<>();

        @Override
        public void addValue(int sensor, ZonedDateTime dateTime, Object value, Position dataPosition)
        {
            var sensorData = data.computeIfAbsent(sensor, s -> new ArrayList<>());
            sensorData.add(new TimeValue(dateTime, value, dataPosition));
        }

        @Override
        public Data build()
        {
            return this;
        }

    }

    public static record TimeValue(ZonedDateTime time, Object value, Position dataPosition)
    {
    }
}
