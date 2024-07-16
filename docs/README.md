## Forge - A forge for your Smithy needs

### Introduction
Forge is a collection of modules that provide [Smithy Traits](https://smithy.io/2.0/trait-index.html) to enable and customize code generation for various protocols and schemas.

### Modules

- #### avro
The avro module is a Java project that contains the `Smithy Traits` to enable and customize Avro generation.
   - Traits
     - `AvroEnabledTrait` 
        - The core trait that specifies that a shape should be included in the Avro generation. This can be set on a Service or on a structure. All transitive shapes are included in the schema.
        - @default -> Avro default
        - @documentation -> Avro doc


- #### json-schema
The json-schema module is a Java project that contains the `Smithy Traits` to enable and customize JSON Schema generation.
While Smithy has a built-in JSON Schema generator, this module provides additional customization options based off the Smithy Model
  - Traits
    - `JsonSchemaEnabledTrait` 
        - The core trait that specifies that a shape should be included in the JSON Schema generation. This can be set on a Service or on a structure. All transitive shapes are included in the schema.
        - Default is `DRAFT_07` , however it can be customized to be `DRAFT_12` also


### CREDITS
This work is inspired and very often copied from the work done by the Disney Streaming team on the [smithy-translate](https://github.com/disneystreaming/smithy-translate) project.
Attribution is included on a per file basis.