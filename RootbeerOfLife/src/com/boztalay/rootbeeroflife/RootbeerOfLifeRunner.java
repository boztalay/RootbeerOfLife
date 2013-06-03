package com.boztalay.rootbeeroflife;


public class RootbeerOfLifeRunner {
	private static final int GRID_SIZE = 25;
	
	private static RootbeerOfLife rootbeerOfLife;
	
	public static void main(String[] args) {
		rootbeerOfLife = new RootbeerOfLife(GRID_SIZE, GRID_SIZE);
		
		while(true) {
			System.out.println();
			for(int y = GRID_SIZE - 1; y >= 0; y--) {
				for(int x = 0; x < GRID_SIZE; x++) {
					if(rootbeerOfLife.isCellAlive(x, y)) {
						System.out.print('.');
					} else {
						System.out.print('#');
					}
				}
				System.out.println();
			}
			
			rootbeerOfLife.runGeneration();
		}
	}
}
