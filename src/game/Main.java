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
import com.jme3.math.Ray;
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
    private Geometry cleanerShape;
	private Enemy[] pow = new Enemy[NUMERO_ENEMIGOS];
	private Bullet[] fire = new Bullet[NUMERO_CARGAS];
    private Material[] enemyMaterial = new Material[5];
	private BulletAppState bulletAppState;
	private RigidBodyControl landscape;
	private CharacterControl player;
    private Ray cleanerRay;
    private Node shootables;
    private Node inhalables;
	private Vector3f walkDirection = new Vector3f();
    private int enemiesCleaned;
    private boolean left = false, right = false, up = false, down = false, aspire = false;

	public static void main(String[] args) {   
        Main app = new Main();
        app.start();                
	}

	public void simpleInitApp() {

		setDisplayFps(false);
		setDisplayStatView(false);
        
        enemiesCleaned = 0;
        
        Box b = new Box(1f, 1f, 1f);
        cleanerShape = new Geometry("Cleaner Shape", b);
        
		bulletAppState = new BulletAppState();
		stateManager.attach(bulletAppState);
        
        cleanerRay = new Ray(cam.getLocation(), cam.getDirection());
        
        shootables = new Node("Shootables");
        rootNode.attachChild(shootables);
        
        inhalables = new Node("Inhalables");
        rootNode.attachChild(inhalables);

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

		CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1, 6f, 1);
		player = new CharacterControl(capsuleShape, 0.05f);
		player.setGravity(30);
		player.setPhysicsLocation(new Vector3f(0, 10, 0));

		initCrossHairs();

		rootNode.attachChild(sceneModel);
		bulletAppState.getPhysicsSpace().add(landscape);
		bulletAppState.getPhysicsSpace().add(player);
	}

	private void setUpKeys() {

		inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
		inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
		inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
		inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping("Aspire", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
		inputManager.addListener(this, "Left");
		inputManager.addListener(this, "Right");
		inputManager.addListener(this, "Up");
		inputManager.addListener(this, "Down");
		inputManager.addListener(this, "Shoot");
        inputManager.addListener(this, "Aspire");
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
        BitmapText ch2 = new BitmapText(guiFont, false);
		ch2.setColor(ColorRGBA.White);
		ch2.setSize(guiFont.getCharSet().getRenderedSize()*0.000004f*settings.getWidth()*settings.getHeight());
		ch2.setText("Enemigos aspirados: "+enemiesCleaned);
		ch2.setLocalTranslation(
				settings.getWidth() / 25,
				settings.getHeight() / 10, 0);
		guiNode.attachChild(ch2);
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
    
    
    private void death(Spatial s, int index) {
        s.setMaterial(enemyMaterial[4]);
        s.addControl(pow[index].getfDeathEnemy());
        inhalables.attachChild(s);
        bulletAppState.getPhysicsSpace().add(pow[index].getfDeathEnemy());
    }
    
    private void aspire() {
       CollisionResults rCleanerEnemy = new CollisionResults();
       inhalables.collideWith(cleanerRay, rCleanerEnemy); 
       rCleanerEnemy.toString();
       if (rCleanerEnemy.size()>0){
           String[] words = getGeometrySpatial(rCleanerEnemy.getClosestCollision().getGeometry()).getName().split ("-");
           pow[Integer.parseInt(words[0])].setAspired(true);
           pow[Integer.parseInt(words[0])].setHasBeenAspired(true);
           pow[Integer.parseInt(words[0])].getfDeathEnemy().setLinearVelocity(cam.getDirection().mult(25f).negate());
       }
       else
           for (int i = 0; i<pow.length; i++)
            pow[i].setAspired(false);
    }
    
    public void notAspire() {
        for (int i = 0; i<pow.length; i++)
            pow[i].setAspired(false);
    }

	public void onAction(String name, boolean isPressed, float tpf) {
            
		if (name.equals("Shoot") && !isPressed) {
			shootFire();
		}
        if (name.equals("Aspire")) {
            if (isPressed) { aspire = true; } else { aspire = false; }
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
        
        initCrossHairs();
        
        cleanerRay.setOrigin(cam.getLocation());
        cleanerRay.setDirection(cam.getDirection());
        
		Vector3f camLeft = cam.getLeft().clone().multLocal(0.4f);
        Vector3f camForward = cam.getDirection().clone().multLocal(0.6f);
        camForward.y = 0;
		walkDirection.set(0, 0, 0);
        
		if (left)  { walkDirection.addLocal(camLeft); }
		if (right) { walkDirection.addLocal(camLeft.negate()); }
		if (up)    { walkDirection.addLocal(camForward); }
		if (down)  { walkDirection.addLocal(camForward.negate()); }
        if (aspire) {aspire();} else {notAspire();}
		player.setWalkDirection(walkDirection);
		cam.setLocation(player.getPhysicsLocation());
        cleanerShape.setLocalTranslation(player.getPhysicsLocation());
        
        if (cam.getDirection().y>0.99033326)    
            cam.setFrame(new Vector3f(cam.getLocation().x, 4.6516128f,cam.getLocation().z), new Vector3f(cam.getLeft().x, -2.4214387E-8f,cam.getLeft().z), new Vector3f(cam.getUp().x, 0.13870794f,cam.getUp().z), new Vector3f(cam.getDirection().x, 0.99033326f,cam.getDirection().z));
        else if (cam.getDirection().y<-0.9903338)         
            cam.setFrame(new Vector3f(cam.getLocation().x, 4.652709f,cam.getLocation().z), new Vector3f(cam.getLeft().x, -3.501773E-7f,cam.getLeft().z), new Vector3f(cam.getUp().x, 0.13870472f,cam.getUp().z), new Vector3f(cam.getDirection().x, -0.9903338f,cam.getDirection().z));
            
            
		for (int i=0; i < pow.length; i++){
            Vector3f dir = pow[i].getSpatial().getLocalTranslation().subtract(player.getPhysicsLocation()).normalize();
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
            else{
                CollisionResults rEnemyCleaner = new CollisionResults();
                inhalables.collideWith(cleanerShape.getWorldBound(), rEnemyCleaner);
                rEnemyCleaner.toString();
                if (rEnemyCleaner.size()>0){
                    String[] words = getGeometrySpatial(rEnemyCleaner.getClosestCollision().getGeometry()).getName().split ("-");
                    if (pow[Integer.parseInt(words[0])].isHasBeenAspired()){
                        getGeometrySpatial(rEnemyCleaner.getClosestCollision().getGeometry()).removeFromParent(); 
                        enemiesCleaned++;
                    }
                }
                if (pow[i].isAspired())
                    pow[i].getfDeathEnemy().setGravity(new Vector3f(0,0f,0));
                else
                    pow[i].getfDeathEnemy().setGravity(new Vector3f(0,-9.81f,0));
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
                    getGeometrySpatial(rEnemy.getClosestCollision().getGeometry()).getControl(LevitationControl.class).setSpeed(100);
                    getGeometrySpatial(rEnemy.getClosestCollision().getGeometry()).getControl(LevitationControl.class).setTopUp(4);
                    if (pow[Integer.parseInt(words[0])].getHealth()==0){
                        getGeometrySpatial(rEnemy.getClosestCollision().getGeometry()).removeFromParent(); 
                        death(getGeometrySpatial(rEnemy.getClosestCollision().getGeometry()), Integer.parseInt(words[0]));
                        pow[Integer.parseInt(words[0])].getSpatial().removeControl(LevitationControl.class);
                        pow[Integer.parseInt(words[0])].setDeath(true);
                    }
                }
            }
        }
    }
}



