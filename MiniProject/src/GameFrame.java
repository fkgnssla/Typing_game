import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

public class GameFrame extends JFrame {
	private GameIntro gameIntro = new GameIntro();	//게임인트로패널
	
	public GameFrame() {
		setTitle("바이러스 퇴치 게임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		c.add(gameIntro);
		setSize(800,700);
		setResizable(false);	//화면 조정불가
		setVisible(true);
	}
}