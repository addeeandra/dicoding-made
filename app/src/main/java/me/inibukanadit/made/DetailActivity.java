package me.inibukanadit.made;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.inibukanadit.made.db.model.Dictionary;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_detail_word)
    TextView tvDetailWord;

    @BindView(R.id.tv_detail_description)
    TextView tvDetailDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent() != null) {
            Dictionary dictionary = getIntent().getParcelableExtra("DICT");
            tvDetailWord.setText(dictionary.getWord());
            tvDetailDescription.setText(dictionary.getTranslate());
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
}
