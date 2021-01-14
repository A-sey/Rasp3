package sey.a.rasp3.ui.defaults;

import android.content.Context;
import android.view.View;

import sey.a.rasp3.model.Default;
import sey.a.rasp3.raw.RawDefault;

public interface DefaultCreate<T extends Default> {
    public View createForm(Context context, T t);

    public View createForm(Context context);

    public boolean positiveClick();
}
