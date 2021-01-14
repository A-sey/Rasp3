package sey.a.rasp3.ui.defaults;

import android.content.Context;
import android.view.View;

import sey.a.rasp3.raw.RawDefault;

public interface DefaultCreate<Raw extends RawDefault> {
    public View createForm(Context context, Raw raw);

    public View createForm(Context context);

    public boolean positiveClick();
}
