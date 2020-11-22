/**********************************************************************
 * Copyright (C) 2020 to present, Patrik Araujo - All Rights Reserved
 * You may only use this code for the purposes authorized
 * by the Author. This code cannot be distributed without
 * consent of the Author. The author is not responsible 
 * by how this software is used.
 **********************************************************************/

package com.housie.housiegame;

public class GameInputs {

	public final int numbersRange;
	public final int numberOfPlayers;
	public final String ticketSize;
	public final int numbersPerRow;
	public final int rows;
	public final int columns;

	public GameInputs(int numbersRange, int numberOfPlayers, String ticketSize, int numbersPerRow) {
		this.numbersRange = requirePositiveInteger(numbersRange, "numbersRange");
		this.numberOfPlayers = requirePositiveInteger(numberOfPlayers, "numberOfPlayers");
		this.ticketSize = ticketSize;
		validateDimension(ticketSize);
		String[] dimensions = ticketSize.toLowerCase().split("x");
		this.rows = Integer.parseInt(dimensions[0]);
		this.columns = Integer.parseInt(dimensions[1]);
		this.numbersPerRow = requirePositiveInteger(numbersPerRow, "numbersPerRow");
		validateInputs();
	}

	public static void validateDimension(String dimension) {
		if (dimension.toLowerCase().split("x").length == 0
				|| dimension.toLowerCase().split("x")[0].equals(dimension.toLowerCase())
				|| dimension.toLowerCase().split("x").length == 1
				)
			throw new IllegalArgumentException("'x' is required between two positive integer numbers.");
		try {
			Integer.parseInt(dimension.toLowerCase().split("x")[0]);
			Integer.parseInt(dimension.toLowerCase().split("x")[1]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("The dimension must have 2 integer numbers");
		}
		if (Integer.parseInt(dimension.toLowerCase().split("x")[0]) < 0
				|| Integer.parseInt(dimension.toLowerCase().split("x")[1]) < 0)
			throw new IllegalArgumentException("The dimension must have 2 positive integer numbers");
	}
	
	public static int requirePositiveInteger(Integer value, String fieldName) {
		if (value <= 0)
			throw new IllegalArgumentException(fieldName + " must be a positive integer.");
		return value;
	}

	public void validateInputs() {
		if (numbersRange > 1000)
			throw new IllegalArgumentException("Excessive range of numbers.");
		if (numberOfPlayers > 1000)
			throw new IllegalArgumentException("Excessive number of players.");
		if (numbersRange < rows * numbersPerRow)
			throw new IllegalArgumentException("The range of values is insufficient to fill the ticket.");
		if (numbersPerRow > columns)
			throw new IllegalArgumentException("The numbersPerRow value cannot be greater than the number of columns.");
	}

}
