package com.rdi.tictactoe;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private TicTacToeField mTicTacToeField;
    private ValueAnimator alphaAnimator;
    private boolean mIsNew;
    public Integer[] mThumbIds = {R.drawable.background, R.drawable.background, R.drawable.background,
            R.drawable.background, R.drawable.background, R.drawable.background,
            R.drawable.background, R.drawable.background, R.drawable.background};

    public static Integer[] mViewsID = {0, 1, 2, 3, 4, 5, 6, 7, 8};

    public ImageAdapter(Context c, TicTacToeField ticTacToeField, boolean isNew) {
        mContext = c;
        mTicTacToeField = ticTacToeField;
        mIsNew = isNew;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return mThumbIds[position];
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        ImageView imageView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.cell_grid, parent, false);
            imageView = grid.findViewById(R.id.imagepart);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setId(mViewsID[position]);
        } else {
            grid = convertView;
        }
        if(mIsNew) animateСreatureField(grid, position);
        return grid;
    }


    void animateСreatureField(View grid, int position) {

        alphaAnimator = ValueAnimator.ofFloat(0f, 1f);
        alphaAnimator.setRepeatCount(0);
        alphaAnimator.setDuration((position % 3 + 1) * 1500);
        alphaAnimator.addUpdateListener(animation -> {
            grid.setAlpha((Float) animation.getAnimatedValue());
        });
        alphaAnimator.start();
    }
}