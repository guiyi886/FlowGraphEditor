package controller;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

// 键盘管理器
public class KeyBoardManager {
    private boolean isCtrl; // 记录是否按下了 Ctrl 键

    private DrawController drawController; // 绘图区控制器
    private AnchorPane drawingArea; // 绘图区

    // 构造函数，接受绘图区控制器实例，并初始化成员变量
    public KeyBoardManager(DrawController drawController) {
        this.drawController = drawController;
        this.drawingArea = drawController.getDrawingArea();
        addListener(); // 添加键盘事件监听器
    }

    // 添加键盘事件监听器
    public void addListener() {
        // 键盘按下事件处理
        drawingArea.setOnKeyPressed(e -> {
            if (e.isControlDown()) // 如果按下了 Ctrl 键
            {
                this.isCtrl = true; // 记录按下了 Ctrl 键
            }
            // 处理删除操作
            if (e.getCode() == KeyCode.DELETE) {
                drawController.delete(); // 删除选中的形状或线条
            }
            // 处理复制操作
            if (e.getCode() == KeyCode.C && isCtrl) {
                System.out.println("复制");
                drawController.copy(); // 复制选中的形状或线条
            }
            // 处理粘贴操作
            if (e.getCode() == KeyCode.V && isCtrl) {
                System.out.println("粘贴");
                drawController.paste(); // 粘贴形状或线条
            }
            e.consume(); // 消费事件，防止事件继续传递
        });

        // 键盘释放事件处理
        drawingArea.setOnKeyReleased(e -> {
            this.isCtrl = false; // 释放 Ctrl 键
        });
    }

    // 返回是否按下了 Ctrl 键
    public boolean isCtrl() {
        return isCtrl;
    }

    // 设置是否按下了 Ctrl 键
    public void setCtrl(boolean isCtrl) {
        this.isCtrl = isCtrl;
    }
}
