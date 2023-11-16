package tech.bran.odata.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 * @author Teodor Bran
 */
public class ODataParser {

    /**
     * parse $select parameter
     *
     * @param input   $select expression
     * @param visitor Visitor implementation
     */
    public <T> T parseSelect(CharStream input, SelectVisitor<T> visitor) {
        final SelectLexer lexer = new SelectLexer(input);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final SelectParser parser = new SelectParser(tokens);
        return parser.expr().accept(visitor);
    }

    /**
     * parse $filter parameter
     *
     * @param input   $filter expression
     * @param visitor Visitor implementation
     */
    public <T> T parseFilter(CharStream input, FilterVisitor<T> visitor) {
        final FilterLexer lexer = new FilterLexer(input);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final FilterParser parser = new FilterParser(tokens);
        return parser.orExpr().accept(visitor);
    }

}
