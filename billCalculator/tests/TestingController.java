package billCalculator.tests;

public class TestingController {
    public static boolean isUnique(int numb) {
        int[] numbers = {1, 2, 3, 4, 10};
        for (int number : numbers) {
            if (numb == number) {
                return false;
            }
        }
        return true;
    }

    public static double ceilRound(double sum) {
        return Math.ceil(sum);
    }

    public static double floorRound(double sum) {
        return Math.floor(sum);
    }

    public static double calcTips(int tips, Double sum) {
        return sum * tips / 100;
    }
}
