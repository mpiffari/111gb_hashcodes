package googlehashcode2019;

import java.util.ArrayList;

public class Slide {
	public Foto f1;
	public Foto f2;
	public ArrayList<String> tags = new ArrayList<>();
	
	Slide(Foto f1, Foto f2){
		this.f1 = f1;
		this.f2 = f2;
		
		if(f2==null){
			if(!f1.direction){
				System.out.println("ATTTENZIONE! mi hai passato f1 e null ma f1 è verticale");
			}
			int i=0;
			while(i<f1.tags.size()){
				this.tags.add(f1.tags.get(i));
				i++;
			}
		}else{
			if(!f1.direction && !f2.direction){
				
			}else{
				System.out.println("ATTTENZIONE! f1 e f2 non sono entrambe verticali");
			}
			tags.addAll(f1.tags);
			for(String e: f2.tags){
			    if(!tags.contains(e))
			        tags.add(e);
			}
		}
		//System.out.println("SLIDE: tags:"+tags.toString());
	}
	
	public String toString(){
		if(f2==null){
			return f1.id+"";
		}else{
			return f1.id + " " + f2.id;
		}
	}
}
