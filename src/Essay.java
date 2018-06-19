import java.util.ArrayList;
import java.util.Scanner;

public class Essay extends Question {
	static final long serialVersionUID = 14;
	
	protected ArrayList<String> responses = new ArrayList<String>();
	
	public Essay(String quest) {
		question = quest;
	}
	
	public void displayQuestion() {
		System.out.println(question);
	}
	
	public ArrayList getResponses() {
		return responses;
	}

	@Override
	void modifyChoices() {}

	@Override
	public void takeQuestion() {
		System.out.println("Enter your response to the question.");
		Scanner usrIn = new Scanner(System.in);
		responses.add(usrIn.nextLine());
	}

	@Override
	public void tabulateQuestion() {
		displayQuestion();
		for(String s: responses) {
			System.out.println(s);
		}
		System.out.print("\n");
	}
	
	@Override
	int timesAnswered() {
		return responses.size();
	}

	@Override
	public String getType() {
		return "essay";
	}
}
