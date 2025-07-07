package pe.bazan.luis.uni;

import java.util.Random;

/**
 * author: Luis Bazan
 */
public class Test {
	public static void main() {
		int length = 1000;
		int[] numbers = new int[length];

		int[] randomIndex = new int[length];

		fillArray(numbers, 100, 600);
		fillRandomIndex(randomIndex);

		System.out.println("wa"); // Breaking point

	}

	public static void fillRandomIndex(int[] array) {
		int[] indexArray = new int[array.length];
		int arrayLength = indexArray.length;

		int iteraciones = 0;

		fillIndex(indexArray);

		int index = 0;

		Random random = new Random();

		for (int i = 0; i < array.length; i++) {
			index = random.nextInt(0, arrayLength);
			array[i] = indexArray[index];
			arrayLength--;
			swap(indexArray, index, arrayLength);
			System.out.println(iteraciones++); // Mostrar iteraciones
		}
	}

	public static void fillIndex(int[] array) {
		for (int i = 0; i < array.length; i++) {
			array[i] = i;
		}
	}

	public static void swap(int[] array, int from, int to) {
		int temp = array[to];
		array[to] = array[from];
		array[from] = temp;
	}

	public static void fillArray(int[] array, int min, int max) {
		Random random = new Random();
		for (int i = 0; i < array.length; i++) {
			array[i] = random.nextInt(min, max+1);
		}
	}
}
