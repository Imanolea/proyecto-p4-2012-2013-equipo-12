
package game;

import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh.Type;
import com.jme3.math.Vector3f;


/**
 * Clase que gestiona los datos relacionados con los proyectiles en pantalla
 * @author Team 12
 */

public class Bullet extends ParticleEmitter{
    
    private boolean shooted; // El proyectil está siendo disparado o no
    private Vector3f direction; // Dirección del proyectil
    private float vel = 40f; // Velocidad de desplazamiento
    private float timer; // Timer relativo a su desaparición en pantalla en caso de salir fuera de los límites del escenario

    public Bullet(String emitter, Type type, int i) {
        super(emitter, type, i);
        shooted = false;
        direction = null;
        timer = 0;
    }

    public boolean isShooted() {
        return shooted;
    }

    public void setShooted(boolean shooted) {
        this.shooted = shooted;
    }
    
    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }
    
    public float getVel() {
        return vel;
    }

    public void setVel(float vel) {
        this.vel = vel;
    }

    public void move(float tpf) {
        move(direction.x*tpf*getVel(), direction.y*tpf*getVel(), direction.z*tpf*getVel());
    }

    /**
     * @return the timer
     */
    public float getTimer() {
        return timer;
    }

    /**
     * @param timer the timer to set
     */
    public void setTimer(float timer) {
        this.timer = timer;
    }

}