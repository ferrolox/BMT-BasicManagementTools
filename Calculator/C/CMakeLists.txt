cmake_minimum_required(VERSION 3.20)

project(Calculator C)

set(CMAKE_C_STANDARD 17)

add_executable(Calculator
    src/main.c
    src/calculator.c
    src/operations.c
)