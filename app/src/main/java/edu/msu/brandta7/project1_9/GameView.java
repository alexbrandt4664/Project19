package edu.msu.brandta7.project1_9;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * Custom view class for our game.
 */
public class GameView extends View {

    private Game game;

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

    private void init(AttributeSet attrs, int defStyle) {game = new Game(getContext());}

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        game.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {return game.onTouchEvent(this, event);}

    public Game getGame() { return game; }

    public void setGame(Game game) { this.game = game; }
}
