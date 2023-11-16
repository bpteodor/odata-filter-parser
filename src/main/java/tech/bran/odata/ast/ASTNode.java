package tech.bran.odata.ast;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Teodor Bran
 */
public interface ASTNode {

    record Identifier(String name) implements ASTNode {
        @Override
        public String toString() {
            return name;
        }
    }

    record Literal(Object value) implements ASTNode {
        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    record Compare(ASTNode op1, String operator, ASTNode op2) implements ASTNode {
        @Override
        public String toString() {
            return op1 + operator + op2;
        }
    }

    record Relational(String operator, Collection<ASTNode> operands) implements ASTNode {
        @Override
        public String toString() {
            return operands.stream().map(Objects::toString).collect(Collectors.joining(" " + operator + " "));
        }
    }

    record Unary(Operator.Unary op, ASTNode node) implements ASTNode {
        @Override
        public String toString() {
            return op.toString() + "(" + node + ")";
        }
    }
}
