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
		boolean visited[][] = null;
		Cell current = null;
		Cell map[][] = maze.map;
		ArrayList<Cell> inCells = new ArrayList<Cell>();
		ArrayList<Cell> frontier = new ArrayList<Cell>();
		current = map[randPos(sizeR)][randPos(sizeC)];
		visited = new boolean[sizeR][sizeC];
		inCells.add(current);
		do{
			frontier = addFront(maze, frontier, inCells, current, maze.type);
			if(!frontier.isEmpty()){
				Cell next = frontier.get(randPos(frontier.size()));
				if(next!=null){
					Cell neigh = getNeigh(maze, next, inCells, maze.type);
					if(neigh!=null){
						System.out.println("Next: "+next.r+" "+next.c);
						System.out.println("Neigh: "+neigh.r+" "+neigh.c);
						System.out.println("Direction: "+getMove(neigh,next));
						neigh.wall[getMove(neigh,next)].present = false;
						next.wall[Maze.oppoDir[getMove(neigh,next)]].present = false;
						frontier.remove(next);
						visited[next.r][next.c] = true;
						inCells.add(next);
						current = next;
					}
				}
			}
		}while(!frontier.isEmpty());

	} // end of generateMaze()

	private int randPos(int cap){
		Random rand = new Random();
		return rand.nextInt(cap);
	}

	private boolean inBounds(int v, int minV, int maxV, int a, int minA, int maxA, int type){
		if(type == Maze.HEX){
			return v >= minV && v < maxV && a >= (v + 1) / 2 && a < maxA + (v + 1) / 2;
		}else{
			return v >= minV && v < maxV && a >= minA && a < maxA;
		}
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

	private int getMove(Cell start, Cell end){
		for(int i=0;i<Maze.NUM_DIR;i++){
			if(start.r+Maze.deltaR[i] == end.r && start.c+Maze.deltaC[i] == end.c){
				System.out.println("Direction is: "+i);
				return i;
			}
		}
		return 0;
	}

	private ArrayList<Cell> addFront(Maze m, ArrayList<Cell> frontier, ArrayList<Cell> inCells, Cell curr, int type){
		for(int i=0;i<Maze.NUM_DIR;i++){
			boolean isfound = false;
			if(i!=1||i!=4){
				if(inBounds(curr.r+m.deltaR[i],0,m.sizeR,curr.c+m.deltaC[i],0,m.sizeC,type)){
					for(Cell c: inCells){
						if(c == m.map[curr.r+m.deltaR[i]][curr.c+m.deltaC[i]])
							isfound = true;
					}
					if(!isfound){
						frontier.add(m.map[curr.r+m.deltaR[i]][curr.c+m.deltaC[i]]);
					}
				}
			}
			if(type==Maze.HEX){
				if(i==1||i==4){
					if(inBounds(curr.r+m.deltaR[i],0,m.sizeR,curr.c+m.deltaC[i],0,m.sizeC,type)){
						// if(!vis[curr.r+m.deltaR[i]][curr.c+m.deltaC[i]]){
						// 	frontier.add(m.map[curr.r+m.deltaR[i]][curr.c+m.deltaC[i]]);
						// }
					}
				}
			}
		}
		return frontier;
	}

	private Cell getNeigh(Maze m, Cell next, ArrayList<Cell> inCells, int type){
		int dire[] = direction(Maze.NUM_DIR);
		for(int i=0;i<Maze.NUM_DIR;i++){
			if(dire[i]!=1 && dire[i]!=4){
				if(inBounds(next.r+m.deltaR[dire[i]],0,m.sizeR,next.c+m.deltaC[dire[i]],0,m.sizeC,type)){
					for(Cell c: inCells){
						if(c == m.map[next.r+m.deltaR[dire[i]]][next.c+m.deltaC[dire[i]]]){
							return c;
						}
					}
				}
			}
			if(type==Maze.HEX){
				if(dire[i]==1||dire[i]==4){
					if(inBounds(next.r+m.deltaR[dire[i]],0,m.sizeR,next.c+m.deltaC[dire[i]],0,m.sizeC,type)){
						for(Cell c: inCells){
							if(c == m.map[next.r+m.deltaR[dire[i]]][next.c+m.deltaC[dire[i]]]){
								return c;
							}
						}
					}
				}
			}
		}
		return null;
	}


} // end of class ModifiedPrimsGenerator
