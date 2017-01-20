import java.util.ArrayList;

public class ItemNode {
	private ArrayList<NodeData> itemNodes; 
	
	public ItemNode(){
		itemNodes = new ArrayList<NodeData>();
	}
	
	public ItemNode(int n){
		this();
		for(int i=GlobalVars.itemCount; i<GlobalVars.itemCount + n; i++){
			NodeData data = new NodeData();
			String name = "P" + Integer.toString(i+1);
			data.setNodeData(name, GlobalVars.getRandInt());
			itemNodes.add(data);
		}
		GlobalVars.itemCount += n;
	}
	
	public ArrayList<NodeData> getItemNodes(){
		return this.itemNodes;
	}
	
	public void setItemNode(NodeData n){
		this.itemNodes.add(n);
	}
	
	public void setItemNode(ArrayList<NodeData> n){
		this.itemNodes.addAll(n);
	}
	
	public void showItemNode(){
		System.out.println("------------");
		for(NodeData n : itemNodes){
			n.showNodeData();
		}
		System.out.println("------------");
	}

}
