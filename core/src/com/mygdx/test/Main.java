package com.mygdx.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;

public class Main extends ApplicationAdapter implements InputProcessor{
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture img;
	static Rectangle golfBall;
    PhysicsEngine p;
	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;
    static boolean pressed;
    //MyInputProcessor i;
    final double epsilon = 0.0001;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("golfball3.png");
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		camera.update();
		tiledMap = new TmxMapLoader().load("map.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		Gdx.input.setInputProcessor(this);

		golfBall = new Rectangle();
		golfBall.x = 800 / 2 - 64 / 2;
		golfBall.y = 20;
		golfBall.width = 64;
		golfBall.height = 64;

		p = new PhysicsEngine(golfBall);

		//MyInputProcessor i = new MyInputProcessor();
		Gdx.input.setInputProcessor(this);
	}


	public boolean touchDown (int x, int y, int pointer, int button) {
		if (button == Input.Buttons.LEFT) {
			//while(Math.abs(p.previousGolfballX - p.golfBall.x) > epsilon && Math.abs(p.previousGolfballY - p.golfBall.y) > epsilon)
			//{
			//	p.moveBall(135, 1);
			//}
			pressed = true;
			return true;
		}
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
		batch.begin();
		batch.draw(img, golfBall.x,golfBall.y);
		batch.end();

		//if(i.touchDown(0,0,0,0)) p.moveBall(135,3); //method that moves the ball, starting with initial speed and then deaccelerating
		if(pressed)
		{
			p.moveBall(135,1.5);
		}

		if(p.getBallStopped()) pressed = false;
		//if(Math.abs(p.previousGolfballX - p.golfBall.x) > epsilon && Math.abs(p.previousGolfballY - p.golfBall.y) > epsilon) pressed = false;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) golfBall.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) golfBall.x += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) golfBall.y += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) golfBall.y -= 200 * Gdx.graphics.getDeltaTime();

	}
	@Override public boolean keyDown(int keycode) {
		return false;
	}

	@Override public boolean keyUp(int keycode) {
		if(keycode == Input.Keys.LEFT)
			camera.translate(-32,0);
		if(keycode == Input.Keys.RIGHT)
			camera.translate(32,0);
		if(keycode == Input.Keys.UP)
			camera.translate(0,32);
		if(keycode == Input.Keys.DOWN)
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
	@Override public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override public boolean scrolled(int amount) {
		return false;
	}


	/*@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
 */
}
