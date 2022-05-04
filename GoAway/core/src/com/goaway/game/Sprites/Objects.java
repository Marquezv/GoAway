package com.goaway.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.goaway.game.GoAway;
import com.goaway.game.Scenes.Hud;
import com.goaway.game.Screens.PlayScreen;

public class Objects extends InteractiveTileObject{
	public Objects(PlayScreen screen, Rectangle bounds) {
		super(screen, bounds);
		fixture.setUserData(this);
		setCategoryFilter(GoAway.OBJECT_BIT);
		Gdx.app.log("Objects", "Collision");
		
	}

	@Override
	public void onHit() {
		// TODO Auto-generated method stub
		
	}

}
