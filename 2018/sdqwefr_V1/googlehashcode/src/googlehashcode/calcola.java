package googlehashcode;

import java.io.BufferedReader;
import googlehashcode.Veicolo;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Math;

public class calcola {
	
	int R;
	int C;
	int F;
	int N;
	int B;
	int T;
	
	public static void main(String[] args) throws IOException {
		calcola c=new calcola();
		FileReader Testo = null;
		try {
			Testo=new FileReader("C:\\Users\\fstra\\Developing\\Workspace\\googlehashcode\\src\\b_should_be_easy.in");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BufferedReader b;
		b=new BufferedReader(Testo);

		String FirstLine = null;
		try {
			FirstLine = b.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String[] Parameters = FirstLine.split(" ");
		
		c.R=Integer.parseInt(Parameters[0]);
		c.C=Integer.parseInt(Parameters[1]);
		c.F=Integer.parseInt(Parameters[2]);
		c.N=Integer.parseInt(Parameters[3]);
		c.B=Integer.parseInt(Parameters[4]);
		c.T=Integer.parseInt(Parameters[5]);
		
		System.out.println("R=" + c.R + " C=" + c.C + " F=" + c.F + " N=" + c.N + " B=" + c.B + " T=" + c.T);
		
		Veicolo[] veicoli= new Veicolo[c.F];
		
		for(int i=0; i<c.F; i++){
			
			veicoli[i] = new Veicolo();
			
		}
		
		
		Corsa[] corsa= new Corsa[c.N];
		
		String line = null;
		int counterCorseEliminate=0;
		for(int i=0; i<c.N; i++){
			line=b.readLine();
			String[] lineSplit = line.split(" ");

			if(((Integer.parseInt(lineSplit[0])+Integer.parseInt(lineSplit[1]))+Math.abs(Integer.parseInt(lineSplit[0])-Integer.parseInt(lineSplit[2]))+Math.abs(Integer.parseInt(lineSplit[1])-Integer.parseInt(lineSplit[3])))<=c.T)
			{
				corsa[i] = new Corsa(lineSplit);
				counterCorseEliminate++;
			}	
			
		}
		
		c.N=counterCorseEliminate;
		System.out.println(c.N);
		
		
		int corsaMigliore=0;
		
		//ciclo per ogni step
		for(int t=0; t<c.T; t++){
			System.out.println("tempo"+t);
			//t = step corrente
			//ciclo per ogni veicolo
			for(int f=0; f<c.F; f++){
				
				if(veicoli[f].occupato==false){
					
					corsaMigliore = c.migliorCorsa(corsa, veicoli[f], c, t);
					if(corsaMigliore!=-1){
						System.out.println("veicolo asseganta corsa"+f);
						veicoli[f].addCorse(corsaMigliore);
						veicoli[f].Tlibero= t + c.diff2punti(veicoli[f].actualRow, veicoli[f].actualColumn, corsa[corsaMigliore].startRow, corsa[corsaMigliore].startColumn) + c.diff2punti(corsa[corsaMigliore].startRow, corsa[corsaMigliore].startColumn, corsa[corsaMigliore].finishRow, corsa[corsaMigliore].finishColumn);
						veicoli[f].occupato=true;
					}
					
					
				}else{
					
					if(t>=veicoli[f].Tlibero){
						veicoli[f].occupato=false;
						veicoli[f].actualRow = corsa[veicoli[f].getLastCorsa()].finishRow; 
						veicoli[f].actualColumn = corsa[veicoli[f].getLastCorsa()].finishColumn; 
					}
				}
			}
		}
		
		
		for(int f=0; f<c.F; f++){
			System.out.println(veicoli[f].toString());
		}
		
	}
	
	public int diff2punti(int a, int b, int x, int y){
		return Math.abs(a-x)+Math.abs(b-y);
	}
	
	public int migliorCorsa(Corsa[] corse, Veicolo v, calcola c, int t){
		//CODICE INSERITO NEL CICLO "MAGGIORE" DOVE SCANSIONO LA FLOTTA
		//Ora dobbiamo controllare che l'auto riesca ad arrivare al rider e selezionare solo i clienti raggiungibili
		int Valore_ottimo;
		int max=-100000000;
		int C_migliore = -1;
		for(int j=0; j<c.N; j++)
		{
			// controlla che la corsa non è già assegnata
			if(corse[j].fatta)
				continue;
			
			
			int Bonus=0;
			if(Math.abs(v.actualRow-corse[j].startRow)+Math.abs(v.actualColumn-corse[j].startColumn)<corse[j].start-t)
			{
				Bonus=c.B;

			}
			
			Valore_ottimo=Bonus+Math.abs(corse[j].startRow-corse[j].finishRow)+Math.abs(corse[j].startColumn-corse[j].finishColumn)-(corse[j].start-t);
			
			if(max==-100000000)
			{
				System.out.println();
				max=Valore_ottimo;
				C_migliore=j;
			}
			else if(max<Valore_ottimo)
			{
				max=Valore_ottimo;
				C_migliore=j;
			}
		}
		
		//System.out.println("Corsa migliore"+C_migliore);
		if(C_migliore!=-1){
			corse[C_migliore].fatta=true;
		}
		
		
		return C_migliore;
	}
	
}
