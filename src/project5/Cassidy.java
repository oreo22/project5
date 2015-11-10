package project5;
/* Diego Guerra (dag3222) 
 * Oriana Wong (oyw58)
 */
/*Description: Cassidy has characteristics based his hour of the day since inception
 * doTimeStep: Cassidy will walk twice depending on the hour of the day
 * If it's night time for a Cassidy, he will reproduce. Night hours are from 0 to 8 military time
 * Fight: Cassidy can't fight Craig. Otherwise:
 * If Cassidy is in his usual Smash Hours, 10 to 14 and 16 to 20, then he will always fight
 * Otherwise, he is in class and can't fight. If it is an Algae, he can take a snack break during class and eat
 * Stats: The stats tell you what the each Cassidy is doing
 * Look: If Cassidy is in school, then he'll look before he goes anywhere. He'll only walk somewhere when he sees no one there 
 */
public class Cassidy extends Critter {
	private int hours;

	public Cassidy(){
		this.hours = 0;
	}
	
	@Override
	public void doTimeStep() {
		hours = (hours+1)%24;
		walk(hours%8);
		walk((hours+1)%8);
		if(hours >= 0 && hours <= 8 && this.getEnergy() > 150){ //sleeping hours
			this.reproduce(new Cassidy(), (hours+2)%8);
		}
	}

	@Override
	public boolean fight(String oponent) {
		if(oponent.equals("project5.Craig")){
			return false;
		}
		else if((hours >=15 && hours<=16) || (hours >=21 && hours<=23)){
			for(int x=0; x<8; x++){
				if(look(x) == null){
					walk(x);
				}
			}
		}
		else if((hours >=10 && hours<=14) || (hours >=16 && hours<=20) || oponent.equals("project5.Algae")){ //smash hours
			return true;
		}
		return false;
	}
	
	public String toString(){
		return "Ç";
	}
	
	public static String runStats(java.util.List<Critter> cassidys) {
		int cassidysAsleep =0;
		int cassidysSmashing =0;
		int cassidysInSchool =0;
		for(Object obj: cassidys){
			Cassidy cas = (Cassidy) obj;
			if(cas.hours >= 0 && cas.hours<=8){
				cassidysAsleep++;
			}
			else if((cas.hours >=10 && cas.hours<=14) || (cas.hours >=16 && cas.hours <= 20)){
				cassidysSmashing++;
			}
			else{
				cassidysInSchool++;
			}
		}
		String cassidyStats= ("There are " + cassidysAsleep + " Cassidy(s) sleeping"+ "\n"+
		"There are " + cassidysSmashing + " Cassidy(s) playing smash" + "\n"+
		"There are " + cassidysInSchool + " Cassidy(s) in school");
		return cassidyStats;
		
	}

	@Override
	public CritterShape viewShape() {
		return CritterShape.STAR;
	}
	//public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.YELLOW; }
	public javafx.scene.paint.Color viewFillColor() { return javafx.scene.paint.Color.YELLOW; }
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.BLACK; }
}