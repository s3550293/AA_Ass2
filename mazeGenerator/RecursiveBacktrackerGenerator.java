package mazeGenerator;

import java.util.*;
import maze.Cell;
import maze.Maze;

/**
 * Recursively traverses a 2D array to generate a path for a maze
 *
 *  @author Joseph Garner
 */
public class RecursiveBacktrackerGenerator implements MazeGenerator {

     /** RecursiveBacktrackerGenerator */

     /**
     * Recursive DFS method, that either gets a neighbour and caves a path or pops a stack if noo
     * neighbours are found
     *
     * ******************************************************************************************
     *
     * ALGORITHM generateMaze(maze)
     * Recursively traverses a graph using depth first search.
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

	/**
     * Randomly selects an integer method
     *
     * ******************************************************************************************
     *
     * ALGORITHM randPos( cap )
     * Randomly Selects a number from a set and returns the value
     * INPUT: max random number
     * OUTPUT : selected random number
     *
     *  1: rand = new Random();
     *  2: return random value with cap as max value
     *
     * ******************************************************************************************
     *
     * @param cap Input sets the max number that can be randomly selected.
     *
	 * @return random integer selected from a list.
	 *
     */
	private int randPos(int cap){
		//Creates a new random object
		Random rand = new Random();
		//returns an integer from the random object while setting the max size to cap
		return rand.nextInt(cap);
	}// end of randPos()


	 /**
     * Generates an array from a select list of integers and randomises the array
     *
     * ******************************************************************************************
     *
     * ALGORITHM direction( max )
     * Generates an array from a select list of integers, randomises the array and returns the array
     * INPUT: max directions
     * OUTPUT : 2d Array of randomised values
     *
	 *  1: rand = new Random();
     *  2: create array arr[ max ]
     *  3: for ( while i is less then max )
     *  4:    arr[ i ] = i
     *  5: end for
     *  6: for ( while i is less then max )
     *  7:    int val = i + rand.nextInt( max - i )
     *  8:    int element = arr[ val ]
     *  9:    arr[ val ] = arr[ i ]
     * 10:    arr[ i ] = element
     * 11: end for
     * 12: return arr
     *
     * ******************************************************************************************
     *
     * @param max max size of the array and its values
     *
	 * @return array with values ranging from 0 to max, randmonised.
	 *
     */
	private int[] direction(int max){
		// Create Random object
		Random rand = new Random();
		// create array setting the size to max
		int arr[] = new int[max];
		// loops through the array adding i to each index
		for(int i = 0;i<max;i++){
			arr[i] = i;
		}
		// loops through array randomising its elements
		for(int i=0;i<max;i++){
			// sets val to a random index
			int val = i + rand.nextInt(max - i);
			// sets temp element to randomly selected object in the array
			int elem = arr[val];
			// sets the randomly selected object to the current i index object
		        arr[val] = arr[i];
			// sets the current i index object to the random object saved in element
		        arr[i] = elem;
		}
		return arr;
	} // end of direction()

	 /**
     * Checks the bounds of the maze
     *
     * ******************************************************************************************
     *
     * ALGORITHM direction( max )
     * Returns a boolean after checking if the varaiables are within the bounds of the maze
     * INPUT: rows V, minV, maxV columns a minA, maxA, Maze type
     * OUTPUT : boolean true if both values are within the bounds
     *
	 *  1: if( maze type is hex )
     *  2:     return boolean value of v >= minV && v < maxV && a >= (v + 1) / 2 && a < maxA + (v + 1) / 2;
     *  3: else
     *  4:    return boolean value of v >= minV && v < maxV && a >= minA && a < maxA;
     *  5: end if
     *
     * ******************************************************************************************
     *
     * @param v row variable that is being checked
     * @param minV min value v is allowed to be
	 * @param maxV max value v is allowed to be
	 * @param a column variable that is being checked
	 * @param minA min value a is allowed to be
	 * @param maxA max value b is allowed to be
	 * @param type passing maze type.
	 *
	 * @return returns a boolean that describes if the cell is outside the maze
	 * This will return true if the cell is located inside the maze
	 *
     */
	private boolean inBounds(int v, int minV, int maxV, int a, int minA, int maxA, int type){
		// Checks if maze type is hex and adjusts the variable
		if(type == Maze.HEX){
			return v >= minV && v < maxV && a >= (v + 1) / 2 && a < maxA + (v + 1) / 2;
		}else{
			return v >= minV && v < maxV && a >= minA && a < maxA;
		}
	} //end of inBounds()


