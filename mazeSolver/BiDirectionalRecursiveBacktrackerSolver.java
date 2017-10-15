package mazeSolver;

import java.util.*;
import maze.*;

/*
 * Implements the BiDirectional recursive backtracking maze solving algorithm.
 */
public class BiDirectionalRecursiveBacktrackerSolver implements MazeSolver {
	/*
	 * Declare global variables
	 * (I know it's bad practice but it's so easy to work with, pls no bully ;-;)
	 */
	boolean flagEnd = false;
	int visitedCounter = 0;
	int mazeType = -1;
	Cell[][] map;
	boolean[][] visitedCellsEn, visitedCellsEx;
	Cell curPos;
	int curDir;

	@Override
	public void solveMaze(Maze maze){
		Stack<Cell> stackCellsEn = new Stack<Cell>();
		//assign values to the global variables
		map = maze.map;
		curPos = maze.entrance;
		curDir = maze.EAST;
		mazeType = maze.type;
		if (mazeType == 2){
			visitedCellsEn = new boolean[maze.sizeR][maze.sizeC + (maze.sizeR + 1) / 2];
			visitedCellsEx = new boolean[maze.sizeR][maze.sizeC + (maze.sizeR + 1) / 2];
		}
		else{
			visitedCellsEn = new boolean[maze.sizeR][maze.sizeC];
			visitedCellsEx = new boolean[maze.sizeR][maze.sizeC];
		}

		//debug
		for(int i = 0; i < 6; i++){
			System.out.println(curPos.neigh[i]);
			curPos = map[curPos.r + maze.deltaR[maze.WEST]][curPos.c + maze.deltaC[maze.WEST]];
			maze.drawFtPrt(curPos);
			stackCellsEn.push(curPos);
		}
		while(stackCellsEn.empty() == false){
			System.out.println("Stack! " + stackCellsEn.peek());
			stackCellsEn.pop();
		}
		getRandomNeighbour(curPos);

		//working space


	} // end of solveMaze()

	//returns nothing at the moment, will have to implement checking if neighbour has been visited before too.
	private Cell getRandomNeighbour(Cell pos){
		//declare linked list of possible cells to return
		LinkedList<Cell> listCells = new LinkedList<Cell>();
		//adds all neighbours of pos to linked list "listCells"
		for(int i = 0; i < 6; i++){
			if(pos.neigh[i] != null){
				listCells.add(pos.neigh[i]);
			}
		}
		System.out.println(Arrays.toString(listCells.toArray()) + ", " + listCells.size() + " elements.");
		return null;
	}

	//returns random integeter from 0 to max
	private int randomNo(int max){
		Random ran = new Random();
		return ran.nextInt(max);
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
