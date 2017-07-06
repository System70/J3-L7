
public class Tested1 {

    @BeforeSuite
    public String beforeTest() { return "!! before test !!"; }

    @Test(priority = 1)
    public String testPrior1a() { return "!! test a with priority level 1 !!"; }

    @Test(priority = 1)
    public String testPrior1b() { return "!! test b with priority level 1 !!"; }

    @Test(priority = 7)
    public String testPrior7() { return "!! test with priority level 7 !!"; }

    @Test
    public String testPriorDefault1() { return "!! test 1 with default pririty level (5) !!"; }

    @Test
    public String testPriorDefault2() { return "!! test 2 with default pririty level (5) !!"; }

    @Test(priority = 0)
    public String testPrior0() { return "!! test with priority level 0 !!"; }

    @Test
    public String testPrior4() { return "!! test with priority leve 4 !!"; }

    @AfterSuite
    public String afterTest() { return "!! after test !!"; }
}
