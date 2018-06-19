public class Survey extends Form {
	public static long serialVersionUID = 2;
	
	public Survey() {}

	public void displayForm() {
		for(Question q: questions) {
			q.displayQuestion();
			System.out.print("\n");
		}
	}

	public void addQuestion(Question newQuestion) {
		questions.add(newQuestion);
	}
	
	public boolean hasQuestions() {
		boolean quests = true;
		if(questions.size() == 0) quests = false;
		
		return quests;
	}
	
	public void modifyQuestion(int quest) {
		questions.get(quest).modifyQuestion();
	}

	@Override
	void displayForModification() {
		for(int i = 0; i < questions.size(); i++) {
			System.out.print((i + 1) + ") ");
			questions.get(i).displayQuestion();
			System.out.print("\n");
		}
	}

	@Override
	public void take() {
		for(Question q: questions) {
			q.displayQuestion();
			System.out.print("\n");
			q.takeQuestion();
		}
	}
}
