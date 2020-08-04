package com.example.acaziatestapp.ui.main;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.acaziatestapp.model.AppRepository;
import com.example.acaziatestapp.model.User;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.acaziatestapp.R;
public class LoginViewModel extends AndroidViewModel {

    private AppRepository repository;
    private LiveData<List<User>> userList;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        userList = repository.getUserList();
    }

    public void login(String email, String pw, Context context, boolean saveUser, FragmentActivity fragmentActivity){
        if(verifyPw(pw, context)){
            if (saveUser)
                insertUser(email, pw, context, fragmentActivity);
            fragmentActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new LogoutFragment()).commit();
        }
    }

    public void insertUser(String email, String password, final Context context, final FragmentActivity fragmentActivity){
        repository.insertUser(new User(email, password), new AppRepository.InsertUserAsyncTask.AsynResponse() {
            @Override
            public void processFinish() {
                Toast.makeText(context, "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new LogoutFragment()).commit();
            }
        });
    }

    public void setAutoCompleteEmail(final AutoCompleteTextView etxEmail, final EditText etxPw, final LifecycleOwner owner, final Context context){
        userList.observe(owner, new Observer<List<User>>() {
            @Override
            public void onChanged(final List<User> users) {
                if (users!=null){
                    String[] emailList = new String[users.size()];
                    for (int i = 0; i < users.size(); i++) {
                        emailList[i]=users.get(i).getEmail();
                    }
                    ArrayAdapter<String> emailAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, emailList);
                    etxEmail.setAdapter(emailAdapter);
                    etxEmail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            etxPw.setText(users.get(position).getPassword());
                            Log.d("Autocompletetextview", "onItemSelected: clicked on "+position);
                        }
                    });
                    etxEmail.setThreshold(1);
                }
            }
        });
    }

    private boolean verifyPw(String password, Context context){
        Pattern patternSC = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher specialChar = patternSC.matcher(password);
        boolean hasSpecialChar = specialChar.find();
        Pattern patternNB = Pattern.compile("[0-9 ]");
        Matcher number = patternNB.matcher(password);
        boolean hasNumber = number.find();
        if(password.length()<6){
            Toast.makeText(context, "Mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!hasSpecialChar){
            Toast.makeText(context, "Mật khẩu không có ký tự đặc biệt", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!hasNumber){
            Toast.makeText(context, "Mật khẩu không có số", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String scOnlyStr = password;
            scOnlyStr = scOnlyStr.replaceAll("[a-zA-Z0-9]", "");
            if (scOnlyStr.length() > 1) {
                Toast.makeText(context, "Mật khẩu có nhiều hơn 1 ký tự đặc biệt", Toast.LENGTH_SHORT).show();
                return false;
            } else
                return true;
        }
    }




}
