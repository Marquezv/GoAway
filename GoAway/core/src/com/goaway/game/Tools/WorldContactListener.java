package com.goaway.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.goaway.game.GoAway;
import com.goaway.game.Sprites.Enemy;
import com.goaway.game.Sprites.InteractiveTileObject;
import com.goaway.game.Sprites.Player;
import com.goaway.game.Sprites.Player.State;

public class WorldContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		
		int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
		
		Gdx.app.log("Jump", "Action");
		if("body".equals(fixA.getUserData()) || "body".equals(fixB.getUserData())) {
			Fixture head = fixA.getUserData() == "body" ? fixA : fixB;
			Fixture object = head == fixA ? fixB : fixA;
			
			if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass()) 
				&& (Player.currentState == State.JUMPING)) {
				((InteractiveTileObject) object.getUserData()).onHit();
			
			}
			
		}
		switch (cDef) {
		case GoAway.ENEMY_HEAD_BIT | GoAway.PLAYER_BIT:
			if(fixA.getFilterData().categoryBits == GoAway.ENEMY_HEAD_BIT) {
				((Enemy)fixA.getUserData()).hitOnShape();
			}
			else {
				((Enemy)fixB.getUserData()).hitOnShape();
			}
			break;
		case GoAway.ENEMY_BIT | GoAway.PLAYER_BIT:
			if(fixA.getFilterData().categoryBits == GoAway.ENEMY_BIT) {
				((Enemy)fixA.getUserData()).reverseVelocity(true, false);
			}
			else {
				((Enemy)fixB.getUserData()).reverseVelocity(true, false);
			}
			break;
		}
		
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	
}
