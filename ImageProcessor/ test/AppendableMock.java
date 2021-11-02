import java.io.IOException;

/**
 * This is a mock Appendable that always throws IOException for all methods for testing purposes.
 */
public class AppendableMock implements Appendable {

  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException("Appendable failed.");
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException("Appendable failed.");
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException("Appendable failed.");
  }
}
