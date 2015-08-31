
package cellularland1;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.control.Label;

/**
 * Controller of the clock displayed in the menu.
 *
 * @author Deas
 */
final class DigitalClock {
    /** Timeline to generate signal over time. */
    final private Timeline timeline;
    /** The label that is refreshed in constant intervals.*/
    final private Label label;
    
    int minutes;
    int seconds;

    public DigitalClock(Label l) {
        label = l;
        reset();

        timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> {
                    step();
                }));
    }
    
    public void reset() {
        minutes = seconds = 0;
    }
    public void refresh() {
        String str = seconds + "";
        if(str.length() == 1) {
            str = "0" + str;
        }
        label.setText(minutes + ":" + str);
    }
    public void step() {
        if(seconds != 59) {
            seconds += 1;
            refresh();
        } else {
            seconds = 0;
            minutes += 1;
            refresh();
        }
    }
    public void start() {
        reset();
        refresh();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    public void resume() {
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    public void stop() {
        timeline.stop();
    }
    public boolean isRunning() {
        return timeline.getStatus() == Animation.Status.RUNNING; 
    }
    public void addSeconds(int s) {
        for(int i = 0; i < s; i++) {
            step();
        }
    }
    public int getTotalSeconds() {
        return minutes * 60 + seconds;
    }
}
