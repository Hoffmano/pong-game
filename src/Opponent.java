import java.awt.*;

public class Opponent {

    public double x, y;
    public int WIDTH = 10;
    public int HEIGHT = 50;
    private double SPEED = 0.7;
    public Rectangle bounds;

    public Opponent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update() {

        if (y + (Game.ball.y - y) * SPEED >= 0) {
            y += (Game.ball.y - y) * SPEED;
        } else {
            y = 0;
        }

        if (y + (Game.ball.y - y) * SPEED <= Game.HEIGHT * Game.SCALE - HEIGHT) {
            y += (Game.ball.y - y) * SPEED;
        } else {
            y = Game.HEIGHT * Game.SCALE - HEIGHT;
        }
        bounds = new Rectangle((int) x, (int) y, WIDTH, HEIGHT);
    }

    public void render(Graphics graphics) {
        graphics.setColor(Color.white);
        graphics.fillRect((int) x, (int) y, WIDTH, HEIGHT);
    }
}
