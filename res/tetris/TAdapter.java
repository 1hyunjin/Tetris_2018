package tetris;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import tetromino.Tetrominoes;

public class TAdapter extends KeyAdapter {

	private MainBoard board = null;
	protected Music alarm;
	
	public TAdapter(MainBoard board1p) {
		this.board = board1p;
		alarm = new Music();
	}
	
	public void keyPressed(KeyEvent e) {

		int keyCode = e.getKeyCode();

		if (!board.isStarted || board.curPiece.getShape() == Tetrominoes.NoShape) {
			return;
		}

		if (keyCode == 'p' || keyCode == 'P') {
			board.pause(1);
			return;
		}

		if (board.isPaused) {
			return;
		}

		switch (keyCode) {
		case KeyEvent.VK_LEFT:
			board.tryMove(board.curPiece, board.curX - 1, board.curY);
			break;
		case KeyEvent.VK_RIGHT:
			board.tryMove(board.curPiece, board.curX + 1, board.curY);
			break;
		case KeyEvent.VK_DOWN:
			board.tryMove(board.curPiece.rotateRight(), board.curX, board.curY);
			break;
		case KeyEvent.VK_UP:
			board.tryMove(board.curPiece.rotateLeft(), board.curX, board.curY);
			break;
		case KeyEvent.VK_SPACE:
			board.dropDown();
			break;
		case 'm':
			board.oneLineDown();
			break;
		case 'M':
			board.oneLineDown();
			break;
		}
		alarm.alStart();
	}
}
