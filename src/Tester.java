import java.lang.reflect.*;
import java.util.*;

public class Tester {

    private static String SPACE = "    ";

    public static void main(String[] args) {

        Class<Class>[] testedClasses = new Class[]
                { Tested1.class, Tested2.class, Tested3.class, Tested4.class };

        for (Class c : testedClasses) {
            try {
                start(c);
            } catch (Exception e) {
                System.out.println(SPACE + e.getMessage());
            }
        }
    }

    public static void start(Class classToTest) throws Exception {
        System.out.println("Testing class: " + classToTest.getSimpleName());

            // get all methods of given class
        ArrayList<Method> beforeSuiteMethods = new ArrayList<>();
        TreeSet<String> testMethodNames = new TreeSet<>();
        ArrayList<Method> afterSuiteMethods = new ArrayList<>();
        Method[] classToTestMethods = classToTest.getMethods();
        for (Method method : classToTestMethods) {
            if (method.getAnnotation(BeforeSuite.class) != null)
                beforeSuiteMethods.add(method);
            else if (method.getAnnotation(AfterSuite.class) != null)
                afterSuiteMethods.add(method);
            else {
                Test testAnnotation = method.getAnnotation(Test.class);
                if (testAnnotation != null) {
                        // if less then 0 or greater 10 - do not invoke when testing
                    if (testAnnotation.priority() < 0 | testAnnotation.priority() > 10) {
                        System.out.println(SPACE + SPACE + "Method " + method.getName() + " with priority level " + testAnnotation.priority() + " will be skipped");
                        continue;
                    }
                    testMethodNames.add("" + String.format("%2d", testAnnotation.priority()) + '@' + method.getName());
                }
            }
        }

            // check fatal conditions
        if (beforeSuiteMethods.size() > 1)
            throw new RuntimeException("Fatal: methods annotated with @BeforeSuite more than 1");
        if (afterSuiteMethods.size() > 1)
            throw new RuntimeException("Fatal: methods annotated with @AfterSuite more than 1");

            // build invocation plan
        LinkedList<Method> methodsToInvoke = new LinkedList<>();
        NavigableSet<String> testMethoNamesDesc = testMethodNames.descendingSet();
        if (beforeSuiteMethods.size() > 0)
            methodsToInvoke.addFirst(beforeSuiteMethods.get(0));
        for (String methodName : testMethoNamesDesc) {
            for (int i = 0; i < classToTestMethods.length; i++) {
                if (classToTestMethods[i].getName().equals(methodName.substring(methodName.indexOf('@') + 1)))
                    methodsToInvoke.add(classToTestMethods[i]);
            }
        }
        if (afterSuiteMethods.size() > 0)
            methodsToInvoke.add(afterSuiteMethods.get(0));

            // instantiate object & invoke methods in planned order
        Constructor[] constructors = classToTest.getConstructors();
        Object o = constructors[0].newInstance();
        for (Method method : methodsToInvoke)
            System.out.println(SPACE + "Invocation of: " + method.getName() + "\tresult: " + method.invoke(o));
    }
}
