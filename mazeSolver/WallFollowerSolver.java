package mazeSolver;

import java.util.*;
import maze.*;

/*
 * Implements WallFollowerSolver
 */

public class WallFollowerSolver implements MazeSolver{
	boolean flagEnd = false;
	int visitedCounter = 0;
	int mazeType = -1;

	@Override
	public void solveMaze(Maze maze){
		Cell map[][] = maze.map;
		boolean visitedTunnels[][];
		visitedTunnels = new boolean[maze.sizeR][maze.sizeC];
		Cell curPos = maze.entrance;
		int curDir = maze.EAST;
		mazeType = maze.type;
		maze.drawFtPrt(curPos);
		visitedCounter++;

		//debug
		/*
		System.out.println("r = " + curPos.r);
		System.out.println("c = " + curPos.c);
		System.out.println("East = " + curPos.wall[maze.EAST].present);
		System.out.println("West = " + curPos.wall[maze.WEST].present);
		System.out.println("NW = " + curPos.wall[maze.NORTHWEST].present);
		System.out.println("NE = " + curPos.wall[maze.NORTHEAST].present);
		System.out.println("SW = " + curPos.wall[maze.SOUTHWEST].present);
		System.out.println("SE = " + curPos.wall[maze.SOUTHEAST].present);
		for(int i=0;i<6;i++){
			curDir = antiClockwise(curDir);
			System.out.println("ACW[" + i + "]" + " = " + curDir);
		}
		for(int i=0;i<6;i++){
			curDir = clockwise(curDir);
			System.out.println("CW[" + i + "]" + " = " + curDir);
		}
		for(int i=0;i<3;i++){
			curPos = map[curPos.r + maze.deltaR[maze.SOUTHWEST]][curPos.c + maze.deltaC[maze.SOUTHWEST]];
			maze.drawFtPrt(curPos);
		}
		for(int i=0;i<3;i++){
			curPos = map[curPos.r + maze.deltaR[maze.WEST]][curPos.c + maze.deltaC[maze.WEST]];
			maze.drawFtPrt(curPos);
		}
		*/

		//workspace
		while(flagEnd == false){
			/*
			//Debug
			try{
    		Thread.sleep(1000);
			}
			catch(InterruptedException ex){
    		Thread.currentThread().interrupt();
			}
			*/
			if(curPos == maze.exit)
				flagEnd = true;
			else if(curPos.tunnelTo != null && visitedTunnels[curPos.r][curPos.c] == false){
				visitedTunnels[curPos.r][curPos.c] = true;
				curPos = curPos.tunnelTo;
				maze.drawFtPrt(curPos);
				visitedCounter++;
				while(curPos.tunnelTo != null){
					if(curPos.wall[antiClockwise(curDir)].present == false){
						curDir = antiClockwise(curDir);
						curPos = map[curPos.r + maze.deltaR[curDir]][curPos.c + maze.deltaC[curDir]];
						maze.drawFtPrt(curPos);
						visitedCounter++;
					}
					else if(curPos.wall[curDir].present == false){
						curPos = map[curPos.r + maze.deltaR[curDir]][curPos.c + maze.deltaC[curDir]];
						maze.drawFtPrt(curPos);
						visitedCounter++;
					}
					else{
						curDir = clockwise(curDir);
					}
				}
			}
			//if there is no left wall
			else if(curPos.wall[antiClockwise(curDir)].present == false){
				curDir = antiClockwise(curDir);
				curPos = map[curPos.r + maze.deltaR[curDir]][curPos.c + maze.deltaC[curDir]];
				maze.drawFtPrt(curPos);
				visitedCounter++;
				//below is needed to avoid logic error of current method ;-;
				if(mazeType == 2){
					curDir = antiClockwise(curDir);
				}
			}
			//if there is no front wall
			else if(curPos.wall[curDir].present == false){
				curPos = map[curPos.r + maze.deltaR[curDir]][curPos.c + maze.deltaC[curDir]];
				maze.drawFtPrt(curPos);
				visitedCounter++;
				//below is needed to avoid logic error of current method ;-;
				if(mazeType == 2){
					curDir = antiClockwise(curDir);
				}
			}
			else{
				curDir = clockwise(curDir);
			}
		}// end of while
	} // end of solveMaze()

	//finds direction that is anticlockwise of the current direction
	public int antiClockwise(int dir){
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
