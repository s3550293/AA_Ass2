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
			normalMaze(maze, 4);
		}v
		else if(maze.type == maze.HEX){
			System.out.println("Maze is hex");
			normalMaze(maze, 6);
		}
		else{
			System.out.println("Da Poop");
		}
	} // end of generateMaze()

	private void normalMaze(Maze maze, int sides){
		boolean flag = true;
		int maxR = maze.sizeR;
		int maxC = maze.sizeC;
		Cell map[][] = maze.map;

		for(int i=0;i<maxR;i++){
			System.out.print("| ");
			for(int j=(i + 1) / 2;j<maxC + (i + 1) / 2;j++){
				if(maze.map[i][j]!=null)
					System.out.print(" "+maze.map[i][j].r+","+maze.map[i][j].c+" ");
				else
					System.out.print(" 0 ");
			}
			System.out.print(" |\n");
		}

		boolean visited[][] = null;
		Cell current = null;
		if(maze.type == maze.HEX){
			visited = new boolean[maze.sizeR][maze.sizeC + (maze.sizeR + 1) / 2];
			do{
				current = maze.map[randPos(maze.sizeR)][randPos(maze.sizeC + (maze.sizeR + 1) / 2)];
			}while(current==null);
		}else{
			visited = new boolean[maxR][maxC];
			current = map[randPos(maxR)][randPos(maxC)];
		}
		
		Stack<Cell> s = new Stack<Cell>();
		int count = 0;
		while(flag){
			// System.out.println(current.r+"  "+current.c);
			int direc[] = direction(sides);
			if(getNeighVisCheck(current, maze, visited, sides)){
				for(int i=0;i<direc.length;i++){
					int dec = getMapMove(maze,direc[i]);
					System.out.println("Direction: "+dec);
					if(!isVisited(current.r+maze.deltaR[dec],current.c+maze.deltaC[dec],maze.sizeR,maze.sizeC,visited,maze.type)){
						System.out.println("Current: "+current.r+", "+current.c);
						Cell next = getNeigh(current, maze, visited, dec);
						//System.out.println("Next: "+next.r+", "+next.c);
						if(next!=null){
							next.wall[Maze.oppoDir[dec]].present = false;
							current.wall[dec].present = false;
							s.push(current);
							visited[current.r][current.c] = true;
							current = next;
							count++;
							break;
						}else{
							System.out.println("Next is null");
							visited[next.r][next.c] = true;
						}
					}
				}
			}else{
				System.out.println("!!Pop!!");
				visited[current.r][current.c] = true;
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

	private boolean isVisited(int r, int c, int maxR, int maxC, boolean visited[][], int type){
		boolean type_b = false;
		if(type == Maze.HEX){}
			//maxC = maxC + (maxR +1) / 2;
			//type_b = true;
		if(inBounds(r, 0, maxR, c, 0,maxC, type_b)){
			return visited[r][c];
		}
		return true;
	}

	private boolean getNeighVisCheck(Cell current, Maze maze, boolean visited[][], int sides){
		int r = current.r;
		int c = current.c;
		boolean type = false;
		if(maze.type == Maze.HEX){}
			//c = c + (r + 1) / 2;
			//type = true;
		int count = 0;
		if(inBounds(r+maze.deltaR[maze.NORTH], 0, maze.sizeR, c+maze.deltaC[maze.NORTH], 0, maze.sizeC, type)){
			if(visited[r + maze.deltaR[maze.NORTH]][c + maze.deltaC[maze.NORTH]]){
				System.out.println("INSIDE NORTH");
				count++;
			}else{
				System.out.println("NORTH is unvisited");
			}
		}else{
			System.out.println("OUT NORTH");
			count++;
		}
		if(inBounds(r+maze.deltaR[maze.EAST], 0, maze.sizeR, c+maze.deltaC[maze.EAST], 0, maze.sizeC, type)){
			if(visited[r + maze.deltaR[maze.EAST]][c + maze.deltaC[maze.EAST]]){
				System.out.println("INSIDE EAST");
				count++;
			}else{
				System.out.println("EAST is unvisited");
			}
		}else{
			System.out.println("OUT EAST");
			count++;
		}
		if(inBounds(r+maze.deltaR[maze.SOUTH], 0, maze.sizeR, c+maze.deltaC[maze.SOUTH], 0, maze.sizeC, type)){
			if(visited[r + maze.deltaR[maze.SOUTH]][c + maze.deltaC[maze.SOUTH]]){
				System.out.println("INSIDE SOUTH");
				count++;
			}else{
				System.out.println("SOUTH is unvisited");
			}
		}else{
			System.out.println("OUT SOUTH");
			count++;
		}
		if(inBounds(r+maze.deltaR[maze.WEST], 0, maze.sizeR, c+maze.deltaC[maze.WEST], 0, maze.sizeC, type)){
			if(visited[r + maze.deltaR[maze.WEST]][c + maze.deltaC[maze.WEST]]){
				System.out.println("INSIDE WEST");
				count++;
			}else{
				System.out.println("WEST is unvisited");
			}
		}else{
			System.out.println("OUT WEST");
			count++;
		}
		if(maze.type == maze.HEX){
			if(inBounds(r+maze.deltaR[maze.NORTHEAST], 0, maze.sizeR, c+maze.deltaC[maze.NORTHEAST], 0, maze.sizeC, type)){
				if(visited[r + maze.deltaR[maze.NORTHEAST]][c + maze.deltaC[maze.NORTHEAST]]){
					System.out.println("INSIDE NORTHEAST");
					count++;
				}else{
					System.out.println("NORTHEAST is unvisited");
				}
			}else{
				System.out.println("OUT NORTHEAST");
				count++;
			}
			if(inBounds(r+maze.deltaR[maze.SOUTHWEST], 0, maze.sizeR, c+maze.deltaC[maze.SOUTHWEST], 0, maze.sizeC, type)){
				if(visited[r + maze.deltaR[maze.SOUTHWEST]][c + maze.deltaC[maze.SOUTHWEST]]){
					System.out.println("INSIDE SOUTHWEST");
					count++;
				}else{
					System.out.println("SOUTHWEST is unvisited");
				}
			}else{
				System.out.println("OUT SOUTHWEST");
				count++;
			}
		}
		System.out.println("Count: "+count);
		if(count == sides)
			return false;
		return true;
	}

	private boolean inBounds(int v, int minV,int maxV, int a, int minA, int maxA,  boolean type){
		if(type == true){
			return v >= minV && v < maxV && a >= (v + 1) / 2 && a < maxA + (v + 1) / 2;
		}else{
			return v >= minV && v < maxV && a >= minA && a < maxA;
		}
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
			return maze.SOUTHWEST;
		}
		return maze.NORTH;
	}

	private Cell getNeigh(Cell current, Maze maze, boolean visited[][], int dire){
		Cell neigh = null;
		int r = current.r;
		int c = current.c;
		int maxC = maze.sizeC;
		boolean type = false;
		if(maze.type == Maze.HEX){}
			//System.out.println("MAZE is HEX");
			//c = c + (r + 1) / 2;
			//maxC = maxC + (maze.sizeR + 1) / 2;
			//type = true;
		if(dire == maze.NORTH){
			System.out.println("New Neigh Is NORTH");
			if(inBounds(r+maze.deltaR[dire], 0, maze.sizeR, c+maze.deltaC[dire], 0, maxC, type)){
				System.out.println("Is Inside");
				if(!visited[r + maze.deltaR[dire]][c + maze.deltaC[dire]]){
					System.out.println("Not visited");
					System.out.println(maze.map[r+maze.deltaR[dire]][c+maze.deltaC[dire]]);
					neigh = maze.map[r+maze.deltaR[dire]][c+maze.deltaC[dire]];
				}
			}
		}
		else if(dire == maze.EAST){
			System.out.println("New Neigh Is EAST");
			if(inBounds(r+maze.deltaR[dire], 0, maze.sizeR, c+maze.deltaC[dire], 0, maxC, type)){
				System.out.println("Is Inside");
				if(!visited[r + maze.deltaR[dire]][c + maze.deltaC[dire]]){
					System.out.println("Not visited");
					System.out.println(maze.map[r+maze.deltaR[dire]][c+maze.deltaC[dire]]);
					neigh = maze.map[r+maze.deltaR[dire]][c+maze.deltaC[dire]];
				}
			}
		}
		else if(dire == maze.SOUTH){
			System.out.println("New Neigh Is SOUTH");
			if(inBounds(r+maze.deltaR[dire], 0, maze.sizeR, c+maze.deltaC[dire], 0, maxC, type)){
				System.out.println("Is Inside");
				if(!visited[r + maze.deltaR[dire]][c + maze.deltaC[dire]]){
					System.out.println("Not visited");
					System.out.println(maze.map[r+maze.deltaR[dire]][c+maze.deltaC[dire]]);
					neigh = maze.map[r+maze.deltaR[dire]][c+maze.deltaC[dire]];
				}
			}
		}
		else if(dire == maze.WEST){
			System.out.println("New Neigh Is WEST");
			if(inBounds(r+maze.deltaR[dire], 0, maze.sizeR, c+maze.deltaC[dire], 0, maxC, type)){
				System.out.println("Is Inside");
				if(!visited[r + maze.deltaR[dire]][c + maze.deltaC[dire]]){
					System.out.println("Not visited");
					System.out.println(maze.map[r+maze.deltaR[dire]][c+maze.deltaC[dire]]);
					neigh = maze.map[r+maze.deltaR[dire]][c+maze.deltaC[dire]];
				}
			}
		}
		else if(dire == maze.NORTHEAST){
			System.out.println("New Neigh Is NORTHEAST");
			if(inBounds(r+maze.deltaR[dire], 0, maze.sizeR, c+maze.deltaC[dire], 0, maxC, type)){
				System.out.println("Is Inside");
				if(!visited[r + maze.deltaR[dire]][c + maze.deltaC[dire]]){
					System.out.println("Not visited");
					System.out.println(maze.map[r+maze.deltaR[dire]][c+maze.deltaC[dire]]);
					neigh = maze.map[r+maze.deltaR[dire]][c+maze.deltaC[dire]];
				}
			}
		}
		else if(dire == maze.SOUTHWEST){
			System.out.println("New Neigh Is SOUTHWEST");
			if(inBounds(r+maze.deltaR[dire], 0, maze.sizeR, c+maze.deltaC[dire], 0, maxC, type)){
				System.out.println("Is Inside");
				if(!visited[r + maze.deltaR[dire]][c + maze.deltaC[dire]]){
					System.out.println("Not visited");
					System.out.println(maze.map[r+maze.deltaR[dire]][c+maze.deltaC[dire]]);
					neigh = maze.map[r+maze.deltaR[dire]][c+maze.deltaC[dire]];
				}
			}
		}
		return neigh;
	}

} // end of class RecursiveBacktrackerGenerator
