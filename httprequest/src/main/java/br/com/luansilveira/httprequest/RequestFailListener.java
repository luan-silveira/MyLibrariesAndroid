package br.com.luansilveira.httprequest;

/**
 * Interface utilizada para implementar ações a serem executadas após a falha da requisição.
 */
public interface RequestFailListener {
    void fail(HttpError error);
}