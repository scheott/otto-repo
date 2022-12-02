// Names:Otto Schenk & Ben Terzich
// x500s:schen445 & terzi022

import java.util.*;

public class MyMaze{
    Cell[][] maze;

    public MyMaze(int rows, int cols) {

        maze = new Cell[rows][cols];
        //maze starts at 0,0
        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < cols; j++) {
                maze[i][j] = new Cell();

                if (i == rows - 1 && j == cols - 1) {
                    maze[i][j].setRight(false);
                    //maze ends at bottom right
                }
            }
        }
    }

    /* TODO: Create a new maze using the algorithm found in the writeup. */
    public static MyMaze makeMaze(int rows, int cols) {

        MyMaze newMaze = new MyMaze(rows, cols);
        Stack<int[]> stack =new Stack<>();

        int[] x = new int[2];
        int[] y = new int[2];


        stack.push(y);
        newMaze.maze[0][0].setVisited(true);//starts at top left corner

        while (!stack.isEmpty()) {

            x = (int[]) stack.peek();
            boolean allVisited = true;
            //each if statement below checks if the x values are in bounds and if they've been visited or note
            if (x[0] - 1 >= 0) {
                if (!newMaze.maze[x[0] - 1][x[1]].getVisited()) {
                    allVisited = false;
                }
            }

            if (x[1] - 1 >= 0) {
                if (!newMaze.maze[x[0]][x[1] - 1].getVisited()) {
                    allVisited = false;
                }
            }

            if (x[1] + 1 <= newMaze.maze[0].length - 1) {
                if (!newMaze.maze[x[0]][x[1] + 1].getVisited()) {
                    allVisited = false;
                }
            }

            if (x[0] + 1 <= newMaze.maze.length - 1) {
                if (!newMaze.maze[x[0] + 1][x[1]].getVisited()) {
                    allVisited = false;
                }
            }
            //if statement below, if all have been visited will break and repeat, else will continue
            int count = 0;
            if (allVisited == true) {
                stack.pop();
                count++;
            }
            while (allVisited == false) {
                //if all have been visited break out of while
                if (count != 0) {
                    break;
                }
                int newx = (int) (Math.random() * 4);

                if (newx == 0) {

                    if ((x[0] - 1) < 0 || newMaze.maze[x[0] - 1][x[1]].getVisited()) {}//first checks if its in bounds and has been visited
                    else {

                        newMaze.maze[x[0] - 1][x[1]].setBottom(false);//opens above curr
                        newMaze.maze[x[0] - 1][x[1]].setVisited(true);
                        x[0]--;

                        y = new int[2];
                        y[0] = x[0];
                        y[1] = x[1];
                        stack.push(y);//new arrays made as x changes
                        allVisited = true;
                    }
                } else if (newx == 1) {

                    if ((x[0] + 1) > newMaze.maze.length - 1 || newMaze.maze[x[0] + 1][x[1]].getVisited()) {}//checks out of bounds and that it hasnt been visited before
                    else {

                        newMaze.maze[x[0]][x[1]].setBottom(false);//opens below curr
                        newMaze.maze[x[0] + 1][x[1]].setVisited(true);
                        x[0]++;

                        y = new int[2];
                        y[0] = x[0];
                        y[1] = x[1];
                        stack.push(y);
                        allVisited = true;
                    }
                } else if (newx == 2) {//repeat about but with x[1] 
                    if ((x[1] - 1) < 0 || newMaze.maze[x[0]][x[1] - 1].getVisited()) {}
                    else {
                        
                        newMaze.maze[x[0]][x[1] - 1].setRight(false);//opens left of curr
                        newMaze.maze[x[0]][x[1] - 1].setVisited(true);
                        x[1]--;
                        
                        y = new int[2];
                        y[0] = x[0];
                        y[1] = x[1];
                        stack.push(y);
                        allVisited = true;
                    }
                } else {
                    if ((x[1] + 1) > newMaze.maze[0].length - 1 || newMaze.maze[x[0]][x[1] + 1].getVisited()) {}
                    else {

                        newMaze.maze[x[0]][x[1]].setRight(false);//opens right of curr
                        newMaze.maze[x[0]][x[1] + 1].setVisited(true);
                        x[1]++;

                        y = new int[2];
                        y[0] = x[0];
                        y[1] = x[1];
                        stack.push(y);
                        allVisited = true;
                    }
                }
            }
        }
        for (int i = 0; i < newMaze.maze.length; i++) {

            for (int j = 0; j < newMaze.maze[0].length; j++) {

                newMaze.maze[i][j].setVisited(false);//this clears the maze so Solve works
            }
        }
        return newMaze;
    }



    /* TODO: Print a representation of the maze to the terminal */
    public void printMaze(boolean path) {
        if(path == true) {

            for (int i = 0; i < (maze.length * 2) + 1; i++) {
                String row = "";

                if (i == 1) {
                    row += " ";//where maze begins
                } else {
                    row += "|";
                }

                for (int j = 0; j < maze[0].length; j++) {

                    if (i == 0 || i == maze.length * 2) {
                        row += "---|";
                        //top or bottom
                    } else {

                        if (i % 2 == 1) {
                            if (path && maze[i / 2][j].getVisited()) {
                                row += " * ";
                                //when solving its visited print *
                            } else {
                                row += "   ";
                            }

                            if (maze[i / 2][j].getRight()) {
                                row += "|";
                            } else {
                                row += " ";
                            }

                        } else {
                            if (maze[i / 2 - 1][j].getBottom())
                                row += "---|";
                            else
                                row += "   |";
                        }
                    }
                }
                System.out.println(row);

            }
        }
    }

    /* TODO: Solve the maze using the algorithm found in the writeup. */
    public void solveMaze() {

        Queue<int[]> queue = new LinkedList<>();

        int[] curr = new int[2];
        int[] x = new int[2];
        queue.add(x);

        while (queue.size() > 0) {//loops while queue isn't empty

            curr = (int[]) queue.remove();
            maze[curr[0]][curr[1]].setVisited(true);//curr is front of queue and marks it as visited

            if (curr[0] == maze.length - 1 && curr[1] == maze[0].length - 1) {
                break;

            } else {
                //going through each possibility, if it makes it to the end, break out of loop,
                if (curr[1] + 1 <= maze[0].length - 1) {//in bounds for each possibility
                    if (!maze[curr[0]][curr[1] + 1].getVisited() && !maze[curr[0]][curr[1]].getRight()) {
                        x = new int[2];
                        x[0] = curr[0];
                        x[1] = curr[1] + 1;
                        queue.add(x);//adds into queue
                    }
                }

                if (curr[1] - 1 >= 0) {
                    if (!maze[curr[0]][curr[1] - 1].getVisited() && !maze[curr[0]][curr[1] - 1].getRight()) {
                        x = new int[2];
                        x[0] = curr[0];
                        x[1] = curr[1] - 1;
                        queue.add(x);
                    }
                }

                if (curr[0] - 1 >= 0) {
                    if (!maze[curr[0] - 1][curr[1]].getVisited() && !maze[curr[0] - 1][curr[1]].getBottom()) {
                        x = new int[2];
                        x[0] = curr[0] - 1;
                        x[1] = curr[1];
                        queue.add(x);
                    }
                }

                if (curr[0] + 1 <= maze.length - 1) {
                    if (!maze[curr[0] + 1][curr[1]].getVisited() && !maze[curr[0]][curr[1]].getBottom()) {
                        x = new int[2];
                        x[0] = curr[0] + 1;
                        x[1] = curr[1];
                        queue.add(x);
                    }
                }

            }
        }
        printMaze(true);
    }

    public static void main(String[] args){
        //statment coverage for printMaze
        MyMaze test = new MyMaze(5, 20);
        test = test.makeMaze(5, 20);
        test.printMaze(true);

        MyMaze test2 = new MyMaze(4, 10);
        test2 = test2.makeMaze(4, 10);
        test2.printMaze(false);
        //statement coverage for solveMaze
        MyMaze test3 = new MyMaze(5, 15);
        test3 = test3.makeMaze(5, 15);
        test3.solveMaze();

        //supposed to not run
        MyMaze test4 = new MyMaze(0, 0);
        test4 = test4.makeMaze(0,0);
        test4.solveMaze();

        //supposed to not run
        MyMaze test5 = new MyMaze(-5, -10);
        test5 = test5.makeMaze(-5,-10);
        test5.solveMaze();

    }
}
