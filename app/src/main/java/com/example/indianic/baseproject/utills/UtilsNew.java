//package com.example.indianic.baseproject.utills;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.location.LocationManager;
//import android.net.ConnectivityManager;
//import android.net.Network;
//import android.net.NetworkInfo;
//import android.provider.Settings;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
//import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
//import android.view.View;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.ImageView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.target.BitmapImageViewTarget;
//import com.socialeaseltd.socialease.R;
//
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//
//public class UtilsNew {
//
////    private static final String TAG = Utils.class.getSimpleName();
//
//    /**
//     * Method is used for checking network availability.
//     *
//     * @param context
//     * @return isNetAvailable: boolean true for Internet availability, false otherwise
//     */
//
//    public static boolean isNetworkAvailable(Context context) {
//
//        boolean isNetAvailable = false;
//        if (context != null) {
//            final ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//            if (mConnectivityManager != null) {
//
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                    final Network[] allNetworks = mConnectivityManager.getAllNetworks();
//
//                    for (Network network : allNetworks) {
//                        final NetworkInfo networkInfo = mConnectivityManager.getNetworkInfo(network);
//                        if (networkInfo != null && networkInfo.isConnected()) {
//                            isNetAvailable = true;
//                            break;
//                        }
//                    }
//
//                } else {
//                    boolean wifiNetworkConnected = false;
//                    boolean mobileNetworkConnected = false;
//
//                    final NetworkInfo mobileInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//                    final NetworkInfo wifiInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//
//                    if (mobileInfo != null) {
//                        mobileNetworkConnected = mobileInfo.isConnected();
//                    }
//                    if (wifiInfo != null) {
//                        wifiNetworkConnected = wifiInfo.isConnected();
//                    }
//                    isNetAvailable = (mobileNetworkConnected || wifiNetworkConnected);
//                }
//            }
//        }
//        return isNetAvailable;
//    }
//
//
//    /**
//     * Called to check permission(In Android M and above versions only)
//     *
//     * @param permission, which we need to pass
//     * @return true, if permission is granted else false
//     */
//    public static boolean checkForPermission(final Context context, final String permission) {
//        int result = ContextCompat.checkSelfPermission(context, permission);
//        //If permission is granted then it returns 0 as result
//        if (result == PackageManager.PERMISSION_GRANTED) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public static String changeDateTimeFormat(String strDate, String srcFormat, String destFormat) {
//
//        Date date = new Date();
//        String strFormatedDate = "";
//
//        try {
//
//            DateFormat formatter = new SimpleDateFormat(srcFormat);
//            date = formatter.parse(strDate);
//            strFormatedDate = new SimpleDateFormat(destFormat).format(date);
//        } catch (ParseException e) {
//
//            e.printStackTrace();
//        }
//
//        return strFormatedDate;
//    }
//
//
//    // This method is for show image in circleview using Glide
//    public static void setImageView(final Context context, String imgUrl, final ImageView imageView) {
//        Glide.with(context).load(imgUrl).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
//            @Override
//            protected void setResource(Bitmap resource) {
//                imageView.setImageBitmap(resource);
//            }
//        });
//    }
//
//    /**
//     * @param mActivity
//     * @param message
//     * @param isCancelable
//     * @return
//     * @purpose show progress dialog
//     */
//    public static ProgressDialog showProgressDialog(final Activity mActivity, final String message, boolean isCancelable) {
//        final ProgressDialog mDialog = new ProgressDialog(mActivity);
//        mDialog.show();
//        mDialog.setCancelable(isCancelable);
//        mDialog.setCanceledOnTouchOutside(false);
//        mDialog.setMessage(message);
//        return mDialog;
//    }
//
//    public static final void dismissProgressDialog(ProgressDialog progressDialog) {
//        if (progressDialog != null && progressDialog.isShowing()) {
//            progressDialog.dismiss();
//        }
//    }
//
//    /**
//     * Alert dialog to show common messages.
//     *
//     * @param title
//     * @param message
//     * @param context
//     */
//    public static void displayDialog(final Context context, final String title, final String message) {
//
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//        alertDialog.setTitle(title);
//        alertDialog.setCancelable(false);
//
//        alertDialog.setMessage(message);
//        alertDialog.setPositiveButton(context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        AlertDialog alert = alertDialog.create();
//        alert.show();
//
//    }
//
//
//    /**
//     * show image in circle view using glide
//     *
//     * @param context
//     * @param imgUrl
//     * @param imageView
//     */
//    public static void getCircleImageView(final Context context, String imgUrl, final ImageView imageView) {
//        Glide.with(context).load(imgUrl).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
//            @Override
//            protected void setResource(Bitmap resource) {
//                RoundedBitmapDrawable circularBitmapDrawable =
//                        RoundedBitmapDrawableFactory.create((context).getResources(), resource);
//                circularBitmapDrawable.setCircular(true);
//                imageView.setImageDrawable(circularBitmapDrawable);
//            }
//        });
//    }
//
//
//    /**
//     * Check if email is in valid format
//     *
//     * @param inputEmail
//     * @return true, if email is in proper format
//     */
//    public final static boolean isValidEmail(CharSequence inputEmail) {
//        if (inputEmail == null) {
//            return false;
//        } else {
//            return android.util.Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches();
//        }
//    }
//
//
//    /**
//     * Hide the soft keyboard from screen for edit text only
//     *
//     * @param mContext
//     * @param view
//     */
//    public static void hideSoftKeyBoard(Context mContext, View view) {
//
//        try {
//
//            final InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//            if (imm != null) {
//                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//    }
//
//
//    public static String getDeviceId(Context context) {
//        String android_id = Settings.Secure.getString(context.getContentResolver(),
//                Settings.Secure.ANDROID_ID);
//        return android_id;
//    }
//
//    /**
//     * Added by umesh nep
//     * @param context
//     */
//    public static void isGPSEnable(final Context context) {
//        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//        boolean gps_enabled = false;
//        boolean network_enabled = false;
//
//        try {
//            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        } catch (Exception ex) {
//        }
//
//        try {
//            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//        } catch (Exception ex) {
//        }
//
//        if (!gps_enabled && !network_enabled) {
//            // notify user
//            final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//            dialog.setMessage(context.getResources().getString(R.string.gps_network_not_enabled));
//            dialog.setPositiveButton(context.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
//                    // TODO Auto-generated method stub
//                    paramDialogInterface.dismiss();
//                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                    context.startActivity(myIntent);
//                }
//            });
//            dialog.setNegativeButton(context.getString(R.string.Cancel), new DialogInterface.OnClickListener() {
//
//                @Override
//                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
//                    // TODO Auto-generated method stub
//                    paramDialogInterface.dismiss();
//                }
//            });
//            dialog.show();
//        }
//
//    }
//
//
//}
