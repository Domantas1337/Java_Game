package main;

import PlayerPack.InitPlayer;


public class Camera {
    private float x, y;
    public Camera(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void update(InitPlayer player) {
        if(x - player.getHitx() + Game.GAME_WIDTH / 6 > (-Game.GAME_WIDTH - Game.GAME_WIDTH / 2.25 + 32) && x - player.getHitx() + Game.GAME_WIDTH / 6 <  -10) {
            x = -player.getHitx() + Game.GAME_WIDTH / 6;
        }
    }

    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }
}
