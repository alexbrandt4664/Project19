package edu.msu.brandta7.project1_9;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 * Custom view class for our Puzzle.
 */
public class GameView extends View {

    private Game game;

    private int width;
    private int height;

    public GameView(Context context) {
        super(context);
        init(null, 0);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {game = new Game(getContext(), this);}


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        game.draw(canvas);
    }


}
