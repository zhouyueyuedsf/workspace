package simpleexample;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RuntimeTest {
	public static void main(String[] args) {
		int A[][] = new int[5000][5000];
		long start = System.currentTimeMillis();
		System.out.println(start);
		for(int j = 0; j < 5000; j++){
			for(int i = 0; i < 5000; i++){
				A[i][j] = 0;
			}
		}
		long end = System.currentTimeMillis();
		Date startDate = new Date(start);	
		Date endDate = new Date(start);	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		
		System.out.println(dateFormat.format(startDate));
		System.out.println(dateFormat.format(endDate));
		System.out.println(end - start);
	}
}
