package controller;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

public class KeyBoardManager {
    private boolean isCtrl;

    private DrawController drawController;
    private AnchorPane drawingArea;

    public KeyBoardManager(DrawController drawController) {
        this.drawController = drawController;
        this.drawingArea = drawController.getDrawingArea();
        addListener();
    }

    public void addListener() {
        drawingArea.setOnKeyPressed(e -> {
            if (e.isControlDown())// 按住了Ctrl键并且未释放
            {
                this.isCtrl = true;
            }
            if (e.getCode() == KeyCode.DELETE) {//设置已经选中的都删除
                drawController.delete();
            }
            e.consume();
        });

        drawingArea.setOnKeyReleased(e -> {
            this.isCtrl = false;
        });
    }

    public boolean isCtrl() {
        return isCtrl;
    }

    public void setCtrl(boolean isCtrl) {
        this.isCtrl = isCtrl;
    }
}
