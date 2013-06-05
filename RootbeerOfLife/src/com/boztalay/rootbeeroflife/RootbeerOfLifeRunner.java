package com.boztalay.rootbeeroflife;

import java.util.Calendar;

public class RootbeerOfLifeRunner {
	private static final int GRID_SIZE = 500;

	private RootbeerOfLife rootbeerOfLife;

	public static void main(String[] args) {
		new RootbeerOfLifeRunner().runRootbeerOfLife();
	}
	
	public void runRootbeerOfLife() {
		rootbeerOfLife = new RootbeerOfLife(GRID_SIZE, GRID_SIZE);

		long startTime = Calendar.getInstance().getTimeInMillis();

		for(int i = 0; i < 100; i++) {
			rootbeerOfLife.runGeneration();
		}

		System.out.println("Finished in " + ( Calendar.getInstance().getTimeInMillis() - startTime ) + "ms");
	}
}
