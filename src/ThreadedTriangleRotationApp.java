import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ThreadedTriangleRotationApp extends JFrame implements ActionListener, KeyListener {
    private int angle = 0;
    private boolean rotatingClockwise = false;
    private boolean rotatingCounterClockwise = false;

    public ThreadedTriangleRotationApp() {
        setTitle("Threaded Triangle Rotation App");
        setSize(380, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        Timer timer = new Timer(100, this);
        timer.start();

        Thread rotationThread = new Thread(() -> {
            while (true) {
                if (rotatingClockwise) {
                    angle += 5;
                    repaint();
                } else if (rotatingCounterClockwise) {
                    angle -= 5;
                    repaint();
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        rotationThread.start();
    }

    public void actionPerformed(ActionEvent e) {
        // Empty implementation
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        int[] xPoints = {150, 250, 50};
        int[] yPoints = {50, 250, 250};

        Polygon triangle = new Polygon(xPoints, yPoints, 3);

        g2d.rotate(Math.toRadians(angle),150,150);
        g2d.fill(triangle);
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_RIGHT) {
            rotatingClockwise = true;
        } else if (keyCode == KeyEvent.VK_LEFT) {
            rotatingCounterClockwise = true;
        }
    }

    public void keyTyped(KeyEvent e) {
        // Empty implementation
    }

    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_RIGHT) {
            rotatingClockwise = false;
        } else if (keyCode == KeyEvent.VK_LEFT) {
            rotatingCounterClockwise = false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ThreadedTriangleRotationApp app = new ThreadedTriangleRotationApp();
            app.setVisible(true);
        });
    }
}