package br.com.luansilveira.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class IncrementEditText extends LinearLayout {

    private EditText editText;

    private int min;
    private int max;

    public IncrementEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.layout_increment_button, this);

        editText = findViewById(R.id.editText);
        Button btMais = findViewById(R.id.btMais);
        Button btMenos = findViewById(R.id.btMenos);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.IncrementEditText);

        boolean editavel = array.getBoolean(R.styleable.IncrementEditText_editavel, true);
        int maxLength = array.getInt(R.styleable.IncrementEditText_maxLength, 4);
        int valorInicial = array.getInt(R.styleable.IncrementEditText_valorInicial, -1);
        this.min = array.getInt(R.styleable.IncrementEditText_valorMin, -1);
        this.max = array.getInt(R.styleable.IncrementEditText_valorMax, -1);

        if (valorInicial >= 0) editText.setText(String.valueOf(valorInicial));
        editText.setClickable(editavel);
        editText.setFocusable(editavel);
        editText.setInputType(editavel ? InputType.TYPE_CLASS_NUMBER : InputType.TYPE_NULL);
        if (maxLength > 0) {
            editText.setEms(maxLength);
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        }

        btMais.setOnClickListener(v -> alterarValorTexto(true));
        btMenos.setOnClickListener(v -> alterarValorTexto(false));
        if (editavel) editText.addTextChangedListener(new TextWatcher() {
            boolean editando = false;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!editando) {
                    int qtde = getIntFromText(s.toString().trim());
                    if (min >= 0 && qtde <= min){
                        editando = true;
                        editText.setText(String.valueOf(min));
                    }

                    if (max >= 0 && qtde >= max){
                        editando = true;
                        editText.setText(String.valueOf(max));
                    }
                } else editando = false;
            }
        });

        array.recycle();
    }

    protected void alterarValorTexto(boolean incrementar){
        int selection = editText.getSelectionEnd();
        int qtde = getIntFromText(editText.getText().toString().trim());

        if (min >= 0){
            if (!incrementar && qtde <= min) return;
        }
        if (max >= 0){
            if (incrementar && qtde >= max) return;
        }

        editText.setText(String.valueOf(incrementar ? ++qtde : --qtde));
        editText.setSelection(selection);
    }

    private int getIntFromText(String text){
        int ret;
        try {
            ret = Integer.parseInt(text);
        } catch (NumberFormatException e){
            ret = 0;
        }

        return ret;
    }

    public Editable getText(){
        return editText.getText();
    }

    public void setText(CharSequence text){
        this.editText.setText(text);
    }

    public void incrementar(){
        this.alterarValorTexto(true);
    }

    public void decrementar(){
        this.alterarValorTexto(false);
    }
}
