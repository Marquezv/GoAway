package com.goaway.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.goaway.game.GoAway;
import com.goaway.game.Scenes.Hud;
import com.goaway.game.Screens.PlayScreen;

public class Coin extends InteractiveTileObject{
	public Coin(PlayScreen screen, Rectangle bounds) {
		super(screen, bounds);
		fixture.setUserData(this);
		setCategoryFilter(GoAway.COIN_BIT);
	}

	@Override
	public void onHit() {
		Gdx.app.log("Coin", "Collision");
		Hud.addScore(100);
	}
}
