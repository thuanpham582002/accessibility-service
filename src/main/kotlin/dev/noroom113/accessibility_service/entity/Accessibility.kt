package dev.noroom113.accessibility_service.entity

import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Embeddable
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany

@Entity
data class Accessibility(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
    @Column(unique = true)
    val name: String,
    val description: String,
    @ElementCollection(targetClass = UrlAccessable::class, fetch = FetchType.EAGER)
    val urlAccessables: MutableList<UrlAccessable> = mutableListOf(),

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "accessibility_hierarchy",
        joinColumns = [JoinColumn(name = "parent_accessibility_id")],
        inverseJoinColumns = [JoinColumn(name = "child_accessibility_id")]
    )
    val childAccessibilities: MutableList<Accessibility> = mutableListOf()
) {
    constructor(name: String, description: String) : this(0, name, description)
    constructor(name: String, description: String, urlAccessables: MutableList<UrlAccessable>) : this(
        0,
        name,
        description,
        urlAccessables
    )

    constructor(
        name: String,
        description: String,
        urlAccessables: MutableList<UrlAccessable>,
        childAccessibilities: MutableList<Accessibility>,
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