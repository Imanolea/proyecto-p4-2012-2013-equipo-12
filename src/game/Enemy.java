package game;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Clase que gestiona los datos relacionados con los enemigos
 * @author Team 12
 */

public class Enemy {
    private Spatial spatial; // Modelo cargado del enemigo
    private float timer = (float) Math.random()*4 +1; // Temporizador que controla el parpadeo
    private final int originalHealth = 2; // Salud original del enemigo
    private RigidBodyControl fDeathEnemy; // Cuerpo físico asignado al enemigo
    private int health; // Salud del enemigo
    private boolean death; // El enemigo está muerto o no
    private boolean active; // El enemigo está desarrollado (en relación sl spawn) o no
    private boolean aspired; // El enemigo está siendo aspirado o no
    private boolean hasBeenAspired; // El enemigo ha sido aspirado desde que murió o no
    private Vector3f direction; // Dirección del enemigo
    private float variant; // Variante que provoca la desviación de movimiento del enemigo
    private float speed; // Velocidad a la que se mueve le enemigo
    
    public Enemy(Spatial s) {
        spatial = s;
        health = originalHealth;
        death = false;
        fDeathEnemy = new RigidBodyControl(1f);
        hasBeenAspired = false;
        active = true;
        direction = new Vector3f((float)Math.random()*2-1,0,(float)Math.random()*2-1);
        speed = (float)Math.random()*3+10;
        variant = (float)Math.random()*0.02f-0.01f;
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

    /**
     * @return the direction
     */
    public Vector3f getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    /**
     * @return the speed
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * @return the variant
     */
    public float getVariant() {
        return variant;
    }

    /**
     * @param variant the variant to set
     */
    public void setVariant(float variant) {
        this.variant = variant;
    }

}
