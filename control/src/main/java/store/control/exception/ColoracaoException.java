package store.control.exception;

public class ColoracaoException extends Exception{

    public ColoracaoException () {
        super("Cor não localizada no banco de dados!");
    }
}
