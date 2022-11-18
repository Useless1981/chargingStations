public class MultiEvaluator {

	public static Evaluator randomStarts(Scenario scenario, int amount) {
		Evaluator bestEval = null;
		for(int i = 0; i < amount; i++) {
			Evaluator e = Evaluator.create(scenario);
			Score s = e.evaluate();
			if(bestEval == null || s.getTotalScore() < bestEval.evaluate().getTotalScore()) bestEval = e;
		}
		return bestEval;
	}

}
