package com.Mies;


import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.geometry.Rectangle2D;
import javafx.scene.text.Text;
;
import static com.almasb.fxgl.dsl.FXGL.*;

public class PlayerComponent extends Component {

    public void respawn(){//respawn player after hit with a bullet
        //respawns player in a random location on the map
        entity.setPosition(FXGLMath.randomPoint(new Rectangle2D(-200,-200, getAppWidth(),getAppHeight())));
        if (Physics.BarrierCollide){//if it respawns within a barrier, it will try again
            entity.setPosition(FXGLMath.randomPoint(new Rectangle2D(-200,-200, getAppWidth(),getAppHeight())));
        }
    }

    public void kill() {//after being hit 3 times, the player dies
        //spawns deadTank at the location of the tank that just dies
        Main.deadTank.setPosition(entity.getCenter().getX() - 5,entity.getCenter().getY() - 5);
        Main.deadTank.setOpacity(100);

        //removes the ability of the killed tank to collide with other objects
        entity.removeComponent(CollidableComponent.class);
        //sets opacity of the killed tank to 0 so it is no longer visible on screen
        entity.setOpacity(0);
        //Instead of removing the entity from the world, I made it invisible because there was an error that would be thrown after the dead player creates any input because it tries to move an entity that doesn't exist

        var hp = Main.player1.getComponent(HealthIntComponent.class);//retrieves the health of player 1
        Text winnerMessage;//initializes winner message variable

        if (hp.getValue() > 1) {//if the hp of player1 >1, player 1 must have won
            winnerMessage = new Text("Winner: Player 1");
        } else {//if the hp of player1<1, player 2 must have won
            winnerMessage = new Text("Winner: Player 2");
        }

        //places the winnerMessage at the desired location
        winnerMessage.setTranslateX(460); //X Location
        winnerMessage.setTranslateY(250); //Y Location

        //text on the screen tells the user how to leave the game
        Text exitMenu = new Text("–Press ESC to exit to main menu–");
        //places the exitMenu text at the desired location
        exitMenu.setTranslateX(420);//X Location
        exitMenu.setTranslateY(275);//Y Location

        //displays the two texts
        FXGL.getGameScene().addUINode(winnerMessage);
        FXGL.getGameScene().addUINode(exitMenu);
    }







}
