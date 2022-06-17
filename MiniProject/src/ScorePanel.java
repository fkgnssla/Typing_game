import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel {
	private int score = 0;
	private int heart = 5;	//생명
	private int combo;		//콤보
	private JLabel textLabel = null;
	private JLabel scoreLabel = null;
	private JLabel levelLabel = null;
	private JLabel levelLabel2 = null;
	private JLabel heartLabel[] = new JLabel[heart];
	private JLabel maskLabel[] = new JLabel[3];	//마스크 3개까지 보유가능
	private ImageIcon heartImg = new ImageIcon("image/heart.png");		//생명 이미지
	private ImageIcon maskImg = new ImageIcon("image/maskCount.png");	//마스크아이템 크기를 줄인 마스크이미지
	private ImageIcon happyImg = new ImageIcon("image/happy.png");		//행복한 이모티콘(4회이상 연속 맞췄을 때 활성화)
	private JLabel happyLabel = new JLabel(happyImg);		
	private ImageIcon smileImg = new ImageIcon("image/smile.png");		//웃는 이모티콘 (기본)
	private JLabel smileLabel = new JLabel(smileImg);	
	private ImageIcon sadImg = new ImageIcon("image/sad.png");			//우는 이모티콘(오타내거나 단어가 입안에 들어온 경우 활성화)
	private JLabel sadLabel = new JLabel(sadImg);
	private ImageIcon icon = new ImageIcon("image/sky.png");	//배경화면 이미지
	private Image img = icon.getImage(); // 이미지 객체
	private int mask;	//마스크 개수
	private int c=0;	//정답 맞힌 개수로 이모티콘 조절하는 변수
	private JButton soundOn = new JButton("배경음악 켜기");
	private JButton soundOff = new JButton("배경음악 끄기");
	private Clip clip = null;
	private int load=1;		//1이면 배경음악 on  0이면  off
	private int plusScore;	//난이도 쉬움:10점 보통:20점 어려움: 30점
	
	public ScorePanel(Clip clip) {
		this.clip = clip;
		mask = 0;
		
		this.setBackground(Color.lightGray);
		setLayout(null);		
		
		textLabel = new JLabel("점수:");
		textLabel.setForeground(Color.RED);
		textLabel.setFont(new Font("함초롱돋움",Font.BOLD,23));
		scoreLabel = new JLabel(Integer.toString(score));
		scoreLabel.setForeground(Color.WHITE);
		scoreLabel.setFont(new Font("함초롱돋움",Font.BOLD,23));
		levelLabel = new JLabel("난이도: ");
		levelLabel.setForeground(Color.BLUE);
		levelLabel.setFont(new Font("함초롱돋움",Font.BOLD, 23));
		levelLabel2 = new JLabel("미설정");
		levelLabel2.setForeground(Color.WHITE);
		levelLabel2.setFont(new Font("함초롱돋움",Font.BOLD, 23));
		
		
		textLabel.setBounds(10,140,150,20);
		add(textLabel);
		scoreLabel.setBounds(100,140,180,20);
		add(scoreLabel);
		levelLabel.setBounds(10,180,150,20);
		add(levelLabel);
		levelLabel2.setBounds(100,180,150,20);
		add(levelLabel2);
		smileLabel.setBounds(10,5,120,120);
		add(smileLabel);
		
		//생명그리기
		for(int i=0;i<heart;i++) {	
			heartLabel[i] = new JLabel(heartImg);
			heartLabel[i].setBounds(5+(i*30),220,heartImg.getIconWidth(),heartImg.getIconHeight());
			heartLabel[i].setOpaque(false);	 //배경없애기
			add(heartLabel[i]);
		}
		
		//마스크그리기
		for(int i=0;i<3;i++) {		
			maskLabel[i] = new JLabel(maskImg);
			maskLabel[i].setBounds(5+(i*30),260,maskImg.getIconWidth(),maskImg.getIconHeight());
			maskLabel[i].setOpaque(false);	 //배경없애기
			maskLabel[i].setVisible(false);		//일단은 보이지 않게(마스크가 없으니까)
			add(maskLabel[i]);
		}
		
		//배경음악 켜기 버튼
		soundOn.setBounds(10,305,150,40);
		soundOn.setFont(new Font("맑은 고딕", Font.BOLD,15));
		add(soundOn);
		soundOn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clip.start();
				load=1;
			}
		});
		
		//배경음악 끄기 버튼
		soundOff.setBounds(10,355,150,40);
		soundOff.setFont(new Font("맑은 고딕", Font.BOLD,15));
		add(soundOff);
		soundOff.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clip.stop();
				load=0;
			}
		});
		
	}
	
   	
   	@Override
   	public void paintComponent(Graphics g) {
   		super.paintComponent(g);
   		
   		g.drawImage(img, 0, 0, getWidth(), getHeight(),this);	//배경그리기
   		
   	}
	public void increase() {
		System.out.println(c);
		if(c==3) {//지금 막 행복한 이모티콘으로 바뀌었을 때
			smileLabel.setIcon(happyImg);
			score += plusScore;
		}//지금 시점 이모티콘이 행복한 이모티콘일때
		else if(c>3) {
			score += (plusScore*2);
		}
		else {	//행복이모티콘일 때 점수 20점씩 증가
			smileLabel.setIcon(smileImg);
			score += plusScore;
		}
		scoreLabel.setText(Integer.toString(score));
		c++;
	}
	/*public void decrease() {
		if(score <= 0)	//0점에서 감점당하면 그대로 (최하점이 0점)
			return;
		score -= 10;
		scoreLabel.setText(Integer.toString(score));
		//heartDecrease();
		
	}
	//생명 증
	void heartIncrease() { 
		heart++;
	}*/
	//생명감소
	int heartDecrease() { 
		heart--;
		c=0;	//4회이상 연속으로 맞춘 콤보 초기회
		smileLabel.setIcon(sadImg);
		switch(heart) {
		case 4:
			heartLabel[4].setVisible(false); break;
		case 3:
			heartLabel[3].setVisible(false); break;
		case 2:
			heartLabel[2].setVisible(false); break;
		case 1:
			heartLabel[1].setVisible(false); break;
		case 0:
			heartLabel[0].setVisible(false); break;
		}
		return heart;	//생명의 개수가 0이면 게임종료
	}
	
	//마스크가 남아있으면 참 없으면 거짓
	boolean isMask() {	
		if(mask>0) return true;
		else return false;
	}
	
	//마스크 추가
	void increaseMask() {
		if(mask>=3) return;	//마스크가 꽉 찼으면 더 이상 받지 않는다.
		mask++;
		
		switch(mask) {
		case 3:
			maskLabel[2].setVisible(true); break;
		case 2:
			maskLabel[1].setVisible(true); break;
		case 1:
			maskLabel[0].setVisible(true); break;
		}
	}
	
	//마스크 감소
	void decreaseMask() { 
		mask--;
		
		switch(mask) {
		case 2:
			maskLabel[2].setVisible(false); break;
		case 1:
			maskLabel[1].setVisible(false); break;
		case 0:
			maskLabel[0].setVisible(false); break;
		}
	}
	
	//패널에서 이모티콘 설정하기위한 메소드
	void setImotion() {
		c = 0;
		smileLabel.setIcon(sadImg);
	}
	
	//다시 시작하기위한 초기화메소드
	void reStart() {
		heart = 5;
		mask =0;
		score = 0;
		c=0;	//4회이상 연속으로 맞춘 콤보 초기회
		scoreLabel.setText("0");
		smileLabel.setIcon(smileImg);
		
		// 생명 채우기
		for(int i=0;i<5;i++) {	
			heartLabel[i].setVisible(true);
		}
		// 마스크 없애기
		for(int i=0;i<3;i++) {	
			maskLabel[i].setVisible(false);
		}
		levelLabel2.setText("미설정");
	}
	
	//점수 반환
	int getScore() {
		return score;
	}
	
	//난이도 레이블 설정
	void setLevel(String i) {
		if(i.equals("쉬움"))
			levelLabel2.setText("쉬움");
		else if(i.equals("보통"))
			levelLabel2.setText("보통");
		else
			levelLabel2.setText("어려움");
	}
	
	//게임로비 <=> 게임실행 바뀌어질 때 배경음악 바뀌는 경우 끄기버튼 눌렀을 때 계속 음악 꺼져있기위해 
	int clipReload(Clip clip) {
		this.clip = clip;
		
		//이벤트 재설정
		add(soundOn);
		soundOn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clip.start();	//재생시작
				load=1;	//배경음악켜짐
			}
		});
		add(soundOff);
		soundOff.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clip.stop();	//재생종료
				load=0;	//배경음악꺼짐
			}
		});
		return load;
	}
	
	//난이도에 따라 올라가는 점수 설정
	void setScore(int plusScore) {
		this.plusScore = plusScore;
	}
}