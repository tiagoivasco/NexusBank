package com.nexus.core.domain

/**
 * A sealed class representing the outcome of any use case or repository call.
 *
 * Usage:
 * ```
 * when (val result = getAccountUseCase()) {
 *     is Result.Success -> render(result.data)
 *     is Result.Error   -> showError(result.message)
 *     is Result.Loading -> showShimmer()
 * }
 * ```
 */
sealed class Result<out T> {

    data class Success<T>(val data: T) : Result<T>()

    data class Error(
        val exception: Throwable,
        val message: String? = exception.localizedMessage
    ) : Result<Nothing>()

    data object Loading : Result<Nothing>()

    // ─── Convenience properties ───────────────────────────────────────────────

    val isSuccess get() = this is Success
    val isError get() = this is Error
    val isLoading get() = this is Loading

    fun getOrNull(): T? = (this as? Success)?.data

    fun getOrThrow(): T = when (this) {
        is Success -> data
        is Error -> throw exception
        is Loading -> throw IllegalStateException("Result is still Loading")
    }
}

// ─── Extension functions ───────────────────────────────────────────────────────

inline fun <T> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
    if (this is Result.Success) action(data)
    return this
}

inline fun <T> Result<T>.onError(action: (Throwable, String?) -> Unit): Result<T> {
    if (this is Result.Error) action(exception, message)
    return this
}

inline fun <T> Result<T>.onLoading(action: () -> Unit): Result<T> {
    if (this is Result.Loading) action()
    return this
}

inline fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> = when (this) {
    is Result.Success -> Result.Success(transform(data))
    is Result.Error -> this
    is Result.Loading -> this
}
