import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class Node<T> {

   private T data;
   private Map<String, Node<T>> children;
   private Node<T> parent;
   private ItemNode items;
   private Map<String, Integer> factor ; 


   public Node() {
       //super();
       //children = new ArrayList<Node<T>>();
       children = new HashMap<String, Node<T>>();
       items = new ItemNode();
       NodeData data1 = new NodeData();
       setData((T) data1);
       factor = new HashMap<String, Integer>();
       //this.factor = 0;
   }

   public Node(T data) {
       this();
       setData(data);
   }
   
   public void incFactor(String item){
	   //this.factor += 1;
	   //String item = new String();
	   if(factor.containsKey(item)){
		   int value = factor.get(item);
		   value += 1;
		   factor.put(item, value);

	   }else{
		   factor.put(item, 1);
	   }
	   
   }

   public boolean checkFactor(String item){
	   if(factor.containsKey(item)){
		   int value = factor.get(item);
		   if(value > GlobalVars.loadLimit){
			   return true;
		   }
	   }   
	   return false;
   }
   

   public Node<T> getParent() {
       return this.parent;
   }
   

   public void setItems(NodeData n){
	   items.setItemNode(n);
   }
   
   public void setItems(ArrayList<NodeData> n){
	   items.setItemNode(n);
   }
   
   public ItemNode getItems(){
	   return this.items;
   }
   
   public Map<String, Node<T>> getChildren() {
       return this.children;
   }
   
   public boolean leftChildExists(){
	   if((boolean)this.children.containsKey("l")){
		   return true;
	   }
	   return false;
   }
   
   public boolean hasOneChildOnly(){
	   if( leftChildExists() && rightChildExists() ){
		   return false;
	   }	   
	   return true;
   }
   
   public void deleteLeftChild(){
	   this.children.remove("l");
   }
   
   public void deleteRightChild(){
	   this.children.remove("r");
   }
   
   public Node<T> getLeftChild(){
	   return this.children.get("l");
   }
   
   public void setLeftChild(Node<T> leftNode){
	   this.children.put("l", leftNode);
   }
   
   public Node<T> getRightChild(){
	   return this.children.get("r");
   }
   
   public void setRightChild(Node<T> rightNode){
	   this.children.put("r", rightNode);
   }
   
   public boolean rightChildExists(){
	   if((boolean)this.children.containsKey("r")){
		   return true;
	   }
	   return false;
   }
   
   

   public int getNumberOfChildren() {
       return getChildren().size();
   }

   public boolean hasChildren() {
       return (getNumberOfChildren() > 0);
   }


   public T getData() {
       return this.data;
   }

   public void setData(T data) {
       this.data = data;
   }

   public String toString() {
       return getData().toString();
   }


   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((data == null) ? 0 : data.hashCode());
      return result;
   }

	private static int getRandInt(int n){
		int k;
		final Random r = new Random();
		k = r.nextInt(n + 1);
		return k;
	}
	
	public void traverseInOrderBinaryTree(ArrayList<Integer> n){
		if(this.leftChildExists()){
			this.getLeftChild().traverseInOrderBinaryTree(n);
		}
		((NodeData) this.getData()).showNodeData();
		items.showItemNode();
		n.add( ( (NodeData) (this.getData()) ) . getValue() );
		if(this.rightChildExists()){
			this.getRightChild().traverseInOrderBinaryTree(n);
		}
	}
	

	
	public void traversePreOrderBinaryTree(){
		((NodeData) this.getData()).showNodeData();
		if(this.leftChildExists()){
			this.getLeftChild().traversePreOrderBinaryTree();
		}
		if(this.rightChildExists()){
			this.getRightChild().traversePreOrderBinaryTree();
		}
	}
	
	public void setNodeInBT(Node<T> newNode){
		
		int currentNodeValue = ((NodeData) this.getData()).getValue();
		int newNodeValue = ((NodeData) newNode.getData()).getValue();
		if(newNodeValue < currentNodeValue){
			if( this.leftChildExists() ){
				this.getLeftChild().setNodeInBT(newNode);
			}else{
				newNode.parent = this;
				this.setLeftChild(newNode);
			}
		}
		else{
			if( this.rightChildExists() ){
				this.getRightChild().setNodeInBT(newNode);
			}else{
				newNode.parent = this;
				this.setRightChild(newNode);
			}
		}
		
	}
	
	public void buildBinaryTree(List<Node<T>> nodes){
		int index = nodes.size();
		//int rand = GlobalVars.getRandInt(index);
		
		int counter = index;
		
		while(counter > 0){
			Node<T> n = new Node<T>();
			//rand = getRandInt(counter);
			n = (Node<T>) nodes.remove(0);
			this.setNodeInBT(n);	
			counter--;
		}
				
				

	}
	
	public void collectLeafNode(List<Node<T>> leafNodes){
		
		if(this.leftChildExists()){
			this.getLeftChild().collectLeafNode(leafNodes);
		}
		if(this.rightChildExists()){
			this.getRightChild().collectLeafNode(leafNodes);
		}
		if(this.getChildren().isEmpty() ){
			leafNodes.add((Node<T>) this);		
		}
		
	}
	
	public void traverseToRoot(){
		if(this.parent == null){
		//if(((NodeData) this.getData()).getName().equals("root")){
			System.out.println("This is root : " + ((NodeData) this.getData()).getName() );
		}
		else{
			((NodeData) this.getData()).showNodeData();
			this.parent.traverseToRoot();
		}
	}

	/*	
	public void moveLeftAndSet(NodeData n){
		if(this.leftChildExists()){
			this.getLeftChild().moveLeftAndSet(n);
		}else{
			this.items.setItemNode(n);
		}
	}
	
	public void searchAndSetItem(Node<T> node, NodeData n){
		if(((NodeData) this.data).compareNodeValue(n) == 1){   // item value is smaller than node value, so turn left
			if(this.leftChildExists()){
				this.getLeftChild().searchAndSetItem(this, n);
			}else{
				if(this.parent == null && this.rightChildExists() ){  // current node is root node					
					this.getRightChild().moveLeftAndSet(n);
				}else{
					this.items.setItemNode(n);
				}
			}
			
		}else if(((NodeData) this.data).compareNodeValue(n) == -1){
			this.searchAndSetItem(this, n);
		}
	}
*/	
	
	public void traverseToNode(Node<T> leafNode){
		
	}
	
	public String searchAndSetItem(NodeData iNode, int value){
		if(  value > ( (NodeData)this.getData() ).getValue()  ){
			return this.getRightChild().searchAndSetItem(iNode, value);
		}
		else if(  value < ( (NodeData)this.getData() ).getValue()  ){
			return this.getLeftChild().searchAndSetItem(iNode, value);
		}
		else{
			this.items.setItemNode(iNode);
			return ((NodeData)(this.getData())).getName();
		}
		
	}
	
	public int findBiggerNodeWithSmallestDiff(NodeData n){
		int difference = ((NodeData) (this.getData() )).getValue() - n.getValue();
		int value = ((NodeData) (this.getData() )).getValue();
		//System.out.println("------ findBiggerNodeWithSmallestDiff -------- ");
		//System.out.println(difference + ":" + ((NodeData) (this.getData() )).getValue() + " : " +  n.getValue());
		//@SuppressWarnings("unchecked")
		Node<NodeData> leaf = (Node<NodeData>) this;
		while(leaf.parent != null){
			int diff = ((NodeData) (leaf.getData() )).getValue() - n.getValue();
			//System.out.println(difference + ":" + diff + ":" + ((NodeData) (leaf.getData() )).getValue() + " : " +  n.getValue());
			if(difference > diff && diff >= 0){
				difference = diff;
				value = ((NodeData) (leaf.getData() )).getValue() ;
			}
			if(difference < 0){
				if(diff > 0){
					difference = diff;
					value = ((NodeData) (leaf.getData() )).getValue() ; 
				}
			}else{
				if(difference > diff && diff > 0){
					difference = diff;
					value = ((NodeData) (leaf.getData() )).getValue() ; 		
				}
			}
			leaf = leaf.parent;
			if(leaf.parent == null){
				break;
			}
		}
		
		if(difference < 0){
			//System.out.println(value + "------ Exit difference less than 0 findBiggerNodeWithSmallestDiff -------- ");
			return -1;
		}
		//System.out.println(value + "------ Exit findBiggerNodeWithSmallestDiff -------- ");
		return value;
	}
	
	public void say(String s){
		System.out.println(s);
	}
	
	public void addItemsToNextMachine(ItemNode itms){
		if(this.rightChildExists()){
			((ItemNode)(this.getRightChild().items)).setItemNode(itms.getItemNodes());
		}
		else if(((NodeData) this.parent.getData()) . getValue() > ((NodeData) this.getData()) . getValue()){
			((ItemNode)(this.parent.items)).setItemNode(itms.getItemNodes());
		}
	}
	
	public void removeNode(int value){
		if(  value > ( (NodeData)(this.getData()) ).getValue()  ){
			say("Turn right");
			this.showNodeData();
			this.getRightChild().removeNode(value);
		}
		else if(  value < ( (NodeData)(this.getData()) ).getValue()  ){
			this.showNodeData();
			say("Turn left");
			this.getLeftChild().removeNode(value);
		}
		else{
			this.showNodeData();
			ItemNode itm = new ItemNode();
			itm = this.getItems();
			this.addItemsToNextMachine(itm);
			
			if( this.getChildren() == null ){
				this.say("This node has no children");
				//this.parent.children = new HashMap<String, Node<T>>();
				if(this.parent.getNodeData() > this.getNodeData() ){ // left child
					this.parent.deleteLeftChild();
					this.parent = null;
				}else{
					this.parent.deleteRightChild();
					this.parent = null;
				}
			}
			else if( this.hasOneChildOnly() ){
				say("has only one child");
				if(this.leftChildExists()){
					this.say("This node has left child");
					if(this.parent.getNodeData() > this.getNodeData() ){ // left child
						say("This node is the left child" + this.parent.getNodeData() + " : "+  this.getNodeData() );
						Node<T> par1 = this.parent;
						Node<T> cld = this.getLeftChild(); 
						this.parent.setLeftChild(this.getLeftChild());
						cld.parent = par1;
					}
					else if(this.parent.getNodeData() < this.getNodeData() ){ // right child
						say("This node is the right child" + this.parent.getNodeData() + " : " +  this.getNodeData());
						Node<T> par1 = this.parent;
						Node<T> cld = this.getLeftChild(); 
						this.parent.setRightChild(this.getLeftChild());
						cld.parent = par1;
					}
				}
				else if(this.rightChildExists()){
					this.say("This node has right child");
					if(this.parent.getNodeData() > this.getNodeData() ){ // left child
						say("This node is the left child" + this.parent.getNodeData() + " : " +  this.getNodeData() );
						Node<T> par1 = this.parent;
						Node<T> cld = this.getRightChild(); 
						this.parent.setLeftChild(this.getRightChild());
						cld.parent = par1;
					}
					else if(this.parent.getNodeData() < this.getNodeData() ){ // right child
						say("This node is the right child" + this.parent.getNodeData() + " : " + this.getNodeData() );
						Node<T> par1 = this.parent;
						Node<T> cld = this.getRightChild(); 
						this.parent.setRightChild(this.getRightChild());
						cld.parent = par1;
					}
				}
			}
			else if(this.leftChildExists() && this.rightChildExists() ){
				Node<T> deletedNode = new Node<T>();
				say("Has both right and left child");
				say("Calling deleteNextHigherNode");
				say("from"); this.getRightChild().showNodeData();
				deletedNode = this.getRightChild().deleteNextHigherNode();
				say("This is returned"); deletedNode.showNodeData();
				say("Called from"); this.showNodeData();
				this.setData(deletedNode.getData());
				this.items.setItemNode(deletedNode.items.getItemNodes());
			}
		}
	}
	
	public Node<T> deleteNextHigherNode(){
		if(this.leftChildExists()){
			say("Turned left");
			this.showNodeData();
			return this.getLeftChild().deleteNextHigherNode();
		}
		else{
			this.showNodeData();
			say("No left child exists");
			if(this.rightChildExists()){
				say("Now right child exists, below");
				this.getRightChild().showNodeData();
				if(this.parent.getNodeData() < this.getNodeData() ){ // right child
					say("This node is the right child");
					this.parent.setRightChild(this.getRightChild());
					return this;
				 }
				 else if(this.parent.getNodeData() > this.getNodeData() ){ // left child
					 say("This node is the left child");
					 this.parent.setLeftChild(this.getRightChild());
					 return this;
				 }
			}
			else{
				say("No right child exists");
				 if(this.parent.getNodeData() < this.getNodeData() ){ // right child
					 say("This node is the right child");
					 //Node<T> temp = new Node<T>();
					 //this.parent.setRightChild(temp);
					 this.parent.deleteRightChild();
					 return this;
				 }
				 else if(this.parent.getNodeData() > this.getNodeData() ){ // left child
					 say("This node is the left child");
					 //Node<T> temp = new Node<T>();
					 //this.parent.setLeftChild(temp);
					 this.parent.deleteLeftChild();
					 return this;
				 }
			}
		}
		return this;
	}
	
	public void getItemFromTree(String item, NodeData passToDown, ArrayList<String> flowData, boolean dataFound ){
		
		//say("flowData" + flowData);
		//say("dataFound : " + dataFound);
		String aliasName = ((NodeData)(this.getData())) . getName();
		String cacheName = GlobalVars.getCacheForAlias(aliasName);
		if(GlobalVars.findItemInCacheMachine(cacheName, item)){
			passToDown = (NodeData) this.getData();
			//flowData += "Item found in alias : " + aliasName + "Cache :" + cacheName ;
			say("Item " + item + " found in alias : " + aliasName + " which belongs to  Cache :" + cacheName+" " );
			flowData.add("Item " + item + " found in alias : " + aliasName + " which belongs to Cache :" + cacheName +" ");
			dataFound = true;
			//return 
		}
		else{
			//return "Item NOTFOUND in alias : " + aliasName + "Cache :" + cacheName  + 
			//flowData = flowData + "----" + "Item NOTFOUND in alias : " + aliasName + "Cache :" + cacheName ;
			say("Item NOTFOUND in alias : " + aliasName + "which belongs to Cache :" + cacheName+" ");
			if(this.parent == null){
				//flowData += "Data NOTFOUND in server";
				flowData.add("Data NOTFOUND in server");
				dataFound = false;
				return;
			}
			this.incFactor(aliasName);
			flowData.add("Item " + item + " NOTFOUND in alias : " + aliasName + " which belongs to Cache :" + cacheName +" ");
			this.parent.getItemFromTree(item, passToDown, flowData, dataFound);
			if(this.checkFactor(item) && dataFound){
				Map<String, Integer> data = new HashMap<String, Integer>();
				data.put(passToDown.getName(), passToDown.getValue());
				GlobalVars.addItemToCacheMachine(cacheName, data);
			}	
		}
		//say("dataFound : " + dataFound);
	}
	
	
	public int getNodeData(){
		return ( (NodeData)(this.getData()) ).getValue();
	}

	public void showNodeData(){
		( (NodeData)(this.getData()) ) . showNodeData();
	}
	
	public static void main(String []str){
	   List<Node<NodeData>> nodes = new LinkedList<Node<NodeData>>();
	   
	   NodeData rootData = new NodeData("root", 500);
	   Node<NodeData> root = new Node<NodeData>(rootData);
	   
	   for(int i=0; i<10; i++){
		   int rand = getRandInt(999);
		   String s = "C" + Integer.toString(i+1);
		   NodeData x = new NodeData(s, rand);
		   Node<NodeData> r = new Node<NodeData>(x);
		   nodes.add(r);
	   }

	   System.out.println("size : " + nodes.size() );

	   root.buildBinaryTree(nodes);
	   
	   //List<Node<NodeData>> collectData = new ArrayList<Node<NodeData>>();
	   System.out.println("Printing PreOrder");
	   root.traversePreOrderBinaryTree();
	   
	   
	   System.out.println("Printing atlast");
	   
	   List<Node<NodeData>> leafNodes = new ArrayList<Node<NodeData>>();
	   root.collectLeafNode(leafNodes);
	   
	   for(Node<NodeData> leaf : leafNodes){
		   leaf.getData().showNodeData();
	   }
	   
	   int r = getRandInt(leafNodes.size() - 1) ;
	   System.out.println("Size and random " + r + " : " + leafNodes.size());
	   System.out.println("Traverse to root ");
	   leafNodes.get(r).traverseToRoot();
	   
   }
}

