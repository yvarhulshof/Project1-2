package com.mygdx.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private static GolfBall[] golfBalls;


	//initialisation of the hole
	//private GolfBall golfBall;
    private SpriteBatch goalBatch;
    private Texture goalImg;
    private static Circle goal;


    //initialisation of the water
    private SpriteBatch waterBatch;
    private Texture waterImg;
    private static Rectangle water;


//    private PhysicsEngine p2;
    private PhysicsEngine[] p;
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private static boolean released;
    private SwingInput SI; //top left GUI in which swing directions and speed can be entered

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

	private BicubicInterpolation interpolator;
    private double[][] hs;

    private int playersNbr = 1;
    private int cpp; //current playing player
    int maxDistanceMulti;
    private int[] scores;

    private boolean elasticBand = true;

	private AIInput AI;
	private boolean aiGoing = false;
	private boolean visible = true;
	double aiTimer = 0;
	private double aiDirection;
	private float angleIncrease=0;
	private float speedIncrease=0;
	private boolean aiDone = false;
	static boolean  aiinputOpen = false;
	boolean oncePerShot=true;


	private ArrayList<ArrayList<Float>> midpoints;

	private int shotIndex;
	private int previousShotIndex;
	private float goalX;
	private float goalY;
	DijkstraMain DM;
	boolean midpointFound;






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


		water = new Rectangle(285, 175, 160, 80);
        goal = new Circle(450, 330, 30);


		aiinputOpen = true;

        collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);

		Gdx.input.setInputProcessor(this); //setting the inputProccesor which allows the user to use the mouse and keyboard to control aspects of the program

		SI = new SwingInput(); //creating an instance of the GUI used in Method 2 to enter the velocity of the ball
		SI.createGUI();

		AI = new AIInput(); //creating an instance of the GUI used in Method 2 to enter the velocity of the ball
		AI.createGUI();




		golfBalls = new GolfBall[playersNbr];
		p = new PhysicsEngine[playersNbr];
		scores = new int[playersNbr];

        cpp = 0;

			for (int i = 0; i < playersNbr; i++) {
				golfBalls[i] = new GolfBall(0, 0);
				p[i] = new PhysicsEngine(golfBalls[i], collisionLayer);
				scores[i] = 0;
			}


        maxDistanceMulti = 350;





        /**
         * Used for bicubic spline interpolation, but we're using normal splines for now
         */

        /*

        /*hs =    new double[][]
                {
                        {0,0,0,0},
                        {0,0,0,0},
                        {0,0,0,0},
                        {0,0,0,0},
                };



        interpolator = new BicubicInterpolation(hs);

        p1 = new PhysicsEngine(golfBall,collisionLayer,interpolator);

        */


        FileInput FI = new FileInput(); //creating an instance of the file reader

        //uncomment to read from Map.Input.txt
       /* mapInfo = FI.readMapInfo(); //receiving information about the map (non-visual, physics related) and then setting these values in the physics engine
        p1.setGravitationalForce(Double.parseDouble(mapInfo[0]));
        p1.setFrictionConstant(Double.parseDouble(mapInfo[1]));
        p1.setMaxSpeed(Double.parseDouble(mapInfo[2])); */

        FI.readSwingInfo(); //used for Method 3, receiving the swingInput information and then assigning these values
        directionValues = FI.getDirectionValues();
        speedValues = FI.getSpeedValues();
        numberOfSwings = directionValues.size();
        currentSwing = 0;

        Win = new WinFrame();

        /*
		List<Vertex> vertices = new ArrayList<Vertex>();
		List<Edge> edges = new ArrayList<Edge>();

		Vertex v1 = new Vertex("A",golfBalls[cpp].x,golfBalls[cpp].y);
		Vertex v2 = new Vertex("B",50,60);
		Vertex v3 = new Vertex("C", 60,100);
		Vertex v4 = new Vertex("D", 120,100);
		Vertex v5 = new Vertex("E", 300,250);
		Vertex v6 = new Vertex("F", 350,200);
		Vertex v7 = new Vertex("G", 400,40);
		Vertex v8 = new Vertex("H", 300,120);
		Vertex v9 = new Vertex("I", 250,300);
		Vertex v10 = new Vertex("J", goal.x,goal.y);

		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);
		vertices.add(v4);
		vertices.add(v5);
		vertices.add(v6);
		vertices.add(v7);
		vertices.add(v8);
		vertices.add(v9);
		vertices.add(v10);


		vertices2 = new ArrayList<Vertex>();
		for(int i = 0; i < vertices.size();i++){
			vertices2.add(vertices.get(i));
		}

		//weight for compromised channels is 1, otherwise weight is 0
		Edge e1A = new Edge("E01", v1 , v2, 1);
		Edge e1B = new Edge("E01", v2 , v1, 1);

		Edge e2A = new Edge("E02", v1 , v3, 1);
		Edge e2B = new Edge("E02", v3 , v1, 1);

		Edge e3A = new Edge("E03", v1 , v4, 0);
		Edge e3B = new Edge("E03", v4 , v1, 0);

		Edge e4A = new Edge("E04", v2 , v3, 1);
		Edge e4B = new Edge("E04", v3 , v2, 1);

		Edge e5A = new Edge("E05", v4 , v3, 0);
		Edge e5B = new Edge("E05", v3 , v4, 0);

		Edge e6A = new Edge("E06", v3 , v6, 1);
		Edge e6B = new Edge("E06", v6 , v3, 1);

		Edge e7A = new Edge("E07", v3 , v5, 1);
		Edge e7B = new Edge("E07", v5 , v3, 1);

		Edge e8A = new Edge("E08", v5 , v7, 1);
		Edge e8B = new Edge("E08", v7 , v5, 1);

		Edge e9A = new Edge("E09", v6 , v8, 0);
		Edge e9B = new Edge("E09", v8 , v6, 0);

		Edge e10A = new Edge("E10", v7 , v8, 0);
		Edge e10B = new Edge("E10", v8 , v7, 0);

		Edge e11A = new Edge("E11", v9 , v10, 0);
		Edge e11B = new Edge("E11", v10 , v9, 0);

		edges.addAll(Arrays.asList(e1A,e2A,e3A,e4A,e1B,e2B,e3B,e4B,e5A,e5B,e6A,e6B,e7A,e7B,e8A,e8B,e9A,e9B,e10A,e10B,e11A,e11B));
		source = v1;
		destination = v4;
		*/
		 //g = new Graph(vertices,edges);

		 //gOP = new GraphOptimalPath();
		//gOP.updateDistances(g, source);
		//result = gOP.findOptimalPath(g,source,destination);

	//	System.out.println("The optimal path is: " + result);




		DM = new DijkstraMain();
		midpoints = DM.findOptimalPath();


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
		if (AI.getVisible()) {
			for (int i = 0; i < playersNbr; i++) {
				batch.draw(golfballImg, golfBalls[i].x, golfBalls[i].y); //draws the golfBall image at the location where the golfBall Circle object is at that point in time
			} //draws the golfBall image at the location where the golfBall Circle object is at that point in time
		}
		else {
			for (int i = 0; i < playersNbr; i++) {
				batch.draw(golfballImg, golfBalls[i].x, golfBalls[i].y);
			}
		}

		/*if (AI.getVisible()) {
			batch.draw(golfballImg, golfBall.x, golfBall.y); //draws the golfBall image at the location where the golfBall Circle object is at that point in time
		} //draws the golfBall image at the location where the golfBall Circle object is at that point in time
//		batch.draw(golfballImg, golfBall.x,golfBall.y); //draws the golfBall image at the location where the golfBall Circle object is at that point in time
//        batch.draw(golfballImg, golfBall2.x,golfBall2.y); */
		batch.end();

		waterBatch.begin();
		waterBatch.draw(waterImg,water.x,water.y);
		waterBatch.end();


		// selecting the different variation of the map
		if (aiinputOpen) {
			if (AI.getCourse1()) {
				water = new Rectangle(285, 75, 160, 80);
				goal = new Circle(450, 300, 30);
				if (oncePerShot) {
					golfBalls[cpp].x = 40;
					golfBalls[cpp].y = 32;
					oncePerShot = false;

				}
			} else if (AI.getCourse2()) {
				water = new Rectangle(200, 200, 160, 80);
				goal = new Circle(450, 100, 30);
				if (oncePerShot) {
					golfBalls[cpp].x = 40;
					golfBalls[cpp].y = 350;
					oncePerShot = false;
					midpoints = DM.findOptimalPath();
				}
			} else if (AI.getCourse3()) {
				water = new Rectangle(285, 75, 160, 80);
				goal = new Circle(50, 300, 30);
				if (oncePerShot) {
					golfBalls[cpp].x = 450;
					golfBalls[cpp].y = 32;
					oncePerShot = false;
					midpoints = DM.findOptimalPath();
				}
			}
		}


		if (aiGoing) {
			aiinputOpen = false;
		}
		//Method 1 of moving the ball

        if(released)
		{
            p[cpp].moveBall(PhysicsEngine.calcAngle(mouseX-(golfBalls[cpp].x + golfBalls[cpp].radius), mouseY-(golfBalls[cpp].y + golfBalls[cpp].radius)), eucliDistance);
            //p[1].moveBall(PhysicsEngine.calcAngle(mouseX-(golfBalls[1].x + golfBalls[1].radius), mouseY-(golfBalls[1].y + golfBalls[1].radius)), eucliDistance);

        }


		if (touchDragged(0,0,0) && p[cpp].getBallStopped())
		{
            if(firstFrameOfSwing){
                p[cpp].setBallBlockedX(false);
                p[cpp].setBallBlockedY(false);
            }
            firstFrameOfSwing = false;
			//leftKeyPressed = true;
			mouseX = Gdx.input.getX() + camXTracer;
			mouseY = Gdx.graphics.getHeight() - Gdx.input.getY() + camYTracer;
			sr = new ShapeRenderer();
			camera.update();
			sr.setProjectionMatrix(camera.combined);
			sr.begin(ShapeType.Line);
			eucliDistance = Math.sqrt(Math.pow((mouseX -(golfBalls[cpp].x + golfBalls[cpp].radius)), 2) + Math.pow((mouseY -(golfBalls[cpp].y + golfBalls[cpp].radius)), 2));
			float red = (float) (eucliDistance/1.5 - 50);
			float green = (float) (255 - eucliDistance/1.5);
			sr.setColor(red,green,0,0);
			//System.out.println("green: " + green + "red : " + red);
			sr.line(mouseX, mouseY, golfBalls[cpp].x + golfBalls[cpp].radius, golfBalls[cpp].y + golfBalls[cpp].radius);
			sr.end();
//			System.out.println("x = " + (mouseX -(golfBalls[cpp].x + golfBalls[cpp].radius)) + " 	y =" + (mouseY -(golfBalls[cpp].y + golfBalls[cpp].radius)) + "		euclidistance = " + eucliDistance + "	angle = " + PhysicsEngine.calcAngle(mouseX-(golfBalls[cpp].x + golfBalls[cpp].radius), (mouseY)-(golfBalls[cpp].y + golfBalls[cpp].radius)));
		}


		//Method 2 of moving the ball
		if(SI.getButtonClicked())
        {
            if(firstFrameOfSwing){
                p[cpp].setBallBlockedX(false);
                p[cpp].setBallBlockedY(false);
            }
            firstFrameOfSwing = false;
            p[cpp].moveBall(SI.getDir(), SI.getSpd());
        }

		/*
		nodes = new String[result.length()];
		wayX = new float[nodes.length];
		wayY = new float[nodes.length];
		for(int i =1; i <= result.length(); i++) {
			nodes[i - 1] = result.substring(i - 1, i);
		}


		boolean found = false;
		int v =0;
		int i = 0;
		int count = 0;
		while(!found) {
			if (nodes[v].equals(vertices2.get(i).getId())) {
				wayX[v] = vertices2.get(i).getXLoc();
				wayY[v] = vertices2.get(i).getYLoc();
				v++;
				count++;
			}
			else{
				i++;
			}
			if(i == vertices2.size()) {
				i = 0;
			}
			if (count == nodes.length) {
				found = true;
			}
		}
		*/

