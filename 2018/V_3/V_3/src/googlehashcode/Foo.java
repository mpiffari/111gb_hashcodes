package googlehashcode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.RuntimeErrorException;

public class Foo {

	static ArrayList<Slide> creaSlides (ArrayList<Foto> foto) {
		ArrayList<Slide> s = new ArrayList<>();
		Collections.shuffle(foto);
		Foto x = null;
		ArrayList<Foto> fotoVerticaliDaAggiungere = null;
		for (Foto f : foto) {
			if (f.direction) {
				s.add(new Slide(f,null));
			} else {
				if (f.direction) {
					s.add(new Slide(f,null));
				} else {
					if (x == null) {
						x = f;
						continue;
					} else {
						int equalityTags = 0;
						for(String tag : x.tags) {
							for(String tagtag: f.tags) {
								if (tag == tagtag) {
									equalityTags ++;
									break;
								}
							}
						}

						if (equalityTags < x.tags.size()/2) {
							s.add(new Slide(x,f));
							x = null;
						} else {
							if(fotoVerticaliDaAggiungere == null) {
								fotoVerticaliDaAggiungere = new ArrayList<>();	
							}
							fotoVerticaliDaAggiungere.add(f);
						}
					}
				}
			}	
		}
		if(fotoVerticaliDaAggiungere == null) {
			fotoVerticaliDaAggiungere = new ArrayList<>();
		}
		for (Foto f : fotoVerticaliDaAggiungere) {
			if (f.direction) {
				s.add(new Slide(f,null));
			} else {
				if (x == null) {
					x = f;
					continue;
				} else {
					s.add(new Slide(x,f));
					x = null;
				}
			}
		}

		//Useful for selfie that are all vertical images
		Collections.shuffle(s);
		return s;
	}

	static ArrayList<Slide> computeSlideshow(ArrayList<Foto> foto, Set<String> firstTags, Set<String> secondTags, Set<String> thirdTags, Set<String> fourTags, Set<String> fiveTags, Set<String> sixTags) {
		ArrayList<Slide> result = new ArrayList<>();
		ArrayList<Slide> ss = Foo.creaSlides(foto);
		System.out.println("+++++++++ Tutte le slides: "+ss.size()+" +++++++++");
		ArrayList<Slide> sss = (ArrayList<Slide>) ss.clone();
		ArrayList<Slide> slideSenzaTopTagMedium = new ArrayList<>();
		ArrayList<Slide> slideSenzaTopTagLow = new ArrayList<>();
		//if (firstTags.size()==1 && secondTags.size() == 1 && thirdTags.size()==1 && fourTags.size() == 1 && fiveTags.size()==1 && sixTags.size() == 1) {
		String firstTag = (String) firstTags.toArray()[0];
		String fourTag = (String) fourTags.toArray()[0];
		String thirdTag = (String) thirdTags.toArray()[0];
		String secondTag = (String) secondTags.toArray()[0];
		String fiveTag = (String) fiveTags.toArray()[0];
		String sixTag = (String) sixTags.toArray()[0];
		
		for (Slide s : ss) {
			if(!s.tags.contains(firstTag) && !s.tags.contains(thirdTag) && !s.tags.contains(secondTag)) {
				sss.remove(s);
				slideSenzaTopTagMedium.add(s);
			}
		}
		
		ss = sss;
		System.out.println("+++++++++ Slides contenenti top tag: "+ss.size()+" +++++++++");
		System.out.println("+++++++++ Slides NON contenenti top tag Medium: "+slideSenzaTopTagMedium.size()+" +++++++++");
		
		//Unisco slides con tag High
		if (ss.size() == 0) {
			System.out.println("ERRORE");
			return new ArrayList<>();
		}

		Slide parent = ss.get(0);
		ss.remove(parent);
		result.add(parent);

		System.out.println("1");
		while (!ss.isEmpty()) {

			Slide possibleChild = null;
			int maxCost = 0;
			/*ArrayList<Slide> sss = new ArrayList<>();
			for (Slide s: ss) {
				if (s.tags.size() <= parent.tags.size()) {
				 	sss.add(s);
				}
			}
			System.out.println("Reduced from "+ ss.size() +"to "+ sss.size());*/
			for (Slide child: ss) {
				//System.out.println("+");
				//if (parent.tags.size() == child.tags.size()) {
				int cost = interestCost(parent, child);
				//int cost =  (int)(Math.random()*10);
				if (cost >= maxCost) {
					maxCost = cost;
					possibleChild = child;
				}
				//}
			}

			/*if (possibleChild == null || parent.tags.size()/2 != maxCost) {
				//Migliore slide possibile tra slide con meno tags
				for (Slide child: ss) {
					//System.out.println("*");
					if (parent.tags.size() != child.tags.size()) {
						//int cost = interestCost(parent, child);
						int cost =  (int)(Math.random()*10);
						if (cost >= maxCost) {
							maxCost = cost;
							possibleChild = child;
						}
					}
				}
			}*/

			//System.out.println("#");

			if (possibleChild == null) {
				throw new RuntimeErrorException(null);
			} 

			//System.out.println(result.toString());
			result.add(possibleChild);
			//System.out.println(result.toString());
			parent = possibleChild;
			ss.remove(possibleChild);

			//System.out.println(possibleChild);
			//System.out.println(ss.toString());
			possibleChild = null;
		}	
		
		//Collections.shuffle(slideSenzaTopTag);
		//result.addAll(slideSenzaTopTag);
		
		sss = (ArrayList<Slide>) slideSenzaTopTagMedium.clone();
		for (Slide s : slideSenzaTopTagMedium) {
			if(!s.tags.contains(fourTag) && !s.tags.contains(fiveTag) && !s.tags.contains(sixTag)) {
				sss.remove(s);
				slideSenzaTopTagLow.add(s);
			}
		}
		
		slideSenzaTopTagMedium = sss;
		System.out.println("+++++++++ Slides contenenti medium tag: "+slideSenzaTopTagMedium.size()+" +++++++++");
		System.out.println("+++++++++ Slides NON contenenti top tag low: "+slideSenzaTopTagLow.size()+" +++++++++");
		
		//Unisco slides con tag medi
		parent = slideSenzaTopTagMedium.get(0);
		slideSenzaTopTagMedium.remove(parent);
		result.add(parent);

		System.out.println("1");
		while (!slideSenzaTopTagMedium.isEmpty()) {

			Slide possibleChild = null;
			int maxCost = 0;
			/*ArrayList<Slide> sss = new ArrayList<>();
			for (Slide s: ss) {
				if (s.tags.size() <= parent.tags.size()) {
				 	sss.add(s);
				}
			}
			System.out.println("Reduced from "+ ss.size() +"to "+ sss.size());*/
			for (Slide child: slideSenzaTopTagMedium) {
				//System.out.println("+");
				//if (parent.tags.size() == child.tags.size()) {
				int cost = interestCost(parent, child);
				//int cost =  (int)(Math.random()*10);
				if (cost >= maxCost) {
					maxCost = cost;
					possibleChild = child;
				}
				//}
			}
			if (possibleChild == null) {
				throw new RuntimeErrorException(null);
			} 

			//System.out.println(result.toString());
			result.add(possibleChild);
			//System.out.println(result.toString());
			parent = possibleChild;
			slideSenzaTopTagMedium.remove(possibleChild);

			//System.out.println(possibleChild);
			//System.out.println(ss.toString());
			possibleChild = null;
		}
		System.out.println("============ 3 ===============");
		return result;
	}

