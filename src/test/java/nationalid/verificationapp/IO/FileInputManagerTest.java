package nationalid.verificationapp.IO;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FileInputManagerTest {
    // TODO: FileManager instance should be injectable or mockable to test reading
    // from files

    @Test
    public void testCutContentIntoLines() {
        String multiLineContent = "a\r\nb\r\nc\r\nd";

        String[] cutContent = FileInputManager.cutContentIntoLines(multiLineContent);

        assertTrue("Content cut into expected parts", cutContent.length == 4);

    }
}
