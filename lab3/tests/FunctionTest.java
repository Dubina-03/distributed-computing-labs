package tests;

import classes.Function;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


public class FunctionTest {
    @Test
    public void calculate(){
        Function f = new Function();

        double expected1 = Math.log(0 + 1);
        Assertions.assertEquals(expected1, f.calculate(0));

        double expected2 = Math.log(0.5 + 1);
        Assertions.assertEquals(expected2, f.calculate(0.5));

        double expected3 = Math.log(1 + 1);
        Assertions.assertEquals(expected3, f.calculate(1));
    }

}
