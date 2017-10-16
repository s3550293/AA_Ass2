package mazeSolver;

import java.util.*;
import maze.*;

/*
 * Implements WallFollowerSolver
 */
public class WallFollowerSolver implements MazeSolver{
	/*
	 * Declare global variables
	 * (I know it's bad practice but it's so easy to work with, pls no bully ;-;)
	 */
	// boolean flag to stop while loop once it finds exit
	boolean flagEnd = false;
	// counter of the amount of cells traversed
	int visitedCounter = 0;
	// declares the maze type, 0 = normal, 1 = tunnel, 2 = hex
	int mazeType = -1;
	// decalres the map to keep track of visited cells
	Cell[][] map;
	// 2D boolean array keeping track of  visited cells
	boolean[][] visitedCells;
	// keeps track of the current visted cell
	Cell curPos;
	// keeps track of which direction curPos is facing
	int curDir;

	/*
	 * Left hand wall follower search
	 * ******************************************************************************************
	 * ALGORITHM DFS ( G )
	 * Perform a left hand wall follower search on the maze
	 * Input: Maze of cells declared by the variable "maze"
	 * OUTPUT : Maze "maze" marked with grey circles to show visited cells.
	 * ******************************************************************************************
	 * @param maze Input Maze.
	 */
	@Override
	public void solveMaze(Maze maze){
		//assign values to the global variables
		map = maze.map;
		visitedCells = new boolean[maze.sizeR][maze.sizeC];
		curPos = maze.entrance;
		curDir = maze.EAST;
		mazeType = maze.type;
		//initial move at start of maze
		maze.drawFtPrt(curPos);
		visitedCounter++;

		//solver loop
		while(flagEnd == false){
			if(curPos == maze.exit)
				flagEnd = true;
			//if tunnel is detected
			else if(curPos.tunnelTo != null){
				//move forward
				curPos = curPos.tunnelTo;
				maze.drawFtPrt(curPos);
				visitedCounter++;
				//funky way figure out which neighbour is unvisited
				int flagTunnel = 0;
				while(flagTunnel >= 0){
					//if there is no front wall
					if(curPos.wall[curDir].present == false
					//neighbour has not been visited
					&& (visitedCells[curPos.r + maze.deltaR[curDir]][curPos.c + maze.deltaC[curDir]] == false
					//or has made greater than 4 loops
					|| flagTunnel > 4)){
						moveForward(maze);
						//break flagTunnel loop
						flagTunnel = -1;
					}
					//go clockwise and increment loop flag
					else{
						curDir = clockwise(curDir);
						++flagTunnel;
					}
				}
			}
			//if there is no left wall
			else if(curPos.wall[antiClockwise(curDir)].present == false){
				curDir = antiClockwise(curDir);
				moveForward(maze);
			}
			//if there is no front wall
			else if(curPos.wall[curDir].present == false){
				moveForward(maze);
			}
			//else rotate clockwise
			else{
				curDir = clockwise(curDir);
			}
		}// end of while
	} // end of solveMaze()

	//moves curPos forward
	private void moveForward(Maze maze){
		curPos = map[curPos.r + maze.deltaR[curDir]][curPos.c + maze.deltaC[curDir]];
		maze.drawFtPrt(curPos);
		visitedCounter++;
		//visitedCells only used for tunnels
		if(mazeType == 1)
			visitedCells[curPos.r][curPos.c] = true;
		//below is needed to avoid logic error of current method ;-;
		if(mazeType == 2)
			curDir = antiClockwise(curDir);
	}// end of moveForward

	//finds direction that is anticlockwise of the current direction
	private int antiClockwise(int dir){
		if(mazeType == 2){
			if(dir == 5)
				return 0;
			else
				return ++dir;
		}
		else{
			switch (dir) {
				case 0:
					return 2;
				case 2:
					return 3;
				case 3:
					return 5;
				case 5:
					return 0;
			}
		}
		return -1;
	} //end of antiClockwise()

	//finds direction that is clockwise of th current direction
	private int clockwise(int dir){
		if(mazeType == 2){
			if(dir == 0)
				return 5;
			else
				return --dir;
		}
		else{
			switch (dir) {
				case 0:
					return 5;
				case 5:
					return 3;
				case 3:
					return 2;
				case 2:
					return 0;
	 		}
		}
		return -1;
	} //end of clockwise()

	@Override
	public boolean isSolved(){
		return flagEnd;
	} // end if isSolved()

	@Override
	public int cellsExplored(){
		return visitedCounter;
	} // end of cellsExplored()

} // end of class WallFollowerSolver
