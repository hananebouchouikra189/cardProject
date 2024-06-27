package com.project.cardgame.games;

import java.util.List;

import com.project.cardgame.model.Player;

public interface GameEvaluator {
	
	public Player evaluateWinner(List<Player> players) ;
}