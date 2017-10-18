package mazeGenerator;

import java.util.*;

import maze.Maze;
import maze.Cell;

/**
 * Uses Growing tree to traverses a 2D array to generate a path for a maze
 *
 *  @author Joseph Garner
 */
public class GrowingTreeGenerator implements MazeGenerator {

	/** GrowingTreeGenerator */

	// Growing tree maze generator. As it is very general, here we implement as "usually pick the most recent cell, but occasionally pick a random cell"
	double threshold = 0.1; // need to implement this tonight

	/**
	 * Utilizes Growing tree algorithm to generate a path with a 10% chance for the algorithm to act like a Recursive Backtracker
     * anmd 90% chance to act like a Modified Prims algorithm.
     *
     * ******************************************************************************************
     *
     * ALGORITHM generateMaze(maze)
     * Utilizes Growing tree algorithm to generate a path
     * INPUT: a new maze
     * OUTPUT : None
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
		// creates a Cell to use as current Cell
		Cell current = null;
		// creates a 2D array to store the cordinance of visted cells
		Cell map[][] = maze.map;
		ArrayList<Cell> inCells = new ArrayList<Cell>();
		ArrayList<Cell> visited = new ArrayList<Cell>();
		// Checks if maze is type hex or normal and sets the current cell accordingly
		if(maze.type == Maze.HEX){
			do{
				current = maze.map[randPos(sizeR)][randPos(sizeC + (sizeR + 1) / 2)];
			}while(current==null);
		}else{
			current = map[randPos(sizeR)][randPos(sizeC)];
		}
		// adds the current cell to the incells array
		inCells.add(current);
		do{
			// gets the next random neighbour
			Cell next = getNeigh(map,current,sizeR,sizeC,visited,maze.type);
			// if next is not null and continues to traverse the maze
			if(next!=null){
				// adda current to visted
				visited.add(current);
				// adds next to incells
				inCells.add(next);
				// set current to next to move forward
				current = next;
			}else{
				// add current to visited
				visited.add(current);
				// remove current from incells so it can not be selected again
				inCells.remove(current);
				// check if incells is empty, the loop will end if incells is empty
				if(!inCells.isEmpty()){
					// randomly chooses if the algorithm will backtrack or select a random cell
					if(movement()){
						// Recursive Backtracker
						current = inCells.get(inCells.size() - 1);
					}else{
						// Modified Prims
						current = inCells.get(randPos(inCells.size()));
					}
				}
			}
		}while(!inCells.isEmpty());

	}

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
     * Generates a random number and applies the threshold to determin if the algorithm will select the
     * newest cell or a random cell
     *
     * ******************************************************************************************
     *
     * ALGORITHM movement()
     * Generates a random number and applies the threshold
     * INPUT: -
     * OUTPUT : boolean true if the value is within the threshold
     *
	 *  1: create random with max of 100
     *  2: if( val is less then max*threshold)
     *  3:     returns true
     *  4: end if 
     *  5: return false
     *
     * ******************************************************************************************
     *
	 *
	 * @return boolean true if the value is within the threshold
	 *
     */
	private boolean movement(){
		// Creates Random using random pos function
		int val = randPos(100);
		//Checks if the value is within the threshold
		if(val <= 100*threshold){
			//algorithm will select the newest cell
			return true;
		}
		//algorithm will select a random
		return false;
	}

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
	}// end of direction()


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
	}// end of inBounds()

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
	private Cell getNeigh(Cell m[][], Cell cur, int sizeR, int sizeC, ArrayList<Cell> visited, int type){
		Cell next = null;
		int dir[] = null;
		int move = 0;
		boolean cont = true;
		// Populates the direction array
		dir = direction(Maze.NUM_DIR);
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
					if(!visited.contains(m[cur.r+Maze.deltaR[move]][cur.c+Maze.deltaC[move]])){
						// removes the shared wall between the two cells
						next.wall[Maze.oppoDir[move]].present = false;
						cur.wall[move].present = false;
						return next;
					}
				}
			}
			cont = true;
		}
		return next;
	}

}
