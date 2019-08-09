package br.com.luansilveira.httprequest;

/**
 * Interface utilizada para implementar ações a serem executadas após o retorno da requisição.
 */
public interface PostExecuteListener {
    void onRequestPostExecute();
}