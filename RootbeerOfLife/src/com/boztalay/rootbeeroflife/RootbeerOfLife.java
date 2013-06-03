package com.boztalay.rootbeeroflife;

import java.util.ArrayList;

import edu.syr.pcpratts.rootbeer.runtime.Kernel;
import edu.syr.pcpratts.rootbeer.runtime.Rootbeer;

public class RootbeerOfLife {
	private boolean[][] cellGrid;
	private boolean[][] lastGrid;
	private int gridWidth;
	private int gridHeight;
	
	private Rootbeer rootbeer;
	
	public RootbeerOfLife(int gridWidth, int gridHeight) {
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
		
		cellGrid = new boolean[gridWidth][gridHeight];
		lastGrid = new boolean[gridWidth][gridHeight];
		
		rootbeer = new Rootbeer();
		
		resetAndSeedGame();
	}
	
	private void resetAndSeedGame() {
		for(int x = 0; x < gridWidth; x++) {
			for(int y = 0; y < gridHeight; y++) {
				lastGrid[x][y] = false;
				cellGrid[x][y] = false;
			}
		}
		
		cellGrid[0][0] = true;
		cellGrid[1][0] = true;
		cellGrid[2][0] = true;
		cellGrid[2][1] = true;
		cellGrid[1][2] = true;
	}
	
	public void runGeneration() {
		copyCellGridToLastGrid();
		
		ArrayList<Kernel> cellJobs = new ArrayList<Kernel>();
		
		for(int x = 0; x < gridWidth; x++) {
			for(int y = 0; y < gridHeight; y++) {
				cellJobs.add(new CellJob(x, y));
			}
		}
		
		rootbeer.runAll(cellJobs);
	}
	
	private class CellJob implements Kernel {
		private final int x;
		private final int y;
		
		public CellJob(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public void gpuMethod() {
			int numberOfNeighbors = countNeighborsOfCell(x, y);
			if(lastGrid[x][y]) {
				if(numberOfNeighbors < 2 || numberOfNeighbors > 3) {
					cellGrid[x][y] = false;
				} else {
					cellGrid[x][y] = true;
				}
			} else {
				if(numberOfNeighbors == 3) {
					cellGrid[x][y] = true;
				} else {
					cellGrid[x][y] = false;
				}
			}
		}
	}
	
	private void copyCellGridToLastGrid() {
		for(int x = 0; x < gridWidth; x++) {
			for(int y = 0; y < gridHeight; y++) {
				lastGrid[x][y] = cellGrid[x][y];
			}
		}
	}
	
	private int countNeighborsOfCell(int x, int y) {
		int numberOfNeighbors = 0;
		
		int startX = x - 1 + gridWidth;
		int endX = startX + 3;
		int startY = y - 1 + gridHeight;
		int endY = startY + 3;
		
		for(int neighborX = startX; neighborX < endX; neighborX++) {
			int wrappedX = neighborX % gridWidth;
			
			for(int neighborY = startY; neighborY < endY; neighborY++) {
				int wrappedY = neighborY % gridHeight;
				if((wrappedX != x || wrappedY != y) && lastGrid[wrappedX][wrappedY]) {
					numberOfNeighbors++;
				}
			}
		}
		
		return numberOfNeighbors;
	}
	
	public boolean isCellAlive(int x, int y) {
		return cellGrid[x][y];
	}
}
