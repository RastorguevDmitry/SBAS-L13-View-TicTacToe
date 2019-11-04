package com.rdi.tictactoe;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import static com.rdi.tictactoe.ImageAdapter.mViewsID;

public class TicTacToeView extends GridView {

    private ValueAnimator alphaAnimator;
    private ValueAnimator mGameEndAnimator;
    private TicTacToeField.Figure mLastFigure = TicTacToeField.Figure.NONE;
    private TicTacToeField mTicTacToeField;

    public TicTacToeField getTicTacToeField() {
        return mTicTacToeField;
    }

    public void setTicTacToeField(TicTacToeField ticTacToeField) {
        mTicTacToeField = ticTacToeField;
    }

    public TicTacToeView(Context context) {
        this(context, null);
    }

    public TicTacToeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TicTacToeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TicTacToeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    void update(int position) {
        ImageView imageView = this.findViewById(mViewsID[position]);
        mLastFigure = mTicTacToeField.getFigure((position) / 3, position % 3);
        switch (mLastFigure) {
            case NONE:
                imageView.setImageResource(R.drawable.background);
                break;
            case CIRCLE:
                imageView.setImageResource(R.drawable.circle);
                break;
            case CROSS:
                imageView.setImageResource(R.drawable.cross);
                break;
        }
        moveAnimation(imageView);
    }

    void updateAll() {
        for (int position = 0;
             position < getTicTacToeField().getSize() * getTicTacToeField().getSize();
             position++) {
            update(position);
        }
    }

    void moveAnimation(ImageView imageView) {
        alphaAnimator = ValueAnimator.ofFloat(0f, 1f);
        alphaAnimator.setRepeatCount(0);
        alphaAnimator.setDuration(500);
        alphaAnimator.addUpdateListener(animation -> {
            imageView.setAlpha((Float) animation.getAnimatedValue());
        });
        alphaAnimator.start();
    }

    public void animateWinnerMatrix(boolean[][] mWinnerMatrix) {
        int position;
        for (int row = 0; row < mWinnerMatrix[0].length; row++) {
            for (int col = 0; col < mWinnerMatrix.length; col++) {
                if (mWinnerMatrix[row][col]) {
                    position = row * 3 + col;
                    ImageView imageView = this.findViewById(mViewsID[position]);
                    imageView.post(() -> winnerMatrixAnimation(imageView));
                }
            }
        }
    }

    private void winnerMatrixAnimation(ImageView imageView) {
        mGameEndAnimator = ValueAnimator.ofFloat(0.8f, 1.2f);
        mGameEndAnimator.setDuration(700);
        mGameEndAnimator.setRepeatCount(2);
        mGameEndAnimator.addUpdateListener(animation -> {
            Float scale = (Float) mGameEndAnimator.getAnimatedValue();
            imageView.setScaleX(scale);
            imageView.setScaleY(scale);
        });
        mGameEndAnimator.start();
    }


//    @Override
//    public Parcelable onSaveInstanceState() {
//        SavedState state = new SavedState(super.onSaveInstanceState());
//        state.mField = mTicTacToeField;
//        state.mFigure = mLastFigure.ordinal();
//        return state;
//    }
//
//
//
//    @Override
//    public void onRestoreInstanceState(Parcelable state) {
//        super.onRestoreInstanceState(state);
//        //    java.lang.ClassCastException: com.rdi.tictactoe.TicTacToeView$SavedState cannot be cast to android.widget.AbsListView$SavedState
//        SavedState myState = (SavedState) state;
//        mTicTacToeField = myState.mField;
//        mLastFigure = TicTacToeField.Figure.values()[myState.mFigure];
//
////
////        if (checkWinners()) {
////            mMode = WorkingMode.GAME_ENDED;
////        } else {
////            mMode = WorkingMode.GAME;
////        }
//    }
//
//
//    private static class SavedState extends AbsSavedState {
//        private TicTacToeField mField;
//        private int mFigure;
//
//        public SavedState(Parcel source) {
//            super(source);
//            mField = source.readParcelable(getClass().getClassLoader());
//            mFigure = source.readInt();
//        }
//
//        public SavedState(Parcelable superState) {
//            super(superState);
//        }
//
//        @Override
//        public int describeContents() {
//            return 0;
//        }
//
//
//
//        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
//            @Override
//            public SavedState createFromParcel(Parcel in) {
//                return new SavedState(in);
//            }
//
//            @Override
//            public SavedState[] newArray(int size) {
//                return new SavedState[size];
//            }
//        };
//
//        @Override
//        public void writeToParcel(Parcel out, int flags) {
//            super.writeToParcel(out, flags);
//            out.writeParcelable(mField, 0);
//            out.writeInt(mFigure);
//        }
//    }


}