	static int interestCost (Slide s1, Slide s2) {
		ArrayList<String> commontags;
		commontags = (ArrayList<String>) getCommonElements(s1.tags, s2.tags);
		//commontags = new ArrayList<String>(s1.tags);
		//commontags.retainAll(s2.tags);
		int common = commontags.size();
		//int common = (int)(Math.random()*10);
		int onlyfirst = s2.tags.size() - common;
		int onlysecond = s1.tags.size() - common;
		return Math.min(common, Math.min(onlyfirst, onlysecond));
	}

	public static <T> List<T> union(List<T> list1, List<T> list2) {
		Set<T> set = new HashSet<T>();

		set.addAll(list1);
		set.addAll(list2);

		return new ArrayList<T>(set);
	}

	public static <T> List<T> intersection(List<T> list1, List<T> list2) {
		List<T> list = new ArrayList<T>();

		for (T t : list1) {
			if(list2.contains(t)) {
				list.add(t);
			}
		}

		return list;
	}

	public static <T> List<T> getCommonElements(final List<T> listA, final List<T> listB) {

		final Map<Integer, List<T>> hashA = new HashMap<Integer, List<T>>(listA.size());

		final Iterator<T> a = listA.iterator();

		while (a.hasNext()) {

			final T item = a.next();

			List<T> subList = hashA.get(item.hashCode());

			if (subList == null) {

				subList = new ArrayList<T>(4);

				hashA.put(item.hashCode(), subList);
			}

			subList.add(item);
		}

		final List<T> results = new ArrayList<T>();

		final Iterator<T> i = listB.iterator();

		while (i.hasNext()) {

			final T item = i.next();

			final List<T> list = hashA.get(item.hashCode());

			if (list != null && list.contains(item))
				results.add(item);
		}

		return results;
	}

}