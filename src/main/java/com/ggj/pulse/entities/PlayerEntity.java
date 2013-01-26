package com.ggj.pulse.entities;

import com.badlogic.gdx.math.Vector2;
import com.ggj.pulse.entities.AbstractEntity;

/**
 * Date: 13.26.1
 * Time: 10:45
 *
 * @author Kristaps Kohs
 */
public class PlayerEntity extends AbstractEntity {
    private Vector2 target = new Vector2();

    private boolean shouldMove;

    public Vector2 getTarget() {
        return target;
    }

    public void setTarget(Vector2 target) {
        this.target = target;
    }

    public boolean isShouldMove() {
        return shouldMove;
    }

    public void setShouldMove(boolean shouldMove) {
        this.shouldMove = shouldMove;
    }
}
