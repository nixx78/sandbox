package lv.nixx.poc.collection.array;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TwoDementionArray {
	
	@Test
	public void twoDimentionalArray() {
		
		int [][] ar = new int[][] {
			{1,2,3,},
			{4,5,6},
			{7,8,9,10}
		};
		
		int[] i = ar[2];
		assertEquals(4, i.length);
		assertEquals(10, ar[2][3]);
	}

}
