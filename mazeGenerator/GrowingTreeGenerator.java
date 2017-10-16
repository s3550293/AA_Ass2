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
		//boolean visited[][] = null;
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
				visited.add(current);
				inCells.add(next);
				current = next;
			}else{
				visited.add(current);
				inCells.remove(current);
				if(!inCells.isEmpty())
					current = inCells.get(randPos(inCells.size()));
			}
		}while(!inCells.isEmpty());

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
			/*
			if(dir[i]==Maze.EAST){move = dir[i];}
			else if(dir[i]==Maze.NORTH){move = dir[i];}
			else if(dir[i]==Maze.WEST){move = dir[i];}
			else if(dir[i]==Maze.SOUTH){move = dir[i];}
			if(type == Maze.HEX){
				if(dir[i]==Maze.SOUTHWEST){move = dir[i];}
				else if(dir[i]==Maze.NORTHEAST){move = dir[i];}
			}
			*/
			if(dire[i]!=1 && dire[i]!=4){
				move = dir[i];
			}
			else if(type==Maze.HEX){
				move = dir[i];
			}
			else{
				cont = false
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
