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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class TicketTest {

	@Test
	public void testTicketWithRandomNumbers() {
		Ticket ticket = new Ticket(3, 10, 100, 7);
		System.out.println(ticket);
		for (int i = 1; i <= 100; i++) {
			ticket.markNumber(i);
		}
		assertTrue(ticket.getMarkedCount() == 21);
		assertTrue(ticket.isTicketFullyMarked());
		for (int i = 0; i < 3; i++) {
			assertTrue(ticket.isRowFullyMarked(i));
		}
	}

	@Test
	public void ticketCreationInvalidInputsSmallRangeOfValues() {
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			new Ticket(5, 6, 10, 5);
		});
		assertThat(exception.getMessage(), is("The range of values is insufficient to fill the ticket."));
	}

	@Test
	public void ticketCreationInvalidInputsPerRowGreaterThanColumn() {
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			new Ticket(5, 6, 50, 8);
		});
		assertThat(exception.getMessage(), is("The numbersPerRow value cannot be greater than the number of columns."));
	}

	@Test
	public void testTicketHasValue() {
		Ticket ticket = new Ticket(3, 4, 10, 2, createTestTable());
		assertTrue(ticket.hasNumber(3));
		assertTrue(ticket.hasNumber(9));
		assertFalse(ticket.hasNumber(4));
	}
	
	@Test
	public void testTicketNumbersAreUnique() {
		Ticket ticket = new Ticket(3, 4, 10, 2);
		Set<Integer> ticketNumbers = new HashSet<Integer>();
		for (Integer integer : ticket.getTicketNumbers().keySet()) {
			ticketNumbers.add(integer);
		}
		assertTrue(ticketNumbers.size() == 6);
	}

	@Test
	public void testMarkTicketValueHit() {
		Ticket ticket = new Ticket(3, 4, 10, 2, createTestTable());
		ticket.markNumber(9);
		assertTrue(ticket.getMarkedRowCount().get(2) == 1);
		assertTrue(ticket.getMarkedCount() == 1);
		ticket.markNumber(8);
		assertTrue(ticket.getMarkedRowCount().get(1) == 1);
		assertTrue(ticket.getMarkedCount() == 2);
	}

	@Test
	public void testMarkTicketValueMiss() {
		Ticket ticket = new Ticket(3, 4, 10, 2, createTestTable());
		ticket.markNumber(13);
		for (int i = 0; i < 3; i++) {
			assertTrue(ticket.getMarkedRowCount().get(i) == 0);
		}
		assertTrue(ticket.getMarkedCount() == 0);
		ticket.markNumber(7);
		for (int i = 0; i < 3; i++) {
			assertTrue(ticket.getMarkedRowCount().get(i) == 0);
		}
		assertTrue(ticket.getMarkedCount() == 0);
	}

	@Test
	public void testIsTicketFullyMarked() {
		Ticket ticket = new Ticket(3, 4, 10, 2, createTestTable());

		ticket.markNumber(9);
		ticket.markNumber(1);
		ticket.markNumber(3);
		// Verify the case of row not fully marked
		assertFalse(ticket.isRowFullyMarked(0));
		assertFalse(ticket.isTicketFullyMarked());

		ticket.markNumber(5);
		ticket.markNumber(6);
		ticket.markNumber(8);
		assertTrue(ticket.isRowFullyMarked(0));
		assertTrue(ticket.isRowFullyMarked(1));
		assertTrue(ticket.isRowFullyMarked(2));
		assertTrue(ticket.isTicketFullyMarked());
	}

	public static Map<Integer, Integer> createTestTable() {
		return createTestTable(Arrays.asList(1, 5, 3, 8, 6, 9));
	}

	public static Map<Integer, Integer> createTestTable(List<Integer> ticketNumbers) {
		Map<Integer, Integer> table = new LinkedHashMap<Integer, Integer>(6);
		int numbersIndex = 0;
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 2; ++j) {
				table.put(ticketNumbers.get(numbersIndex++), i);
			}
		}
		return table;
	}

}
