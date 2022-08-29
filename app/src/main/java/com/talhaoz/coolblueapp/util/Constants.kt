package com.talhaoz.coolblueapp.util

object Constants {
    const val BASE_URL = "https://bdk0sta2n0.execute-api.eu-west-1.amazonaws.com/mobile-assignment/"
    const val SEARCH_END_POINT = "search"

    const val SEARCH_PARAM_QUERY = "query"
    const val SEARCH_PARAM_PAGE = "page"

    const val QUERY_PAGE_SIZE = 24
    const val QUERY_LENGTH_LIMIT = 30

    const val SEARCH_QUERY_TOO_LONG_ERROR =
        "Search Keyword is too long\nPlease enter something shorter!"
    const val SEARCH_QUERY_EMPTY_ERROR = "Search Keyword is Empty!"
    const val TEST_LONG_QUERY_CONSTANT =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris" +
                " nisi ut aliquip ex ea commodo consequat."
}