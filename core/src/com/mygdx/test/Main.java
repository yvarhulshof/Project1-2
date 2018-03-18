package com.mygdx.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class Main extends ApplicationAdapter {
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture img;
	Rectangle golfBall;
    PhysicsEngine p;

    boolean firstPress = true;
	double startTime = 0;

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

		p = new PhysicsEngine();

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0.5f, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, golfBall.x,golfBall.y);
		batch.end();

        //if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) golfBall.x += p.moveBall(90,5) * Gdx.graphics.getDeltaTime();



		if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
		{

			if(firstPress) startTime = System.nanoTime() / 1000000000.0;
			firstPress = false;

			golfBall.x -= 5*Math.pow(System.nanoTime() / 1000000000.0 - startTime,1.2);
			System.out.println("speed: " + (int) (5*Math.pow(System.nanoTime() / 1000000000.0 - startTime,1.2)));

			//golfBall.x -= 100*Math.pow(Gdx.graphics.getDeltaTime(),2);
		}
		else firstPress = true;

		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) golfBall.x += 200 * Gdx.graphics.getDeltaTime();


	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	public Rectangle getGolfBall(){
	    return golfBall;
    }
}