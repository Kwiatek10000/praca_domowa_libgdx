package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture car;
	Texture map;
	Texture grid;
	Texture logo;
	OrthographicCamera mapCamera;
	OrthographicCamera carCamera;
	OrthographicCamera logoCamera;
	private Music bgmusic;
	float time = 0;
	float time2 = 0;
	int angle = 0;
	float x = 0;
	float sin = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		car = new Texture("Audi.png");
		map = new Texture("mapa.png");
		logo = new Texture("logo.png");
		grid = new Texture("libgdxgridtest.png");
		mapCamera = new OrthographicCamera();
		carCamera = new OrthographicCamera();
		logoCamera = new OrthographicCamera();
		bgmusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		bgmusic.setLooping(true);
		bgmusic.play();
	}

	@Override
	public void render () {
		time2 += Gdx.graphics.getDeltaTime();
		if ((time2*64)<500) {
			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			sin = 32*((float) Math.sin(time2*4));

			logoCamera.setToOrtho(false, 800, 400);
			logoCamera.translate(-800/2, -400/2);
			logoCamera.translate(0, sin);
			logoCamera.update();

			batch.begin();
			batch.setProjectionMatrix(logoCamera.combined);
			batch.draw(logo, -100, -100);
			batch.end();
		} else {
			time += Gdx.graphics.getDeltaTime();

			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			carCamera.setToOrtho(false, 800, 400);
			carCamera.translate(-400/2, -200/2);
			//carCamera.translate(, );
			if ((time*64)>450&&(time*64)<535) {
				carCamera.rotate(-angle);
				angle++;
			} else if ((time*64)>535) {
				carCamera.rotate(-90);
			}
			//carCamera.rotate(time*10);
			carCamera.zoom = 0.5f;
			carCamera.update();

			mapCamera.setToOrtho(false, 800, 400);
			mapCamera.translate(-800/2, -400/2);
			if ((time*64)<450) {
				mapCamera.translate(0, time*64);
				x = time*64;
			} else if ((time*64)>535) {
				mapCamera.translate((time*64)-525, x);
			} else if ((time*64)>450&&(time*64)<535) {
				mapCamera.translate(0, x);
			}
			mapCamera.update();

			batch.begin();
			{
				batch.setProjectionMatrix(mapCamera.combined);
				batch.draw(map, -400, -200);
				batch.setProjectionMatrix(carCamera.combined);
				batch.draw(car, -25, -25);
			}
			batch.end();
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
