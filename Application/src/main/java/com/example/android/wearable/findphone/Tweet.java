package com.example.android.wearable.findphone;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import java.util.Random;

import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Created by hashimotomika on 7/7/15.
 */
public class Tweet extends Activity{
    Random rand = new Random();
    int n = rand.nextInt(1000);
    private Twitter mTwitter;
    private String mInputText = "ﾌﾘﾌﾘ~(ｏ^-^)[" + Integer.toString(n) + "]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        if have no accessToken, go TwitterOAuthActivity
         */
        if(!TwitterUtils.hasAccessToken((this))){
            Intent intent = new Intent(this, TwitterOAuthActivity.class);
            startActivity(intent);
            finish();
        } else {
            mTwitter = TwitterUtils.getTwitterInstance(this);
            tweet();
        }
    }

    private void tweet(){
        AsyncTask<String, Void, Boolean> task = new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                try {
                    mTwitter.updateStatus(params[0]);
                    return true;
                } catch (TwitterException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    showToast("ツイートしたお");
                    finish();
                } else {
                    showToast("ツイートできなかったのだ");
                }
            }
        };
        task.execute(mInputText);
    }


    private void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}
