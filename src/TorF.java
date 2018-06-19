import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TorF extends Question {
	static final long serialVersionUID = 13;
	
	private ArrayList<Integer> responses = new ArrayList<Integer>();
	
	public TorF(String quest) {
		question = quest;
		choices.add("True");
		choices.add("False");
	}
	
	public void displayQuestion() {
		System.out.println(question);
		System.out.println(choices.get(0) + " / " + choices.get(1));
	}
	
	public ArrayList getResponses() {
		return responses;
	}

	@Override
	void modifyChoices() {}

	@Override
	public void takeQuestion() {
		int response = 0;
		Scanner usrIn = new Scanner(System.in);
		
		System.out.println("Enter a 1 if the answer is true and a 2 if the answer is false.");
		while(response != 1 && response != 2) {
			try {
				response = usrIn.nextInt();
				
				if(response != 1 && response != 2) System.out.println("Enter a 1 or 2.");
			}
			catch (InputMismatchException ex) {
				System.out.println("Enter a 1 or 2.");
				usrIn.next();
			}
		}
		responses.add(response);
	}
	
	public void tabulateQuestion() {
		displayQuestion();
		
		int trueCount = 0, falseCount = 0;
		
		for(Integer i: responses) {
			if(i.equals(1)) trueCount++;
			else falseCount++;
		}
		
		System.out.println("True: " + trueCount);
		System.out.println("False: " + falseCount);
		System.out.print("\n");
	}

	@Override
	int timesAnswered() {
		return responses.size();
	}

	@Override
	public String getType() {
		return "torf";
	}
}
