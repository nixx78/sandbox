package lv.nixx.poc.datastruct.matrix;

import java.util.Arrays;
import org.junit.Test;

public class MatrixMultiplication {
	
	
	@Test
	public void multiple() {
		
	/*
	 * Операция умножения двух матриц выполнима только в том случае, если число 
	 * столбцов в первом сомножителе равно числу строк во втором; в этом случае говорят, 
	 * что матрицы согласованы. В частности, умножение всегда выполнимо, 
	 * если оба сомножителя — квадратные матрицы одного и того же порядка.
	 * Таким образом, из существования произведения A B  AB вовсе не следует существование
	 *  произведения B A .
	 */
		
		int[][] A = new int[][]{
			{1,2},
			{3,4},
			{5,6},
		};
		
		int[][] B = new int[][] {
			{1,1,1},
			{2,2,2},
		};
		
		int aRows = A.length;
		int aColumns = A[0].length;
		int bColumns = B[0].length;

		int[][] res = new int[aRows][bColumns];
		
		System.out.println("Resulting matrix size: [" + res[0].length + "*" + res.length + "]");

		for (int i = 0; i < aRows; i++) { // For each A matrix Row
			for (int j = 0; j < bColumns; j++) { // For each B matrix Column
				for (int k = 0; k < aColumns; k++) { // A column and B row
					res[i][j] += A[i][k] * B[k][j];
				}
			}
		}
		
		
		Arrays.stream(res).forEach(
				row -> {
					Arrays.stream(row)
						.forEach(elem -> System.out.print(elem + "\t"));
					System.out.println();
		});
		
	}

}
