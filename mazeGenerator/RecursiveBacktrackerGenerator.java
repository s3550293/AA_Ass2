package mazeGenerator;

import java.util.*;
import maze.Cell;
import maze.Maze;

public class RecursiveBacktrackerGenerator implements MazeGenerator {

	@Override
	public void generateMaze(Maze maze) {
		// TODO Auto-generated method stub
		int sizeR = maze.sizeR;
		int sizeC = maze.sizeC;
		Cell map[][] = maze.map;
		boolean visited[][] = null;
		Cell current = null;
		if(maze.type == Maze.HEX){
			visited = new boolean[sizeR][sizeC + (sizeR + 1) / 2];
			do{
				current = maze.map[randPos(sizeR)][randPos(sizeC + (sizeR + 1) / 2)];
			}while(current==null);
		}else{
			visited = new boolean[sizeR][sizeC];
			current = map[randPos(sizeR)][randPos(sizeC)];
		}
		Stack<Cell> s = new Stack<Cell>();
		do{
			Cell next = getNeigh(map, current, sizeR, sizeC, visited, maze.type);
			if(next!=null){
				visited[current.r][current.c] = true;
				s.push(current);
				current = next;
			}else{
				visited[current.r][current.c] = true;
				s.pop();
				if(!s.isEmpty())
					current = s.peek();
			}
		}while(!s.isEmpty());
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

	private Cell getNeigh(Cell m[][], Cell cur, int sizeR, int sizeC, boolean vis[][], int type){
		Cell next = null;
		int dir[] = null;
		int move = 0;
		dir = direction(6);
		for(int i=0;i<Maze.NUM_DIR;i++){
			if(dir[i]==Maze.EAST){move = dir[i];}
			else if(dir[i]==Maze.NORTH){move = dir[i];}
			else if(dir[i]==Maze.WEST){move = dir[i];}
			else if(dir[i]==Maze.SOUTH){move = dir[i];}
			if(type == Maze.HEX){
				if(dir[i]==Maze.SOUTHWEST){move = dir[i];}
				else if(dir[i]==Maze.NORTHEAST){move = dir[i];}
			}
			if(inBounds(cur.r+Maze.deltaR[move],0,sizeR, cur.c+Maze.deltaC[move], 0, sizeC, type)){
				if(!vis[cur.r + Maze.deltaR[move]][cur.c + Maze.deltaC[move]]){
					next = m[cur.r+Maze.deltaR[move]][cur.c+Maze.deltaC[move]];
					next.wall[Maze.oppoDir[move]].present = false;
					cur.wall[move].present = false;
					return next;
				}
			}
		}
		return next;
	}
} // end of class RecursiveBacktrackerGenerator
