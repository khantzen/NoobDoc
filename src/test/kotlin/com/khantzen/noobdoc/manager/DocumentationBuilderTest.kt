package com.khantzen.noobdoc.manager

import com.khantzen.noobdoc.configuration.model.User
import com.khantzen.noobdoc.model.Documentation
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.junit.Test

class DocumentationBuilderTest {

    @Test
    fun buildJavaDocumentationTest() {
        val docBuilder = DocumentationBuilder(User("j", "java", "src/test/resources/ProjectTemplate/java/"))

        val doc: Documentation = docBuilder.build()!!

        assertThat(doc.sectionList).isNotNull.isNotEmpty.hasSize(2)
    }

    @Test
    fun noSupportedLanguage() {
        val docBuilder = DocumentationBuilder(User("j", "unknown", "src/test/resources/ProjectTemplate/java/"))

        try {
            val doc = docBuilder.build()
            fail("Suppose to fail")
        } catch (ignored: IllegalArgumentException) {
            return
        } catch (ignored: Exception) {
            fail("Invalid exception thrown")
        }
    }

    @Test
    fun buildPythonDocumentation() {

    }
}