package com.incrediblehohol.domain.contracts

internal interface IMapper<T, P> {
    fun map(input: T): P
}