package org.agorahq.markland.core.collection

import org.hexworks.cobalt.Identifier

/**
 * Contains metadata about a [Document].
 */
data class DocumentMetadata(
        val id: Identifier,
        val title: String,
        /**
         * An excerpt of the [Document]. Used in TL;DR sections.
         */
        val tldr: String,
        /**
         * A short description of the [Document]. Used in link cards.
         */
        val shortDescription: String,
        /**
         * The content of this document in markdown format.
         */
        val markdownContent: String,
        /**
         * The link at which this [Document] is accessible.
         */
        val permalink: String,
        /**
         * The unix timestamp of the creation data of this [Document].
         */
        val createdAt: Long,
        /**
         * The unix timestamp of the last update of this [Document].
         */
        val updatedAt: Long = createdAt,
        val categories: List<String> = listOf(),
        val tags: List<String> = listOf())