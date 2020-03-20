package tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TetrisBoard extends JPanel {

	public MainBoard mMainBoard;
	public SideBoard mSideBoard;
	private StateBoard mStateBoard;
	private int level;

	public TetrisBoard() {

		mStateBoard = new StateBoard();
		mMainBoard = new MainBoard(this);
		mSideBoard = new SideBoard();
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gbc.gridy = 0;
		gbc.gridwidth = gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.weightx = gbc.weighty = 70;
		gbc.insets = new Insets(4, 4, 4, 0);
		add(mMainBoard, gbc);
		mMainBoard.setBackground(Color.BLACK);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 20;
		gbc.insets = new Insets(4, 4, 4, 3);

		mSideBoard.add(mStateBoard, BorderLayout.WEST);
		add(mSideBoard, gbc);

		validate();
		repaint();
	}

	public MainBoard getMainBoard() {
		return mMainBoard;
	}

	public JLabel getStatusBar() {
		return mStateBoard.getStatusBar();
	}

	public JLabel getStage() {
		return mStateBoard.getStage();
	}

}
