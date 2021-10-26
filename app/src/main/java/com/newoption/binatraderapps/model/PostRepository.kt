package com.newoption.binatraderapps.model

import com.newoption.binatraderapps.R
import java.util.*

class PostRepository {
    val mainData: MutableList<Int>
        get() {
            val users: MutableList<Int> = ArrayList()
            users.add(R.drawable.ic21)
            users.add(R.drawable.ic22)
            users.add(R.drawable.ic23)
            users.add(R.drawable.ic24)
            users.add(R.drawable.ic25)
            return users
        }

    val mainData2: MutableList<Int>
        get() {
            val users: MutableList<Int> = ArrayList()
            users.add(R.drawable.ic31)
            users.add(R.drawable.ic32)
            users.add(R.drawable.ic33)
            users.add(R.drawable.ic34)
            users.add(R.drawable.ic35)
            return users
        }
}