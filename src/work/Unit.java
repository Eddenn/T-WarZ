package work;

public abstract class Unit extends Cell{
	
	private int x;
	private int y;
	private int movePts;
	private int healthPts;
	private int defaultMovePts;
	private int defaultAttackPts;
	private int attackPts;
	private int attackDamage;
	private int attackRange;

	public Unit(int id,int x,int y,int movePts,int healthPts,int attackPts,int attackDamage,int attackRange) {
		super(id);
		this.x = x;
		this.y = y;
		this.movePts = movePts;
		this.defaultMovePts = movePts;
		this.healthPts = healthPts;
		this.attackPts = attackPts;
		this.defaultAttackPts = attackPts;
		this.attackDamage = attackDamage;
		this.attackRange = attackRange;
		
	}
	
	public String getName(){
		return this.getClass().getName().substring(5);
	}
	
	public int getDefaultMovePts(){
		return defaultMovePts;
	}
	public int getDefaultAttackPts(){
		return defaultAttackPts;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public int getMovePts() {
		return this.movePts;
	}
	public int getHealthPts() {
		return this.healthPts;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y ;
	}
	public void setMovePts(int movePts) {
		this.movePts = movePts;
	}
	public void setHealthPts(int healthPts) {
		this.healthPts = healthPts;
	}
	public int getAttackPts() {
		return attackPts;
	}
	public void setAttackPts(int attackPts) {
		this.attackPts = attackPts;
	}
	public int getAttackDamage() {
		return attackDamage;
	}
	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
	}
	public int getAttackRange() {
		return attackRange;
	}
	public void setAttackRange(int attackRange) {
		this.attackRange = attackRange;
	}
	
	public String toString(){
		return super.toString()+this.getClass().getName()+"("+this.x+"/"+this.y+")";
	}
}
