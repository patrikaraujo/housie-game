/**********************************************************************
 * Copyright (C) 2020 to present, Patrik Araujo - All Rights Reserved
 * You may only use this code for the purposes authorized
 * by the Author. This code cannot be distributed without
 * consent of the Author. The author is not responsible 
 * by how this software is used.
 **********************************************************************/

package com.housie.housiegame;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private String name;
	private List<Ticket> tickets;
	private List<Rules> rules;
	private List<Rules> wins;
	private List<Rules> turnWins;

	public Player(String name, List<Rules> rules) {
		this.name = name;
		this.rules = rules;
		this.wins = new ArrayList<Rules>();
		this.turnWins = new ArrayList<Rules>();
		this.tickets = new ArrayList<Ticket>(1);
	}
	
	public void assignTicket(Ticket ticket) {
		this.tickets.add(ticket);
	}
	
	public void ruleClaimed(Rules rule) {
		rules.remove(rule);
		turnWins.remove(rule);
	}

	public PlayerStatus acceptNumberCalled(int number) {
		// Mark number in a ticket
		List<Rules> winningRules = new ArrayList<Rules>();
		for (Ticket ticket : tickets) {
			ticket.markNumber(number);
			// Verify if winner against the rules
			for (Rules rule : rules) {
				if (rule.isWinner(ticket)) {
					turnWins.add(rule);
					wins.add(rule);
					winningRules.add(rule);
				}
			}
		}
		// Return a PlayerStatus object
		PlayerStatus playerStatus = buildPlayerStatus();
		for (Rules rule : winningRules) {
			ruleClaimed(rule);
		}
		return playerStatus;
	}
	
	public boolean isWinner() {
		return !this.wins.isEmpty();
	}
	
	private PlayerStatus buildPlayerStatus() {
		return new PlayerStatus(new ArrayList<Rules>(turnWins));
	}
	
	public List<Rules> getWins(){
		return this.wins;
	}
	
	public String toString() {
		return this.name;
	}

}
