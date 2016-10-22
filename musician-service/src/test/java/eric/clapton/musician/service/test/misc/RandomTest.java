package eric.clapton.musician.service.test.misc;

import java.security.SecureRandom;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class RandomTest {
	@Test
	public void testRandomInRange() {
		final int FROM = 10;
		final int TO = 10;
		Random random = new SecureRandom();

		for (int i = 0; i < 5; i++) {
			int result = FROM + random.nextInt(TO + 1 - FROM);
			Assert.assertTrue(result >= FROM);
			Assert.assertTrue(result <= TO);
		}
	}
}
