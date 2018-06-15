package com.mygdx.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;

import static com.badlogic.gdx.graphics.Color.CYAN;

/**
 * Main class which creates the visual representation of the game and updates the game states in its render method
 * There are 3 implemented methods for moving the ball:
 * Method 1: Drag and point, a manual way of shooting the ball often used in mini golf video games
 * Method 2: Velocity input in GUI, using a small GUI the user can input the direction and speed of each swing
 * Method 3: Reading from a file, using the GolfswingInput.txt file velocity values are read and the ball moves autonomously
 */
public class Main extends ApplicationAdapter implements InputProcessor{

	private OrthographicCamera camera; //enables us to have a moveable viewpoint (operated by WASD keys)

	// initialisation of the ball
	private SpriteBatch batch; //a collection of image files
	private Texture golfballImg; //golf ball image file
	private static GolfBall golfBall;
	private static GolfBall golfBall2;//golf ball circle object to which the image file is attached
	private static GolfBall ghostBall;

	//initialisation of the hole
	private SpriteBatch goalBatch;
	private Texture goalImg;
	private static Circle goal;

	//initialisation of the water
	private SpriteBatch waterBatch;
	private Texture waterImg;
	private static Rectangle water;

	//ai
	private boolean aiGoing = false;
	private boolean visible = true;
	double aiTimer = 0;
	private double aiDirection;
	private float angleIncrease=0;
	private float speedIncrease=0;
	private boolean aiDone = false;
	static boolean  aiinputOpen = false;
	boolean oncePerShot=true;

	private PhysicsEngine p;
	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;
	private static boolean released;
	private SwingInput SI; //top left GUI in which swing directions and speed can be entered
	private AIInput AI;
	private TiledMapTileLayer collisionLayer;
	private boolean firstFrameOfSwing = true;

	private int numberOfSwings; //used for Method 3, giving the total number of swings entered in the GolswingInput.txt file
	private int currentSwing; //used for Method 3, giving the current swing number
	private float mouseX;
	private float mouseY;
	private float camXTracer;
	private float camYTracer;
	double eucliDistance;//absolute distance between the ball and the mouse

	//private FileInput FI; //instance of FileInput from which GolfswingInput and MapInput can be read
	private String[] mapInfo; //the course information read from MapInput.txt
	private ArrayList<Double> directionValues; //the GolfswingInput values
	private ArrayList<Double> speedValues; //the GolfswingInput values

	/**
	 * Method in which we create the initial game state, load the map, create the file readers and set the physics engine and input processor
	 */
	private ShapeRenderer sr;
	private WinFrame Win;

	@Override
	public void create () {
		/** create the sprites */
		batch = new SpriteBatch();
		golfballImg = new Texture("golfball3.png");
		goalBatch = new SpriteBatch();
		goalImg = new Texture("circle.png");
		waterBatch = new SpriteBatch();
		waterImg = new Texture("water.jpg");
		float w = Gdx.graphics.getWidth(); //the width of the screen
		float h = Gdx.graphics.getHeight(); //the height of the screen
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		camera.update();
		camXTracer = 0;
		camYTracer = 0;


		/** load the map */
		//tiledMap = new TmxMapLoader().load("map.tmx");
		tiledMap = new TmxMapLoader().load("map2.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap); //creates the background map (visual)



				water = new Rectangle(285, 75, 160, 80);
				goal = new Circle(450, 300, 30);
				aiinputOpen = true;



		golfBall = new GolfBall(0,0);

		ghostBall = new GolfBall(0,0);
		collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);




		Gdx.input.setInputProcessor(this); //setting the inputProccesor which allows the user to use the mouse and keyboard to control aspects of the program

		SI = new SwingInput(); //creating an instance of the GUI used in Method 2 to enter the velocity of the ball
		SI.createGUI();

		AI = new AIInput(); //creating an instance of the GUI used in Method 2 to enter the velocity of the ball
		AI.createGUI();

		


		p = new PhysicsEngine(golfBall,collisionLayer); //creating an instance of the physics engine


		FileInput FI = new FileInput(); //creating an instance of the file reader

		//uncomment to read from Map.Input.txt
       /* mapInfo = FI.readMapInfo(); //receiving information about the map (non-visual, physics related) and then setting these values in the physics engine
        p.setGravitationalForce(Double.parseDouble(mapInfo[0]));
        p.setFrictionConstant(Double.parseDouble(mapInfo[1]));
        p.setMaxSpeed(Double.parseDouble(mapInfo[2])); */

		FI.readSwingInfo(); //used for Method 3, receiving the swingInput information and then assigning these values
		directionValues = FI.getDirectionValues();
		speedValues = FI.getSpeedValues();
		numberOfSwings = directionValues.size();
		currentSwing = 0;

		Win = new WinFrame();
	}


