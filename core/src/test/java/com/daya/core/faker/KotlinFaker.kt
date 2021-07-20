package com.daya.core.faker

import io.github.serpro69.kfaker.Faker
import java.util.*
import kotlin.random.Random

object KotlinFaker {
    val faker = Faker()

    val randomNumber = Random.nextInt(100)

    val randomUrl = UUID.randomUUID()
        .toString()
        .take(15)
        .replaceFirstChar { "https://" }

}