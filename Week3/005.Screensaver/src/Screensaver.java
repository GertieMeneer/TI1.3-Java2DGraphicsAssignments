import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Screensaver extends JPanel implements Runnable {

    private static final int NUM_PARTICLES = 50;
    private static final int MAX_PARTICLE_SPEED = 5;
    private static final int MAX_PARTICLE_SIZE = 10;
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;

    private Particle[] particles;

    public Screensaver() {
        particles = new Particle[NUM_PARTICLES];
        Random random = new Random();
        for (int i = 0; i < NUM_PARTICLES; i++) {
            particles[i] = new Particle(
                    random.nextInt(SCREEN_WIDTH),
                    random.nextInt(SCREEN_HEIGHT),
                    random.nextInt(MAX_PARTICLE_SPEED),
                    random.nextInt(MAX_PARTICLE_SPEED),
                    random.nextInt(MAX_PARTICLE_SIZE) + 1,
                    new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256))
            );
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Particle particle : particles) {
            particle.draw(g);
        }
    }

    @Override
    public void run() {
        while (true) {
            for (Particle particle : particles) {
                particle.move();
            }
            repaint();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Neon Screensaver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setResizable(false);
        Screensaver screensaver = new Screensaver();
        frame.add(screensaver);
        frame.setVisible(true);
        Thread thread = new Thread(screensaver);
        thread.start();
    }

    private static class Particle {
        private int x;
        private int y;
        private int dx;
        private int dy;
        private int size;
        private Color color;

        public Particle(int x, int y, int dx, int dy, int size, Color color) {
            this.x = x;
            this.y = y;
            this.dx = dx;
            this.dy = dy;
            this.size = size;
            this.color = color;
        }

        public void move() {
            x += dx;
            if (x < 0) {
                x = 0;
                dx = -dx;
            } else if (x > SCREEN_WIDTH - size) {
                x = SCREEN_WIDTH - size;
                dx = -dx;
            }
            y += dy;
            if (y < 0) {
                y = 0;
                dy = -dy;
            } else if (y > SCREEN_HEIGHT - size) {
                y = SCREEN_HEIGHT - size;
                dy = -dy;
            }
        }

        public void draw(Graphics g) {
            g.setColor(color);
            g.fillRect(x, y, size, size);
        }
    }
}
