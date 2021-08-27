package com.winfox.testapp.ui.customViews;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.winfox.testapp.R;

public class CustomToast {

    public static Toast makeText(Activity activity, CharSequence text) {

        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_view, null);

        TextView tv = (TextView) layout.findViewById(R.id.textView);
        tv.setText(text);

        Toast toast = new Toast(activity);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        return toast;

    }

}
