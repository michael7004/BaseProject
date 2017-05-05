package com.example.indianic.baseproject.utills;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.indianic.baseproject.R;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * Created Class Utills on 22/04/17.
 */

public class Utills {

    public static final String AMOUNT_TAG = "TOTAL_AMOUNT";
    public static final String CREDIT_CARD_TAG = "CREDIT_CARD_TAG";
    public static final int CARD_NUMBER_FIELD_LENGTH = 19;
    public static final int CARD_NUMBER_LENGTH = 16;
    public static final int CVV_NUMBER_LENGTH = 3;
    public static final int EXP_DATE_FIELD_LENGTH = 5;
    public static final int EXP_DATE_LENGTH = 4;
    private static final String TAG = Utills.class.getSimpleName();
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    private static final boolean isToastShow = true;

    // To get unique deviceId
    public static String getUniqueDeviceId(Context context) {
        String deviceId = null;
        // 1 compute IMEI
        final TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        deviceId = TelephonyMgr.getDeviceId(); // Requires // READ_PHONE_STATE
//       device id=356530062340787
        if (deviceId == null) {
            // 2 android ID - unreliable
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        if (deviceId == null) {
            // 3 compute DEVICE ID
            deviceId = "35"
                    + // we make this look like a valid IMEI
                    Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10
                    + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10 + Build.TYPE.length() % 10 + Build.USER.length() % 10; // 13
            // digits
        }
        return deviceId;
    }

    public static void hideSoftKeyboard(Activity activity) {
        final InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            if (activity.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    /**
     * Hide the soft keyboard from screen for edit text only
     *
     * @param mContext
     * @param view
     */
    public static void hideSoftKeyBoard(Context mContext, View view) {
        try {
            final InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if internet connection is available or not
     *
     * @param context
     * @return true, if internet is available
     */
    public static boolean isNetworkAvailable(final Context context) {
        boolean isNetAvailable = false;
        if (context != null) {
            final ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager != null) {
                final NetworkInfo activeNetwork = mConnectivityManager.getActiveNetworkInfo();
                if (activeNetwork != null) isNetAvailable = true;
            }
        }
        return isNetAvailable;
    }

    /**
     * Method to show progress dialog
     *
     * @param mActivity
     * @param message
     * @param isCancelable
     * @return dialog
     */
    public static ProgressDialog showProgressDialog(final Context mActivity, final String message, boolean isCancelable) {
        final ProgressDialog mDialog = new ProgressDialog(mActivity);
        mDialog.show();
        mDialog.setCancelable(isCancelable);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setMessage(message);
        return mDialog;
    }

    /**
     * Method to dismiss progress dialog
     *
     * @param progressDialog
     */
    public static final void dismissProgressDialog(ProgressDialog progressDialog) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * Display dialog with message and a ok button
     *
     * @param context
     * @param msg
     */
    public static void displayDialog(final Context context, final String msg) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(msg);
        alertDialog.setPositiveButton(context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }


    /**
     * Method used for creating new Photo path
     *
     * @param mActivity
     * @return
     */
    public static File createPhotoFile(Activity mActivity) {

        final File file = new File(mActivity.getApplicationContext().getExternalCacheDir() + File.separator + System.currentTimeMillis() + ".jpg");
        if (file.exists()) {
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }


    public static void showNoInternetDialog(final Context context) {
        displayDialog(context, context.getString(R.string.msg_no_internet));
    }

    /**
     * Check if email is in valid format
     *
     * @param inputEmail
     * @return true, if email is in proper format
     */
    public final static boolean isValidEmail(CharSequence inputEmail) {
        if (inputEmail == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches();
        }
    }

    public static void showToast(final Context context, final String message) {
        if (isToastShow) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    public static void showKeybord(final Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    public static void touchListener(final Context context, final View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard((Activity) context);
                return false;
            }
        });
    }

    public final static int pxToDp(final Context context, final int px) {
        final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        final int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public final static int dpToPx(final Context context, final int dp) {
        final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        final int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    /**
     * Share text with different application
     *
     * @param context
     * @param sharingText
     */
    public static void shareContent(final Context context, final String sharingText) {
        final Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, sharingText);
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        sendIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }

    /**
     * Call snackbar which will disapper in 3-5 seconds
     *
     * @param view
     * @param message
     * @return Snackbar
     */
    public static Snackbar showSnackbarNonSticky(final View view, final String message, final boolean isError, final Context context) {
        final Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        if (isError) {
            snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRed));
        } else {
            snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreen));
        }
        final TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(5);
        snackbar.show();
        return snackbar;
    }

    /**
     * Removes last comma from any data
     *
     * @param data
     * @return converted data
     */
    public static String removeLastComma(final String data) {
        if (data.substring(data.length() - 1).contains(",")) {
            return data.substring(0, data.length() - 1);
        } else {
            return data;
        }
    }

    /**
     * Called to check permission(In Android M and above versions only)
     *
     * @param permission, which we need to pass
     * @return true, if permission is granted else false
     */
    public static boolean checkForPermission(final Context context, final String permission) {
        int result = ContextCompat.checkSelfPermission(context, permission);
        //If permission is granted then it returns 0 as result
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Validate password with regular expression
     *
     * @param password
     * @return true valid password and matches it's pattern, false invalid password
     */
    public static boolean isValidPassword(String password) {
        final String PASSWORD_PATTERN =
                "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";
        final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        final Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * Create type for particular font
     *
     * @param context
     * @param fontName
     * @return
     */
    public static Typeface createTypeface(Context context, final String fontName) {
        final Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + fontName);
        return typeface;
    }

    /**
     * Replace the fragment in container
     *
     * @param fragment
     */
    public static void replaceFragment(final Context context, final Fragment fragment, final int container) {
        ((Activity) context).getFragmentManager()
                .beginTransaction()
                .replace(container, fragment, fragment.getClass().getSimpleName())
                .commitAllowingStateLoss();
    }

    /**
     * Hide current fragment and add new fragment to container
     *
     * @param currentFragment
     * @param newFragment
     */
    public static void addFragment(final Context context, final Fragment currentFragment, final Fragment newFragment, final int container) {
        ((Activity) context).getFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .add(container, newFragment, newFragment.getClass().getSimpleName())
                .hide(currentFragment)
                .addToBackStack(currentFragment.getClass().getSimpleName())
                .commitAllowingStateLoss();
    }

    /**
     * Parse dateformat as per requirement
     *
     * @param date
     * @param currentForamt
     * @param parseFormat
     * @return
     */
    public static String parseForamt(final String date, final String currentForamt, final String parseFormat) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat(currentForamt, Locale.ENGLISH);
            final Date dateObj = sdf.parse(date);
            return new SimpleDateFormat(parseFormat, Locale.US).format(dateObj);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get Current date
     *
     * @return
     */
    public static String getCurrentDate() {
        final Calendar calendar = Calendar.getInstance();
        final String day = calendar.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + calendar.get(Calendar.DAY_OF_MONTH) : String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        final String month = calendar.get(Calendar.MONTH) < 10 ? "0" + calendar.get(Calendar.MONTH) : String.valueOf(calendar.get(Calendar.MONTH));
        final int year = calendar.get(Calendar.YEAR);
        return year + "-" + month + "-" + day;
    }

    /**
     * Get Current time
     *
     * @return
     */
    public static String getCurrentTime() {
        final Calendar calendar = Calendar.getInstance();
        final String hour = calendar.get(Calendar.HOUR_OF_DAY) < 10 ? "0" + calendar.get(Calendar.HOUR_OF_DAY) : String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        final String minute = calendar.get(Calendar.MINUTE) < 10 ? "0" + calendar.get(Calendar.MINUTE) : String.valueOf(calendar.get(Calendar.MINUTE));
        final String second = calendar.get(Calendar.SECOND) < 10 ? "0" + calendar.get(Calendar.SECOND) : String.valueOf(calendar.get(Calendar.SECOND));
        return hour + ":" + minute + ":" + second;
    }

    /**
     * Encrypt message with secret key
     * @param message
     * @return
     */
    public static String encrypt(final String message) {
        try {
            final byte[] key = hexStringToByteArray(Constants.SECRET_KEY);
            final byte[] iv = hexStringToByteArray(Constants.IV);

            final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            final SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            final IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            final byte[] encrypted = cipher.doFinal(message.getBytes());

            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception e) {
            e.getMessage();
        }
        return "";
    }

    /**
     * Convert char to bytes and used in encryption process
     * @param c
     * @return
     */
    private static int toByte(char c) {
        if (c >= '0' && c <= '9') return (c - '0');
        if (c >= 'A' && c <= 'F') return (c - 'A' + 10);
        if (c >= 'a' && c <= 'f') return (c - 'a' + 10);
        throw new RuntimeException("Invalid hex char '" + c + "'");
    }

    /**
     * Convert Hex string to byte array and used in encryption process
     * @param hexString
     * @return
     */
    private static byte[] hexStringToByteArray(String hexString) {
        int length = hexString.length();
        byte[] buffer = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            buffer[i / 2] = (byte) ((toByte(hexString.charAt(i)) << 4) | toByte(hexString.charAt(i + 1)));
        }
        return buffer;
    }

}
