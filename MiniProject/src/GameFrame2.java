import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;


// 게임 프레임
public class GameFrame2 extends JFrame {
	// 떨어지는 라벨 저장할 벡터
   private Vector<JLabel> vector = new Vector<JLabel>();
   private Vector<JLabel> wordVector = new Vector<JLabel>();	//단어를 저장하는 벡터
   private ScorePanel scorePanel = null;			//스코어패널
   private GamePanel gamePanel = null;				//게임패널
   private ControlPanel controlPanel = null;		//입력창패널
   private GameGroundPanel gameGroundPanel = null;	//게임화면패널
   private RankPanel rankPanel = null;				//점수판패널
   private long delay; 								//떨어지는 속도
   private long delaySave;							//지연시간 저장 (눈 item 떄문에)
   private long createDelay;						//바이러스가 생성되는 속도
   private Thread createVirusThread = new CreateVirusThread();	//단어 생성 스레드
   private Thread fallingThread = new FallingThread();			//단어 떨어지는 스레드
   
   private ImageIcon gameStartImg = new ImageIcon("image/play1.png");	//게임시작 버튼이미지
   private ImageIcon gamePauseImg = new ImageIcon("image/pause.png");	//게임중단 버튼이미지
   private JLabel gameStart = new JLabel(gameStartImg);	//게임시작 이미지레이블
   private JLabel gamePause = new JLabel(gamePauseImg);	//게임중단 이미지레이블
   
   private String fileKor = "wordsKor.txt";			//한글단어파일
   private String fileEng = "wordsEng.txt";			//영어단어파일
   
   private boolean flag = false;	//떨어지는 스레드 종료하는 플래그
   
   private int load = 1;			//배경음악 0이면 음소거 1이면 켜짐
   private Clip clip;	//배경음악
   private Clip clip2;	//단어 맞췄을 때
   private Clip clip3;	//단어 못 맞췄을 때
   private Clip clip4;	//아이템 먹었을 때
   private Clip clip5;	//게임오버 됐을 때
   private Clip clip6;	//클릭할 때 (게임시작/게임종료버튼)
   