	 /**
     * Searches through the adjecnt cells and returns the next available cell.
     *
     * ******************************************************************************************
     *
     * ALGORITHM getNeigh( m[][], cur, sizeR, sizeC, vis[][], type )
     * Searches through all neighbors and returns either null or an unvisted cell
     * INPUT: Cell m[][], Cell cur, int sizeR, int sizeC, boolean vis[][], int type
     * OUTPUT : a cell either null if no unvisited neighbor is found or returns an unvisited neighbor
     *
	 *  1: populate direction array
     *  2: set continue to true
     *  3: for ( while i is less then max )
     *  4:    arr[ i ] = i
     *  5: end for
     *  6: if ( cell contains tunnel )
     *  7:    if( exit has not been visited )
     *  8:        return tunnel exit
     *  9:    end if
     * 10: end if
     * 11: for ( while i is less then Maze.NUM_DIR )
     * 12:    if ( dir[i]!=1 && dir[i]!=4 )
     * 13:        set move to dir[i]
     * 14:    else if( type==Maze.HEX )
     * 15:        set move to dir[i]
     * 16:    else
     * 17:        set continue to false
     * 18: end if
     * 19: if ( continue )
     * 20:    if( next cell coordinates are inside the maze )
     * 21:       if( next cell coordinates have not been visited )
     * 22:             set next cell
     * 23:             remove shared wall
     * 24:             return next
     * 25:       end if
     * 26:    end if
     * 27: end if
     * 28: return next
     *
     * ******************************************************************************************
     *
     * @param m[][] current map
     * @param cur current Cell position
	 * @param sizeR max row size of the maze
	 * @param sizeC max coloumn size of the maze
	 * @param vis[][] 2D array of visited cell coordinates
	 * @param type passing maze type hex normal or tunnel.
	 *
	 * @return a cell either null if no unvisited neighbor is found or returns an unvisited neighbor
	 *
     */
	private Cell getNeigh(Cell m[][], Cell cur, int sizeR, int sizeC, boolean vis[][], int type){
		// Creates the next cell
		Cell next = null;
		int dir[] = null;
		int move = 0;
		// Populates the direction array
		dir = direction(Maze.NUM_DIR);
		// Boolean continue used to seperate hex and normal movements
		boolean cont = true;
		// Checking for a tunnel. if the current is a tunnel then next will be the
		if(cur.tunnelTo!=null){
			if(!vis[cur.tunnelTo.r][cur.tunnelTo.c]){
				return cur.tunnelTo;
			}
		}
		// loops through the total number of direction untill it finds a neighbour that is not null or visited
		// if there are no available neighbours then next is retuned as null
		for(int i=0;i<Maze.NUM_DIR;i++){
			// checks for invalid movement if the maze is normal
			if(dir[i]!=1 && dir[i]!=4){
				// sets move to the direction of the next cell
				move = dir[i];
			}
			else if(type==Maze.HEX){
				// sets move to the direction of the next cell
				move = dir[i];
			}
			else{
				//if move has not been set then
				cont = false;
			}
			if(cont){
				// Checks if the movement moves to an existing cell that is inside the maze
				if(inBounds(cur.r+Maze.deltaR[move],0,sizeR, cur.c+Maze.deltaC[move], 0, sizeC, type)){
					// Checks if the cell has been visted before
					if(!vis[cur.r + Maze.deltaR[move]][cur.c + Maze.deltaC[move]]){
						// removes the shared wall between the two cells
						next = m[cur.r+Maze.deltaR[move]][cur.c+Maze.deltaC[move]];
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
