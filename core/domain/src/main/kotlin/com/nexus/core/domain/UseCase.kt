package com.nexus.core.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Base class for use cases that require input [Params].
 *
 * All business logic runs on [dispatcher] (default: IO) and exceptions are
 * caught and wrapped in [Result.Error] — the caller never needs a try/catch.
 *
 * Example:
 * ```
 * class TransferUseCase(
 *     private val repo: TransferRepository
 * ) : UseCase<TransferParams, Transfer>() {
 *     override suspend fun execute(params: TransferParams) = repo.transfer(params)
 * }
 * ```
 */
abstract class UseCase<in Params, out T>(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    protected abstract suspend fun execute(params: Params): Result<T>

    suspend operator fun invoke(params: Params): Result<T> =
        withContext(dispatcher) {
            runCatching { execute(params) }
                .getOrElse { Result.Error(it) }
        }
}

/**
 * Base class for use cases that need no input parameters.
 *
 * Example:
 * ```
 * class GetAccountUseCase(
 *     private val repo: HomeRepository
 * ) : NoParamsUseCase<Account>() {
 *     override suspend fun execute() = repo.getAccount()
 * }
 * ```
 */
abstract class NoParamsUseCase<out T>(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    protected abstract suspend fun execute(): Result<T>

    suspend operator fun invoke(): Result<T> =
        withContext(dispatcher) {
            runCatching { execute() }
                .getOrElse { Result.Error(it) }
        }
}
