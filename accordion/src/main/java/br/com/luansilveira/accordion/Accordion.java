package br.com.luansilveira.accordion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class Accordion extends LinearLayout {

    ImageView imgIcon;
    ImageView imgButton;
    TextView txtTitle;
    View layoutCabecalho;
    View divider;
    LinearLayout layoutContent;

    //attrs
    private String title;
    private int icon = -1;
    private int textColor;
    private boolean collapsed;


    public Accordion(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getAttrs(context, attrs);
    }

    public Accordion(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(context, attrs);
    }

    public void getAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Accordion);
        this.title = array.getString(R.styleable.Accordion_title);
        this.icon = array.getResourceId(R.styleable.Accordion_icon, -1);
        this.collapsed = array.getBoolean(R.styleable.Accordion_collapsed, false);
        this.textColor = array.getColor(R.styleable.Accordion_textColorTitle, -1);
        array.recycle();
    }

    @SuppressLint("InflateParams")
    public void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_accordion, null);
        txtTitle = view.findViewById(R.id.txtTitle);
        layoutCabecalho = view.findViewById(R.id.layoutCabecalho);
        layoutContent = view.findViewById(R.id.layoutContent);
        imgIcon = view.findViewById(R.id.imgIcon);
        imgButton = view.findViewById(R.id.imgButton);
        divider = view.findViewById(R.id.divider);


        //Coloca as views dentro do layout do conteúdo.
        while (getChildCount() > 0) {
            View vchild = getChildAt(0);
            removeView(vchild);
            layoutContent.addView(vchild);
        }

        addView(view);
    }

    public void prepararLayout(Context context) {
        init(context);
        int visibility = collapsed ? GONE : VISIBLE;
        layoutContent.setVisibility(visibility);
        divider.setVisibility(visibility);
        txtTitle.setText(title);
        if (textColor != -1) txtTitle.setTextColor(textColor);
        if (icon != -1) imgIcon.setImageResource(icon);
        imgButton.setImageResource(collapsed ? R.drawable.ic_expand : R.drawable.ic_collapse);
        layoutCabecalho.setOnClickListener(v -> {
            boolean visible = layoutContent.getVisibility() == View.VISIBLE;
            int visibility1 = visible ? GONE : VISIBLE;
            layoutContent.setVisibility(visibility1);
            divider.setVisibility(visibility1);
            imgButton.setImageResource(visible ? R.drawable.ic_expand : R.drawable.ic_collapse);
        });
    }


    @Override
    protected void onFinishInflate() {
        prepararLayout(getContext());
        super.onFinishInflate();
    }
}
