package com.example.familymap.UserInterface;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.familymap.R;
import com.example.familymap.model.DataCache;
import com.example.familymap.model.LoginStorage;
import com.example.familymap.net.ServerProxy;
import com.example.familymap.shared.model.Event;
import com.example.familymap.shared.model.Person;
import com.example.familymap.shared.requests.LoginRequest;
import com.example.familymap.shared.requests.PersonRequest;
import com.example.familymap.shared.requests.RegisterRequest;
import com.example.familymap.shared.results.LoginResult;
import com.example.familymap.shared.results.RegisterResult;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;
import static com.example.familymap.R.string.login_toast;

public class LoginFragment extends Fragment {
    private MainActivity main = new MainActivity();
    //private LoginStorage storage = new LoginStorage();
    private EditText titleField;
    private Button loginButton;
    private Button registerButton;
    private CheckBox checkBox;
    private boolean isChecked;
    RadioGroup radioGroup;
    RadioButton FradioButton;
    RadioButton MradioButton;

    EditText host, port, username, password, firstname, lastname, email;
    CharSequence genderTest;
    //String genderTest;
    Character gender;
    // public String username;
    //public String password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        DataCache dataCache = DataCache.getInstance();
        List<Event> testEventList = dataCache.getEventList();
        if(testEventList.size() != 0){
            FragmentManager manager = this.getFragmentManager();
            Fragment fragment2 = manager.findFragmentById(R.id.fragmentContainer);

            fragment2 = new MapsFragment();
            manager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment2)
                    .hide(this)
                    .commit();
        }

        View a = inflater.inflate(R.layout.fragment_main, parent, false);

        titleField = (EditText) a.findViewById(R.id.username);
        titleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                main.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        loginButton = (Button) a.findViewById(R.id.login_button);
        loginButton.setOnClickListener((v) -> {
            loginButtonClicked(a);
        });
        loginButton.setText("Login");
        loginButton.setEnabled(false);

        registerButton = (Button) a.findViewById(R.id.register_button);
        registerButton.setOnClickListener((v) -> {
            registerButtonClicked(a);
        });
        registerButton.setText("Register");
        registerButton.setEnabled(false);

        radioGroup = (RadioGroup) a.findViewById(R.id.radio_group);

        //FradioButton = (RadioButton)a.findViewById(RadioID);

        host = (EditText) a.findViewById(R.id.server_host);
        port = (EditText) a.findViewById(R.id.server_port);
        username = (EditText) a.findViewById(R.id.username);
        password = (EditText) a.findViewById(R.id.password);
        firstname = (EditText) a.findViewById(R.id.first_name);
        lastname = (EditText) a.findViewById(R.id.last_name);
        email = (EditText) a.findViewById(R.id.email);

        host.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                enableSubmitIfReady();
                enableSubmitIfReadyRegister();

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                enableSubmitIfReady();
                enableSubmitIfReadyRegister();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableSubmitIfReady();
                enableSubmitIfReadyRegister();

            }
        });
        port.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                enableSubmitIfReady();
                enableSubmitIfReadyRegister();

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                enableSubmitIfReady();
                enableSubmitIfReadyRegister();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableSubmitIfReady();
                enableSubmitIfReadyRegister();

            }
        });
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                enableSubmitIfReady();
                enableSubmitIfReadyRegister();

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                enableSubmitIfReady();
                enableSubmitIfReadyRegister();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableSubmitIfReady();
                enableSubmitIfReadyRegister();

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                enableSubmitIfReady();
                enableSubmitIfReadyRegister();

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                enableSubmitIfReady();
                enableSubmitIfReadyRegister();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableSubmitIfReady();
                enableSubmitIfReadyRegister();

            }
        });

        lastname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                enableSubmitIfReadyRegister();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                enableSubmitIfReadyRegister();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableSubmitIfReadyRegister();
            }
        });
        firstname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                enableSubmitIfReadyRegister();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                enableSubmitIfReadyRegister();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableSubmitIfReadyRegister();

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                enableSubmitIfReadyRegister();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                enableSubmitIfReadyRegister();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableSubmitIfReadyRegister();

            }
        });

        return a;
    }

    public void enableSubmitIfReady() {
        boolean isReady = !username.getText().toString().isEmpty() &&
        !host.getText().toString().isEmpty() &&
        !port.getText().toString().isEmpty() &&
        !password.getText().toString().isEmpty();

        loginButton.setEnabled(isReady);
    }

    public void enableSubmitIfReadyRegister() {
        boolean isReady = !username.getText().toString().isEmpty() &&
                !host.getText().toString().isEmpty() &&
                !port.getText().toString().isEmpty() &&
                !password.getText().toString().isEmpty() &&
                !firstname.getText().toString().isEmpty() &&
                !lastname.getText().toString().isEmpty() &&
                !email.getText().toString().isEmpty();

                registerButton.setEnabled(isReady);
    }

    public void loginButtonClicked(View a) {
        int RadioID = radioGroup.getCheckedRadioButtonId();
        MradioButton = (RadioButton) a.findViewById(RadioID);
        genderTest = MradioButton.getText().toString();
        gender.toLowerCase(genderTest.charAt(0));

        System.out.println("GENDER TEST" + '\n');
        System.out.print(gender);
        System.out.println("GENDER TEST" + '\n');

        LoginAsync async = new LoginAsync();
        boolean test = false;
        test = async.doInBackground(host.getText().toString(), port.getText().toString(),
                username.getText().toString(), password.getText().toString());

        if (test == true) {

            FragmentManager manager = this.getFragmentManager();
            Fragment fragment2 = manager.findFragmentById(R.id.fragmentContainer);

            fragment2 = new MapsFragment();
            manager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment2)
                    .hide(this)
                    .commit();

            DataCache cache = DataCache.getInstance();
            Person person = cache._getUser();
            Toast toast = Toast.makeText(getContext(), "First Name: " + person.getFirstName() + ", Last Name: " + person.getLastName(),
                    Toast.LENGTH_SHORT);
            toast.show();

        } else {

            Toast toast = Toast.makeText(getContext(), "Sign-in Attempt Failed",
                    Toast.LENGTH_SHORT);

            toast.show();
        }
    }

    public void registerButtonClicked(View a) {
        int RadioID = radioGroup.getCheckedRadioButtonId();
        MradioButton = (RadioButton) a.findViewById(RadioID);
        genderTest = MradioButton.getText().toString();
        gender = genderTest.charAt(0);
        gender = Character.toLowerCase(gender);

        System.out.println("GENDER TEST" + '\n');
        System.out.print(gender);
        System.out.println("GENDER TEST" + '\n');

        RegisterAsync async = new RegisterAsync();
        boolean test = false;
        test = async.doInBackground(host.getText().toString(), port.getText().toString(),
                username.getText().toString(), password.getText().toString(), firstname.getText().toString(),
                lastname.getText().toString(), email.getText().toString(), gender.toString());
        if (test == true) {
            System.out.print("SUCCESS!! " + '\n');

            FragmentManager manager = this.getFragmentManager();
            Fragment fragment2 = manager.findFragmentById(R.id.fragmentContainer);

                fragment2 = new MapsFragment();
                manager.beginTransaction()
                        .add(R.id.fragmentContainer, fragment2)
                        .hide(this)
                        .commit();


            DataCache cache = DataCache.getInstance();
            Person person = cache._getUser();
            Toast toast = Toast.makeText(getContext(), "First Name: " + person.getFirstName() + ", Last Name: " + person.getLastName(),
                    Toast.LENGTH_SHORT);
            toast.show();

        } else {
            MainActivity main = new MainActivity();
            Toast toast = Toast.makeText(getContext(), "Registration Attempt Failed",
                    Toast.LENGTH_SHORT);

            toast.show();
        }
    }

    public class RegisterAsync extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            ServerProxy proxy = new ServerProxy();

            for (String s : strings) {
                if(s == null){
                    return false;
                }
                if (s.isEmpty()) {
                    return false;
                }
            }

            RegisterRequest request = new RegisterRequest();
            request.userName = strings[2];
            request.password = strings[3];
            request.firstName = strings[4];
            request.lastName = strings[5];
            request.email = strings[6];
            request.gender = strings[7];

            RegisterResult result = proxy.register(request);

            System.out.print(result);

            if (result.personID == null) {
                return false;
            } else {
                FamilyListAsync familyinput = new FamilyListAsync();
                familyinput.doInBackground(result.authToken);

                EventListAsync eventAsync = new EventListAsync();
                eventAsync.doInBackground(result.authToken);

                PersonIDAsync sync = new PersonIDAsync();
                boolean test = sync.doInBackground(result.personID, result.authToken);
                if (test = true) {
                    return true;
                } else {
                    return false;
                }
                //return true;
            }
        }
    }

    public class FamilyListAsync extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            ServerProxy proxy = new ServerProxy();

            for (String s : strings) {
                if(s == null){
                    return false;
                }
                if (s.isEmpty()) {
                    return false;
                }
            }

            try {
                Person[] persons = proxy.getFamily(strings[0]);
                DataCache data = DataCache.getInstance();
                data._inputPeople(persons);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
            }
        }

    public class EventListAsync extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            ServerProxy proxy = new ServerProxy();

            for (String s : strings) {
                if(s == null){
                    return false;
                }
                if (s.isEmpty()) {
                    return false;
                }
            }

            try {
                Event[] events = proxy.getEvents(strings[0]);
                DataCache data = DataCache.getInstance();
                data._inputEvents(events);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }
    }

    public class LoginAsync extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            ServerProxy proxy = new ServerProxy();

            for (String s : strings) {
                if(s == null){
                    return false;
                }
                if (s.isEmpty()) {
                    return false;
                }
            }
            LoginRequest request = new LoginRequest();
            request.userName = strings[2];
            request.password = strings[3];

            LoginResult result = proxy.login(request);

            System.out.print(result);

            if (result.personID == null) {
                return false;
            } else {
                FamilyListAsync familyinput = new FamilyListAsync();
                familyinput.doInBackground(result.authToken);

                EventListAsync eventAsync = new EventListAsync();
                eventAsync.doInBackground(result.authToken);

                PersonIDAsync sync = new PersonIDAsync();
                boolean test = sync.doInBackground(result.personID, result.authToken);
                if (test = true) {
                    return true;
                } else {
                    return false;
                }
                //return true;
            }
        }
    }

    public class PersonIDAsync extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            ServerProxy proxy2 = new ServerProxy();

            if (strings[0] == null) {
                return false;
            }
            if (strings[1] == null) {
                return false;
            }

            Person person = proxy2.getPersonById(strings[0], strings[1]);

            if (person == null) {
                return false;
            } else {
                DataCache data = DataCache.getInstance();
                data.setUser(person);
                System.out.print("PERSON");
                System.out.print(person.getFirstName());
                System.out.print("PERSON");
                return true;
            }
        }
    }

    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

}
