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
    public FilterParser.ExpressionContext parse(CharStream input) {
        final FilterLexer lexer = new FilterLexer(input);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final FilterParser parser = new FilterParser(tokens);
        return parser.expression();
    }

    /**
     * parse & visit
     *
     * @param input   OData filter expression
     * @param visitor Visitor implementation
     */
    public <T> T parse(CharStream input, FilterVisitor<T> visitor) {
        return parse(input).accept(visitor);
    }

    //ParseTreeWalker.DEFAULT.walk(listener, tree); ?
}
