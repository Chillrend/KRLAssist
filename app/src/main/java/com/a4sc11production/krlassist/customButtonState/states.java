package com.a4sc11production.krlassist.customButtonState;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import com.a4sc11production.krlassist.R;

public class states extends android.support.v7.widget.AppCompatButton {
    private static final int[] STATE_IS_SELECTED = {R.attr.state_is_selected};

    private boolean mStateIsSelected = false;

    public void setmStateIsSelected(boolean isSelected){
        mStateIsSelected = isSelected;
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace){
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 2);

        if (mStateIsSelected){
            mergeDrawableStates(drawableState, STATE_IS_SELECTED);
        }

        return drawableState;
    }

    public states (Context ctx, AttributeSet attrs){
        super(ctx, attrs);
    }
}
