package com.example.indianic.baseproject.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.indianic.baseproject.R;
import com.example.indianic.baseproject.activity.MainActivity;

/**
 * PdfFragment class created on 01/05/17.
 */

public class ContactUsFragment extends BaseFragment {

    private TextView tvSpannableTitle;
    private TextView tvSpannableTiming;
    private TextView tvSpannableHelpLine;
    private TextView tvSpannableHelpLineVal;
    private TextView tvSpannableEmail;
    private TextView tvSpannableWebSite;
    private TextView tvContactNo;
    private View view;
    private Context context;
    String htmlString="<u>HEAD CENTER</u>";
    String htmlTimingString="<u>Timing:</u>";
    String htmlHelpLineString="<u>OUR HELPLINE NUMBER</u>";
    String htmlHelpLinValeString="<u>1) 9033082625</u>";
    String htmlEmailString="<u>EMAIL</u>";
    String htmlWebSiteString="<u>WEBSITE</u>";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        return view;


    }

    @Override
    public void initView(View view) {
        ((MainActivity) getActivity()).setTitle("Contact us");
//        ((MainActivity) getActivity()).setTitleColor(ContextCompat.getColor(context, R.color.colorWhite));
        tvSpannableTitle= (TextView) view.findViewById(R.id.fragment_contact_us_tv_spannable_title);
        tvSpannableTiming= (TextView) view.findViewById(R.id.fragment_contact_us_tv_add_line_four);
        tvSpannableHelpLine= (TextView) view.findViewById(R.id.fragment_contact_us_tv_add_line_seven);
        tvSpannableHelpLineVal= (TextView) view.findViewById(R.id.fragment_contact_us_tv_add_line_eight);
        tvSpannableEmail= (TextView) view.findViewById(R.id.fragment_contact_us_tv_add_line_nine);
        tvSpannableWebSite= (TextView) view.findViewById(R.id.fragment_contact_us_tv_add_line_eleven);

        tvSpannableTiming.setText(Html.fromHtml(htmlTimingString));
        tvSpannableTitle.setText(Html.fromHtml(htmlString));
        tvSpannableHelpLine.setText(Html.fromHtml(htmlHelpLineString));
        tvSpannableHelpLineVal.setText(Html.fromHtml(String.valueOf(htmlHelpLinValeString)));
        tvSpannableEmail.setText(Html.fromHtml(String.valueOf(htmlEmailString)));
        tvSpannableWebSite.setText(Html.fromHtml(String.valueOf(htmlWebSiteString)));
    }

    @Override
    public void trackScreen() {

    }


}
