package mazeGenerator;

import maze.Maze;
import java.util.*;
import maze.Cell;

public class ModifiedPrimsGenerator implements MazeGenerator {

	@Override
	public void generateMaze(Maze maze) {
		// TODO Auto-generated method stub
		int sizeR = maze.sizeR;
		int sizeC = maze.sizeC;
		int size = sizeR + sizeC;
		boolean visited[][] = null;
		boolean fvisited[][] = null;
		Cell map[][] = maze.map;
		Cell current = null;
		current = map[randPos(sizeR)][randPos(sizeC)];
		visited = new boolean[sizeR][sizeC];
		fvisited = new boolean[sizeR][sizeC];
		LinkedList<Cell> inner = new LinkedList<>();
		LinkedList<Cell> frontier = new LinkedList<>();
		frontier = getfront(frontier, map, current, sizeR, sizeC, fvisited, maze.type);
		while(!frontier.isEmpty()){
			carve(current, inner, map, sizeR, sizeC, visited, maze.type);
			visited[current.r][current.c] = true;
			inner.add(current);
			frontier.remove(current);
			current = frontier.get(randPos(frontier.size()));
		}
	} // end of generateMaze()

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

	private boolean inBounds(int v, int minV, int maxV, int a, int minA, int maxA, int type){
		if(type == Maze.HEX){
			return v >= minV && v < maxV && a >= (v + 1) / 2 && a < maxA + (v + 1) / 2;
		}else{
			return v >= minV && v < maxV && a >= minA && a < maxA;
		}
	}

	private void carve(Cell current, LinkedList<Cell> inner, Cell m[][], int sizeR, int sizeC, boolean vis[][], int type){
		Cell neigh = null;
		for(int i=0;i<6;i++){
			if(i!=1||i!=4){
				for(Cell c: inner){
					if(c == m[current.r+Maze.deltaR[i]][current.c+Maze.deltaC[i]])
						neigh = c;
						System.out.println("Current: "+current.c+" "+current.r);
						current.wall[i].present = false;
						System.out.println("Neigh: "+neigh.c+" "+neigh.r);
						neigh.wall[Maze.oppoDir[i]].present = false;
				}
			}
			if(type == Maze.HEX){
				for(Cell c: inner){
					if(c == m[current.r+Maze.deltaR[i]][current.c+Maze.deltaC[i]])
						neigh = c;
						current.wall[i].present = false;
						neigh.wall[Maze.oppoDir[i]].present = false;
				}
			}
		}
	}

	private LinkedList<Cell> getfront(LinkedList<Cell> frontier, Cell m[][], Cell cur, int sizeR, int sizeC, boolean vis[][], int type){
		int move = 0;
		int dir[] = direction(6);
		for(int i=0;i<Maze.NUM_DIR;i++){
			if(dir[i]!=Maze.SOUTHWEST || dir[i]!=Maze.NORTHEAST){
				if(inBounds(cur.r+Maze.deltaR[move],0,sizeR, cur.c+Maze.deltaC[move], 0, sizeC, type)){
					frontier.add(m[cur.r+Maze.deltaR[dir[i]]][cur.c+Maze.deltaC[dir[i]]]);
					vis[cur.r+Maze.deltaR[dir[i]]][cur.c+Maze.deltaC[dir[i]]] = true;
				}
			}
			if(type == Maze.HEX){
				if(dir[i]==Maze.SOUTHWEST || dir[i]==Maze.NORTHEAST){
					if(inBounds(cur.r+Maze.deltaR[move],0,sizeR, cur.c+Maze.deltaC[move], 0, sizeC, type)){
						frontier.add(m[cur.r+Maze.deltaR[dir[i]]][cur.c+Maze.deltaC[dir[i]]]);
						vis[cur.r+Maze.deltaR[dir[i]]][cur.c+Maze.deltaC[dir[i]]] = true;
					}
				}
			}
		}
		return frontier;
	}

} // end of class ModifiedPrimsGenerator
package mazeGenerator;

