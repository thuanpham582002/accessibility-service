package dev.noroom113.accessibility_service.entity

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Converter
import jakarta.persistence.ElementCollection
import jakarta.persistence.Embeddable
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import java.io.Serializable

@Entity
data class Accessibility(
    @Id
    val id: Long = 0,
    @Column(unique = true)
    val name: String,
    val description: String,
    @ElementCollection(targetClass = UrlAccessable::class, fetch = FetchType.EAGER)
    val urlAccessables: MutableSet<UrlAccessable> = mutableSetOf(),

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "accessibility_hierarchy",
        joinColumns = [JoinColumn(name = "parent_accessibility_id")],
        inverseJoinColumns = [JoinColumn(name = "child_accessibility_id")]
    )
    val childAccessibilities: MutableSet<Accessibility> = mutableSetOf(),
) {
    constructor(name: String, description: String) : this(0, name, description)
    constructor(name: String, description: String, urlAccessables: MutableSet<UrlAccessable>) : this(
        0,
        name,
        description,
        urlAccessables
    )

    constructor(
        name: String,
        description: String,
        urlAccessables: MutableSet<UrlAccessable>,
        childAccessibilities: MutableSet<Accessibility>,
    ) : this(0, name, description, urlAccessables, childAccessibilities)

    override fun toString(): String {
        val urlAccessables = urlAccessables.joinToString { it.toString() }
        val childAccessibilities = childAccessibilities.joinToString { it.toString() }
        return "$id $name $description $urlAccessables $childAccessibilities"
    }
}

@Embeddable
data class UrlAccessable(
    @Convert(converter = HttpMethodSetConverter::class)
    val method: Set<HttpMethod>,
    val uri: String,
) : Serializable

@Converter(autoApply = true)
class HttpMethodSetConverter : AttributeConverter<Set<HttpMethod>, String> {

    override fun convertToDatabaseColumn(attribute: Set<HttpMethod>): String {
        return attribute.joinToString(separator = ",") { it.name }
    }

    override fun convertToEntityAttribute(dbData: String): Set<HttpMethod> {
        return dbData.split(",").map { HttpMethod.valueOf(it) }.toSet()
    }
}

enum class HttpMethod {
    GET, POST, PUT, DELETE, ALL
}
