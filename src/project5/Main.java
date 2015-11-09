package project5;
import javafx.scene.canvas.Canvas;
/* Diego Guerra (dag3222) 
 * Oriana Wong (oyw58)
 */
public class Main {
	
public static void main(String[] args) throws InvalidCritterException {
		CritterGUI.canvas = new Canvas();
		CritterGUI.statsCanvas=new Canvas();
		CritterGUI.launch(CritterGUI.class, args);
		

	}
}
