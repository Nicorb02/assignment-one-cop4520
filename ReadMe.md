# Prime Number Finder Summary

## Approach:

This approach takes advantage of the multi-threading capabilities in Java. The work of finding prime numbers from 1 to 10^8 is divided among 8 different threads. Each thread works simultaneously on a specific range of numbers, optimizing the efficiency of the system. This approach is designed to reduce costs since the machine is rented by the minute, making better use of computational power and improving overall results.

## Correctness:

To ensure program accuracy, the top ten primes have been extracted and validated using an online tool. The program is designed to provide precise results.

## Compilation and Execution Steps:

1. Open the terminal and run:
    ```bash
    javac AssignmentOne.java
    ```
2. Then execute the compiled program:
    ```bash
    java AssignmentOne
    ```
3. View the results in the output file `primes.txt`.
