package me.inibukanadit.made.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.inibukanadit.made.DetailActivity;
import me.inibukanadit.made.R;
import me.inibukanadit.made.db.helper.DictEnglishHelper;
import me.inibukanadit.made.db.helper.DictIndonesiaHelper;
import me.inibukanadit.made.db.model.Dictionary;

public class SearchesFragment extends Fragment implements SearchesView {

    @BindView(R.id.sv_search_input)
    SearchView svInput;

    @BindView(R.id.lv_search_words)
    ListView lvWordList;

    @BindView(R.id.tv_search_placeholder)
    TextView tvPlaceholder;

    private Language mLanguage = Language.ENGLISH;
    private SearchAsyncTask mSearchTask;
    private SearchesPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searches, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getActivity() != null) {
            if (mLanguage == Language.ENGLISH) {
                mPresenter = new SearchesPresenter(this, new DictEnglishHelper(getActivity()));
                mSearchTask = new SearchAsyncTask(this, mPresenter);

                svInput.setQueryHint(getString(R.string.search_hint_en));
                tvPlaceholder.setText(R.string.search_placeholder_en);
            } else if (mLanguage == Language.INDONESIA) {
                mPresenter = new SearchesPresenter(this, new DictIndonesiaHelper(getActivity()));

                svInput.setQueryHint(getString(R.string.search_hint_id));
                tvPlaceholder.setText(R.string.search_placeholder_id);
            }

            svInput.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    if (mSearchTask != null) {
                        mSearchTask.cancel(true);
                        mSearchTask = null;
                    }
                    mSearchTask = new SearchAsyncTask(SearchesFragment.this, mPresenter);
                    mSearchTask.execute(s);
                    return true;
                }
            });
        }
    }

    @Override
    public void showWords(final List<Dictionary> words) {
        if (getActivity() != null) {
            lvWordList.setVisibility(View.VISIBLE);
            tvPlaceholder.setVisibility(View.INVISIBLE);

            List<String> wordStrings = new ArrayList<>();
            for (Dictionary dict : words) {
                wordStrings.add(dict.getWord());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, wordStrings);
            lvWordList.setAdapter(adapter);
            lvWordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
                    detailIntent.putExtra("DICT", words.get(position));

                    startActivity(detailIntent);
                }
            });
        }
    }

    @Override
    public void showPlaceholder() {
        lvWordList.setVisibility(View.INVISIBLE);
        tvPlaceholder.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String message) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }

    public void setLanguage(Language language) {
        mLanguage = language;
    }

    public enum Language {
        ENGLISH, INDONESIA
    }

    static class SearchAsyncTask extends AsyncTask<String, Void, List<Dictionary>> {

        private SearchesView mView;
        private SearchesPresenter mPresenter;

        SearchAsyncTask(SearchesView view, SearchesPresenter presenter) {
            mView = view;
            mPresenter = presenter;
        }

        @Override
        protected List<Dictionary> doInBackground(String... queries) {
            return mPresenter.searchWords(queries[0]);
        }

        @Override
        protected void onPostExecute(List<Dictionary> dictionaries) {
            super.onPostExecute(dictionaries);

            if (!dictionaries.isEmpty()) {
                mView.showWords(dictionaries);
            } else {
                mView.showPlaceholder();
            }
        }
    }
}
