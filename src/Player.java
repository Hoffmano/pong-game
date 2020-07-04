import java.awt.*;

public class Player {

    public boolean up = false;
    public boolean down = false;
    public int x;
    public int y;
    private int WIDTH = 10;
    public int HEIGHT = 50;
    private int SPEED = 10;
    public Rectangle bounds;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        if (up) {
            if (y - SPEED >= 0) {
                y -= SPEED;
            } else {
                y = 0;
            }
        } else if (down) {
            if (y + SPEED <= Game.HEIGHT * Game.SCALE - HEIGHT) {
                y += SPEED;
            } else {
                y = Game.HEIGHT * Game.SCALE - HEIGHT;
            }
        }
        bounds = new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public void render(Graphics graphics) {
        graphics.setColor(Color.white);
        graphics.fillRect(x, y, 10, 50);
    }
}
