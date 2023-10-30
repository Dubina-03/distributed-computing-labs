package tests;

import classes.Function;
import classes.IntegralCalculation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.text.DecimalFormat;


public class IntegralTest {
    @Test
    public void calculation(){
        IntegralCalculation integral = new IntegralCalculation();
        Function f = new Function();
        DecimalFormat df = new DecimalFormat("#.#############");

        double a = 0;
        double b = 1;
        int n = 500;
        double h = (b - a) / n;
        int numThred = 1;
        double s = 0;
        for (int i = 1; i < n + 1; i++) {
            double x = a + i * h;
            s += f.calculate(x)*h;
        }
        Assertions.assertEquals(Double.parseDouble(df.format(s)), Double.parseDouble(df.format(integral.calculation(a, b, n, f, numThred))));

        a = 0;
        b = 1;
        n = 500;
        h = (b - a) / n;
        numThred = 100;
        s = 0;
        for (int i = 1; i < n + 1; i++) {
            double x = a + i * h;
            s += f.calculate(x)*h;
        }
        Assertions.assertEquals(Double.parseDouble(df.format(s)), Double.parseDouble(df.format(integral.calculation(a, b, n, f, numThred))));

        a = 0;
        b = 1;
        n = 1000000;
        h = (b - a) / n;
        numThred = 5;
        s = 0;
        for (int i = 1; i < n + 1; i++) {
            double x = a + i * h;
            s += f.calculate(x)*h;
        }
        Assertions.assertEquals(Double.parseDouble(df.format(s)), Double.parseDouble(df.format(integral.calculation(a, b, n, f, numThred))));

        a = 0;
        b = 1;
        n = 1000000;
        h = (b - a) / n;
        numThred = 50;
        s = 0;
        for (int i = 1; i < n + 1; i++) {
            double x = a + i * h;
            s += f.calculate(x)*h;
        }
        Assertions.assertEquals(Double.parseDouble(df.format(s)), Double.parseDouble(df.format(integral.calculation(a, b, n, f, numThred))));

        a = 1;
        b = 3;
        n = 1000;
        h = (b - a) / n;
        numThred = 20;
        s = 0;
        for (int i = 1; i < n + 1; i++) {
            double x = a + i * h;
            s += f.calculate(x)*h;
        }
        Assertions.assertEquals(Double.parseDouble(df.format(s)), Double.parseDouble(df.format(integral.calculation(a, b, n, f, numThred))));
    }

}