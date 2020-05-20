// SPDX-License-Identifier: Apache-2.0
// Copyright (c) 2016 Cookpad Inc.

package com.cookpad.android.plugin.license.extension

import com.cookpad.android.plugin.license.LicenseToolsPluginExtension
import org.gradle.api.Project
import org.gradle.api.logging.LogLevel

fun Project.toFormattedText(): String {
    return "$group:$name:$version"
}

fun Project.writeLicenseHtml(html: String, withCss: Boolean = true) {
    val ext = extensions.getByType(LicenseToolsPluginExtension::class.java)
    val assetsDir = file(ext.assetsDir)
    assetsDir.mkdirs()
    logger.info( "render $assetsDir/${ext.outputHtml}")
    file("$assetsDir/${ext.outputHtml}").writeText(html)
}
fun Project.writeLicenseCss(css: String) {
    val ext = extensions.getByType(LicenseToolsPluginExtension::class.java)
    val assetsDir = file("src/main/assets")
    assetsDir.mkdirs()
    logger.info("render $assetsDir/${ext.outputHtml}")
    file("$assetsDir/${ext.outputCss}").writeText(css)
}

fun Project.writeLicenseJson(json: String) {
    val ext = extensions.getByType(LicenseToolsPluginExtension::class.java)
    val assetsDir = file(ext.assetsDir)
    assetsDir.mkdirs()
    logger.info("render $assetsDir/${ext.outputJson}")
    file("$assetsDir/${ext.outputJson}").writeText(json)
}

fun Project.writeLicenseYaml(yaml: String) {
    val ext = extensions.getByType(LicenseToolsPluginExtension::class.java)
    logger.info("render ./${ext.licensesYaml}")
    file("./${ext.licensesYaml}").writeText(yaml)
}
