package com.Mies;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.*;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.particle.ParticleComponent;
import com.almasb.fxgl.particle.ParticleEmitters;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import static com.almasb.fxgl.dsl.FXGL.*;


public class Factory implements EntityFactory {
    @Spawns("bullet1")//creates the player 1 bullet
    public Entity newBullet1(SpawnData data){
        Point2D dir = data.get("dir");//retrieves the direction it faces
        return FXGL.entityBuilder(data)
                .type(EntityType.BULLET1)//classifies it as a bullet1 entity for collisions
                .viewWithBBox("bullet.png")//entity has the bullet image with a bounding box for collisions
                .with(new ProjectileComponent(dir, 250))//projectile moves in the direction it faces when shot, at 250 pixels/s
                .with(new OffscreenCleanComponent())//if it leaves the screen it is removed.
                .with(new Physics())//uses the physics class
                .collidable()//can collide with other entities
                .build();//builds bullet1
    }
    @Spawns("bullet2") //creates the player 2 bullet
    public Entity newBullet2(SpawnData data){
        Point2D dir = data.get("dir");//retrieves the direction it faces
        return FXGL.entityBuilder(data)
                .type(EntityType.BULLET2)//classifies it as a bullet2 entity for collisions
                .viewWithBBox("bullet.png")//entity has bullet image with a bounding box for collisions
                .with(new ProjectileComponent(dir, 250))//entity moves in the direction it faces when shot
                .with(new OffscreenCleanComponent())//if it leaves the screen it is removed
                .with(new Physics())//uses the physics class
                .collidable()//can collide with other entities
                .build();//builds bullet2
    }


    @Spawns("background")//creates the background
    public Entity newBackground(SpawnData data){
        return FXGL.entityBuilder(data)
                .view("background.png")//background has the background image
                .build();//builds the background

    }


    @Spawns("barrier1")//creates the vertical barrier
    public Entity newBarrier1(SpawnData data){
        return FXGL.entityBuilder(data)
                .type(EntityType.BARRIER)//classifies it as a barrier entity for collisions
                .viewWithBBox("barrier.png")//barrier has barrier image with a bounding box for collisions
                .collidable()//can collide with other entities
                .build();//builds the vertical barrier
    }

    @Spawns("barrier2")//creates the horizontal barrier
    public Entity newBarrier2(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.BARRIER)//classifies it as a barrier entity for collisions
                .viewWithBBox("horizontalBarrier.png")//barrier has horizontal barrier image with abounding box for collisions
                .collidable()//can collide with other entities
                .build();//builds the horizontal barrier
    }


    @Spawns("player1")//creates player 1
    public Entity newPlayer1(SpawnData data){

        var hp = new HealthIntComponent(3);//creates a health component with a max health of 3

        var hpView = new ProgressBar(true);//creates a health bar that changes as the health changes
        hpView.setFill(Color.BLUE);//health bar is blue
        hpView.setMaxValue(3);//shows a maximum of 3
        hpView.setWidth(26);//width of bar
        hpView.setRotate(90);//rotates bar 90 degrees
        hpView.setTranslateY(8);//translates health bar up 8 pixels
        hpView.setTranslateX(-25);//translates health bar 25 pixels left
        hpView.currentValueProperty().bind(hp.valueProperty());//binds how many lives you have left to the changes in the progress bar

        //defines the tank texture, turns it blue, and makes it a bit brighter
        var texture = FXGL.texture("tank.png").multiplyColor(Color.BLUE).brighter().brighter();

        return FXGL.entityBuilder(data)
                .type(EntityType.PLAYER1)//classifies it as a player1 entity for collisions
                .at(25, 240)//spawns the player at (25,240) initially
                .viewWithBBox(texture)//player 1 has the texture defined above with a bounding box for collisions
                .view(hpView)//has the hp progress bar
                .with(hp)//has a hp component
                .with(new PlayerComponent())//uses the PlayerComponent class
                .with(new Input())//uses the Input class
                .with(new Physics())//uses the physics class
                .with(new KeepOnScreenComponent())//cannot leave the bounds of the screen
                .collidable()//can collide with other entities
                .build();//builds player 1
    }


    @Spawns("player2")//creates player 2
    public Entity newPlayer2(SpawnData data){
        var hp = new HealthIntComponent(3);//creates a health component with a max health of 3

        var hpView = new ProgressBar(true);//creates a health bar that changes as the health changes
        hpView.setFill(Color.LIMEGREEN);//health bar is blue
        hpView.setMaxValue(3);//shows a maximum of 3
        hpView.setWidth(26);//width of bar
        hpView.setRotate(90);//rotates bar 90 degrees
        hpView.setTranslateY(8);//translates health bar up 8 pixels
        hpView.setTranslateX(-25);//translates health bar 25 pixels left
        hpView.currentValueProperty().bind(hp.valueProperty());//binds how many lives you have left to the changes in the progress bar

        //defines the tank texture, turns it Lime green, and makes it a bit brighter
        var texture = FXGL.texture("tank.png").multiplyColor(Color.LIMEGREEN).brighter();

        return FXGL.entityBuilder(data)
                .type(EntityType.PLAYER2)//classifies it as a player2 entity for collisions
                .at(900, 400)//spawns the player at (900,400) initially
                .viewWithBBox(texture)//player 2 has the texture defined above with a bounding box for collisions
                .view(hpView)//has the hp progress bar
                .with(hp)//has a hp component
                .with(new PlayerComponent())//uses the PlayerComponent class
                .with(new Input())//uses the Input class
                .with(new Physics())//uses the physics class
                .with(new KeepOnScreenComponent())//cannot leave the bounds of the screen
                .collidable()//can collide with other entities
                .build();//builds player 2
    }


    @Spawns("explosion")//creates explosion for when a player gets hit
    public Entity newExplosion(SpawnData data) {
        var emitter = ParticleEmitters.newExplosionEmitter(100);//the radius of the explosion is 100 pixels
        emitter.setMaxEmissions(1);//how circles of particles it generates
        emitter.setSize(4, 10);//how large the particles are
        emitter.setStartColor(Color.YELLOW);//the particles start off as yellow
        emitter.setEndColor(Color.RED);//the particles turn red
        emitter.setSpawnPointFunction(i -> new Point2D(64, 64));//where the explosion will spawn

        return entityBuilder(data)
                .with(new ExpireCleanComponent(Duration.seconds(10)))//how long the explosion lasts
                .with(new ParticleComponent(emitter))//the particles emit outwards
                .build();//build the explosion emitter
    }

    @Spawns("deadTank")//creates the dead tank for when the player dies
    public Entity newDeadTank(SpawnData data){
        return FXGL.entityBuilder(data)
                .view("deadTank.png")//sets the entity picture as deadTank
                .with(new PlayerComponent())//uses the playercomponent class
                .scale(1.25,1.25)//scales the image to be a bit faster
                .build();//build the dead tank

    }
}
