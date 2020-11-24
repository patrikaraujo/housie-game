/**********************************************************************
 * Copyright (C) 2020 to present, Patrik Araujo - All Rights Reserved
 * You may only use this code for the purposes authorized
 * by the Author. This code cannot be distributed without
 * consent of the Author. The author is not responsible 
 * by how this software is used.
 **********************************************************************/

package com.housie.housiegame;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;

public class PlayHousieGame {

	private static final int CHAR_N = (int) 'n';
	private static final int CHAR_UPPERCASE_N = (int) 'N';
	private static final int CHAR_Q = (int) 'q';
	private static final int CHAR_UPPERCASE_Q = (int) 'Q';

	private static int readInteger(Scanner scanner, String fieldName) {
		return readInteger(scanner, null, fieldName);
	}

	private static int readInteger(Scanner scanner, Integer defaultValue, String fieldName) {
		while (true) {
			try {
				String input = scanner.nextLine();
				int intValue = input.isEmpty() && defaultValue != null ? defaultValue : Integer.parseInt(input);
				return GameInputs.requirePositiveInteger(intValue, fieldName);
			} catch (IllegalArgumentException e) {
				System.out.print("Please enter a positive integer value: ");
			}
		}
	}

	private static String readTicketSize(Scanner scanner, String defaultValue) {
		while (true) {
			try {
				String input = scanner.nextLine();
				if (input.isEmpty())
					return defaultValue;
				GameInputs.validateDimension(input);
				return input;
			} catch (IllegalArgumentException e) {
				System.out.print(e.getLocalizedMessage() + ": ");
			}
		}
	}

	private static GameInputs captureGameInputs() {
		System.out.println("**** Let's Play Housie ****");
		System.out.println("Note: - Press 'Q' to quit any time.\n");
		Scanner scanner = new Scanner(System.in);
		GameInputs gameInputs = null;
		while (true) {
			System.out.print(">> Enter the number range (1-n): Default to 1-90: ");
			int numbersRange = readInteger(scanner, 90, "numbersRange");
			System.out.print(">> Enter number of players playing the game: ");
			int players = readInteger(scanner, "players");
			System.out.print(">> Enter ticket size: Default to 3x10: ");
			String ticketSize = readTicketSize(scanner, "3x10");
			System.out.print(">> Enter numbers per row: Default to 5: ");
			int numbersPerRow = readInteger(scanner, 5, "numbersPerRow");
			try {
				gameInputs = new GameInputs(numbersRange, players, ticketSize, numbersPerRow);
				break;
			} catch (IllegalArgumentException e) {
				System.out.println(e.getLocalizedMessage() + "\n");
				System.out.println("Review the inputs and try again.\n");
			}
		}
		return gameInputs;
	}

	private static GameStatus drawGameNumbers(HousieGame game) {
		Terminal terminal = null;
		try {
			terminal = TerminalBuilder.builder().jna(true).system(true).build();
		} catch (IOException e) {
			e.printStackTrace();
		}

		GameStatus status = game.getGameStatus();
		terminal.enterRawMode();
		NonBlockingReader reader = terminal.reader();
		System.out.println("\n**** Tickets Created Successfully ****");
		System.out.println(">> Press N to generate next number: ");
		int valueRead = 0;
		do {
			System.out.print("Next number is: ");
			try {
				valueRead = reader.read();
				if (valueRead == CHAR_N || valueRead == CHAR_UPPERCASE_N) {
					status = game.runNextNumber();
					System.out.println(status.getTurnNumberCalled());
					if (status.hasTurnWinner()) {
						for (Entry<Rules, Player> entry : status.getTurnWinners().entrySet()) {
							System.out.println("We have a winner: " + entry.getValue().toString() + " has won '"
									+ entry.getKey().toString() + "' winning combination.");
						}
					}
				} else {
					System.out.println("Please press N or Q to quit");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (valueRead != CHAR_Q && valueRead != CHAR_UPPERCASE_Q && !status.isGameOver());
		return status;
	}

	public static void displayGameOver(GameStatus status) {
		System.out.println("\n***** Game Over *****\n");
		System.out.println("=====================");
		System.out.println("       Summary");
		Map<String, String> winnersMap = new HashMap<String, String>();
		for (Entry<Rules, Player> entry : status.getWinners().entrySet()) {
			String message = "";
			if (winnersMap.containsKey(entry.getValue().toString()))
				message += winnersMap.get(entry.getValue().toString()) + " and " + entry.getKey().toString();
			else
				message += entry.getKey().toString();
			winnersMap.put(entry.getValue().toString(), message);
		}
		for (Entry<String, String> entry : winnersMap.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
		System.out.println("=====================");
	}

	public static void main(String[] args) {
		GameInputs gameInputs = captureGameInputs();
		HousieGame game = new HousieGame(gameInputs);
		GameStatus status = drawGameNumbers(game);
		displayGameOver(status);
	}

}
