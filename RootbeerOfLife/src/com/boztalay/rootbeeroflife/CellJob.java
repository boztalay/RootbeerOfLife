package com.boztalay.rootbeeroflife;

import edu.syr.pcpratts.rootbeer.runtime.Kernel;

public class CellJob implements Kernel {
	private final int x;
	private final int y;

	private final boolean[][] lastGrid;
	private final boolean[][] cellGrid;
	
	public CellJob(int x, int y, boolean[][] lastGrid, boolean[][] cellGrid) {
		this.x = x;
		this.y = y;
		this.lastGrid = lastGrid;
		this.cellGrid = cellGrid;
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

	private int countNeighborsOfCell(int x, int y) {
		int numberOfNeighbors = 0;

		int startX = x - 1 + lastGrid.length;
		int endX = startX + 3;
		int startY = y - 1 + lastGrid[0].length;
		int endY = startY + 3;

		for(int neighborX = startX; neighborX < endX; neighborX++) {
			int wrappedX = neighborX % lastGrid.length;

			for(int neighborY = startY; neighborY < endY; neighborY++) {
				int wrappedY = neighborY % lastGrid[0].length;
				if(( wrappedX != x || wrappedY != y ) && lastGrid[wrappedX][wrappedY]) {
					numberOfNeighbors++;
				}
			}
		}

		return numberOfNeighbors;
	}
}