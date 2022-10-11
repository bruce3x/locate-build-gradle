package com.bruce3x.locatebuildgradle

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.fileEditor.OpenFileDescriptor

class OpenLocalFileAction : AnAction() {
    override fun update(e: AnActionEvent) {
        val project = e.project
        e.presentation.isEnabledAndVisible = project != null
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.dataContext.getData(PlatformDataKeys.PROJECT) ?: return
        val virtualFile = e.dataContext.getData(PlatformDataKeys.VIRTUAL_FILE) ?: return

        val files = BuildGradleSearcher.search(project, virtualFile)

        val buildGradle = files.firstOrNull() ?: return

        OpenFileDescriptor(project, buildGradle).navigate(true)
    }

}
