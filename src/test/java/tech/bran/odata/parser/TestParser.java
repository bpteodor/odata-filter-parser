package tech.bran.odata.parser;


import lombok.NonNull;
import org.antlr.v4.runtime.CharStreams;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tech.bran.odata.ast.ASTBuilder;
import tech.bran.odata.ast.ASTNode;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Teodor Bran
 */
public class TestParser {

    final ODataParser underTest = new ODataParser();

    @NonNull
    static String[][] validExpressions() {
        return new String[][]{
                {"id == '01' or id == '02'", "id=='01' OR id=='02'"},
                {"id eq '01' or id eq '02'", "id=='01' OR id=='02'"},
                {"id == '01' or id eq '02'", "id=='01' OR id=='02'"}

                //{"not id == '01'", "NOT(id=='01')"}
        };
    }


    @ParameterizedTest(name = "[{index}] expr: {0}")
    @MethodSource("validExpressions")
    public void testValidExpressionsWithAST(String input, String expected) {

        final ASTBuilder builder = new ASTBuilder();

        final ASTNode root = underTest.parseFilter(CharStreams.fromString(input), builder);

        assertThat(root.toString()).isEqualTo(expected);
    }

}
