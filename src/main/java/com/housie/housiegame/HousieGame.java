/**********************************************************************
 * Copyright (C) 2020 to present, Patrik Araujo - All Rights Reserved
 * You may only use this code for the purposes authorized
 * by the Author. This code cannot be distributed without
 * consent of the Author. The author is not responsible 
 * by how this software is used.
 **********************************************************************/

package com.housie.housiegame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HousieGame {

	private GameInputs inputs;
	private List<Player> players;
	private List<Rules> rules;
	private List<Integer> drawnNumbers;
	private List<Integer> calledNumbers;
	private int numbersDrawn = 0;
	private Map<Rules, Player> winners;
	private Map<Rules, Player> turnWinners;

	public HousieGame(GameInputs inputs, List<Rules> rules, List<Player> players, List<Integer> drawnNumbers) {
		inputs.validateInputs();
		this.inputs = inputs;
		this.rules = rules;
		this.players = players;
		this.drawnNumbers = drawnNumbers;
		this.calledNumbers = new ArrayList<Integer>();
		this.winners = new HashMap<Rules, Player>();
		this.turnWinners = new HashMap<Rules, Player>();
	}

	public HousieGame(GameInputs inputs) {
		inputs.validateInputs();
		this.inputs = inputs;
		this.rules = createRules();
		this.players = createPlayers(inputs.numberOfPlayers);
		this.drawnNumbers = drawNumbers();
		this.calledNumbers = new ArrayList<Integer>();
		this.winners = new HashMap<Rules, Player>();
		this.turnWinners = new HashMap<Rules, Player>();
	}

	public GameStatus runNextNumber() {
		GameStatus gameStatus = null;
		if (!isGameOver()) {
			int calledNumber = pickNumber();
			this.calledNumbers.add(calledNumber);
			notifyPlayersOfCalledNumber(calledNumber);
			gameStatus = buildGameStatus();
			for (Rules rule : winners.keySet()) {
				acknowledgeWinner(rule);
			}
		} else {
			gameStatus = buildGameStatus();
		}
		return gameStatus;
	}

	private void notifyPlayersOfCalledNumber(int calledNumber) {
		for (Player player : this.players) {
			PlayerStatus status = player.acceptNumberCalled(calledNumber);
			if (status.isWinner()) {
				for (Rules rule : status.getWins()) {
					addWinner(player, rule);
					notifyWinningRule(rule);
				}
			}
		}
	}

	private void addWinner(Player player, Rules rule) {
		turnWinners.put(rule, player);
		winners.put(rule, player);
	}

	private void notifyWinningRule(Rules rule) {
		for (Player player : this.players) {
			player.ruleClaimed(rule);
		}
	}

	public void acknowledgeWinner(Rules rule) {
		turnWinners.remove(rule);
	}

	public GameStatus getGameStatus() {
		return buildGameStatus();
	}

	private GameStatus buildGameStatus() {
		int turnNumberCalled = calledNumbers.size() > 0 ? calledNumbers.get(calledNumbers.size() - 1) : -1;
		return new GameStatus(new HashMap<Rules, Player>(winners), new HashMap<Rules, Player>(turnWinners),
				Collections.unmodifiableList(calledNumbers), turnNumberCalled, isGameOver());
	}

	public boolean isGameOver() {
		return calledNumbers.size() == inputs.numbersRange || winners.size() == rules.size();
	}

	public boolean hasWinner() {
		return !winners.isEmpty();
	}

	private int pickNumber() {
		return drawnNumbers.get(numbersDrawn++);
	}

	public List<Integer> drawNumbers() {
		List<Integer> values = IntStream.range(1, inputs.numbersRange + 1).boxed().collect(Collectors.toList());
		Collections.shuffle(values);
		return values;
	}

	private List<Player> createPlayers(int numberOfPlayers) {
		List<Player> players = new ArrayList<Player>();
		for (int i = 0; i < numberOfPlayers; ++i) {
			Player player = new Player("Player#" + i, new ArrayList<Rules>(this.rules));
			player.assignTicket(new Ticket(inputs.rows, inputs.columns, inputs.numbersRange, inputs.numbersPerRow));
			players.add(player);
		}
		return players;
	}

	private List<Rules> createRules() {
		return Arrays.asList(Rules.EARLY_FIVE, Rules.TOP_LINE, Rules.FULL_HOUSE);
	}

	public List<Rules> getRules() {
		return Collections.unmodifiableList(rules);
	}

	public List<Player> getPlayers() {
		return Collections.unmodifiableList(players);
	}

	public Map<Rules, Player> getWinners() {
		return Collections.unmodifiableMap(winners);
	}

}
