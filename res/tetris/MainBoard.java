package tetris;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import tetromino.Shape;
import tetromino.Tetrominoes;

public class MainBoard extends JPanel implements ActionListener {

	final int boardWidth = 10;
	final int boardHeight = 22;
	Timer timer;

	boolean isFallingFinished = false;
	boolean isStarted = false;
	boolean isPaused = false;
	boolean ghost = true;
	int numLinesRemoved = 0;
	int curX = 0;
	int curY = 0;
	int ghostX = 0;
	int ghostY = 0;
	public Shape curPiece;
	private Shape ghostPiece; 
	private Tetrominoes[] board;
	private MainBoard enemy = this;
	private JLabel statusbar;
	private JLabel stage;
	private TetrisBoard tetrisBoard;
	Shape next = new Shape();
	int level = 1;
	private Music music;

	public MainBoard(TetrisBoard parent) {

		setFocusable(true);
		curPiece = new Shape();
		ghostPiece = new Shape();
		timer = new Timer(500, this);
		timer.start();
		statusbar = parent.getStatusBar();
		stage = parent.getStage();
		tetrisBoard = parent;
		board = new Tetrominoes[boardWidth * boardHeight];
		clearBoard();
		music = new Music();
	}

	public void getBoard(MainBoard board) { 
		enemy = board;
	}

	public void actionPerformed(ActionEvent e) {
		if (isFallingFinished) {
			isFallingFinished = false;
			newPiece();

		} else {
			oneLineDown();
		}
	}

	int squareWidth() {
		return (int) getSize().getWidth() / boardWidth;
	}

	int squareHeight() {
		return (int) getSize().getHeight() / boardHeight;
	}

	Tetrominoes shapeAt(int x, int y) {
		return board[(y * boardWidth) + x];
	}

	public void start(int a) {
		if (isPaused)
			return;
		getLevel();
		stage.setText(String.valueOf(level));

		isStarted = true;
		isFallingFinished = false;
		numLinesRemoved = 0;
		clearBoard();
		if (level == 1)
			next.setRandomShape1();
		else if (level == 2)
			next.setRandomShape2();
		else
			next.setRandomShape3();
		newPiece();
		timer.start();
		music.BGMstart();

		if (a == 2) {
			music.MusicStop();
		}
	}

	public void pause(int a) {
		if (!isStarted)
			return;

		isPaused = !isPaused;
		if (isPaused) {

			timer.stop();
			music.MusicStop();
			statusbar.setText("paused");
		} else {
			timer.start();
			if (a == 1) {
				music.MusicRestart();
			} else
				music.MusicStop();
			statusbar.setText(String.valueOf(numLinesRemoved));
		}
		repaint();
		getLevel();
		stage.setText(String.valueOf(level));
	}

	public void paintbackground(Graphics g) {
		super.paint(g);
		for (int i = 0; i < boardHeight; ++i) {
			for (int j = 0; j < boardWidth; ++j) {
				g.setColor(Color.WHITE);
				g.drawRect(0 + squareWidth() * j, 0 + i * squareHeight(), squareWidth(), squareHeight());
			}
		}
	}

	public void paint(Graphics g) {
		super.paint(g);

		Dimension size = getSize();
		int boardTop = (int) size.getHeight() - boardHeight * squareHeight();
		for (int i = 0; i < boardHeight; ++i) {
			for (int j = 0; j < boardWidth; ++j) {
				Tetrominoes shape = shapeAt(j, boardHeight - i - 1);
				if (shape != Tetrominoes.NoShape)
					drawSquare(g, 0 + j * squareWidth(), boardTop + i * squareHeight(), shape, false);
			}
		}

		if (curPiece.getShape() != Tetrominoes.NoShape) {
			for (int i = 0; i < 4; ++i) {
				int x = curX + curPiece.x(i); 
				int y = curY - curPiece.y(i); 
				drawSquare(g, x * squareWidth(), boardTop + (boardHeight - y - 1) * squareHeight(), curPiece.getShape(), false);
			}

			for (int i = 0; i < 4; ++i) {
				ghost(g);
				int x = ghostX + ghostPiece.x(i);
				int y = ghostY - ghostPiece.y(i);
				drawSquare(g, x * squareWidth(), boardTop + (boardHeight - y - 1) * squareHeight(),
						ghostPiece.getShape(), true);
			}
		}
	}

	private void ghost(Graphics g) { 
		Dimension size = getSize();
		int boardTop = (int) size.getHeight() - boardHeight * squareHeight(); 
																				
		updateUI();
		int newY = curY;
		while (newY > 0) {
			if (!tryGhostMove(curPiece, curX, newY - 1)) {
				break;
			}
			--newY;
		}
	}

	public void dropDown() {
		int newY = curY;
		while (newY > 0) {
			if (!tryMove(curPiece, curX, newY - 1))
				break;
			--newY;
		}
		pieceDropped();
	}

