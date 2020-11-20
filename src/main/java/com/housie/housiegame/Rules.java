/**********************************************************************
 * Copyright (C) 2020 to present, Patrik Araujo - All Rights Reserved
 * You may only use this code for the purposes authorized
 * by the Author. This code cannot be distributed without
 * consent of the Author. The author is not responsible 
 * by how this software is used.
 **********************************************************************/

package com.housie.housiegame;

public enum Rules {

	FULL_HOUSE("Full House") {
		@Override
		public boolean isWinner(Ticket ticket) {
			return ticket.isTicketFullyMarked();
		}
	},
	EARLY_FIVE("Early Five") {
		@Override
		public boolean isWinner(Ticket ticket) {
			return ticket.getMarkedCount() == 5;
		}
	},
	TOP_LINE("Top Line") {
		@Override
		public boolean isWinner(Ticket ticket) {
			return ticket.isRowFullyMarked(0);
		}
	};

	private Rules(String displayName) {
		this.displayName = displayName;
	}

	private final String displayName;

	public abstract boolean isWinner(Ticket tiket);

	@Override
	public String toString() {
		return this.displayName;
	}
}
