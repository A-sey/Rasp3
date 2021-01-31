package sey.a.rasp3.ui.defaults;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import sey.a.rasp3.R;
import sey.a.rasp3.model.Default;
import sey.a.rasp3.model.LessonDate;
import sey.a.rasp3.ui.lesson.LessonDetailsFragment;

public class CreateDetails<T extends Default> {

    private Fragment getFragment(T o) {
        Fragment fragment = null;
        if (o instanceof LessonDate) {
            LessonDetailsFragment ldf = new LessonDetailsFragment();
            ldf.setLessonDate((LessonDate) o);
            fragment = ldf;
        }
        return fragment;
    }

    public void show(Context context, FragmentManager fragmentManager, T o) {
        if(fragmentManager == null) {
            fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = getFragment(o);
        if(fragment==null){
            return;
        }
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
