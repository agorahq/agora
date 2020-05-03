package org.agorahq.agora.core.api.shared.templates

import kotlinx.html.ButtonType
import kotlinx.html.FormEncType
import kotlinx.html.FormMethod
import kotlinx.html.InputType
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h3
import kotlinx.html.html
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.label
import org.agorahq.agora.core.api.extensions.include
import org.agorahq.agora.core.api.template.template
import org.agorahq.agora.core.api.viewmodel.UserRegistrationViewModel

val DEFAULT_REGISTRATION_PAGE = template<UserRegistrationViewModel> { model ->
    val (site) = model.context
    html {
        include(DEFAULT_HEAD, site.title)
        body {
            include(DEFAULT_NAVIGATION, model.context)
            div("container") {
                div("card mt-2") {
                    div("card-header") {
                        h3 { +"Register" }
                    }
                    div("card-body") {
                        form(action = "/register", encType = FormEncType.multipartFormData, method = FormMethod.post) {
                            div("form-row") {
                                div("form-group col-md-6") {
                                    label {
                                        htmlFor = "email"
                                        +"Email"
                                    }
                                    input(type = InputType.email, classes = "form-control") {
                                        id = "email"
                                        name = "email"
                                        disabled = true
                                        value = model.email
                                    }
                                }
                                div("form-group col-md-6") {
                                    label {
                                        htmlFor = "username"
                                        +"Username"
                                    }
                                    input(classes = "form-control") {
                                        id = "username"
                                        name = "username"
                                        value = model.username.value
                                    }
                                }
                            }
                            div("form-row") {
                                div("form-group col-md-6") {
                                    label {
                                        htmlFor = "firstName"
                                        +"First Name"
                                    }
                                    input(classes = "form-control") {
                                        id = "firstName"
                                        name = "firstName"
                                        disabled = true
                                        value = model.firstName
                                    }
                                }
                                div("form-group col-md-6") {
                                    label {
                                        htmlFor = "lastName"
                                        +"Last Name"
                                    }
                                    input(classes = "form-control") {
                                        id = "lastName"
                                        name = "lastName"
                                        disabled = true
                                        value = model.lastName
                                    }
                                }
                            }
                            button(type = ButtonType.submit, classes = "btn btn-primary") { +"Go" }
                        }
                    }
                }
                include(DEFAULT_FOOTER, site)
            }
        }
    }
}