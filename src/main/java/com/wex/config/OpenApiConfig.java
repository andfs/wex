package com.wex.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(
        title = "Wex challenge",
        description = """
                Purchase api for Wex challenge.\n
                This api has two endpoints:\n 
                    1) To create a purchase with the basic info provided in the email.
                    2) To get a purchase by id with the amount converted to a specified currency. This conversion can be made by 3 ways:
                        2.1) If a currency code is passed (e.g: BRL, CAD, CLP etc), it'll be used for convert the amount to this currency.
                        2.2) If a country name is passed (in english), it'll be used for convert the amount to the country currency.
                        2.3) If nothing is passed, the api will use the request locale to get the currency of the request and convert the amount.
                Obs: It wasn't used any kind of security for simplicity and because wasn't mentioned in the instructions email.
                """,
        version = "1.0",
        contact = @Contact(name = "Anderson de Faria Silva", email = "andfariasilva@gmail.com", url = "https://www.linkedin.com/in/andfariasilva")))
public class OpenApiConfig {
}
