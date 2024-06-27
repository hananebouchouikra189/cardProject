package com.projet.cardgame.controller;

import java.util.ArrayList;
import java.util.List;

import com.project.cardgame.games.GameEvaluator;
import com.project.cardgame.model.Deck;
import com.project.cardgame.model.Player;
import com.project.cardgame.model.PlayingCard;
import com.project.cardgame.view.GameViewable;

public class GameController {

	enum GameState {
		AddingPlayers, CardsDealt, WinnerRevealed;
	}

	Deck deck;
	List<Player> players;
	Player winner;
	GameViewable commandLineView;
	GameState gameState;
	GameEvaluator evaluator;

	public GameController(Deck deck, GameViewable commandLineView, GameEvaluator gameEvaluator) {
		super();
		this.deck = deck;
		this.commandLineView = commandLineView;
		this.players = new ArrayList<Player>();
		this.gameState = GameState.AddingPlayers;
		this.evaluator = gameEvaluator;
		commandLineView.setController(this);
	}

	public void run() {
		while (gameState == GameState.AddingPlayers) {
			commandLineView.promptForPlayerName();
		}

		switch (gameState) {
		case CardsDealt:
			commandLineView.promptForFlip();
			break;
		case WinnerRevealed:
			commandLineView.promptForNewGame();
			break;
		}
	}

	public void addPlayer(String playerName) {
		if (gameState == GameState.AddingPlayers) {
			players.add(new Player(playerName));
			commandLineView.showPlayerName(players.size(), playerName);
		}
	}

	public void startGame() {
		if (gameState != GameState.CardsDealt) {
			deck.shuffle();
			int playerIndex = 1;
			for (Player player : players) {
				player.addCardToHand(deck.removeTopCard());
				commandLineView.showFaceDownCardForPlayer(playerIndex++, player.getName());
			}
			gameState = GameState.CardsDealt;
		}
		this.run();
	}

	public void flipCards() {
		int playerIndex = 1;
		for (Player player : players) {
			PlayingCard pc = player.getCard(0);
			pc.flip();
			commandLineView.showCardForPlayer(playerIndex++, player.getName(), 
					pc.getRank().toString(), pc.getSuit().toString());
		}

		evaluateWinner();
		displayWinner();
		rebuildDeck();
		gameState = GameState.WinnerRevealed;
		this.run();
	}

	void evaluateWinner() {
		winner = evaluator.evaluateWinner(players);
	}

	void displayWinner() {
		commandLineView.showWinner(winner.getName());
	}

	void rebuildDeck() {
		for (Player player : players) {
			deck.returnCardToDeck(player.removeCard());
		}
	}
	void exitGame() {
		System.exit(0);
	}

	public void nextActtion(String nextChoise) {
		if("+q".equals(nextChoise)) exitGame();
		else startGame();
		
	}

}