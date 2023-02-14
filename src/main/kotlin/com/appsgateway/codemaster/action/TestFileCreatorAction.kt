package com.appsgateway.codemaster.action

import com.appsgateway.codemaster.exceptions.CreateFileFailedException
import com.appsgateway.codemaster.exceptions.FileAlreadyExistException
import com.appsgateway.codemaster.files.FileCreator
import com.appsgateway.codemaster.files.FileInteraction
import com.appsgateway.codemaster.files.SourceFileHelper
import com.appsgateway.codemaster.network.base.ChatGPTAPI
import com.appsgateway.codemaster.network.base.OPERATIONTYPE
import com.appsgateway.codemaster.utlis.TestFileContentUtils
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.ui.Messages
import kotlinx.coroutines.runBlocking

class TestFileCreatorAction : AnAction() {

    /**
     * On Android Studio the update(event) may be called twice with the first event contains
     * null virtual file but the second call the virtual file isn't null.
     *
     * Without suspending the update with Thread.sleep(1000) the presentation may be failed to enable the action.
     */
    override fun update(event: AnActionEvent) {

//        val virtualFile = event.getData(PlatformDataKeys.VIRTUAL_FILE)
//        val sourceFileHelper = SourceFileHelper.createFrom(event)
////        val isLibFile = sourceFileHelper.contentRootPath.startsWith("lib/")
////        val isShouldEnable = virtualFile?.extension == "dart" && isLibFile
////        event.presentation.isEnabled = isShouldEnable




        super.update(event)
        updated(event)
    }

    override fun actionPerformed(event: AnActionEvent) {
/*       val project = event.project
//        val sourceFileHelper = SourceFileHelper.createFrom(event)
//        val testFilePath = sourceFileHelper.createTestFilePath()
//        val confirmedPath = Messages.showInputDialog(
//            "Test file path",
//            "Create Flutter Test File",
//            null,
//            testFilePath,
//            null
//        )
//        if (confirmedPath != null) {
//            val fileCreator = FileCreator(project!!)
//            val (testDir, fileName) = sourceFileHelper.splitPathAndFileName(confirmedPath, "")
//            try {
//                val testFile = fileCreator.createFile(testDir, fileName)
//                TestFileContentUtils.writeContentToFile(testFile)
//                val fileInteraction = FileInteraction(project)
//                fileInteraction.refreshWithoutFileWatcher(false)
//                fileInteraction.openFile(testFile)
//                fileInteraction.goToLine(4)
//            } catch (e: FileAlreadyExistException) {
//                Messages.showErrorDialog("File already exists", "Error")
//            } catch (e: CreateFileFailedException) {
//                Messages.showErrorDialog("Failed to create test file", "Error")
//            }
//        }
    }*/
        callApi(event)
}



}


fun  callApi(event: AnActionEvent){
    val editor = event.getRequiredData(CommonDataKeys.EDITOR)
    val project = event.getRequiredData(CommonDataKeys.PROJECT)
    val document = editor.document
    // Work off of the primary caret to get the selection info
    val primaryCaret = editor.caretModel.primaryCaret
    val start = primaryCaret.selectionStart
    val end = primaryCaret.selectionEnd
    // Replace the selection with a fixed string.
    // Must do this document change in a write action context.


    WriteCommandAction.runWriteCommandAction(
        project
    ) {
        runBlocking {
            val result = ChatGPTAPI.getInstance()
                .getResponseFromChatGPT(
                    document.text.substring(start, end),
                    OPERATIONTYPE.UNITTEST
                )

            //todo ::: action on select
            Messages.showMessageDialog(
                project,
                result,
                "Code Master unit test",
                Messages.getInformationIcon()
            )
        }

    }
    // De-select the text range that was just replaced
    primaryCaret.removeSelection()
}


fun  updated(event: AnActionEvent){
    // Get required data keys
    val project = event.project
    val editor = event.getData(CommonDataKeys.EDITOR)
    // Set visibility and enable only in case of existing project and editor and if a selection exists
    event.presentation.isEnabledAndVisible =
        project != null && editor != null && editor.selectionModel.hasSelection()


}