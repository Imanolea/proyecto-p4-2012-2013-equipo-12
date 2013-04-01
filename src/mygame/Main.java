package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResults;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Main extends SimpleApplication
        implements ActionListener {

  private final int NUMERO_ENEMIGOS = 20;  
  private Spatial sceneModel;
  private Spatial[] pow = new Spatial[NUMERO_ENEMIGOS];
  private ParticleEmitter[] fire = new ParticleEmitter[5];
  private BulletAppState bulletAppState;
  private RigidBodyControl landscape;
  private CharacterControl player;
  private PointLight lamp_light;
  private Vector3f walkDirection = new Vector3f();
  private boolean left = false, right = false, up = false, down = false;

  public static void main(String[] args) {
    Main app = new Main();
    app.start();
  }

  public void simpleInitApp() {
      
    setDisplayFps(false);
    setDisplayStatView(false);
      
    bulletAppState = new BulletAppState();
    stateManager.attach(bulletAppState);
    
    viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
    flyCam.setMoveSpeed(100);
    setUpKeys();
    setUpLight();
    
    for (int i=0; i<fire.length; i++)
        fire[i] = createFire();

    sceneModel = assetManager.loadModel("Scenes/Escenario/Escenario.j3o");
    sceneModel.setLocalScale(6f);

    CollisionShape sceneShape =
            CollisionShapeFactory.createMeshShape((Node) sceneModel);
    landscape = new RigidBodyControl(sceneShape, 0);
    sceneModel.addControl(landscape);
    
    CollisionResults rEnemigoEscenario;
    
    for (int i=0; i<pow.length; i++){
        pow[i] = assetManager.loadModel("Models/Bicho/Bicho.j3o");
        pow[i].setLocalScale(0.8f, 0.8f, 0.8f);
        do{
            pow[i].setLocalTranslation((float)Math.random()*56-28, (float)Math.random()*5+8, (float)Math.random()*56-28);
            rEnemigoEscenario = new CollisionResults();
            sceneModel.collideWith(pow[i].getWorldBound(), rEnemigoEscenario);
            rEnemigoEscenario.toString();
        }while (rEnemigoEscenario.size()>0);
        rootNode.attachChild(pow[i]);
    }

    CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
    player = new CharacterControl(capsuleShape, 0.05f);
    player.setGravity(30);
    player.setPhysicsLocation(new Vector3f(0, 10, 0));
    
    initCrossHairs();

    rootNode.attachChild(sceneModel);
    bulletAppState.getPhysicsSpace().add(landscape);
    bulletAppState.getPhysicsSpace().add(player);
  }
  
  private ParticleEmitter createFire() {
   
    ParticleEmitter fire = 
            new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
    Material mat_red = new Material(assetManager, 
            "Common/MatDefs/Misc/Particle.j3md");

    fire.setMaterial(mat_red);
    fire.setImagesX(2); 
    fire.setImagesY(2); // 2x2 texture animation
    fire.setEndColor(new ColorRGBA(1f, 1f, 0f, 1f));   // red
    fire.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
    fire.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 3, 0));
    fire.setStartSize(1f);
    fire.setEndSize(0.1f);
    fire.setGravity(0, 0, 0);
    fire.setLowLife(1f);
    fire.setHighLife(2f);
    fire.getParticleInfluencer().setVelocityVariation(0.3f);
    return fire;
  }

  private void setUpLight() {
      
      DirectionalLight l1 = new DirectionalLight();
      l1.setColor(ColorRGBA.White);
      l1.setDirection(new Vector3f(-5f,0,0).normalizeLocal());
      rootNode.addLight(l1);
      
      DirectionalLight l2 = new DirectionalLight();
      l2.setColor(ColorRGBA.White);
      l2.setDirection(new Vector3f(5f,0,0).normalizeLocal());
      rootNode.addLight(l2);
      
      DirectionalLight l3 = new DirectionalLight();
      l3.setColor(ColorRGBA.White);
      l3.setDirection(new Vector3f(0,0,-5f).normalizeLocal());
      rootNode.addLight(l3);
      
      DirectionalLight l4 = new DirectionalLight();
      l4.setColor(ColorRGBA.White);
      l4.setDirection(new Vector3f(0,0,5f).normalizeLocal());
      rootNode.addLight(l4);
      
      DirectionalLight l5 = new DirectionalLight();
      l5.setColor(ColorRGBA.White);
      l5.setDirection(new Vector3f(0,-5f,0).normalizeLocal());
      rootNode.addLight(l5);
      
      DirectionalLight l6 = new DirectionalLight();
      l6.setColor(ColorRGBA.White);
      l6.setDirection(new Vector3f(0,5f,0).normalizeLocal());
      rootNode.addLight(l6);
      
  }

  private void setUpKeys() {
      
    inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
    inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
    inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
    inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));;
    inputManager.addListener(this, "Left");
    inputManager.addListener(this, "Right");
    inputManager.addListener(this, "Up");
    inputManager.addListener(this, "Down");
  }

  public void onAction(String binding, boolean value, float tpf) {
    if (binding.equals("Left")) {
      if (value) { left = true; } else { left = false; }
    } else if (binding.equals("Right")) {
      if (value) { right = true; } else { right = false; }
    } else if (binding.equals("Up")) {
      if (value) { up = true; } else { up = false; }
    } else if (binding.equals("Down")) {
      if (value) { down = true; } else { down = false; }
    } 
  }
  
  protected void initCrossHairs() {
    guiNode.detachAllChildren();
    guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
    BitmapText ch = new BitmapText(guiFont, false);
    ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
    ch.setText("+");        // fake crosshairs :)
    ch.setLocalTranslation( // center
      settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
      settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
    guiNode.attachChild(ch);
  }

  
  public void simpleUpdate(float tpf) {
    Vector3f camDir = cam.getDirection().clone().multLocal(0.6f);
    Vector3f camLeft = cam.getLeft().clone().multLocal(0.4f);
    walkDirection.set(0, 0, 0);
    if (left)  { walkDirection.addLocal(camLeft); }
    if (right) { walkDirection.addLocal(camLeft.negate()); }
    if (up)    { walkDirection.addLocal(camDir); }
    if (down)  { walkDirection.addLocal(camDir.negate()); }
    player.setWalkDirection(walkDirection);
    cam.setLocation(player.getPhysicsLocation());
    
    for (int i=0; i < pow.length; i++)
        pow[i].lookAt(player.getPhysicsLocation().clone(), Vector3f.UNIT_Y);
  }
}