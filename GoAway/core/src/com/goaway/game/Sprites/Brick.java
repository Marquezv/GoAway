package com.goaway.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.goaway.game.GoAway;
import com.goaway.game.Scenes.Hud;
import com.goaway.game.Screens.PlayScreen;

public class Brick extends InteractiveTileObject{
	public Brick(PlayScreen screen, Rectangle bounds) {
		super(screen, bounds);
		fixture.setUserData(this);
		setCategoryFilter(GoAway.BRICK_BIT);
		
	}

	@Override
	public void onHit() {
		Gdx.app.log("Brick", "Collision");
		setCategoryFilter(GoAway.DESTROYED_BIT);
		getCell().setTile(tile);
		Hud.addScore(200);
	}
}
