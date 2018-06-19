import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ShortAnswer extends Essay {
	static final long serialVersionUID = 11;
	
	public ShortAnswer(String quest) {
		super(quest);
	}
	
	public void displayQuestion() {
		System.out.println(question);
	}
	
	public void tabulateQuestion() {
		displayQuestion();
		ArrayList<String> differentResponses= new ArrayList<String>();
		ArrayList<Integer> amounts= new ArrayList<Integer>();
		
		for(String s: responses) {
			if(!differentResponses.contains(s)) {
				differentResponses.add(s);
				amounts.add(1);
			}
			else {
				int index = differentResponses.indexOf(s);
				amounts.set(index, amounts.get(index) + 1);
			}
		}
		
		for(int i = 0; i < differentResponses.size(); i++) {
			System.out.println(amounts.get(i) + ") " + differentResponses.get(i));
		}
		System.out.print("\n");
	}
	
	public String getType() {
		return "sa";
	}
}
