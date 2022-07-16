package ru.roundkubik.news.presentation.model

interface LanguagesUi {

    fun map(
        russian: () -> Unit,
        english: () -> Unit,
        none: () -> Unit
    )

    class Base(
        private val choice: LanguageChoice,
        private val englishTitle: String,
        private val russianTitle: String
    ) : LanguagesUi {
        override fun map(
            russian: () -> Unit,
            english: () -> Unit,
            none: () -> Unit
        ) {
            when (choice) {
                LanguageChoice.ENGLISH -> english()
                LanguageChoice.RUSSIAN -> russian()
                LanguageChoice.NONE -> none()
            }
        }
    }
}

enum class LanguageChoice {
    ENGLISH,
    RUSSIAN,
    NONE
}