/**********************************************************************
 * Copyright (C) 2020 to present, Patrik Araujo - All Rights Reserved
 * You may only use this code for the purposes authorized
 * by the Author. This code cannot be distributed without
 * consent of the Author. The author is not responsible 
 * by how this software is used.
 **********************************************************************/

package com.housie.housiegame;

import java.util.List;

public class PlayerStatus {

	private List<Rules> wins;

	public PlayerStatus(List<Rules> wins) {
		this.wins = wins;
	}

	public boolean isWinner() {
		return !this.wins.isEmpty();
	}

	public List<Rules> getWins() {
		return this.wins;
	}

}
