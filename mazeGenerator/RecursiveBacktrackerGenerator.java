package mazeGenerator;

import java.util.*;
import maze.Cell;
import maze.Maze;

public class RecursiveBacktrackerGenerator implements MazeGenerator {

	@Override
	public void generateMaze(Maze maze) {
		// TODO Auto-generated method stub
		if(maze.type == maze.NORMAL){
				System.out.println("Maze is normal");
				normalMaze(maze);
			}
			else{
				System.out.println("Da Poop");
			}
	} // end of generateMaze()

	private void normalMaze(Maze maze){
		boolean flag = true;
		int maxR = maze.sizeR;
		int maxrC = maze.sizeC;
		Cell map[][] = maze.map;
		boolean visited[][] = new boolean[maxR][maxrC];
		Stack<Cell> s = new Stack<Cell>();
		//Cell current = map[randPos(maxR)][randPos(maxrC)];
		Cell current = maze.entrance;
		visited[current.r][current.c] = true;
		while(flag){
			System.out.println(current.r+"  "+current.c);
			int direc[] = direction(4);
			if(getNeighVisCheck(current, maze, visited, false)){
				for(int i=0;i<direc.length;i++){
					int dec = getMapMove(maze,direc[i]);
					//System.out.println("TEST IF: "+!isVisited(current.r+maze.deltaR[dec],current.c+maze.deltaC[dec],maze.sizeR,maze.sizeC,visited));
					if(isVisited(current.r+maze.deltaR[dec],current.c+maze.deltaC[dec],maze.sizeR,maze.sizeC,visited) == false){
						System.out.println("Has this been visted: "+visited[current.r][current.c]);
						visited[current.r][current.c] = true;
						Cell next = getNeigh(current, maze, visited, dec);
						System.out.println(next.r+"  "+next.c+" HAS NOT BEEN VISITED BEFORE");
						System.out.println(visited[next.r][next.c]+" BEEN VISITED");
						if(next!=null){
							System.out.println("Next is not null");
							maze.map[current.c][current.r].wall[dec].present = false;
							maze.map[next.c][next.r].wall[maze.oppoDir[dec]].present = false;
							s.push(current);
							current = next;
							break;
						}
					}
				}
			}else{
				System.out.println("Pop");
				s.pop();
				if(!s.isEmpty())
					current = s.peek();
				else{
					flag = false;
				}
			}
			
		}



	}

	private int randPos(int cap){
		Random rand = new Random();
		return rand.nextInt(cap);
	}

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
	}

	private boolean isVisited(int r, int c, int maxR, int maxC, boolean visited[][]){
		if(inBounds(r, 0, maxR) && inBounds(c, 0,maxC)){
			System.out.println("In Bounds checking visited");
			return visited[r][c];
		}
		return true;
	}

	private boolean getNeighVisCheck(Cell current, Maze maze, boolean visited[][], boolean hex){
		int r = current.r;
		int c = current.c;
		int count = 0;
		if(inBounds(r+maze.deltaR[maze.NORTH], 0, maze.sizeR) && inBounds(c+maze.deltaC[maze.NORTH], 0, maze.sizeC)){
			if(visited[r + maze.deltaR[maze.NORTH]][c + maze.deltaC[maze.NORTH]]){
				System.out.println("THE NORTH HAS COME");
				count++;
			}
		}else{
			System.out.println("Outside NORTH");
			count++;
		}
		if(inBounds(r+maze.deltaR[maze.EAST], 0, maze.sizeR) && inBounds(c+maze.deltaC[maze.EAST], 0, maze.sizeC)){
			if(visited[r + maze.deltaR[maze.EAST]][c + maze.deltaC[maze.EAST]]){
				System.out.println("THE EAST HAS COME");
				count++;
			}
		}else{
			System.out.println("Outside EAST");
			count++;
		}
		if(inBounds(r+maze.deltaR[maze.SOUTH], 0, maze.sizeR) && inBounds(c+maze.deltaC[maze.SOUTH], 0, maze.sizeC)){
			if(visited[r + maze.deltaR[maze.SOUTH]][c + maze.deltaC[maze.SOUTH]]){
				System.out.println("THE SOUTH HAS COME");
				count++;
			}
		}else{
			System.out.println("Outside SOUTH");
			count++;
		}
		if(inBounds(r+maze.deltaR[maze.WEST], 0, maze.sizeR) && inBounds(c+maze.deltaC[maze.WEST], 0, maze.sizeC)){
			if(visited[r + maze.deltaR[maze.WEST]][c + maze.deltaC[maze.WEST]]){
				System.out.println("THE WEST HAS COME");
				count++;
			}
		}else{
			System.out.println("Outside WEST");
			count++;
		}
		System.out.println("Count: "+count);
		if(count == 4)
			return false;
		return true;
	}

	private boolean inBounds(int v, int minV,int maxV){
		if(v >= minV && v < maxV){
			return true;
		}
		return false;
	}

	private int getMapMove(Maze maze, int direction){
		if(direction == 0){
			return maze.NORTH;
		}
		else if(direction == 1){
			return maze.EAST;
		}
		else if(direction == 2){
			return maze.SOUTH;
		}
		else if(direction == 3){
			return maze.WEST;
		}
		else if(direction == 4){
			return maze.NORTHEAST;
		}
		else if(direction == 5){
			return maze.SOUTHEAST;
		}
		else if(direction == 6){
			return maze.SOUTHWEST;
		}
		else if(direction == 7){
			return maze.NORTHWEST;
		}
		return maze.NORTH;
	}

	private Cell getNeigh(Cell current, Maze maze, boolean visited[][], int dire){
		Cell neigh = null;
		int r = current.r;
		int c = current.c;
		if(dire == maze.NORTH){
			if(inBounds(r+maze.deltaR[dire], 0, maze.sizeR) && inBounds(c+maze.deltaC[dire], 0, maze.sizeC)){
				if(!visited[r + maze.deltaR[dire]][c + maze.deltaC[dire]]){
					neigh = maze.map[r+maze.deltaR[dire]][c+maze.deltaC[dire]];
				}
			}
		}
		else if(dire == maze.EAST){
			if(inBounds(r+maze.deltaR[dire], 0, maze.sizeR) && inBounds(c+maze.deltaC[dire], 0, maze.sizeC)){
				if(!visited[r + maze.deltaR[dire]][c + maze.deltaC[dire]]){
					neigh = maze.map[r+maze.deltaR[dire]][c+maze.deltaC[dire]];
				}
			}
		}
		else if(dire == maze.SOUTH){
			if(inBounds(r+maze.deltaR[dire], 0, maze.sizeR) && inBounds(c+maze.deltaC[dire], 0, maze.sizeC)){
				if(!visited[r + maze.deltaR[dire]][c + maze.deltaC[dire]]){
					neigh = maze.map[r+maze.deltaR[dire]][c+maze.deltaC[dire]];
				}
			}
		}
		else if(dire == maze.WEST){
			if(inBounds(r+maze.deltaR[dire], 0, maze.sizeR) && inBounds(c+maze.deltaC[dire], 0, maze.sizeC)){
				if(!visited[r + maze.deltaR[dire]][c + maze.deltaC[dire]]){
					neigh = maze.map[r+maze.deltaR[dire]][c+maze.deltaC[dire]];
				}
			}
		}
		return neigh;
	}

} // end of class RecursiveBacktrackerGenerator
