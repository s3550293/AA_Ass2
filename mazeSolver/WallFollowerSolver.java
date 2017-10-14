package mazeSolver;

import java.util.*;
import maze.*;

/*
 * Implements WallFollowerSolver
 */

public class WallFollowerSolver implements MazeSolver{

	@Override
	public void solveMaze(Maze maze){
		boolean flagEnd = false;
		Cell map[][] = maze.map;
		Cell curPos = maze.entrance;
		int curDir = maze.EAST;
		maze.drawFtPrt(curPos);

		/*
		//debug
		System.out.println("South = " + curPos.wall[maze.SOUTH].present);
		System.out.println("r = " + curPos.r);
		System.out.println("c = " + curPos.c);
		System.out.println("East = " + curPos.wall[maze.EAST].present);
		System.out.println("West = " + curPos.wall[maze.WEST].present);
		curDir = antiClockwise(curDir);
		curPos = map[curPos.r + maze.deltaR[curDir]][curPos.c + maze.deltaC[curDir]];
		System.out.println("r = " + curPos.r);
		System.out.println("c = " + curPos.c);
		maze.drawFtPrt(curPos);
		*/

		//workspace
		while(flagEnd == false){
			if(curPos == maze.exit)
				flagEnd = true;
			else if(curPos.wall[antiClockwise(curDir)].present == false){
				curDir = antiClockwise(curDir);
				System.out.println(curDir);
				curPos = map[curPos.r + maze.deltaR[curDir]][curPos.c + maze.deltaC[curDir]];
				maze.drawFtPrt(curPos);
			}
			else if(curPos.wall[curDir].present == false){
				curPos = map[curPos.r + maze.deltaR[curDir]][curPos.c + maze.deltaC[curDir]];
				maze.drawFtPrt(curPos);
			}
			else{
				curDir = clockwise(curDir);
			}
		}
	} // end of solveMaze()

	//finds direction that is anticlockwise of the current direction
	//only works for normal
	private int antiClockwise(int dir){
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
		return -1;
	} //end of antiClockwise()

	private int clockwise(int dir){
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
		return -1;
	} //end of clockwise()

	@Override
	public boolean isSolved(){
		// TODO Auto-generated method stub
		// true if solved.
		return false;
	} // end if isSolved()

	@Override
	public int cellsExplored(){
		// TODO Auto-generated method stub
		// counts no. of cells explored
		return 0;
	} // end of cellsExplored()

} // end of class WallFollowerSolver
