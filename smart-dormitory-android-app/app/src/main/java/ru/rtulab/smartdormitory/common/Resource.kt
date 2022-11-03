package ru.rtulab.smartdormitory.common

sealed class Resource<out T> {
    class Success<T>(val data: T) : Resource<T>()
    class Error(val msg: String) : Resource<Nothing>()
    object Loading: Resource<Nothing>()
    object Empty: Resource<Nothing>()

    inline fun handle(
        onSuccess: (data: T) -> Unit = {},
        onError: (msg: String) -> Unit = {},
        onLoading: () -> Unit = {},
    ) {
        when(this) {
            is Success -> onSuccess(data)
            is Error -> onError(msg)
            Loading -> onLoading()
        }
    }

    operator fun plus(other: Resource<*>) =
        ResourceGroup(this, other)
}

class ResourceGroup(
    first: Resource<*>,
    second: Resource<*>
) {

    private val group = mutableListOf<Resource<*>>(first, second)


    /**
     * Adds [other] to this [ResourceGroup]
     * @param other [Resource] to be added to this group
     * @return this group with added [Resource]
     */
    operator fun plus(other: Resource<*>): ResourceGroup {
        group.add(other)
        return this
    }

    /**
     * Handles the result of every [Resource] in order of addition.
     * @param onError function to be invoked when any [Resource] in this group is a [Resource.Error].
     *        Its error message is propagated to this function
     * @param onSuccess function to be invoked if every [Resource] in this group is a [Resource.Success].
     *        It returns a list of values produced by resources **in order of addition**. Take extreme care when
     *        casting to your desired type.
     * @sample ru.rtuitlab.itlab.presentation.screens.reports.ReportsViewModel.fetchReports
     */
    fun handle(
        onSuccess: (data: List<*>) -> Unit,
        onError: (msg: String) -> Unit
    ) {
        val data = mutableListOf<Any>()
        group.forEach { resource ->
            resource.handle(
                onError = {
                    onError(it)
                    return
                },
                onSuccess = {
                    data.add(it!!)
                }
            )
        }

        onSuccess(data)
    }
}