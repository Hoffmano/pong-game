import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable, KeyListener {

    public static Player player;
    public static Opponent opponent;
    public static Ball ball;

    public static int WIDTH = 256;
    public static int HEIGHT = 144;
    public static int SCALE = 3;

    private static boolean isRunning = false;

    private static int frames = 0;

    BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    private Thread thread;

    public Game() {
        this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        this.addKeyListener(this);

        player = new Player(10, (((HEIGHT * SCALE) - 50) / 2));
        opponent = new Opponent(WIDTH * SCALE - 20, (((HEIGHT * SCALE) - 50) / 2));
        ball = new Ball((((WIDTH * SCALE) - 10) / 2), (((HEIGHT * SCALE) - 10) / 2));
    }

    public static void main(String[] args) {
        Game game = new Game();
        createFrame(game);
        game.start();
    }

    private static void createFrame(Game game) {
        JFrame frame = new JFrame("Pong");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    @Override
    public void run() {
        requestFocus();
        long lastTime = System.nanoTime();
        int fps = 30;
        double nanoSecondsPerFrame = 1000000000 / fps;
        double deltaTime = 0;
        double initialTimeMillis = System.currentTimeMillis();
        while (isRunning) {
            long now = System.nanoTime();
            deltaTime += (now - lastTime) / nanoSecondsPerFrame;
            lastTime = now;
            deltaTime = executeNextFrame(deltaTime);
            initialTimeMillis = printFps(initialTimeMillis);
        }
    }

    private double executeNextFrame(double deltaTime) {
        if (deltaTime >= 1) {
            frames++;
            update();
            render();
            deltaTime = 0;
        }
        return deltaTime;
    }

    private double printFps(double timer) {
        if (System.currentTimeMillis() - timer >= 1000) {
            //            System.out.println(frames);
            frames = 0;
            timer += 1000;
        }
        return timer;
    }

    private void update() {
        player.update();
        opponent.update();
        ball.update();
    }

    private void render() {
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        if (bufferStrategy == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);

        player.render(graphics);
        opponent.render(graphics);
        ball.render(graphics);

        bufferStrategy.show();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_W) {
            player.up = true;
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_S) {
            player.down = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_W) {
            player.up = false;
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_S) {
            player.down = false;
        }
    }
}
