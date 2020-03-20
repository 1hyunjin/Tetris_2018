package tetris;

import java.awt.event.KeyEvent;

import tetromino.Tetrominoes;

public class TAdapterMulti extends TAdapter {
	private MainBoard board = null;

	public TAdapterMulti(MainBoard board2p, MainBoard board1p) {
		super(board2p);
		alarm = super.alarm;
		this.board = board1p;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		
		int keyCode = e.getKeyCode();
		
		if (!board.isStarted || board.curPiece.getShape() == Tetrominoes.NoShape) {
			return;
		}
		
		if (keyCode == 'p' || keyCode == 'P') {
			board.pause(2);
			return;
		}
		
		if (board.isPaused) {
			return;
		}
		
		switch (keyCode) {
		case 's':
			board.tryMove(board.curPiece, board.curX - 1, board.curY);
			break;
		case 'S':
			board.tryMove(board.curPiece, board.curX - 1, board.curY);
			break;
		case 'f':
			board.tryMove(board.curPiece, board.curX + 1, board.curY);
			break;
		case 'F':
			board.tryMove(board.curPiece, board.curX + 1, board.curY);
			break;
		case 'd':
			board.tryMove(board.curPiece.rotateRight(), board.curX, board.curY);
			break;
		case 'D':
			board.tryMove(board.curPiece.rotateRight(), board.curX, board.curY);
			break;
		case 'e':
			board.tryMove(board.curPiece.rotateLeft(), board.curX, board.curY);
			break;
		case 'E':
			board.tryMove(board.curPiece.rotateLeft(), board.curX, board.curY);
			break;
		case KeyEvent.VK_SHIFT:
			board.dropDown();
			break;
		case 'z':
			board.oneLineDown();
			break;
		case 'Z':
			board.oneLineDown();
			break;
		}
	}
}
