package utils;

public class Rel {
    public static int X(int x) {
		return (int) (Dimensions.WIDTH * (x / (double) 1920));
	}

	public  static int Y(int y) {
		return (int) (Dimensions.HEIGHT * (y / (double) 1080));
	}

	public static int W(int w) {
		return (int) (Dimensions.WIDTH * (w / (double) 1920));
	}

	public static int H(int h) {
		return (int) (Dimensions.HEIGHT * (h / (double) 1080));
	}
}
