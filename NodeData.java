public class NodeData {
	private String name;
	private int value;
	
	public NodeData(){
		name = "";
		value = 0;
	}
	public NodeData(String str, int val){
		this.name = str;
		this.value = val;
	}
	public int getValue(){
		return this.value;
	}
	public String getName(){
		return this.name;
	}
	
	public void setValue(int val){
		this.value = val;
	}
	public void setName(String name){
		this.name = name;
	}
	public void showNodeData(){
		System.out.println(this.name + " : " + this.value);
	}
	
	public int compareNodeValue(NodeData d){
		if(this.value > d.value){
			return 1;
		}else if(this.value < d.value){
			return -1;
		}
		return 0;
	}
	
	public void setNodeData(String name, int value){
		this.name = name;
		this.value = value;
	}
}
