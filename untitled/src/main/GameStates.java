package main;

public enum GameStates {

	PLAYING, MENU, EDIT, GAME_OVER, LEVEL1, LEVEL2, LEVEL3;

	public static GameStates gameState = MENU;
	public static GameStates gameStatesEdit = LEVEL1;
	public static GameStates levelState = LEVEL1;

	public static void SetGameState(GameStates state) {
		gameState = state;
	}
	public static void SetGameStateEdit(GameStates state) {
		gameStatesEdit = state;
	}
	public static GameStates getGameStateEdit(){return gameStatesEdit;}
}
