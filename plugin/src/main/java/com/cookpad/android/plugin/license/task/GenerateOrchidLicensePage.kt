// SPDX-License-Identifier: Apache-2.0
// Copyright (c) 2016 Cookpad Inc.

package com.cookpad.android.plugin.license.task

import com.cookpad.android.plugin.license.LicenseToolsPluginExtension
import com.cookpad.android.plugin.license.Templates
import com.cookpad.android.plugin.license.data.LibraryInfo
import com.cookpad.android.plugin.license.extension.writeLicenseHtml
import com.cookpad.android.plugin.license.extension.writeLicenseCss
import com.cookpad.android.plugin.license.util.YamlUtils
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.internal.impldep.com.google.common.annotations.VisibleForTesting

object GenerateOrchidLicensePage {
    fun register(project: Project): Task {
        return project.task("generateOrchidLicensePage").doLast {
            val ext = project.extensions.getByType(LicenseToolsPluginExtension::class.java)
            val yamlInfoList = YamlUtils.loadToLibraryInfo(project.file(ext.licensesYaml))
            project.writeLicenseHtml(yamlInfoList.toHtml())
            project.writeLicenseCss(Templates.cssTemplate())
            project.logger.quiet( "File successfully generated:\n" +
                    "${project.projectDir.canonicalPath}/${ext.assetsDir}/${ext.outputHtml}" +
                    "${project.projectDir.canonicalPath}/${ext.assetsDir}/${ext.outputCss}")
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
