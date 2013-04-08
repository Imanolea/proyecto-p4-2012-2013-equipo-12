package game;

import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.scene.Spatial;

public class Enemy {
    private Spatial spatial;
    private float timer = (float) Math.random()*4 +1;
    
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

}
