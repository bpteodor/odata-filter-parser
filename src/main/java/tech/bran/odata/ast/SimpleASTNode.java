package tech.bran.odata.ast;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Teodor Bran
 */
@Data
@Accessors(fluent = true)
public class SimpleASTNode {
    final Type type;

    String token;

    SimpleASTNode operand1;
    String operator;
    SimpleASTNode operand2;

    public static SimpleASTNode id(String name) {
        return new SimpleASTNode(Type.identifier).token(name);
    }

    public static SimpleASTNode literal(String val) {
        return new SimpleASTNode(Type.literal).token(val);
    }

    public static SimpleASTNode unary(String op, SimpleASTNode val) {
        return new SimpleASTNode(Type.unary).operand1(val).operator(op);
    }

    public static SimpleASTNode binary(SimpleASTNode val1, String op, SimpleASTNode val2) {
        return new SimpleASTNode(Type.binary).operand1(val1).operator(op).operand2(val2);
    }

    enum Type {
        identifier, literal, unary, binary
    }


    @Override
    public String toString() {
        switch (type) {
            case identifier:
                return token;
            case literal:
                return "'" + token + "'";
            case unary:
                return operator + " " + operand1;
            case binary:
                return operand1 + " " + operator + " " + operand2;
        }
        throw new IllegalStateException("Unknown AST node type");
    }
}
