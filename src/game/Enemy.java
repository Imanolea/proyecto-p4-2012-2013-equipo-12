package game;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.scene.Spatial;

public class Enemy {
    private Spatial spatial;
    private float timer = (float) Math.random()*4 +1;
    private final int originalHealth = 2;
    private RigidBodyControl fDeathEnemy;
    private int health;
    private boolean death;
    private boolean active;
    private boolean aspired;
    private boolean hasBeenAspired;
    
    public Enemy() {
        spatial = null;
        health = originalHealth;
        death = false;
        fDeathEnemy = new RigidBodyControl(1f);
        hasBeenAspired = false;
        active = true;
    }
    
    public Enemy(Spatial s) {
        spatial = s;
        health = originalHealth;
        death = false;
        fDeathEnemy = new RigidBodyControl(1f);
        hasBeenAspired = false;
        active = true;
    }

    public void setSpatial(Spatial spatial) {
        this.spatial = spatial;
    }
    
    public Spatial getSpatial() {
        return spatial;
    }

    public Material getMaterial() {
        CollisionResults rMaterial = new CollisionResults();
        getSpatial().collideWith(getSpatial().getWorldBound(), rMaterial);
        rMaterial.toString();
        return rMaterial.getClosestCollision().getGeometry().getMaterial();
    }

    public float getTimer() {
        return timer;
    }

    public void setTimer(float timer) {
        this.timer = timer;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getOriginalHealth() {
        return originalHealth;
    }

    public boolean isDeath() {
        return death;
    }
    
    public void setDeath(boolean death) {
        this.death = death;
    }

    /**
     * @return the fDeathEnemy
     */
    public RigidBodyControl getfDeathEnemy() {
        return fDeathEnemy;
    }

    /**
     * @param fDeathEnemy the fDeathEnemy to set
     */
    public void setfDeathEnemy(RigidBodyControl fDeathEnemy) {
        this.fDeathEnemy = fDeathEnemy;
    }

    /**
     * @return the aspired
     */
    public boolean isAspired() {
        return aspired;
    }

    /**
     * @param aspired the aspired to set
     */
    public void setAspired(boolean aspired) {
        this.aspired = aspired;
    }

    /**
     * @return the hasBeenAspired
     */
    public boolean isHasBeenAspired() {
        return hasBeenAspired;
    }

    /**
     * @param hasBeenAspired the hasBeenAspired to set
     */
    public void setHasBeenAspired(boolean hasBeenAspired) {
        this.hasBeenAspired = hasBeenAspired;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

}
