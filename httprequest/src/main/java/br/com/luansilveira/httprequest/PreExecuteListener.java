package br.com.luansilveira.httprequest;


/**
 * Interface utilizada para implementar ações a serem executadas antes do envio da requisição.
 */
public interface PreExecuteListener {
    void onRequestPreExecute();
}