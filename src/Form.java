import java.io.Serializable;
import java.util.ArrayList;

abstract class Form implements Serializable {
	static final long serialVersionUID = 1;
	protected ArrayList<Question> questions = new ArrayList<Question>();
	protected String saveName = "";
	
	abstract void displayForm();
	abstract void addQuestion(Question newQuestion);
	abstract void displayForModification();
	abstract void modifyQuestion(int quest);
	abstract void take();
	
	public void updateSaveName(String newName) {
		saveName = newName;
	};
	
	public String getSaveName() {
		return saveName;
	}
	
	public boolean hasSaveName() {
		if(saveName.equals("")) return false;
		else return true;
	}
	public Question getQuestion(int questionNumber) { return questions.get(questionNumber - 1); }
	public int getNumQuestions() { return questions.size(); }
	public void tabulate() {
		for(Question q: questions) {
			q.tabulateQuestion();
		}
	};
}
