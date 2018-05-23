package com.khantzen.noobdoc.manager

import java.io.File

//@noobDocSection("NoobDoc-01", "Project file tree building")
class ProjectFileFetcher {

    fun listFileTree(path: String, ext: String): List<File> {
        val fileTree = arrayListOf<File>()
        val dir: File = File(path)

        //@noobDoc("NoobDoc will loop recursively on each of your folder", "NBFetch", "NoobDocFetching")
        if (dir.listFiles() == null) {
            return fileTree
        }

        for (entry: File in dir.listFiles()) {
            //@noobDoc("For each entry in each folder, if the extension match with the one passed in programm argument NoobDoc will generate a documentation for this file", "NBFetch-00", "NoobDocFetching")
            if (entry.isFile && entry.path.endsWith(".$ext")) fileTree.add(entry)
            //@noobDoc("If NoobDoc find folder in your folders, it will loop into them too and document the valid one", "NBFetch-01", "NoobDocFetching")
            else fileTree.addAll(listFileTree(entry.path, ext))
        }

        return fileTree
    }
}