   public GameFrame2() {
	  setTitle("바이러스 퇴치 게임");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      loadAudioOri();	//기본소리
      loadAudioHit();	//맞췄을 떄
      loadAudioMiss();	//못 맞췄을 때
      loadAudioItem();	//아이템 먹었을 때
      loadAudioGameOver();	//게임오버 됐을 때
      loadAudioClick();	//클릭핳 때
      clip.start();		//배경음악 켜기
      
      scorePanel = new ScorePanel(clip);
      gameGroundPanel = new GameGroundPanel();
      controlPanel = new ControlPanel();
      gamePanel = new GamePanel(); 
      rankPanel = new RankPanel();
      
      rankPanel.setRank();		//랭킹정렬
      rankPanel.showRank();		//랭킹패널 갱신
      
      setSize(800, 700);
      setResizable(false); 		//화면크기 바꾸기 불가
      setVisible(true);
      makeMenu();				//메뉴바 추가 (프레임에 붙이는 것)
  
      splitPane();				//콘텐트 팬 추가
   }
   public void loadAudioOri() {
		try {
			clip= AudioSystem.getClip();
			File audioFile = new File("audio/play.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			clip.open(audioStream);
		}
		catch (Exception e) {
			return;
		}
	}
   public void loadAudioStart() {
		try {
			clip= AudioSystem.getClip();
			File audioFile = new File("audio/playGame.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			clip.open(audioStream);
		}
		catch (Exception e) {return;}
	}
   
   public void loadAudioHit() {
		try {
			clip2= AudioSystem.getClip();
			File audioFile = new File("audio/hit.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			clip2.open(audioStream);
		}
		catch (Exception e) {return;}
	}
   public void loadAudioMiss() {
		try {
			clip3= AudioSystem.getClip();
			File audioFile = new File("audio/miss.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			clip3.open(audioStream);
		}
		catch (Exception e) {return;}
	}
   public void loadAudioItem() {
		try {
			clip4= AudioSystem.getClip();
			File audioFile = new File("audio/item.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			clip4.open(audioStream);
		}
		catch (Exception e) {return;}
	}
   public void loadAudioGameOver() {
		try {
			clip5= AudioSystem.getClip();
			File audioFile = new File("audio/gameOver.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			clip5.open(audioStream);
		}
		catch (Exception e) {return;}
	}
   public void loadAudioClick() {
		try {
			clip6= AudioSystem.getClip();
			File audioFile = new File("audio/btn.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			clip6.open(audioStream);
		}
		catch (Exception e) {return;}
	}
   
   //게임로비 <=> 게임실행 바뀌어질 때 배경음악 바뀌는 경우 끄기버튼 눌렀을 때 계속 음악 꺼져있기위해 
   public void clipReload() {
	   load = scorePanel.clipReload(clip);
	   if(load == 0)	//배경음악 끄기
		   clip.stop();
   }
   
   //컨텐츠팬 대체
   private void splitPane() {
		JSplitPane hPane = new JSplitPane();
		getContentPane().add(hPane, BorderLayout.CENTER);
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		hPane.setDividerLocation(600);
		hPane.setEnabled(false);
		hPane.setLeftComponent(gamePanel);
		
		JSplitPane pPane = new JSplitPane();
		pPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		pPane.setDividerLocation(400);
		pPane.setTopComponent(scorePanel);
		pPane.setBottomComponent(rankPanel);
		hPane.setRightComponent(pPane);
		pPane.setEnabled(false);
	}
   
   //메뉴
   private void makeMenu() {
		JMenuBar mBar = new JMenuBar();			//메뉴바
		setJMenuBar(mBar);						//메뉴바 설정(붙이기)
		JMenu fileMenu = new JMenu("Game");		//메뉴
		fileMenu.addSeparator();				//메뉴아이템사이의 경계선추가
		
		JMenuItem wordPlus = new JMenuItem("단어 추가하기");	//단어 추가 할 수있는 메뉴목록 생성
		wordPlus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String word = JOptionPane.showInputDialog(gameGroundPanel,"추가할 단어를 입력하세요.");
				try {
				FileWriter words = new FileWriter("wordsKor.txt",true);
	   			BufferedWriter bw = new BufferedWriter(words);
	   		    
	            bw.write(word); //버퍼에 데이터 입력
	            bw.newLine(); //개행
	            bw.flush(); //버퍼의 내용을 파일에 쓰기
				}
				catch (Exception e1) {
					return;
				}
			}
		});
		fileMenu.add(wordPlus);
		
		JMenuItem exit = new JMenuItem("나가기");		//게임을 종료하는 메뉴목록 생성
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(JFrame.EXIT_ON_CLOSE);
			}
		});
		
		fileMenu.add(exit);	//메뉴안에 아이템 넣기(리스트)
		mBar.add(fileMenu);						//메뉴바에 메뉴 붙이기
		
		mBar.add(new JLabel("    "));
		mBar.add(gameStart);
		mBar.add(new JLabel("    "));
		mBar.add(gamePause);
		
		JLabel level = new JLabel("   난이도  ");
		level.setFont(new Font("함초롱돋움",Font.PLAIN, 20));
		mBar.add(level);
		
		JComboBox j = new JComboBox();
		j.addItem(new String("쉬움"));
		j.addItem(new String("보통"));
		j.addItem(new String("어려움"));
		
		mBar.add(j);
		mBar.add(new JLabel("                                                                                              "
				+ "                              "));
		ImageIcon wifi = new ImageIcon("image/signal.png");
		mBar.add(new JLabel(wifi));
		
		gameStart.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				clip6.start();
				clip6.setFramePosition(0);
				clip.close();	//재생되던 음악 멈추고 삭제
				loadAudioStart();	//게임실행배경음악
				clip.start();		//게임실행배경음악 켜기
				clipReload();	//scorePanel에게 clip갱신된 것 보내기
				String str = j.getSelectedItem().toString();
				scorePanel.setLevel(str);	//스코어 패널의 난이도레이블 갱신
				flag = false;	//떨어지는 스레드 종료하지않기위함
				if (str.equals("쉬움") == true) {	//골라진 것
					delaySave = delay = 400;
					createDelay = 4000;
					scorePanel.setScore(10); 	//점수 설정
				}
				else if (str.equals("보통") == true) {
					delaySave = delay = 250;
					createDelay = 2000;
					scorePanel.setScore(20);	//점수 설정
				}
				else {
					delaySave = delay = 100;
					createDelay = 1500;
					scorePanel.setScore(30);	//점수 설정
				}
				//System.out.println(delay + "," + createDelay);
				flag = false;	//플래그 초기회
			    createVirusThread.start();
		        fallingThread.start();
			}
		});
		gamePause.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				clip6.start();
				clip6.setFramePosition(0);
				clip5.start();
		   		clip5.setFramePosition(0);
				createVirusThread.interrupt();
				flag = true;	//떨어지는 스레드 종료 플래그
				gameOver();
				reStartPack();	//게임을 다시 시작하기위한 구성이 들어있는 함수
				clip.close();	//재생되던 음악 멈추고 삭제
				//로비 배경음악 켜기
				loadAudioOri();
				clip.start();
				clipReload();
			}
		});
	}
   
   //게임을 다시 시작하기위한 구성이 들어있는 메소드
   void reStartPack() {		
       createVirusThread = new CreateVirusThread();
       fallingThread = new FallingThread();
       
       //화면의 바이러스 + 벡터안의 자료제거
	   int  count = vector.size();
       for(int i=0;i<count;i++) {
    	   //JLabel label = (JLabel)wordVector.get(i).getParent();	//바이러스이미지레이블을 가리킨다
    	   JLabel label = vector.get(0);
    	   gameGroundPanel.remove(label); //화면에서 이미지(이 안에 단어레이블도 있음)제거
    	   vector.remove(0);		 //바이러스이미지레이블 벡터에서 단어제거
    	   wordVector.remove(0);	 //단어레이블 벡터에서 단어제거
       }
       gameGroundPanel.repaint(); // 화면갱신
       scorePanel.reStart();
   }
   
   //게임 로비 패널
   class GamePanel extends JPanel {
       public GamePanel() {
         setOpaque(false);
         setLayout(new BorderLayout());
         add(gameGroundPanel,BorderLayout.CENTER);
         add(controlPanel,BorderLayout.SOUTH);
      }
 
   }
   //게임 실행 패널
   class GameGroundPanel extends JPanel {
	    //ImageIcon icon = new ImageIcon("image/ab.gif");
	   ImageIcon icon = new ImageIcon("image/k.jpg");
	   	Image img = icon.getImage(); // 이미지 객체
	   	
	   	@Override
	   	public void paintComponent(Graphics g) {
	   		super.paintComponent(g);
	   		
	   		g.drawImage(img, 0, 0, getWidth(), getHeight(),this);	//배경그리기
	   		
	   	}
	  public GameGroundPanel() {
		  setLayout(null);
	      setOpaque(false);
	  }
   }
   //입력창패널
   class ControlPanel extends JPanel {
	    private Color color = new Color(49, 98, 199);
		private JTextField input = new JTextField(15); // 사용자가 단어를 입력하는 창
	
		private Timer m_timer = new Timer();	//타이머의 기능을 수행하는 클래스
		private TimerTask m_task = new TimerTask() {	//타이머가 끝나고 수행되어할 내용을 작성하는 클래스
			@Override
			public void run() {
				delay = delaySave;	//delay를 원상복구 시킨다
				m_timer.cancel();	//타이머 종료
				m_timer = new Timer();	//새로운 타이머 넣어놓기
			}
		}; 
		
		public boolean matchWord(String text) {		//입력한 단어와 바이러스들의 단어를 비교하는 멤버함수
			for(int i=0;i<wordVector.size();i++) {
				if(text.equals(wordVector.get(i).getText())) {		//입력한 단어가 있으면
					//효과음 지정
					if(text.equals("마스크") || text.equals("눈")) {	//아이템 먹을 시 소리나도록
						clip4.start();
						clip4.setFramePosition(0);
					}
					else {		//바이러스 퇴치할 때 소리나도록
						clip2.start();
						clip2.setFramePosition(0);
					}
					
					if(text.equals("마스크")) {		//마스크아이템 먹으면 => 입력한 단어가 mask이면
						scorePanel.increaseMask();
					}
					if(text.equals("눈")) {	//타이머아이템 먹으면 (중복되게 먹으면 소용 X / 2개있으면 먼저 먹은거 효과 끝나고 먹어야한다)
						delay = 600;
						m_timer.schedule(m_task, 5000);		//5초뒤에 delay 초기화
						m_task = new TimerTask() {	//m_task 재 할당해주기
							@Override
							public void run() {
								delay = 200;	//떨어지는 속도 delay 복구
								m_timer.cancel();
								m_timer = new Timer();
							}
						}; 
					}
					JLabel label = (JLabel)wordVector.get(i).getParent();	//바이러스이미지레이블을 가리킨다
					gameGroundPanel.remove(label); //화면에서 이미지(이 안에 단어레이블도 있음)제거
					vector.remove(i);		 //바이러스이미지레이블 벡터에서 단어제거
					wordVector.remove(i);	 //단어레이블 벡터에서 단어제거
					return true;
				}
			}
			
			//입력한 단어가 없는경우 (오타난 경우)
			clip3.start();				//오타냈을 때 나는 소리
			clip3.setFramePosition(0);
			input.setText("");
			return false;
		}
		//생성자
		public ControlPanel() {
			this.setLayout(new FlowLayout());
			this.setBackground(Color.LIGHT_GRAY);
			add(input);
			this.setOpaque(true);
			this.setBackground(color);
			
			input.setFont(new Font("Aharoni", Font.PLAIN, 20));	//입력창의 폰트크기 설정
			input.addActionListener((ActionListener) new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {	//입력창에 Enter키 누르면
					JTextField tf = (JTextField)e.getSource();		//input 텍스트필드르 가리킴
					String text = tf.getText();
					if(text.equals("그만"))
						System.exit(0); // 프로그램 종료
				
					if(vector.size()<=0)	//바이러스를 저장하는 벡터가 비었다면 아무일도 일어나지 않는다
						return;
					boolean match = matchWord(text);	//입력한 단어와 내려오는 단어와 비교
					if(match == true) {
						tf.setText(""); // 입력창에 입력된 텍스트 지우기
						 gameGroundPanel.repaint(); // 화면갱신
				         scorePanel.increase();
					}
					else {								//틀렸다면
						//입력한 단어 없으면 우는 이모티콘으로 바꾸기
						scorePanel.setImotion();				
					}
				}
			});
		}
	}

   
   // 바이러스 생성 쓰레드
   class CreateVirusThread extends Thread {
	  private int randomX;
	  private JLabel virus;
	  private JLabel word;
	  private Words words = new Words("wordsKor.txt");
	  private ImageIcon virusImg1 = new ImageIcon("image/b1.png");	//바이러스 이미지1
	  private ImageIcon virusImg2 = new ImageIcon("image/b2.png");	//바이러스 이미지2
	  private ImageIcon virusImg3 = new ImageIcon("image/b3.png");	//바이러스 이미지3
	  private ImageIcon snowImg = new ImageIcon("image/snow.png");	//눈 이미지
	  private ImageIcon maskImg = new ImageIcon("image/mask.png");	//마스크 이미지

	   @Override
	   public void run() {
	      while (true) {
	         String str = words.getRandomWord(); // 랜덤한 단어 받기
	         
	         int r = (int)(Math.random()*100);	 //바이러스 이미지 선택
	         if(0<=r && r<30) virus = new JLabel(virusImg1);
	         else if(30<=r && r<60) virus = new JLabel(virusImg2);
	         else if(60<=r && r<90) virus = new JLabel(virusImg3);
	         else  if(90<=r && r<95){	//5% 확률로 타이머아이템이 나올 수 있다 (5초동안 바이러스 속도 감소)
	        	 virus = new JLabel(snowImg);
	        	 str = new String("눈");
	         }
	         
	         else {		//5% 확률로 마스크아이템이 나올 수 있다 (마스크개수 1개 증가)
	        	 virus = new JLabel(maskImg);
	        	 str = new String("마스크");
	         }	
	         
	         word = new JLabel(str);
	         word.setHorizontalAlignment(JLabel.CENTER); // JLabel 가운데정렬
	         word.setSize(virusImg1.getIconWidth(), virusImg1.getIconHeight());
	         word.setFont(new Font("맑은 고딕", Font.BOLD, 24));
	         
	         //아이템은 단어색 검정색 바이러스는 흰색
	         if(90<=r) word.setForeground(Color.BLACK);
	         else	word.setForeground(Color.WHITE);
	         word.setOpaque(false);
	         
	         virus.add(word);	//바이러스이미지에 단어레이블 넣기
	         
	         Dimension size1 = virus.getPreferredSize();	//virus JLabel의 사이즈
	
	         randomX = (int) (Math.random() * gameGroundPanel.getWidth());
	         while (randomX + size1.getWidth() >= gameGroundPanel.getWidth()) {	//단어레이블이 화면 밖으로 나가는 것을 방지
	             //System.out.println("넘김");
	             randomX = (int) (Math.random() * gameGroundPanel.getWidth());
	          }
	
	         virus.setBounds(randomX, 15, virusImg1.getIconWidth(), virusImg1.getIconHeight()); // 바이러스이미지 레이블 생성(x,y,w,h)
	         
	         vector.add(virus); // 생성된 바이러스(이미지)레이블 벡터에 저장
	         wordVector.add(word); // 생성된 단어레이블 벡터에 저장
	         
	         gameGroundPanel.add(virus);
	         gameGroundPanel.repaint(); // 화면갱신
	
	         try {
	            sleep(createDelay);
	         } catch (InterruptedException e) {
	            e.printStackTrace();
	            return;
	         }
	      }
	   }
	}
   
   	//게임오버 됐을 때 메소드
   	public void gameOver() {
   		clip5.start();
   		clip5.setFramePosition(0);
   		String [] answer = {"게임종료", "한 판 더하기"};
   		
   		String name = JOptionPane.showInputDialog(gameGroundPanel,"기록할 닉네임을 입력하세요."); 
   		try {
   			FileWriter rank = new FileWriter("Rank.txt",true);
   			BufferedWriter bw = new BufferedWriter(rank);
   		   
            bw.write(name + " "); //버퍼에 데이터 입력
            bw.write(Integer.toString(scorePanel.getScore()));
            bw.newLine();
            bw.flush(); //버퍼의 내용을 파일에 쓰기
            //랭크 갱신하기
            rankPanel.setRank();
            rankPanel.showRank();
   		} catch (Exception e) {
   	            e.getStackTrace();
   		}
   		
   		int sel = JOptionPane.showOptionDialog(gameGroundPanel, "닉네임:" + name + "   점수: " + scorePanel.getScore()+ "점",
   				"Game Over!!", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, answer, null);
   		if(sel == 0) { // "예" 선택. 창 닫는다
   			System.exit(JFrame.EXIT_ON_CLOSE);
  		}
   		else if(sel == 1) { // "다시시작" 선택. 현재 프레임 닫고 새 프레임 연다
   			reStartPack();	//게임을 다시 시작하기위한 구성이 들어있는 함수
   		}
   		
   		clip.close();	//재생되던 음악 멈추고 삭제
		loadAudioOri();	//기본음악 
		clip.start();	//기본음악 재생
		clipReload();
   	}
	// 떨어지기 쓰레드
	class FallingThread extends Thread {   
	   // 떨어지기 메소드
	   void move(JLabel virus, int i) {	// label은 단어레이블을 가진 이미지레이블이다
	      int y = virus.getY() + 5;
	      String str = wordVector.get(i).getText();
	      ImageIcon demon = new ImageIcon("image/demon.png");	//입안에 바이러스 들어갈 때 바뀌는 이미지
	      
	      //바이러스일때만 악마로 이미지 바뀐다
	      if(y==455 && !(str.equals("마스크") || str.equals("눈"))) {
	    	  virus.setIcon(demon);
	    	  wordVector.get(i).setText("");
	      }
	      
	      //입속으로 떨어지면
	      if(y>455) {	
	    	  vector.remove(i);		//바이러스이미지레이블 벡터에서 제거
	    	  wordVector.remove(i);	//단어레이블 벡터에서 제거
	    	  gameGroundPanel.remove(virus);	//화면에서 바이러스이미지레이블 지우기
	    		  
	    	  if(str.equals("마스크") || str.equals("눈")) {	//virus가 마스크 또는 타이머면 그냥 지나간다
	    		  gameGroundPanel.repaint();
	    		  return;
	    	  }
	    	  if(scorePanel.isMask() == true) 			//마스크로 방어할 경우 마스크 개수만 깎인다
	    		  scorePanel.decreaseMask();
	    	  else {
	    		  if(scorePanel.heartDecrease() == 0) {	//생명 하나 감소시키고 생명이 다 떨어졌을때
	    			  createVirusThread.interrupt();	//생성스레드 종료
	    			  flag = true;	//떨어지는 스레드 종료 플래그 활성화
	    			  gameOver();	//게임오버시 시작하는 메소드
	    		  }
	    	  }
	    	  gameGroundPanel.repaint();
	    	  return;
	      }
	      virus.setBounds(virus.getX(), y, virus.getWidth(), virus.getHeight());
	   }
	
	   @Override
	   public void run() {
	      while (true) {
	         // 벡터에서 바이러스 하나씩 꺼내서 아래로 이동 시키기
	         for (int i = 0; i < vector.size(); i++) {
	            move(vector.get(i),i); // 레이블값과 레이블의 인덱스값인 인수로 전송
	         }
	         try {
	            sleep(delay);
	            if(flag == true)
	            	return;
	         } catch (InterruptedException e) {
	            e.printStackTrace();
	            System.out.println("떨어지는 스레드 종료");
	            return;
	         }
	      }
	   }
	}
}


//txt파일을 읽고 벡터에 저장하고 벡터로부터 랜덤하게 단어를 추출하는 클래스
class Words {
	private Vector<String> wordVector = new Vector<String>();
	public Words(String fileName) {
		try {
			Scanner scanner = new Scanner(new FileReader(fileName));
			while(scanner.hasNext()) { // 파일 끝까지 읽음
				String word = scanner.nextLine(); // 한 라인을 읽고 '\n'을 버린 나머지 문자열만 리턴
				wordVector.add(word); // 문자열을 벡터에 저장
			}
			scanner.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("file not found error");
			System.exit(0);
		}
	}
	
	public String getRandomWord() {	//벡터에 저장된 단어 중 랜덤하게 하나를 반환하는 함수
		final int WORDMAX = wordVector.size(); // 총 단어의 개수
		int index = (int)(Math.random()*WORDMAX);
		while(wordVector.get(index).length()>8) {
			index = (int)(Math.random()*WORDMAX);
		}
		return wordVector.get(index);
	}	
}