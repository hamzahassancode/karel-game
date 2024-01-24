/*
 * File: BlankKarel.java
 * ---------------------
 * This class is a blank one that you can change at will.
 */

import stanford.karel.*;

public class BlankKarel extends SuperKarel {

	private int row, column;
	private int midRow, midColumn;
	private int step;

	public void run() {

		step = 0;
		setBeepersInBag(1000);
		row = moveToWall();
		turnLeft();
		column = moveToWall();
		midRow = row / 2;
		midColumn = column / 2;

		if (row < 5 || column < 5) {
			throw new RuntimeException("Map " + row + "x" + column + " can't be divided into the required shape because it's too small!");
		} else {
			drawOuter();
			drawInner();
			System.out.println("in Map " + row + "x" + column + " Number of step is: " + step);
		}
	}
	private void drawOuter() {
		turnAround();
		move(midColumn);
		turnRight();
		divideOuter(column);
		moveToWall();
		turnLeft();
		move(midRow);
		turnLeft();
		divideOuter(row);
	}
	private void drawInner() {
		if (row < column) {
			if (((row % 2 == 1 && column % 2 == 1)) || ((row % 2 == 0 && column % 2 == 0))) {
				int secondPosition = midColumn - (midRow - 1);
				goToInner(midRow, secondPosition);
				drawSquare(row - 3, row - 3);
			} else {
				int secondPosition = (row % 2 == 1 && column % 2 == 0) ? midColumn - midRow : midColumn - (midRow - 2);
				goToInner(midRow, secondPosition);
				drawSquare((column - ((secondPosition * 2) + 1)), row - 3);
			}
		} else {
			if (((column % 2 == 1 && row % 2 == 1)) || ((column % 2 == 0 && row % 2 == 0))) {
				goToInner(midColumn, 1);
				drawSquare(column - 3, column - 3);
			} else if (row % 2 == 1 && column % 2 == 0) {
				goToInner(midColumn - 1, 1);
				drawSquare(column - 3, column - 4);
			} else if (row % 2 == 0 && column % 2 == 1) {
				goToInner(midColumn + 1, 1);
				drawSquare(column - 3, column - 2);
			}
		}
	}
	private void divideOuter(int shouldDivided) {
		int move = (shouldDivided == column) ? (row - 1) : (column - 1);
		if (shouldDivided % 2 == 0) {
			moveAndPutBeeper(move);
			if (facingWest() || (facingNorth() && shouldDivided == column)) {
				turnRight();
				move(1);
				turnRight();
			} else {
				turnLeft();
				move(1);
				turnLeft();
			}
			moveAndPutBeeper(move);
			turnLeft();
		} else {
			moveAndPutBeeper(move);
			turnLeft();
		}
	}
	private int moveToWall() {
		int count = 1;
		while (frontIsClear()) {
			move(1);
			count++;
		}return count;
	}
	private void drawSquare(int height, int width) {
		for (int i = 0; i < 2; i++) {
			moveAndPutBeeper(height);
			turnLeft();
			moveAndPutBeeper(width);
			turnLeft();
		}
	}
	private void goToInner(int midOfSmaller, int secondPosition) {
		move(midOfSmaller - 1);
		turnLeft();
		move(secondPosition);
	}
	private void moveAndPutBeeper(int numOfStep) {
		for (int i = 0; i < numOfStep; i++) {
			if (noBeepersPresent())
				putBeeper();
			move(1);
		}if (noBeepersPresent()) putBeeper();
	}
	public void move(int step) {
		for (int i = 0; i < step; i++) {
			move();
			this.step++;
		}
	}
	}