//		for(int j = 0; j < result.length(); j++){
		//	System.out.println( "node: " +nodes[j] + " x " + wayX[j] + " y " + wayY[j]);
//		}

		if (AI.getButtonClicked() || aiGoing) {

			aiGoing = true;
			/*
			if (firstFrameOfSwing) {
				p[cpp].setBallBlockedX(false);
				p[cpp].setBallBlockedY(false);
			}
			*/



			float ballX = golfBalls[cpp].getXCoords();
			float ballY = golfBalls[cpp].getYCoords();

			goalX = midpoints.get(0).get(shotIndex);
			goalY = midpoints.get(1).get(shotIndex);

			/*
			if(((golfBalls[cpp].x - goalX <= 30 || golfBalls[cpp].x - goalX <= -20 && golfBalls[cpp].y - goalY <= 30 || golfBalls[cpp].y - goalY <= -20) && firstFrameOfSwing) || j == 0){
			//if(firstFrameOfSwing){
				p[cpp].setBallBlockedX(false);
				p[cpp].setBallBlockedY(false);
				j++;
				firstFrameOfSwing = false;
			}
			*/

			//goalX = midpoints[0][j];
			//goalY = midpoints[1][j];

			//goalX = wayX[j];
			//goalY = wayY[j];
			 midpointFound = false;
			System.out.println(midpointFound);
			System.out.println("position x: " + golfBalls[cpp].x);
			System.out.println("position y: " + golfBalls[cpp].y);
			if (p[cpp].firstAICall) {
				aiDirection = p[cpp].aiAngle(ballX, ballY, goalX, goalY);
				System.out.println("shot with goal: " + goalX + " " + goalY);
				double direction = aiDirection + angleIncrease;
				p[cpp].moveBall(direction , speedIncrease);
				System.out.println("position x: " + golfBalls[cpp].x);
				System.out.println("position y: " + golfBalls[cpp].y);
				}

				if(Math.abs(goalX - golfBalls[cpp].x) <= 10 && Math.abs(goalY - golfBalls[cpp].y) <= 10 && firstFrameOfSwing ){
					midpointFound = true;
					System.out.println(midpointFound);
					firstFrameOfSwing = false;
					if(shotIndex < midpoints.size()) {
						shotIndex++;
					}
                    }


				if (p[cpp].getBallStopped() && !midpointFound) {
					golfBalls[cpp].x = p[cpp].positionX();
					golfBalls[cpp].y = p[cpp].positionY();
					speedIncrease += 100;
				}

				if(p[cpp].getBallStopped() && midpointFound){
				//	midpointFound = false;
					System.out.println(midpointFound);
					golfBalls[cpp].x = goalX;
					golfBalls[cpp].y = goalY;
					speedIncrease += 100;
				}
				if(speedIncrease == 2000) {
					speedIncrease = 0;
					angleIncrease += 5;
			}


				//if (golfBalls[cpp].x - goalX <= 30 || golfBalls[cpp].x - goalX <= -20 && golfBalls[cpp].y - goalY <= 30 || golfBalls[cpp].y - goalY <= -20) {
					//if(j < nodes.length-1){ j++;}
				//}
			}



		//Method 3 of moving the ball, uncomment and comment Method 1 to use

        /*
        if(currentSwing < numberOfSwings) p1.moveBall(directionValues.get(currentSwing),speedValues.get(currentSwing));
        if(p1.getBallStopped()) currentSwing++;
        if(currentSwing == numberOfSwings) currentSwing = 0;
        */

