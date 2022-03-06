package googlehashcode;

import java.io.BufferedReader;
import googlehashcode.Veicolo;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class calcola {
	
	int R;
	int C;
	int F;
	int N;
	int B;
	int T;
	
	//Controllo primordiale su tutti i clienti: da fare quando
	//si leggono gli N riders
	//Distanza veicolo e riders + distanza tra partenza e arrivo
	if(((Corsa_partenza_riga+Corsa_partenza_colonna)+abs(Corsa_partenza_riga-Corsa_arrivo_riga)+abs(Corsa_partenza_colonna-Corsa_arrivo_colonna))>T)
	{
		//ELIMINARE LA CORSA POICHè NON è FATTIBILE IN T STEP
	}

	//CODICE INSERITO NEL CICLO "MAGGIORE" DOVE SCANSIONO LA FLOTTA
	//Ora dobbiamo controllare che l'auto riesca ad arrivare al rider e selezionare solo i clienti raggiungibili

		for(ogni_corsa_non_assegnata)		
		{	
			int Bonus=0;
			if(abs(posizione_Veicolo_riga-corsa_partenza_riga)+abs(posizione_Veicolo_colonna-corsa_partenza_colonna)<tempo_partenza_corsa-tempo_attuale)
			{
				Bonus=B

			}
			int Valore_ottimo
		}		 
	}


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
		
		System.out.print("R=" + c.R + " C=" + c.C + " F=" + c.F + " N=" + c.N + " B=" + c.B + " T=" + c.T);
		
		Veicolo[] veicoli= new Veicolo[c.F];
		
		for(int i=0; i<c.F; i++){
			
			veicoli[i] = new Veicolo();
			
		}
		
		
		Corsa[] corsa= new Corsa[c.N];
		String line = null;
		for(int i=0; i<c.N; i++){
			line=b.readLine();
			corsa[i] = new Corsa(i, i, i, i, i, i);
			
		}
		/*
		int i=0;
		int j=0;
		String line = null;
		String[] lineSplit;
		c.pizza = new String[c.row_count][c.column_count];
		while(true)
		{
			try {
				line=b.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(line==null)
				break;
			lineSplit=line.split("");
			for(j=0;j<c.column_count;j++)
			{
				c.pizza[i][j]=lineSplit[j];
			}
			i++;
		}
		
		for(i=0; i<c.row_count; i++){
			for(j=0; j<c.column_count; j++){
				System.out.print(c.pizza[i][j]);
			}
			System.out.print("\n");
		}
		
		c.pizzaControllo = new int[c.row_count][c.column_count];
		for(i=0; i<c.row_count; i++){
			for(j=0; j<c.column_count; j++){
				if(c.pizzaControllo[i][j]==0){
					c.analisi(i, j);
				}
				
			}
		}
		*/
		
	}
	
	
	
}
