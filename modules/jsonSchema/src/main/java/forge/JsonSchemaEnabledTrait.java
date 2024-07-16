package forge;

import software.amazon.smithy.model.SourceException;
import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.traits.*;
import software.amazon.smithy.utils.ToSmithyBuilder;

public class JsonSchemaEnabledTrait extends  AbstractTrait implements ToSmithyBuilder<JsonSchemaEnabledTrait> {
    public static final ShapeId ID = ShapeId.from("forge#jsonSchemaEnabled");
    public enum DRAFT_VERSION {
        DRAFT_07,
        DRAFT_12,
    }
    private final DRAFT_VERSION draftVersion;

    public DRAFT_VERSION getDraftVersion() {
        return draftVersion;
    }
    public JsonSchemaEnabledTrait(JsonSchemaEnabledTrait.Builder builder) {
        super(ID, builder.getSourceLocation());
        this.draftVersion = builder.draftVersion;
    }


    public static JsonSchemaEnabledTrait.Builder builder() {
        return new JsonSchemaEnabledTrait.Builder();
    }

    @Override
    public  JsonSchemaEnabledTrait.Builder toBuilder() {
        return new JsonSchemaEnabledTrait.Builder().sourceLocation(getSourceLocation()).draftVersion(draftVersion);
    }


    public static final class Builder extends AbstractTraitBuilder<JsonSchemaEnabledTrait, JsonSchemaEnabledTrait.Builder> {

        private DRAFT_VERSION draftVersion;

        public Builder draftVersion(DRAFT_VERSION draftVersion) {
            this.draftVersion = draftVersion;
            return this;
        }


        @Override
        public JsonSchemaEnabledTrait build() {
            return new JsonSchemaEnabledTrait( this);
        }
    }

    @Override
    protected Node createNode() {
        return   Node.objectNodeBuilder()
                .sourceLocation(getSourceLocation())
                .withMember("draftVersion", Node.from(draftVersion.toString()))
                .build();
    }

    public static final class Provider extends AbstractTrait.Provider {
        public Provider() {
            super(ID);
        }

        @Override
        public Trait createTrait(ShapeId target, Node value) {
            JsonSchemaEnabledTrait.Builder builder = builder().sourceLocation(value);

            value.expectObjectNode()
                    .expectStringMember("draftVersion", (draftVersionType) -> {
                        if (draftVersionType.equalsIgnoreCase("draft12")) {
                            builder.draftVersion(DRAFT_VERSION.DRAFT_12);
                        } else {
                            builder.draftVersion(DRAFT_VERSION.DRAFT_07);
                        }
                    });
            return builder.build();
        }
    }
}

