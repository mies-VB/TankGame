package com.Mies;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;

import static com.almasb.fxgl.core.math.Vec2.fromAngle;
import static com.almasb.fxgl.dsl.FXGL.spawn;

public class Input extends Component {
    public HealthIntComponent hp;//initializes variable hp

    public void rotateLeft(){//rotates the player left
        hp = entity.getComponent(HealthIntComponent.class); //retrieves the health of the entity
        if(hp.getValue()>0) {//action will only occur if entity is not dead
            entity.rotateBy(-2);//rotates entity left by 2 degrees at a time
        }
    }
    public void rotateRight(){//rotates player right
        hp = entity.getComponent(HealthIntComponent.class);//retrieves health of the entity
        if(hp.getValue()>0) {//action will only occur if entity is not dead
            entity.rotateBy(2);//rotates entity right by 2 degrees at a time
        }
    }
    public void moveForward(){//moves entity forward
        hp = entity.getComponent(HealthIntComponent.class);//retrieves health of the entity
        Vec2 dir = fromAngle(entity.getRotation())//retrieves the rotation of the entity
                .mulLocal(1.25);//multiplies it by 1.25
        if(hp.getValue()>0) {//action will only occur if entity is not dead
            entity.translate(dir);//translates entity in the direction it is faced
        }
    }
    public void moveBack(){//moves entity backwards
        hp = entity.getComponent(HealthIntComponent.class);//retrieves health of the entity
        Vec2 dir = fromAngle(entity.getRotation() + 180)//retrieves the rotation of the entity
                .mulLocal(1.25);//multiplies it by 1.25
        if(hp.getValue()>0) {//action will only occur if entity is not dead
            entity.translate(dir);//translates entity backwards from the direction it is faced
        }
    }

    public void shoot1(){//Player 1 shoots
        hp = entity.getComponent(HealthIntComponent.class);//retrieves health of the entity
        Vec2 dir = fromAngle(entity.getRotation());//retrieves rotation of the entity
        if(hp.getValue()>0) {//action will only occur if entity is not dead
            //spawns bullet in the centre of the player 1 tank facing the direction of the barrel
            spawn("bullet1", new SpawnData(entity.getCenter().getX() - 5, entity.getCenter().getY() - 5)
                    .put("dir", dir.toPoint2D())
            );
        }
    }

    public void shoot2(){//Player 2 shoots
        hp = entity.getComponent(HealthIntComponent.class);//retrieves the health of the entity
        Vec2 dir = fromAngle(entity.getRotation());//retrieves the rotation of the entity
        if(hp.getValue()>0) {//action only occurs if entity is not dead
            //spawns bullet in the player 2 tank facing the direction of the barrel
            spawn("bullet2", new SpawnData(entity.getCenter().getX() - 5, entity.getCenter().getY() - 5)
                    .put("dir", dir.toPoint2D())
            );
        }
    }
}
