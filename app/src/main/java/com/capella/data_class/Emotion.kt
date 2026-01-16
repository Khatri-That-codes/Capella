package com.capella.data_class

data class Emotion(
    val label: String,
    val icon : String
)


val emotions = listOf(
    Emotion("Happy", "😊"),
    Emotion("Sad", "😢"),
    Emotion("Angry", "😠"),
    Emotion("Surprised", "😲"),
    Emotion("Fearful", "😨"),
    Emotion("Disgusted", "🤢"),
    Emotion("Neutral", "😐"),
    Emotion("Excited", "🤩"),
    Emotion("Hungry", "😋")
)