package com.Mies;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Main extends GameApplication {


    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1017);
        settings.setHeight(508);
        settings.setTitle("TankGame");
        settings.setVersion("1.0");
        settings.setFullScreenAllowed(true);
        settings.setPreserveResizeRatio(true);
        settings.setManualResizeEnabled(true);
        settings.setAppIcon("tank.png");
        settings.setMainMenuEnabled(true);
        settings.setFullScreenFromStart(false);
    }

    //initialize entity variables
    public static Entity player1;
    public static Entity player2;
    public static Entity deadTank;
    public Entity barrier;


    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new Factory());

        //create background
        spawn("background");

        //spawn the dead tank
        deadTank = spawn("deadTank");
        deadTank.setOpacity(0);

        //spawn barriers
        barrier = spawn("barrier1", 100,100);
        barrier = spawn("barrier1",450,-120);
        barrier = spawn("barrier1",450,0);
        barrier = spawn("barrier1",750,285);
        barrier = spawn("barrier2",151,350);
        barrier = spawn("barrier2",200,100);
        barrier = spawn("barrier2", 550,285);
        barrier = spawn("barrier2",700,100);

        //spawn players
        player1 = spawn("player1");
        player2 = spawn ("player2");
    }

    @Override
    protected void initInput() {
        //Player 1 move forward/backward
        onKey(KeyCode.W, "Player1 Move Forward", () -> player1.getComponent(Input.class).moveForward());
        onKey(KeyCode.S, "Player1 Move Backward", () -> player1.getComponent(Input.class).moveBack());

        //Player 1 rotate left/right
        onKey(KeyCode.A, "Player1 Rotate Left", () -> player1.getComponent(Input.class).rotateLeft());
        onKey(KeyCode.D, "Player1 Rotate Right", () -> player1.getComponent(Input.class).rotateRight());

        //Player 1 shoot
        onKeyDown(KeyCode.C, "Player1Shoot", () -> player1.getComponent(Input.class).shoot1());

        //Player 2 move forward/backward
        onKey(KeyCode.I, "Player2 Move Forward", () -> player2.getComponent(Input.class).moveForward());
        onKey(KeyCode.K, "Player2 Move Backward", () -> player2.getComponent(Input.class).moveBack());

        //Player 2 rotate left/right
        onKey(KeyCode.J, "Player2 Rotate Left", () -> player2.getComponent(Input.class).rotateLeft());
        onKey(KeyCode.L, "Player2 Rotate Right", () -> player2.getComponent(Input.class).rotateRight());

        //Player 2 shoot
        onKeyDown(KeyCode.PERIOD, "Player2Shoot", () -> player2.getComponent(Input.class).shoot2());
    }

    @Override
    protected void initPhysics() {
        //runs physics properties for each player
        player1.getComponent(Physics.class).worldPhysics();
        player2.getComponent(Physics.class).worldPhysics();
    }

    //launch game
    public static void main(String[] args) {
        launch(args);
    }
}