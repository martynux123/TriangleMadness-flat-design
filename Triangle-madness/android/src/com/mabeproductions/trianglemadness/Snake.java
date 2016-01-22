package com.mabeproductions.trianglemadness;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mabeproductions.trianglemadness.Math.MathUtils;

public class Snake {

	private ArrayList<Vector2> snake = new ArrayList<Vector2>();
	private float angle;
	private Vector2 pos;
	private long delay;
	private float length;
	private boolean isAlive = true;
	private Rectangle bounds;
	private ShapeRenderer renderer;

	public Snake(float angle, Vector2 pos, long delay, float length) {
		this.angle = angle;
		this.pos = pos;
		this.delay = delay;
		this.length = length;
		this.renderer = new ShapeRenderer();
		threadRunner();
		for (int i = 0; i < length; i++) {

			if (i == 0)
				snake.add(pos);

			Vector2 lastPos = snake.get(0);
			Vector2 offSet = MathUtils.moveAlongAngle2(angle, 17);
			Vector2 finalPos = lastPos.add(offSet);

			snake.add(new Vector2(lastPos.x + offSet.x, lastPos.y + offSet.y));

		}

	}

	public void update() {

	}

	private void threadRunner() {

		new Thread(new Runnable() {

			public void run() {
				// Dabar isAlive=true;
				while (isAlive) {

					try {

						Thread.sleep(delay);
						Vector2 lastPos = snake.get(snake.size() - 1);
						Vector2 offSet = MathUtils.moveAlongAngle2(angle, 17);
						Vector2 finalPos = lastPos.add(offSet);
						//bounds = new Rectangle(lastPos.x + offSet.x, lastPos.y + offSet.y, 50, 50);

						snake.remove(0);
						snake.add(new Vector2(lastPos.x + offSet.x, lastPos.y + offSet.y));

						if (snake.get(snake.size() - 1).y > Gdx.graphics.getHeight() - 80) {
							angle += 90;
							snake.remove(0);
						}

						if (snake.get(snake.size() - 1).y < 0) {
							angle += 90;
							snake.remove(0);
						}
						if (snake.get(snake.size() - 1).x < 0) {
							angle += 90;
							snake.remove(0);
						}
						if (snake.get(snake.size() - 1).x > Gdx.graphics.getWidth() - 60) {
							angle += 90;
							snake.remove(0);

						}

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("angle " + angle + " size " + snake.size());

					// Idejau sita eilute.Pagal ideja veikia taip. Kai paliecia
					// kazkur siena, kol dar yra 1gyvas panaikina,
					// O tada dar ir removina paskutini. Nezinau ar cia geras
					// budas, bet veikia normaliai atrodo :D.
					if (snake.size() == 1) {
						isAlive = false;
						snake.remove(0);
					}

				}
			}

		}).start();
	}

	public void render() {

		for (int i = 0; i < snake.size(); i++) {

			// Setting up a shape renderer
			renderer.setAutoShapeType(true);
			renderer.setColor(Color.GREEN);

			if (snake.get(snake.size() - 1) == snake.get(i)) {
				renderer.setColor(Color.WHITE);
			}

			renderer.begin();
			renderer.set(ShapeType.Filled);
			renderer.rect(snake.get(i).x, snake.get(i).y, 0, 0, 50, 50, 1, 1, 45);
			
			renderer.end();

		}

	}

}