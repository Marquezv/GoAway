package com.goaway.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.goaway.game.GoAway;
import com.goaway.game.Screens.PlayScreen;
import com.goaway.game.Sprites.Brick;
import com.goaway.game.Sprites.Coin;
import com.goaway.game.Sprites.Objects;
import com.goaway.game.Sprites.Rat;

public class B2WorldCreator {
	private Array<Rat> rats;

	public B2WorldCreator(PlayScreen screen) {
		World world = screen.getWorld();
		TiledMap map = screen.getMap();
		
		BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create ground bodies/fixtures
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)/ GoAway.PPM, (rect.getY() + rect.getHeight() / 2)/ GoAway.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / GoAway.PPM, rect.getHeight() / 2 / GoAway.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        
//        //create brick bodies/fixtures
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Brick(screen, rect);
  
        }

        //create coin bodies/fixtures
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Coin(screen, rect);
            
        }
        
        //create object bodies/fixtures
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Objects(screen, rect);
            
        }
        
        // create rats
        rats = new Array<Rat>();
        for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            rats.add(new Rat(screen, rect.getX() / GoAway.PPM, rect.getY() / GoAway.PPM));
            
        }
        

	}
	 public Array<Rat> getRats() {
 		return rats;
 	}

 	public void setRats(Array<Rat> rats) {
 		this.rats = rats;
 	}
}
