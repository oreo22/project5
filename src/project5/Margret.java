package project5;
/* Diego Guerra (dag3222) 
 * Oriana Wong (oyw58)
 */

/*Description: Margret is Margret.
 *doTimeStep: Margret runs in random directions if the dice roll is 0. Otherwise, they do nothing
 * Fight: Margret fights Craig if the dice roll is 0 or 1. 
 * Otherwise, they fight everyone.
 * Stats: The stats tell you how many Margrets there are in the world. 
 */
public class Margret extends Critter{
	public String toString() { return "M"; }

	@Override
	public void doTimeStep() {
		int action=Critter.getRandomInt(2);
		int direction=Critter.getRandomInt(8);
		if( action==0){
			run(direction);
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