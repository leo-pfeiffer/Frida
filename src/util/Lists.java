// Todo this isn't working yet

package util;

import java.util.List;
/** A simple class to get the first and last element of an object that
 * implements java.util.List.
 * @author 190026921 */
public abstract class Lists {

    /** Get the first element of the list.
     * @param list An object implementing list.
     * @return The first element E of the list. */
    public static <E> E getFirst(List<E> list) {
        if (list != null & list.size() != 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /** Get the last element of the list.
     * @param list An object implementing list.
     * @return The first element E of the list. */
    public static <E> E getLast(List<E> list) {
        if (list != null & list.size() != 0) {
            return list.get(list.size() - 1);
        } else {
            return null;
        }
    }
}