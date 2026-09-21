# OpenDCS DECODES Language specification


## Purpose

This provides an independent specification and basic java implementation of the DECODES processing language
used by DECODES. Current documentation is available (here)[https://opendcs-env.readthedocs.io/en/stable/legacy-decoding-guide.html#execution-of-format-statements]
and will be merged into this repository over time.

at this time only the following gradle commands will do anything interesting:

`./gradlew :decodes-lang:grun --info` will run antlr4's GUI that shows how the syntax tree of what we're calling a 
decodes 'script' (all lables and format statements combined into a single text file separated by new lines) to
see that the grammar is correctly processing the required elements. The input file is in decodes-lang/src/test/resources/basic_test.script.

`./gradlew :decodes-lang-java:run --info` will run the grammar on the same input file as above, however, it will
process the generated tree into something that can then be executed by a scripting engine. At this time it just
renders what it processed to the terminal to verify operations. Actual tests will be created to show actual processing
as decodes operations are ported from OpenDCS's code into the new scheme of things.