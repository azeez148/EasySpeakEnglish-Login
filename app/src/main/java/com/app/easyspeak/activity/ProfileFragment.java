package com.app.easyspeak.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.app.easyspeak.model.User;
import com.app.easyspeak.serviceImpl.UserLoginServiceImpl;
import com.app.easyspeak.serviceImpl.UserProfileServiceImpl;

import javax.inject.Inject;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    User user = null;

    private EditText fName;
    private EditText sName;
    private EditText phoneNum;
    private EditText dob;
    private EditText mPassword;
    private EditText mEmail;
    Context context = null;

    @Inject
    private UserProfileServiceImpl userProfileService;
    public ProfileFragment() {
        super();
        userProfileService = new UserProfileServiceImpl();
    }

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserUpdateTask mAuthTask = null;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View rootView =  inflater.inflate(R.layout.fragment_profile, container, false);
        Bundle bundle = this.getArguments();
        user = (User)bundle.getSerializable("user");
        Log.v("user in profile ", user.toString());

        fName = (EditText) rootView.findViewById(R.id.reg_fname);
        sName = (EditText) rootView.findViewById(R.id.reg_sname);
        mEmail = (EditText) rootView.findViewById(R.id.reg_email);
        mPassword = (EditText) rootView.findViewById(R.id.reg_password);
        phoneNum = (EditText) rootView.findViewById(R.id.reg_mobile);
        dob = (EditText) rootView.findViewById(R.id.reg_dob);

        if(null != user){
            fName.setText(user.getFirstName());
            sName.setText(user.getSecondName());
            mEmail.setText(user.getEmail());
            mEmail.setEnabled(false);
            phoneNum.setText(user.getMobileNumber());
            dob.setText(user.getDateofBirth());
            mPassword.setText(user.getPassword());
        }
        Button mUpdateButton = (Button) rootView.findViewById(R.id.reg_button);
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptUpdate();
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptUpdate() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        fName.setError(null);
        sName.setError(null);
        mEmail.setError(null);
        phoneNum.setError(null);
        dob.setError(null);
        mPassword.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String mobileNum = phoneNum.getText().toString();
        String firstName = fName.getText().toString();
        String secondName = sName.getText().toString();
        String dateOfBirth = dob.getText().toString();
        String userId = user.getId();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(firstName)) {
            mPassword.setError(getString(R.string.error_invalid_fname));
            focusView = mPassword;
            cancel = true;
        } // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(secondName)) {
            sName.setError(getString(R.string.error_invalid_sname));
            focusView = sName;
            cancel = true;
        }

        if (TextUtils.isEmpty(dateOfBirth)) {
            dob.setError(getString(R.string.error_invalid_dob));
            focusView = dob;
            cancel = true;
        } if (TextUtils.isEmpty(mobileNum) && !isValidMobile(mobileNum)) {
            phoneNum.setError(getString(R.string.error_invalid_mobile));
            focusView = phoneNum;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmail.setError(getString(R.string.error_field_required));
            focusView = mEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            User user = new User(userId, email, password, email, firstName,secondName,dateOfBirth,mobileNum);

            mAuthTask = new ProfileFragment.UserUpdateTask(user);
            mAuthTask.execute((Void) null);
        }
    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
    private boolean isValidMobile(String mobileNum) {
        //TODO: Replace this with your own logic
        return mobileNum.length() == 10;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserUpdateTask extends AsyncTask<Void, Void, Boolean> {

User user=null;

        UserUpdateTask(User user) {
            this.user = user;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);

                Log.v("user reutrning ",user.toString());
                user = userProfileService.updateUserProfile(user,context);


            } catch (InterruptedException e) {
                return false;
            }

            Log.v("user for profile",user.toString());

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            //showProgress(false);

            if (success) {
                new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Good job!")
                        .setContentText("You clicked the button!")
                        .show();

                Log.v("onPostExecute ","success");
//                finish();
            } else {
                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Something went wrong!")
                        .show();
                Log.v("onPostExecute ","failed");
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            //showProgress(false);
        }
    }
}
