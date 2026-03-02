com.example.DummyTest

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DummyTest {

    @Test
    public void testPipelineWorks() {
        System.out.println("CI/CD pipeline is working!");
        assertTrue(true);  // This will always pass, just testing workflow
    }
}