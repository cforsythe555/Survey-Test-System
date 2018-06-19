import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Matching extends Question {
	static final long serialVersionUID = 16;
	
	private ArrayList<String> matchingOptions= new ArrayList<String>();
	private ArrayList<ArrayList<Integer>> responses= new ArrayList<ArrayList<Integer>>();
	
	public Matching(String quest) {
		question = quest;
	}
	
	public void displayQuestion() {
		System.out.println(question);
		displayChoices();
		displayMatches();
	}
	
	public ArrayList getResponses() {
		return responses;
	}
	
	public void displayChoices() {
		System.out.println("Choices: ");
		for(int i= 65; i < 65 + choices.size(); i++) {
			System.out.print((char) i + ") " + choices.get(i - 65) + " ");
		}
		System.out.println("\n");
	}
	
	public void displayMatches() {
		System.out.println("Matches:");
		for(int i = 0; i < matchingOptions.size(); i++) {
			System.out.print((i + 1) + ") " + matchingOptions.get(i) + " ");
		}
		System.out.print("\n");
	}
	
	public void displayCorrectAnswer() {
	}
	
	public void addChoice(String newChoice) {
		choices.add(newChoice);
	}
	
	public String getMatchingOption(int matchNum) {
		return matchingOptions.get(matchNum);
	}
	
	public int getChoicesSize() {
		return choices.size();
	}
	
	public void addMatchingChoice(String newChoice) {
		matchingOptions.add(newChoice);
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
			String newChoice= "";
			while(numChoice == 0 || numChoice > choices.size()) {
				try {
					numChoice = usrIn.nextInt();
					
					if(numChoice < 1 || numChoice > choices.size()) System.out.println("Enter a valid choice");
					else {
						Scanner usrIn2 = new Scanner(System.in);
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
		else modifyMatches();
	}
	
	private void modifyMatches() {
		System.out.println("Would you like to modify a match? Enter yes/no");
		Scanner usrIn = new Scanner(System.in);
		String changeMatches = "";
		while(!changeMatches.toLowerCase().equals("yes") && !changeMatches.toLowerCase().equals("no")) {
			changeMatches = usrIn.nextLine();
			
			if(!changeMatches.toLowerCase().equals("yes") && !changeMatches.toLowerCase().equals("no")) {
				System.out.println("Enter a \"yes\" or a \"no\".");
			}
		}
		if (changeMatches.toLowerCase().equals("yes")) {
			displayMatches();
			System.out.println("Which match would you like to modify?\nEnter an integer value of the match instead of the letter value.");
			int numChoice = 0;
			String newChoice = "";
			while(numChoice == 0 || numChoice > matchingOptions.size()) {
				try {
					numChoice = usrIn.nextInt();
					
					if(numChoice < 1 || numChoice > matchingOptions.size()) System.out.println("Enter a valid choice");
					else {
						Scanner usrIn2 = new Scanner(System.in);
						System.out.println("Enter a new value for the choice.");
						newChoice = usrIn2.nextLine();
						matchingOptions.set(numChoice - 1, newChoice);
					}
				}
				catch (InputMismatchException ex) {
					System.out.println("Enter an integer");
					usrIn.next();
				}
			}
			modifyMatches();
		}
	}

	@Override
	public void takeQuestion() {
		ArrayList<Integer> response = new ArrayList<Integer>();
		for(int i = 0; i < choices.size(); i++) {
			System.out.println("Enter the match for " + (char) (i + 65));
			Scanner usrIn = new Scanner(System.in);
			int usrChoice = 0;
			while(usrChoice < 1 || usrChoice > matchingOptions.size()) {
				try {
					usrChoice = usrIn.nextInt();
					
					if(usrChoice < 1 || usrChoice > matchingOptions.size()) System.out.println("Enter a valid matching option.");
				}
				catch(InputMismatchException ex) {
					System.out.println("Enter the integer value of the match.");
					usrIn.next();
				}
			}
			response.add(usrChoice);
		}
		responses.add(response);
	}
	
	@Override
	void tabulateQuestion() {
		displayQuestion();
		ArrayList<ArrayList<Integer>> differentResponses = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> amounts = new ArrayList<Integer>();
		
		for(ArrayList<Integer> a: responses) {
			if(!differentResponses.contains(a)) {
				differentResponses.add(a);
				amounts.add(1);
			}
			else {
				int index = differentResponses.indexOf(a);
				amounts.set(index, amounts.get(index) + 1);
			}
		}
		
		for(int i = 0; i < differentResponses.size(); i++) {
			System.out.println(amounts.get(i) + ")");
			for(int k = 0; k < differentResponses.get(i).size(); k++) {
				System.out.print(differentResponses.get(i).get(k) + " ");
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}

	@Override
	int timesAnswered() {
		return responses.size();
	}

	@Override
	public String getType() {
		return "matching";
	}
}
