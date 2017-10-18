package mazeGenerator;

import maze.Maze;
import java.util.*;
import maze.Cell;

/**
 * Uses modifies prims to traverses a 2D array to generate a path for a maze
 *
 *  @author Joseph Garner
 */
public class ModifiedPrimsGenerator implements MazeGenerator {

	/** ModifiedPrimsGenerator */

	/**
     * Utilizes modified prims to generate a path
     *
     * ******************************************************************************************
     *
     * ALGORITHM generateMaze(maze)
     * Utilizes modified prims to generate a path
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
		ArrayList<Cell> frontier = new ArrayList<Cell>();
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
			// Adds any new neighbours to the frontier
			frontier = addFront(maze, frontier, inCells, current, maze.type);
			// continues if there are still neighbours
			if(!frontier.isEmpty()){
				// get first cell
				Cell next = frontier.get(randPos(frontier.size()));
				if(next!=null){
					// gets the second cell
					Cell neigh = getNeigh(maze, next, inCells, maze.type);
					if(neigh!=null){
						// removes the walls between the two cells
						neigh.wall[getMove(neigh,next)].present = false;
						next.wall[Maze.oppoDir[getMove(neigh,next)]].present = false;
						// removes the first cell from the frontier
						frontier.remove(next);
						// adds the first cell to the inCells array
						inCells.add(next);
						//set current to first cell
						current = next;
					}
				}
			}
		}while(!frontier.isEmpty());
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
     * Gets the direction moved between the current and next cell
     *
     * ******************************************************************************************
     *
     * ALGORITHM getMove( start , end )
     * Gets the direction moved between the current and next cell
     * INPUT: current cell and next cell
     * OUTPUT : 2d Array of randomised values
     *
	 *  1: for ( while i is less then max )
     *  2:     if ( start cordiance + movement = end cordiance  )
     *  3:         return i
     *  4:    end if
     *  5: end for
     *
     * ******************************************************************************************
     *
     * @param start starting cell
     * @param end cell we are moving to
     *
	 * @return i movement direction
	 *
     */
	private int getMove(Cell start, Cell end){
		for(int i=0;i<Maze.NUM_DIR;i++){
			// Checks if both cells are neighbors
			if(start.r+Maze.deltaR[i] == end.r && start.c+Maze.deltaC[i] == end.c){
				return i;
			}
		}
		return 0;
	}// end of getMove()

	 /**
     * Searches through the adjecnt cells and adds them to the frontier if they arent already within
     * the array
     *
     * ******************************************************************************************
     *
     * ALGORITHM addFront( m, frontier, inCells, curr, type )
     * Searches through all neighbors and adds them to the frontier if they arent already within the array
     * INPUT: Maze m, ArrayList<Cell> frontier, ArrayList<Cell> inCells, Cell curr, int type
     * OUTPUT : the new frontier containing the added cells
     *
     *  1: set continue to true
     *  2: for ( while i is less then Maze.NUM_DIR )
     *  3:    if ( dir[i]!=1 && dir[i]!=4 )
     *  4:        set move to dir[i]
     *  5:    else if( type==Maze.HEX )
     *  6:        set move to dir[i]
     *  7:    else
     *  8:        set continue to false
     *  9: end if
     * 10: if ( continue )
     * 11:    if( neigh cell coordinates are inside the maze )
     * 12:       if( neigh cell is not contained within incells and frontier )
     * 13:             return neigh
     * 14:       end if
     * 15:    end if
     * 16: end if
     * 17: return frontier
     *
     * ******************************************************************************************
     *
     * @param m current Maze
     * @param frontier arraylist of unvisted cells that are neighbors of visited cells
	 * @param inCells arraylist of cells in the maze
	 * @param curr current cell
	 * @param type passing maze type hex normal or tunnel.
	 *
	 * @return a cell either null if no unvisited neighbor is found or returns an unvisited neighbor
	 *
     */
	private ArrayList<Cell> addFront(Maze m, ArrayList<Cell> frontier, ArrayList<Cell> inCells, Cell curr, int type){
		boolean cont;
		// loops through the total number of direction untill it finds a neighbour that is not null or visited
		// if there are no available neighbours then next is retuned as null 
		for(int i=0;i<Maze.NUM_DIR;i++){
			if(i!=1 && i!=4){
				cont = true;
			}
			else if(type==Maze.HEX){
				cont = true;
			}
			else{
				cont = false;
			}
			if(cont){
				// Checks if the movement moves to an existing cell that is inside the maze
				if(inBounds(curr.r+m.deltaR[i],0,m.sizeR,curr.c+m.deltaC[i],0,m.sizeC,type)){
					// Checks if neighbour not within inCells and frontier
					if(!inCells.contains(m.map[curr.r+m.deltaR[i]][curr.c+m.deltaC[i]]) && !frontier.contains(m.map[curr.r+m.deltaR[i]][curr.c+m.deltaC[i]]))
						// adds the cell to the frontier array
						frontier.add(m.map[curr.r+m.deltaR[i]][curr.c+m.deltaC[i]]);
				}
			}
		}
		return frontier;
	}// end of addFront()


	 /**
     * Searches through the adjecnt cells and returns the next available cell.
     *
     * ******************************************************************************************
     *
     * ALGORITHM getNeigh( m, next, inCells, type )
     * Searches through all neighbors and returns either null or an unvisted cell
     * INPUT: Maze m, Cell next, ArrayList<Cell> inCells, int type
     * OUTPUT : a cell either null if no unvisited neighbor is found or returns an unvisited neighbor
     *
	 *  1: populate direction array
     *  2: set continue to true
     *  3: for ( while i is less then max )
     *  4:    arr[ i ] = i
     *  5: end for
     *  6: for ( while i is less then Maze.NUM_DIR )
     *  7:    if ( dir[i]!=1 && dir[i]!=4 )
     *  8:        set move to dir[i]
     *  9:    else if( type==Maze.HEX )
     * 10:        set move to dir[i]
     * 11:    else
     * 12:        set continue to false
     * 13: end if
     * 14: if ( continue )
     * 15:    if( neigh cell coordinates are inside the maze )
     * 16:       if( neigh cell is contained within incells )
     * 17:             return neigh
     * 18:       end if
     * 19:    end if
     * 20: end if
     * 21: return neigh
     *
     * ******************************************************************************************
     *
     * @param m current Maze
     * @param next current cell
	 * @param inCells arraylist of cells in the maze
	 * @param type passing maze type hex normal or tunnel.
	 *
	 * @return a cell either null if no unvisited neighbor is found or returns an unvisited neighbor
	 *
     */
	private Cell getNeigh(Maze m, Cell next, ArrayList<Cell> inCells, int type){
		int move = 0;
		// Populates the direction array
		int dir[] = direction(Maze.NUM_DIR);
		boolean cont = true;
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
				if(inBounds(next.r+m.deltaR[move],0,m.sizeR,next.c+m.deltaC[move],0,m.sizeC,type)){
					// Checks if neighbour is within the inCells array
					if(inCells.contains(m.map[next.r+m.deltaR[move]][next.c+m.deltaC[move]])){
						// returns neighbour if it is contained within inCells
						return m.map[next.r+m.deltaR[move]][next.c+m.deltaC[move]];
					}
				}
			}
			cont = true;
		}
		return null;
	}// end of getNeigh()

} // end of class ModifiedPrimsGenerator
