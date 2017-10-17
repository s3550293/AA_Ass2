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
	// decalres the map to keep track of visited cells
	Cell[][] map;
	// 2D boolean array keeping track of visited cells of both the entrance and exit
	boolean[][] visitedCellsEn, visitedCellsEx;

	@Override
	public void solveMaze(Maze maze){
		Stack<Cell> stackCellsEn = new Stack<Cell>();
		Stack<Cell> stackCellsEx = new Stack<Cell>();
		//assign values to the global variables
		map = maze.map;
		if (maze.type == 2){
			visitedCellsEn = new boolean[maze.sizeR][maze.sizeC + (maze.sizeR + 1) / 2];
			visitedCellsEx = new boolean[maze.sizeR][maze.sizeC + (maze.sizeR + 1) / 2];
		}else{
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
		stackCellsEn.push(maze.entrance);
		stackCellsEx.push(maze.exit);
		visitedCellsEn[maze.entrance.r][maze.entrance.c] = true;
		visitedCellsEx[maze.exit.r][maze.exit.c] = true;
		int debugCtr = 40;
		while(flagEnd == false){
			traverse(maze, visitedCellsEn, stackCellsEn);
			intercept(stackCellsEn, stackCellsEx);
			traverse(maze, visitedCellsEx, stackCellsEx);
			intercept(stackCellsEx, stackCellsEn);
		}

		while(stackCellsEn.empty() == false){
			maze.drawFtPrt(stackCellsEn.peek());
			System.out.println("StackEn! " + stackCellsEn.peek());
			stackCellsEn.pop();
		}
		while(stackCellsEx.empty() == false){
			maze.drawFtPrt(stackCellsEx.peek());
			System.out.println("StackEx! " + stackCellsEx.peek());
			stackCellsEx.pop();
		}
	} // end of solveMaze()

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

	private void intercept(Stack<Cell> stackCellsA, Stack<Cell> stackCellsB){
		if(stackCellsB.contains(stackCellsA.peek()) == true){
			flagEnd = true;
			while(stackCellsB.peek() != stackCellsA.peek()){
				stackCellsB.pop();
			}
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
