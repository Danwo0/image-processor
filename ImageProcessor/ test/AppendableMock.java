import java.io.IOException;

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
