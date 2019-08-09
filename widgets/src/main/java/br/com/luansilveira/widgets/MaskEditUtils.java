package br.com.luansilveira.widgets;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Luan Christian Nascimento da Silveira
 */
public class MaskEditUtils {

    public static final String MASK_CEP = "#####-###";
    public static final String MASK_DATA_DD_MM_AAAA = "##/##/####";
    public static final String MASK_CPF = "###.###.###-##";
    public static final String MASK_CNPJ = "##.###.###/####-##";
    public static final String MASK_TEL_8 = "####-####";
    public static final String MASK_TEL_9 = "#####-####";
    public static final String MASK_TEL_DDD_8 = "(##) ####-####";
    public static final String MASK_TEL_DDD_9 = "(##) #####-####";

    public static String unmask(String s) {
        return s == null ? null : s.replaceAll("[^\\d]+", "");
    }

    public static String mask(String s, String mask) {
        if (s == null || s.isEmpty()) return null;
        StringBuilder retorno = new StringBuilder();
        int indexOfMask = 0;

        for (char c : s.toCharArray()) {
            if (indexOfMask >= mask.length()) break;
            char m = mask.charAt(indexOfMask);
            while (m != '#' && indexOfMask < (mask.length() - 1)) {
                retorno.append(m);
                m = mask.charAt(++indexOfMask);
            }

            retorno.append(c);
            indexOfMask++;
        }

        return retorno.toString();
    }

    public static String maskTelefone(String s) {
        return mask(s, s.length() > 10 ? MASK_TEL_DDD_9 : MASK_TEL_DDD_8);
    }

    public static String maskCnpj(String s) {
        return mask(s, MASK_CNPJ);
    }

    public static String maskCep(String s) {
        return mask(s, MASK_CEP);
    }

    public static TextWatcher insert(final EditText editText, final String mask) {
        return insert(editText, mask, false);
    }


    public static TextWatcher insert(final EditText editText, final String mask, boolean variavel) {
        return new TextWatcher() {

            private String atual;

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(atual)) {
                    editText.removeTextChangedListener(this);
                    String maskType = mask;
                    String value = MaskEditUtils.unmask(s.toString());
                    if (variavel) {
                        if (MASK_CPF.equals(mask)) {
                            maskType = value.length() > 11 ? MASK_CNPJ : MASK_CPF;
                        } else if (MASK_TEL_8.equals(mask)) {
                            maskType = value.length() > 8 ? MASK_TEL_9 : MASK_TEL_8;
                        } else if (MASK_TEL_DDD_8.equals(mask)) {
                            maskType = value.length() > 10 ? MASK_TEL_DDD_9 : MASK_TEL_DDD_8;
                        }
                    }

                    atual = MaskEditUtils.mask(value, maskType);
                    if (MASK_DATA_DD_MM_AAAA.equals(mask)) {
                        atual = MaskEditUtils.ajustarData(atual);
                    }

                    editText.setText(atual);
                    editText.setSelection(editText.length());
                    editText.addTextChangedListener(this);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        };
    }

    /**
     * Ajusta uma data que esteja incorreta.
     * Ex.: Se a data informada for 87/20/1999, a data será ajustada automaticamente para 08/07/2019.
     *
     * @param data Data já mascarada no formato dd/MM/yyyy
     * @return String - data ajustada
     */
    private static String ajustarData(String data) {
        if (data == null) return null;

        data = data.replaceAll("[^\\d/]+", "");
        if (data.isEmpty()) return data;

        int maximoDias = 31;

        String[] strData = data.split("/");
        String strDia = strData[0];
        String strMes = strData.length > 1 ? strData[1] : "";
        String strAno = strData.length > 2 ? strData[2] : "";

        if (Integer.parseInt(strDia) > maximoDias) {
            strMes = strDia.substring(strDia.length() - 1) + strMes;
            if (strMes.length() >= 2) {
                strAno = strMes.substring(strMes.length() - 1) + strAno;
                if (strMes.length() > 2) strMes = strMes.substring(0, 2);
            }
            strDia = "0" + strDia.substring(0, 1);
        }

        if (!strMes.isEmpty() && Integer.parseInt(strMes) > 12) {
            strAno = strMes.substring(strMes.length() - 1) + strAno;
            strMes = "0" + strMes.substring(0, 1);
        }
        if (strAno.length() > 4) strAno = strAno.substring(0, 4);

        //Se a data estiver completa, corrige o número de dias do mês
        if (strMes.length() == 2 && strAno.length() == 4) {
            int mes = Integer.parseInt(strMes);
            int ano = Integer.parseInt(strAno);
            int dia = Integer.parseInt(strDia);
            Calendar calendar = new GregorianCalendar(ano, mes - 1, 1);
            maximoDias = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            if (dia > maximoDias) strDia = String.valueOf(maximoDias);
        }

        return strDia + (strMes.isEmpty() ? "" : "/" + strMes) + (strAno.isEmpty() ? "" : "/" + strAno);
    }

    public static String removerAcentos(String string) {
        if (string != null) {
            string = Normalizer.normalize(string, Normalizer.Form.NFD);
            string = string.replaceAll("[^\\p{ASCII}]", "");
        }
        return string;
    }


    public static EditText currencyMask(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            private String atual;
            private double valor;
            private DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(atual)) {
                    editText.removeTextChangedListener(this);
                    String texto = s.toString().replaceAll("[.,]", "");
                    valor = Double.parseDouble(texto.isEmpty() ? "0" : texto);
                    String valorFormatado = decimalFormat.format(valor / 100d);
                    atual = valorFormatado;
                    editText.setText(valorFormatado);
                    editText.setSelection(editText.length());
                    editText.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return editText;
    }

}
