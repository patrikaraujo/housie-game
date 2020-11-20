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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class HousieGameTest {

	@Test
	public void testCreateNewGame() {
		HousieGame game = new HousieGame(getValidGameInputs());
		assertTrue(game.getPlayers().size() == 5);
		for (int i = 0; i < game.getPlayers().size(); i++) {
			assertThat(game.getPlayers().get(i).toString(), is("Player#" + i));
		}
		for (Rules rule : Rules.values()) {
			assertTrue(game.getRules().contains(rule));
		}
		assertFalse(game.hasWinner());
		assertTrue(game.getWinners().isEmpty());
		assertFalse(game.isGameOver());

		GameStatus status = game.getGameStatus();
		assertFalse(status.hasTurnWinner());
		assertFalse(status.hasWinner());
		assertTrue(status.getNumbersCalled().isEmpty());
		assertTrue(status.getTurnNumberCalled() == -1);
		assertTrue(status.getTurnWinners().isEmpty());
		assertTrue(status.getWinners().isEmpty());
	}

	@Test
	public void testRunSmallGame() {
		HousieGame game = new HousieGame(getValidSmallGameInputs());

		GameStatus gameStatus = game.runNextNumber();
		while (!gameStatus.isGameOver()) {
			gameStatus = game.runNextNumber();
		}
		assertTrue(game.hasWinner());
		assertTrue(game.isGameOver());
		assertTrue(gameStatus.hasWinner());
		assertTrue(gameStatus.getWinners() != null);
	}

	@Test
	public void testRunSmallGameRehearsed() {
		List<Rules> rules = Arrays.asList(Rules.EARLY_FIVE, Rules.TOP_LINE, Rules.FULL_HOUSE);
		Player player1 = new Player("Player1", new ArrayList<Rules>(rules));
		Player player2 = new Player("Player2", new ArrayList<Rules>(rules));
		Ticket ticket1 = new Ticket(3, 4, 20, 2, createTestTable(Arrays.asList(2, 8, 6, 4, 10, 12)));
		Ticket ticket2 = new Ticket(3, 4, 20, 2, createTestTable(Arrays.asList(1, 5, 3, 7, 11, 9)));
		player1.assignTicket(ticket1);
		player2.assignTicket(ticket2);

		HousieGame game = new HousieGame(getValidSmallGameInputs(), rules, Arrays.asList(player1, player2),
				Arrays.asList(13, 2, 8, 1, 5, 3, 7, 11, 9));

		GameStatus gameStatus = game.runNextNumber();
		int ruleWinner = 1;
		while (!gameStatus.isGameOver()) {
			gameStatus = game.runNextNumber();
			if (gameStatus.hasTurnWinner() && ruleWinner == 1) {
				assertTrue(gameStatus.getTurnWinners().get(Rules.TOP_LINE).toString().equals("Player1"));
				++ruleWinner;
			} else if (gameStatus.hasTurnWinner() && ruleWinner == 2) {
				assertTrue(gameStatus.getTurnWinners().get(Rules.EARLY_FIVE).toString().equals("Player2"));
				++ruleWinner;
			} else if (gameStatus.hasTurnWinner() && ruleWinner == 3) {
				assertTrue(gameStatus.getTurnWinners().get(Rules.FULL_HOUSE).toString().equals("Player2"));
			}
		}
		assertTrue(gameStatus.getWinners().size() == 3);
	}

	@Test
	public void testRunNextNumberAfterGameOver() {
		HousieGame game = new HousieGame(getValidSmallGameInputs());

		GameStatus gameStatus = game.runNextNumber();
		while (!gameStatus.isGameOver()) {
			gameStatus = game.runNextNumber();
		}
		GameStatus gameStatusAfterGameOver = game.runNextNumber();
		assertTrue(gameStatus.getNumbersCalled().size() == gameStatusAfterGameOver.getNumbersCalled().size());
	}

	@Test
	public void testOnlyOneWinnerPerRule() {
		List<Rules> rules = Arrays.asList(Rules.EARLY_FIVE, Rules.TOP_LINE, Rules.FULL_HOUSE);
		Player player1 = new Player("Player1", new ArrayList<Rules>(rules));
		Player player2 = new Player("Player2", new ArrayList<Rules>(rules));
		Ticket ticket = new Ticket(3, 4, 10, 2, createTestTable());
		player1.assignTicket(ticket);
		player2.assignTicket(ticket);

		HousieGame game = new HousieGame(getValidSmallGameInputs(), rules, Arrays.asList(player1, player2),
				Arrays.asList(1, 5, 3, 8, 6, 9));

		GameStatus gameStatus = game.runNextNumber();
		while (!gameStatus.isGameOver()) {
			gameStatus = game.runNextNumber();
		}
		assertTrue(gameStatus.hasWinner());
		assertTrue(gameStatus.getWinners().size() == 3);
		for (Player player : gameStatus.getWinners().values()) {
			assertTrue(player.toString().equals("Player1"));
		}

	}

	@Test
	public void testGameOverAllNumbersCalled() {
		List<Rules> rules = Arrays.asList(Rules.EARLY_FIVE, Rules.TOP_LINE, Rules.FULL_HOUSE);
		Player player1 = new Player("Player1", new ArrayList<Rules>(rules));
		Player player2 = new Player("Player2", new ArrayList<Rules>(rules));
		Ticket ticket = new Ticket(3, 4, 10, 2, createTestTable());
		player1.assignTicket(ticket);
		player2.assignTicket(ticket);

		HousieGame game = new HousieGame(getValidSmallGameInputs(), rules, Arrays.asList(player1, player2),
				Arrays.asList(1, 2, 2, 2, 2, 2, 2, 2, 2, 2));

		GameStatus gameStatus = game.runNextNumber();
		while (!gameStatus.isGameOver()) {
			gameStatus = game.runNextNumber();
		}
	}

	@Test
	public void testRunLargeSizeGame() {
		HousieGame game = new HousieGame(getValidLargeGameInputs());

		GameStatus gameStatus = game.runNextNumber();
		while (!gameStatus.isGameOver()) {
			gameStatus = game.runNextNumber();
			assertTrue(gameStatus.getNumbersCalled().size() > 0);
			assertTrue(gameStatus.getTurnNumberCalled() > 0);
			if (gameStatus.hasTurnWinner()) {
				assertFalse(gameStatus.getTurnWinners().isEmpty());
				assertFalse(gameStatus.getWinners().isEmpty());
			}
		}
		assertTrue(gameStatus.hasWinner());
		assertTrue(gameStatus.getWinners() != null);
		assertTrue(gameStatus.getWinners().size() == 3);

	}

	@Test
	public void testInvalidTicketDimensions() {

		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			GameInputs.validateDimension("23");
		});
		assertThat(exception.getMessage(), is("'x' is required between two positive integer numbers."));

		exception = assertThrows(IllegalArgumentException.class, () -> {
			GameInputs.validateDimension("");
		});
		assertThat(exception.getMessage(), is("'x' is required between two positive integer numbers."));

		exception = assertThrows(IllegalArgumentException.class, () -> {
			GameInputs.validateDimension("x");
		});
		assertThat(exception.getMessage(), is("'x' is required between two positive integer numbers."));

		exception = assertThrows(IllegalArgumentException.class, () -> {
			GameInputs.validateDimension("2x");
		});
		assertThat(exception.getMessage(), is("'x' is required between two positive integer numbers."));

		exception = assertThrows(IllegalArgumentException.class, () -> {
			GameInputs.validateDimension("x5");
		});
		assertThat(exception.getMessage(), is("The dimension must have 2 integer numbers"));

		exception = assertThrows(IllegalArgumentException.class, () -> {
			GameInputs.validateDimension("455ex");
		});
		assertThat(exception.getMessage(), is("'x' is required between two positive integer numbers."));

		exception = assertThrows(IllegalArgumentException.class, () -> {
			GameInputs.validateDimension("-2x-34");
		});
		assertThat(exception.getMessage(), is("The dimension must have 2 positive integer numbers"));

		exception = assertThrows(IllegalArgumentException.class, () -> {
			GameInputs.validateDimension("5x-34");
		});
		assertThat(exception.getMessage(), is("The dimension must have 2 positive integer numbers"));
	}

	@Test
	public void testInvalidGameInputs() {

		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			new GameInputs(10, 4, "3x5", 5);
		});
		assertThat(exception.getMessage(), is("The range of values is insufficient to fill the ticket."));

		exception = assertThrows(IllegalArgumentException.class, () -> {
			new GameInputs(20, 4, "3x5", 6);
		});
		assertThat(exception.getMessage(), is("The numbersPerRow value cannot be greater than the number of columns."));
	}

	private GameInputs getValidGameInputs() {
		return new GameInputs(90, 5, "3x10", 5);
	}

	private GameInputs getValidSmallGameInputs() {
		return new GameInputs(10, 2, "3x4", 2);
	}

	private GameInputs getValidLargeGameInputs() {
		return new GameInputs(1000, 100, "10x90", 80);
	}

	private List<Map<Integer, String>> createTestTable() {
		return createTestTable(Arrays.asList(1, 5, 3, 8, 6, 9));
	}

	private List<Map<Integer, String>> createTestTable(List<Integer> ticketNumbers) {
		List<Map<Integer, String>> table = new ArrayList<Map<Integer, String>>(3);
		for (int i = 0; i < 3; ++i) {
			table.add(new HashMap<Integer, String>(4));
		}
//		List<Integer> numbers = Arrays.asList(1, 5, 3, 8, 6, 9);
		int numbersIndex = 0;
		for (int i = 0; i < table.size(); ++i) {
			for (int j = 0; j < 2; ++j) {
				table.get(i).put(ticketNumbers.get(numbersIndex++), "");
			}
		}
		return table;
	}
}
