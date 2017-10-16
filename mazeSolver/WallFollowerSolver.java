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
	// 2D boolean array keeping track of cells visited,
	boolean[][] visitedCells;
	// declares the cell which moves
	Cell curPos;
	// keeps track of which direction the curPos is facing
	int curDir;

	/*
	 * Left hand wall follower search
	 *
	 * ******************************************************************************************
	 * ALGORITHM DFS ( G )
	 * Perform a left hand wall follower search on the maze
	 * Input: Maze of cells declared by the variable "maze"
	 * OUTPUT : Maze "maze" marked with grey circles to show visited cells.
	 *
	 * ******************************************************************************************
	 *
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

		/*
		//debug
		try
			Thread.sleep(1000);
		catch(InterruptedException ex)
			Thread.currentThread().interrupt();
		*/

		//solver loop
		while(flagEnd == false){
			//if found exit, end loop
			if(curPos == maze.exit)
				flagEnd = true;
			//if tunnel is detected
			else if(curPos.tunnelTo != null && visitedCells[curPos.r][curPos.c] == false){
				visitedCells[curPos.r][curPos.c] = true;
				curPos = curPos.tunnelTo;
				maze.drawFtPrt(curPos);
				visitedCounter++;
				//to account for Jo's weird generation issue of a tunnel exit that has all 4 walls
				boolean isolatedSquare = true;
				for(int i = 0; i < 6; i++){
					if(curPos.neigh[i] != null && curPos.wall[i].present == false){
						isolatedSquare = false;
						curDir = i;
					}
				}
				if(isolatedSquare == true){
					curPos = curPos.tunnelTo;
					visitedCounter++;
				}
				else{
					curPos = map[curPos.r + maze.deltaR[curDir]][curPos.c + maze.deltaC[curDir]];
					maze.drawFtPrt(curPos);
					visitedCounter++;
				}
			}
			//if there is no left wall
			//turn anticlockwise and move forward
			else if(curPos.wall[antiClockwise(curDir)].present == false){
				curDir = antiClockwise(curDir);
				moveForward(maze);
			}
			//if there is no front wall
			//move forward
			else if(curPos.wall[curDir].present == false){
				moveForward(maze);
			}
			//else rotate clockwise
			else{
				curDir = clockwise(curDir);
			}
		}// end of while
	} // end of solveMaze()

	/*
	 * Left hand wall follower search
	 *
	 * ******************************************************************************************
	 //???????????
	 * ALGORITHM DFS ( G )
	 * Perform a left hand wall follower search on the maze
	 * Input: Maze of cells declared by the variable "maze"
	 * OUTPUT : Maze "maze" marked with grey circles to show visited cells.
	 * ******************************************************************************************
	 *
	 * @param maze Input Maze.
	 */
	private void moveForward(Maze maze){
		curPos = map[curPos.r + maze.deltaR[curDir]][curPos.c + maze.deltaC[curDir]];
		maze.drawFtPrt(curPos);
		visitedCounter++;
		//visitedCells only used for tunnels
		/*
		if(mazeType == 1)
			visitedCells[curPos.r][curPos.c] = true;
		*/
		//below is needed to avoid logic error of current method ;-;
		if(mazeType == 2)
			curDir = antiClockwise(curDir);
	}

	/*
	 * Anticlockwise
	 *
	 * ******************************************************************************************
	 * ALGORITHM DFS ( G )
	 * Perform a left hand wall follower search on the maze
	 * Input: integer representing the current direction
	 * OUTPUT : integer representing the anticlockwise direction of the current direction
	 * ******************************************************************************************
	 *
	 * @param integer representing current direction
	 * @returns integer representing the direction anticlockwise of the current direction
	 */
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
