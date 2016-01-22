package com.mabeproductions.trianglemadness.Math;

import com.badlogic.gdx.math.Vector2;

public class MathUtils {
	
	
	public static Vector2 moveAlongAngle2(float angle, float distance){
		
		float x = (float) Math.sin(Math.toRadians(angle))*distance;
		float y = (float) Math.cos(Math.toRadians(angle))*distance;
		
		return new Vector2(x, y);
		
	}
	
	
	
}