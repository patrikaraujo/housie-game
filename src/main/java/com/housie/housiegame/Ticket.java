/**********************************************************************
 * Copyright (C) 2020 to present, Patrik Araujo - All Rights Reserved
 * You may only use this code for the purposes authorized
 * by the Author. This code cannot be distributed without
 * consent of the Author. The author is not responsible 
 * by how this software is used.
 **********************************************************************/

package com.housie.housiegame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Ticket {

	private List<Map<Integer, String>> table;
	private List<Integer> rowMarkedCount;
	private int markedCount;
	private int rows;
	private int columns;
	private int numbersPerRow;
	private int numbersRange;

	public Ticket(int rows, int columns, int numbersRange, int numbersPerRow) {
		this.numbersRange = numbersRange;
		this.numbersPerRow = numbersPerRow;
		this.rows = rows;
		this.columns = columns;
		validateInputs();
		this.table = new ArrayList<Map<Integer, String>>(rows);
		for (int i = 0; i < rows; ++i) {
			this.table.add(new HashMap<Integer, String>(columns));
		}
		this.rowMarkedCount = Stream.generate(() -> Integer.valueOf(0)).limit(rows).collect(Collectors.toList());
		this.fillTable();
	}

	public Ticket(int rows, int columns, int numbersRange, int numbersPerRow, List<Map<Integer, String>> table) {
		this.numbersRange = numbersRange;
		this.numbersPerRow = numbersPerRow;
		this.rows = rows;
		this.columns = columns;
		validateInputs();
		this.table = table;
		this.rowMarkedCount = Stream.generate(() -> Integer.valueOf(0)).limit(rows).collect(Collectors.toList());
	}

	public List<Integer> getMarkedRowCount() {
		return Collections.unmodifiableList(this.rowMarkedCount);
	}

	public int getMarkedCount() {
		return this.markedCount;
	}

	public boolean isRowFullyMarked(int row) {
		return this.rowMarkedCount.get(row) == this.numbersPerRow;
	}

	public boolean isTicketFullyMarked() {
		boolean fullyMarked = true;
		for (int i = 0; i < table.size(); ++i) {
			if (!isRowFullyMarked(i)) {
				fullyMarked = false;
				break;
			}
		}
		return fullyMarked;
	}

	private void validateInputs() {
		if (numbersRange < rows * numbersPerRow)
			throw new IllegalArgumentException("The range of values is insufficient to fill the ticket.");
		if (numbersPerRow > columns)
			throw new IllegalArgumentException("The numbersPerRow value cannot be greater than the number of columns.");
	}

	public boolean hasNumber(int number) {
		for (int i = 0; i < table.size(); ++i) {
			if (table.get(i).containsKey(number)) {
				return true;
			}
		}
		return false;
	}

	public void markNumber(int number) {
		for (int i = 0; i < table.size(); ++i) {
			if (table.get(i).remove(number) != null) {
				rowMarkedCount.set(i, rowMarkedCount.get(i) + 1);
				++markedCount;
			}
		}
	}

	private void fillTable() {
		List<Integer> numbers = generateRandomNumbers(numbersRange, rows * numbersPerRow);
		int numbersIndex = 0;
		for (int i = 0; i < table.size(); ++i) {
			for (int j = 0; j < numbersPerRow; ++j) {
				table.get(i).put(numbers.get(numbersIndex++), "");
			}
		}
	}

	private List<Integer> generateRandomNumbers(int numbersRange, int numbersCount) {
		List<Integer> values = IntStream.range(1, numbersRange + 1).boxed()
				.collect(Collectors.toCollection(ArrayList::new));
		Collections.shuffle(values);
		return values.subList(0, numbersCount);
	}

	@Override
	public String toString() {
		StringBuilder response = new StringBuilder();
		for (Map<Integer, String> row : table) {
			response.append(row.toString() + "\n");
		}
		return response.toString();
	}

}
