import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import test_func.AllInOne;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AllInOne.class)
public class AllInOneTest {

	@Test
	public void testStaticMethd() {
		PowerMockito.mockStatic(AllInOne.class);
	}

}
