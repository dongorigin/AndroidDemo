package cn.dong.demo.util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import cn.dong.demo.R;

/**
 * Fragment跳转控制工具
 * 
 * @author dong 2014-1-14
 */
public class FragmentUtil {

    public static void startFragment(FragmentManager fm, Fragment fragment) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    public static void startFragment(FragmentManager fm, Class<? extends Fragment> fragmentClass,
            Bundle args) {
        startFragment(fm, fragmentClass, args, R.id.container);
    }

    public static void startFragment(FragmentManager fm, Class<? extends Fragment> fragmentClass,
            Bundle args, int containerId) {
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment currentFragment = fm.findFragmentById(containerId);
        if (currentFragment != null) { // containerId处存在Fragment
            if (fragmentClass.equals(currentFragment.getClass())) {
                // return; // 欲添加的Fragment与当前Fragment的类相同，则停止跳转，防止重复加载
            } else {
                transaction.hide(currentFragment);// 隐藏当前Fragment
            }
        }
        try {
            Fragment fragment = fragmentClass.newInstance();
            fragment.setArguments(args);
            if (fragment.isAdded()) {
                Log.d("", "isAdded");
            }
            transaction.setCustomAnimations(R.anim.fragment_in, android.R.anim.fade_out);
            transaction.add(containerId, fragment, fragmentClass.getName());
            transaction.addToBackStack(null);
            transaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
            transaction.commitAllowingStateLoss();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void replaceFragment(FragmentManager fm, Class<? extends Fragment> fragmentClass,
            Bundle args, boolean isAddToBackStack) {
        replaceFragment(fm, fragmentClass, args, R.id.container, isAddToBackStack);
    }

    public static void replaceFragment(FragmentManager fm, Class<? extends Fragment> fragmentClass,
            Bundle args, int containerId, boolean isAddToBackStack) {
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment currentFragment = fm.findFragmentById(containerId);
        if (currentFragment != null) {// containerId处存在Fragment
            if (fragmentClass.equals(currentFragment.getClass())) {
                // return; // 替换的Fragment与当前Fragment的类相同，则停止替换，防止重复替换
            }
        }
        try {
            Fragment fragment = fragmentClass.newInstance();
            fragment.setArguments(args);
            transaction.replace(containerId, fragment, fragmentClass.getName());
            if (isAddToBackStack) {
                Log.d("FragmentUtil", "isAddToBackStack");
                transaction.addToBackStack(null);
            }
            transaction.commitAllowingStateLoss();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
