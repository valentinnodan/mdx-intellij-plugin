package org.intellij.plugin.mdx.ui.preview

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.ProcessOutput
import com.intellij.javascript.nodejs.NodeCommandLineUtil
import com.intellij.javascript.nodejs.interpreter.NodeCommandLineConfigurator
import com.intellij.javascript.nodejs.interpreter.NodeInterpreterUtil
import com.intellij.javascript.nodejs.interpreter.NodeJsInterpreter
import com.intellij.javascript.nodejs.interpreter.NodeJsInterpreterManager
import com.intellij.lang.javascript.buildTools.npm.PackageJsonUtil
import com.intellij.lang.javascript.library.JSLibraryUtil
import com.intellij.openapi.application.ApplicationNamesInfo
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.util.registry.Registry
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.psi.PsiManager
import com.intellij.psi.impl.PsiManagerImpl
import org.intellij.plugin.mdx.lang.MdxFileType
import org.intellij.plugin.mdx.lang.psi.MdxFile
import java.io.File

class MdxHtmlGetter {
    private val MARKER = "!!!${ApplicationNamesInfo.getInstance().fullProductName} webpack loader!!!"

    internal val logger: Logger = Logger.getInstance("#org.intellij.plugin.mdx.ui.preview.WebPackExecutor")

    private fun run(project: Project, interpreter: NodeJsInterpreter, virtualFile: VirtualFile): ProcessOutput? {
        virtualFile.contentsToByteArray()
        val commandLine = GeneralCommandLine()
        commandLine.withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
        commandLine.withCharset(Charsets.UTF_8)
        commandLine.environment["NODE_ENV"] = "development"
        val contents = String(virtualFile.contentsToByteArray())
        commandLine.workDirectory = File(project.basePath!!)
        commandLine.addParameter("-e")
        val targetPath = NodeInterpreterUtil.convertLocalPathToRemote(FileUtil.toSystemDependentName(""), interpreter)
        //language=JavaScript
        commandLine.addParameter("""
      require.main = { require: require };
      var mdxI = require('@mdx-js/mdx');
      var m = require('@mdx-js/react');
      var ReactDOMServer = require('react-dom/server');
      var babel = require("@babel/core");

      const content = `$contents`

      const transpile = () => {
        return mdxI.sync(content)
      }

      const getHtml = async () => {
        let jsxWithExportDefault = transpile();
        const jsx = jsxWithExportDefault
        .replace(/export default /, 'const MDXContent = ')
        const result = babel.transform(jsx, {
        presets: [
                    ['@babel/preset-react'],
                    ['@babel/preset-env',
                        {
                            corejs: '3.3.5',
                            useBuiltIns: 'entry',
                            modules: 'auto',
                            targets: {
                                      node: "current"
                                     }
                        }
                    ]
                    ]});

        var AsyncFunction = Object.getPrototypeOf(async function(){}).constructor

        const functionCode = "{\n" + result.code + "\nvar MDX_content_res = await MDXContent({'components': {}})\n return MDX_content_res;}"
        const func = new AsyncFunction('mdx', functionCode)
        const elem = await func(m.mdx)
        return ReactDOMServer.renderToString(elem)
      }
      (getHtml()).then(console.log)
      """.trimIndent())
        NodeCommandLineConfigurator.find(interpreter).configure(commandLine, NodeCommandLineConfigurator.defaultOptions(project))
        return NodeCommandLineUtil.execute(commandLine, Registry.intValue("webpack.execution.timeout.ms", 10000).toLong())
    }


    private fun getInterpreter(project: Project): NodeJsInterpreter? {
        val interpreter = NodeJsInterpreterManager.getInstance(project).interpreter
        return if (interpreter != null && interpreter.validate(project) == null) interpreter else null
    }

    fun loadHtml(project: Project, file: VirtualFile): String {
        if (file.fileType !== MdxFileType.INSTANCE) {
            return ""
        }
        val interpreter = getInterpreter(project) ?: return ""
        val run = run(project, interpreter, file)
        if (run != null && run.exitCode == 0) {
            val stdOut = run.stdout.trim()
            val lastNewLine = stdOut.lastIndexOf(MARKER)
            val result = if (lastNewLine >= 0) stdOut.substring(lastNewLine + MARKER.length + 1) else stdOut
            println("HERE SHOULD BE STD")
            println(stdOut)
            println("_______________________________")
            return stdOut
        } else if (run != null) {
            println("NULL")
            logger.warn("failed to evaluate mdx. exit code: ${run.exitCode}${if (run.isTimeout) ", timed out" else ""}\n" +
                    "stdout: ${run.stdout}\nstderr: ${run.stderr}")
//            errorNotify(project, configPath, run.stderr)
        }
        return ""
    }


//    private fun extractErrorDetails(stdErr: String): String {
//        for (part in stdErr.split('\n', '\r')) {
//            val indexOfErrorMessage = part.indexOf("Error:")
//            if (indexOfErrorMessage >= 0) {
//                return part.substring(indexOfErrorMessage + "Error:".length)
//            }
//        }
//        return ""
//    }

//    private fun errorNotify(project: Project, configPath: String, stdErr: String) {
//        val errorDetails = extractErrorDetails(stdErr)
//        notifier.notify(project, "Can't analyse <a href='#'>${PathUtil.getFileName(configPath)}</a>: coding assistance will ignore module resolution rules in this file.\n" +
//                "Possible reasons: this file is not a valid webpack configuration file or its format is not currently supported by the IDE." +
//                (if (StringUtil.isEmpty(errorDetails)) "" else "\nError details: $errorDetails"), MessageType.WARNING, configPath)
//    }
}