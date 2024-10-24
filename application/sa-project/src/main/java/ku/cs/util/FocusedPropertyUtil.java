package ku.cs.util;

import animatefx.animation.FadeInUp;
import animatefx.animation.FadeOut;
import javafx.scene.Node;
import javafx.scene.control.TextInputControl;

import java.util.Collections;
import java.util.List;

public class FocusedPropertyUtil {

    private FocusedPropertyUtil() {}
    public static void setAppearNodesOnFieldFocused(TextInputControl focusedNode, List<Node> showNodes, double speed) {
        if(focusedNode.getText().equalsIgnoreCase("")){
            showNodes.forEach(t->new FadeOut(t).setSpeed(1000).play());
        }
        focusedNode.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue && focusedNode.getText().equalsIgnoreCase("")) showNodes.forEach(t->new FadeInUp(t).setSpeed(speed).play());
            else if (focusedNode.getText().equalsIgnoreCase("")) showNodes.forEach(t->new FadeOut(t).setSpeed(speed).play());
        });
    }
    public static void setAppearNodesOnFieldFocused(TextInputControl focusedNode, List<Node> showNodes) {
        setAppearNodesOnFieldFocused(focusedNode, showNodes, 2);
    }

    public static void setAppearNodeOnFieldFocused(TextInputControl focusedNode, Node showNodes) {
        setAppearNodesOnFieldFocused(focusedNode, Collections.singletonList(showNodes));
    }
}
