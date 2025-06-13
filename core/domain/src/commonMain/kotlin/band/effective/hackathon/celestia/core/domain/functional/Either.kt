package band.effective.hackathon.celestia.core.domain.functional

/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Either] are either an instance of [Success] or [Error].
 * 
 * @param A The type of the error value
 * @param B The type of the success value
 */
sealed class Either<out A, out B> {
    /**
     * Represents the error case of [Either]
     * 
     * @param value The error value
     */
    data class Error<out A>(val value: A) : Either<A, Nothing>()
    
    /**
     * Represents the success case of [Either]
     * 
     * @param value The success value
     */
    data class Success<out B>(val value: B) : Either<Nothing, B>()
    
    /**
     * Returns true if this is a [Success], false otherwise.
     */
    val isSuccess: Boolean get() = this is Success
    
    /**
     * Returns true if this is an [Error], false otherwise.
     */
    val isError: Boolean get() = this is Error
    
    /**
     * Returns the [value][Success.value] if this is a [Success] or null if this is an [Error].
     */
    fun getOrNull(): B? = when (this) {
        is Success -> value
        is Error -> null
    }
    
    /**
     * Returns the [value][Error.value] if this is an [Error] or null if this is a [Success].
     */
    fun errorOrNull(): A? = when (this) {
        is Success -> null
        is Error -> value
    }
}

/**
 * Creates a [Either.Success] with the given [value].
 */
fun <B> success(value: B): Either<Nothing, B> = Either.Success(value)

/**
 * Creates a [Either.Error] with the given [value].
 */
fun <A> error(value: A): Either<A, Nothing> = Either.Error(value)

/**
 * Maps the [Either.Success] value if this is a [Either.Success], otherwise returns this [Either.Error].
 */
inline fun <A, B, C> Either<A, B>.map(transform: (B) -> C): Either<A, C> = when (this) {
    is Either.Success -> Either.Success(transform(value))
    is Either.Error -> this
}

/**
 * Maps the [Either.Error] value if this is a [Either.Error], otherwise returns this [Either.Success].
 */
inline fun <A, B, C> Either<A, B>.mapError(transform: (A) -> C): Either<C, B> = when (this) {
    is Either.Success -> this
    is Either.Error -> Either.Error(transform(value))
}

/**
 * Returns the result of applying [onSuccess] to the value if this is a [Either.Success],
 * or the result of applying [onError] to the value if this is a [Either.Error].
 */
inline fun <A, B, C> Either<A, B>.fold(
    onError: (A) -> C,
    onSuccess: (B) -> C
): C = when (this) {
    is Either.Success -> onSuccess(value)
    is Either.Error -> onError(value)
}

/**
 * Applies the given function [transform] to the value if this is a [Either.Success],
 * otherwise returns this [Either.Error].
 */
inline fun <A, B, C> Either<A, B>.flatMap(transform: (B) -> Either<A, C>): Either<A, C> = when (this) {
    is Either.Success -> transform(value)
    is Either.Error -> this
}

/**
 * Returns the encapsulated value if this is a [Either.Success],
 * or throws the encapsulated error if this is a [Either.Error].
 */
fun <A : Throwable, B> Either<A, B>.getOrThrow(): B = when (this) {
    is Either.Success -> value
    is Either.Error -> throw value
}

/**
 * Returns the encapsulated value if this is a [Either.Success],
 * or the [defaultValue] if this is a [Either.Error].
 */
fun <A, B> Either<A, B>.getOrDefault(defaultValue: B): B = when (this) {
    is Either.Success -> value
    is Either.Error -> defaultValue
}

/**
 * Returns the encapsulated value if this is a [Either.Success],
 * or the result of calling [defaultValue] if this is a [Either.Error].
 */
inline fun <A, B> Either<A, B>.getOrElse(defaultValue: (A) -> B): B = when (this) {
    is Either.Success -> value
    is Either.Error -> defaultValue(value)
}