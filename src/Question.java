import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

abstract class Question implements Serializable {
	static final long serialVersionUID = 10;
	
	protected String question= "";
	protected ArrayList<String> choices= new ArrayList<String>();
	
	public String getQuestionPrompt() { return question; };
	public void modifyQuestion() {
		System.out.println("Would you like to modify the prompt? Enter yes/no");
		Scanner usrIn = new Scanner(System.in);
		String changePrompt = "";
		while(!changePrompt.toLowerCase().equals("yes") && !changePrompt.toLowerCase().equals("no")) {
			changePrompt = usrIn.nextLine();
			
			if(!changePrompt.toLowerCase().equals("yes") && !changePrompt.toLowerCase().equals("no")) {
				System.out.println("Enter a \"yes\" or a \"no\".");
			}
		}
		if (changePrompt.toLowerCase().equals("yes")) {
			System.out.println("Enter a new prompt.");
			question = usrIn.nextLine();
		}
		modifyChoices();
	};
	abstract void modifyChoices();
	abstract void displayQuestion();
	abstract void takeQuestion();
	abstract void tabulateQuestion();
	abstract int timesAnswered();
	abstract String getType();
	abstract ArrayList getResponses();
}
