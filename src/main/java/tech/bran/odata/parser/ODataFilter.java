package tech.bran.odata.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 * @author Teodor Bran
 */
public class ODataFilter {

    /**
     * parse the expression and return the root AST
     *
     * @param input OData filter expression
     * @return root AST
     */
    public ODFParser.ExpressionContext parse(CharStream input) {
        final ODFLexer lexer = new ODFLexer(input);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final ODFParser parser = new ODFParser(tokens);
        return parser.expression();
    }

    /**
     * parse & visit
     *
     * @param input   OData filter expression
     * @param visitor Visitor implementation
     */
    public void parse(CharStream input, ODFVisitor<?> visitor) {
        parse(input).accept(visitor);
    }

    //ParseTreeWalker.DEFAULT.walk(listener, tree); ?
}
