// SPDX-License-Identifier: Apache-2.0
// Copyright (c) 2016 Cookpad Inc.

package com.cookpad.android.plugin.license.exception

import com.cookpad.android.plugin.license.data.LibraryInfo

class NotEnoughInformationException(val libraryInfo: LibraryInfo) : RuntimeException(
        "PleaseExtend licenses.yml, missing information in:\n " +
        libraryInfo.artifactId + "\n<"+libraryInfo.license+">\n Holder: " + libraryInfo.copyrightHolder)
