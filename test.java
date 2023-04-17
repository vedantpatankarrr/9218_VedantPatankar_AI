
//import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

class State implements Comparable<State> {

    // Constructor for array initialization
    State(int array[][]) {
        arr = array;

    }

    State(List<State> p, int array[][]) {
        arr = array;
        path.addAll(p);
        path.add(new State(array));
    }

    int heuristic, level, cost;
    int[][] arr = new int[4][4];

    List<State> path = new ArrayList<State>();

    // overriding the compare method
    @Override
    public int compareTo(State s) {
        return (this.cost - s.cost);
    }

}

public class test {

    // static int initialarr[][] = { { 1, 2, 3, 4 }, { 5, 6, 0, 8 }, { 9, 10, 7, 11}, { 13, 14, 15, 12 } };

    static int initialarr[][] = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 0, 10, 11, 12}, { 9, 13, 14, 15 } };


    static List<int[][]> visitedStates = new ArrayList<int[][]>();

    static int targetmatrix[][] = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 0 } };
    static PriorityQueue<State> q = new PriorityQueue<State>();

    static int blank_x, blank_y; // variables to store the x and y coordinates of the blank space

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        System.out.println("Enter the matrix");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                initialarr[i][j] = in.nextInt();
            }
        }

        State initialState = new State(initialarr);
        visitedStates.add(initialState.arr);

        //Initialize level to 1

        initialState.level = 1;
        initialState.heuristic = findDifference(initialState.arr);

        // calculate the cost of the initial state
        initialState.cost = initialState.level + initialState.heuristic;

        q.add(initialState);
        printMatrix(initialarr);

        solvePuzzle();

    }

    static boolean solvePuzzle() {

        // continue till solution is found

        while (!q.isEmpty()) {
            State currentState = q.poll();

            // Stop if the current state is the target state (goal state)
            if (isSolutionFound(currentState.arr)) {
                System.out.println("Solution Found");
                printMatrices(currentState.path);
                return true;

            }

            // Check all cases for the blank space

            if (isUpValid(currentState.arr)) {
                State s = new State(currentState.path, moveUp(currentState.arr));

                // iNcrease the level by 1
                s.level = currentState.level + 1;

                // calculate new matrix heuristic and cost
                s.heuristic = findDifference(s.arr);
                s.cost = s.level + s.heuristic;

                // mark as visited
                q.add(s);
                visitedStates.add(s.arr);

            }

            if (isDownValid(currentState.arr)) {
                State s = new State(currentState.path, moveDown(currentState.arr));
                s.level = currentState.level + 1;
                s.heuristic = findDifference(s.arr);
                s.cost = s.level + s.heuristic;

                q.add(s);
                visitedStates.add(s.arr);

            }

            if (isLeftValid(currentState.arr)) {
                State s = new State(currentState.path, moveLeft(currentState.arr));
                s.level = currentState.level + 1;
                s.heuristic = findDifference(s.arr);
                s.cost = s.level + s.heuristic;

                q.add(s);
                visitedStates.add(s.arr);

            }

            if (isRightValid(currentState.arr)) {
                State s = new State(currentState.path, moveRight(currentState.arr));
                s.level = currentState.level + 1;
                s.heuristic = findDifference(s.arr);
                s.cost = s.level + s.heuristic;
                q.add(s);
                visitedStates.add(s.arr);

            }

        }

        return false;

    }

    private static void printMatrices(List<State> path) {
        for (State s : path) {
            printMatrix(s.arr);
        }

    }

    private static void printPath(List<State> path) {
        for (State s : path) {
            printMatrix(s.arr);
        }

    }

    static boolean isUpValid(int[][] matrix) {

        int x = -1;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (matrix[i][j] == 0) {
                    x = i;
                    break;
                }
            }
        }
        if (x == 0)
            return false;

        int[][] t = moveUp(matrix);

        if (!isVisited(t) && isSolvable(t))
            return true;
        else
            return false;

    }

    static boolean isDownValid(int[][] matrix) {

        int x = -1;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (matrix[i][j] == 0) {
                    x = i;
                    break;
                }
            }
        }
        if (x == 3)
            return false;

        int[][] t = moveDown(matrix);

        if (!isVisited(t) && isSolvable(t))
            return true;
        else
            return false;

    }

    static boolean isLeftValid(int[][] matrix) {

        int y = -1;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (matrix[i][j] == 0) {
                    y = j;
                    break;
                }
            }
        }
        if (y == 0)
            return false;

        int[][] t = moveLeft(matrix);

        if (!isVisited(t) && isSolvable(moveLeft(matrix)))
            return true;
        else
            return false;

    }

    static boolean isRightValid(int[][] matrix) {

        int y = -1;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (matrix[i][j] == 0) {
                    y = j;
                    break;
                }
            }
        }

        if (y == 3)
            return false;

        int[][] t = moveRight(matrix);

        if (!isVisited(t) && isSolvable(moveRight(matrix)))
            return true;
        else
            return false;

    }

    static boolean isSolvable(int[][] matrix) {
        return true;
    }

    static int[][] moveUp(int[][] x) {

        int[][] matrix = new int[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = x[i][j];
            }
        }
        getBlankPosition(matrix);

        int temp = matrix[blank_x][blank_y];
        matrix[blank_x][blank_y] = matrix[blank_x - 1][blank_y];
        matrix[blank_x - 1][blank_y] = temp;

        return matrix;

    }

    static int[][] moveDown(int[][] x) {

        int[][] matrix = new int[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = x[i][j];
            }
        }
        getBlankPosition(matrix);

        int xchg = matrix[blank_x][blank_y];
        matrix[blank_x][blank_y] = matrix[blank_x + 1][blank_y];
        matrix[blank_x + 1][blank_y] = xchg;

        return matrix;

    }

    static int[][] moveRight(int[][] x) {

        int[][] matrix = new int[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = x[i][j];
            }
        }
        getBlankPosition(matrix);

        int xchg = matrix[blank_x][blank_y];
        matrix[blank_x][blank_y] = matrix[blank_x][blank_y + 1];
        matrix[blank_x][blank_y + 1] = xchg;

        return matrix;

    }

    static int[][] moveLeft(int[][] x) {

        int[][] matrix = new int[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = x[i][j];
            }
        }
        getBlankPosition(matrix);

        int xchg = matrix[blank_x][blank_y];
        matrix[blank_x][blank_y] = matrix[blank_x][blank_y - 1];
        matrix[blank_x][blank_y - 1] = xchg;

        return matrix;

    }

    static void getBlankPosition(int[][] x) {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (x[i][j] == 0) {
                    blank_x = i;
                    blank_y = j;
                }
            }
        }

    }

    static boolean isSolutionFound(int array[][]) {
        int i, j;
        for (i = 0; i < 4; i++)
            for (j = 0; j < 4; j++) {
                if (array[i][j] != targetmatrix[i][j])
                    return false;
            }
        return true;
    }

    static boolean checkEqualMatrix(int arr1[][], int arr2[][]) {
        int i, j;
        for (i = 0; i < 4; i++)
            for (j = 0; j < 4; j++) {
                if (arr1[i][j] != arr2[i][j])
                    return false;
            }
        return true;
    }

    static boolean isVisited(int[][] currentmatrix) {

        for (int[][] matrix : visitedStates) {

            if (checkEqualMatrix(currentmatrix, matrix)) {
                return true;
            }

        }
        return false;
    }

    static void printMatrix(int p[][]) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(p[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("");
    }

    // To calculate the number of misplaced tiles in the given matrix

    static int findDifference(int[][] arr) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (arr[i][j] != targetmatrix[i][j]) {
                    count++;
                }
            }
        }
        return count;

    }
}