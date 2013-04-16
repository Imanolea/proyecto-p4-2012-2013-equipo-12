package game;

import GUI.MainMenu;
import animations.LevitationControl;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResults;
import com.jme3.effect.ParticleMesh;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;


public class Main extends SimpleApplication
implements ActionListener {

	private final int NUMERO_ENEMIGOS = 20;
	private final int NUMERO_CARGAS = 5;

	private Spatial sceneModel;
	private Enemy[] pow = new Enemy[NUMERO_ENEMIGOS];
	private Bullet[] fire = new Bullet[NUMERO_CARGAS];
    private Material[] enemyMaterial = new Material[5];
	private BulletAppState bulletAppState;
	private RigidBodyControl landscape;
	private CharacterControl player;
    private Node shootables;
	private Vector3f walkDirection = new Vector3f();
	//private boolean camUpLimitsEstablished = false, camDownLimitsEstablished=false;
    //private float yUpLocation=0, yUpLeft=0, yUpUp=0, yUpDirection=0, yDownLocation=0, yDownLeft=0, yDownUp=0, yDownDirection=0;
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
        
        shootables = new Node("Shootables");
        rootNode.attachChild(shootables);

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
			pow[i] = new Enemy(assetManager.loadModel("Models/Pow/Pow.j3o")) {};
			pow[i].getSpatial().setLocalScale(0.8f, 0.8f, 0.8f);
            pow[i].getSpatial().setName(i+"-entity");
			do{
				pow[i].getSpatial().setLocalTranslation((float)Math.random()*56-28, (float)Math.random()*5+8, (float)Math.random()*56-28);
				rEnemigoEscenario = new CollisionResults();
				sceneModel.collideWith(pow[i].getSpatial().getWorldBound(), rEnemigoEscenario);
				rEnemigoEscenario.toString();
			}while (rEnemigoEscenario.size()>0);
			shootables.attachChild(pow[i].getSpatial());
		}
        
        Enemy powHurt = new Enemy (assetManager.loadModel("Models/Pow/PowHurt.j3o"));
        Enemy powEyesClosed = new Enemy (assetManager.loadModel("Models/Pow/PowEyesClosed.j3o"));
        Enemy powWeak = new Enemy (assetManager.loadModel("Models/Pow/PowWeak.j3o"));
        Enemy powDeath = new Enemy (assetManager.loadModel("Models/Pow/PowDeath.j3o"));
        
        enemyMaterial[0] = pow[0].getMaterial();
        enemyMaterial[1] = powHurt.getMaterial();
        enemyMaterial[2] = powEyesClosed.getMaterial();
        enemyMaterial[3] = powWeak.getMaterial();
        enemyMaterial[4] = powDeath.getMaterial();

		CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
		player = new CharacterControl(capsuleShape, 0.05f);
		player.setGravity(30);
		player.setPhysicsLocation(new Vector3f(0, 10, 0));

		initCrossHairs();

		rootNode.attachChild(sceneModel);
		bulletAppState.getPhysicsSpace().add(landscape);
		bulletAppState.getPhysicsSpace().add(player);
	}

	private void setUpKeys() {

		inputManager.addMapping("Shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
		inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
		inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
		inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
		inputManager.addListener(this, "Left");
		inputManager.addListener(this, "Right");
		inputManager.addListener(this, "Up");
		inputManager.addListener(this, "Down");
		inputManager.addListener(this, "Shoot");
	}

	private Bullet createFire() {

		Bullet f = 
				new Bullet("Emitter", ParticleMesh.Type.Triangle, 30);
		Material mat_red = new Material(assetManager, 
				"Common/MatDefs/Misc/Particle.j3md");
		mat_red.setTexture("Texture", assetManager.loadTexture(
				"Effects/Explosion/flame.png"));
		f.setMaterial(mat_red);
		f.setImagesX(2); 
		f.setImagesY(2);
		f.setEndColor(new ColorRGBA(1f, 1f, 0f, 1f));
		f.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f));
		f.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 3, 0));
		f.setStartSize(2f);
		f.setEndSize(0.1f);
		f.setGravity(0, 0, 0);
		f.setLowLife(0.2f);
		f.setHighLife(0.5f);
		f.getParticleInfluencer().setVelocityVariation(0.3f);
		return f;
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

	protected void initCrossHairs() {

		guiNode.detachAllChildren();
		guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
		BitmapText ch = new BitmapText(guiFont, false);
		ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
		ch.setText("+");
		ch.setLocalTranslation(
				settings.getWidth() / 2 - guiFont.getCharSet().getRenderedSize() / 3 * 2,
				settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
		guiNode.attachChild(ch);
	}

	public void shootFire() {

		for (int i=0; i<fire.length; i++)
			if (!fire[i].isShooted()){
                rootNode.attachChild(fire[i]);
                fire[i].setShooted(true);
                fire[i].setParticlesPerSec(20);
                fire[i].setDirection(cam.getDirection());
                fire[i].setLocalTranslation(cam.getLocation());
				i=fire.length;
			}
	}
    
    public Spatial getGeometrySpatial(Geometry geometry){
        Spatial s = geometry.getParent();
        while (s != null){
            if (s.getName().endsWith("-entity"))
                return s;
            s = s.getParent();
        }
        return null;
    }
    
    
    private void death(Spatial s) {
        s.setMaterial(enemyMaterial[4]);
        Spatial deathEnemy = s;
        rootNode.attachChild(deathEnemy);
        deathEnemy.setLocalTranslation(s.getLocalTranslation());
        RigidBodyControl fDeathEnemy = new RigidBodyControl(1f);
        deathEnemy.addControl(fDeathEnemy);
        bulletAppState.getPhysicsSpace().add(fDeathEnemy);
    }

	public void onAction(String name, boolean isPressed, float tpf) {
            
		if (name.equals("Shoot") && !isPressed) {
			shootFire();
		}
		if (name.equals("Left")) {
			if (isPressed) { left = true; } else { left = false; }
		} else if (name.equals("Right")) {
			if (isPressed) { right = true; } else { right = false; }
		}
		if (name.equals("Up")) {
			if (isPressed) { up = true; } else { up = false; }
		} else if (name.equals("Down")) {
			if (isPressed) { down = true; } else { down = false; }
		} 
	}

	public void simpleUpdate(float tpf) { 
            
		Vector3f camLeft = cam.getLeft().clone().multLocal(0.4f);
        Vector3f camForward = cam.getDirection().clone().multLocal(0.6f);
        camForward.y = 0;
		walkDirection.set(0, 0, 0);
        
		if (left)  { walkDirection.addLocal(camLeft); }
		if (right) { walkDirection.addLocal(camLeft.negate()); }
		if (up)    { walkDirection.addLocal(camForward); }
		if (down)  { walkDirection.addLocal(camForward.negate()); }
		player.setWalkDirection(walkDirection);
		cam.setLocation(player.getPhysicsLocation());
        
        if (cam.getDirection().y>0.99){
            
            /*if (!camUpLimitsEstablished){
                yUpLocation= cam.getLocation().y;
                yUpLeft= cam.getLeft().y;
                yUpUp= cam.getUp().y;
                yUpDirection= cam.getDirection().y;
                camUpLimitsEstablished=true;
                System.out.println(yUpLocation);
                System.out.println(yUpLeft);
                System.out.println(yUpUp);
                System.out.println(yUpDirection);
            }*/
            
            cam.setFrame(new Vector3f(cam.getLocation().x, 4.6516128f,cam.getLocation().z), new Vector3f(cam.getLeft().x, -2.4214387E-8f,cam.getLeft().z), new Vector3f(cam.getUp().x, 0.13870794f,cam.getUp().z), new Vector3f(cam.getDirection().x, 0.99033326f,cam.getDirection().z));
        }
        else if (cam.getDirection().y<-0.99){
            
            /*if (!camDownLimitsEstablished){
                yDownLocation= cam.getLocation().y;
                yDownLeft= cam.getLeft().y;
                yDownUp= cam.getUp().y;
                yDownDirection= cam.getDirection().y;
                camDownLimitsEstablished=true;
                System.out.println(yDownLocation);
                System.out.println(yDownLeft);
                System.out.println(yDownUp);
                System.out.println(yDownDirection);
            }*/
            
            cam.setFrame(new Vector3f(cam.getLocation().x, 4.652709f,cam.getLocation().z), new Vector3f(cam.getLeft().x, -3.501773E-7f,cam.getLeft().z), new Vector3f(cam.getUp().x, 0.13870472f,cam.getUp().z), new Vector3f(cam.getDirection().x, -0.9903338f,cam.getDirection().z));
        } 
            
            
		for (int i=0; i < pow.length; i++){
            if (!pow[i].isDeath()){
			    pow[i].getSpatial().lookAt(player.getPhysicsLocation().clone(), Vector3f.UNIT_Y);
            
                if (pow[i].getSpatial().getControl(LevitationControl.class).getSpeed()>2){
                    pow[i].getSpatial().setMaterial(enemyMaterial[1]);   
                } else {
                    pow[i].setTimer(pow[i].getTimer()+tpf);
                    if (pow[i].getTimer()>7){
                        pow[i].getSpatial().setMaterial(enemyMaterial[2]);
                        if (pow[i].getTimer()>7.1)
                            pow[i].setTimer(1);
                    } else {
                        if (pow[i].getHealth()>pow[i].getOriginalHealth()/2)
                            pow[i].getSpatial().setMaterial(enemyMaterial[0]);
                        else
                            pow[i].getSpatial().setMaterial(enemyMaterial[3]);
                    }
                    
                }
            }
        }
        
        for (int i=0; i < fire.length; i++){  
            if (fire[i].isShooted()){
                fire[i].move(tpf);
                Box ball = new Box(1f, 1f, 1f);
                Geometry theBall = new Geometry("Ball", ball);
                theBall.move(fire[i].getWorldTranslation().x, fire[i].getWorldTranslation().y, fire[i].getWorldTranslation().z);
                CollisionResults rScene = new CollisionResults();
                sceneModel.collideWith(theBall.getWorldBound(), rScene);
                rScene.toString();
                CollisionResults rEnemy = new CollisionResults();
                shootables.collideWith(theBall.getWorldBound(), rEnemy);
                rEnemy.toString();
                if (rScene.size()>0){
                    fire[i].setParticlesPerSec(0f);
                    fire[i].setShooted(false); 
                }
                if (rEnemy.size()>0){
                    fire[i].setParticlesPerSec(0f);
                    fire[i].setShooted(false);
                    String[] words = getGeometrySpatial(rEnemy.getClosestCollision().getGeometry()).getName().split ("-");
                    pow[Integer.parseInt(words[0])].setHealth(pow[Integer.parseInt(words[0])].getHealth()-1);
                    if (pow[Integer.parseInt(words[0])].getHealth()==0){
                        getGeometrySpatial(rEnemy.getClosestCollision().getGeometry()).removeFromParent(); 
                        death(getGeometrySpatial(rEnemy.getClosestCollision().getGeometry()));
                        pow[Integer.parseInt(words[0])].setDeath(true);
                    }
                    getGeometrySpatial(rEnemy.getClosestCollision().getGeometry()).getControl(LevitationControl.class).setSpeed(100);
                    getGeometrySpatial(rEnemy.getClosestCollision().getGeometry()).getControl(LevitationControl.class).setTopUp(4);
                }
            }
        }
    }
}



