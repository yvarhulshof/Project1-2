package com.mygdx.game;

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
    Rectangle picture;
    boolean firstPress = true;
	double startTime = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("golfball3.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        picture = new Rectangle();
        picture.x = 800 / 2 - 64 / 2;
        picture.y = 20;
        picture.width = 64;
        picture.height = 64;
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0.5f, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, picture.x,picture.y);
		batch.end();


        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
		{
			if(firstPress) startTime = System.nanoTime() / 1000000000.0;
			firstPress = false;

			picture.x -= 5*Math.pow(System.nanoTime() / 1000000000.0 - startTime,1.2);
			System.out.println(Gdx.graphics.getDeltaTime() / 100000000);

			//picture.x -= 100*Math.pow(Gdx.graphics.getDeltaTime(),2);
		}

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) picture.x += 200 * Gdx.graphics.getDeltaTime();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
