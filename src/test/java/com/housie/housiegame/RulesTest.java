/**********************************************************************
 * Copyright (C) 2020 to present, Patrik Araujo - All Rights Reserved
 * You may only use this code for the purposes authorized
 * by the Author. This code cannot be distributed without
 * consent of the Author. The author is not responsible 
 * by how this software is used.
 **********************************************************************/

package com.housie.housiegame;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class RulesTest {

	@Test
	public void testTopLineRulesLogic() {
		Ticket ticket = new Ticket(3, 4, 10, 2, createTestTable());
		assertFalse(Rules.TOP_LINE.isWinner(ticket));
		ticket.markNumber(1);
		ticket.markNumber(5);
		assertTrue(Rules.TOP_LINE.isWinner(ticket));
		assertThat(Rules.TOP_LINE.toString(), is("Top Line"));
		
	}
	
	@Test
	public void testEarlyFiveRulesLogic() {
		Ticket ticket = new Ticket(3, 4, 10, 2, createTestTable());
		assertFalse(Rules.EARLY_FIVE.isWinner(ticket));
		ticket.markNumber(1);
		ticket.markNumber(5);
		ticket.markNumber(3);
		ticket.markNumber(8);
		ticket.markNumber(6);
		assertTrue(Rules.EARLY_FIVE.isWinner(ticket));
		assertThat(Rules.EARLY_FIVE.toString(), is("Early Five"));
	}
	
	@Test
	public void testFullHouseRulesLogic() {
		Ticket ticket = new Ticket(3, 4, 10, 2, createTestTable());
		assertFalse(Rules.FULL_HOUSE.isWinner(ticket));
		ticket.markNumber(1);
		ticket.markNumber(5);
		ticket.markNumber(3);
		ticket.markNumber(8);
		ticket.markNumber(6);
		ticket.markNumber(9);
		assertTrue(Rules.FULL_HOUSE.isWinner(ticket));
		assertThat(Rules.FULL_HOUSE.toString(), is("Full House"));
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
