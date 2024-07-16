$version: "2.0"

namespace forge

@trait(selector: ":test(structure, service)")
structure jsonSchemaEnabled {
    draftVersion: DRAFT_VERSION
}

enum DRAFT_VERSION {
   DRAFT_07
   DRAFT_12
}