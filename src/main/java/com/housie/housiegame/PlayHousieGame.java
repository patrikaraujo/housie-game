/**********************************************************************
 * Copyright (C) 2020 to present, Patrik Araujo - All Rights Reserved
 * You may only use this code for the purposes authorized
 * by the Author. This code cannot be distributed without
 * consent of the Author. The author is not responsible 
 * by how this software is used.
 **********************************************************************/

package com.housie.housiegame;

import java.io.IOException;
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

	private static int readInteger(Scanner scanner) {
		return readInteger(scanner, null);
	}

	private static int readInteger(Scanner scanner, Integer defaultValue) {
		while (true) {
			try {
				String input = scanner.nextLine();
				if (input.isEmpty() && defaultValue != null)
					return defaultValue;
				return Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.print("Please enter an integer value: ");
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

	public static void main(String[] args) {
		System.out.println("**** Let's Play Housie ****");
		System.out.println("Note: - Press 'Q' to quit any time.");
		Scanner scanner = new Scanner(System.in);
		GameInputs gameInputs = null;
		while (true) {
			System.out.print(">> Enter the number range (1-n): ");
			int numberRange = readInteger(scanner);
			System.out.print(">> Enter number of players playing the game: ");
			int players = readInteger(scanner);
			System.out.print(">> Enter ticket size: Default to 3x10: ");
			String ticketSize = readTicketSize(scanner, "3x10");
			System.out.print(">> Enter numbers per row. Default to 5: ");
			int numbersPerRow = readInteger(scanner, 5);
			try {
				gameInputs = new GameInputs(numberRange, players, ticketSize, numbersPerRow);
				break;
			} catch (IllegalArgumentException e) {
				System.out.println(e.getLocalizedMessage());
				System.out.println("Review the inputs and try again.");
			}
		}

		Terminal terminal = null;
		try {
			terminal = TerminalBuilder.builder().jna(true).system(true).build();
		} catch (IOException e) {
			e.printStackTrace();
		}

		HousieGame game = new HousieGame(gameInputs);

		GameStatus status = game.getGameStatus();
		terminal.enterRawMode();
		NonBlockingReader reader = terminal.reader();
		int valueRead = 0;
		do {
			System.out.print(">> Press N to generate next number: ");
			try {
				valueRead = reader.read();
				if (valueRead == CHAR_N || valueRead == CHAR_UPPERCASE_N) {
					status = game.runNextNumber();
					System.out.println(status.getTurnNumberCalled());
					if (status.hasTurnWinner()) {
						for (Entry<Rules, Player> entry : status.getTurnWinners().entrySet()) {
							System.out.println(entry.getValue().toString() + " has won " + entry.getKey().toString());
						}
					}
				} else {
					System.out.println("Please press N or Q to quit");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (valueRead != CHAR_Q && valueRead != CHAR_UPPERCASE_Q && !status.isGameOver());
		System.out.println("Game Summary");
		for (Entry<Rules, Player> entry : status.getWinners().entrySet()) {
			System.out.println(entry.getValue().toString() + " has won " + entry.getKey().toString());
		}

	}

}
