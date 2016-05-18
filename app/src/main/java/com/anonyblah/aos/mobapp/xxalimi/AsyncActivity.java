package com.anonyblah.aos.mobapp.xxalimi;

/**
 * Created by Kloc on 2016-05-06.
 */
public interface AsyncActivity {
    void showLoadingProgressDialog();
    void showProgressDialog(CharSequence message);
    void dismissProgressDialog();
}
