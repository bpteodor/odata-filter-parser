package tech.bran.odata;

/**
 * @author Teodor Bran
 */
public class Check {

    public static void that(boolean expr, String msg) {
        if (!expr) throw new IllegalArgumentException(msg);
    }

    public static void notNull(Object o, String msg) {
        that(o != null, msg);
    }
}
