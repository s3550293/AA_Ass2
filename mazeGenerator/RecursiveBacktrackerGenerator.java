package mazeGenerator;

import java.util.*;
import maze.Cell;
import maze.Maze;

public class RecursiveBacktrackerGenerator implements MazeGenerator {

	/**
     * Recursive DFS method, that implements DFS visitation semantics.
     * 
     * ******************************************************************************************
     * 
     * ALGORITHM generateMaze(maze)
     * Perform a Depth first search recursively traversal of a graph.
     * INPUT: a new maze
     * OUTPUT : None
     * 
     *  1: visited[][] = null;
     *  2: current = null
     *  3: if type is hex
     *  4:    set visited[][] to hex cordinance
     *  5:    while current is null
     *  6:        set current to random cell
     *  7:    end while
     *  8: else
     *  9:    set visited[][] to norm cordinance
     * 10:   set current to random cell 
     * 11: end if
     * 12: do
     * 13:     next = get next cell
     * 14:     if( next is not null )
     * 15:         visited[current.r][current.c] = true
     * 16:         push current into stack
     * 17:         current = next
	 * 18:     else
	 * 19:         visited[current.r][current.c] = true
	 * 20:         pop stack
	 * 21:         if( stack is not empty)
     * 22:             current = top of stack
     * 23:         end if
     * 24:     end if
     * 25: while stack is not empty loop
     *
     * ******************************************************************************************
     * 
     * @param maze Input Maze.
     *
     */
	@Override
	public void generateMaze(Maze maze) {
		// Sets local variables
		int sizeR = maze.sizeR;
		int sizeC = maze.sizeC;
		Cell map[][] = maze.map;
		// creates a 2D array to store the cordinance of visted cells 
		boolean visited[][] = null;
		// creates a Cell to use as current Cell
		Cell current = null;
		// Checks if maze is type hex or normal and sets visited[][] accordingly
		if(maze.type == Maze.HEX){
			visited = new boolean[sizeR][sizeC + (sizeR + 1) / 2];
			//sets current to a random cell, this will loop untill current is not null
			do{
				current = maze.map[randPos(sizeR)][randPos(sizeC + (sizeR + 1) / 2)];
			}while(current==null);
		}else{
			visited = new boolean[sizeR][sizeC];
			current = map[randPos(sizeR)][randPos(sizeC)];
		}
		// Creates a stack to hold the history of visited cells
		Stack<Cell> s = new Stack<Cell>();
		// Main do while, this will loop until stack is empty/all cells have been visited
		do{
			// calls getNeigh() to next the next cell
			Cell next = getNeigh(map, current, sizeR, sizeC, visited, maze.type);
			// Checks is next cell is null, will pop the stack if next is null
			if(next!=null){
				// set current visited[current.r][current.c] to true so we do not visit it again
				visited[current.r][current.c] = true;
				// add the current cell to the stack
				s.push(current);
				//set the current cell to next to step forward
				current = next;
			}else{
				// set current visited[current.r][current.c] to true so we do not visit it again
				visited[current.r][current.c] = true;
				// remove the top cell
				s.pop();
				if(!s.isEmpty())
					// set current cell to the top of the stack to step back
					current = s.peek();
			}
		}while(!s.isEmpty());
	} // end of generateMaze()

	private int randPos(int cap){
		Random rand = new Random();
		return rand.nextInt(cap);
	}// end of randPos()

	private int[] direction(int max){
		Random rand = new Random();
		int arr[] = new int[max];
		for(int i = 0;i<max;i++){
			arr[i] = i;
		}
		for(int i=0;i<max;i++){
			int val = i + rand.nextInt(max - i);
			int elem = arr[val];
		        arr[val] = arr[i];
		        arr[i] = elem;
		}
		return arr;
	} // end of direction()

	private boolean inBounds(int v, int minV, int maxV, int a, int minA, int maxA, int type){
		if(type == Maze.HEX){
			return v >= minV && v < maxV && a >= (v + 1) / 2 && a < maxA + (v + 1) / 2;
		}else{
			return v >= minV && v < maxV && a >= minA && a < maxA;
		}
	} //end of inBounds()

	private Cell getNeigh(Cell m[][], Cell cur, int sizeR, int sizeC, boolean vis[][], int type){
		Cell next = null;
		int dir[] = null;
		int move = 0;
		dir = direction(6);
		boolean cont = true;
		if(cur.tunnelTo!=null){
			if(!vis[cur.tunnelTo.r][cur.tunnelTo.c]){
				return cur.tunnelTo;
			}
		}
		for(int i=0;i<Maze.NUM_DIR;i++){
			if(dir[i]!=1 && dir[i]!=4){
				move = dir[i];
			}
			else if(type==Maze.HEX){
				move = dir[i];
			}
			else{
				cont = false;
			}
			if(cont){
				if(inBounds(cur.r+Maze.deltaR[move],0,sizeR, cur.c+Maze.deltaC[move], 0, sizeC, type)){
					if(!vis[cur.r + Maze.deltaR[move]][cur.c + Maze.deltaC[move]]){
						next = m[cur.r+Maze.deltaR[move]][cur.c+Maze.deltaC[move]];
						//next.wall[Maze.oppoDir[move]].present = false;
						cur.wall[move].present = false;
						return next;
					}
				}
			}
			cont = true;
		}
		return next;
	}//end of getNeigh()
} // end of class RecursiveBacktrackerGenerator
