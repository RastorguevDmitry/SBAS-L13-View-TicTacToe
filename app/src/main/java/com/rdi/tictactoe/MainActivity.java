package com.rdi.tictactoe;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String ARG_SCORE_CROSSES = "score_crosses";
    private static final String ARG_SCORE_CIRCLES = "score_circles";
    private static final String CURRENT_FIGURE = "current_figure";
    private final String SAVE_PARCEL = "SAVE_PARCEL";

    private TicTacToeField ticTacToeField;
    private ImageAdapter imageAdapter;
    private TicTacToeView ticTacToeView;
    private TextView scoreCrosses;
    private TextView scoreCircles;
    private int mScoreCrosses = 0;
    private int mScoreCircles = 0;
    private View mResultsLayout;
    private TextView resultText;
    private Button buttonRestart;
    private Button buttonQuit;

    private TicTacToeField.Figure currentFigure = TicTacToeField.Figure.CROSS;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        if (savedInstanceState != null) {
            mScoreCrosses = savedInstanceState.getInt(ARG_SCORE_CROSSES);
            mScoreCircles = savedInstanceState.getInt(ARG_SCORE_CIRCLES);
            currentFigure = TicTacToeField.Figure.values()[
                    savedInstanceState.getInt(CURRENT_FIGURE)];
            ticTacToeField = savedInstanceState.getParcelable(SAVE_PARCEL);
            newField(false);
            ticTacToeView.post(() -> ticTacToeView.updateAll());
        } else newField(true);

        showScores();
    }

    private void initView() {
        ticTacToeView = findViewById(R.id.tic_tac_toe_view);
        scoreCrosses = findViewById(R.id.score_crosses);
        scoreCircles = findViewById(R.id.score_circles);
        mResultsLayout = findViewById(R.id.results_layout);
        resultText = findViewById(R.id.result_text);
        buttonRestart = findViewById(R.id.button_restart);
        buttonQuit = findViewById(R.id.button_quit);
        ticTacToeView.setBackgroundColor(Color.WHITE);
        buttonRestart.setOnClickListener(v -> {
            hideResult();
            newField(true);
        });

        buttonQuit.setOnClickListener(v -> finish());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SAVE_PARCEL, ticTacToeField);
        outState.putInt(ARG_SCORE_CROSSES, mScoreCrosses);
        outState.putInt(ARG_SCORE_CIRCLES, mScoreCircles);
        outState.putInt(CURRENT_FIGURE, currentFigure.ordinal());

        super.onSaveInstanceState(outState);
    }


    private void newField(boolean isNew) {
        if (isNew) {
            ticTacToeField = new TicTacToeField(3);
        }
        imageAdapter = new ImageAdapter(this, ticTacToeField, isNew);
        ticTacToeView.setAdapter(imageAdapter);
        ticTacToeView.setTicTacToeField(ticTacToeField);
        ticTacToeView.setOnItemClickListener(gridViewOnItemClickListener);
    }

    private GridView.OnItemClickListener gridViewOnItemClickListener = new GridView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position,
                                long id) {
            currentFigure = currentFigure == TicTacToeField.Figure.CIRCLE ? TicTacToeField.Figure.CROSS : TicTacToeField.Figure.CIRCLE;
            ticTacToeField.setFigure((position) / 3, position % 3, currentFigure);
            ticTacToeView.update(position);
            TicTacToeField.Figure winner = ticTacToeField.getWinner();

            switch (winner) {
                case NONE:
                    if (ticTacToeField.isFull()) showResults(winner, true);
                    break;
                case CROSS:
                case CIRCLE:
                    showResults(winner, true);
                    break;
            }
        }
    };

    private void showResults(TicTacToeField.Figure winner, boolean animate) {
        showResultInField();
        if (animate) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_results_appearance);
            animation.setInterpolator(new OvershootInterpolator(2f));
            mResultsLayout.startAnimation(animation);
        }
        ticTacToeView.setOnItemClickListener(null);
        mResultsLayout.setVisibility(View.VISIBLE);

        switch (winner) {
            case CROSS:
                mScoreCrosses++;
                resultText.setText(R.string.cross);
                break;
            case CIRCLE:
                mScoreCircles++;
                resultText.setText(R.string.circle);
                break;
            case NONE:
                resultText.setText(R.string.result_draw);
                break;
        }
        showScores();
    }


    private void hideResult() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_results_disappearance);
        animation.setInterpolator(new AnticipateInterpolator(2f));
        mResultsLayout.setVisibility(View.VISIBLE);
        mResultsLayout.startAnimation(animation);
        mResultsLayout.setVisibility(View.INVISIBLE);
    }

    private void showScores() {
        scoreCrosses.setText(Integer.toString(mScoreCrosses));
        scoreCircles.setText(Integer.toString(mScoreCircles));
    }

    private void showResultInField() {
        boolean[][] mWinnerMatrix = ticTacToeField.getWinnerMatrix();
        ticTacToeView.animateWinnerMatrix(mWinnerMatrix);
    }

}