package pe.bazan.luis.uni.domain;

public class ArraysUtils {
	public static void swap(int[] array, int from, int to) {
		int temp = array[to];
		array[to] = array[from];
		array[from] = temp;
	}

	public static void swap(Object[] array, int from, int to) {
		Object temp = array[to];
		array[to] = array[from];
		array[from] = temp;
	}
}
