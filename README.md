# TicTacToe
Play an AI in Tic Tac Toe!

This includes two versions of AI. The first is a minimax algorithm that serves as an unbeatable AI.

The second is my take (no outside algorithms) on a very primitive neural network that works by weighting moves as positive or negative based on results and enters it into a file (history.txt). When searching for a move, the program interprets that file to find what move to do based on past moves - if it hasn't encountered a particular game state, it finds the closest state and uses that as a starting point for the current state.
