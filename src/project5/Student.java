package project5;
/* Diego Guerra (dag3222) 
 * Oriana Wong (oyw58)
 */

import project5.Critter.CritterShape;

/*Description: Students are critters that have different characteristics based on their "grade" or energy
 * doTimeStep: Student just walks in a random direction based on its energy. 
 * If they're an A student though, they can reproduce. Cause we need as many A students we can get.
 * Fight: Students can't fight Craig. Otherwise:
 * If they're an A student, they're always fighting everything else besides Craig. 
 * If they're a B student, they fight half the time.
 * If they're a C student, they sometimes take the chance to fight. 
 * Otherwise, they don't fight....well except Algae. They always fight Algae.
 * Stats: The stats tell you how many students of each grade type in the world.
 */
public class Student extends Critter{

	@Override
	public void doTimeStep() {
		int roll = Critter.getRandomInt(100);
		if(this.getEnergy() >= 85){
			walk(roll%8);
		}
		else if(roll>85){
			walk(0);
		}
		if(this.getEnergy() >150){
			this.reproduce(new Student(), (this.getEnergy()+1)%8);
		}
		
	}

	@Override
	public boolean fight(String oponent) {
		if(oponent.equals("project5.Craig") && this.getEnergy() >= 100){ //Chase is supreme ruler
			return false;
		}
		//Fighting other Critters(TAs)//
		int roll = Critter.getRandomInt(100);
		if(this.getEnergy() >= 90){
			return true;
		}
		else if(this.getEnergy() >=80){
			
			if(roll >= 50){
				return true;
			}
			return false;
		}
		else if(this.getEnergy() >= 70){
			if(roll >= 75){
				return true;
			}
			return false;
		}
		run(this.getEnergy()%8);
		return false;
	}
	public String toString(){
		return "S";
	}
	public static String runStats(java.util.List<Critter> Students) {
		int A =0;
		int B =0;
		int C =0;
		int failing =0;
		for(Object obj: Students){
			Student student = (Student) obj;
			if(student.getEnergy() >= 90){
				A++;
			}
			else if(student.getEnergy() >= 80){
				B++;
			}
			else if (student.getEnergy() >= 70){
				C++;
			}
			else{
				failing++;
			}
		}
		String studentStats="There are " + A + " student(s) with A's in this course"+ "\n"+
		"There are " + B + " student(s) with B's in this course" +"\n"+
		"There are " + C + " student(s) with C's in this course"+"\n"+
		"There are " + failing + " student(s) failing this course and should drop";
		return studentStats;
	}

	@Override
	public CritterShape viewShape() {
		return CritterShape.DIAMOND;
	}
	//public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.YELLOW; }
	public javafx.scene.paint.Color viewFillColor() { return javafx.scene.paint.Color.LIGHTGREEN; }
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.DODGERBLUE; }
}
