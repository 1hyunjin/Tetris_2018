package tetris;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StateBoard extends JPanel {
	
	JLabel statusbar;
	JLabel stage;
	JLabel sStage = new JLabel();
	JLabel score = new JLabel();
	
	public StateBoard() {
		score = new JLabel("<SCORE>");
		statusbar = new JLabel("0");
		sStage = new JLabel("<스테이지>");
		stage = new JLabel("1");
		score.setForeground(Color.WHITE);
		statusbar.setForeground(Color.WHITE);
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		add(score);
		add(statusbar);
		add(sStage);
		add(stage);
		setBackground(Color.lightGray);
	}
	
	public JLabel getStatusBar() {
		return statusbar;
	}
	
	public JLabel getStage() {
		return stage;
	}
}