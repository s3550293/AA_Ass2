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
	// boolean flag to stop while loop once it finds exit, false to loop, true to stop
	boolean flagEnd = false;
	// counter of the amount of cells traversed
	int visitedCounter = 0;

	@Override
	public void solveMaze(Maze maze){
		// declares stack which keeps track of path
		Stack<Cell> stackCellsEn = new Stack<Cell>();
		Stack<Cell> stackCellsEx = new Stack<Cell>();
		// decalres the map to keep track of visited cells
		Cell[][] map = maze.map;
		// 2D boolean array keeping track of visited cells of both the entrance and exit
		boolean[][] visitedCellsEn, visitedCellsEx;
		if (maze.type == 2){
			visitedCellsEn = new boolean[maze.sizeR][maze.sizeC + (maze.sizeR + 1) / 2];
			visitedCellsEx = new boolean[maze.sizeR][maze.sizeC + (maze.sizeR + 1) / 2];
		}else{
			visitedCellsEn = new boolean[maze.sizeR][maze.sizeC];
			visitedCellsEx = new boolean[maze.sizeR][maze.sizeC];
		}

		// initialise
		stackCellsEn.push(maze.entrance);
		stackCellsEx.push(maze.exit);
		visitedCellsEn[maze.entrance.r][maze.entrance.c] = true;
		visitedCellsEx[maze.exit.r][maze.exit.c] = true;
		// loop
		while(flagEnd == false){
			traverse(maze, visitedCellsEn, stackCellsEn);
			intercept(stackCellsEn, stackCellsEx);
			traverse(maze, visitedCellsEx, stackCellsEx);
			intercept(stackCellsEx, stackCellsEn);
		}
		drawStack(maze, stackCellsEn);
		drawStack(maze, stackCellsEx);
	} // end of solveMaze()

	//traverse an add to a stack
	private void traverse(Maze maze, boolean[][] visitedCells, Stack<Cell> stackCells){
		Cell neighbour = getRandomNeighbour(maze, stackCells.peek(), visitedCells);
		if(neighbour != null){
			Cell nextPos = neighbour;
			System.out.println("Push - " + stackCells.peek().r + " " + stackCells.peek().c);
			stackCells.push(nextPos);
			//maze.drawFtPrt(nextPos);
			visitedCells[nextPos.r][nextPos.c] = true;
		}else{
			System.out.println("Pop - " + stackCells.peek().r + " " + stackCells.peek().c);
			stackCells.pop();
		}
	}// end of traverse()

	// Check to see if a stack has intercepted another
	private void intercept(Stack<Cell> stackCellsA, Stack<Cell> stackCellsB){
		if(stackCellsB.contains(stackCellsA.peek()) == true){
			flagEnd = true;
			while(stackCellsB.peek() != stackCellsA.peek()){
				stackCellsB.pop();
			}
		}
	}// end of intercept()

	private void drawStack(Maze maze, Stack<Cell> stackCells){
			while(stackCells.empty() == false){
				maze.drawFtPrt(stackCells.peek());
				stackCells.pop();
			}
	}

	// returns a random unvisited neighbouring cell
	private Cell getRandomNeighbour(Maze maze, Cell pos, boolean[][] visitedCells){
		// declare linked list of possible cells to return
		LinkedList<Cell> listCells = new LinkedList<Cell>();
		// adds all neighbours of pos to linked list "listCells"
		for(int i = 0; i < 6; i++){
			// if neighbour exists, is not visited and then if wall is in the way
			if(pos.neigh[i] != null
			&& visitedCells[pos.r + maze.deltaR[i]][pos.c + maze.deltaC[i]] == false
			&& pos.wall[i].present == false){
				listCells.add(pos.neigh[i]);
			}
		}
		//if linkedlist is empty then return null
		if(listCells.size() == 0){
			return null;
		//else return random cell from linkedlist
		}else{
			int rdn = randomNo(listCells.size());
			return listCells.get(rdn);
		}
	}// end of getRandomNeighbour()

	// returns random integeter from 0 to max - 1
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
