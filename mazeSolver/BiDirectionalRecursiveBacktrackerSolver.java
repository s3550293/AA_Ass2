package mazeSolver;

import java.util.*;
import maze.*;

/*
 * Implements the BiDirectional recursive backtracking maze solving algorithm.
 */
public class BiDirectionalRecursiveBacktrackerSolver implements MazeSolver {
	/*
	 * Declare global variables
	 * (I know it's bad practice but it's so easy to work with ;-;)
	 */
	boolean flagEnd = false;
	int visitedCounter = 0;
	int mazeType = -1;
	Cell[][] map;
	boolean[][] visitedCells;
	Cell curPos;
	int curDir;

	@Override
	public void solveMaze(Maze maze){
		Stack<Cell> stackCells = new Stack<Cell>();
		//assign values to the global variables
		map = maze.map;
		curPos = maze.entrance;
		curDir = maze.EAST;
		mazeType = maze.type;
		if (mazeType == 2)
			visitedCells = new boolean[maze.sizeR][maze.sizeC + (maze.sizeR + 1) / 2];
		else
			visitedCells = new boolean[maze.sizeR][maze.sizeC];

		//debug
		for(int i = 0; i < 6; i++){
			System.out.println(curPos.neigh[i]);
		}

		//working space
		stackCells.push(curPos);
		while(flagEnd == false){
			stackCells.push(curPos);
		}
	} // end of solveMaze()

	private Cell getRandomNeighbour(Cell pos){
		Cell[] possibleNeigh = new Cell[6];
		for(int i = 0; i < 6; i++){
			if(curPos.neigh[i] != null){

			}
		}
		return null;
	}


	@Override
	public boolean isSolved(){
		return flagEnd;
	} // end if isSolved()


	@Override
	public int cellsExplored(){
		return visitedCounter;
	} // end of cellsExplored()
} // end of class BiDirectionalRecursiveBackTrackerSolver
