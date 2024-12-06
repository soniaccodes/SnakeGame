import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {

    //declaring variables and arrays
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25; //how big the objects in the game are
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE; // calculates how many objects can fit on the screen
    static final int DELAY = 75; // delay for timer. higher the number the slower it runs

    // two arrays (called x and y) these arrays will hold all of the coordinates for all of the body
    // parts of our snake including the head
    final int x[] = new int[GAME_UNITS]; // x coordinates
    final int y[] = new int[GAME_UNITS]; //y coordinates
    int bodyParts = 6; //snake will start with 6 body parts
    int applesEaten = 0;
    int appleX; // x-coordinate of apple. note: will be random each time
    int appleY;
    char direction = 'R'; //snake will start going Right
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel() { //constructor for GamePanel which initializes objects
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }

    public void startGame() {
        newApple();
        running = true; // its false to begin with
        timer = new Timer(DELAY, this); // this will dictate how fast the game is running (we also put "this" as an argument because we are using the action listener interface
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }

    public void draw(Graphics g) {

        if (running) {
            //apple
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE); //this is how large the apple is
            //this for loop draws lines across our game panel so it becomes like a grid
            g.setColor(Color.gray); //sets the color for the grid lines
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            //head and body of snake
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) { //head of snake
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else { //body of snake
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }

        } else {
            gameOver(g);
        }
    }

    public void newApple() { //generates coordinates of a new apple whenever this method is called (when we begin the game or score a point)
        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE; // x-coordinate of apple
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;

    }

    public void move() { //this method allows the snake to move
        // for loop reads as follows:
        // set i to the number of body parts we have for our snake
        //continue this as long as i is greater than 0
        //then we will decrement i by 1 after each iteration of this for loop
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1]; //shifting coordinates in the array over by one
            y[i] = y[i - 1];
        }
        //switch will change the direction of where the snake is headed
        // each case is all possible directions
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE; //will go to next position - UP
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE; //Down
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE; //Left
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE; //Right
                break;
        }
    }

    public void checkApple() {
        //examine coordinates of snake and of apple
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions() {
        // checking to see if head collides with body. Need to iterate through all of the body parts of the snake
        for (int i = bodyParts; i > 0; i--) { //reads as: iterate through bodyParts as long as i is greater than 0, for each iteration decrement by 1
            if ((x[0] == x[i]) && (y[0] == y[i])) { //x[0] is head if true then head collided with body
                running = false; //game over method
            }
        }
        //check if head touches left border
        if (x[0] < 0) {
            running = false;
        }
        // check if head touches right border
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }
        //check if head touches top border
        if (y[0] < 0) {
            running = false;
        }
        //check if head touches bottom border
        if (y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        if (!running) {
            timer.stop();

        }
    }

    public void gameOver(Graphics g) {
        //game over text
        g.setColor(Color.red);
        g.setFont(new Font("TimesRoman", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint(); //if game is not running call repaint method

    }


    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}


