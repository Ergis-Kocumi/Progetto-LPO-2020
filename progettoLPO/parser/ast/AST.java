package progettoLPO.parser.ast;

import progettoLPO.visitors.Visitor;

public interface AST {
	<T> T accept(Visitor<T> visitor);
}
