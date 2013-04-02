package game;

import com.jme3.scene.Spatial;

public class Enemy {
    private Spatial spatial;
    
    public Enemy() {
        spatial = null;
    }
    
    public Enemy(Spatial s) {
        spatial = s;
    }

    public void setSpatial(Spatial spatial) {
        this.spatial = spatial;
    }
    
    public Spatial getSpatial() {
        return spatial;
    }
}
