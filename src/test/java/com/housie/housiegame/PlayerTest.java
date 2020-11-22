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
		Ticket ticket = new Ticket(3, 4, 10, 2, TicketTest.createTestTable());
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

}
