package com.example.indianic.baseproject.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.indianic.baseproject.R;
import com.example.indianic.baseproject.activity.MainActivity;

import static com.example.indianic.baseproject.utills.Utills.showSnackbarNonSticky;

/**
 * MyPdfFragment class created on 01/05/17.
 */

public class FeedBackFragment extends BaseFragment {


    private EditText etMsgArea;
    private Button btnSubmit;
    private View view;
    private RelativeLayout svParent;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feed_back, container, false);
        return view;


    }

    @Override
    public void initView(View view) {
        ((MainActivity) getActivity()).setTitle("FeedBack");
//        ((MainActivity) getActivity()).setTitleColor(ContextCompat.getColor(context, R.color.colorWhite));
        etMsgArea = (EditText) view.findViewById(R.id.fragment_feed_back_et_info_area);
        svParent = (RelativeLayout) view.findViewById(R.id.fragment_feed_back_rl_container);
        btnSubmit = (Button) view.findViewById(R.id.fragment_feed_back_btn_submit);
        btnSubmit.setOnClickListener(this);


    }

    @Override
    public void trackScreen() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.fragment_feed_back_btn_submit:
                if (validation()) {

//      login(etEmail.getText().toString(), etPassWord.getText().toString());
                    Toast.makeText(getActivity(), "This functionality not implemented yet", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    /**
     * Validate user input for further process
     *
     * @return true if fields are validate or false
     */
    private boolean validation() {
        if (TextUtils.isEmpty(etMsgArea.getText().toString())) {
            showSnackbarNonSticky(svParent, getString(R.string.err_msg_enter_msg), true, getActivity());
            etMsgArea.requestFocus();
            return false;
        } else {
            return true;
        }
    }

}
