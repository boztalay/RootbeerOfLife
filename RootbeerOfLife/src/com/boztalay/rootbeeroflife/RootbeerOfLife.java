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
				cellJobs.add(new CellJob(x, y, lastGrid, cellGrid));
			}
		}

		rootbeer.runAll(cellJobs);
	}

	private void copyCellGridToLastGrid() {
		for(int x = 0; x < gridWidth; x++) {
			for(int y = 0; y < gridHeight; y++) {
				lastGrid[x][y] = cellGrid[x][y];
			}
		}
	}

	public boolean isCellAlive(int x, int y) {
		return cellGrid[x][y];
	}
}