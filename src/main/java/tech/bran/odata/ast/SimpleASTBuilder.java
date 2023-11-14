package tech.bran.odata.ast;

import lombok.Data;
import org.antlr.v4.runtime.tree.RuleNode;
import tech.bran.odata.parser.ODFBaseVisitor;
import tech.bran.odata.parser.ODFParser;

/**
 * @author Teodor Bran
 */
@Data
public class SimpleASTBuilder extends ODFBaseVisitor<SimpleASTNode> {

    SimpleASTNode root;

    @Override
    public SimpleASTNode visitExpression(ODFParser.ExpressionContext ctx) {
        return root = ctx.getChild(0).accept(this);
    }

    @Override
    public SimpleASTNode visitEqualityExpr(ODFParser.EqualityExprContext ctx) {
        return SimpleASTNode.binary(
                ctx.getChild(0).accept(this),
                "=",
                ctx.getChild(2).accept(this));
    }

    @Override
    public SimpleASTNode visitChildren(RuleNode node) {
        if (node.getChildCount() == 2)
            return SimpleASTNode.unary(
                    node.getChild(0).getText(),
                    node.getChild(1).accept(this));

        if (node.getChildCount() == 3)
            return SimpleASTNode.binary(
                    node.getChild(0).accept(this),
                    node.getChild(1).getText(),
                    node.getChild(2).accept(this));

        return super.visitChildren(node);
    }

    @Override
    public SimpleASTNode visitValue(ODFParser.ValueContext ctx) {
        //super.visitValue(ctx);
        return SimpleASTNode.id(ctx.getText());
    }

    @Override
    public SimpleASTNode visitLiteral(ODFParser.LiteralContext ctx) {
        return SimpleASTNode.literal(ctx.getText());
    }

    @Override
    public SimpleASTNode visitUnaryExpr(ODFParser.UnaryExprContext ctx) {
        return SimpleASTNode.id(ctx.getChild(1).getText());
    }
}
