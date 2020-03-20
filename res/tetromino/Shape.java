package tetromino;

import java.util.Random;

public class Shape {

	private Tetrominoes pieceShape;//테트로미노는 네개로 이루어진 블록들로 이중 7개는 테트리스에 주로 사용
	private int coords[][];
	private int[][][] coordsTable;

	public Shape() {
		coords = new int[4][2];
		setShape(Tetrominoes.NoShape);
	}

	public void setShape(Tetrominoes shape) {

		coordsTable = new int[][][] { { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } },
				{ { 0, -1 }, { 0, 0 }, { -1, 0 }, { -1, 1 } }, { { 0, -1 }, { 0, 0 }, { 1, 0 }, { 1, 1 } },
				{ { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 } }, { { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } },
				{ { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } }, { { -1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } },
				{ { 1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } } };//블록좌표 네개씩을 조합하여 만듬, 맨 첫번째 것은 아무것도 아님/ 2열 4행 8차원

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 2; ++j) {
				coords[i][j] = coordsTable[shape.ordinal()][i][j];//ordinal()함수는 정의된 순서를 리턴합니다. 0부터 시작!, shape객체는 테트로미노의 객체다.
			}
		}
		pieceShape = shape;

	}
	
	private void setX(int index, int x) {//set은 값 지정하는 것.
		coords[index][0] = x;
	}

	private void setY(int index, int y) {
		coords[index][1] = y;
	}

	public int x(int index) {
		return coords[index][0];
	}

	public int y(int index) {
		return coords[index][1];
	}

	public Tetrominoes getShape() {
		return pieceShape;
	}

	public void setRandomShape1() {//랜덤으로 일곱개중 하나 내보내는 것.
		Random r = new Random();
		int x = Math.abs(r.nextInt()) % 3 + 1;
		Tetrominoes[] values = Tetrominoes.values();
		setShape(values[x]);
	}
	
	public void setRandomShape2() {//랜덤으로 일곱개중 하나 내보내는 것.
		Random r = new Random();
		int x = Math.abs(r.nextInt()) % 5 + 1;
		Tetrominoes[] values = Tetrominoes.values();
		setShape(values[x]);
	}
	
	public void setRandomShape3() {//랜덤으로 일곱개중 하나 내보내는 것.
		Random r = new Random();
		int x = Math.abs(r.nextInt()) % 7 + 1;
		Tetrominoes[] values = Tetrominoes.values();
		setShape(values[x]);
	}


	public int minY() {
		int m = coords[0][1];
		for (int i = 0; i < 4; i++) {
			m = Math.min(m, coords[i][1]);
		}
		return m;
	}

	public Shape rotateLeft() {
		if (pieceShape == Tetrominoes.SquareShape)
			return this;

		Shape result = new Shape();
		result.pieceShape = pieceShape;

		for (int i = 0; i < 4; ++i) {
			result.setX(i, y(i));
			result.setY(i, -x(i));
		}
		return result;
	}

	public Shape rotateRight() {
		if (pieceShape == Tetrominoes.SquareShape)
			return this;

		Shape result = new Shape();
		result.pieceShape = pieceShape;

		for (int i = 0; i < 4; ++i) {
			result.setX(i, -y(i));
			result.setY(i, x(i));
		}
		return result;
	}
}