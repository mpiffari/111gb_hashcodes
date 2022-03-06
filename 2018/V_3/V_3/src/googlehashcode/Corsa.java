package googlehashcode;

public class Corsa {
	
	int startRow;
	int startColumn;
	int finishRow;
	int finishColumn;
	int start;
	int finish;
	
	boolean fatta;
	
	Corsa(String arr[]){
		startRow = Integer.parseInt(arr[0]);
		startColumn = Integer.parseInt(arr[1]);
		finishRow = Integer.parseInt(arr[2]);
		finishColumn = Integer.parseInt(arr[3]);
		start = Integer.parseInt(arr[4]);
		finish = Integer.parseInt(arr[5]);
		fatta = false;
	}
	
}
