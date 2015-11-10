package project5;
/* Diego Guerra (dag3222) 
 * Oriana Wong (oyw58)
 */

/*Description: Margret is Margret.
 *doTimeStep: Margret runs in random directions if the dice roll is 0. If she has slain enough Critters,
 * she will reproduce with her strongest genes
 * Otherwise, they do nothing
 * Fight: Margret always fights everyone 
 * Stats: The stats tell you how many Margrets there are in the world. 
 */
public class Margret extends Critter{
	public String toString() { return "M"; }

	@Override
	public void doTimeStep() {
		int action=Critter.getRandomInt(2);
		int direction=Critter.getRandomInt(8);
		if( action==0){
			run(direction%8);
		}
		if(this.getEnergy()>500){
			this.reproduce(new Margret(), (direction+1)%8);
		}
	}

	@Override
	public boolean fight(String oponent) {
	
		return true; //always fights
	}

	public static String runStats(java.util.List<Critter> Margrets) {
		
		return "There are " + Margrets.size() + " Margret(s) hunting down students";
		
	}

	@Override
	public CritterShape viewShape() {
		return CritterShape.TRIANGLE;
	}
	
	public javafx.scene.paint.Color viewFillColor() { return javafx.scene.paint.Color.LAVENDER; }
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.DARKSLATEBLUE; }

}