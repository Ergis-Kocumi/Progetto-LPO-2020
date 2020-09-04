package progettoLPO.visitors;

import progettoLPO.parser.ast.Block;
import progettoLPO.parser.ast.Exp;
import progettoLPO.parser.ast.Stmt;
import progettoLPO.parser.ast.StmtSeq;
import progettoLPO.parser.ast.VarIdent;

public interface Visitor<T> {
	T visitAdd(Exp left, Exp right);

	T visitAssignStmt(VarIdent ident, Exp exp);

	T visitIntLiteral(int value);

	T visitEq(Exp left, Exp right);

	T visitMoreStmt(Stmt first, StmtSeq rest);

	T visitMul(Exp left, Exp right);

	T visitPrintStmt(Exp exp);

	T visitProg(StmtSeq stmtSeq);

	T visitSign(Exp exp);

	T visitVarIdent(VarIdent id); // the only corner case ...

	T visitSingleStmt(Stmt stmt);

	T visitVarStmt(VarIdent ident, Exp exp);

	T visitNot(Exp exp);

	T visitAnd(Exp left, Exp right);

	T visitBoolLiteral(boolean value);

	T visitIfStmt(Exp exp, Block thenBlock, Block elseBlock);

	T visitBlock(StmtSeq stmtSeq);

	T visitPairLit(Exp left, Exp right);

	T visitFst(Exp exp);

	T visitSnd(Exp exp);
	
	T visitForStmt(VarIdent id, Exp exp, Block stmts);
	
	T visitLower(Exp left, Exp right);
	
	T visitNumOf(Exp exp); 
	
	T visitSeasonLiteral(String value); 
	
	T visitSeasonOf(Exp exp);
}
