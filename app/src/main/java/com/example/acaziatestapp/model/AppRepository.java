package com.example.acaziatestapp.model;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class AppRepository {
    private UserDao userDao;
    private LiveData<List<User>> userList;

    public AppRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        this.userDao = database.userDao();
        userList = userDao.getAll();
    }

    public void insertUser(User user, InsertUserAsyncTask.AsynResponse delegate){
        new InsertUserAsyncTask(userDao, delegate).execute(user);
    }
    public static class InsertUserAsyncTask extends AsyncTask<User, Void, Void>{

        public interface AsynResponse {
            void processFinish();
        }

        UserDao userDao;
        AsynResponse delegate;


        public InsertUserAsyncTask(UserDao userDao, AsynResponse delegate) {
            this.userDao = userDao;
            this.delegate = delegate;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            this.delegate.processFinish();
        }
    }

    public LiveData<List<User>> getUserList() {
        return userList;
    }
}
