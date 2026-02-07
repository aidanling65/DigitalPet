package com.example.tamagotchi.data_logging.google

enum class LoadingState(val message: (InteractionType) -> String) {
    LOADING(
        { it ->
            when (it) {
                InteractionType.BACKUP -> "Uploading data"
                InteractionType.RESTORE -> "Loading data"
                InteractionType.LOGIN -> "Logging in"
                else -> ""
            }
        }
    ),
    SUCCESS({
        when (it) {
            InteractionType.BACKUP -> "Data uploaded"
            InteractionType.RESTORE -> "Data restored, you may need to restart the app to see changes"
            InteractionType.LOGIN -> "Logged in"
            else -> ""
        }
    }
    ),
    FAILED({
        when (it) {
            InteractionType.BACKUP -> "Error uploading data"
            InteractionType.RESTORE -> "Error loading data"
            InteractionType.LOGIN -> "Error logging in"
            else -> ""
        }
    }
    ),
    CLOSED(
        {""}
    )
}

enum class InteractionType {
    BACKUP,
    RESTORE,
    LOGIN,
    NONE
}

data class LoadingStateData(
    var loadingState: LoadingState,
    var interactionType: InteractionType
)