import maze.Maze;
import java.util.*;
import maze.Cell;

public class ModifiedPrimsGenerator implements MazeGenerator {

	@Override
	public void generateMaze(Maze maze) {
		// TODO Auto-generated method stub
		int sizeR = maze.sizeR;
		int sizeC = maze.sizeC;
		int size = sizeR + sizeC;
		boolean visited[][] = null;
		boolean fvisited[][] = null;
		Cell map[][] = maze.map;
		Cell current = null;
		current = map[randPos(sizeR)][randPos(sizeC)];
		visited = new boolean[sizeR][sizeC];
		fvisited = new boolean[sizeR][sizeC];
		LinkedList<Cell> inner = new LinkedList<>();
		LinkedList<Cell> frontier = new LinkedList<>();
		frontier = getfront(frontier, map, current, sizeR, sizeC, fvisited, maze.type);
		while(!frontier.isEmpty()){
			carve(current, inner, map, sizeR, sizeC, visited, maze.type);
			visited[current.r][current.c] = true;
			inner.add(current);
			frontier.remove(current);
			current = frontier.get(randPos(frontier.size()));
		}
	} // end of generateMaze()

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

	private boolean inBounds(int v, int minV, int maxV, int a, int minA, int maxA, int type){
		if(type == Maze.HEX){
			return v >= minV && v < maxV && a >= (v + 1) / 2 && a < maxA + (v + 1) / 2;
		}else{
			return v >= minV && v < maxV && a >= minA && a < maxA;
		}
	}

	private void carve(Cell current, LinkedList<Cell> inner, Cell m[][], int sizeR, int sizeC, boolean vis[][], int type){
		Cell neigh = null;
		for(int i=0;i<6;i++){
			if(i!=1||i!=4){
				for(Cell c: inner){
					if(c == m[current.r+Maze.deltaR[i]][current.c+Maze.deltaC[i]])
						neigh = c;
						System.out.println("Current: "+current.c+" "+current.r);
						current.wall[i].present = false;
						System.out.println("Neigh: "+neigh.c+" "+neigh.r);
						neigh.wall[Maze.oppoDir[i]].present = false;
				}
			}
			if(type == Maze.HEX){
				for(Cell c: inner){
					if(c == m[current.r+Maze.deltaR[i]][current.c+Maze.deltaC[i]])
						neigh = c;
						current.wall[i].present = false;
						neigh.wall[Maze.oppoDir[i]].present = false;
				}
			}
		}
	}

	private LinkedList<Cell> getfront(LinkedList<Cell> frontier, Cell m[][], Cell cur, int sizeR, int sizeC, boolean vis[][], int type){
		int move = 0;
		int dir[] = direction(6);
		for(int i=0;i<Maze.NUM_DIR;i++){
			if(dir[i]!=Maze.SOUTHWEST || dir[i]!=Maze.NORTHEAST){
				if(inBounds(cur.r+Maze.deltaR[move],0,sizeR, cur.c+Maze.deltaC[move], 0, sizeC, type)){
					frontier.add(m[cur.r+Maze.deltaR[dir[i]]][cur.c+Maze.deltaC[dir[i]]]);
					vis[cur.r+Maze.deltaR[dir[i]]][cur.c+Maze.deltaC[dir[i]]] = true;
				}
			}
			if(type == Maze.HEX){
				if(dir[i]==Maze.SOUTHWEST || dir[i]==Maze.NORTHEAST){
					if(inBounds(cur.r+Maze.deltaR[move],0,sizeR, cur.c+Maze.deltaC[move], 0, sizeC, type)){
						frontier.add(m[cur.r+Maze.deltaR[dir[i]]][cur.c+Maze.deltaC[dir[i]]]);
						vis[cur.r+Maze.deltaR[dir[i]]][cur.c+Maze.deltaC[dir[i]]] = true;
					}
				}
			}
		}
		return frontier;
	}

} // end of class ModifiedPrimsGenerator
