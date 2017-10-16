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
	// boolean flag to stop while loop once it finds exit
	boolean flagEnd = false;
	// counter of the amount of cells traversed
	int visitedCounter = 0;
	// declares the maze type, 0 = normal, 1 = tunnel, 2 = hex
	int mazeType = -1;
	// decalres the map to keep track of visited cells
	Cell[][] map;
	// 2D boolean array keeping track of visited cells of both the entrance and exit
	boolean[][] visitedCellsEn, visitedCellsEx;
	// keeps track of the current visted cell
	Cell curPosEn, curPosEx;

	@Override
	public void solveMaze(Maze maze){
		Stack<Cell> stackCellsEn = new Stack<Cell>();
		//assign values to the global variables
		map = maze.map;
		curPosEn = maze.entrance;
		curPosEx = maze.exit;
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
		/*
		for(int i = 0; i < 6; i++){
			System.out.println(i + " " + curPosEn.neigh[i]);
		}
		maze.drawFtPrt(curPosEncurPosEn);
		stackCellsEn.push(curPosEn);
		for(int i = 0; i < 6; i++){
			curPosEn = map[curPosEn.r + maze.deltaR[maze.WEST]][curPosEn.c + maze.deltaC[maze.WEST]];
			maze.drawFtPrt(curPosEn);
			stackCellsEn.push(curPosEn);
			visitedCellsEn[curPosEn.r][curPosEn.c] = true;
		}
		while(stackCellsEn.empty() == false){
			System.out.println("Stack! " + stackCellsEn.peek());
			stackCellsEn.pop();
		}
		System.out.println(getRandomNeighbour(maze, curPosEn));
		*/


		//working space
		stackCellsEn.push(curPosEn);
		//maze.drawFtPrt(curPosEn);
		visitedCellsEn[curPosEn.r][curPosEn.c] = true;
		int debugCtr = 500;
		while(stackCellsEn.peek() != maze.exit){
			Cell neighbour = getRandomNeighbour(maze, curPosEn);
			if(neighbour != null){
				curPosEn = neighbour;
				System.out.println("Push - " + stackCellsEn.peek().r + " " + stackCellsEn.peek().c);
				stackCellsEn.push(curPosEn);
				//maze.drawFtPrt(curPosEn);
				visitedCellsEn[curPosEn.r][curPosEn.c] = true;
			}
			else{
				System.out.println("Pop - " + stackCellsEn.peek().r + " " + stackCellsEn.peek().c);
				stackCellsEn.pop();
				curPosEn = stackCellsEn.peek();
			}
		}

		while(stackCellsEn.empty() == false){
			maze.drawFtPrt(stackCellsEn.peek());
			System.out.println("Stack! " + stackCellsEn.peek());
			stackCellsEn.pop();
		}

	} // end of solveMaze()

	// returns a random unvisited neighbouring cell
	private Cell getRandomNeighbour(Maze maze, Cell pos){
		// declare linked list of possible cells to return
		LinkedList<Cell> listCells = new LinkedList<Cell>();
		// adds all neighbours of pos to linked list "listCells"
		for(int i = 0; i < 6; i++){
			// if neighbour exists, is not visited and then if wall is in the way
			if(pos.neigh[i] != null
			&& visitedCellsEn[pos.r + maze.deltaR[i]][pos.c + maze.deltaC[i]] == false
			&& pos.wall[i].present == false){
				listCells.add(pos.neigh[i]);
			}
		}
		if(listCells.size() == 0){
			return null;
		}else{
			int rdn = randomNo(listCells.size());
			return listCells.get(rdn);
		}
	}

	// returns random integeter from 0 to max - 1
	private int randomNo(int max){
		Random ran = new Random();
		return ran.nextInt(max);
	}

	@Override
	public boolean isSolved(){
		//return flagEnd;
		return true;
	} // end if isSolved()

	@Override
	public int cellsExplored(){
		return visitedCounter;
	} // end of cellsExplored()

} // end of class BiDirectionalRecursiveBackTrackerSolver
