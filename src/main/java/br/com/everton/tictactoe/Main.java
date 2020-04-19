package br.com.everton.tictactoe;

import java.io.IOException;

import br.com.everton.tictactoe.core.Game;

public class Main {

	public static void main(String[] args) throws IOException {
		Game game = new Game();
		game.play();
	}

}
