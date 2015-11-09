package project5;
/* Diego Guerra (dag3222) 
 * Oriana Wong (oyw58)
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CritterWorld {
//--------------Generating the World--------------	
	static void makeWorld() throws InvalidCritterException {
		for(int x=0; x<3; x++){
			Critter.makeCritter("project5.Craig");
		}
		for(int x=0; x<10; x++){
			Critter.makeCritter("project5.Algae");
		}
	}

//-------------Running the World---------------
	static void runWorld(int steps) {
		for(int x=0; x<steps;x++){
			Critter.worldTimeStep();//run all the time steps
		}
	}
	
//--------------Showing the world: CritterWorld----------
	static void displayWorld() { //where is 0,0? top left or bottom left?
		printBorder(); 
		Critter.displayWorld();
		printBorder();
		System.out.println();
	}
	private static void printBorder(){
		System.out.print("+");
		//Top
		for(int b=0; b<Params.world_width; b++){
			System.out.print("-");
		}
		System.out.print("+");
	}
}
