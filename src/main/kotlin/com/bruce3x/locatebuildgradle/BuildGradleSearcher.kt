package com.bruce3x.locatebuildgradle

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

object BuildGradleSearcher {

    private fun parents(project: Project, file: VirtualFile): Sequence<VirtualFile> {
        return sequence {
            var next: VirtualFile? = file.parent
            while (next != null) {
                yield(next)

                if (next.path == project.basePath) break
                next = next.parent
            }
        }
    }

    fun search(project: Project, file: VirtualFile): Sequence<VirtualFile> {
        return parents(project, file)
            .mapNotNull { it.children.firstOrNull(VirtualFile::isBuildGradle) }
    }
}

private val VirtualFile.isBuildGradle: Boolean
    get() = name == "build.gradle" || name == "build.gradle.kts"

