package dev.noroom113.accessibility_service.entity

import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Embeddable
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
data class Accessibility(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
    @Column(unique = true)
    val name: String,
    val description: String,
    @ElementCollection(targetClass = UrlAccessable::class, fetch = FetchType.EAGER)
    val urlAccessables: List<UrlAccessable> = emptyList(),
    @OneToMany(fetch = FetchType.EAGER)
    val childAccessibilities: List<Accessibility> = emptyList(),
) {
    constructor(name: String, description: String) : this(0, name, description)
    constructor(name: String, description: String, urlAccessables: List<UrlAccessable>) : this(
        0,
        name,
        description,
        urlAccessables
    )

    constructor(
        name: String,
        description: String,
        urlAccessables: List<UrlAccessable>,
        childAccessibilities: List<Accessibility>,
    ) : this(0, name, description, urlAccessables, childAccessibilities)

    override fun toString(): String {
        val urlAccessables = urlAccessables.joinToString { it.toString() }
        val childAccessibilities = childAccessibilities.joinToString { it.toString() }
        return "$id $name $description $urlAccessables $childAccessibilities"
    }
}

@Embeddable
data class UrlAccessable(
    @HttpMethod val method: String,
    val uri: String,
)

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class HttpMethod {
    companion object {
        const val GET = "GET"
        const val POST = "POST"
        const val PUT = "PUT"
        const val DELETE = "DELETE"
        const val ALL = "ALL"
    }
}