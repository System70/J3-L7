
public class Tested4 {

    @Test(priority = 1)
    public String testPrior1() { return "!! test with priority level 1 !!"; }

    @Test(priority = 10)
    public String testPrior10() { return "!! test with priority level 10 !!"; }

    @Test(priority = -1)
    public String testPriorMinus1() { return "!! test with priority level -1 !!"; }

    @Test
    public String testPriorDef() { return "!! test with default pririty level (5) !!"; }

    @Test(priority = 15)
    public String testPrior15() { return "!! test with priority level 15 !!"; }

    @AfterSuite
    public String afterTest() { return "!! after test !!"; }
}
