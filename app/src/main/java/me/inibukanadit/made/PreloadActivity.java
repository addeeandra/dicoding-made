package me.inibukanadit.made;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import me.inibukanadit.made.db.helper.DictEnglishHelper;
import me.inibukanadit.made.db.helper.DictIndonesiaHelper;
import me.inibukanadit.made.db.helper.DictionaryHelper;
import me.inibukanadit.made.db.model.Dictionary;

public class PreloadActivity extends AppCompatActivity {

    SharedPreferences mSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preload);

        mSharedPreference = getSharedPreferences("preload", MODE_PRIVATE);

        if (isLoaded()) {
            openMainPage();
            return;
        }

        try {
            LangLoader indonesiaLoader = new LangLoader(
                    new DictIndonesiaHelper(this),
                    getAssets().open("indonesia_english"));

            LangLoader englishLoader = new LangLoader(
                    new DictEnglishHelper(this),
                    getAssets().open("english_indonesia"));

            new LoadLangAsyncTask(new LoadLangAsyncTask.OnCompleteListener() {
                @Override
                public void onComplete() {
                    setLoaded();
                    openMainPage();
                }
            }).execute(indonesiaLoader, englishLoader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openMainPage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean isLoaded() {
        return mSharedPreference.getBoolean("loaded", false);
    }

    private void setLoaded() {
        mSharedPreference
                .edit()
                .putBoolean("loaded", true)
                .apply();
    }

    static class LoadLangAsyncTask extends AsyncTask<LangLoader, Void, Void> {

        private OnCompleteListener mOnCompleteListener;

        LoadLangAsyncTask(OnCompleteListener mOnCompleteListener) {
            this.mOnCompleteListener = mOnCompleteListener;
        }

        @Override
        protected Void doInBackground(LangLoader... langLoaders) {
            for (LangLoader loader : langLoaders) {
                try {
                    InputStream inputStream = loader.getInputStream();
                    DictionaryHelper dictionaryHelper = loader.getDatabaseHelper().open();

                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    String receiveString = "";
                    Dictionary dict = new Dictionary();

                    dictionaryHelper.beginTransaction();
                    while ((receiveString = bufferedReader.readLine()) != null) {
                        String[] splits = receiveString.split("\\t");
                        dict.setWord(splits[0]);
                        dict.setTranslate(splits[1]);

                        dictionaryHelper.insertTransaction(dict);
                    }
                    dictionaryHelper.setTransactionSuccessful();
                    dictionaryHelper.endTransaction();

                    dictionaryHelper.close();
                    inputStream.close();
                } catch (FileNotFoundException e) {
                    Log.e("FILE HELPER", "File not found: " + e.toString());
                } catch (IOException e) {
                    Log.e("FILE HELPER", "Can not read file: " + e.toString());
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (mOnCompleteListener != null) mOnCompleteListener.onComplete();
        }

        interface OnCompleteListener {

            void onComplete();

        }

    }

    static class LangLoader {

        private DictionaryHelper databaseHelper;
        private InputStream inputStream;

        LangLoader(DictionaryHelper helper, InputStream inputStream) {
            this.databaseHelper = helper;
            this.inputStream = inputStream;
        }

        public DictionaryHelper getDatabaseHelper() {
            return databaseHelper;
        }

        public void setDatabaseHelper(DictionaryHelper helper) {
            this.databaseHelper = helper;
        }

        public InputStream getInputStream() {
            return inputStream;
        }

        public void setInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }
    }
}
