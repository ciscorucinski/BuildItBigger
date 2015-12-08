package want.to.see.a.magictrick;

import android.support.annotation.NonNull;

import java.util.EnumSet;

import in.championswimmer.sfg.lib.SimpleFingerGestures;

public class Gestures<T> extends SimpleFingerGestures implements
        SimpleFingerGestures.OnFingerGestureListener {

	private T fragment;
    private OnGestureListener<T> listener;
    private @NonNull EnumSet<GestureInformation> gestures;

    private static final boolean HANDLED_GESTURE = false;
	private static final boolean NOT_HANDLED = !HANDLED_GESTURE;

    public Gestures() {

//        this.listener = this;
        this.gestures = EnumSet.allOf(GestureInformation.class); // If nothing was filtered, then allow all gestures
	    this.fragment = null; // If nothing is listened on, then no gestures should register

    }

	public void addGestureListener(OnGestureListener<T> listener){ this.listener = listener; }
    public void filterGestures(@NonNull EnumSet<GestureInformation> set) { gestures = set; }
	public void listenOn(T view) { this.fragment = view; }

    public boolean sendSwipeEvent(GestureInformation swipe, int fingers) {

        // if no gestures
        if (gestures.isEmpty()) return NOT_HANDLED; // Quick check to optimize if no gestures registered
        if (!gestures.contains(swipe)) return NOT_HANDLED;

        if (listener == null) return NOT_HANDLED;
	    if (fragment == null) return NOT_HANDLED;  // If there is no fragment, then nothing will happen

        listener.onFilteredGesture(fragment, swipe, fingers);
        return HANDLED_GESTURE;


    }

    @Override public boolean onSwipeUp(int i, long l, double v)  {
        return sendSwipeEvent(GestureInformation.UP, i);
    }
    @Override public boolean onSwipeDown(int i, long l, double v) {
        return sendSwipeEvent(GestureInformation.DOWN, i);
    }
    @Override public boolean onSwipeLeft(int i, long l, double v) {
        return sendSwipeEvent(GestureInformation.LEFT, i);
    }
    @Override public boolean onSwipeRight(int i, long l, double v) {
        return sendSwipeEvent(GestureInformation.RIGHT, i);
    }
    @Override public boolean onPinch(int i, long l, double v) {
        return sendSwipeEvent(GestureInformation.PINCH, i);
    }
    @Override public boolean onUnpinch(int i, long l, double v) {
        return sendSwipeEvent(GestureInformation.STRETCH, i);
    }
    @Override public boolean onDoubleTap(int i) {
        return sendSwipeEvent(GestureInformation.DOUBLE_TAP, i);
    }

    public interface OnGestureListener<T> {

        void onFilteredGesture(T fragment, GestureInformation swipe, int fingers);

    }


    public interface Filterable<T> {

	    Gestures<T> modifyGestureListener(@NonNull Gestures<T> gestures);

    }
}