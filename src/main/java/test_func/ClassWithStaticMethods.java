package test_func;

public class ClassWithStaticMethods {
	public static String firstMethod(String name) {
        return "Hello " + name + " !";
    }
 
    public static String secondMethod() {
        return "Hello no one!";
    }
 
    public static String thirdMethod() {
        return "Hello no one again!";
    }
    
    public final String finalMethod() {
    	return "Hello no one";
    }
    
}
