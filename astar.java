import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

class State implements Comparable<State> {

    State(int array[][]) {
        arr = array;
    }

    State(List<State> p, int array[][]) {
        arr = array;
        path.addAll(p);
        path.add(new State(array));
    }

    int heuristic, level, cost;
    int[][] arr = new int[3][3];

    List<State> path = new ArrayList<State>();

    // overriding the compare method
    @Override
    public int compareTo(State s) {
        return (this.cost - s.cost);
    }

}

public class astar {

 

    static int initialarr[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0} };


    static List<int[][]> visitedStates = new ArrayList<int[][]>();

    static int targetmatrix[][] = {  { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0} };
    static PriorityQueue<State> q = new PriorityQueue<State>();

    static int blank_x, blank_y; // variables to store the x and y coordinates of the blank space

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        System.out.println("Enter the matrix");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                initialarr[i][j] = in.nextInt();
            }
        }

        State initialState = new State(initialarr);
        visitedStates.add(initialState.arr);

        initialState.level = 1;
        initialState.heuristic = findDifference(initialState.arr);

        initialState.cost = initialState.level + initialState.heuristic;

        q.add(initialState); 
        printMatrix(initialarr);

        solvePuzzle();

    }

    static boolean solvePuzzle() {
        int temp = 1;
        while (!q.isEmpty()) {
            State currentState = q.poll();
            if (isSolutionFound(currentState.arr)) {
                System.out.println("Solution Found");
                printMatrices(currentState.path);
                System.out.println("Goal state achieved");
                return true;
                

            }
            else
            {
                temp++;
               
                
            }

            if (isUpValid(currentState.arr)) {
                State s = new State(currentState.path, moveUp(currentState.arr));

                s.level = currentState.level + 1;

                s.heuristic = findDifference(s.arr);
                s.cost = s.level + s.heuristic;

                q.add(s);
                visitedStates.add(s.arr);

            }
            else
            {
                temp++;
               
                
            }
            

            if (isDownValid(currentState.arr)) {
                State s = new State(currentState.path, moveDown(currentState.arr));
                s.level = currentState.level + 1;
                s.heuristic = findDifference(s.arr);
                s.cost = s.level + s.heuristic;

                q.add(s);
                visitedStates.add(s.arr);

            }
            else
            {
                temp++;
               
                
            }
            if (isLeftValid(currentState.arr)) {
                State s = new State(currentState.path, moveLeft(currentState.arr));
                s.level = currentState.level + 1;
                s.heuristic = findDifference(s.arr);
                s.cost = s.level + s.heuristic;

                q.add(s);
                visitedStates.add(s.arr);

            }
            else
            {
                temp++;
               
                
            }
            if (isRightValid(currentState.arr)) {
                State s = new State(currentState.path, moveRight(currentState.arr));
                s.level = currentState.level + 1;
                s.heuristic = findDifference(s.arr);
                s.cost = s.level + s.heuristic;
                q.add(s);
                visitedStates.add(s.arr);

            }
            else
            {
                temp++;
               
                
            }
            if (temp == 4)
            {
                System.out.print("No Solution");
                break;
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

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
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

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[i][j] == 0) {
                    x = i;
                    break;
                }
            }
        }
        if (x == 2)
            return false;

        int[][] t = moveDown(matrix);

        if (!isVisited(t) && isSolvable(t))
            return true;
        else
            return false;

    }

    static boolean isLeftValid(int[][] matrix) {

        int y = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
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

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[i][j] == 0) {
                    y = j;
                    break;
                }
            }
        }

        if (y == 2)
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

        int[][] matrix = new int[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
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

        int[][] matrix = new int[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
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

        int[][] matrix = new int[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
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

        int[][] matrix = new int[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
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

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (x[i][j] == 0) {
                    blank_x = i;
                    blank_y = j;
                }
            }
        }

    }

    static boolean isSolutionFound(int array[][]) {
        int i, j;
        for (i = 0; i < 3; i++)
            for (j = 0; j < 3; j++) {
                if (array[i][j] != targetmatrix[i][j])
                    return false;
            }
        return true;
    }

    static boolean checkEqualMatrix(int arr1[][], int arr2[][]) {
        int i, j;
        for (i = 0; i < 3; i++)
            for (j = 0; j < 3; j++) {
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
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(p[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("");
    }


    static int findDifference(int[][] arr) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (arr[i][j] != targetmatrix[i][j]) {
                    count++;
                }
            }
        }
        return count;

    }
}