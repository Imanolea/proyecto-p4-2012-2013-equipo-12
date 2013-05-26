
package animations;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

/**
 * Clase que gestiona el movimiento de levitación enemigo
 * @author Team 12
 */

public class LevitationControl extends AbstractControl{

    private float speed = 2f; // Velocidad a la que levita
    private float topUp = 2f; // Rango de movimiento de la levitación
    private boolean up = true; // Dirección en la que levita
    private float displacement = (float) Math.random()*topUp*2-topUp; // Desplazamiento inicial en la levitación
    
    
    /**
     * Realiza el movimiento del modelo
     * @param tpf tiempo por frame
     */
    protected void controlUpdate(float tpf) {
        
        if (getSpeed()>2){
            setSpeed(getSpeed() - 200*tpf);
        }
        else{
            setTopUp(2);
            setSpeed(2);
        }
        
        if (up){
            spatial.move(0f, tpf*getSpeed()/20, 0f);
            setDisplacement(getDisplacement() + tpf*getSpeed());
            if (getDisplacement()>getTopUp()){
                up=false;
                setDisplacement(-getDisplacement());
            }
        } else {
            spatial.move(0f, -tpf*getSpeed()/20, 0f);
            setDisplacement(getDisplacement() + tpf*getSpeed());
            if (getDisplacement()>getTopUp()){
                up=true;
                setDisplacement(-getDisplacement());
            }
        }
    }

    protected void controlRender(RenderManager rm, ViewPort vp) {        
    }
    
    /**
     * Crea una instancia del control
     * @param spatial al que se aplica el control
     * @return el control
     */

    public Control cloneForSpatial(Spatial spatial) {
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
