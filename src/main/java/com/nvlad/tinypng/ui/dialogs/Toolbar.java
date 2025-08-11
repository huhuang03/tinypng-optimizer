package com.nvlad.tinypng.ui.dialogs;

import com.intellij.ide.actions.AboutAction;
import com.intellij.openapi.actionSystem.*;

import javax.swing.*;
import java.awt.*;

class Toolbar {
    static JPanel create() {
        DefaultActionGroup group = new DefaultActionGroup();
        group.add(new AboutAction());

        ActionToolbar actionToolbar = ActionManager.getInstance().createActionToolbar("top", group, true);
        JPanel toolbar = (JPanel) actionToolbar.getComponent();
        toolbar.setMinimumSize(new Dimension(0, actionToolbar.getMaxButtonHeight()));

        return toolbar;
    }
}
