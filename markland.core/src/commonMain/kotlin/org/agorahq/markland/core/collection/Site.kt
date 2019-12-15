package org.agorahq.markland.core.collection

data class Site(val title: String,
                val collections: List<Collection<out Any>>,
                val email: String,
                val description: String,
                val host: String,
                val port: Int,
                val baseUrl: String)