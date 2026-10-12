#include "calculator.h"
#include "operations.h"

double calculate(double a, double b, char operation) {
    switch (operation) {
        case '+':
            return add(a, b);

        case '-':
            return subtract(a, b);

        case '*':
            return multiply(a, b);

        case '/':
            return divide(a, b);

        default:
            return 0;
    }
}