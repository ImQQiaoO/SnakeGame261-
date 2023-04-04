import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Menu extends JPanel implements KeyListener {

    JFrame frame = new JFrame();
    public static String highestScore = "-";

    Color black = Color.BLACK;
    Color red = Color.RED;
    Color blue = Color.BLUE;
    Color green = Color.GREEN;
    Color white = Color.WHITE;

    public void changeBackgroundColor(Graphics g, Color c) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setBackground(c);
    }

    public void changeBackgroundColor(Graphics g, int red, int green, int blue) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setBackground(new Color(red, green, blue));
    }

    public void clearBackground(Graphics g, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.clearRect(0, 0, width, height);
    }

    public void changeColor(Graphics g, Color c) {
        g.setColor(c);
    }

    public void changeColor(Graphics g, int red, int green, int blue) {
        g.setColor(new Color(red, green, blue));
    }

    public void drawText(Graphics g, int x, int y, String s, int size) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Arial", Font.BOLD, size));
        g2d.drawString(s, x, y);
    }

    void drawLine(Graphics g, int x1, int y1, int x2, int y2) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.draw(new Line2D.Double(x1, y1, x2, y2));
    }

    void drawRectangle(Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.draw(new Rectangle2D.Double(x, y, width, height));
    }

    void drawSolidRectangle(Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.fill(new Rectangle2D.Double(x, y, width, height));
    }

    void drawCircle(Graphics g, int x, int y, double radius) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.draw(new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2));
    }

    void drawSolidCircle(Graphics g, int x, int y, double radius) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.fill(new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2));
    }

    public void setupWindow(int width, int height) {

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.LINE_AXIS));
//        frame.setLayout(new GridLayout(3, 2));
//        frame.setLayout(new CardLayout());
        frame.setSize(width, height);
        frame.setLocation(200, 200);
        frame.setTitle("Snake-Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);
        frame.addKeyListener(this);
        frame.setResizable(false);

//        Insets insets = frame.getInsets();
//        frame.setSize(width + insets.left + insets.right,
//                height + insets.top + insets.bottom);

    }


    public Menu() {
        setupWindow(500, 500);

        //检查是否有Score.txt文件，如果没有则创建，用来记录最高分
        boolean isScoreFileExist = false;
        String basePath = "./src/";
        String[] list0;
        list0 = new File(basePath).list();
        for (int i = 0; i < Objects.requireNonNull(list0).length; i++) {
            if (list0[i].equals("Score.txt")) {
                isScoreFileExist = true;
            }
        }
        if (!isScoreFileExist) {
            String fileName = "./src/Score.txt";
            Path path = Paths.get(fileName);
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
                bufferedWriter.write("0");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public void paintComponent(Graphics g) {
        changeBackgroundColor(g, new Color(102, 186, 101));
        clearBackground(g, 500, 500);
        changeColor(g, blue);
        drawText(g, 100, 100, "Welcome to Snake!", 30);
        changeColor(g, black);
        drawText(g, 100, 150, "Please Choose Game Mode:", 20);
        drawText(g, 120, 250, "- 1. Single Player (Quick start)", 20);
        drawText(g, 120, 300, "- 2. Single Player (Infinite Mode)", 20);
        drawText(g, 120, 350, "- 3. Multi Player", 20);
        changeColor(g, blue);
        try {
            FileReader fd = new FileReader("./src/Score.txt");
            BufferedReader br = new BufferedReader(fd);
            highestScore = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        drawText(g, 120, 400, "Highest Score: " + highestScore, 30);

        Data.label.paintIcon(this, g, 210, 170);
    }

    //添加键盘监听事件
    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == '1') {
            modeChooser(true, true);      //Border
        } else if (e.getKeyChar() == '2') {
            modeChooser(true, false);
        } else if (e.getKeyChar() == '3') {
            modeChooser(false, false);
        }
        //Add new features here: Ranking List, etc.
    }
    public void modeChooser(boolean gameMode, boolean border) {
        JFrame gameFrame = new JFrame("Snake");
        gameFrame.setBounds(10, 10, 900, 720);
        gameFrame.setResizable(false);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.add(new GamePage(gameFrame, gameMode)); //ture: single player
        gameFrame.setVisible(true); //Show the window
        frame.dispose();
        GamePage.border = border;
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
