package project5;
/* Diego Guerra (dag3222) 
 * Oriana Wong (oyw58)
 */

/*Description: Jeho are critters that have different characteristics based on their degree.
 * Jeho starts out with a masters degree. Based on his energy, he can lose his degrees or gain more.
 * doTimeStep:Jeho are assigned a degree based on their energy. The higher the energy level, the higher degree. 
 * Based on a dice roll, Jeho can either run or walk in a random direction. Otherwise, they can reproduce. 
 * Fight: Jeho can fight Craig, TA's, and students if they are at a PHD level. At Masters level, they can fight the other TA's (Cassidy and Margret) and students
 * Otherwise, at Bachelors, they can only fight students if their dice roll is at least 3. Otherwise, they reproduce and walk away.
 * Jeho always fights Algae.
 * Stats: The stats tell you how many Jeho of each type of degree are in the world. 
 */
public class Jeho extends Critter{
	private String degree;
	
	public Jeho(){
		degree = "masters";
	}
	
	public String toString() { return "J"; }

	@Override
	public void doTimeStep() {
		if(getEnergy()<=50){
			this.degree = "bachelors";
		}
		else if(getEnergy()<=75){
			this.degree = "masters";
		}
		else if(getEnergy() >= 120){
			this.degree = "phd";
		}
		int action=Critter.getRandomInt(4);
		int direction=Critter.getRandomInt(8);
		if( action==0){
			run(direction);
		}
		else if (action==1){
			walk(direction);
		}
		else{
				reproduce(new Jeho(), Critter.getRandomInt(8));
			
		}
		
	}

	@Override
	public boolean fight(String oponent) {
		int roll= Critter.getRandomInt(5);
		if(oponent.equals("project5.Craig") && degree.equals("phd")){
			return true;
		}
		else if((oponent.equals("project5.Cassidy") || oponent.equals("project5.Margret")) && (degree.equals("masters") || degree.equals("phd")) ){
			return true;
		}
		else if(oponent.equals("project5.Student")){
			if(degree.equals("phd") || degree.equals("masters")){
				return true;
			}
			else if(roll>=3){
				return true;
			}
				reproduce(new Jeho(), Critter.getRandomInt(8));
				walk(0);
				return false;
			}
		else if(oponent.equals("project5.Algae")){
			return true;
		}
		else{
			return false; 
		}
	}
	public static String runStats(java.util.List<Critter> Jehos) {
		int bachelors =0;
		int masters =0;
		int phd =0;
		for(Object obj: Jehos){
			Jeho jeho = (Jeho) obj;
			if((jeho.degree).equals("bachelors")){
				bachelors++;
			}
			else if((jeho.degree).equals("masters")){
				masters++;
			}
			else{
				phd++;
			}
		}
		String jehoStats="There are " + bachelors + " Jeho(s) working on their bachelors" + "\n"+
		"There are " + masters + " Jeho(s) working on their masters" + "\n"+
		"There are " + phd + " Jeho(s) working on their phd and can take down Craig";
		return jehoStats;
	}

	@Override
	public CritterShape viewShape() {
		return CritterShape.SQUARE;
	}

	
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.DARKRED; }
	public javafx.scene.paint.Color viewFillColor() { return javafx.scene.paint.Color.DARKBLUE; }
}