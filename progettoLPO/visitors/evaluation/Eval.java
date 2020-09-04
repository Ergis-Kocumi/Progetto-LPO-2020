package progettoLPO.visitors.evaluation;

import java.io.PrintWriter;

import progettoLPO.environments.EnvironmentException;
import progettoLPO.environments.GenEnvironment;
import progettoLPO.parser.ast.Block;
import progettoLPO.parser.ast.Exp;
import progettoLPO.parser.ast.Stmt;
import progettoLPO.parser.ast.StmtSeq;
import progettoLPO.parser.ast.VarIdent;
import progettoLPO.visitors.Visitor;

import static java.util.Objects.requireNonNull;

public class Eval implements Visitor<Value> {

	private final GenEnvironment<Value> env = new GenEnvironment<>();
	private final PrintWriter printWriter; // output stream used to print values

	public Eval() {
		printWriter = new PrintWriter(System.out, true);
	}

	public Eval(PrintWriter printWriter) {
		this.printWriter = requireNonNull(printWriter);
	}

	// dynamic semantics for programs; no value returned by the visitor

	@Override
	public Value visitProg(StmtSeq stmtSeq) {
		try {
			stmtSeq.accept(this);
			// possible runtime errors
			// EnvironmentException: undefined variable
		} catch (EnvironmentException e) {
			throw new EvaluatorException(e);
		}
		return null;
	}

	// dynamic semantics for statements; no value returned by the visitor

	@Override
	public Value visitAssignStmt(VarIdent ident, Exp exp) {
		env.update(ident, exp.accept(this));
		return null;
	}

	@Override
	public Value visitPrintStmt(Exp exp) {
		printWriter.println(exp.accept(this));
		return null;
	}

	@Override
	public Value visitVarStmt(VarIdent ident, Exp exp) {
		env.dec(ident, exp.accept(this));
		return null;
	}

	@Override
	public Value visitIfStmt(Exp exp, Block thenBlock, Block elseBlock) {
		if (exp.accept(this).toBool())
			thenBlock.accept(this);
		else if (elseBlock != null)
			elseBlock.accept(this);
		return null;
	}

	@Override
	public Value visitBlock(StmtSeq stmtSeq) {
		env.enterScope();
		stmtSeq.accept(this);
		env.exitScope();
		return null;
	}
	
	@Override
	public Value visitForStmt(VarIdent id, Exp exp, Block stmts) // fatta come spiega il file della 
	{ 
		var v = env.lookup(id).toInt(); // valore di i per l'iterazione in cui ci troviamo
		var m = exp.accept(this).toInt(); // m = valore massimo per i nella


		if(v <= m) 
		{
			//i passaggi descritti nel file specifiche.pdf
			stmts.accept(this);
			var a = new IntValue(Integer.valueOf(++v)); // v essendo il valore di i, deve venire incrementato ovviamente ad ogni interazione per il for 
			env.update(id, a);  

			return visitForStmt(id, exp, stmts);
		}
		return null;
	}

	// dynamic semantics for sequences of statements
	// no value returned by the visitor

	@Override
	public Value visitSingleStmt(Stmt stmt) {
		stmt.accept(this);
		return null;
	}

	@Override
	public Value visitMoreStmt(Stmt first, StmtSeq rest) {
		first.accept(this);
		rest.accept(this);
		return null;
	}

	// dynamic semantics of expressions; a value is returned by the visitor

	@Override
	public Value visitAdd(Exp left, Exp right) {
		return new IntValue(left.accept(this).toInt() + right.accept(this).toInt());
	}

	@Override
	public Value visitIntLiteral(int value) {
		return new IntValue(value);
	}

	@Override
	public Value visitMul(Exp left, Exp right) {
		return new IntValue(left.accept(this).toInt() * right.accept(this).toInt());
	}

	@Override
	public Value visitSign(Exp exp) {
		return new IntValue(-exp.accept(this).toInt());
	}

	@Override
	public Value visitVarIdent(VarIdent id) {
		return env.lookup(id);
	}

	@Override
	public Value visitNot(Exp exp) {
		return new BoolValue(!exp.accept(this).toBool());
	}

	@Override
	public Value visitAnd(Exp left, Exp right) {
		return new BoolValue(left.accept(this).toBool() && right.accept(this).toBool());
	}

	@Override
	public Value visitBoolLiteral(boolean value) {
		return new BoolValue(value);
	}

	@Override
	public Value visitEq(Exp left, Exp right) {
		return new BoolValue(left.accept(this).equals(right.accept(this)));
	}
	
	@Override
	public Value visitLower(Exp left, Exp right) { 
		return new BoolValue(left.accept(this).lower(right.accept(this)));
	}

	@Override
	public Value visitPairLit(Exp left, Exp right) {
		return new PairValue(left.accept(this), right.accept(this));
	}

	@Override
	public Value visitFst(Exp exp) {
		return exp.accept(this).toProd().getFstVal();
	}

	@Override
	public Value visitSnd(Exp exp) {
		return exp.accept(this).toProd().getSndVal();
	}
	
	@Override
	public Value visitNumOf(Exp exp) {
		return new IntValue(exp.accept(this).toIntSeason());
	}
	
	@Override
	public Value visitSeasonOf(Exp exp) {
		return new SeasonValue(exp.accept(this).toSeason());
	}
	
	@Override
	public Value visitSeasonLiteral(String value) {
		return new SeasonValue(value);
	}

}
