import org.junit.jupiter.api.Test;
import springbook.Calculator;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class CalcSumTest {
    @Test
    void sumOfNumbers() throws IOException {
        Calculator calc = new Calculator();
        int sum = calc.sum(getClass().getResource(
                "numbers.txt"
        ).getPath());

        assertThat(sum).isEqualTo(10);
    }
}
