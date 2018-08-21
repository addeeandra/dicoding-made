package me.inibukanadit.made;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.inibukanadit.made.data.MovieDbApi;
import me.inibukanadit.made.data.model.Movie;
import me.inibukanadit.made.utils.Mapper;

public class DetailActivity extends AppCompatActivity {

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

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle data = getIntent().getExtras();
        if (data != null) {
            Movie movie = Mapper.bundleToMovie(data);

            Picasso.get().load(MovieDbApi.getPosterUrl(movie.getPoster())).into(ivMoviePoster);
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            tvDate.setText(movie.getReleaseDate());
            tvVote.setText(String.valueOf(movie.getVoteAverageRate()));
            tvPopularity.setText(String.valueOf(movie.getPopularityRate()));
        }
    }
}
