package br.com.luansilveira.widgets;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * EditText Customizado (herdado do LinearLayout) que contém um botão para pegar uma data pelo calendário.
 *
 * @author Luan Christian Nascimento da Silveira
 */
public class DatePickerEditText extends LinearLayout {

    private Calendar initialCalendar;
    private EditText editText;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));


    public DatePickerEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.layout_datepicker_edittext, this);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DatePickerEditText);

        int textColor = array.getColor(R.styleable.DatePickerEditText_textColor, getResources().getColor(android.R.color.black));

        ImageButton btCalendar = findViewById(R.id.btCalendar);
        editText = findViewById(R.id.editText);
        editText.setTextColor(textColor);
        editText.addTextChangedListener(MaskEditUtils.insert(editText, MaskEditUtils.MASK_DATA_DD_MM_AAAA));

        DatePickerDialog.OnDateSetListener listener = (view, year, month, dayOfMonth) -> {
            DecimalFormat decimalFormat = new DecimalFormat("00");
            String data = decimalFormat.format(dayOfMonth) + "/" + decimalFormat.format(month + 1) + "/" + year;
            editText.setText(data);
            editText.setError(null);
        };

        btCalendar.setOnClickListener(v -> {
            editText.requestFocus();
            Calendar calendar = this.initialCalendar;
            if (calendar == null) {
                calendar = Calendar.getInstance();
            }

            if (!getText().isEmpty()) {
                try {
                    Date date = dateFormat.parse(getText());
                    calendar.setTime(date);
                } catch (ParseException e) {
                    String[] data = getText().split("/");
                    int dia = data.length > 0 ? Integer.parseInt(data[0]) : 0;
                    int mes = data.length > 1 ? Integer.parseInt(data[1]) : 0;

                    calendar = new GregorianCalendar(
                            calendar.get(Calendar.YEAR),
                            mes == 0 ? calendar.get(Calendar.MONTH) : mes - 1,
                            dia == 0 ? calendar.get(Calendar.DAY_OF_MONTH) : dia);
                }
            }

            DatePickerDialog dialog = new DatePickerDialog(context, listener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        });

        array.recycle();
    }


    public DatePickerEditText setInitialCalendar(Calendar initialCalendar) {
        this.initialCalendar = initialCalendar;
        return this;
    }

    public String getText() {
        return this.editText.getText().toString();
    }

    public DatePickerEditText setText(String text) {
        this.editText.setText(text);
        return this;
    }

    public Date getDate() {
        try {
            return dateFormat.parse(this.getText());
        } catch (ParseException e) {
            return null;
        }
    }

    public EditText getEditText() {
        return editText;
    }
}
