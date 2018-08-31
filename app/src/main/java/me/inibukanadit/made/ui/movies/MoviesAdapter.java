package me.inibukanadit.made.ui.movies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.inibukanadit.made.R;
import me.inibukanadit.made.data.MovieDbApi;
import me.inibukanadit.made.data.model.Movie;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> mMovies;
    private OnMovieClickListener mOnMovieClickListener;

    public MoviesAdapter(List<Movie> movies, OnMovieClickListener onMovieClickListener) {
        mMovies = movies;
        mOnMovieClickListener = onMovieClickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(mMovies.get(position), mOnMovieClickListener);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_movie_preview)
        ImageView ivPoster;

        @BindView(R.id.tv_movie_title)
        TextView tvTitle;

        @BindView(R.id.tv_movie_overview)
        TextView tvOverview;

        @BindView(R.id.tv_movie_date)
        TextView tvDate;

        @BindView(R.id.btn_movie_detail)
        Button btnDetail;

        @BindView(R.id.btn_movie_share)
        Button btnShare;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Movie movie, final OnMovieClickListener onMovieClickListener) {
            Picasso.get().load(MovieDbApi.getPosterUrl(movie.getPoster())).into(ivPoster);
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            tvDate.setText(movie.getReleaseDate());

            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onMovieClickListener.onDetailClick(movie);
                }
            });

            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onMovieClickListener.onShareclick(movie);
                }
            });
        }

    }

    interface OnMovieClickListener {

        void onDetailClick(Movie movie);

        void onShareclick(Movie movie);

    }

}