	/**
	 * Method which updates the state of the game and is automatically called 30-60 times per second
	 * Used to visualize the current game state
	 */
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0.5f, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		batch.setProjectionMatrix(camera.combined);
		/** draw the sprite */
		goalBatch.begin();
		goalBatch.draw(goalImg,goal.x,goal.y);
		goalBatch.end();

		batch.begin();
		if(AI.getVisible()) {
			batch.draw(golfballImg, golfBall.x, golfBall.y); //draws the golfBall image at the location where the golfBall Circle object is at that point in time
		} //draws the golfBall image at the location where the golfBall Circle object is at that point in time
		batch.end();

		waterBatch.begin();
		waterBatch.draw(waterImg,water.x,water.y);
		waterBatch.end();

		if(aiinputOpen ==  true) {
			if (AI.getCourse1()) {
				water = new Rectangle(285, 75, 160, 80);
				goal = new Circle(450, 300, 30);
				if(oncePerShot) {
					golfBall.x = 40;
					golfBall.y = 32;
					oncePerShot=false;
				}
			} else if (AI.getCourse2()) {
				water = new Rectangle(200, 200, 160, 80);
				goal = new Circle(450, 100, 30);
				if(oncePerShot) {
					golfBall.x = 40;
					golfBall.y = 350;
					oncePerShot=false;
				}
			} else if (AI.getCourse3()) {
				water = new Rectangle(285, 75, 160, 80);
				goal = new Circle(50, 300, 30);
				if(oncePerShot) {
					golfBall.x = 450;
					golfBall.y = 32;
					oncePerShot=false;
				}
			}
		}
		if(aiGoing){aiinputOpen = false;}

		//Method 1 of moving the ball

		if(released)
		{
			p.moveBall(PhysicsEngine.calcAngle(mouseX-(golfBall.x + golfBall.radius), (mouseY)-(golfBall.y + golfBall.radius)), eucliDistance);
		}


		if (touchDragged(0,0,0) && p.getBallStopped())
		{
			if(firstFrameOfSwing){
				p.setBallBlockedX(false);
				p.setBallBlockedY(false);
			}
			firstFrameOfSwing = false;
			//leftKeyPressed = true;
			mouseX = Gdx.input.getX() + camXTracer;
			mouseY = Gdx.graphics.getHeight() - Gdx.input.getY() + camYTracer;
			sr = new ShapeRenderer();
			camera.update();
			sr.setProjectionMatrix(camera.combined);
			sr.begin(ShapeType.Line);
			eucliDistance = Math.sqrt(Math.pow((mouseX -(golfBall.x + golfBall.radius)), 2) + Math.pow((mouseY -(golfBall.y + golfBall.radius)), 2));
			float red = (float) (eucliDistance/1.5 - 50);
			float green = (float) (255 - eucliDistance/1.5);
			sr.setColor(red,green,0,0);
			//System.out.println("green: " + green + "red : " + red);
			sr.line(mouseX, mouseY, golfBall.x + golfBall.radius, golfBall.y + golfBall.radius);
			sr.end();
			System.out.println("x = " + (mouseX -(golfBall.x + golfBall.radius)) + " 	y =" + (mouseY -(golfBall.y + golfBall.radius)) + "		euclidistance = " + eucliDistance + "	angle = " + PhysicsEngine.calcAngle(mouseX-(golfBall.x + golfBall.radius), (mouseY)-(golfBall.y + golfBall.radius)));
		}


