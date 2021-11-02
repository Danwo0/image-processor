import java.io.IOException;
import java.nio.CharBuffer;

/**
 * This is a mock Readable that always throws IOException for all methods for testing purposes.
 */
public class ReadableMock implements Readable {

  @Override
  public int read(CharBuffer cb) throws IOException {
    throw new IOException("Readable failed");
  }

}
