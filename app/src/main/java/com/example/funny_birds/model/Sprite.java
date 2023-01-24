package com.example.funny_birds.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public class Sprite {
    private Bitmap bitmap;
    private List<Rect> frames;
    private int width;
    private int height;
    private int currentFrame;
    private double frameTime;
    private double timeForCurrentFrame;

    private double x, y;
    private double velocityX, velocityY;

    private int padding;

    public Sprite(double x, double y, double velocityX, double velocityY, Rect initialFrame, Bitmap bitmap) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.bitmap = bitmap;
        this.frames = new ArrayList<Rect>();
        this.frames.add(initialFrame);
        this.bitmap = bitmap;
        this.timeForCurrentFrame = 0.0;
        this.frameTime = 0.1;
        this.currentFrame = 0;
        this.width = initialFrame.width();
        this.height = initialFrame.height();
        this.padding = 20;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public int getFramesCount() {
        return frames.size();
    }

    public double getTimeForCurrentFrame() {
        return timeForCurrentFrame;
    }

    public double getFrameTime() {
        return frameTime;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame % getFramesCount();
    }

    public void setFrameTime(double frameTime) {
        this.frameTime = Math.abs(frameTime);
    }

    public void setTimeForCurrentFrame(double timeForCurrentFrame) {
        this.timeForCurrentFrame = Math.abs(timeForCurrentFrame);
    }

    public void addFrame(Rect frame) {
        frames.add(frame);
    }

    public void update(int ms) {
        timeForCurrentFrame += ms;
        if (timeForCurrentFrame >= frameTime) {
            currentFrame = (currentFrame + 1) % getFramesCount();
            timeForCurrentFrame -= frameTime;
        }
        x = x + velocityX * ms / 1000.0;
        y = y + velocityY * ms / 1000.0;
    }

    public void draw(Canvas canvas) {
        Paint p = new Paint();
        Rect destination = new Rect((int) x, (int) y, (int) (x + width), (int) (y + height));
        canvas.drawBitmap(bitmap, frames.get(currentFrame), destination, p);
    }

    public Rect getBoundingBoxRect() {
        return new Rect((int) x + padding, (int) y + padding,
                (int) (x + width - 2 * padding), (int) (y + height - 2 * padding));
    }

    public boolean intersect (Sprite s) {
        return getBoundingBoxRect().intersect(s.getBoundingBoxRect());
    }
}
