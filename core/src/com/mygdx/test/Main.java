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
 * Documentation is essential!!!
 */
public class Main extends ApplicationAdapter implements InputProcessor{
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture img;
	static Circle golfBall;

	SpriteBatch goalBatch;
	Texture goalImg;
	static Circle goal;

	SpriteBatch waterBatch;
	Texture waterImg;
	static Rectangle water;

	PhysicsEngine p;
	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;
    static boolean released;
    SwingInput SI;
    ArrayList<Rectangle> obstacles;
    private float mouseX;
    private float mouseY;

    private FileInput FI;
    private String[] mapInfo;
    private ArrayList<String> swingInfo;

    private ShapeRenderer sr;
	WinFrame Win;

	@Override
	public void create () {
		/** create the sprites */
		batch = new SpriteBatch();
		img = new Texture("golfball3.png");
		goalBatch = new SpriteBatch();
		goalImg = new Texture("circle.png");
		waterBatch = new SpriteBatch();
		waterImg = new Texture("water.jpg");
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		camera.update();

		/** load the map */
		//tiledMap = new TmxMapLoader().load("map.tmx");
		tiledMap = new TmxMapLoader().load("map2.tmx");

		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		Gdx.input.setInputProcessor(this);
		SI = new SwingInput();

		SI.createGUI();

		sr = new ShapeRenderer();

		for(MapObject object : tiledMap.getLayers().get("Obstacles").getObjects()){
		    Rectangle rect = ((RectangleMapObject) object).getRectangle();
		    sr.begin(ShapeRenderer.ShapeType.Filled);
		    sr.rect(rect.x,rect.y,rect.width,rect.height);
		    sr.end();
        }

        obstacles = new ArrayList<Rectangle>();

        sr.setColor(CYAN);

		water = new Rectangle(1200/2 - 64/2, 70, 120, 80);
        golfBall = new Circle(800 / 2 - 64 / 2, 20, 25);
        goal = new Circle(600/2, 40, 30);


		double golfBallHeight = Math.sin(golfBall.x) + Math.pow(golfBall.y,2);

		p = new PhysicsEngine(golfBall);

        FI = new FileInput();

	   mapInfo = FI.readMapInfo();

        p.setGravitationalForce(Double.parseDouble(mapInfo[0]));
        p.setFrictionConstant(Double.parseDouble(mapInfo[1]));
        p.setMaxSpeed(Double.parseDouble(mapInfo[2]));

        swingInfo = FI.readSwingInfo();

		Gdx.input.setInputProcessor(this);
		Win = new WinFrame();



	}


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
		batch.draw(img, golfBall.x,golfBall.y);
		batch.end();

		waterBatch.begin();
		waterBatch.draw(waterImg,water.x,water.y);
		waterBatch.end();

		sr.setProjectionMatrix(camera.combined);

		//if(i.touchDown(0,0,0,0)) p.moveBall(135,3); //method that moves the ball, starting with initial speed and then deaccelerating
		if(released)
		{
			p.moveBall(PhysicsEngine.calcAngle(mouseX-golfBall.x, mouseY-golfBall.y), Math.pow(((Math.sqrt(Math.pow((mouseX - golfBall.x), 2) + Math.pow((mouseY - golfBall.y), 2)))/500),2));
		}

		if (touchDragged(0,0,0) && p.getBallStopped())
		{
			//leftKeyPressed = true;
			mouseX = Gdx.input.getX();
			mouseY = Gdx.input.getY();
			ShapeRenderer sr = new ShapeRenderer();
			camera.update();
			sr.setProjectionMatrix(camera.combined);
			sr.begin(ShapeType.Line);
			sr.setColor((float) (Math.sqrt(Math.pow((mouseX - golfBall.x), 2) + Math.pow((mouseY - golfBall.y), 2))/300),255 - ((float) (Math.sqrt(Math.pow((mouseX - golfBall.x), 2) + Math.pow((mouseY - golfBall.y), 2))/300)),0,0);
			sr.line(mouseX, Gdx.graphics.getHeight() - mouseY, golfBall.x + golfBall.radius, golfBall.y + golfBall.radius);
			sr.end();

		}

		if(SI.getButtonClicked())
        {
            p.moveBall(SI.getDir(), SI.getSpd());
        }

		if(p.getBallStopped()){
		    released = false;
            SI.setButtonClicked(false);
        }

        double golfBallEdgeXpos = golfBall.x + golfBall.radius;
        double golfBallEdgeXneg = golfBall.x - golfBall.radius;
        double golfBallEdgeYpos = golfBall.y + golfBall.radius;
        double golfBallEdgeYneg = golfBall.y - golfBall.radius;

        /*for(Rectangle obst : obstacles){
		    if(     golfBallEdgeXpos > obst.x && golfBallEdgeXpos < obst.x + obst.width ||
                    golfBallEdgeXneg > obst.x && golfBallEdgeXneg < obst.x + obst.width ||
                    golfBallEdgeYpos > obst.x && golfBallEdgeYpos < obst.x + obst.width ||
                    golfBallEdgeXneg > obst.x && golfBallEdgeYneg < obst.x + obst.width)
            {
                System.out.println("collision");
            }
            System.out.println("obst x: " + obst.x);


		    //if(golfBall.x == obst.x && golfBall.y == obst.y) //should checking all of their coordinates, not just their center
            //{
            //    System.out.println("collision");
            //}

        }
        */
        /** if ball in the goal's radius --> win window pops */
		if(		goal.x - golfBall.x <= 0 && goal.x - golfBall.x >= -80 &&
				goal.y - golfBall.y <= 0 && goal.y - golfBall.y >= -80){
			System.out.println("congrats");
			Win.winGUI();
			golfBall.x = 800/2 - 64/2;
			golfBall.y = 20;

		}

		/** check collision with the water and make the ball respawn */
		/**  !!!!!!!!!! need to make it pop at speed 0!!!*/
		if(		water.x - golfBall.x <= 33 && water.x - golfBall.x >= -110 &&
				water.y - golfBall.y <= 33 && water.y - golfBall.y >= -70){
			System.out.println(" u in water. game over");

			golfBall.x = water.x - (golfBall.radius + water.width/2);
			golfBall.y = water.y - (golfBall.radius + water.height/2);

		}


        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) golfBall.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) golfBall.x += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) golfBall.y += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) golfBall.y -= 200 * Gdx.graphics.getDeltaTime();

	}
	@Override public boolean keyDown(int keycode) {
		return false;
	}

	@Override public boolean keyUp(int keycode) {
		if(keycode == Input.Keys.A)
			camera.translate(-32,0);
		if(keycode == Input.Keys.D)
			camera.translate(32,0);
		if(keycode == Input.Keys.W)
			camera.translate(0,32);
		if(keycode == Input.Keys.S)
			camera.translate(0,-32);
		if(keycode == Input.Keys.NUM_1)
			tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
		if(keycode == Input.Keys.NUM_2)
			tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
		return false;
	}

	@Override public boolean keyTyped(char character) {

		return false;
	}
/*
	@Override public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 clickCoordinates = new Vector3(screenX,screenY,0);
		Vector3 position = camera.unproject(clickCoordinates);
		golfBall.setPosition(position.x, position.y);
		return true;
	}
*/
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
		img.dispose();
	}

}
