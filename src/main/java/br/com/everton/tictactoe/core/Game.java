package br.com.everton.tictactoe.core;

import java.io.IOException;

import br.com.everton.tictactoe.Constants;
import br.com.everton.tictactoe.score.FileScoreManager;
import br.com.everton.tictactoe.score.ScoreManager;
import br.com.everton.tictactoe.ui.UI;

public class Game {

	private Board board = new Board();
	private Player[] players = new Player[Constants.SYMBOL_PLAYERS.length];
	private int currentPlayerIndex = -1;
	private ScoreManager scoreManager;

	public void play() throws IOException {

		scoreManager = createScoreManager();

		UI.printGameTitle();
		for (int i = 0; i < players.length; i++) {
			players[i] = createPlayer(i);
		}

		boolean gameEnded = false;
		Player currentPlayer = nextPlayer();
		Player winner = null;

		while (!gameEnded) {
			board.print();

			boolean sequenceFound;
			try {
				sequenceFound = currentPlayer.play();
			} catch (InvalidMoveException e) {
				UI.printText("ERRO: " + e.getMessage());
				continue;
			}

			if (sequenceFound) {
				gameEnded = true;
				winner = currentPlayer;

			} else if (board.isFull()) {
				gameEnded = true;
			}

			currentPlayer = nextPlayer();
		}

		if (winner == null) {
			UI.printText("O jogo terminou empatado");
		} else {
			UI.printText("o Jogador '" + winner.getName() + "' venceu o jogo!");

			scoreManager.saveScore(winner);
		}

		board.print();
		UI.printText("Fim do Jogo!");
	}

	private Player createPlayer(int index) {
		String name = UI.readInput("Jogador " + (index + 1) + " =>");
		char symbol = Constants.SYMBOL_PLAYERS[index];
		Player player = new Player(name, board, symbol);

		Integer score = scoreManager.getScore(player);
		
		if(score != null) {
			UI.printText("O jogador '" + player.getName() + "' já possui " + score + " vitoria(s)!");
		}

		UI.printText("O Jogador '" + name + "' vai usar o símbolo '" + symbol + "'");

		return player;
	}

	// Forma 1
//	private Player nextPlayer() {
//		currentPlayerIndex++;
//		if(currentPlayerIndex >= players.length) {
//			currentPlayerIndex = 0;
//		}
//		return players[currentPlayerIndex];
//
//	}
	// Forma 2
	private Player nextPlayer() {
		currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
		return players[currentPlayerIndex];

	}

	private ScoreManager createScoreManager() throws IOException {
		return new FileScoreManager();
	}
}
