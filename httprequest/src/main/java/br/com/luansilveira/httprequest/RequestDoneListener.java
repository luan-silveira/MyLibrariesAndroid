package br.com.luansilveira.httprequest;

import br.com.luansilveira.json.JSON;

/**
 * Interface utilizada para implementar ações a serem executadas após o êxito da requisição.
 */
public interface RequestDoneListener {
    void done(JSON result);
}