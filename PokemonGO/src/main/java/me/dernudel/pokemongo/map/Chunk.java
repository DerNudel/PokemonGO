package me.dernudel.pokemongo.map;

public class Chunk {

	Point p1, p2;
	
	public Chunk(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	Point blockAt(int num) {
		int x = 0, z = 0;
		int f = 0;
		for (int i = 0; i<= 16; i++) {
			for (int y = 0; y<= 16; y++) {
				f++;
				if (f == num) {
					x = (int) p1.x + i;
					z = (int) p2.z + y;
					break;
				}
				if (f == num) {
					break;
				}
			}
		}
		return new Point(x, z);
	}
	
}
