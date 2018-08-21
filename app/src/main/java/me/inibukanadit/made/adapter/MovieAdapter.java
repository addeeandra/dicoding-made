package me.inibukanadit.made.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.inibukanadit.made.R;
import me.inibukanadit.made.data.MovieDbApi;
import me.inibukanadit.made.data.model.Movie;

public class MovieAdapter extends BaseAdapter {

    private List<Movie> mMovies;

    public MovieAdapter(List<Movie> movies) {
        mMovies = movies;
    }

    @Override
    public int getCount() {
        return mMovies.size();
    }

    @Override
    public Object getItem(int i) {
        return mMovies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mMovies.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.list_movie, viewGroup, false);
        }

        Movie movie = mMovies.get(i);
        ImageView ivPoster = view.findViewById(R.id.iv_movie_preview);
        TextView tvTitle = view.findViewById(R.id.tv_movie_title);
        TextView tvOverview = view.findViewById(R.id.tv_movie_overview);
        TextView tvDate = view.findViewById(R.id.tv_movie_date);

        Picasso.get().load(MovieDbApi.getPosterUrl(movie.getPoster())).into(ivPoster);
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        tvDate.setText(movie.getReleaseDate());

        return view;
    }

}
