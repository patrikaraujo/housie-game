/**********************************************************************
 * Copyright (C) 2020 to present, Patrik Araujo - All Rights Reserved
 * You may only use this code for the purposes authorized
 * by the Author. This code cannot be distributed without
 * consent of the Author. The author is not responsible 
 * by how this software is used.
 **********************************************************************/

package com.housie.housiegame;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class PlayerTest {

	@Test
	public void testNewPlayerCreation() {
		Player player = new Player("Player1", Collections.emptyList());
		assertTrue(player.getWins() != null);
		assertTrue(player.getWins().size() == 0);
		assertTrue(player.toString().equals("Player1"));
	}
	
	@Test
	public void testPlayerMarkingNumbers() {
		Player player = new Player("Player", new ArrayList<Rules>(Arrays.asList(Rules.EARLY_FIVE, Rules.TOP_LINE)));
		Ticket ticket = new Ticket(3, 4, 10, 2, createTestTable());
		player.assignTicket(ticket);
		
		PlayerStatus status = player.acceptNumberCalled(9);
		assertFalse(player.isWinner());
		assertFalse(status.isWinner());
		status = player.acceptNumberCalled(1);
		status = player.acceptNumberCalled(5);
		assertTrue(player.isWinner());
		assertTrue(status.isWinner());
		assertTrue(status.getWins().size() == 1);
		assertTrue(status.getWins().get(0).equals(Rules.TOP_LINE));
		status = player.acceptNumberCalled(3);
		assertTrue(status.getWins().size() == 0);
		assertTrue(player.getWins().size() == 1);
	}
	
	private List<Map<Integer, String>> createTestTable() {
		List<Map<Integer, String>> table = new ArrayList<Map<Integer, String>>(3);
		for (int i = 0; i < 3; ++i) {
			table.add(new HashMap<Integer, String>(4));
		}
		List<Integer> numbers = Arrays.asList(1, 5, 3, 8, 6, 9);
		int numbersIndex = 0;
		for (int i = 0; i < table.size(); ++i) {
			for (int j = 0; j < 2; ++j) {
				table.get(i).put(numbers.get(numbersIndex++), "");
			}
		}
		return table;
	}

}
