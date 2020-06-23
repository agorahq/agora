package org.agorahq.agora.core.api.data

/**
 * A [Page] is a top level [Resource] which also has an [url]
 * where it can be accessed.
 */
interface Page : Resource {

    val url: ResourceURL<out Page>

    companion object
}
