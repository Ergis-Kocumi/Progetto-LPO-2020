package progettoLPO.parser.ast; 

import progettoLPO.visitors.Visitor;

public class SeasonLiteral extends PrimLiteral<String> {

	public SeasonLiteral(String n) {
		super(n);
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitSeasonLiteral(value); 
	}
}