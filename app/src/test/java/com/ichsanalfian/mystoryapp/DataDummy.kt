package com.ichsanalfian.mystoryapp

import com.ichsanalfian.mystoryapp.response.ListStoryItem

object DataDummy {
    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..9) {
            val lat = -10.212 + i * 0.001
            val lon = -16.002 + i * 0.001
            val story = ListStoryItem(
                "https://story-api.dicoding.dev/images/stories/photos-$i.png",
                "2022-01-08T06:34:1$i.598Z",
                "Dimas $i",
                "Lorem Ipsum $i",
                lon,
                "story-$i",
                lat
            )
            items.add(story)
        }
        return items
    }
}