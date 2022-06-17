import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RankPanel extends JPanel{
	private JLabel name[] = new JLabel[8];	//닉네임
	private JLabel score[] = new JLabel[8];	//점수
	private JLabel titleLabel = new JLabel("Top 8");
	private JLabel rankLabel = new JLabel("순위");
	private JLabel nameLabel = new JLabel("닉네임");
	private JLabel scoreLabel = new JLabel("점수");
	private Vector<Player> playerRank;   //플레이어들의 닉네임과 점수를 모아놓는 벡터
	private Player player = null;
	
	public RankPanel() {
		setLayout(null);
		
		//Top8 레이블추가
		titleLabel.setForeground(Color.RED);
		titleLabel.setFont(new Font("맑은 고딕", Font.BOLD,18));
		titleLabel.setSize(120,20);
		titleLabel.setLocation(60, 5);
		add(titleLabel);
		//순위타이틀 레이블추가
		rankLabel.setForeground(Color.ORANGE);
		rankLabel.setFont(new Font("맑은 고딕", Font.BOLD,14));
		rankLabel.setSize(120,20);
		rankLabel.setLocation(10, 40);
		add(rankLabel);
		//닉네임타이틀 레이블추가
		nameLabel.setForeground(Color.ORANGE);
		nameLabel.setFont(new Font("맑은 고딕", Font.BOLD,14));
		nameLabel.setSize(120,20);
		nameLabel.setLocation(45, 40);
		add(nameLabel);
		//점수타이틀 레이블추가
		scoreLabel.setForeground(Color.ORANGE);
		scoreLabel.setFont(new Font("맑은 고딕", Font.BOLD,14));
		scoreLabel.setSize(120,20);
		scoreLabel.setLocation(125, 40);
		add(scoreLabel);
		
		//닉네임 레이블추가
		for(int i=0;i<8;i++) {
			name[i] = new JLabel(Integer.toString(i+1) + "      " + "null");
			name[i].setFont(new Font("맑은 고딕",Font.BOLD,14));
			name[i].setSize(120,20);
			name[i].setLocation(10, 60 +i*15);
			add(name[i]);
		}
		//점수 레이블추가
		for(int i=0;i<8;i++) {
			score[i] = new JLabel("0");
			score[i].setForeground(Color.WHITE);
			score[i].setFont(new Font("맑은 고딕",Font.BOLD,14));
			score[i].setSize(150,20);
			score[i].setLocation(125, 60 +i*15);
			add(score[i]);
		}
	}
	ImageIcon icon = new ImageIcon("image/sv.png");	//배경이미지
   	Image img = icon.getImage(); // 이미지 객체
   	
   	@Override
   	public void paintComponent(Graphics g) {
   		super.paintComponent(g);
   		
   		g.drawImage(img, 0, 0, getWidth(), getHeight(),this);	//배경그리기
   		
   	}
   	
   	//Rank.txt파일에서 닉네임과 점수 읽어와 벡터에 저장 후 정렬
	void setRank() {
		try {
			FileReader rw = new FileReader("Rank.txt");
            BufferedReader br = new BufferedReader( rw );
            playerRank = new Vector<Player>();
          //읽을 라인이 없을 경우 br은 null을 리턴한다.
            String readLine = null ;
            while( ( readLine =  br.readLine()) != null ){
            	System.out.println(readLine);
                String s[] = readLine.split(" ");
                player = new Player(s[0],Integer.parseInt(s[1]));
                playerRank.add(player);
            }
            Collections.sort(playerRank);	//점수대로 오름차순 정렬
		}
        catch (Exception e) {
            System.out.println(e);
        }
	}
	
	//닉네임레이블, 점수레이블 갱신
	void showRank() {
		Iterator<Player> it = playerRank.iterator();
		
		for(int i=0;i<8;i++) {
			if(!it.hasNext()) break;
			Player p = it.next();
			name[i].setText(Integer.toString(i+1) + "      " + p.getName());
			score[i].setText("" + p.getScore());
		}
	}
}
