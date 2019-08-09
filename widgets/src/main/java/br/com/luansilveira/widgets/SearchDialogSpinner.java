package br.com.luansilveira.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatSpinner;

import java.util.ArrayList;
import java.util.List;

public class SearchDialogSpinner extends AppCompatSpinner {

    private List<Object> list;

    public SearchDialogSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchDialogSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SearchDialogSpinner(Context context) {
        super(context);
    }

    @Override
    public boolean performClick() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext())
                .setNegativeButton("Fechar", (dialog1, which) -> dialog1.dismiss());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_search_spinner_dialog, null);

        ListView listView = view.findViewById(R.id.listView);
        SearchView searchView = view.findViewById(R.id.searchView);
        TextView txtNaoEncontrado = view.findViewById(R.id.txtNaoEncontrado);
        ArrayAdapter<?> adapter = (ArrayAdapter<?>) getAdapter();

        if (list == null) list = new ArrayList<>();
        list.clear();
        for (int i = 0; i < adapter.getCount(); i++) {
            list.add(adapter.getItem(i));
        }

        ArrayAdapter<?> adapterListView = new ArrayAdapter<>(getContext(), R.layout.layout_spinner, list);
        listView.setAdapter(adapterListView);
        listView.setSelection(this.getSelectedItemPosition());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterListView.getFilter().filter(newText, count -> txtNaoEncontrado.setVisibility(count > 0 ? GONE : VISIBLE));

                return true;
            }
        });

        AlertDialog dialog = dialogBuilder.setView(view).show();

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            adapterListView.getFilter().filter(null);
            Object item = parent.getItemAtPosition(position);
            this.setSelection(list.indexOf(item));
            dialog.dismiss();
        });

        return true;
    }

}
