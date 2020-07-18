package org.agorahq.agora.core.api.shared.templates

import kotlinx.html.*
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.shared.layouts.withDefaultLayout

fun HTML.renderDefaultHomepage(ctx: OperationContext<out Any>) = withDefaultLayout(ctx, "Home") {
    val (_, _, _, message) = ctx
    renderDefaultMessage(message)
    h1("mt-3") {
        +"Welcome to Agora!"
    }
    blockQuote {
        +"""
            This site serves as a demo for the Agora community space project.
            Feel free to look around, try things out and tinker with them.
        """.trimIndent()
    }
    p {
        +"Agora is composed of modules. You can mix and match which modules do you want to use for your website."
    }
    p {
        +"The "
        strong { +"User" }
        +" module is always enabled. It lets your users register to the site using OAuth (Facebook or Google). "
        +"Feel free to try it out by clicking the "
        strong { +"Login" }
        +" button."
    }
    p {
        +"The "
        strong { +"Post" }
        +" module will let you write "
        em { +"posts." }
        +" Feel free to navigate "
        a("/posts") { +"there" }
        +" to check it out."
    }
    p {
        +"The "
        strong { +"Comment" }
        +" module will enable your authenticated users to comment on any page where comments are enabled."
    }
    p { +"Feedback is also welcome!" }
}
