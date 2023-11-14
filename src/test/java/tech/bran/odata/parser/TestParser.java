package tech.bran.odata.parser;


import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.RuleNode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Teodor Bran
 */
public class TestParser {

    final ODataFilter underTest = new ODataFilter();

    @NonNull
    static String[][] validExpressions() {
        return new String[][]{
                {"id == '01' or id == '02'", "id = '01' or id = '02'"},
                {"id eq '01' or id eq '02'", "id = '01' or id = '02'"},
                {"id == '01' or id eq '02'", "id = '01' or id = '02'"}
        };
    }


    @ParameterizedTest(name = "[{index}] expr: {0}")
    @MethodSource("validExpressions")
    public void testValidExpressions(String input, String expected) {

        final TestListener listener = new TestListener();

        final String result = underTest.parse(CharStreams.fromString(input), listener);

        assertThat(result).isEqualTo(expected);
    }

    @Data
    @Slf4j

    public static class TestListener extends ODFBaseVisitor<String> {

        String data = "";

        protected String defaultResult() {
            return "";
        }

        @Override
        public String visitExpression(ODFParser.ExpressionContext ctx) {
            return super.visitExpression(ctx);
        }

        protected String aggregateResult(String aggregate, String nextResult) {
            return aggregate + nextResult;
        }

        /*@Override
        public String visitOrExpr(ODFParser.OrExprContext ctx) {
            return ctx.getChild(0).accept(this) + " " + ctx.getChild(1).getText() + " " + ctx.getChild(2).accept(this);
        }*/

        @Override
        public String visitEqualityExpr(ODFParser.EqualityExprContext ctx) {
            return ctx.getChild(0).accept(this) + " = " + ctx.getChild(2).accept(this);
        }

        @Override
        public String visitChildren(RuleNode node) {
            if (node.getChildCount() == 3)
                return node.getChild(0).accept(this) + " " + node.getChild(1).getText() + " " + node.getChild(2).accept(this);
            return super.visitChildren(node);
        }

        @Override
        public String visitValue(ODFParser.ValueContext ctx) {
            return ctx.getText();
        }

        @Override
        public String visitUnaryExpr(ODFParser.UnaryExprContext ctx) {
            return ctx.getChild(1).getText();
        }
    }
}
