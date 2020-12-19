
public class CAsorter {
	public static int[][][] sort(int[][][] array) {
		int sorterArray[] = new int[(array.length * array[0].length * array[0][0].length)];

		int iter = 0;
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[0].length; j++) {
				for(int k = 0; k < array[0][0].length; k++) {
					sorterArray[iter] = array[i][j][k];
					iter++;
				}
			}
		}

		for(int i = 0; i < sorterArray.length; i++) {
			for(int j = i + 1; j < sorterArray.length; j++) {
				if(sorterArray[i] > sorterArray[j]) {
					int temp = sorterArray[j];
					sorterArray[j] = sorterArray[i];
					sorterArray[i] = temp;
				}
			}
		}
		
		for(int i = sorterArray.length - 1; i > 0; i--) {
			sorterArray[i] = sorterArray[i] - sorterArray[i-1];
		}
		
		for(int i = 0; i< sorterArray.length; i++) {
			++sorterArray[i];
		}

		iter = 0;
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[0].length; j++) {
				for(int k = 0; k < array[0][0].length; k++) {
					array[i][j][k] = sorterArray[iter];
					iter++;
				}
			}
		}
		return array;
	}
}
