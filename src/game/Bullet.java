
package game;

import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh.Type;
import com.jme3.math.Vector3f;


class Bullet extends ParticleEmitter{
    
    private boolean shooted;
    private Vector3f direction;
    private float vel = 30f;
    
    public Bullet(){
        super();
        shooted = false;
        direction = null;
    }

    public Bullet(String emitter, Type type, int i) {
        super(emitter, type, i);
        shooted = false;
        direction = null;
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

    void move(float tpf) {
        move(direction.x*tpf*getVel(), direction.y*tpf*getVel(), direction.z*tpf*getVel());
    }

}