package com.goaway.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.goaway.game.GoAway;
import com.goaway.game.Screens.PlayScreen;

public class Rat extends Enemy{

	private float stateTime;
	private Animation<TextureRegion> walkAnimation;
	private Array<TextureRegion> frames;
	private boolean setToDestroy = false;
	private boolean destroyed = false;
	
	public Rat(PlayScreen screen, float x, float y) {
		super(screen, x, y);
		frames = new Array<TextureRegion>();
		for(int i = 0; i < 3; i++) 
			frames.add(new TextureRegion(screen.getAtlasEnemy().findRegion("Idle"), i * 64, 0, 32, 32));
		
		walkAnimation = new Animation(0.6f, frames);
		stateTime = 0;
		setBounds(getX(), getY(), 32 / GoAway.PPM, 32 / GoAway.PPM);
		setToDestroy = false;
		destroyed = false;
	}
	
	public void update(float dt) {
		stateTime += dt;
		if(setToDestroy && !destroyed) {
			world.destroyBody(b2body);
			destroyed = true;
			setRegion(new TextureRegion(screen.getAtlasEnemy().findRegion("Hurt"), 26, 0, 32, 32));
			stateTime = 0;
		}
		else if(!destroyed){
			b2body.setLinearVelocity(velocity);
			setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
			setRegion(walkAnimation.getKeyFrame(stateTime, true));
		}
		
	}
	
	@Override
	protected void defineEnemy() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(getX() , getY());
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		Vector2[] verticeShape = new Vector2[4];
		verticeShape[0] = new Vector2(10, -6).scl(1 / GoAway.PPM);
		verticeShape[1] = new Vector2(-10, -6).scl(1 / GoAway.PPM);
		verticeShape[2] = new Vector2(10, -15).scl(1 / GoAway.PPM);
		verticeShape[3] = new Vector2(-10, -15).scl(1 / GoAway.PPM);
		shape.set(verticeShape);
		
		
		fdef.filter.categoryBits = GoAway.ENEMY_BIT;
		fdef.filter.maskBits = GoAway.GROUND_BIT | 
				GoAway.COIN_BIT | 
				GoAway.BRICK_BIT |
				GoAway.ENEMY_BIT |
				GoAway.OBJECT_BIT |
				GoAway.PLAYER_BIT;
		
		fdef.shape = shape;
		fdef.restitution = 0.5f;
		
		b2body.createFixture(fdef).setUserData(this);
		
		 EdgeShape head = new EdgeShape();
	     head.set(new Vector2(-8 / GoAway.PPM, -5/ GoAway.PPM), new Vector2(8 / GoAway.PPM, -5/ GoAway.PPM));
	     fdef.filter.categoryBits = GoAway.ENEMY_HEAD_BIT;
	     fdef.shape = head;
	     fdef.isSensor = true;
	     b2body.createFixture(fdef).setUserData(this);
	}
	
	public void draw(Batch batch) {
		if(!destroyed || stateTime < 1) {
			super.draw(batch);
		}
	}
	
	@Override
	public void hitOnShape() {
		setToDestroy = true;
	}
}
