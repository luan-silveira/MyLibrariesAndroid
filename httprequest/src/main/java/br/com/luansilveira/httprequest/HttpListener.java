package br.com.luansilveira.httprequest;

import br.com.luansilveira.json.JSON;

/**
 * Interface utilizada para implementar ações a serem executadas após o retorno da requisição.<br>
 * Ela possui dois métodos para serem implementados quando a requisição for concluída com êxito ou em caso de falha.
 */
public interface HttpListener {
    /**
     * Método executado quando há êxito na requisição.
     *
     * @param result Dados JSON.
     */
    void onRequestDone(JSON result);

    /**
     * Método executado quando há falha na requisição.
     *
     * @param error HttpError
     */
    void onRequestFail(HttpError error);
}