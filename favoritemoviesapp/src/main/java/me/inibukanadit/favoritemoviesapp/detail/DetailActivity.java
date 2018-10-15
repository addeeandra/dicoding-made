package me.inibukanadit.favoritemoviesapp.detail;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import me.inibukanadit.favoritemoviesapp.R;
import me.inibukanadit.sharedmodule.remote.MovieDbApi;
import me.inibukanadit.sharedmodule.remote.model.Movie;
import me.inibukanadit.sharedmodule.ui.BaseActivity;

public class DetailActivity extends BaseActivity implements DetailView {

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MovieDbApi mMovieDbApi = new MovieDbApi();
    private DetailPresenter mPresenter = new DetailPresenter(mMovieDbApi, mCompositeDisposable);

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.iv_movie_preview)
    ImageView ivMoviePoster;

    @BindView(R.id.tv_movie_title)
    TextView tvTitle;

    @BindView(R.id.tv_movie_date)
    TextView tvDate;

    @BindView(R.id.tv_movie_overview)
    TextView tvOverview;

    @BindView(R.id.tv_movie_popularity_value)
    TextView tvPopularity;

    @BindView(R.id.tv_movie_vote_value)
    TextView tvVote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mPresenter.onAttach(this);

        Bundle data = getIntent().getExtras();
        if (data != null) {
            Movie movie = data.getParcelable("movie");
            mPresenter.onMoviePassed(movie);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }

    @Override
    public void showMovie(Movie movie) {
        Picasso.get().load(MovieDbApi.getPosterUrl(movie.getPoster())).into(ivMoviePoster);
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        tvDate.setText(movie.getReleaseDate());
        tvVote.setText(String.valueOf(movie.getVoteAverageRate()));
        tvPopularity.setText(String.valueOf(movie.getPopularityRate()));
    }
}
