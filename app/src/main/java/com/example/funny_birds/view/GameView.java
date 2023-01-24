package com.example.funny_birds.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;

import com.example.funny_birds.R;
import com.example.funny_birds.model.Sprite;

public class GameView extends View {
    private int width, height;
    private int points;
    private Sprite player;
    private final int timerInterval = 30;
    private Sprite enemy;

    class Timer extends CountDownTimer {
        public Timer() {
            super(Integer.MAX_VALUE, timerInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            update();
        }

        @Override
        public void onFinish() {
        }
    }

    public GameView(Context context) {
        super(context);
        points = 0;

        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.player);

        int w = b.getWidth() / 5;
        int h = b.getHeight() / 3;

        Rect firstFrame = new Rect(0, 0, w, h);
        player = new Sprite(10, 0, 0, 300, firstFrame, b);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if (i == 2 && j == 3) {
                    continue;
                }
                player.addFrame(new Rect(j * w, i * h, j * w + w, i * w + w));
            }
        }
        b = BitmapFactory.decodeResource(getResources(), R.drawable.enemy);
        w = b.getWidth() / 5;
        h = b.getHeight() / 3;

        firstFrame = new Rect(4 * w, 0, 5 * w, h);

        enemy = new Sprite(2000, 250, -300, 0, firstFrame, b);

        for (int i = 0; i < 3; i++) {
            for (int j = 4; j >= 0; j--) {
                if (i ==0 && j == 4) {
                    continue;
                }
                if (i ==2 && j == 0) {
                    continue;
                }
                enemy.addFrame(new Rect(j * w, i * h, j * w + w, i * w + w));
            }
        }
        Timer t = new Timer();
        t.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawARGB(250, 127, 199, 255);
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setTextSize(180.0F);
        p.setColor(Color.BLACK);
        p.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(points + "", (int)(width / 2), 200, p);

        player.draw(canvas);
        enemy.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventAction = event.getAction();
        if (eventAction == MotionEvent.ACTION_DOWN)  {
            if (event.getY() < player.getBoundingBoxRect().top) {
                player.setVelocityY(-300);
                points--;
            }
            else if (event.getY() > (player.getBoundingBoxRect().bottom)) {
                player.setVelocityY(300);
                points--;
            }
        }
        return true;
    }

    private void teleportEnemy () {
        enemy.setX(width + Math.random() * 500);
        enemy.setY(Math.random() * (height - enemy.getHeight()));
    }

    protected void update() {
        player.update(timerInterval);
        enemy.update(timerInterval);

        if (player.getY() + player.getHeight() > height) {
            player.setY(height - player.getHeight());
            player.setVelocityY(-player.getVelocityY());
            points--;
        }
        else if (player.getY() < 0) {
            player.setY(0);
            player.setVelocityY(-player.getVelocityY());
            points--;
        }

        if (enemy.getX() < - enemy.getWidth()) {
            teleportEnemy();
            points +=10;
        }
        if (enemy.intersect(player)) {
            teleportEnemy();
            points -= 40;
        }

        invalidate();
    }
}
