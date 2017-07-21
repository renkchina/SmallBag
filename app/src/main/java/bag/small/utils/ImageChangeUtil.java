package bag.small.utils;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.Button;

/**
 * Created by Administrator on 2017/4/26.
 */

public class ImageChangeUtil {

    public static void setDrawLeft(Button button, int normalDraw, int selectedDraw) {
        Drawable leftDrawable;
        if (button.isSelected()) {
            button.setSelected(false);
            leftDrawable = ContextCompat.getDrawable(button.getContext(), normalDraw);
        } else {
            button.setSelected(true);
            leftDrawable = ContextCompat.getDrawable(button.getContext(), selectedDraw);
        }
        leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
        button.setCompoundDrawables(leftDrawable, null, null, null);
    }
    public static void setDrawRight(Button button, int normalDraw, int selectedDraw) {
        Drawable rightDrawable;
        if (button.isSelected()) {
            button.setSelected(false);
            rightDrawable = ContextCompat.getDrawable(button.getContext(), normalDraw);
        } else {
            button.setSelected(true);
            rightDrawable = ContextCompat.getDrawable(button.getContext(), selectedDraw);
        }
        rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
        button.setCompoundDrawables(rightDrawable, null, null, null);
    }
}
