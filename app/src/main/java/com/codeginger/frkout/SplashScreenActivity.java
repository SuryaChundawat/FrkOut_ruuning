package com.codeginger.frkout;

import android.app.Activity;
import android.os.Bundle;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;

// Created by Pratik Mehta

public class SplashScreenActivity extends Activity
{
    private static int SPLASH_TIME_OUT = 3000;

    Common c;
    UserSessionManager usm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        c = new Common();
        usm = new UserSessionManager(getApplicationContext());

        // Open Another Activity After SPLASH_TIME_OUT
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if (c.isConnected(getApplicationContext()))
                {
                    // Called Async Process
                    new PrefetchData().execute();
                }
                else
                {
                    startActivity(new Intent(SplashScreenActivity.this, NoInternetActivity.class));
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }

    private class PrefetchData extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            if (usm.isUserLogin() && usm.isUserProfileInterestSetup())
            {
                Intent i = new Intent(SplashScreenActivity.this, DashboardActivity.class);
                startActivity(i);
                finish();
            }
            else
            {
                if (!usm.isUserLogin())
                {
                    startActivity(new Intent(SplashScreenActivity.this, OTPActivity.class));
                    finish();
                }
                else if (!usm.isUserProfileInterestSetup())
                {
                    startActivity(new Intent(SplashScreenActivity.this, UserProfileActivity.class));
                    finish();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
        }

    }

}
