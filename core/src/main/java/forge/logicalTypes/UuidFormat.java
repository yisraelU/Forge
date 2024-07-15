package forge.logicalTypes;

        import software.amazon.smithy.model.node.Node;
        import software.amazon.smithy.model.shapes.ShapeId;
        import software.amazon.smithy.model.traits.AnnotationTrait;
public final class UuidFormat extends AnnotationTrait {
    public static ShapeId ID = ShapeId.from("forge#uuid");

    public UuidFormat() {
        super(ID, Node.objectNode());
    }

    public static final class Provider extends AnnotationTrait.Provider<UuidFormat> {
        public Provider() {
            super(ID, (node) -> new UuidFormat());
        }
    }
}