/*
        for (int z = 0; i < playersNbr; i++)
			if(ballEucliDistance(z, cpp) > maxDistanceMulti && z != cpp ){
                System.out.println("distance now");
                if (!elasticBand)
        		    resetBall();
                else{
                    golfBalls[cpp].setVX2(golfBalls[cpp].getVx2()/2);
                    golfBalls[cpp].setVY2(golfBalls[cpp].getVy2()/2);
//                    p[i].moveBall(PhysicsEngine.calcAngle(- (golfBalls[i].x + golfBalls[i].radius)-(golfBalls[cpp].x + golfBalls[cpp].radius), - (golfBalls[i].y + golfBalls[i].radius)-(golfBalls[cpp].y + golfBalls[cpp].radius)), (ballEucliDistance(i, cpp))/2);
                }
			}
*/




		//We update these booleans if the ball is stopped so that we know if we can make another swing
		if( p[cpp].getBallStopped()){
            if (released){
                scores[cpp] ++;
                System.out.println("score of " + cpp + " is " + scores[cpp]);
                if (cpp == playersNbr - 1){
                    cpp = 0;
                    System.out.println(" i is now 0");
                }
                else{
                    cpp++;
                    System.out.println(" i is now i + 1   or " + cpp );
                }
            }
            released = false;
            SI.setButtonClicked(false);
            firstFrameOfSwing = true;
		}

        if(goal.x - golfBalls[cpp].x <= 10 && goal.x - golfBalls[cpp].x >= -80 && goal.y - golfBalls[cpp].y <= 0 && goal.y - golfBalls[cpp].y >= -80){

			if (aiGoing && !aiDone) {
				final double finalDir = aiDirection + angleIncrease;
				final double finalSpeed = speedIncrease;
				AI.setVisible();
				golfBalls[cpp].x = p[cpp].positionX();
				golfBalls[cpp].y = p[cpp].positionY();
				p[cpp].golfBall.setVX2(0);
				p[cpp].golfBall.setVY2(0);
				p[cpp].moveBall(finalDir, finalSpeed);
				aiGoing = false;
				aiDone = true;
			}
			else {
				System.out.println("congrats");
				Win.winGUI();
				resetBall();
				golfBalls[cpp].x =80;
				golfBalls[cpp].y = 0;
				aiGoing = false;
				AI.setButtonClicked(false);
				oncePerShot = true;
				System.out.println(aiTimer / 60);
			}


		}

		if (p[cpp].getBallStopped()) {
			released = false;
			SI.setButtonClicked(false);
			firstFrameOfSwing = true;
		}


		/** check collision with the water and make the ball respawn */
		/**  !!!!!!!!!! need to make it pop at speed 0!!!*/
		if(water.x - golfBalls[cpp].x <= 33 && water.x - golfBalls[cpp].x >= -110 && water.y - golfBalls[cpp].y <= 33 && water.y - golfBalls[cpp].y >= -70){
            resetBall();
			System.out.println(" u in water. game over");
			if(aiGoing){ angleIncrease += 20; }
		}




		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) golfBalls[cpp].x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) golfBalls[cpp].x += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) golfBalls[cpp].y += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) golfBalls[cpp].y -= 200 * Gdx.graphics.getDeltaTime();

	}

	private void resetBall(){
	    System.out.println(" ball reseted : " + cpp);
        golfBalls[cpp].x = p[cpp].positionX();
        golfBalls[cpp].y = p[cpp].positionY();
        p[cpp].golfBall.setVX2(0);
        p[cpp].golfBall.setVY2(0);
    }


	//Methods concerning the user controls
	public boolean touchUp(int x, int y, int pointer, int button) {
		if (button == Input.Buttons.LEFT) {
			released = true;
			return true;
		}
		return false;
	}

	public double ballEucliDistance(int a, int b){
        return Math.sqrt(Math.pow( (double) ((golfBalls[a].x + golfBalls[a].radius) - (golfBalls[b].x + golfBalls[b].radius)), 2) + Math.pow((double) ((golfBalls[a].y + golfBalls[a].radius) -(golfBalls[b].y + golfBalls[b].radius)), 2));
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