package me.dernudel.pokemongo.map;

public class Point {

	double x, z;
	
	public Point(double x, double z) {
		this.x = x;
		this.z = z;
	}
	
	public String toString() {
		return "P" + x + "," + z + "P";
	}
	
	public static Point fromString(String s) {
		String f = s.replaceAll("P", "");
		String[] ss = f.split(",");
		if (ss.length != 2) return null;
		return new Point(Double.parseDouble(ss[0]), Double.parseDouble(ss[1]));
	}
}
