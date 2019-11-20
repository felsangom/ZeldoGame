package br.com.fsg.world;

public class Camera {

	public static int x = 0;
	public static int y = 0;

	public static int clamp(int current, int min, int max) {
		if (current < min) {
			return min;
		}

		if (current > max) {
			return max;
		}

		return current;
	}
}
