package com.company;

public class Main {
    static class Direction {
        public String name;
        public int deltaX;
        public int deltaY;
        public Direction previous;
        public Direction next;

        Direction(String name, int deltaX, int deltaY) {
            this.name = name;
            this.deltaX = deltaX;
            this.deltaY = deltaY;
        }

        @Override
        public String toString() {
            StringBuilder answer = new StringBuilder();
            answer.append("[").append(this.name).append("; (").append(this.deltaX).append("; ").append(this.deltaY).append(")]");
            return answer.toString();
        }
    }

    static int dimentionX;
    static int dimentionY;
    static int startX = 0;
    static int startY = 0;
    static int currentX;
    static int currentY;
    static int perimetr;
    static Direction searchDirection;
    static int[][] field;

    public static void main(String[] args) {
        /* Task*/
//        You are given a map in form of a two-dimensional integer grid where 1 represents land and 0 represents water.
//        Grid cells are connected horizontally/vertically (not diagonally). The grid is completely surrounded by water,
//        and there is exactly one island (i.e., one or more connected land cells).
//        The island doesn't have "lakes" (water inside that isn't connected to the water around the island).
//        One cell is a square with side length 1. The grid is rectangular, width and height don't exceed 100. Determine the perimeter of the island.
//        Example Input:
//        [[0,1,0,0],
//         [1,1,1,0],
//         [0,1,0,0],
//         [1,1,0,0]]
//
//        Output: 16


        //Init common directions
        Direction up = new Direction("UP", 0, -1);
        Direction left = new Direction("LEFT",  -1, 0);
        Direction down = new Direction("DOWN",  0, 1);
        Direction right = new Direction("RIGHT",  1, 0);
        up.next = left; up.previous=right;
        left.next = down; left.previous=up;
        down.next = right; down.previous=left;
        right.next = up; right.previous=down;

        //Init field

        field = new int[][]{{0, 1, 0,  0},
                            {1, 1, 1,  0},
                            {0, 1, 0,  0},
                            {1, 1, 0,  0},};


        //init variables and searchDirection
        dimentionX = field[0].length;
        dimentionY = field.length;
        currentX = 0;
        currentY = 0;
        perimetr = 0;
        searchDirection = up;

        System.out.println("Dimentiyons of field: x=" + dimentionX + "; y=" + dimentionY);


        //find first earth cell
        boolean foundCell = false;
        while (!foundCell) {
            for (int x = 0; x < dimentionX; x++) {
                for (int y = 0; y < dimentionY; y++) {
                    if (field[y][x] == 1) {
                        foundCell = true;
                        startX = x;
                        currentX = x;
                        startY = y;
                        currentY = y;
                      //  perimetr++;
                        break;
                    }
                    if (foundCell) break;
                }
                if (foundCell) break;
            }
        }
        System.out.println("Start point is ");
        status();

        //Start cycle to pass through the perimetr
        boolean isPerimetrClosed = false;
        while (!isPerimetrClosed) {

            // Checking do I get start point passed one full circle
            if (perimetr >= 4 && currentX == startX && currentY == startY) {
                isPerimetrClosed = true;
                System.out.println("the perimeter is closed ");
            }

            //Checking if next move is possible

            else if (!isMovePossible(currentX, currentY, searchDirection)) {
                isPerimetrClosed = true;
              //todo carefully check break
                //break;
            }
            //Check next step inside the array
            else makeMove(currentX, currentY, searchDirection);
        }
        status();
    }

    private static void status() {
        System.out.println("Current point is (" + currentX + "; " + currentY + ") = " + field[currentY][currentX] +
                ";  direction = " + searchDirection + "; Perimetr = " + perimetr+"\n");
    }

    private static void makeMove(int x, int y, Direction currentDirection) {
        //todo продумать работу с углами и краями
        if((x+currentDirection.deltaX<0)||(x+currentDirection.deltaX>dimentionX-1)||(y+currentDirection.deltaY<0)||(y+currentDirection.deltaY>dimentionY-1)) {
            searchDirection=currentDirection.next;
            perimetr++;
        }
           else if(field[y+currentDirection.deltaY][x+currentDirection.deltaX]==0) {
                perimetr++;
                searchDirection=currentDirection.next;
            } else if(field[y+currentDirection.deltaY][x+currentDirection.deltaX]==1) {
                currentX=x+currentDirection.deltaX;
                currentY=y+currentDirection.deltaY;
                searchDirection=currentDirection.previous;
            }

       // System.out.println("Move made. new status is");
       // status();
    }

    private static boolean isMovePossible(int currentX, int currentY, Direction currentDirection) {
        boolean answer = false;

        //Check next step inside the array
        if (currentX < 0 || currentX > dimentionX - 1 || currentY < 0 || currentY > dimentionY - 1) {
            System.out.println("Something went wrong, you come out of the field border");
            status();
        }
        //Check out of left side
        else if (currentX < startX) {
            System.out.println("Something went wrong, you come over the left border");
            status();
        } else answer = true;
        return answer;
    }
}



