package me.inibukanadit.made;

import android.content.Intent;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.inibukanadit.made.adapter.MovieAdapter;
import me.inibukanadit.made.data.MovieDbApi;
import me.inibukanadit.made.data.model.Movie;
import me.inibukanadit.made.utils.Mapper;

public class MainActivity extends AppCompatActivity implements MainView {

    private MovieDbApi mApi = new MovieDbApi();
    private MainPresenter mPresenter = new MainPresenter(this, mApi);

    @BindView(R.id.et_movies_search)
    EditText etSearch;

    @BindView(R.id.list_movies)
    ListView listMovies;

    @BindView(R.id.clp_movies)
    ContentLoadingProgressBar clpMovies;

    @BindView(R.id.ph_movies)
    TextView phMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mPresenter.fetchMovieList("");
    }

    @OnClick(R.id.btn_movies_search)
    public void onClickButtonSearch(View v) {
        String query = etSearch.getText().toString();
        mPresenter.fetchMovieList(query);
    }

    @Override
    public void showMovies(final List<Movie> movieList) {
        listMovies.setVisibility(View.VISIBLE);
        listMovies.setAdapter(new MovieAdapter(movieList));
        listMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle data = Mapper.movieToBundle(movieList.get(i));
                Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);

                detailIntent.putExtras(data);
                startActivity(detailIntent);
            }
        });
    }

    @Override
    public void showLoading() {
        clpMovies.show();
        phMovies.setVisibility(View.GONE);
        listMovies.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        clpMovies.hide();
    }

    @Override
    public void showPlaceholder() {
        phMovies.setVisibility(View.VISIBLE);
        listMovies.setVisibility(View.GONE);
    }

}
