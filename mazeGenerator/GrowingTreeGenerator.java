package mazeGenerator;

import java.util.*;

import maze.Maze;
import maze.Cell;

public class GrowingTreeGenerator implements MazeGenerator {
	// Growing tree maze generator. As it is very general, here we implement as "usually pick the most recent cell, but occasionally pick a random cell"
	double threshold = 0.1; // need to implement this tonight
	@Override
	public void generateMaze(Maze maze) {
		int sizeR = maze.sizeR;
		int sizeC = maze.sizeC;
		Cell current = null;
		Cell map[][] = maze.map;
		ArrayList<Cell> inCells = new ArrayList<Cell>();
		ArrayList<Cell> visited = new ArrayList<Cell>();
		if(maze.type == Maze.HEX){
			do{
				current = maze.map[randPos(sizeR)][randPos(sizeC + (sizeR + 1) / 2)];
			}while(current==null);
		}else{
			current = map[randPos(sizeR)][randPos(sizeC)];
		}
		inCells.add(current);
		do{
			Cell next = getNeigh(map,current,sizeR,sizeC,visited,maze.type);
			if(next!=null){
				System.out.println("Current: "+current.r+" "+current.c);
				System.out.println("Next: "+current.r+" "+current.c);
				visited.add(current);
				inCells.add(next);
				current = next;
			}else{
				System.out.println("POP Current: "+current.r+" "+current.c);
				visited.add(current);
				inCells.remove(current);
				if(!inCells.isEmpty())
				if(movement()){
					current = inCells.get(inCells.size() - 1);
				}else{
					current = inCells.get(randPos(inCells.size()));
				}
			}
		}while(!inCells.isEmpty());

	}

	private int randPos(int cap){
		Random rand = new Random();
		return rand.nextInt(cap);
	}

	private boolean movement(){
		int val = randPos(100);
		if(val <= 100*threshold){
			System.out.println("Recursive");
			return true;
		}
		System.out.println("Prims");
		return false;
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

	private Cell getNeigh(Cell m[][], Cell cur, int sizeR, int sizeC, ArrayList<Cell> visited, int type){
		Cell next = null;
		int dir[] = null;
		int move = 0;
		boolean cont = true;
		dir = direction(6);
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
					if(!visited.contains(m[cur.r+Maze.deltaR[move]][cur.c+Maze.deltaC[move]])){
						next = m[cur.r+Maze.deltaR[move]][cur.c+Maze.deltaC[move]];
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
