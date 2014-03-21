package io.sporkpgm.util;

public class RandomUtils {

	public static int getRandom(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}

}
