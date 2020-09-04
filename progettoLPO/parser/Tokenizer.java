package progettoLPO.parser;

import java.io.IOException;

public interface Tokenizer extends AutoCloseable {

	TokenType next() throws TokenizerException;

	TokenType tokenType();

	String tokenString();

	int intValue();

	boolean boolValue();
	
	String seasonValue(); //funzione che serve poi per riconoscere gli enum season

	public void close() throws IOException;

	int getLineNumber();

}