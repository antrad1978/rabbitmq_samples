package samples.testing;

import static org.junit.Assert.*;

import org.junit.Test;

import org.junit.Assert; 
import samples.CreditCardValidator;

public class UnitTest {
	
	protected void setUp() throws Exception {
		
	}

	@Test
	public void test() {
		Long test=1234567890123456L;
		boolean res = CreditCardValidator.isValid(test);
		Assert.assertEquals(false, res);
	}

}
