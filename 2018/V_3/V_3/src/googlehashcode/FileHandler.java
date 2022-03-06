package googlehashcode;

import java.awt.List;
import java.io.*;
import java.nio.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.Painter;


public class FileHandler {

	private String separatorOfElement = " ";
	private String absolutePath = "";
	//private String exe = "a_example";
	private String exe = "b_lovely_landscapes";
	//private String exe = "c_memorable_moments";
	//private String exe = "d_pet_pictures";
	//private String exe = "e_shiny_selfies";
	
	int numLinee = 0;
	public static ArrayList<Foto> fotoList = new ArrayList<>();
	public static Set<String> firstTopTags;
	public static Set<String> secondTopTags;
	public static Set<String> thirdTopTags;
	public static Set<String> fourTopTags;
	public static Set<String> fiveTopTags;
	public static Set<String> sixTopTags;

	public static void main(String[] args) {
		FileHandler fh = new FileHandler();
		fh.absolutePath = "C:\\Users\\miche\\OneDrive\\Desktop\\Coding\\HC_19\\";
		fotoList = fh.readAlltheLine();
		
		System.out.println("DIOCANE");
		
		ArrayList<Slide> slideList = Foo.computeSlideshow(fotoList, firstTopTags, secondTopTags, thirdTopTags, fourTopTags,fiveTopTags, sixTopTags);
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
			HashMap<String, Integer> tagDataset = new HashMap<>();
			for(int i=0; i<numLines; i++){
				String line = b.readLine();
							
				if(i==0){
					numLinee = Integer.parseInt(line);
					//System.out.println("NumLinee "+numLinee);
				}else{
					//System.out.println("id:" + (i-1));
					String[] parameters = line.split(" ");
					Foto foto = new Foto ((i-1), parameters);
					
					for (String tag : foto.tags) {
						if (!tagDataset.containsKey(tag)) {
							tagDataset.put(tag, 1);
						} else {
							int oldRecurrence = tagDataset.get(tag);
							tagDataset.replace(tag, oldRecurrence+1);
						}
					}
					fotoList.add(foto);
				}
			}
			ArrayList<Integer> keys = new ArrayList(tagDataset.values());
		
			Collections.sort(keys);
		
			Integer firstRecurrence = keys.get((keys.size()-1));
			Integer secondRecurrence = keys.get((keys.size()-2));
			Integer thirdRecurrence = keys.get((keys.size()-3));
			System.out.println("Highest recurrence of same tag: "+ firstRecurrence);
			
			firstTopTags = getKeysByValue(tagDataset,firstRecurrence);
			secondTopTags = getKeysByValue(tagDataset,secondRecurrence);
			thirdTopTags = getKeysByValue(tagDataset,thirdRecurrence);
			fourTopTags = getKeysByValue(tagDataset,keys.get((keys.size()-4)));
			fiveTopTags = getKeysByValue(tagDataset,keys.get((keys.size()-5)));
			sixTopTags = getKeysByValue(tagDataset,keys.get((keys.size()-6)));
			/*System.out.println("FIRST TOP TAGS");
			for (String s: firstTopTags) {
				System.out.println(s);
			}
			System.out.println("SECOND TOP TAGS");
			for (String s: secondTopTags) {
				System.out.println(s);
			}
			System.out.println("THIRD TOP TAGS");
			for (String s: secondTopTags) {
				System.out.println(s);
			}*/
			return fotoList;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
	}
	
	public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
	    return map.entrySet()
	              .stream()
	              .filter(entry -> Objects.equals(entry.getValue(), value))
	              .map(Map.Entry::getKey)
	              .collect(Collectors.toSet());
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
