package billCalculator.tests;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ControllerTest {

    @BeforeEach
    void setUp() {
        System.out.println("Initial setup...");
        System.out.println("Code executes only ones");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Tests finished");
    }

    @Test
    void isUnique() {
        System.out.println("        boolean test = TestingController.isUnique(7);\n" +
                "        Assert.assertTrue(test);");
        boolean test = TestingController.isUnique(7);
        Assert.assertTrue(test);
    }
    @Test
    void ceilRound(){
        System.out.println("        double test = TestingController.ceilRound(7.004);\n" +
                "        Assert.assertEquals(test,8.0, 8.0);");
        double test = TestingController.ceilRound(7.004);
        Assert.assertEquals(test,8.0, 8.0);
    }

    @Test
    void floorRound(){
        System.out.println("        double test = TestingController.floorRound(45.95);\n" +
                "        Assert.assertEquals(test,45.0, 45.0);");
        double test = TestingController.floorRound(45.95);
        Assert.assertEquals(test,45.0, 45.0);
    }

    @Test
    void calcTips(){
        System.out.println("        double test = TestingController.calcTips(5, 100.0);\n" +
                "        Assert.assertEquals(test, 5.0, 5.0);");
        double test = TestingController.calcTips(5, 100.0);
        Assert.assertEquals(test,5.0, 5.0);
    }

}