package demo8;

import org.junit.Test;
import org.komamitsu.failuredetector.PhiAccuralFailureDetector;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author lili
 * @date 2022/4/10 0:20
 */
public final class HealthChecker {

    @Test
    public void test() {
        PhiAccuralFailureDetector detector = new PhiAccuralFailureDetector.Builder().build();
        detector.heartbeat(10);
        detector.heartbeat(11);
        detector.heartbeat(12);
        detector.heartbeat(13);
        System.out.println(detector.phi());
        boolean available = detector.isAvailable();
        assertFalse(available);
    }


    @Test
    public void test2() {
        PhiAccuralFailureDetector failureDetector = new PhiAccuralFailureDetector.Builder().build();
        long now = 1420070400000L;
        for (int i = 0; i < 300; i++) {
            long timestampMillis = now + i * 1000;

            if (i > 290) {
                double phi = failureDetector.phi(timestampMillis);
                if (i == 291) {
                    assertTrue(1 < phi && phi < 3);
                    assertTrue(failureDetector.isAvailable(timestampMillis));
                } else if (i == 292) {
                    assertTrue(3 < phi && phi < 8);
                    assertTrue(failureDetector.isAvailable(timestampMillis));
                } else if (i == 293) {
                    assertTrue(8 < phi && phi < 16);
                    assertTrue(failureDetector.isAvailable(timestampMillis));
                } else if (i == 294) {
                    assertTrue(16 < phi && phi < 30);
                    assertFalse(failureDetector.isAvailable(timestampMillis));
                } else if (i == 295) {
                    assertTrue(30 < phi && phi < 50);
                    assertFalse(failureDetector.isAvailable(timestampMillis));
                } else if (i == 296) {
                    assertTrue(50 < phi && phi < 70);
                    assertFalse(failureDetector.isAvailable(timestampMillis));
                } else if (i == 297) {
                    assertTrue(70 < phi && phi < 100);
                    assertFalse(failureDetector.isAvailable(timestampMillis));
                } else {
                    assertTrue(100 < phi);
                    assertFalse(failureDetector.isAvailable(timestampMillis));
                }
                continue;
            } else if (i > 200) {
                if (i % 5 == 0) {
                    double phi = failureDetector.phi(timestampMillis);
                    assertTrue(0.1 < phi && phi < 0.5);
                    assertTrue(failureDetector.isAvailable(timestampMillis));
                    continue;
                }
            }
            failureDetector.heartbeat(timestampMillis);
            assertTrue(failureDetector.phi(timestampMillis) < 0.1);
            assertTrue(failureDetector.isAvailable(timestampMillis));
        }
    }


}
