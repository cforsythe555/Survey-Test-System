import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Ranking extends Question {
	static final long serialVersionUID = 12;
	
	private ArrayList<ArrayList<Integer>> responses = new ArrayList<ArrayList<Integer>>();
	
	public Ranking(String quest) {
		question = quest;
	}
	
	public void addChoice(String newChoice) {
		choices.add(newChoice);
	}
	
	public int getChoicesSize() {
		return choices.size();
	}
	
	public ArrayList getResponses() {
		return responses;
	}
	
	public void displayChoices() {
		for(int i= 0; i < choices.size(); i++) {
			System.out.print((i + 1) + ") " + choices.get(i) + " ");
		}
		System.out.print("\n");
	}
	
	void displayQuestion() {
		System.out.println(question);
		displayChoices();
	}

	void modifyChoices() {
		System.out.println("Would you like to modify a choice?");
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
	}

	@Override
	public void takeQuestion() {
		ArrayList<Integer> response = new ArrayList<Integer>();
		for(int i = 0; i < choices.size(); i++) {
			if(i == 0) System.out.println("Enter the highest choice.");
			else if(i == choices.size() - 1) System.out.println("Enter the lowest choice.");
			else System.out.println("Enter the next highest choice.");
			
			Scanner usrIn = new Scanner(System.in);
			int choice = 0;
			while(choice < 1 || choice > choices.size()) {
				try {
					choice = usrIn.nextInt();
					
					if(choice < 1 || choice > choices.size()) System.out.println("Enter a valid choice.");
				}
				catch (InputMismatchException ex) {
					System.out.println("Enter a valid choice.");
					usrIn.next();
				}
			}
			response.add(choice);
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
		return "ranking";
	}
}
