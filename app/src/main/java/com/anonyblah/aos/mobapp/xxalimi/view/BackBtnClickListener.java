package com.anonyblah.aos.mobapp.xxalimi.view;

import android.view.View;

import com.ramotion.foldingcell.FoldingCell;

/**
 * Created by Kloc on 2016-05-11.
 */
public abstract class BackBtnClickListener implements View.OnClickListener {

    FoldingCell cell;

    public BackBtnClickListener(FoldingCell cell) {
        this.cell = cell;
    }
}
