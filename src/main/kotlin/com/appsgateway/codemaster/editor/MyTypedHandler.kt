// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.appsgateway.codemaster.editor

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile

/**
 * This is a custom [TypedHandlerDelegate] that handles actions activated keystrokes in the editor.
 * The execute method inserts a fixed string at Offset 0 of the document.
 * Document changes are made in the context of a write action.
 */
internal class MyTypedHandler : TypedHandlerDelegate() {
    override fun charTyped(c: Char, project: Project, editor: Editor, file: PsiFile): Result {
        // Get the document and project
        val document = editor.document
        // Construct the runnable to substitute the string at offset 0 in the document
        val runnable = Runnable { document.insertString(0, "editor_basics\n") }
        // Make the document change in the context of a write action.
        WriteCommandAction.runWriteCommandAction(project, runnable)
        return Result.STOP
    }
}