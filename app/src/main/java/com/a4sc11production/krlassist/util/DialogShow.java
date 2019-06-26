package com.a4sc11production.krlassist.util;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.a4sc11production.krlassist.R;


public class DialogShow {
    public void showDialogBox(Activity activity, String line, String long_desc, String severity){
        final Dialog dialog = new Dialog(activity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_line_status_dialog);

        TextView line_name = (TextView) dialog.findViewById(R.id.dialog_line_name);
        TextView tv_desc = (TextView) dialog.findViewById(R.id.dialog_long_desc);
        ImageView line_icon = (ImageView) dialog.findViewById(R.id.dialog_line_icon);
        CardView cv = (CardView) dialog.findViewById(R.id.dialog_parent);

        line_name.setText(line);
        tv_desc.setText(long_desc);

        switch (severity) {
            case "NORMAL":
                cv.setCardBackgroundColor(activity.getApplicationContext().getResources().getColor(R.color.colorNormal));
                break;
            case "MEDIUM":
                cv.setCardBackgroundColor(activity.getApplicationContext().getResources().getColor(R.color.colorWarning));
                break;
            case "SEVERE":
                cv.setCardBackgroundColor(activity.getApplicationContext().getResources().getColor(R.color.colorDanger));
                break;
        }

        switch (line) {
            case "Central Line":
                line_icon.setImageResource(R.drawable.ic_red_line_18dp);
                break;
            case "Loop Line":
                line_icon.setImageResource(R.drawable.ic_loop_line_18dp);
                break;
            case "Rangkasbitung Line":
                line_icon.setImageResource(R.drawable.ic_rangkasbitung_line_18dp);
                break;
            case "Bekasi Line":
                line_icon.setImageResource(R.drawable.ic_bekasi_line_18dp);
                break;
            case "Tangerang Line":
                line_icon.setImageResource(R.drawable.ic_tangerang_line_18dp);
                break;
            case "Tanjung Priok Line":
                line_icon.setImageResource(R.drawable.ic_tanjung_priok_18dp);
                break;
        }

        FrameLayout dialogButton = (FrameLayout) dialog.findViewById(R.id.frmNo);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }
}
