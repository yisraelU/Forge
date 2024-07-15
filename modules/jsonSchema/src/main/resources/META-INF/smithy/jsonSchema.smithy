$version: "2.0"

namespace forge

@trait(selector: "structure")
structure jsonSchemaEnabled {
}

@trait(selector: "service")
structure jsonSchema {
    @required
    schema: String
}