package calculator;

@SuppressWarnings("JavaPrintToLogpoint")
public class Main {

    @SuppressWarnings("unused")
    static void main(String[] args) {

        Calculator calculator = new Calculator();

        Operation addition = new Addition();
        Operation subtraction = new Subtraction();
        Operation multiplication = new Multiplication();
        Operation division = new Division();

        IO.println("Addition: " + calculator.calculate(addition, 10, 5));
        IO.println("Subtraction: " + calculator.calculate(subtraction, 10, 5));
        IO.println("Multiplication: " + calculator.calculate(multiplication, 10, 5));
        IO.println("Division: " + calculator.calculate(division, 10, 5));
    }
}