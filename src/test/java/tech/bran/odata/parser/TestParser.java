package tech.bran.odata.parser;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStreams;
import org.junit.jupiter.api.Test;

/**
 * @author Teodor Bran
 */
public class TestParser {

    final ODataFilter underTest = new ODataFilter();

    @Test
    public void test1() {
        final String input = "id == '01' or id eq '02'";

        final TestListener listener = new TestListener();

        underTest.parse(CharStreams.fromString(input), listener);

        //Assertions.assertThat(listener.data).isEqualTo("teo = kaboom");
    }

    @Data
    @Slf4j

    public static class TestListener extends ODFBaseVisitor<Object> {

        String data = "";

        /*@Override
        public Object visitName(ODFParser.NameContext ctx) {
            data += ctx.getText() + "= ";
            return super.visitName(ctx);
        }

        @Override
        public Object visitMessage(ODFParser.MessageContext ctx) {
            data += ctx.getText();
            return super.visitMessage(ctx);
        }*/
    }
}
