package com.a4sc11production.krlassist.util;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toolbar;

public class ChangeActionBarAndStatusBarColor{
    private Context ctx;

    public ChangeActionBarAndStatusBarColor(Context ctx){
        this.ctx = ctx;
    }

    public void changeStatusActionBarColorFromFragment(Window window, ActionBar abar, int ColorNormal, int ColorDark){

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(ctx.getResources().getColor(ColorDark));


        abar.setBackgroundDrawable(new ColorDrawable(ctx.getResources().getColor(ColorNormal)));
    }


}
