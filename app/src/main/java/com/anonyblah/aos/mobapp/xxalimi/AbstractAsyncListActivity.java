package com.anonyblah.aos.mobapp.xxalimi;

import android.app.ListActivity;
import android.app.ProgressDialog;

/**
 * Created by Kloc on 2016-05-06.
 */
public class AbstractAsyncListActivity extends ListActivity implements AsyncActivity {
    protected static final String TAG = AbstractAsyncListActivity.class.getSimpleName();

    private ProgressDialog progressDialog;
    private boolean destroyed = false;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.destroyed = true;
    }

    @Override
    public void showLoadingProgressDialog() {
        this.showProgressDialog(getString(R.string.wait_msg));
    }

    @Override
    public void showProgressDialog(CharSequence message) {
        if(this.progressDialog == null) {
            this.progressDialog = new ProgressDialog(this);
            this.progressDialog.setIndeterminate(true);
        }
        this.progressDialog.setMessage(message);
        this.progressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        if(this.progressDialog != null && !this.destroyed) {
            this.progressDialog.dismiss();
        }
    }
}
