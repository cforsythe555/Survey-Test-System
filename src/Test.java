import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Test extends Form {
	static final long serialVersionUID = 3;
	
	private ArrayList<ArrayList> correctResponses= new ArrayList<ArrayList>();
	
	public Test() {}

	public void displayForm() {
		for(int i= 0; i < questions.size(); i++) {
			questions.get(i).displayQuestion();
			displayCorrectAnswer(questions.get(i), i);
			System.out.print("\n");
		}
	}

	public void addQuestion(Question newQuestion) {
		questions.add(newQuestion);
	}
	
	public void addCorrectResponse(ArrayList ca) {
		correctResponses.add(ca);
	}
	
	public boolean hasQuestions() {
		boolean quests = true;
		if(questions.size() == 0) quests = false;
		
		return quests;
	}
	
	public Question getQuestion(int questNum) {
		return questions.get(questNum - 1);
	}
	
	public int timesTaken() {
		return questions.get(0).timesAnswered();
	}

	@Override
	public void modifyQuestion(int quest) {
		questions.get(quest).modifyQuestion();
		
	}
	
	public void modifyCorrectAnswer(int questNum) {
		if(questions.get(questNum).getType().equals("Matching")) {
			Matching mat = (Matching) questions.get(questNum);
			System.out.println("Would you like to modify the correct matches? Enter yes/no");
			Scanner usrIn = new Scanner(System.in);
			String modifyCA = "";
			while(!modifyCA.toLowerCase().equals("yes") && !modifyCA.toLowerCase().equals("no")) {
				modifyCA = usrIn.nextLine();
				
				if(!modifyCA.toLowerCase().equals("yes") && !modifyCA.toLowerCase().equals("no")) {
					System.out.println("Enter a \"yes\" or a \"no\".");
				}
			}
			if(modifyCA.toLowerCase().equals("yes")) {
				mat.displayChoices();
				mat.displayMatches();
				int amtChoices = mat.getChoicesSize();
				ArrayList<Integer> newCorrectOrder = new ArrayList<Integer>();
				for(int i = 0; i < amtChoices; i++) {
					System.out.println("Enter the correct match for choice " + (char) (i + 65) + ".");
					int match = 0;
					while(match > amtChoices || match <= 0) {
						try {
							match = usrIn.nextInt();
						
							if(match > amtChoices || match <= 0) System.out.println("Please enter one of the matching options.");
							else if (newCorrectOrder.contains(match)) {
								System.out.println("Enter a unique choice.");
								match = 0;
							}
							else newCorrectOrder.add(match);
						}
						catch (InputMismatchException ex) {
							System.out.println("Enter an integer valaue.");
							usrIn.next();
						}
					}
				}
				correctResponses.set(questNum, newCorrectOrder);
			}
		}
		else if(questions.get(questNum).getType().equals("mc")) {
			MultipleChoice mc = (MultipleChoice) questions.get(questNum);
			System.out.println("Would you like to modify the correct choices? Enter yes/no");
			Scanner usrIn = new Scanner(System.in);
			String modifyCA ="";
			while(!modifyCA.toLowerCase().equals("yes") && !modifyCA.toLowerCase().equals("no")) {
				modifyCA = usrIn.nextLine();
				
				if(!modifyCA.toLowerCase().equals("yes") || !modifyCA.toLowerCase().equals("yes")) {
					System.out.println("Enter a \"yes\" or a \"no\".");
				}
			}
			if(modifyCA.toLowerCase().equals("yes")) {
				int amtChoices = mc.getChoicesSize();
				System.out.println("How many correct choices are there?");
				int numRightChoices = 0;
				while(numRightChoices < 1 || numRightChoices > amtChoices) {
					try {
						numRightChoices = usrIn.nextInt();
						
						if(numRightChoices <= 0 || numRightChoices > amtChoices) System.out.println("Enter a value within the range of the 1 - the number of choices for the question.");
					}
					catch (InputMismatchException ex) {
						System.out.println("Enter an integer value.");
						usrIn.next();
					}
				}
				mc.setNumResponses(numRightChoices);
				mc.displayChoices();
				ArrayList<Integer> newCorrectChoices = new ArrayList<Integer>();
				
				for(int i = 0; i < numRightChoices; i++) {
					System.out.println("Enter a correct choice.");
					int match = 0;
					while(match > amtChoices || match <= 0) {
						try {
							match = usrIn.nextInt();
						
							if(match > amtChoices || match <= 0) System.out.println("Please enter one of the options.");
							else if (newCorrectChoices.contains(match)) {
								System.out.println("Enter a unique option.");
								match = 0;
							}
							else newCorrectChoices.add(match);
						}
						catch (InputMismatchException ex) {
							System.out.println("Enter an integer.");
							usrIn.next();
						}
					}
				}
				correctResponses.set(questNum, newCorrectChoices);
			}
		}
		else if(questions.get(questNum).getType().equals("ranking")) {
			Ranking ra = (Ranking) questions.get(questNum);
			System.out.println("Would you like to modify the correct order? Enter yes/no");
			Scanner usrIn= new Scanner(System.in);
			String modifyCA = "";
			while(!modifyCA.toLowerCase().equals("yes") && !modifyCA.toLowerCase().equals("no")) {
				modifyCA = usrIn.nextLine();
				
				if(!modifyCA.toLowerCase().equals("yes") && !modifyCA.toLowerCase().equals("no")) {
					System.out.println("Enter a \"yes\" or a \"no\".");
				}
			}
			if(modifyCA.toLowerCase().equals("yes")) {
				int amtChoices = ra.getChoicesSize();
				ArrayList<Integer> correctOrder = new ArrayList<Integer>();
				System.out.println("Please enter the correct order, one value at a time.");
				System.out.println("Choices: ");
				ra.displayChoices();
				for(int i = 0; i < amtChoices; i++) {
					if (i == 0) System.out.println("Enter the higest choice.");
					else System.out.println("Enter the next highest choice.");
					int nxtChoice = 0;
					while(nxtChoice < 1 || nxtChoice > amtChoices) {
						try {
							nxtChoice = usrIn.nextInt();
							
							if(nxtChoice < 1 || nxtChoice > amtChoices) System.out.println("Enter a value within the range of 1 - the number of choices for this question.");
							else if (correctOrder.contains(nxtChoice)) {
								System.out.println("Enter a unique value.");
								nxtChoice = 0;
							}
							else correctOrder.add(nxtChoice);
						}
						catch (InputMismatchException ex) {
							System.out.println("Enter an integer.");
							usrIn.next();
						}
					}
				}
				correctResponses.set(questNum, correctOrder);
			}
		}
		else if(questions.get(questNum).getType().equals("torf")) {
			System.out.println("Would you like to toggle the correct answer? Enter yes/no");
			Scanner usrIn= new Scanner(System.in);
			String modifyCA = "";
			while(!modifyCA.toLowerCase().equals("yes") && !modifyCA.toLowerCase().equals("no")) {
				modifyCA = usrIn.nextLine();
				
				if(!modifyCA.toLowerCase().equals("yes") && !modifyCA.toLowerCase().equals("no")) {
					System.out.println("Enter a \"yes\" or a \"no\".");
				}
			}
			ArrayList<Integer> correctAnswer = new ArrayList<Integer>();
			if(modifyCA.toLowerCase().equals("yes") && (int) correctResponses.get(questNum).get(0) == 1) correctAnswer.add(0);
			else if(modifyCA.toLowerCase().equals("yes")) correctAnswer.add(1);
			correctResponses.set(questNum, correctAnswer);
		}
		else if(questions.get(questNum).getType().equals("sa")) {
			System.out.println("Would you like to modify the correct answers? Enter yes/no");
			Scanner usrIn = new Scanner(System.in);
			String modifyCA ="";
			while(!modifyCA.toLowerCase().equals("yes") && !modifyCA.toLowerCase().equals("no")) {
				modifyCA = usrIn.nextLine();
				
				if(!modifyCA.toLowerCase().equals("yes") && !modifyCA.toLowerCase().equals("no")) {
					System.out.println("Enter a \"yes\" or a \"no\".");
				}
			}
			if(modifyCA.toLowerCase().equals("yes")) {
				System.out.println("How many correct answers are there?");
				int numRightAns = 0;
				while(numRightAns < 1) {
					try {
						numRightAns = usrIn.nextInt();
						
						if(numRightAns <= 0) System.out.println("Enter a value greater than 0.");
					}
					catch (InputMismatchException ex) {
						System.out.println("Enter an integer value.");
						usrIn.next();
					}
				}
				ArrayList<String> newCorrectAnswers = new ArrayList<String>();
				for(int i= 0; i < numRightAns; i++) {
					Scanner usrIn2 = new Scanner(System.in);
					System.out.println("Enter a correct answer");
					newCorrectAnswers.add(usrIn2.nextLine());
				}
				correctResponses.set(questNum, newCorrectAnswers);
			}
		}
	}
	

	@Override
	public void displayForModification() {
		for(int i = 0; i < questions.size(); i++) {
			System.out.print((i + 1) + ") ");
			questions.get(i).displayQuestion();
			displayCorrectAnswer(questions.get(i), i);
			System.out.print("\n");
		}
	}

	@Override
	public void take() {
		for (Question q: questions) {
			q.displayQuestion();
			System.out.print("\n");
			q.takeQuestion();
		}
	}
	
	public void grade(int numResponses) {
		int numerator = 0, divisor = 0, essays = 0;
		for(int i = 0; i < questions.size(); i++) {
			if(!questions.get(i).getType().equals("essay")) {
				divisor++;
				if(questions.get(i).getType().equals("matching") && questions.get(i).getResponses().get(numResponses - 1).equals(correctResponses.get(i))) numerator ++;
				else if(questions.get(i).getType().equals("mc") && correctResponses.get(i).containsAll((ArrayList) questions.get(i).getResponses().get(numResponses - 1))) numerator ++;
				else if(questions.get(i).getType().equals("ranking") && questions.get(i).getResponses().get(numResponses - 1).equals(correctResponses.get(i))) numerator ++;
				else if(questions.get(i).getType().equals("torf") && correctResponses.get(i) == questions.get(i).getResponses().get(numResponses - 1)) numerator++;
				else if(correctResponses.get(i).contains(questions.get(i).getResponses().get(numResponses - 1))) numerator++;
			}
			else essays++;
		}
		System.out.println("The grade is " + (numerator * 10) + "/" + (divisor * 10) + " and there are " + essays + " ungraded essays.\n");
	}
	
	public void displayCorrectAnswer(Question q, int questNum) {
		if(q.getType().equals("matching")) {
			Matching mat= (Matching) q;
			System.out.println("The correct matches are: ");
			for(int i = 0; i < mat.getChoicesSize(); i++) {
				System.out.println((char) (i + 65) + " " + mat.getChoice(i) + " with " + correctResponses.get(questNum).get(i) + " " + mat.getMatchingOption((int) correctResponses.get(questNum).get(i) - 1));
			}
		}
		else if(q.getType().equals("mc")) {
			MultipleChoice mc= (MultipleChoice) q;
			if(correctResponses.get(questNum).size() == 1) {
				System.out.println("The correct choice is: " + correctResponses.get(questNum).get(0) + " "  + mc.getChoice((int) correctResponses.get(questNum).get(0) - 1));
			}
			else {
				System.out.print("The correct choices are: ");
				for(int i= 0; i < correctResponses.get(questNum).size(); i++) {
					System.out.print(correctResponses.get(questNum).get(i) + " " + mc.getChoice((int) correctResponses.get(questNum).get(i) - 1) + " ");
				}
				System.out.print("\n");
			}
		}
		else if(q.getType().equals("ranking")) {
			System.out.print("The correct order is: ");
			for(int i = 0; i < correctResponses.get(questNum).size(); i++) {
				System.out.print(correctResponses.get(questNum).get(i) + " ");
			}
			System.out.print("\n");
		}
		else if(q.getType().equals("torf")) {
			if((int) correctResponses.get(questNum).get(0) == 1) System.out.println("The correct answer is: true");
			else System.out.println("The correct answer is: false");
		}
		else if(q.getType().equals("sa")) {
			if(correctResponses.get(questNum).size() == 1) System.out.println("The correct answer is: " + correctResponses.get(questNum).get(0));
			else {
				System.out.println("The correct answers are:");
				for(int i = 0; i < correctResponses.get(questNum).size(); i++) {
					System.out.println(correctResponses.get(questNum).get(i));
				}
			}
		}
	}
}
