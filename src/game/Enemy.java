package game;

import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.scene.Spatial;

public class Enemy {
    private Spatial spatial;
    private float timer = (float) Math.random()*4 +1;
    private final int originalHealth = 2;
    private int health;
    private boolean death;
    
    public Enemy() {
        spatial = null;
        health = originalHealth;
        death = false;
    }
    
    public Enemy(Spatial s) {
        spatial = s;
        health = originalHealth;
        death = false;
    }

    public void setSpatial(Spatial spatial) {
        this.spatial = spatial;
    }
    
    public Spatial getSpatial() {
        return spatial;
    }

    Material getMaterial() {
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

}
