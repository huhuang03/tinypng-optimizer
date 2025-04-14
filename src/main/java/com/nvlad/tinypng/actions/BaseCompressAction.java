package com.nvlad.tinypng.actions;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.ArrayUtil;
import com.nvlad.tinypng.Constants;
import com.nvlad.tinypng.PluginGlobalSettings;
import com.tinify.Tinify;

import java.util.LinkedList;
import java.util.List;

public abstract class BaseCompressAction extends AnAction {
    private static final String[] SUPPORTED_EXTENSIONS = {"png", "jpg", "jpeg"};

    @Override
    public void actionPerformed(AnActionEvent e) {
        if (StringUtil.isEmptyOrSpaces(Tinify.key())) {
            PluginGlobalSettings settings = PluginGlobalSettings.getInstance();
            if (StringUtil.isEmptyOrSpaces(settings.apiKey)) {
                settings.apiKey = Messages.showInputDialog(e.getProject(), Constants.API_KEY_QUESTION, Constants.TITLE, Messages.getQuestionIcon());
            }

            if (StringUtil.isEmptyOrSpaces(settings.apiKey)) {
                return;
            }

            Tinify.setKey(settings.apiKey);
        }
    }

    @Override
    public boolean isDumbAware() {
        return true;
    }

    List<VirtualFile> getSupportedFileList(VirtualFile[] files, boolean breakOnFirstFound) {
        List<VirtualFile> result = new LinkedList<>();
        if (files == null) {
            return result;
        }

        for (VirtualFile file : files) {
            if (file.isDirectory()) {
                //noinspection UnsafeVfsRecursion
                result.addAll(getSupportedFileList(file.getChildren(), breakOnFirstFound));
                if (breakOnFirstFound && !result.isEmpty()) {
                    break;
                } else {
                    continue;
                }
            }

            final String extension = file.getExtension();
            if (extension != null && ArrayUtil.contains(extension.toLowerCase(), SUPPORTED_EXTENSIONS)) {
                result.add(file);
                if (breakOnFirstFound) {
                    break;
                }
            }
        }

        return result;
    }
}
