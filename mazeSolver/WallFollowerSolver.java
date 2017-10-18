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
	// keeps track of the current visted cell
	Cell curPos;
	// keeps track of which direction curPos is facing
	int curDir;

	/*
	 * Left hand wall follower solver
	 * ******************************************************************************************
	 * ALGORITHM SOLVEMAZE(maze)
	 * Perform a left hand wall follower search on the maze
	 * Input: Maze of cells declared by the variable "maze"
	 * OUTPUT : None
	 *  1: while flagEnd = false
	 *  2: 	if curPos is at exit
	 *  3: 		flagEnd = true;
	 *  4: 	else if curPos is at tunnel
	 *  5: 		curPos = tunnel
	 *  6: 		declare int neigh
	 *  7:		if neigh >= 0
	 *  8:			curDir = neigh;
	 *  9:			moveForward()
	 * 10:		else
	 * 11:			curPos = tunnel
	 * 12:			curDir = oppoDir[curDir];
	 * 13: 		end of if
	 * 14:	else if curPos.wall(anticlockwise(curDir)
	 * 15:		curDir = antiClockwise(curDir)
	 * 16:		moveForward()
	 * 17:	else if curPos.wall(curDir)
	 * 18:		moveForward()
	 * 19:	else
	 * 20:		curDir = clockwise(curDir)
	 * 21: end of while
	 * ******************************************************************************************
	 * @param maze Input maze
	 */
	@Override
	public void solveMaze(Maze maze){
		//assign values to the global variables
		map = maze.map;
		curPos = maze.entrance;
		curDir = maze.EAST;
		mazeType = maze.type;
		//initial move at start of maze
		maze.drawFtPrt(curPos);
		visitedCounter++;

		// solver loop
		while(flagEnd == false){
			if(curPos == maze.exit)
				flagEnd = true;
			// if tunnel is detected
			else if(curPos.tunnelTo != null){
				// move to tunnel
				curPos = curPos.tunnelTo;
				maze.drawFtPrt(curPos);
				visitedCounter++;
				// move forward to random neighbour
				int neigh = getRandomNeighbour(maze, curPos);
				// if neighbouring direction exists, move random one
				if(neigh >= 0){
					curDir = neigh;
					moveForward(maze);
				//else there is no neighbour
				}else{
					curPos = curPos.tunnelTo;
					curDir = maze.oppoDir[curDir];
					moveForward(maze);
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

	/*
	 * Finds a random unvisited neighbouring cell's direction
	 * ******************************************************************************************
	 * ALGORITHM GETRANDOMNEIGHBOUR(maze, pos)
	 * INPUT : The maze, current cell position pos
	 * OUTPUT : direction of neighbouring cell
	 *  1: declare listDir as linked list of directions
	 *  2: for number of directions
	 *  3: 	if pos has a neighbour
	 *  5: 	and pos has no walls in direction
	 *  6: 		 add pos's neighbour to listDir
	 *  7: 	end of if
	 *  8: end of for
	 *  9: if listCells is empty
	 * 10:		return -1
	 * 11: else
	 * 12: 		return random integer from listDir
	 * 13: end of if
	 * ******************************************************************************************
 	 * @param maze Input maze
 	 * @param cell of current position that is getting neighbour
	 * @return int representing direction to neighbouring cell
	 */
	private int getRandomNeighbour(Maze maze, Cell pos){
		// declare linked list of possible cells to return
		LinkedList<Integer> listDir = new LinkedList<Integer>();
		// adds all neighbours of pos to linked list "listCells"
		for(int i = 0; i < 6; i++){
			// if neighbour exists and if wall is not in the way
			if(pos.neigh[i] != null
			&& pos.wall[i].present == false){
				listDir.add(i);
			}
		}
		//if linkedlist is empty then return -1
		if(listDir.size() == 0){
			return -1;
		//else return random element from linkedlist
		}else{
			int rdn = randomNo(listDir.size());
			return listDir.get(rdn);
		}
	}// end of getRandomNeighbour()

	/*
	 * Generates random number between 0 and max - 1
	 * ******************************************************************************************
	 * ALGORITHM RANDOMNO(max)
	 * INPUT : integer max
	 * OUTPUT : None
	 * 1: generate ran as random number
	 * 2: return ran as integer
	 * ******************************************************************************************
	 * @param max Integer for range of max number
	 * @return ran Random integer between 0 and max - 1
	 */
	private int randomNo(int max){
		Random ran = new Random();
		return ran.nextInt(max);
	}

	/*
	 * Moves the curPos forward the maze
	 * ******************************************************************************************
	 * ALGORITHM MOVEFORWARD(maze)
	 * INPUT : The maze
	 * OUTPUT : None
	 * 1: curPos = map[delta[curDir]]
	 * 2: maze.drawFtPrt
 	 * 3: if mazeType is hex
 	 * 4: 	curDir = antiClockwise(curDir)
 	 * 5: end if
	 * ******************************************************************************************
	 * @param maze Input maze
	 * @return integer representing anticlockwise direction
	 */
	private void moveForward(Maze maze){
		curPos = map[curPos.r + maze.deltaR[curDir]][curPos.c + maze.deltaC[curDir]];
		maze.drawFtPrt(curPos);
		visitedCounter++;
		//below is needed to avoid logic error of current method ;-;
		if(mazeType == 2)
			curDir = antiClockwise(curDir);
	}// end of moveForward

	/*
	 * Finds direction that is anticlockwise of the current direction
	 * ******************************************************************************************
	 * ALGORITHM ANTICLOCKWISE(dir)
	 * INPUT : dir Integer that represents current direction
	 * OUTPUT : Integer that represents anticlockwise of the current direction
	 * 1: if maze is hex
	 * 2: 	return anticlockwise hex values
	 * 3: else
	 * 4: 	return anticlockwise square values
	 * 5: end if
	 * ******************************************************************************************
	 * @param maze Input maze
	 * @return integer representing anticlockwise direction
	 */
	private int antiClockwise(int dir){
		// if maze is hex
		if(mazeType == 2){
			if(dir == 5)
				return 0;
			else
				return ++dir;
		}
		// else any other maze
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

	/*
	 * Finds direction that is clockwise of the current direction
	 * ******************************************************************************************
	 * ALGORITHM CLOCKWISE(dir)
	 * INPUT : dir Integer that represents current direction
	 * OUTPUT : Integer that represents clockwise of the current direction
	 * 1: if maze is hex
	 * 2: 	return clockwise hex values
	 * 3: else
	 * 4: 	return clockwise square values
	 * 5: end if
	 * ******************************************************************************************
	 * @param maze Input maze
	 * @return integer representing clockwise direction
	 */
	private int clockwise(int dir){
		// if maze is hex
		if(mazeType == 2){
			if(dir == 0)
				return 5;
			else
				return --dir;
		}
		// else any other maze
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
