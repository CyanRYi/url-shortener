package tech.sollabs.urlshortener.util;

/**
 * @author Cyan
 * @since 0.9
 * @see <a href="https://rocksea.tistory.com/348">[Rocksea - knowledge creator]</a>
 */
public class Base62 {

  private static final char[] BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

  public static String encode(long value) {
    final StringBuilder sb = new StringBuilder();
    do {
      int i = (int)(value % 62);
      sb.append(BASE62[i]);
      value /= 62;
    } while (value > 0);
    return sb.toString();
  }
}
