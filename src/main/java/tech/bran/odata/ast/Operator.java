package tech.bran.odata.ast;

/**
 * @author Teodor Bran
 */
public class Operator {

    enum Unary {
        NOT;

        @Override
        public String toString() {
            return "!";
        }
    }

}
