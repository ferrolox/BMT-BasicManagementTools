package typewriter;

@SuppressWarnings("JavaPrintToLogpoint")
public class Main {

    static void main() {

        // Primitive types
        byte smallNumber = 10;
        short mediumNumber = 1000;
        int integer = 100000;
        long largeNumber = 10000000000L;

        float floatNumber = 5.5f;
        double doubleNumber = 10.25;

        char character = 'A';
        boolean active = true;

        // Numeric calculations
        int sum = smallNumber + mediumNumber;
        double result = integer + doubleNumber;

        IO.println("Sum: " + sum);
        IO.println("Result: " + result);

        // Integer division
        int integerDivision = 10 / 3;
        double decimalDivision = 10.0 / 3.0;

        IO.println("Integer division: " + integerDivision);
        IO.println("Decimal division: " + decimalDivision);

        int number = 10;
        double convertedNumber = number;

        IO.println("Implicit conversion: " + convertedNumber);

        double decimal = 10.75;
        int castedNumber = (int) decimal;
        // int uncastedNumber = decimal;

        IO.println("Explicit cast: " + castedNumber);

        Integer boxed = integer;

        int unboxed = boxed;

        IO.println("Boxed: " + boxed);
        IO.println("Unboxed: " + unboxed);

        // String conversion
        String numberAsString = String.valueOf(integer);

        // Parsing
        int parsedNumber = Integer.parseInt("42");
        double parsedDecimal = Double.parseDouble("3.14");

        IO.println("String: " + numberAsString);
        IO.println("Parsed integer: " + parsedNumber);
        IO.println("Parsed decimal: " + parsedDecimal);

        // Formatted output
        IO.print("Integer: %d | Decimal: %.2f | Character: %c | Active: %b%n"
            .formatted(
                integer,
                doubleNumber,
                character,
                active
            )
        );

        // Complex formatted output
        IO.print("Name: %-10s | Integer: %06d | Decimal: %8.2f | Percent: %6.1f%% | Hex: 0x%X%n"
            .formatted(
                "Alice",
                42,
                12.3456,
                87.5,
                255
            )
        );
    }
}