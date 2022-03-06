package googlehashcode;

import java.util.ArrayList;
import java.util.UUID;

public class Veicolo {
	int Id;
	int actualRow;
	int actualColumn;
	int Tlibero;
	boolean occupato;
	
	ArrayList<Integer> corse;
	
	Veicolo(){
		
		actualRow=0;
		actualColumn=0;
		Tlibero=0;
		occupato=false;
		corse = new ArrayList<>();
	}
	
	public void addCorse(int indiceCorsa){
		corse.add(indiceCorsa);
	}
	
	public Integer getLastCorsa(){
		return corse.get(corse.size() - 1);
	}
	
	public String toString(){
		int c=0;
		String stringa ="";
		for(int i=0; i<corse.size(); i++){
			stringa+=corse.get(i)+" ";
			c++;
		}
		
		stringa = c+ " " + stringa;
		return stringa;
	}
	
}
