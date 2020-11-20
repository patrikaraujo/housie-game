/**********************************************************************
 * Copyright (C) 2020 to present, Patrik Araujo - All Rights Reserved
 * You may only use this code for the purposes authorized
 * by the Author. This code cannot be distributed without
 * consent of the Author. The author is not responsible 
 * by how this software is used.
 **********************************************************************/

package com.housie.housiegame;

import java.util.List;
import java.util.Map;

public class GameStatus {

	private Map<Rules, Player> winners;
	private Map<Rules, Player> turnWinners;
	private List<Integer> numbersCalled;
	private int turnNumberCalled;
	private boolean isGameOver;

	public GameStatus(Map<Rules, Player> winners, Map<Rules, Player> turnWinners, List<Integer> numbersCalled,
			int turnNumberCalled, boolean isGameOver) {
		this.winners = winners;
		this.turnWinners = turnWinners;
		this.numbersCalled = numbersCalled;
		this.turnNumberCalled = turnNumberCalled;
		this.isGameOver = isGameOver;
	}

	public boolean hasTurnWinner() {
		return !this.turnWinners.isEmpty();
	}

	public boolean hasWinner() {
		return !this.winners.isEmpty();
	}

	public Map<Rules, Player> getTurnWinners() {
		return this.turnWinners;
	}

	public Map<Rules, Player> getWinners() {
		return this.winners;
	}

	public List<Integer> getNumbersCalled() {
		return this.numbersCalled;
	}

	public int getTurnNumberCalled() {
		return this.turnNumberCalled;
	}

	public boolean isGameOver() {
		return isGameOver;
	}
}
