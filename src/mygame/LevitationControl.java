
package mygame;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

public class LevitationControl extends AbstractControl{

    private float speed = 2f;
    private float topUp = 2f;
    private boolean up = true;
    private float displacement = (float) Math.random()*topUp*2-topUp;
    
    protected void controlUpdate(float tpf) {
        
        if (up){
            spatial.move(0f, tpf*speed/20, 0f);
            setDisplacement(getDisplacement() + tpf*speed);
            if (getDisplacement()>topUp){
                up=false;
                setDisplacement(0);
            }
        } else {
            spatial.move(0f, -tpf*speed/20, 0f);
            setDisplacement(getDisplacement() + tpf*speed);
            if (getDisplacement()>topUp){
                up=true;
                setDisplacement(0);
            }
        }
    }

    protected void controlRender(RenderManager rm, ViewPort vp) {        
    }

    public Control cloneForSpatial(Spatial spatial) {
        displacement = (float)Math.random()*4-2;
        Control control = new LevitationControl();
        control.setSpatial(spatial);
        return control;
    }

    /**
     * @return the SPEED
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * @param SPEED the SPEED to set
     */
    public void setSpeed(float Speed) {
        this.speed = Speed;
    }

    /**
     * @return the topUp
     */
    public float getTopUp() {
        return topUp;
    }

    /**
     * @param topUp the topUp to set
     */
    public void setTopUp(float topUp) {
        this.topUp = topUp;
    }

    /**
     * @return the displacement
     */
    public float getDisplacement() {
        return displacement;
    }

    /**
     * @param displacement the displacement to set
     */
    public void setDisplacement(float displacement) {
        this.displacement = displacement;
    }
    
}
