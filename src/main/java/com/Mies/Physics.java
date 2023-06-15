package com.Mies;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;


import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.onCollision;

public class Physics extends Component {
    public static boolean BarrierCollide;//sets a boolean for whether a tank is colliding with a barrier when it respawns
    public void worldPhysics() {//creates physics for the world

        //on collision with bullet and player 1
        onCollisionBegin(EntityType.BULLET2, EntityType.PLAYER1, (bullet, player1) -> {
            //run the bulletCollide method for player1
            entity.getComponent(Physics.class).bulletCollide(bullet, player1);
        });

        //on collision with bullet and player 2
        onCollisionBegin(EntityType.BULLET1, EntityType.PLAYER2, (bullet, player2) -> {
            //run the bulletCollide method for player 2
            entity.getComponent(Physics.class).bulletCollide(bullet, player2);
        });

        //on collision with player 1 and player 2
        onCollision(EntityType.PLAYER1, EntityType.PLAYER2, (player1, player2) -> {
            //run the playerCollide method for player 1 and player 2
            player1.getComponent(Physics.class).playerCollide(player1, player2);
        });

        //on collision with player 1 and a barrier
        onCollision(EntityType.PLAYER1, EntityType.BARRIER, (player1, barrier1) -> {
            //run the barrierCollide method for player 1 and barrier
            player1.getComponent(Physics.class).barrierCollide(player1, barrier1 );
        });

        //on collision with player 2 and a barrier
        onCollision(EntityType.PLAYER2, EntityType.BARRIER, (player2, barrier1) -> {
            //run the barrierCollide method for player 2 and barrier
            player2.getComponent(Physics.class).barrierCollide(player2, barrier1 );
        });

        //on collision with bullet2 and a barrier
        onCollision(EntityType.BULLET2, EntityType.BARRIER, (bullet, barrier1) -> {
            //remove the bullet2 from the world
            bullet.removeFromWorld();
        });

        //on collision with bullet1 and a barrier
        onCollision(EntityType.BULLET1, EntityType.BARRIER, (bullet, barrier1) -> {
            //remove the bullet1 from the world
            bullet.removeFromWorld();
        });
    }
    public void barrierCollide(Entity entity, Entity barrier) {//barrierCollide method
        BarrierCollide = true;//when they collide, the boolean becomes true

        //if the entity X position is less than the barrier X position + its length, and the entity X position + the entity width is greater than the barrier X position, and the entity Y position is less than the barrier Y position + the barrier height, and the entity Y position + the entity height is greater than barrier Y position
        if (entity.getX() < barrier.getX() + barrier.getWidth() && entity.getX() + entity.getWidth() > barrier.getX() && entity.getY() < barrier.getY() + barrier.getHeight() && entity.getY() + entity.getHeight() > barrier.getY()) {

            //the difference in X position
            double deltaX = Math.min(Math.abs(entity.getX() + entity.getWidth() - barrier.getX()), Math.abs(entity.getX() - barrier.getX() - barrier.getWidth()));
            //the difference in the Y position
            double deltaY = Math.min(Math.abs(entity.getY() + entity.getHeight() - barrier.getY()), Math.abs(entity.getY() - barrier.getY() - barrier.getHeight()));

            //if the difference in the X position is less than the difference in the Y position
            if (deltaX < deltaY) {
                if (entity.getX() + entity.getWidth() / 2 < barrier.getX() + barrier.getWidth() / 2) {
                    entity.translateX(-deltaX);//translate entity in the negative difference in the X position
                } else {
                    entity.translateX(deltaX);//translate the entity in the X direction
                }
            } else {
                if (entity.getY() + entity.getHeight() / 2 < barrier.getY() + barrier.getHeight() / 2) {
                    entity.translateY(-deltaY);//translate the entity in the negative difference in the Y position
                } else {
                    entity.translateY(deltaY);//translate the entity in the Y direction
                }
            }
        }
    }

    public void playerCollide( Entity player1, Entity player2) {//method for when players collide
        double deltaX = player1.getCenter().getX() - player2.getCenter().getX();//get the difference in the X positions
        double deltaY = player1.getCenter().getY() - player2.getCenter().getY();//get the difference between the Y positions


        if (deltaX < deltaY) {//if the difference in the X position is less than the difference in the Y position
            player1.translateX( -6);//translate player 1 6 units back
            player2.translateX( 6);//translate player 2 4 units forward
        } else {
            player1.translateY( -6);//translate player 1 6 units up
            player2.translateY( 6);//translate player 2 4 units down
        }

    }

    public void bulletCollide(Entity bullet, Entity player){//method for whn players and bullets collide
        var hp = player.getComponent(HealthIntComponent.class);//get the player HP
        bullet.removeFromWorld();//remove the bullet from the world
        FXGL.getGameScene().getViewport().shake(10,0.5);//add the onscreen shake effect
        Point2D explosionSpawnPoint = player.getCenter().subtract(64, 64);//place the explosion effect at the hit character
        spawn("explosion", explosionSpawnPoint);//spawn the explosion
        if (hp.getValue() > 1){//if hp is greater than 1
            hp.damage(1);//do one point of damage
            player.getComponent(PlayerComponent.class).respawn();//respawn the player
        }
        else{//if hp is 1
            hp.damage(1);//do one point of damage
            player.getComponent(PlayerComponent.class).kill();//kill the oplayer
        }
    }
}
