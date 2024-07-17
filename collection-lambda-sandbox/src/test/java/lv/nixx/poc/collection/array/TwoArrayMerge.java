package lv.nixx.poc.collection.array;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

public class TwoArrayMerge {

	private int[] merge(int[] a1, int[] a2) {

		final int a1Length = a1.length;
		final int a2Length = a2.length;

		int[] res = new int[a1Length + a2Length];
		Arrays.fill(res, -1);

		int i = 0;
		int j = 0;
		int pos = 0;

		while (i < a1Length && j < a2Length) {
			if (a1[i] < a2[j]) {
				res[pos] = a1[i];
				i++;
			} else {
				res[pos] = a2[j];
				j++;
			}
			pos++;
		}

		for (int k = i; k < a1Length; k++) {
			res[pos] = a1[k];
			pos++;
		}

		for (int k = j; k < a2Length; k++) {
			res[pos] = a2[k];
			pos++;
		}

		return res;
	}

	@Test
	public void mergeTwoArrays1() {
		int[] a1 = { 1, 3, 10, 15, 22 };
		int[] a2 = { 2, 3, 7, 47 };

		final int[] r = merge(a1, a2);

		assertNotNull(r);
		Arrays.stream(r).forEach(System.out::println);
		assertArrayEquals(new int[]{1, 2, 3, 3, 7, 10, 15, 22, 47}, r);
	}

	@Test
	public void mergeTwoArrays2() {
		int[] a1 = { 2, 3, 7, 47 };
		int[] a2 = { 1, 3, 10, 15, 22 };

		final int[] r = merge(a1, a2);

		assertNotNull(r);
		Arrays.stream(r).forEach(System.out::println);
		assertArrayEquals(new int[]{1, 2, 3, 3, 7, 10, 15, 22, 47}, r);
	}

	@Test
	public void mergeTwoArrays3() {
		int[] a1 = { 2, 3, 7, 47 };
		int[] a2 = { 0, 0, 0 };

		final int[] r = merge(a1, a2);

		assertNotNull(r);
		Arrays.stream(r).forEach(System.out::println);
		assertArrayEquals(new int[]{0, 0, 0, 2, 3, 7, 47}, r);
	}

}