		//Method 2 of moving the ball
		if(SI.getButtonClicked())
		{
			if(firstFrameOfSwing){
				p.setBallBlockedX(false);
				p.setBallBlockedY(false);
			}
			firstFrameOfSwing = false;
			p.moveBall(SI.getDir(), SI.getSpd());
		}
		if(AI.getButtonClicked() || aiGoing)
		{
			aiGoing = true;
			if(firstFrameOfSwing){
				p.setBallBlockedX(false);
				p.setBallBlockedY(false);
			}

			firstFrameOfSwing = false;
			float ballX= golfBall.getXCoords();
			float ballY = golfBall.getYCoords();
			float goalX = goal.x;
			float goalY = goal.y;
			if(p.firstAICall){
				aiDirection = p.aiAngle(ballX,ballY,goalX,goalY);
			}
			p.moveBall(aiDirection+angleIncrease, speedIncrease);
			if(p.golfBall.getVx2() < 30 && p.golfBall.getVy2() < 30){

				golfBall.x = p.positionX();
				golfBall.y = p.positionY();
				p.golfBall.setVX2(0);
				p.golfBall.setVY2(0);

				speedIncrease +=100;

				if(speedIncrease == 2000){
					speedIncrease = 0;
					angleIncrease +=5;
				}
			}
			aiTimer++;
			System.out.println("timer:" + aiTimer/60);
		}


		//Method 3 of moving the ball, uncomment and comment Method 1 to use

        /*
        if(currentSwing < numberOfSwings) p.moveBall(directionValues.get(currentSwing),speedValues.get(currentSwing));
        if(p.getBallStopped()) currentSwing++;
        if(currentSwing == numberOfSwings) currentSwing = 0;
        */



		//We update these booleans if the ball is stopped so that we know if we can make another swing
		if( p.getBallStopped()){
			released = false;
			SI.setButtonClicked(false);
			firstFrameOfSwing = true;
		}

		if(goal.x - golfBall.x <= 10 && goal.x - golfBall.x >= -80 && goal.y - golfBall.y <= 10 && goal.y - golfBall.y >= -80){

			if(aiGoing && !aiDone){
				final double finalDir = aiDirection+angleIncrease;
				final double finalSpeed = speedIncrease;
				AI.setVisible();
				golfBall.x = p.positionX();
				golfBall.y = p.positionY();
				p.golfBall.setVX2(0);
				p.golfBall.setVY2(0);
				p.moveBall(finalDir, finalSpeed);
				aiGoing=false;

				aiDone = true;
			}
			else{
				System.out.println("congrats");
				Win.winGUI();
				golfBall.x = 80;
				golfBall.y = 80;
				aiGoing = false;
				AI.setButtonClicked(false);
				oncePerShot=true;
				System.out.println(aiTimer/60);
			}

		}



		/** check collision with the water and make the ball respawn */
		/**  !!!!!!!!!! need to make it pop at speed 0!!!*/
		if(water.x - golfBall.x <= 33 && water.x - golfBall.x >= -110 && water.y - golfBall.y <= 33 && water.y - golfBall.y >= -70){

			System.out.println(" u in water. game over");
			golfBall.x = p.positionX();
			golfBall.y = p.positionY();
			p.golfBall.setVX2(0);
			p.golfBall.setVY2(0);
		}


		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) golfBall.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) golfBall.x += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) golfBall.y += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) golfBall.y -= 200 * Gdx.graphics.getDeltaTime();
	}


	//Methods concerning the user controls
	public boolean touchUp(int x, int y, int pointer, int button) {
		if (button == Input.Buttons.LEFT) {
			released = true;
			return true;
		}
		return false;
	}
	@Override public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT))
			return true;
		else
			return false;
	}


	@Override public boolean keyDown(int keycode) {
		return false;
	}

	@Override public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.A) {
			camera.translate(-32, 0);
			goal.x += 32;
			water.x += 32;
			camXTracer -= 32;
		}
		if (keycode == Input.Keys.D) {
			camera.translate(32, 0);
			goal.x -= 32;
			water.x -= 32;
			camXTracer += 32;
		}
		if (keycode == Input.Keys.W){
			camera.translate(0, 32);
			goal.y -= 32;
			water.y -= 32;
			camYTracer += 32;
		}
		if(keycode == Input.Keys.S) {
			camera.translate(0, -32);
			goal.y += 32;
			water.y += 32;
			camYTracer -= 32;
		}
		if(keycode == Input.Keys.NUM_1)
			tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
		if(keycode == Input.Keys.NUM_2)
			tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
		return false;
	}


	@Override public boolean keyTyped(char character) {

		return false;
	}

	@Override public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override public boolean scrolled(int amount) {
		return false;
	}


	@Override
	public void dispose () {
		batch.dispose();
		golfballImg.dispose();
	}

}