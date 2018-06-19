import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MultipleChoice extends Question {
	static final long serialVersionUID = 15;
	
	private ArrayList<ArrayList<Integer>> responses = new ArrayList<ArrayList<Integer>>();
	private int numResponsesForSurvey = 0;

	public void setNumResponses(int numResponses) {
		numResponsesForSurvey = numResponses;
	}

	public MultipleChoice(String quest) {
		question = quest;
	}

	public ArrayList getResponses() {
		return responses;
	}

	public void addChoice(String choice) {
		choices.add(choice);
	}

	public void displayChoices() {
		for(int i= 0; i < choices.size(); i++) {
			System.out.print((i + 1) + ") " + choices.get(i) + " ");
		}
		System.out.print("\n");
	}
	
	public int getChoicesSize() {
		return choices.size();
	}

	public void displayQuestion() {
		System.out.println(question);
		displayChoices();
	}
	
	public String getChoice(int choiceNum) {
		return choices.get(choiceNum);
	}

	@Override
	public void modifyChoices() {
		System.out.println("Would you like to modify a choice? Enter yes/no");
		Scanner usrIn = new Scanner(System.in);
		String changeChoices = "";
		while(!changeChoices.toLowerCase().equals("yes") && !changeChoices.toLowerCase().equals("no")) {
			changeChoices = usrIn.nextLine();
			
			if(!changeChoices.toLowerCase().equals("yes") && !changeChoices.toLowerCase().equals("no")) {
				System.out.println("Enter a \"yes\" or a \"no\".");
			}
		}
		if (changeChoices.toLowerCase().equals("yes")) {
			displayChoices();
			System.out.println("Which choice would you like to modify?");
			int numChoice = 0;
			String newChoice = "";
			while(numChoice == 0 || numChoice > choices.size()) {
				try {
					numChoice = usrIn.nextInt();
					Scanner usrIn2 = new Scanner(System.in);
					if(numChoice < 1 || numChoice > choices.size()) System.out.println("Enter a valid choice");
					else {
						System.out.println("Enter a new value for the choice.");
						newChoice = usrIn2.nextLine();
						choices.set(numChoice - 1, newChoice);
					}
				}
				catch (InputMismatchException ex) {
					System.out.println("Enter an integer");
					usrIn.next();
				}
			}
			modifyChoices();
		}
	}

	@Override
	public void takeQuestion() {
		ArrayList<Integer> response = new ArrayList<Integer>();
		Scanner usrIn = new Scanner(System.in);
		if(numResponsesForSurvey == 1) {
			System.out.println("Enter a choice.");
			int usrResponse = 0;
			while(usrResponse < 1 || usrResponse > choices.size()) {
				try {
					usrResponse = usrIn.nextInt();
					
					if(usrResponse < 1 || usrResponse > choices.size()) System.out.println("Enter a valid choice.");
				}
				catch (InputMismatchException ex) {
					System.out.println("Enter the integer value of your choice.");
					usrIn.next();
				}
			}
			response.add(usrResponse);
		}
		else {
			System.out.println("Enter " + numResponsesForSurvey + " choices.");
			for(int i = 0; i < numResponsesForSurvey; i++) {
				System.out.println("Enter a choice.");
				int usrChoice = 0;
				while(usrChoice < 1 || usrChoice > choices.size()) {
					try {
						usrChoice = usrIn.nextInt();
						
						if(usrChoice < 1 || usrChoice > choices.size()) System.out.println("Enter a valid choice.");
					}
					catch (InputMismatchException ex) {
						System.out.println("Enter a valid choice.");
						usrIn.next();
					}
				}
				response.add(usrChoice);
			}
		}
		responses.add(response);
	}

	@Override
	public void tabulateQuestion() {
		displayQuestion();
		
		ArrayList<Integer> amounts = new ArrayList<Integer>();
		for(String s: choices) amounts.add(0);
		
		for(ArrayList<Integer> a: responses) {
			for(Integer i: a) {
				amounts.set(i - 1, amounts.get(i - 1) + 1);
			}
		}
		
		for(int i = 0; i < amounts.size(); i++) {
			System.out.println((i + 1) + ") " + amounts.get(i));
		}
		System.out.print("\n");
	}

	public int timesAnswered() {
		return responses.size();
	}

	@Override
	public String getType() {
		return "mc";
	}
}
