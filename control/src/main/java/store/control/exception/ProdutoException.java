package store.control.exception;

public class ProdutoException extends Exception{

    public ProdutoException() {
        super("Produto não localizada no banco de dados!");
    }

    public ProdutoException (String message) {
        super(message);
    }
}
