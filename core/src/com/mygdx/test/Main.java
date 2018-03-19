package com.mygdx.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Main extends ApplicationAdapter{
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture img;
	static Rectangle golfBall;
    PhysicsEngine p;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("golfball3.png");

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		golfBall = new Rectangle();
		golfBall.x = 800 / 2 - 64 / 2;
		golfBall.y = 20;
		golfBall.width = 64;
		golfBall.height = 64;

		p = new PhysicsEngine(golfBall);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0.5f, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, golfBall.x,golfBall.y);
		batch.end();


		p.moveBall(135,3); //method that moves the ball, starting with initial speed and then deaccelerating


        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) golfBall.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) golfBall.x += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) golfBall.y += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) golfBall.y -= 200 * Gdx.graphics.getDeltaTime();

	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

}
