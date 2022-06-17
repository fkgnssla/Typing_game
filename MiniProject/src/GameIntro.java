import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

public class GameIntro extends JPanel{	//시작화면
	private ImageIcon icon = new ImageIcon("image/city.gif");	//배경이미지
	private Image img = icon.getImage(); // 이미지 객체
	
	private JLabel gameName = new JLabel("바이러스 퇴치 게임");
	private JLabel gameStart = new JLabel("입장하기");
	private JLabel gameOut = new JLabel("종료하기");
	
	private Container b = (Container) getParent();	// 현재 패널이 속한 프레임 알아내기
	private Clip clip;	//입장하기 or 종료하기 클릭시 오디오 재생도구
	
	public GameIntro() {
		setLayout(null);
		
		gameName.setBounds(200, 150, 400, 80);
		gameName.setFont(new Font("맑은 고딕",Font.BOLD,40));
		add(gameName);
		
		gameStart.setFont(new Font("맑은 고딕",Font.BOLD,30));
		gameStart.setBounds(310, 250, 400, 80);
		//입장하기 눌렀을 때
		gameStart.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				
				clip.start();
				new GameFrame2();	//게임을 다시 시작하기위한 구성이 들어있는 함수
			}
			public void mouseEntered(MouseEvent e) {
				gameStart.setFont(new Font("맑은 고딕",Font.BOLD,35));
				gameStart.setForeground(Color.WHITE);
			}
			public void mouseExited(MouseEvent e) {
				gameStart.setFont(new Font("맑은 고딕",Font.BOLD,30));
				gameStart.setForeground(Color.BLACK);
			}
		});
		add(gameStart);
		
		gameOut.setFont(new Font("맑은 고딕",Font.BOLD,30));
		gameOut.setBounds(310, 330, 400, 80);
		//종료하기 눌렀을 때
		gameOut.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				System.exit(JFrame.EXIT_ON_CLOSE);	//게임을 다시 시작하기위한 구성이 들어있는 함수
			}
			public void mouseEntered(MouseEvent e) {
				gameOut.setFont(new Font("맑은 고딕",Font.BOLD,35));
				gameOut.setForeground(Color.WHITE);
			}
			public void mouseExited(MouseEvent e) {
				gameOut.setFont(new Font("맑은 고딕",Font.BOLD,30));
				gameOut.setForeground(Color.BLACK);
			}
		});
		add(gameOut);
		
		
		loadAudioClick();
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0,getWidth(),getHeight() ,this);
	}
	
	
	public void loadAudioClick() {
		try {
			clip= AudioSystem.getClip();
			File audioFile = new File("audio/btn.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			clip.open(audioStream);
		}
		catch (Exception e) {return;}
	}
}