import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

public class GlobalVars {
	public static final int randSize = 9999;
	public static final int copy = 5;
	public static int itemCount = 0;
	public static int loadLimit = 5;
	public static Map<String, Set<String>> cacheAlias = new TreeMap<String,Set<String>>();
	public static Map<String, Integer> aliasHash = new TreeMap<String, Integer>();
	public static Map<String, Map<String, Integer>> cacheMachine = new TreeMap<String, Map<String, Integer> >();
	
	public static int getRandInt(){
		int k;
		final Random r = new Random();
		k = r.nextInt(randSize + 1);
		return k;
	}
	
	public static void showCacheMachine(){
		say("Show cacheMachine");
		Set<String> caches = new HashSet<String>();
		caches = cacheAlias.keySet();
		for(String c : caches){
			Map<String,Integer> items = new HashMap<String, Integer>();
			sayn(c + "=");
			if(cacheMachine.containsKey(c)){
				items = cacheMachine.get(c);
				Iterator it = items.entrySet().iterator();
				while (it.hasNext()) {
					  Entry pair = (Entry) it.next();
					  sayn(pair.getKey() + ":" + pair.getValue() + ","  );
					}
				items.toString();
			}else{
				say("doent contain key "+ c);
			}
			
			/*Iterator it = items.entrySet().iterator();
			while (it.hasNext()) {
			  Map.Entry pair = (Map.Entry) it.next();
			  sayn(pair.getKey() + ":" + pair.getValue() + ","  );
			}*/
			say("");
		}

	}
	

	public static String getAliasValueByName(int aliasValue){
		for (Entry<String, Integer> entry : GlobalVars.aliasHash.entrySet()) {
		     //System.out.println(entry.getKey() + ":[" + entry.getValue() + "]");	
			if(entry.getValue() == aliasValue){
				return entry.getKey();
			}
		}
		return "";
	}
	public static void say(String s){
		System.out.println(s);
	}
	public static void sayn(String s){
		System.out.print(s);
	}
	public static void addItemToCacheMachine(String cName, Map<String, Integer> item){
		say("addItemToCacheMachine : " + cName + ":" + item.toString() );
		Map<String, Integer> items = new HashMap<String, Integer> ();
		if(cacheMachine.containsKey(cName)){
			items = cacheMachine.get(cName);
		}
		items.putAll(item);
		cacheMachine.put(cName, items);
	}
	
	public static boolean findItemInCacheMachine(String cName, String item){

		//say("findItemInCacheMachine cacheName " + cName + " item : " + item );
		if(cName.compareTo( "Server") == 0) {
			Set<String> caches = new HashSet<String>();
			caches = cacheAlias.keySet();
			for(String c : caches){
				Map<String,Integer> items = new HashMap<String, Integer>();
				items = cacheMachine.get(c);
				Iterator it = items.entrySet().iterator();
				while (it.hasNext()) {
				  Entry pair = (Entry) it.next();
				  say(item + ":" + pair.getKey());
				  if(item.compareTo( (String)(pair.getKey()) ) == 0) {
					  return true;
				  }
				}
			}
		}
		else{
			Map<String,Integer> items = new HashMap<String, Integer>();
			items = cacheMachine.get(cName);
			Iterator it = items.entrySet().iterator();
			while (it.hasNext()) {
			  Entry pair = (Entry) it.next();
			  //say(item + ":" + pair.getKey());
			  if(item.compareTo( (String) pair.getKey() ) == 0 ){
				  return true;
			  }
			  //say("not equal");
			}
		}
		
		return false;
	}
/*	
	public static void addItemToCacheMachine(String cName, Set<String> itms){
		Set<String> items = new HashSet<String> ();
		if(cacheMachine.containsKey(cName)){
			items = cacheMachine.get(cName);
		}
		items.addAll(itms);
		cacheMachine.put(cName, items);
	}
*/	
	
	public static int getRandInt(int n){
		int k;
		final Random r = new Random();
		k = r.nextInt(n + 1);
		return k;
	}
	
	public static void showCacheAlias(){
		say("showCacheAlias");
		for (Entry<String, Set<String>> entry : GlobalVars.cacheAlias.entrySet()) {
		     System.out.print(entry.getKey() + ":[ " ); //+ entry.getValue());
		     for(String s : (Set<String>)(entry.getValue()) ){
		    	 System.out.print(s + " ");
		     }
		     System.out.println("]");	    	    
		}
	}
	
	public static void showAliasHash(){
		say("showAliasHash");
		for (Entry<String, Integer> entry : GlobalVars.aliasHash.entrySet()) {
		     System.out.println(entry.getKey() + ":[" + entry.getValue() + "]");	    	    
		}
	}
	
	public static Set<String> getAliasForCache(String cName){
		Set<String> alias = new HashSet<String>();
		for (Entry<String, Set<String>> entry : GlobalVars.cacheAlias.entrySet()) {
		     for(String s : (Set<String>)(entry.getValue()) ){
		    	 alias.add(s);
		     }
		}
		return alias;
	}
	
	public static void deleteAliasForCache(String cName){
		//Set<String> alias = getAliasForCache(cName);
		//aliasHash.keySet().remove(alias);
		//Set<String> alias = new HashSet<String>();
		for (Entry<String, Set<String>> entry : GlobalVars.cacheAlias.entrySet()) {
		     for(String s : (Set<String>)(entry.getValue()) ){
		    	 //alias.add(s);
		    	 aliasHash.remove(s);
		     }
		}
		
	}
	
	public static void deleteCacheEntry(String cName){
		cacheAlias.remove(cName);
	}
	
	public static String getCacheForAlias(String alias){
		//String cName = new String();
		if(alias.compareTo("Server") == 0 ){
			return "Server";
		}
		for (Entry<String, Set<String>> entry : GlobalVars.cacheAlias.entrySet()) {
		     //System.out.print(entry.getKey() + ":[ " ); //+ entry.getValue());
		     for(String s : (Set<String>)(entry.getValue()) ){
		    	 //System.out.print(s + " ");
		    	 if(alias == s){
		    		 return entry.getKey();
		    	 }
		     }
		     //System.out.println("]");	    	    
		}
		return "";
	}
}
