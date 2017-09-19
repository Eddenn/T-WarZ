package work;

public class Cell {
	
	private int id;

	public Cell(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String toString(){
		return "["+this.id+"]";
	}
}
