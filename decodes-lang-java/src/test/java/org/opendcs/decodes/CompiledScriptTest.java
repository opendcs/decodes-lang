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
import org.opendcs.decodes.exec.DecodesExecutionContext;
import org.opendcs.decodes.exec.ScriptExecutor;

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
            TA: 2(2x, f(s,a,6D' ', 3))
              """.trim();
        var scriptCompiled = ScriptCompiler.compile(scriptIn);

        assertNotNull(scriptCompiled);

        var data = """
        xx5.1 1.223 12.5 xx123456  xx654321
        """.trim();

        var ctx = new TestContext(ByteBuffer.wrap(data.getBytes(StandardCharsets.UTF_8)));
        System.out.println(scriptCompiled.toString());
        ScriptExecutor.execute(scriptCompiled, ctx);

        assertFalse(ctx.collection.isEmpty());

    }

    public static class TestContext implements DecodesExecutionContext<Map<Integer, ArrayList<Object>>>
    {
        private final ByteBuffer data;
        private final Map<Integer, ArrayList<Object>> collection = new HashMap<>();
        private String currentLabel;

        public TestContext(ByteBuffer data)
        {
            this.data = data;
        }

        @Override
        public ByteBuffer getMessageData()
        {
            return this.data;
        }

        @Override
        public Map<Integer, ArrayList<Object>> getDataCollection()
        {
            return this.collection;
        }

        @Override
        public void addVariable(int sensor, ZonedDateTime zdt, Object value)
        {
            collection.computeIfAbsent(sensor, s -> new ArrayList<>()).add(value);
        }

        @Override
        public String currentLabel()
        {
            return currentLabel;
        }

        @Override
        public void currentLabel(String label)
        {
            this.currentLabel = label;
        }

    }
}
