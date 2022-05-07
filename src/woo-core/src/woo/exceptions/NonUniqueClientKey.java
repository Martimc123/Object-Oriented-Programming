package woo.exceptions;

/** Exception thrown when a client key is duplicated. */
public class NonUniqueClientKey extends Exception {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201709021324L;

  /** Client key. */
  private String _key;

  /** @param key the duplicated key */
  public NonUniqueClientKey(String key) {
    _key = key;
  }

  public String getKey() {
    return _key;
  }

}
