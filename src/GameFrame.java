import javax.swing.JFrame;

public class GameFrame extends JFrame {

    GameFrame() {

        GamePanel panel = new GamePanel();

        this.add(panel);
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack(); // when adding components to JFrame, this pack function will make sure components fit in JFrame
        this.setVisible(true);
        this.setLocationRelativeTo(null); // makes it so application appears at the center of the screen
    }
}
