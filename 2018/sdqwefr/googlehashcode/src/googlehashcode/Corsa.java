package googlehashcode;

public class Corsa {
	
	int startRow;
	int startColumn;
	int finishRow;
	int finishColumn;
	int start;
	int finish;
	
	boolean fatta;
	
	Corsa(int a, int b, int x, int y, int s, int f){
		startRow = a;
		startColumn = b;
		finishRow = x;
		finishColumn = y;
		start = s;
		finish = f;
		fatta = false;
	}
	
}
