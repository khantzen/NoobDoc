package com.khantzen.noobdoc.manager

import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths



class FileFetcherTest {
    lateinit var projectFileFetcher: ProjectFileFetcher
    val path = "src/test/resources/fileFetcher"

    @Before
    fun setUp() {
        // On instancie la classe manager
        projectFileFetcher = ProjectFileFetcher()

        // Création de l'arborescence pour test U dans test/java/resources
        File(path).deleteRecursively()
        File(path).mkdirs()


    }

    @After
    fun end() { // On supprime le fichier après le test
        File(path).deleteRecursively()
    }

    @Test
    fun testFileFetching() {
        createFile("$path/fold1/f2.java")
        createFile("$path/fold1/f3.java")
        createFile("$path/fold1/fold2/f4.java")
        createFile("$path/f1.java")

        val fileList : List<File> = projectFileFetcher.listFileTree(path, "java")

        assertThat(fileList)
                .isNotNull
                .isNotEmpty
                .hasSize(4)
    }

    @Test
    fun testFileFetchingWithMixedExtension() {
        createFile("$path/fold1/f2.java")
        createFile("$path/fold1/f3.java")
        createFile("$path/fold1/fold2/f4.java")
        createFile("$path/fold1/fold2/noJava.txt")
        createFile("$path/fold1/fold2/noJava2.cs")
        createFile("$path/f1.java")

        val fileList : List<File> = projectFileFetcher.listFileTree(path, "java")

        assertThat(fileList)
                .isNotNull
                .isNotEmpty
                .hasSize(4)
    }

    private fun createFile(path: String) {
        val pathToFile: Path = Paths.get(path)
        Files.createDirectories(pathToFile.getParent())
        Files.createFile(pathToFile)
    }
}