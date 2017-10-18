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

	/*
	 * Loop traversing the maze until path is found
	 * ******************************************************************************************
	 * ALGORITHM SOLVEMAZE(maze)
	 * INPUT : The maze
	 * OUTPUT : None
	 *  1: stackCellsEn.push(entrace)
	 *  2: stackCellsEx.push(exit)
	 *  3: visitedCellsEn[entrace r][entrace c]
	 *  4: visitedCellsEx[exit r][exit c]
	 *  5: while flagEnd is false
	 *  6: 		traverse(visitedCellsEn)
	 *  7: 		intercept(visitedCellsEn)
	 *  8: 		traverse(visitedCellsEx)
	 *  9: 		intercept(visitedCellsEx)
	 * 10: end of while
	 * 11: draw(visitedCellsEx)
	 * 12: draw(visitedCellsEn)
	 * ******************************************************************************************
	 * @param maze Input maze
	 */
	@Override
	public void solveMaze(Maze maze){
		// declares stack which keeps track of paths
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
			// traverse the maze from the entrace stack and cell
			traverse(maze, visitedCellsEn, stackCellsEn);
			// check if entrance stack intercepted the exit stack
			intercept(stackCellsEn, stackCellsEx);
			// traverse the maze from the exit stack and cell
			traverse(maze, visitedCellsEx, stackCellsEx);
			// check if exit stack intercepted the entrace stack
			intercept(stackCellsEx, stackCellsEn);
		}
		// draw the entrace stack
		drawStack(maze, stackCellsEn);
		// draw the exit stack
		drawStack(maze, stackCellsEx);
	} // end of solveMaze()

	/*
	 * Traverse the maze by one cell as depth first search
	 * ******************************************************************************************
	 * ALGORITHM TRAVERSE(maze, visitedCells, stackCells)
	 * Executes one traverse through the maze
	 * INPUT : The maze, 2d array of visited cells, stack of cells in order of visited
	 * OUTPUT : None
	 * 1: neighbour = getRandomNeighbour
	 * 2: if stackCells.peek is at tunnel and tunnel visitedCells = false
	 * 3: 	stackCells.push(tunnel)
	 * 4: else if there is a neighbour
	 * 5:   stackCells.push(neighbour)
	 * 6: else
	 * 7: 	stackCells.pop
	 * 7: end if
	 * ******************************************************************************************
	 * @param maze Input maze
	 * @param visitedCells 2d array of booleans that show previously visited cells of maze
	 * @param marked Array of booleans, that indicate whether a vertex has been
	 * @param stackCells Stack of Cells that show the current path taken
	 */
	private void traverse(Maze maze, boolean[][] visitedCells, Stack<Cell> stackCells){
		visitedCounter++;
		// get random neighbour that can be tranversed to
		Cell neighbour = getRandomNeighbour(maze, stackCells.peek(), visitedCells);
		// if tunnel is reached and the tunnel exit is not yet visited
		if(stackCells.peek().tunnelTo != null
		&& visitedCells[stackCells.peek().tunnelTo.r][stackCells.peek().tunnelTo.c] == false){
			// add tunnel exit ot stack
			stackCells.push(stackCells.peek().tunnelTo);
			visitedCells[stackCells.peek().r][stackCells.peek().c] = true;
		// if there is a traversable neighbour
		}else if(neighbour != null){
			// add neighbour to stack
			Cell nextPos = neighbour;
			stackCells.push(nextPos);
			visitedCells[nextPos.r][nextPos.c] = true;
		// if no possible traversable neighbours
		}else{
			// move back a space
			stackCells.pop();
		}
	}// end of traverse()

	/*
	 * Check to see if a stack has intercepted another
	 * ******************************************************************************************
	 * ALGORITHM INTERCEPTED(stackCellsA, stackCellsB)
	 * Checks if stackCellsA has intercepted stackCellsB
	 * cut stackCellsB until it meets stackCellsA
	 * INPUT : stack of cells stackCellsA, stack of cells stackCellsB
	 * OUTPUT : None
	 * 1: if stackCellsB contains stackCellsA.peek
	 * 2: 	flagEnd = true
	 * 3: 	while stackCellsB.peek != stackCellsA.peek
	 * 4: 		stackCellsB.pop
	 * 5: 	end of while
	 * 6: end of if
	 * ******************************************************************************************
	 * @param stackCellsA Stack of Cells that show the current path taken
	 * @param stackCellsB Stack of Cells that show the current path taken
	 */
	private void intercept(Stack<Cell> stackCellsA, Stack<Cell> stackCellsB){
		//if top of stackA exists in all of stackB
		if(stackCellsB.contains(stackCellsA.peek()) == true){
			// end the main loop
			flagEnd = true;
			// pop the rest of stackB until it meets stack A
			while(stackCellsB.peek() != stackCellsA.peek()){
				stackCellsB.pop();
			}
		}
	}// end of intercept()

	/*
	 * Draws the stack on the maze
	 * ******************************************************************************************
	 * ALGORITHM INTERCEPTED(stackCellsA, stackCellsB)
	 * Pops through the whole stack while drawing each cell
	 * INPUT : The maze, stack of cells stackCells
	 * OUTPUT : None
	 * 1: while stackCell is not empty
	 * 2: 	maze.drawFtPrt(stackCell)
	 * 3: 	stackCell.pop
	 * 4: end of while
	 * ******************************************************************************************
	 * @param maze Input maze
	 * @param stackCells Stack of Cells that show the current path taken
	 */
	private void drawStack(Maze maze, Stack<Cell> stackCells){
			while(stackCells.empty() == false){
				maze.drawFtPrt(stackCells.peek());
				stackCells.pop();
			}
	}

	/*
	 * Finds a random unvisited neighbouring cell
	 * ******************************************************************************************
	 * ALGORITHM GETRANDOMNEIGHBOUR(maze, pos, visitedCells
	 * INPUT : The maze, current cell position pos, stack of cells stackCells
	 * OUTPUT : neighbouring cell
	 *  1: declare listCells as LinkedList of Cells
	 *  2: for number of directions
	 *  3: 	if pos has a neighbour,
	 *  4: 	direction has not been visited
	 *  5: 	and pos has no walls in direction
	 *  6: 		 add pos's neighbour to listCells
	 *  7: 	end of if
	 *  8: end of for
	 *  9: if listCells is empty
	 * 10:		return null
	 * 11: else
	 * 12: 		return random element from listCells
	 * 13: end of if
	 * ******************************************************************************************
 	 * @param maze Input maze
 	 * @param cell of current position that is getting neighbour
 	 * @param stackCells Stack of Cells that show the current path taken
	 * @return neighbouring cell
	 */
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

	@Override
	public boolean isSolved(){
		return flagEnd;
	} // end if isSolved()

	@Override
	public int cellsExplored(){
		return visitedCounter;
	} // end of cellsExplored()

} // end of class BiDirectionalRecursiveBackTrackerSolver
