#include <stdio.h>
#include "calculator.h"

int main(void) {

    double a = 10;
    double b = 5;

    double addition = calculate(a, b, '+');
    double subtraction = calculate(a, b, '-');
    double multiplication = calculate(a, b, '*');
    double division = calculate(a, b, '/');

    printf("Addition: %.2f\n", addition);
    printf("Subtraction: %.2f\n", subtraction);
    printf("Multiplication: %.2f\n", multiplication);
    printf("Division: %.2f\n", division);

    return 0;
}