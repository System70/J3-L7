
public class Tested3 extends Tested1 {

    @AfterSuite
    public String afterTest2() { return "!! after test to throw exception !!"; }
}
