import java.awt.*;
import java.util.Random;

public class Ball {
    public double x, y;
    public int WIDTH = 10;
    public int HEIGHT = 10;
    public double SPEED = 10;
    public double directionX;
    public double directionY;
    public Rectangle bounds;

    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
        double angle = Math.toRadians(new Random().nextInt((60 - 30) + 1) + 30);
        int initialDirection = new Random().nextInt(2);
        if (initialDirection == 1) {
            angle += Math.toRadians(180);
        }
        this.directionX = Math.cos(angle);
        this.directionY = Math.sin(angle);
    }

    public void update() {
        if (y <= 0 || y + HEIGHT >= Game.HEIGHT * Game.SCALE) {
            directionY *= -1;
        }

        if (x <= 0) {
            System.out.println("Bot win");
            new Game();
            return;
        } else if (x >= Game.WIDTH * Game.SCALE) {
            System.out.println("You win");
            new Game();
            return;
        }

        this.x += directionX * SPEED;
        this.y += directionY * SPEED;

        Rectangle bounds = new Rectangle((int) x, (int) y, WIDTH, HEIGHT);

        if (bounds.intersects(Game.player.bounds)) {
            directionX *= -1;
        }else if(bounds.intersects(Game.opponent.bounds)){
            directionX *= -1;
        }
    }

    public void render(Graphics graphics) {
        graphics.setColor(Color.white);
        graphics.fillOval((int) x, (int) y, WIDTH, HEIGHT);
    }
}
