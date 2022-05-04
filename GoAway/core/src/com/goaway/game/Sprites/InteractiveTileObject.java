package com.goaway.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.goaway.game.GoAway;
import com.goaway.game.Screens.PlayScreen;

public abstract class InteractiveTileObject {
	protected World world;
	protected TiledMap map;
	protected TiledMapTile tile;
	protected Rectangle bounds;
	protected Body body;
	
	protected Fixture fixture;
	
	public InteractiveTileObject(PlayScreen screen, Rectangle bounds) {
		this.world = screen.getWorld();
		this.map = screen.getMap();
		this.bounds = bounds;
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2)/ GoAway.PPM, (bounds.getY() + bounds.getHeight() / 2)/ GoAway.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / GoAway.PPM, bounds.getHeight() / 2 / GoAway.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);
	}
	
	public abstract void onHit();
	public void setCategoryFilter(short filterBit) {
		Filter filter = new Filter();
		filter.categoryBits = filterBit;
		fixture.setFilterData(filter);
	}
	
	public TiledMapTileLayer.Cell getCell(){
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(2);
		return layer.getCell((int)(body.getPosition().x * GoAway.PPM / 16), 
				(int)(body.getPosition().y * GoAway.PPM / 16));
	}
}
