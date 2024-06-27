package com.project.cardgame.games;

import com.project.cardgame.model.Deck;
import com.project.cardgame.view.CommandLineView;
import com.project.cardgame.view.GameSwingView;
import com.projet.cardgame.controller.GameController;

public class Games {
    public static void main(String args[]) {
        GameSwingView gameSwingView = new GameSwingView();
        gameSwingView.createAndShowGUI(); // Assurez-vous que cette ligne est présente
        GameController gc = new GameController(new Deck(), gameSwingView, new HighCardGameEvaluator());
        gc.run();
    }
}
