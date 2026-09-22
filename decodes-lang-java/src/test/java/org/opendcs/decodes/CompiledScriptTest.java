package org.opendcs.decodes;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

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
}
