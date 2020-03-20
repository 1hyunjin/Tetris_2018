package tetris;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartScreen extends JFrame {
	
	boolean isStarted = false;
	boolean isPaused = false;
	//배경음악 생성.
	Music background = new Music();
	//image button 만들기
	ImageIcon img1 = new ImageIcon("res/images/tetrisname.png");
	ImageIcon img2 = new ImageIcon("res/images/button1.png"); 
	ImageIcon img3 = new ImageIcon("res/images/button2.png"); 
	ImageIcon img4 = new ImageIcon("res/images/pressbutton1.png");
	ImageIcon img5 = new ImageIcon("res/images/pressbutton2.png");
	ImageIcon img6 = new ImageIcon("res/images/On.png");	
	ImageIcon img7 = new ImageIcon("res/images/Off.png");
	ImageIcon img8 = new ImageIcon("res/images/pressOn.png");
	ImageIcon img9 = new ImageIcon("res/images/pressOff.png");
	//생성
	JFrame jf = new JFrame();
	JLabel jl = new JLabel(img1);
	JButton one = new JButton(img2);
	JButton two = new JButton(img3);
	JButton onButton = new JButton(img6);
	JButton offButton = new JButton(img7);
	
	JPanel jPanel1 = new JPanel();
	JPanel jPanel2 = new JPanel();
	JPanel jPanel3 = new JPanel();
	
	public StartScreen() {
		
		background.MusicPlay("res/music/start.wav");
		
		getContentPane().setBackground(Color.BLACK);
		setTitle("Tetris");
		setSize(400, 600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		//button image 알맞게 맞추기
		one.setBorderPainted(false);
		one.setFocusPainted(false);
		one.setContentAreaFilled(false);
		two.setBorderPainted(false);
		two.setFocusPainted(false);
		two.setContentAreaFilled(false);
		onButton.setBorderPainted(false);
		onButton.setFocusPainted(false);
		onButton.setContentAreaFilled(false);
		offButton.setBorderPainted(false);
		offButton.setFocusPainted(false);
		offButton.setContentAreaFilled(false);
		
		
		//버튼 눌렀을 때 이미지 버튼 변화
		one.setPressedIcon(img4);
		two.setPressedIcon(img5);
		onButton.setPressedIcon(img8);
		offButton.setPressedIcon(img9);
		
		//border,flow layout 만들기
		setLayout(new BorderLayout(0,50));
		FlowLayout f = new FlowLayout(FlowLayout.CENTER,60,50); //button 간의 거리 조정.
		
		//borderLayout에 위 공백 생성
		jPanel1.setBorder(BorderFactory.createEmptyBorder(80, 0, 0, 0));
		
		add(jPanel1, BorderLayout.NORTH);
		jPanel1.add(jl, BorderLayout.SOUTH);
		jPanel1.setBackground(Color.BLACK);
		
		add(jPanel2, BorderLayout.CENTER);
		jPanel2.setBackground(Color.BLACK);
		jPanel2.setLayout(f);//flowLayout으로 jpanel2 button 2개 놓기
		jPanel2.add(one);
		jPanel2.add(two);
		jPanel2.add(onButton);
		jPanel2.add(offButton);
		//validate();	
		

		setLocationRelativeTo(null);
		
		//button click -> background sound on/off.
		onButton.addMouseListener(new OnMouse());
		offButton.addMouseListener(new OffMouse());
		
		//button click -> change to another frame.
		one.addMouseListener(new ToTetris());
		two.addMouseListener(new ToBattle());
	}
	
	//MouseAdapter
	class OffMouse extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			
			background.MusicStop();
		}
		public void mouseReleased(MouseEvent e) {
			
		}
	}
	
	class OnMouse extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			background.MusicStop();
			background.MusicPlay("res/music/start.wav");
		}
		public void mouseReleased(MouseEvent e) {
			
		}
	}
	
	//화면 전환
	class ToTetris extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			
			background.MusicStop();
			//테트리스
			Tetris game = new Tetris(1);
			game.setLocationRelativeTo(null);//윈도우 창을 화면의 가운데에 띄우는 역할.
			game.setVisible(true);
			setVisible(false);
		}
		public void mouseReleased(MouseEvent e) {
			
		}
	}
	
	//2인용 화면전환
	class ToBattle extends MouseAdapter{
		
		public void mousePressed(MouseEvent e) {
			Music music = new Music();
			setVisible(false);
			background.MusicStop();
			Tetris bat = new Tetris(2);
			bat.setLocationRelativeTo(null);
			bat.setVisible(true);
			setVisible(false);
		}
		public void mouseRelased(MouseEvent e) {
			
		}
	}
	
	public static void main(String[] args) {
		new StartScreen();
	}
}