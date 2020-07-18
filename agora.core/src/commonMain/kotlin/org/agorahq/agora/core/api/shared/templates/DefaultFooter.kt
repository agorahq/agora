package org.agorahq.agora.core.api.shared.templates

import kotlinx.html.FlowContent
import kotlinx.html.footer
import kotlinx.html.script
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.shared.date.Dates.renderCurrentDate

fun FlowContent.renderDefaultFooter(site: SiteMetadata) {
    footer {
        text("Copyright ${site.title} ${renderCurrentDate()}")
    }
    script {
        src = "https://code.jquery.com/jquery-3.5.1.slim.min.js"
        attributes["integrity"] = "sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        attributes["crossorigin"] = "anonymous"
    }
    script {
        src = "https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        attributes["integrity"] = "sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        attributes["crossorigin"] = "anonymous"
    }
    script {
        src = "https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        attributes["integrity"] = "sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        attributes["crossorigin"] = "anonymous"
    }
}
