package com.mygdx.game;

import java.io.IOException;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	boolean FirstTime= true;
	  private Skin skin;
	  private Stage stage;
	//Texture img;	 
	 private BitmapFont font;
	 Provider LeServeur = new Provider();
	 private String  Messages= "En attente de connexion !";
	 ShapeRenderer SH;
	 String RécupX;
	 String RécupY;
	 int y= 50;
	 int x = 50;
	 
	 Label label ;
	 int nbressai =0;
	@Override
	public void create () {
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		//skin = new Skin();
		
		batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
		
		 font = new BitmapFont();
	     font.setColor(Color.RED);
	     LeServeur.run();
	     
	     stage = new Stage();

	        final TextButton button = new TextButton("Click me", skin, "default");
	        
	        button.setWidth(200f);
	        button.setHeight(20f);
	        button.setPosition(Gdx.graphics.getWidth() /2 - 100f, Gdx.graphics.getHeight()/2 - 10f);
	        button.addListener(new ClickListener(){
	            @Override 
	            public void clicked(InputEvent event, float x, float y){
	                button.setText("You clicked the button");
	                nbressai = nbressai +1;
	                LeServeur.sendMessage("You clicked the button. Essai = " + nbressai);
	               
	            }
	        });
	        
	        //final Label text = new Label();
	        label = new Label("Attente connexion", skin);
	        
	        stage.addActor(button);
	        stage.addActor(label);
	        
	        Gdx.input.setInputProcessor(stage);
	        SH = new ShapeRenderer();
	}

	@Override
	public void render () {
		//
        //String RécupY;
		 //int x;
		  //int y;
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//batch.draw(img, 0, 0);
		font.draw(batch, Messages, 10, 450);
		 stage.draw();
		batch.end();
		if (FirstTime == false) {
			//LeServeur.run();
			String Unmessage= "";
			try {
				Unmessage=LeServeur.LireMessage();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (Unmessage.length()> 1) {
				//Messages=Unmessage;
			  label.setText(Unmessage);	
			  // Lemessage="Bouton droit X =" +String.format("%06d",x) + "---Y =" + String.format("%06d",y);
			
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
				
					LeServeur.sendMessage(Lemessage);
				
				
				
			}
			return true;
			}
			});			
		
		
		
		 
	}
}
