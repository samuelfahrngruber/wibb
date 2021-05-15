package com.spogss.wibb.data

enum class FilterKey(val camelCase: String) {
    OFFER_TYPE("offerType"),
    BEER_NAME_MATCHING("beerNameMatching"),
    STORE_NAME_MATCHING("storeNameMatching"),
    PRICE_AT_LEAST("priceGte"),
    PRICE_AT_MOST("priceLte"),
    VALID_AT_DATE("validAtDate")
}