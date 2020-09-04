package progettoLPO.visitors.evaluation;

public interface Value {
	/* default conversion methods */
	default int toInt() {
		throw new EvaluatorException("Expecting an integer");
	}
	
	default int toIntSeason() {
		throw new EvaluatorException("Expecting a season");
	}

	default boolean toBool() {
		throw new EvaluatorException("Expecting a boolean");
	}

	default PairValue toProd() {
		throw new EvaluatorException("Expecting a pair");
	}

	default String toSeason() { 
		throw new EvaluatorException("Expecting an integer");
	}
	
	boolean lower(Object obj); 
}