	public void oneLineDown() {
		if (!tryMove(curPiece, curX, curY - 1))
			pieceDropped();
	}

	public void clearBoard() {
		for (int i = 0; i < boardHeight * boardWidth; ++i) {
			board[i] = Tetrominoes.NoShape;
		}
	}

	private void pieceDropped() {
		for (int i = 0; i < 4; ++i) {
			int x = curX + curPiece.x(i);
			int y = curY - curPiece.y(i);
			board[(y * boardWidth) + x] = curPiece.getShape();
		}

		removeFullLines();

		if (!isFallingFinished)
			newPiece();
	}

	private void newPiece() {
		curPiece.setShape(next.getShape());
		getLevel();
		
		if (level == 1)
			next.setRandomShape1();
		else if (level == 2)
			next.setRandomShape2();
		else
			next.setRandomShape3();

		tetrisBoard.mSideBoard.getNext(next);

		curX = boardWidth / 2 + 1;
		curY = boardHeight - 1 + curPiece.minY();

		if (!tryGhostMove(curPiece, curX, curY)) {
			curPiece.setShape(Tetrominoes.NoShape);
			timer.stop();
			// 게임오버시 소리 정지
			music.MusicStop();
			isStarted = false;
			statusbar.setText("game over");

			enemy.timer.stop();
			enemy.music.MusicStop();
			enemy.statusbar.setText("You win!");
			// isPaused = true;
			statusbar.setText("game over");
		}
	}

	public int getLevel() {
		return level;
	}

	public boolean tryMove(Shape newPiece, int newX, int newY) {
		return move(newPiece, newX, newY, 'c');
	}

	private boolean tryGhostMove(Shape newPiece, int newX, int newY) {
		return move(newPiece, newX, newY, 'g');
	}

	private boolean move(Shape newPiece, int newX, int newY, char a) {
		for (int i = 0; i < 4; ++i) {
			int x = newX + newPiece.x(i);
			int y = newY - newPiece.y(i);
			if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight)
				return false;
			if (shapeAt(x, y) != Tetrominoes.NoShape)
				return false;
		}
		
		if (a == 'c') {
			curPiece = newPiece;
			curX = newX;
			curY = newY;
			repaint();
			return true;
		} else {
			ghostPiece = newPiece;
			ghostX = newX;
			ghostY = newY;
			repaint();
			return true;
		}
	}

	private void removeFullLines() {
		int numFullLines = 0;

		for (int i = boardHeight - 1; i >= 0; --i) {
			boolean lineIsFull = true;

			for (int j = 0; j < boardWidth; ++j) {
				if (shapeAt(j, i) == Tetrominoes.NoShape) {
					lineIsFull = false;
					break;
				}
			}

			if (lineIsFull) {
				++numFullLines;
				for (int k = i; k < boardHeight - 1; ++k) {
					for (int j = 0; j < boardWidth; ++j)
						board[(k * boardWidth) + j] = shapeAt(j, k + 1);
				}
			}
		}

		if (numFullLines > 0) {
			numFullLines *= 300; 
			numLinesRemoved += numFullLines;
			statusbar.setText(String.valueOf(numLinesRemoved));
			isFallingFinished = true;
			curPiece.setShape(Tetrominoes.NoShape);
			repaint();
			stage();
		}
	}

	public void stage() {
		if (numLinesRemoved > 200 && numLinesRemoved <= 500) {
			level = 2;
			pause(1);
			timer.stop();
			music.MusicStop();
			statusbar.setText("0");

			statusbar.setText(String.valueOf(numLinesRemoved));
			timer.setDelay(300);
			pause(1);
		} else if (numLinesRemoved > 500) {
			pause(1);
			level = 3;
			timer.stop();
			music.MusicStop();

			timer.setDelay(200);
			pause(1);
		}
	}

	private void drawSquare(Graphics g, int x, int y, Tetrominoes shape, boolean isGhost) {
		int transparent;

		if (isGhost) {
			transparent = 122;
		} else {
			transparent = 255;
		}

		Color colors[] = { new Color(0, 0, 0, transparent), new Color(0, 255, 204, transparent),
				new Color(136, 255, 77, transparent), new Color(255, 255, 0, transparent),
				new Color(204, 0, 255, transparent), new Color(51, 153, 255, transparent),
				new Color(255, 92, 51, transparent), new Color(255, 230, 242, transparent) };

		Color color = colors[shape.ordinal()];

		g.setColor(color);
		g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

		g.setColor(color.brighter());
		g.drawLine(x, y + squareHeight() - 1, x, y);
		g.drawLine(x, y, x + squareWidth() - 1, y);

		g.setColor(color.darker());
		g.drawLine(x + 1, y + squareHeight() - 1, x + squareWidth() - 1, y + squareHeight() - 1);
		g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1, x + squareWidth() - 1, y + 1);
	}
}