package test_func;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ClassWithStaticMethods.class)
//Final classes,classes with final,private,static or native methods 

public class ClassWithStatictest {

	@Test
	public void test() {
		//Mocking the class 
		PowerMockito.mockStatic(ClassWithStaticMethods.class);
		
		//defining the values methods should return when invoked
		PowerMockito.when(ClassWithStaticMethods.firstMethod(Mockito.anyString()))
		.thenReturn("Hello World");
	
		//Calling the function
	    String first=ClassWithStaticMethods.firstMethod("Welcome");
	   
	    assertEquals("Hello World",first);
	 }
	
	
	  @Test 
	  public void testNoArgsConstructors() throws Exception { 
		  //Creating a mock object 
      ClassWithStaticMethods mock=Mockito.mock(ClassWithStaticMethods.class);
	  
	   PowerMockito.whenNew(ClassWithStaticMethods.class).withNoArguments().
	  thenReturn(mock);
	  //Whenever the no-arg constructor of that class is invoked ,a mock instance
	  //should be returned
	  
	  }
	 
	
	
	@Test
	public void testAllArgConstructor() throws Exception {
		//Creating a mock object
		ClassWithStaticMethods mock=Mockito.mock(ClassWithStaticMethods.class);
		
		
		PowerMockito.whenNew(ClassWithStaticMethods.class).withAnyArguments().thenReturn(mock);
		
		
		
		//Whenever the no-arg constructor of that class is invoked ,a mock instance
		//should be returned 
		
	}
	
	@Test
	public void testFinalMethod() {
		ClassWithStaticMethods mock=PowerMockito.mock(ClassWithStaticMethods.class);
		//Class with final,private, static or native methods should be mocked with PowerMockito
		Mockito.when(mock.finalMethod()).thenReturn("JAVA World");
		String welcome=mock.finalMethod();
	    assertEquals("JAVA World",welcome);
	}

	/*
	 * @Test public void testfinalPlaying() throws Exception {
	 * //ClassWithStaticMethods a=new ClassWithStaticMethods();
	 * //PowerMockito.verifyNew(ClassWithStaticMethods.class).withNoArguments();
	 * Mockito.when(a.finalMethod()).thenReturn("Hello"); String
	 * actual=a.finalMethod(); Mockito.verify(a.finalMethod());
	 * assertEquals("Hello", actual); }
	 */
}
