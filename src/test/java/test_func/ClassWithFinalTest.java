package test_func;

import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

public class ClassWithFinalTest {

	@Test
	public void testWithNoArgs() throws Exception {
		ClassWithFinal mock=PowerMockito.mock(ClassWithFinal.class);
		
		PowerMockito.whenNew(ClassWithFinal.class).withNoArguments().thenReturn(mock);
		
	
		
	}

}
