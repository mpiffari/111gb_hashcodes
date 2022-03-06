package googlehashcode2019;

import java.io.*;
import java.nio.*;
import java.util.ArrayList;


public class FileHandler {

	private String separatorOfElement = " ";
	private String absolutePath = "";
	private String exe = "a_example";
	//private String exe = "b_lovely_landscapes";
	//private String exe = "c_memorable_moments";
	//private String exe = "d_pet_pictures";
	//private String exe = "e_shiny_selfies";
	
	int numLinee = 0;
	public static ArrayList<Foto> fotoList = new ArrayList<>();

	public static void main(String[] args) {
		FileHandler fh = new FileHandler();
		fh.absolutePath = "C:\\Users\\fstra\\Desktop\\Coding\\";
		fotoList = fh.readAlltheLine();
		ArrayList<Slide> slideList = Foo.computeSlideshow(fotoList);
		/*Slide slide1 = new Slide(fotoList.get(1), null);
		Slide slide2 = new Slide(fotoList.get(2), null);
		Slide slide3 = new Slide(fotoList.get(3), fotoList.get(2));
		ArrayList<Slide> slideList = new ArrayList<Slide>();
		slideList.add(slide1);
		slideList.add(slide2);
		slideList.add(slide3);*/
		fh.toFile(slideList);
	}

	private void toFile(ArrayList<Slide> slideList) {
		// write the content in file
		int i=0;
		String stringa=""+slideList.size()+"\n";
		while(i<slideList.size()){
			stringa+=slideList.get(i).toString()+"\n";
			i++;
		}
		
		try(FileOutputStream fileOutputStream = new FileOutputStream(absolutePath+exe+"_out.txt")) {
		    fileOutputStream.write(stringa.getBytes());
		} catch (FileNotFoundException e) {
		    // exception handling

		} catch (IOException e) {
		    // exception handling
		}
	}

	//Return each number separated by
	public ArrayList<Foto> readAlltheLine() {
		FileReader Testo = null;
		try {
			Testo=new FileReader(absolutePath+exe+".txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BufferedReader b;
		b=new BufferedReader(Testo);

		String allLine = " ";
		int numLines = 0;
		try {
			numLines = FileHandler.countLinesNew(absolutePath+exe+".txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("Num lines" + numLines);
		try {
			for(int i=0; i<numLines; i++){
				String line = b.readLine();
				
				allLine = allLine.concat(line);
				
				if(i==0){
					numLinee = Integer.parseInt(line);
					//System.out.println("NumLinee "+numLinee);
				}else{
					System.out.println("id:" + (i-1));
					String[] Parameters = line.split(" ");
					Foto foto = new Foto ((i-1), Parameters);
					fotoList.add(foto);
				}
			}
			return fotoList;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
	}

	public void setPath(String path) {
		this.absolutePath = path;
	}

	public static int countLinesNew(String filename) throws IOException {
	    InputStream is = new BufferedInputStream(new FileInputStream(filename));
	    try {
	        byte[] c = new byte[1024];

	        int readChars = is.read(c);
	        if (readChars == -1) {
	            // bail out if nothing to read
	            return 0;
	        }

	        // make it easy for the optimizer to tune this loop
	        int count = 0;
	        while (readChars == 1024) {
	            for (int i=0; i<1024;) {
	                if (c[i++] == '\n') {
	                    ++count;
	                }
	            }
	            readChars = is.read(c);
	        }

	        // count remaining characters
	        while (readChars != -1) {
	            //System.out.println("Numero di caratteri:"+readChars);
	            for (int i=0; i<readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	            readChars = is.read(c);
	        }
	        return count == 0 ? 1 : count;
	    } finally {
	        is.close();
	    }
	}
}
