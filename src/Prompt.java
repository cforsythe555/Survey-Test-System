import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Prompt {
	private static Survey activeSurvey = new Survey();
	private static Test activeTest= new Test();
	
	public Prompt() {}
	
	public static void menu1() {
		System.out.println("1) Survey");
		System.out.println("2) Test");
		System.out.println("Please enter the number of your choice.");
		try {
			int choice = 0;
			Scanner usrIn = new Scanner(System.in);
			choice = usrIn.nextInt();
			if(choice != 1 && choice != 2) {
				System.out.println("Please enter a valid choice.");
				menu1();
			}
			else {
				menu2(choice);
			}
		}
		catch (InputMismatchException ex) {
			System.out.println("Try again and please enter valid input.");
			menu1();
		}
		catch(Exception ex) {
			System.out.println("Try again and please enter valid input.");
		}
	}
	
	private static void menu2(int formChoice) {
		String formType = "";
		if(formChoice == 1) formType = "Survey";
		else formType = "Test";
		
		System.out.println("1) Create a new " + formType);
		System.out.println("2) Display current " + formType);
		System.out.println("3) Load a " + formType);
		System.out.println("4) Save a " + formType);
		System.out.println("5) Modify an existing " + formType);
		System.out.println("6) Take a " + formType);
		System.out.println("7) Tabulate a " + formType);
		if(formChoice == 1) {
			System.out.println("8) Quit");
		}
		else {
			System.out.println("8) Grade a Test");
			System.out.println("9) Quit");
		}
		try {
			int choice = 0;
			Scanner usrIn = new Scanner(System.in);
			choice = usrIn.nextInt();
			if(((choice <= 0 || choice > 8) && formChoice == 1) || ((choice <= 0 || choice > 9) && formChoice == 2)) {
				System.out.println("Please enter a valid choice.");
				menu2(formChoice);
			}
			else {
				handleMenu2Ans(formChoice, choice);
			}
		}
		catch(InputMismatchException ex) {
			System.out.println("Please try again and provide valid input.");
			menu2(formChoice);
		}
		catch(Exception ex) {
			System.out.println(ex);
			System.out.println("Please try again and provide valid input.");
			menu2(formChoice);
		}
	}
	
	private static void handleMenu2Ans(int formChoice, int choice) throws ClassNotFoundException, IOException {
		Scanner usrIn = new Scanner(System.in);
		switch(choice) {
			case 1:
				if(formChoice == 1) activeSurvey = new Survey();
				else activeTest = new Test();
				menu3(formChoice);
				break;
			case 2:
				if(formChoice == 1 && activeSurvey.hasQuestions()) activeSurvey.displayForm();
				else if(formChoice == 2 && activeTest.hasQuestions()) activeTest.displayForm();
				else {
					System.out.println("Nothing to display.");
					menu2(formChoice);
				}
				menu2(formChoice);
				break;
			case 3:
				loadMenu(formChoice);
				break;
			case 4:
				saveMenu(formChoice);
				break;
			case 5:
				if(formChoice == 1) {
					activeSurvey.displayForModification();
					System.out.println("Choose a question to modify.");
					int questionChoice = 0;
					while(questionChoice < 1 || questionChoice > activeSurvey.getNumQuestions()) {
						try {
							questionChoice = usrIn.nextInt();
							
							if(questionChoice < 1 || questionChoice > activeSurvey.getNumQuestions()) System.out.println("Enter a valid number for a question.");
						}
						catch (InputMismatchException ex) {
							System.out.println("Enter the number of the question you would like to modify.");
							usrIn.next();
						}
					}
					activeSurvey.modifyQuestion(questionChoice - 1);
				}
				else {
					activeTest.displayForModification();
					System.out.println("Choose a question to modify.");
					int questionChoice = 0;
					while(questionChoice < 1 || questionChoice > activeTest.getNumQuestions()) {
						try {
							questionChoice = usrIn.nextInt();
							
							if(questionChoice < 1 || questionChoice > activeTest.getNumQuestions()) System.out.println("Enter a valid number for a question.");
						}
						catch (InputMismatchException ex) {
							System.out.println("Enter the number of the question you would like to modify.");
							usrIn.next();
						}
					}
					activeTest.modifyQuestion(questionChoice - 1);
					activeTest.modifyCorrectAnswer(questionChoice - 1);
				}
				menu2(formChoice);
				break;
			case 6:
				if(formChoice == 1) {
					if(activeSurvey.hasSaveName()) {
						activeSurvey.take();
						save(activeSurvey.getSaveName(), formChoice);
					}
					else {
						handleUnsavedForm(formChoice);
						activeSurvey.take();
						save(activeSurvey.getSaveName(), formChoice);
					}
				}
				else {
					if(activeTest.hasSaveName()) {
						activeTest.take();
						save(activeTest.getSaveName(), formChoice);
					}
					else {
						handleUnsavedForm(formChoice);
						activeTest.take();
						save(activeTest.getSaveName(), formChoice);
					}
				}
				menu2(formChoice);
				break;
			case 7:
				if(formChoice == 1) activeSurvey.tabulate();
				else activeTest.tabulate();
				menu2(formChoice);
				break;
			case 8:
				if(formChoice == 1) menu1();
				else {
					System.out.println("The active test has been taken " + activeTest.timesTaken() + " times.");
					System.out.println("Which time would you like to grade?");
					int gradeChoice = 0;
					while(gradeChoice < 1 || gradeChoice > activeTest.timesTaken()) {
						try {
							gradeChoice = usrIn.nextInt();
							
							if(gradeChoice < 1 || gradeChoice > activeTest.timesTaken()) System.out.println("Enter a valid choice.");
						}
						catch (InputMismatchException ex) {
							System.out.println("Enter an integer.");
							usrIn.next();
						}
					}
					activeTest.grade(gradeChoice);
					menu2(formChoice);
				}
				break;
			case 9:
				menu1();
				break;
		}
	}
	
	private static void handleUnsavedForm(int formChoice) {
		String formType = "Test";
		if(formChoice == 1) formType = "Survey";
		System.out.println("Please enter a filename for the " + formType + ". Only use letters, numbers, underscores, and dashes.");
		
		try {
			String filename = "";
			while(filename.length() == 0) {
				Scanner usrIn = new Scanner(System.in);
				filename = usrIn.nextLine();
				String filenameRegex = "[-_A-Za-z0-9]*";
				if(filename.matches(filenameRegex)) {
					if(formChoice == 1) filename+= "-survey.ser";
					else filename+= "-test.ser";
					save(filename, formChoice);
				}
				else {
					System.out.println("Only enter letters, numbers, underscores, and dashes.");
					filename = "";
				}
			}
		}
		catch(InputMismatchException ex) {
			System.out.println("Please try again with valid input.");
			saveMenu(formChoice);
		}
		catch(Exception ex) {
			System.out.println("Please try again with valid input.");
			saveMenu(formChoice);
		}
	}
	
	private static void menu3(int formChoice) {
		System.out.println("1) Add a new T/F question");
		System.out.println("2) Add a new multiple choice question");
		System.out.println("3) Add a new short answer question");
		System.out.println("4) Add a new essay question");
		System.out.println("5) Add a new ranking question");
		System.out.println("6) Add a new matching question");
		System.out.println("7) Quit creation");
		
		try {
			int choice = 0;
			Scanner usrIn = new Scanner(System.in);
			choice = usrIn.nextInt();
			if(choice <= 0 || choice > 7) {
				System.out.println("Please choose a valid option.");
				menu3(formChoice);
			}
			else {
				addQuestion(formChoice, choice);
				menu3(formChoice);
			}
		}
		catch(InputMismatchException ex) {
			System.out.println("Please try again with valid input.");
			menu3(formChoice);
		}
		catch(Exception ex) {
			System.out.println(ex);
			System.out.println("Please try again with valid input.");
			menu3(formChoice);
		}
	}
	
	private static void addQuestion(int formChoice, int questionChoice) {
		switch(questionChoice) {
			case 1:
				if(formChoice == 1) activeSurvey.addQuestion(makeTorF(formChoice));
				else activeTest.addQuestion(makeTorF(formChoice));
				break;
			case 2:
				if(formChoice == 1) activeSurvey.addQuestion(makeMultipleChoice(formChoice));
				else activeTest.addQuestion(makeMultipleChoice(formChoice));
				break;
			case 3:
				if(formChoice == 1) activeSurvey.addQuestion(makeShortAnswer(formChoice));
				else activeTest.addQuestion(makeShortAnswer(formChoice));
				break;
			case 4:
				if(formChoice == 1) activeSurvey.addQuestion(makeEssay(formChoice));
				else activeTest.addQuestion(makeEssay(formChoice));
				break;
			case 5:
				if(formChoice == 1) activeSurvey.addQuestion(makeRanking(formChoice));
				else activeTest.addQuestion(makeRanking(formChoice));
				break;
			case 6:
				if(formChoice == 1) activeSurvey.addQuestion(makeMatching(formChoice));
				else activeTest.addQuestion(makeMatching(formChoice));
				break;
			case 7:
				menu2(formChoice);
		}
		menu3(formChoice);
	}
	
	private static Essay makeEssay(int formChoice) {
		Essay newEssay = new Essay("");
		Scanner usrIn = new Scanner(System.in);
		System.out.println("Enter the prompt for your Essay question:");
		newEssay = new Essay(usrIn.nextLine());
		if(formChoice == 2) activeTest.addCorrectResponse(new ArrayList());
		return newEssay;
	}
	
	private static Matching makeMatching(int formChoice) {
		Matching newMatching = new Matching("");
		Scanner usrIn = new Scanner(System.in);
		System.out.println("Enter the prompt of your matching question: ");
		newMatching = new Matching(usrIn.nextLine());
		int amtChoices = 0;
		System.out.println("How many choices would you like to enter?");
		while(amtChoices <= 0) {
			try {
				amtChoices = usrIn.nextInt();
			
				if(amtChoices < 0) System.out.println("Please provide a positive number.");
				else usrIn.nextLine();
			}
			catch (InputMismatchException ex) {
				System.out.println("Enter a positive number.");
				usrIn.next();
			}
		}
		for(int i= 0; i < amtChoices; i++) {
			System.out.println("Enter the new choice:");
			newMatching.addChoice(usrIn.nextLine());
		}
		for(int i = 0; i < amtChoices; i++) {
			System.out.println("Enter an option for matching:");
			newMatching.addMatchingChoice(usrIn.nextLine());
		}
		if(formChoice == 2) {
			System.out.println("The choices are:");
			newMatching.displayChoices();
			System.out.println("The matches are:");
			newMatching.displayMatches();
			ArrayList<Integer> correctOrder = new ArrayList<Integer>();
			for(int i = 0; i < amtChoices; i++) {
				System.out.println("Enter the correct match for choice " + (char) (i + 65) + ".");
				int match = 0;
				while(match > amtChoices || match <= 0) {
					try {
						match = usrIn.nextInt();
					
						if(match > amtChoices || match <= 0) System.out.println("Please enter one of the matching options.");
						else if (correctOrder.contains(match)) {
							System.out.println("Enter a unique choice.");
							match = 0;
						}
						else correctOrder.add(match);
					}
					catch (InputMismatchException ex) {
						System.out.println("Enter an integer valaue.");
						usrIn.next();
					}
				}
			}
			activeTest.addCorrectResponse(correctOrder);
		}
		return newMatching;
	}
	
	private static MultipleChoice makeMultipleChoice(int formChoice) {
		MultipleChoice newMultipleChoice = new MultipleChoice("");
		Scanner usrIn = new Scanner(System.in);
		System.out.println("Enter the prompt of your Multiple Choice question:");
		newMultipleChoice = new MultipleChoice(usrIn.nextLine());
		int amtChoices = 0;
		System.out.println("How many choices would you like to enter?");
		while(amtChoices <= 0) {
			String userInput = usrIn.nextLine();
			if(!userInput.matches("[0-9]*")) System.out.println("Please provide valid input.");
			else amtChoices = Integer.parseInt(userInput);
			
			if(amtChoices < 0) System.out.println("Please provide a positive number.");
		}
		for(int i= 0; i < amtChoices; i++) {
			System.out.println("Enter the new choice:");
			newMultipleChoice.addChoice(usrIn.nextLine());
		}
		if(formChoice == 2) {
			System.out.println("How many correct choices are there?");
			int numRightChoices = 0;
			while(numRightChoices < 1 || numRightChoices > amtChoices) {
				try {
					numRightChoices = usrIn.nextInt();
					
					if(numRightChoices < 0 || numRightChoices > amtChoices) System.out.println("Enter a value within the range of the 1 - the number of choices for the question.");
				}
				catch (InputMismatchException ex) {
					System.out.println("Enter an integer value.");
					usrIn.next();
				}
			}
			newMultipleChoice.displayChoices();
			ArrayList<Integer> correctOrder = new ArrayList<Integer>();
			
			for(int i = 0; i < numRightChoices; i++) {
				System.out.println("Enter a correct choice.");
				int match = 0;
				while(match > amtChoices || match <= 0) {
					try {
						match = usrIn.nextInt();
					
						if(match > amtChoices || match <= 0) System.out.println("Please enter one of the options.");
						else if (correctOrder.contains(match)) {
							System.out.println("Enter a unique option.");
							match = 0;
						}
						else correctOrder.add(match);
					}
					catch (InputMismatchException ex) {
						System.out.println("Enter an integer.");
						usrIn.next();
					}
				}
			}
			newMultipleChoice.setNumResponses(numRightChoices);
			activeTest.addCorrectResponse(correctOrder);
		}
		else {
			System.out.println("How many responses should someone taking the survey give?");
			int numResponses = 0;
			while(numResponses < 1 || numResponses > amtChoices) {
				try {
					numResponses = usrIn.nextInt();
					
					if(numResponses < 1 || numResponses > amtChoices) System.out.println("Enter a valid choice.");
				}
				catch(InputMismatchException ex) {
					System.out.println("Enter an integer.");
					usrIn.next();
				}
			}
			newMultipleChoice.setNumResponses(numResponses);
		}
		return newMultipleChoice;
	}
	
	private static Ranking makeRanking(int formChoice) {
		Ranking newRanking = new Ranking("");
		Scanner usrIn = new Scanner(System.in);
		System.out.println("Enter the prompt for your Ranking question:");
		newRanking = new Ranking(usrIn.nextLine());
		int amtChoices = 0;
		System.out.println("How many choices would you like to enter?");
		while(amtChoices <= 0) {
			try {
				amtChoices = usrIn.nextInt();
			
				if(amtChoices < 0) System.out.println("Please provide a positive number.");
				else usrIn.nextLine();
			}
			catch (InputMismatchException ex) {
				System.out.println("Enter an integer.");
				usrIn.next();
			}
		}
		for(int i= 0; i < amtChoices; i++) {
			System.out.println("Enter the new choice:");
			newRanking.addChoice(usrIn.nextLine());
		}
		if(formChoice == 2) {
			ArrayList<Integer> correctOrder = new ArrayList<Integer>();
			System.out.println("Please enter the correct order, one value at a time.");
			System.out.println("Choices: ");
			newRanking.displayChoices();
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
			activeTest.addCorrectResponse(correctOrder);
		}
		return newRanking;
	}
	
	private static ShortAnswer makeShortAnswer(int formChoice) {
		ShortAnswer newShortAnswer = new ShortAnswer("");
		Scanner usrIn = new Scanner(System.in);
		System.out.println("Enter a prompt for your Short Answer question: ");
		newShortAnswer = new ShortAnswer(usrIn.nextLine());
		if(formChoice == 2) {
			System.out.println("How many correct answers are there?");
			int numRightAns = 0;
			while(numRightAns < 1) {
				try {
					numRightAns = usrIn.nextInt();
					
					if(numRightAns < 1) System.out.println("Enter a value greater than 0.");
				}
				catch(InputMismatchException ex) {
					System.out.println("Enter an integer.");
					usrIn.next();
				}
			}
			String newCA = "";
			Scanner usrIn2 = new Scanner(System.in);
			ArrayList<String> correctAnswers= new ArrayList<String>();
			for(int i = 0; i < numRightAns; i++) {
				System.out.println("Enter a correct answer.");
				newCA = usrIn2.nextLine();
				correctAnswers.add(newCA);
			}
			activeTest.addCorrectResponse(correctAnswers);
		}
		return newShortAnswer;
	}
	
	private static TorF makeTorF(int formChoice) {
		TorF newTorF = new TorF("");
		Scanner usrIn = new Scanner(System.in);
		System.out.println("Enter a prompt for yout True/False question:");
		newTorF = new TorF(usrIn.nextLine());
		if (formChoice == 2) {
			System.out.println("Enter 1 if the answer is true and 2 if the answer is false.");
			ArrayList<Integer> correctAnswer = new ArrayList<Integer>();
			int correctChoice = 0;
			while(correctChoice != 1 && correctChoice != 2) {
				try{
					correctChoice = usrIn.nextInt();
					
					if(correctChoice != 1 && correctChoice != 2) System.out.println("Please enter a 1 or 2.");
					else correctAnswer.add(correctChoice);
				}
				catch(InputMismatchException ex) {
					System.out.println("Enter a 1 or a 2.");
					usrIn.next();
				}
				activeTest.addCorrectResponse(correctAnswer);
			}
		}
		return newTorF;
	}
	
	private static void saveMenu(int formChoice) {
		String formType = "Test";
		if(formChoice == 1) formType = "Survey";
		System.out.println("Please enter a filename for the " + formType + ". Only use letters, numbers, underscores, and dashes.");
		
		try {
			String filename = "";
			while(filename.length() == 0) {
				Scanner usrIn = new Scanner(System.in);
				filename = usrIn.nextLine();
				String filenameRegex = "[-_A-Za-z0-9]*";
				if(filename.matches(filenameRegex)) {
					if(formChoice == 1) filename+= "-survey.ser";
					else filename+= "-test.ser";
					save(filename, formChoice);
				}
				else {
					System.out.println("Only enter letters, numbers, underscores, and dashes.");
					filename = "";
				}
			}
			menu2(formChoice);
		}
		catch(InputMismatchException ex) {
			System.out.println("Please try again with valid input.");
			saveMenu(formChoice);
		}
		catch(Exception ex) {
			System.out.println("Please try again with valid input.");
			saveMenu(formChoice);
		}
	}
	
	private static void loadMenu(int formChoice) throws ClassNotFoundException, IOException {
		File dir = new File(".");
		File[] listFiles = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if(formChoice == 1) return name.toLowerCase().endsWith("-survey.ser");
				else return name.toLowerCase().endsWith("-test.ser");
			}
		});
		for(int i = 0; i < listFiles.length; i++) {
			System.out.println((i + 1) + ")" + listFiles[i].getName().substring(0, listFiles[i].getName().length() - 4));
		}
		System.out.println("Please enter the number of the file that you would like to load.");
		Scanner usrIn = new Scanner(System.in);
		int fileChoice = 0;
		while(fileChoice == 0 || fileChoice > listFiles.length) {
			try {
				fileChoice = usrIn.nextInt();
				
				if(fileChoice == 0 || fileChoice > listFiles.length) System.out.println("Enter a valid choice.");
			}
			catch (InputMismatchException ex) {
				System.out.println("Enter an integer.");
				usrIn.next();
			}
		}
		load(listFiles[fileChoice - 1].getName(), formChoice);
	}
	
	private static void save(String filename, int formChoice) throws IOException {
		try {
			FileOutputStream file = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(file);
			
			if(formChoice == 1) {
				out.writeObject(activeSurvey);
				activeSurvey.updateSaveName(filename);
			}
			else {
				out.writeObject(activeTest);
				activeTest.updateSaveName(filename);
			}
			
			out.close();
			file.close();
			
			System.out.println("Saved form " + filename + " successfully.");
		}
		catch(IOException ex) {
			throw ex;
		}
	}
	
	private static void load(String filename, int formChoice) throws IOException, ClassNotFoundException {
		try {
			FileInputStream file = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(file);
			
			
			try {
				if (formChoice == 1) activeSurvey = (Survey) in.readObject();
				else activeTest = (Test) in.readObject();
			}
			catch(EOFException ex) {}
			in.close();
			file.close();
			
			menu2(formChoice);
		}
		catch(EOFException ex) {
			menu2(formChoice);
		}
		catch(IOException ex) {
			throw ex;
		}
		catch(ClassNotFoundException ex) {
			throw ex;
		}
		
	}
}
