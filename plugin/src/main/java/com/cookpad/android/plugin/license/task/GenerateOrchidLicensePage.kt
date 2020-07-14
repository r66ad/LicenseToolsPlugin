// SPDX-License-Identifier: Apache-2.0
// Copyright (c) 2016 Cookpad Inc.

package com.cookpad.android.plugin.license.task

import com.cookpad.android.plugin.license.LicenseToolsPluginExtension
import com.cookpad.android.plugin.license.Templates
import com.cookpad.android.plugin.license.data.LibraryInfo
import com.cookpad.android.plugin.license.extension.loadYaml
import com.cookpad.android.plugin.license.extension.writeLicenseHtml
import com.cookpad.android.plugin.license.extension.writeLicenseCss
import com.cookpad.android.plugin.license.util.YamlUtils
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.internal.impldep.com.google.common.annotations.VisibleForTesting
import org.yaml.snakeyaml.Yaml
import java.io.File

object GenerateOrchidLicensePage {
    fun register(project: Project): Task {
        return project.task("generateOrchidLicensePage").doLast {
            project.pluginManager.withPlugin("com.eden.orchidPlugin") {
                project.logger.quiet("ORCHID PLUGIN FOUND: " + project.projectDir.absolutePath.toString())
                val ext = project.extensions.getByType(LicenseToolsPluginExtension::class.java)
                if(ext.withOrchid) {
                    val yamlInfoList = YamlUtils.loadToLibraryInfo(project.file(ext.licensesYaml))
                    project.writeLicenseHtml(yamlInfoList.toHtml(), false)
                    project.writeLicenseCss(Templates.cssTemplate())
//                    val orchidConfig = project.file("${ext.orchidDir}/config.yml").loadYaml()
//                    project.logger.quiet("YAML: " + orchidConfig)
                    project.logger.quiet("Please upgrade the orchid config:" + project.file("${ext.orchidDir}/config.yml"))
                    project.logger.quiet("\n" +
                            "Editorial:\n" +
                            "  extraCss:\n" +
                            "    - 'assets/css/'"+ext.outputCss+"\n" +
                            "...\n" +
                            "  menu:\n" +
                            "    - type: \"link\"\n" +
                            "      title: Software Licenses\n" +
                            "      url: /licenses_output\n")
                    project.logger.quiet("Files successfully generated:")
                    project.logger.quiet("${project.projectDir.canonicalPath}/${ext.orchidDir}/pages/${ext.outputHtml}")
                    project.logger.quiet("${project.projectDir.canonicalPath}/${ext.orchidDir}/assets/css/${ext.outputCss}")
                }
            }
        }
    }

    @VisibleForTesting
    fun List<LibraryInfo>.toHtml(): String {
        val licenseHtml = StringBuffer()
        this.filterNot { it.skip ?: false }
            .forEach { licenseHtml.append(Templates.buildLicenseHtml(it)) }
        return licenseHtml.toString()
    }
}
