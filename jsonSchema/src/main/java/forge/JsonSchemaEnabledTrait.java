package forge;

import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.model.shapes.ShapeId;
import software.amazon.smithy.model.traits.AnnotationTrait;

public class JsonSchemaEnabledTrait extends AnnotationTrait {
    public static final ShapeId ID = ShapeId.from("czr#jsonSchemaEnabled");
    public JsonSchemaEnabledTrait(ObjectNode node) {
        super(ID, node);
    }

    public JsonSchemaEnabledTrait() {
        this(Node.objectNode());
    }

    public static final class Provider extends AnnotationTrait.Provider<JsonSchemaEnabledTrait> {
        public Provider() {
            super(ID, JsonSchemaEnabledTrait::new);
        }
    }
}
