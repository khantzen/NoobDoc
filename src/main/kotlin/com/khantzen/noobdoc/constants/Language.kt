package com.khantzen.noobdoc.constants

import java.util.*


enum class Language(val id: String, val extension: String, val inlineComment: String) {
    Java("java", "java", "//"),
    CSharp("c#", "cs", "//"),
    Kotlin("kotlin", "kt", "//"),
    Php("php", "php", "//"),
    Python("python", "py", "#");

    companion object {
        fun getById(id: String): Language {
            return Arrays.stream(Language.values())
                    .filter({ it.id == id })
                    .findFirst()
                    .orElse(null)
                    ?: throw IllegalArgumentException("Language $id is not supported by NoobDoc")
        }
    }

}