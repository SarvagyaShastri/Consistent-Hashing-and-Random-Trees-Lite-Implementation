import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.text.html.HTMLDocument.Iterator;

public class RandomBinaryTree<T> {
	private Node<T> root;
	private List<Node<T>> leafNodes;
	private int lowestNodeValue;
	
	//@SuppressWarnings("unchecked")
	public RandomBinaryTree(){
		NodeData dNode = new NodeData("Server", GlobalVars.randSize/2);
		this.root = new Node(dNode);
	}
	
	public Node<T> getRoot(){
		return this.root;
	}
	
	public RandomBinaryTree(ArrayList<Node<T>> cacheNodes){
		this();
		root.buildBinaryTree(cacheNodes);
		//root.traverseInOrderBinaryTree();
		ArrayList<Integer> n = new ArrayList<Integer> ();
		root.traverseInOrderBinaryTree(n);
		lowestNodeValue = n.get(0);
		//System.out.println("LowestNode Vlaue : " +  lowestNodeValue);
		List<Node<T>> lNodes = new ArrayList<Node<T>>();
		root.collectLeafNode(lNodes);
		leafNodes = lNodes ;
		//root.collectLeafNode(leafNodes);
		
	}
	
	
	public void buildRandomBinaryTree(List<Node<T>> cacheNodes){
		root.buildBinaryTree(cacheNodes);
		List<Node<T>> lNodes = new ArrayList<Node<T>>();
		root.collectLeafNode(lNodes);
		leafNodes = lNodes ;
	}
	
	public void showLeafNodes(){
		for(Node<T> leaf : leafNodes){
			((NodeData) leaf.getData()).showNodeData();
		}
	}
	
	public void say(String s){
		System.out.println(s);
	}

	public void distributeItems(ArrayList<NodeData> iNodes){
		for(NodeData n : iNodes){
			root.setItems(n);
			int nodeValue = 0;
			int optimalNodeValue = GlobalVars.randSize;
			int flag = 0;
			for(Node<T> leaf : this.leafNodes){
				nodeValue = leaf.findBiggerNodeWithSmallestDiff(n);
				if(nodeValue > 0 && nodeValue <= optimalNodeValue){
					optimalNodeValue = nodeValue;
					flag = 1;
				}
			}
			System.out.println("------------------------___-----------");
			System.out.println("Optimal Value for a node : " + n.getValue() + " : " + optimalNodeValue);
			System.out.println("------------------------___-----------");
			String alias = new String();
			if(flag == 1){
				alias = root.searchAndSetItem(n, optimalNodeValue);
				
			}else{
				alias = root.searchAndSetItem(n, lowestNodeValue);
				
			}
			Map<String, Integer> item = new HashMap<String, Integer>();
			
			item.put(n.getName(), n.getValue());
			say("Calling getCacheForAlias");
			String cName = GlobalVars.getCacheForAlias(alias);
			say("cacheName : " + cName);
			GlobalVars.addItemToCacheMachine(cName, item );			
		}
	}
	
	public String getPage(String pName){
		Node<T> node = this.leafNodes.get(GlobalVars.getRandInt(leafNodes.size() - 1));
		say("Page Request " + pName);

		NodeData passToDown = new NodeData();
		ArrayList<String> pageData = new ArrayList<String>(); //new String();
		boolean dataFound = false;
		node.getItemFromTree(pName, passToDown, pageData, dataFound);
		String p = new String();
		//say("dataFound : " + dataFound);
		for(String s : pageData){
			p = p + s;
		}
		return pName + " " + p;
	}

	public void removeCache(String cName){
		Set<String> aliases = new HashSet<String>();
		aliases = GlobalVars.cacheAlias.get(cName);
		for(String alias : aliases){
			int aliasValue = GlobalVars.aliasHash.get(alias);
			System.out.println("alias " + alias + " : " + aliasValue);
			root.removeNode(aliasValue);
		}
		GlobalVars.deleteAliasForCache(cName);
		GlobalVars.deleteCacheEntry(cName);
	}
	
	public void addCache(String cName){
		
	}
	
	public static void main(String[] str){
		
		//List<Node<NodeData>> cac;
		String cName[] = {"A", "B", "C"} ;
		CacheMacs cache = new CacheMacs(cName); // Randomly creating
		//System.out.println("Showing cache Nodes");
		//cache.showCacheNodes();
		//System.out.println("Binary Tree Object");
		RandomBinaryTree<NodeData> r = new RandomBinaryTree<NodeData>(cache.getCacheNodes());
		System.out.println("Showing leaf Nodes");
		r.showLeafNodes(); // leaves that are open to clients to access the server
		
		ItemNode iNodes = new ItemNode(20);
		//iNodes.showItemNode();
		r.distributeItems(iNodes.getItemNodes());//
		
		//ArrayList<Integer> nValues = new ArrayList<Integer>() ;
		//System.out.println("InOrdertraversal");
		//r.getRoot().traverseInOrderBinaryTree(nValues);
		
		//GlobalVars.showCacheAlias();
		//GlobalVars.showAliasHash();
		
		//r.removeCache("A");
		
		//System.out.println("After removal of node A");
		
		//System.out.println("InOrdertraversal");
		//r.getRoot().traverseInOrderBinaryTree(nValues);
		//GlobalVars.showCacheAlias();
		//GlobalVars.showAliasHash();
		GlobalVars.showCacheMachine();
		
		
		String PageData = r.getPage("P2");
		System.out.println(PageData);
		
	}
}
