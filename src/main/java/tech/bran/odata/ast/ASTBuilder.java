package tech.bran.odata.ast;

import lombok.Data;
import org.antlr.v4.runtime.tree.RuleNode;
import tech.bran.odata.Check;
import tech.bran.odata.parser.FilterBaseVisitor;
import tech.bran.odata.parser.FilterParser;

import java.util.ArrayList;
import java.util.List;

import static tech.bran.odata.ast.Operator.Unary.NOT;

/**
 * @author Teodor Bran
 */
@Data
public class ASTBuilder extends FilterBaseVisitor<ASTNode> {


    @Override
    public ASTNode visitOrExpr(FilterParser.OrExprContext ctx) {
        return visitRelational(ctx, "OR");
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public ASTNode visitAndExpr(FilterParser.AndExprContext ctx) {
        return visitRelational(ctx, "AND");
    }

    public ASTNode visitRelational(RuleNode node, String op) {
        if (node.getChildCount() == 1)
            return super.visitChildren(node); // fall through

        Check.that(node.getChildCount() % 2 == 1, "odd number expected");
        final List<ASTNode> items = new ArrayList<>(node.getChildCount() / 2);
        for (int i = 0; i < node.getChildCount(); i += 2) {
            items.add(node.getChild(i).accept(this));
        }
        return new ASTNode.Relational(op, items);
    }

    @Override
    public ASTNode visitUnaryExpr(FilterParser.UnaryExprContext node) {
        if (node.getChildCount() == 1)
            return super.visitChildren(node); // fall through
        return new ASTNode.Unary(NOT, node.getChild(1).accept(this));
    }

    @Override
    public ASTNode visitCompExpr(FilterParser.CompExprContext node) {
        return new ASTNode.Compare(node.getChild(0).accept(this),
                operator(node.getChild(1).getText()),
                node.getChild(2).accept(this));
    }

    @Override
    public ASTNode visitPrimaryExpr(FilterParser.PrimaryExprContext ctx) {
        if (ctx.getChildCount() == 3) {
            super.visitChildren(ctx);
        }
        return super.visitChildren(ctx);
    }

    /**
     * reduce operators to simpler notations
     */
    String operator(String op) {
        return switch (op) {
            case "eq" -> "==";
            case "ne" -> "!=";
            // TODO add all
            default -> op;
        };
    }


    /*@Override
    public SimpleASTNode visitExpression(FilterParser.ExpressionContext ctx) {
        return root = ctx.getChild(0).accept(this);
    }

    @Override
    public SimpleASTNode visitEqualityExpr(FilterParser.EqualityExprContext ctx) {
        return SimpleASTNode.binary(
                ctx.getChild(0).accept(this),
                "=",
                ctx.getChild(2).accept(this));
    }*/

    /*@Override
    public ASTNode visitChildren(RuleNode node) {
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
    }*/

    @Override
    public ASTNode visitLiteral(FilterParser.LiteralContext ctx) {
        return new ASTNode.Literal(ctx.getText());
    }

    @Override
    public ASTNode visitIdentifier(FilterParser.IdentifierContext ctx) {
        return new ASTNode.Identifier(ctx.getText());
    }
}
