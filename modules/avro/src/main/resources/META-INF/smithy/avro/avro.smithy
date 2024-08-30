$version: "2"
metadata suppressions = [
    {
        id: "UnreferencedShape",
        namespace: "forge",
        reason: "This is a library namespace."
    }
]

namespace forge.avro

@trait(selector: "service")
structure avroEnabled {
}

@trait(selector: "structure")
structure avroSchema {
}


@trait(selector: "string")
string avroName

@trait(selector: "structure")
structure avroName {
}

@trait(selector: "structure")
structure avroNamespace {
}

@trait(selector: "structure")
structure avroDoc {
}

@trait(selector: "*")
structure avroNamedType{
    name: String
}

list AliasList {
    member : String
}

@trait(selector: "blob")
structure avroFixed{
    namespace: String
    @required
    name: String
    aliases: AliasList
    @required
    size: Integer
}

@trait (selector: "bigdecimal")
structure avroDecimal {
    precision: Integer
    scale: Integer
    underlyingType:UnderlyingType
}

enum  UnderlyingType {
    BYTES,
    FIXED

}
@trait(selector: "string")
structure uuid {
}

@trait (selector: "timestamp")
structure avroTime {
    target: TimeStampTarget
}

enum TimeStampTarget {
    DATE,
    TIME_MILLIS,
    TIME_MICROS,
    TIMESTAMP_MILLIS,
    TIMESTAMP_MICROS,
    LOCAL_TIMESTAMP_MILLIS,
    LOCAL_TIMESTAMP_MICROS
}

@trait (selector: "string")
structure order {
    target: OrderTarget
}

enum OrderTarget {
    ASCENDING,
    DESCENDING,
    IGNORE
}