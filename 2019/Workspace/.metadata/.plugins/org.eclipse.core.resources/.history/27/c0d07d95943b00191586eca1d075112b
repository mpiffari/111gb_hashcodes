package googlehashcode2019;

import java.util.ArrayList;

public class Foto {
	public int id;
	public boolean direction;
	public ArrayList<String> tags= new ArrayList<>();
	
	Foto(int idd, String arr[]){
		this.id = idd;
		if(arr[0].equals("H")){
			direction=true;
		}else{
			direction=false;
		}
		int i=0;
		while(i<Integer.parseInt(arr[1])){
			tags.add(arr[i+2]);
			i++;
		}
	}
	
	public String toString(){
		return "id:"+this.id+ " direction:"+this.direction + " tags:"+this.tags.toString();
	}
}
