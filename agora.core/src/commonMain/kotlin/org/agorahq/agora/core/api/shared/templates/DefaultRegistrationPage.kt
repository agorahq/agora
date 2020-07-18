import kotlinx.html.*
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.shared.layouts.withDefaultLayout
import org.agorahq.agora.core.api.viewmodel.UserRegistrationViewModel

fun HTML.renderDefaultRegistrationPage(
        context: OperationContext<out Any>,
        model: UserRegistrationViewModel
) = withDefaultLayout(
        context = context,
        pageTitle = "Register"
) {
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
}
