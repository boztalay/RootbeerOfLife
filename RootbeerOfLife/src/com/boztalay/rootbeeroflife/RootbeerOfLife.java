package com.boztalay.rootbeeroflife;

import java.util.ArrayList;

import edu.syr.pcpratts.rootbeer.runtime.Kernel;
import edu.syr.pcpratts.rootbeer.runtime.Rootbeer;
import edu.syr.pcpratts.rootbeer.runtime.StatsRow;

public class RootbeerOfLife {
	private boolean[][] cellGrid;
	private boolean[][] lastGrid;
	private int gridWidth;
	private int gridHeight;

	private Rootbeer rootbeer;
	private int numberOfGenerations;

	public RootbeerOfLife(int gridWidth, int gridHeight) {
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;

		cellGrid = new boolean[gridWidth][gridHeight];
		lastGrid = new boolean[gridWidth][gridHeight];

		rootbeer = new Rootbeer();
		numberOfGenerations = 0;
		
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
		
		StatsRow stats = rootbeer.getStats().get(0);
		
		System.out.println("-------------------");
		System.out.println("Generation " + numberOfGenerations + " Stats");
		System.out.println("Ran on GPU:\t\t" + rootbeer.getRanGpu());
		System.out.println("Initialization time:\t" + stats.getInitTime());
		System.out.println("Serialization time:\t" + stats.getSerializationTime());
		System.out.println("Execution time:\t\t" + stats.getExecutionTime());
		System.out.println("Deserialization time:\t" + stats.getDeserializationTime());
		System.out.println("Total time:\t\t" + stats.getOverallTime());
		System.out.println("Number of blocks:\t" + stats.getNumBlocks());
		System.out.println("Number of threads:\t" + stats.getNumThreads());
		
		numberOfGenerations++;
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