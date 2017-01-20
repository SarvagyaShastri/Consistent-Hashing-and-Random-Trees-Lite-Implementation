import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CacheMacs {
	//private List<Node<T>> cacheNodes;
	private ArrayList<Node<NodeData>> cacheNodes;

	public CacheMacs(){
		cacheNodes = new ArrayList<Node<NodeData>>();	
	}
	public CacheMacs(ArrayList<String> cacheName){
		//cacheNodes = new ArrayList<Node<T>>();
		this();
		//int k = 0;
		for(String s : cacheName){
			Set<String> aliasList = new HashSet<String>();
			for(int i=0;i<GlobalVars.copy;i++){
				String name = s + Integer.toString(i+1);
				int value = GlobalVars.getRandInt();
				//int value = arr[k++];
				NodeData itemNode = new NodeData(name, value );
				Node<NodeData> node = new Node<NodeData>(itemNode);
				cacheNodes.add(node);
				GlobalVars.aliasHash.put(name, value);
				aliasList.add(name);
			}
			GlobalVars.cacheAlias.put(s, aliasList);
		}
	}
	public CacheMacs(String[] cacheName){
		//cacheNodes = new ArrayList<Node<T>>();
		this();
		//int k = 0;
		for(String s : cacheName){
			Set<String> aliasList = new HashSet<String>();
			for(int i=0;i<GlobalVars.copy;i++){
				String name = s + Integer.toString(i+1);
				int value = GlobalVars.getRandInt();
				//int value = arr[k++];
				NodeData itemNode = new NodeData(name, value);
				Node<NodeData> node = new Node<NodeData>(itemNode);
				cacheNodes.add(node);
				GlobalVars.aliasHash.put(name, value);
				aliasList.add(name);
			}
			GlobalVars.cacheAlias.put(s, aliasList);
		}
	}
	
	
	public ArrayList<Node<NodeData>> getCacheNodes(){
		return this.cacheNodes;
	}
	
	public void showCacheNodes(){
		for(Node<NodeData> n : cacheNodes){
			n.getData().showNodeData();
		}
	}
}
