package project5;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/* Diego Guerra (dag3222) 
 * Oriana Wong (oyw58)
 */
import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import project5.Critter;
import project5.CritterGUI;
import project5.Params;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */
public abstract class Critter{
	private int energy = 0;	
	private int x_coord;
	private int y_coord;
	private boolean move_flag;
	private static java.util.Random rand = new java.util.Random();
	private static ArrayList<Critter> population=new ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	private static boolean fightPhase = false;
	
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	
	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape(); 
	
	protected String look(int direction) {
		/*If the location is unoccupied, then look returns null. If the location is occupied, 
		 * then look returns the toString() result for the Critter in that location.
		If a critter invokes look during doTimeStep, then the result of calling look is based 
		on the old position of the critter. If a critter invokes look during its fight method, 
		then the result of calling look is based on the most current up-to-date information.
		*/
		int[] coord=findNewLocation(direction,1);
		for(int z=0; z<Critter.population.size(); z++){
			if(Critter.population.get(z).x_coord == coord[0] && Critter.population.get(z).y_coord == coord[1]){
				return population.get(z).toString();
			}
		}
		return null;
	}
	protected String look2(int direction) {
		/*look2() looks at the location that’s two grid locations away in the same direction.
		If a critter invokes look during doTimeStep, then the result of calling look is based 
		on the old position of the critter. If a critter invokes look during its fight method, 
		then the result of calling look is based on the most current up-to-date information.*/
		int[] coord=findNewLocation(direction,2);
		for(int z=0; z<Critter.population.size(); z++){
			if(Critter.population.get(z).x_coord == coord[0] && Critter.population.get(z).y_coord == coord[1]){
				return population.get(z).toString();
			}
		}
		return null;
	}
	
	
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}

	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	protected int getEnergy() { return energy; }
	
	
	private boolean adjacentLocationOccupied(int x, int y){
		for(int z=0; z<Critter.population.size(); z++){
			if(Critter.population.get(z).x_coord == x && Critter.population.get(z).y_coord == y){
				return true;
			}
		}
		return false;
	}
	
	private int[] findNewLocation(int direction, int step){
		int[] newCoord=new int[2];
		newCoord[0] = x_coord;
		newCoord[1] = y_coord;
		switch (direction){
		case 0: if(y_coord == 0){ newCoord[1] = Params.world_height-step;}else{newCoord[1] = (y_coord-step)%Params.world_height;}			  break;
        case 1: newCoord[0]=(x_coord+step)%Params.world_width; if(y_coord == 0){ newCoord[1] = (Params.world_height-1);}else{newCoord[1] = (y_coord-step)%Params.world_height;} break;
        case 2: newCoord[0]=(x_coord+step)%Params.world_width; 			  break;
        case 3: newCoord[0]=(x_coord+step)%Params.world_width; newCoord[1]=(y_coord+step)%Params.world_height; break;
        case 4: newCoord[1]=(y_coord+step)%Params.world_height; 			  break;
        case 5: if(x_coord == 0){newCoord[0] = Params.world_width-step;}else{newCoord[0] = (x_coord-step)%Params.world_width;} 
        		newCoord[1]=(y_coord+step)%Params.world_height; break; //what if it is at 0
        case 6: if(x_coord == 0){newCoord[0] = Params.world_width-step;}else{newCoord[0] = (x_coord-step)%Params.world_width;} break;
        case 7: newCoord[0]=(x_coord+step)%Params.world_width; if(y_coord == 0){ newCoord[1] = (Params.world_height-1);}else{newCoord[1] = (y_coord-step)%Params.world_height;} break;
        default: System.out.println("Invalid direction. Try again."); break;
        
		}
		return newCoord;
	}
	protected final void walk(int direction) {
		if(this.move_flag == false){
			int[] newCoord=findNewLocation(direction,1);
			if(Critter.fightPhase){
				if(!adjacentLocationOccupied(newCoord[0], newCoord[1])){
					x_coord=newCoord[0];
					y_coord=newCoord[1];
				}
			}
			//different for fightstage and normal stages
			//only set newLocationIsOccupied if it's fightPhase
			/*if(this location is occupied && CritterWorld.fightPhase==true ){
			 * newLocationIsOccupied==true;
				}*/
			
			else{
				x_coord=newCoord[0];
				y_coord=newCoord[1];
			}
			
			move_flag=true; //it walked!
		}
		energy-=Params.walk_energy_cost;
	}
	protected final void run(int direction) {
		if(this.move_flag == false){
			int[] newCoord=findNewLocation(direction,1);
			if(Critter.fightPhase){
				if(!adjacentLocationOccupied(newCoord[0], newCoord[1])){
					x_coord=newCoord[0];
					y_coord=newCoord[1];
				}
			}
				 //different for fightstage and normal stages
			//only set newLocationIsOccupied if it's fightPhase
			/*if(this location is occupied && CritterWorld.fightPhase==true ){
			 * newLocationIsOccupied==true;
				}*/
			
			else{
				x_coord=newCoord[0];
				y_coord=newCoord[1];
			}
			
			move_flag=true; //it walked!
		}
		energy-=Params.run_energy_cost;
	}
	protected final void reproduce(Critter offspring, int direction){
		if(this.energy <= Params.min_reproduce_energy){
			return;
		}
			//offspring.energy = (int) Math.floor(this.energy/2) + Params.walk_energy_cost;
			offspring.energy=(this.energy/2)+Params.walk_energy_cost;
			offspring.x_coord = offspring.x_coord;
			offspring.y_coord = offspring.y_coord;
			offspring.walk(direction);
			babies.add(offspring);
			//this.energy = (int) Math.ceil(this.energy/2);
			this.energy=(this.energy/2);
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/* create and initialize a Critter subclass
	 * critter_class_name must be the name of a concrete subclass of Critter, if not
	 * an InvalidCritterException must be thrown
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
        try { 
            Class<Critter> class_type  = (Class<Critter>) Class.forName(critter_class_name);
    		Critter newSpawn;
				newSpawn = class_type.newInstance(); //calling the basic constructor for whatever Critter sub-type was given
				newSpawn.energy=Params.start_energy;
	    		newSpawn.x_coord=Critter.getRandomInt(Params.world_width);
	    		newSpawn.y_coord=Critter.getRandomInt(Params.world_height);
	    		if(critter_class_name == "project5.Project5TestCritter"){
	    			newSpawn.x_coord =0;
	    			newSpawn.y_coord =  3;
	    		}
	    		newSpawn.move_flag = false;
	    		Critter.population.add(newSpawn);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				throw new InvalidCritterException(critter_class_name);
			}
        
		/*If the random location selected for the critter is already 
		 * occupied, the critter should be placed into that position anyway. 
		 * The encounter between the two critters now located in the same position will be resolved 
		 * in the next time step (provided both critters are still in the same position at the end of that 
		 * time step, see below).*/
	}
	
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new ArrayList<Critter>();
		try {
		for(int x=0; x<Critter.population.size(); x++){
	
				if(Class.forName(critter_class_name).isInstance(Critter.population.get(x))){
					result.add(Critter.population.get(x));
				}
		}
		
		
		} catch (ClassNotFoundException | IllegalArgumentException e) {
			throw new InvalidCritterException(critter_class_name);
		}
		return result;
	}
	
	public static String runStats(List<Critter> critters) {
		String regStats= "" + critters.size() + " critters as follows -- ";
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			regStats= regStats+ (prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		regStats=regStats + "\n";
		return regStats;
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setXCoord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setYCoord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
	}
	
	

	public static void worldTimeStep() {
		for(int x=0; x<Critter.population.size(); x++){
			Critter.population.get(x).move_flag = false;
			Critter.population.get(x).energy -= Params.rest_energy_cost;
			Critter.population.get(x).doTimeStep(); 
		}
		handleEncounters();
		killCritters();
		populatebabies();
		for(int x=0; x<Params.refresh_algae_count;x++){
			Critter newAlgae = new Algae();
			newAlgae.energy = Params.start_energy;
			newAlgae.x_coord=Critter.getRandomInt(Params.world_width);
    		newAlgae.y_coord=Critter.getRandomInt(Params.world_height);
    		Critter.population.add(newAlgae);
		}
	}
//------------Critter World Time Step Methods----------	
	//-------Fighting-------
	private static void handleEncounters(){
		Critter.fightPhase = true;
		for(int y=0; y<Critter.population.size(); y++){
				for(int x=y+1; x<Critter.population.size(); x++){
					if(Critter.population.get(y).energy> 0 && Critter.population.get(x).energy > 0 && Critter.population.get(x).x_coord == Critter.population.get(y).x_coord && Critter.population.get(x).y_coord== Critter.population.get(y).y_coord){
						int xStrength = 0;
						int yStrength = 0;
						boolean xFight = Critter.population.get(x).fight(Critter.population.get(y).getClass().getName());
						boolean yFight = Critter.population.get(y).fight(Critter.population.get(x).getClass().getName());
						if(xFight){ xStrength = Critter.getRandomInt(Critter.population.get(x).energy);}
						if(yFight){ yStrength = Critter.getRandomInt(Critter.population.get(y).energy);}
						//if it's the same position, neither of them ran away successfully.
						if(Critter.population.get(x).x_coord == Critter.population.get(y).x_coord && Critter.population.get(x).y_coord== Critter.population.get(y).y_coord){
							if(xStrength>=yStrength && Critter.population.get(y).energy > 0){ //make sure y didnt die trying to run away
								Critter.population.get(x).energy=(Critter.population.get(x).energy + Critter.population.get(y).energy/2);
								Critter.population.get(y).energy=0;
							}
							else if(yStrength > xStrength && Critter.population.get(x).energy > 0){ //making sure x didnt die trying to run away
								Critter.population.get(y).energy=(Critter.population.get(y).energy + Critter.population.get(x).energy/2);
								Critter.population.get(x).energy=0;
							}
							//if they both tried to run away but they could not and they died because they ran out of energy, then nothing happens
				
						}
					//solve for unsuccessful run away 
					}
				}
		}
		Critter.fightPhase = false;
	}
	//--------------Killing Critters----------
	private static void killCritters(){
		for(int x=0; x<Critter.population.size(); x++){
			if(Critter.population.get(x).energy <= 0){
				Critter.population.remove(x--);
			}
		}
	}

	
	//----Reproduction-----
	private static void populatebabies() {
		for(int x=0; x<Critter.babies.size();x++){
			Critter.population.add(Critter.babies.get(x));
		}
		
		Critter.babies = new ArrayList<Critter>();	
	}
	
//----------Showing the Grid of the World-------
	public static void displayWorld() {
		GraphicsContext gc  = CritterGUI.canvas.getGraphicsContext2D();
		//---Clearing the entire Canvas----//
		gc.clearRect(0, 0, CritterGUI.canvas.getWidth(), CritterGUI.canvas.getHeight());
		
		
		for(int n=0; n<population.size(); n++){
			Color color = population.get(n).viewFillColor();
			Color outLine = population.get(n).viewOutlineColor();
			
			gc.setFill(color);
			gc.setStroke(outLine);
			
			double x = population.get(n).x_coord;
			double y = population.get(n).y_coord;
			
			//-----Getting x and y positions, width and height using a proportion------//
			double yPos = y*((CritterGUI.canvas.getHeight())/(Params.world_height));
			double xPos = x*(CritterGUI.canvas.getWidth()/(Params.world_width*2));
			double width = (CritterGUI.canvas.getWidth()/(Params.world_width*2));
			double height = (CritterGUI.canvas.getHeight()/(Params.world_height*2));
			
			gc.clearRect(xPos, yPos, width, height);  //This makes sure shapes wont be drawn on top of each other
			if(population.get(n).viewShape() == Critter.CritterShape.CIRCLE){
				gc.fillOval(xPos, yPos, width/2, height); 
				gc.strokeOval(xPos, yPos, width/2, height); 	 
			}
			else if(population.get(n).viewShape() == Critter.CritterShape.SQUARE){
				gc.clearRect(xPos, yPos, width, height);
				gc.fillRect(xPos+width/8, yPos+height/8, width-width/4, height-height/4);
				gc.strokeRect(xPos+width/8, yPos+height/8, width-width/4, height-height/4);
			
			}
			else if(population.get(n).viewShape() == Critter.CritterShape.TRIANGLE){
				double[] xPoints = {xPos, xPos+(width/2),xPos+width};
				double[] yPoints = {yPos+height, yPos,yPos+height};
				
				gc.fillPolygon(xPoints, yPoints, 3);
				gc.strokePolygon(xPoints, yPoints, 3);
			}
			else if(population.get(n).viewShape() == Critter.CritterShape.DIAMOND){
				double[] xPoints = {xPos, xPos+(width/2),xPos+width, xPos+(width/2)};
				double[] yPoints = {yPos+(height/2), yPos, yPos+(height/2), yPos+height};
				
				gc.fillPolygon(xPoints, yPoints, 4);
				gc.strokePolygon(xPoints, yPoints, 4);
			}
			else if(population.get(n).viewShape() == Critter.CritterShape.STAR){
				double[] xPoints = {xPos+(width*9/16), xPos+(width*7/16), xPos, xPos+(width*3/8), xPos+(width*2/8), xPos+(width*9/16), xPos+(width*7/8), xPos+(width*6/8), xPos+width, xPos+(width*11/16)};
				double[] yPoints = {yPos, yPos+(height*3/8), yPos+(height*3/8), yPos+(height*5/8), yPos+height, yPos+(height*3/4), yPos+(height), yPos+(height*5/8), yPos+(height*3/8), yPos+(height*3/8)};
				
				gc.fillPolygon(xPoints, yPoints, 10);
				gc.strokePolygon(xPoints, yPoints, 10);
				}
	
		}

			
	}
}