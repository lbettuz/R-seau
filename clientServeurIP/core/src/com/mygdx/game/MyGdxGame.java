package com.mygdx.game;

import java.io.IOException;
import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;


public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	boolean FirstTime= true;
	 private BitmapFont font;
	 private String  Messages= "Connexion en cours!";
	 Requester Leclient = new Requester();
	 ShapeRenderer SH;
	 String RécupX;
	 String RécupY;
	 int y;
	 int x;
	@Override
	public void create () {
		batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
		
		 font = new BitmapFont();
	     font.setColor(Color.RED);
	     Leclient.run();
	     SH = new ShapeRenderer();
	}

	@Override
	public void render () {
		String Unmessage="";
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//batch.draw(img, 0, 0);
		font.draw(batch, Messages, 10, 450);
		batch.end();
		
		if (FirstTime == false) {
			//LeServeur.run();
			
			
			try {
				Unmessage=Leclient.LireMessage();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (Unmessage.length()> 0) {
				Messages=Unmessage;
				//Bouton droit X =123456---Y =123456
				if (Unmessage.length()> 25)
				{
					RécupX = Unmessage.substring(16, 22);
					 x = Integer.parseInt(RécupX); 
					 System.out.println(x);
					 RécupY = Unmessage.substring(29);
					 y = Integer.parseInt(RécupY); 
					 System.out.println(x);
					 
				}
				
			}
		}
		
		SH.begin(ShapeType.Filled);
		 SH.setColor(Color.BLUE);
		 SH.circle(x,y, 10);
		 SH.end();
		// System.out.println("Render");
		FirstTime= false;
		
		
		Gdx.input.setInputProcessor(new InputAdapter () {
			public boolean touchDown (int x, int y, int pointer, int button) {
				String Lemessage="";
			if ( button == Input.Buttons.RIGHT)
			{    // bouton droit de la souris --> Creation d'une souris
				
			}
			if ( button == Input.Buttons.LEFT) {				
				 // Bouton gauche de la souris --> on supprime les souris sous le curseur
				Lemessage="Bouton droit X =" +String.format("%06d",x) + "---Y =" + String.format("%06d",y);
				try {
					Leclient.sendMessage(Lemessage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			return true;
			}
			});			
		
		
	}
}
