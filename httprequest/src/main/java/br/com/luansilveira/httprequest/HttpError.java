package br.com.luansilveira.httprequest;

import br.com.luansilveira.json.JSON;

/**
 * Classe utilizada para representar um erro HTTP retornado do servidor.
 */
public class HttpError {

    private int statusCode;
    private String textStatus;
    private JSON data;

    public HttpError(int statusCode, String textStatus, JSON data) {
        this.statusCode = statusCode;
        this.textStatus = textStatus;
        this.data = data;
    }

    /**
     * Retorna o código de erro HTTP do servidor (200, 404, 500, etc.).
     *
     * @return int
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Define o código de erro HTTP do servidor (200, 404, 500, etc.).
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Retorna o texto de erro referente ao código de erro HTTP do servidor (200, 404, 500, etc.).
     *
     * @return int
     */
    public String getTextStatus() {
        return textStatus;
    }

    /**
     * Define o texto de erro HTTP do servidor.
     */
    public void setTextStatus(String textStatus) {
        this.textStatus = textStatus;
    }

    /**
     * Retorna os dados do erro em formato JSON.
     *
     * @return JSON
     */
    public JSON getData() {
        return data;
    }

    /**
     * Define os dados do erro em formato JSON.
     */
    public void setData(JSON data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Status: " + this.statusCode + " - " + (this.textStatus == null ? "" : this.textStatus) + "\n"
                + (data == null ? "" : data.toString());
    }
}