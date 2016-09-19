# Genetic Algorithm

It solves Travelling salesman problem using gentic algorithm.

## Run

```
$ cd GA/src
$ javac Main.java
$ java Main
```

## Configure

### Population#tsplib_cities

**args:**

filepath - absolute path to the tsplib file you would like to use

type     - "points" if your file format is pair of x and y co-ordinates (EUC_2D)
           "dist" if your file format is a 2D distance matrix of cities (UPPER_DIAG_ROW)

It loads the tsplib file given to it and stores it in a 2D distance matix.

### Population#random_point_cities

**args:**
range_of_dist - Maximum value of distance between any two cities

Generates a symmetric 2D distance matrix with random distance values.
